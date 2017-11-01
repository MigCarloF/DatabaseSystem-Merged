package com.database;

import com.accountant.ui.Controller;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseDB {
    private static final String DATABASE_URL = "https://sbt-database-team-echo.firebaseio.com/";
    private static DatabaseReference database;
    private static Map<String, Fee> feesMap = new HashMap<>();
    private static Map<String, Bus> busMap = new HashMap<>();
    //private final Controller controller;
    //private static ArrayList<Fee> feeslist = new ArrayList<>();
    private static Controller accountant;


    public static void initFirebase() throws IOException {
        try {
            // [START initialize]
            FileInputStream serviceAccount = new FileInputStream("sbt-database-team-echo-firebase-adminsdk-idtwe-79746fb4e6.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                    .setDatabaseUrl(DATABASE_URL)
                    .build();
            FirebaseApp.initializeApp(options);
            // [END initialize]
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: invalid service account credentials. See README.");
            System.out.println(e.getMessage());

            System.exit(1);
        }

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //database = FirebaseDatabase.getInstance().getReference();
        //startDataListener();
    }



    static void run(){

        DatabaseReference fref = database.child("Fees");

        fref.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " number of transactions");
                Fee f = snapshot.getValue(Fee.class);
                System.out.println(f.getBus_plate());
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    //Fee fee = dataSnapshot.getValue(Fee.class);
                    //System.out.println(fee.getBus_plate());
                    //System.out.println(fee.getArrivalFee() + "\t" + fee.getTimePaid() + " - " + fee.getComment());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("The read failed: " + error.getMessage());
            }

        });
    }

    static void loadBuses(){

    }

    static void startDataListener() {
        DatabaseReference ref = database.child("Fees");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
//                Fee fee = dataSnapshot.getValue(Fee.class);
//                System.out.println("x: " + fee.getComment());
//                System.out.println("y: " + fee.getOrNum());
//                System.out.println("Previous Post ID: " + prevChildKey);
                Fee fee = dataSnapshot.getValue(Fee.class);
                //Controller.dataChanged(fee);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    static void initFee(){
        DatabaseReference ref = database.child("Fees");
        feesMap.put("0", new Fee(true,true,"09:00","OR_","Employee", LocalDate.of(2017, Month.AUGUST, 21),"Plate: shyet"));
        ref.setValue(feesMap);
    }

    public static void addFee(Fee fee){
        DatabaseReference ref = database.child("Fees");
        feesMap.put(fee.getOrNum(), fee);

        ref.setValue(feesMap);

    }

    static void addBus(Bus bus){
        DatabaseReference ref = database.child("Buses");
        busMap.put(bus.getPlateNo(), bus);

        ref.setValue(busMap);
    }



    public static void getAllFees(DataListener dataListener){
        DatabaseReference ref = database.child("Fees");
        ArrayList<Fee> feeslist = new ArrayList<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<Fee> feeslist = new ArrayList<>();
            //@Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Fee fee = snap.getValue(Fee.class);

                    //System.out.println(fee.getBus_plate());
                    feeslist.add(new Fee(fee.getBus_plate(),fee.getEmployeeID(),fee.getArrivalFee(),fee.getLoadingFee(),fee.getTimePaid(),fee.getOrNum(),fee.getComment(),fee.getDatePaid(),fee.get_void(),fee.getPaidArrival(),fee.getPaidLoading()));
                    System.out.println(feeslist.size() + " tae");
                }

                dataListener.newDataReceived(feeslist);
            }
            public void onCancelled(DatabaseError error) {

            }

        });

        System.out.println(feeslist.size() + " Tae");
    }

    public static void getAllBus(DotaListener dotaListener){ //ui
        DatabaseReference ref = database.child("Buses");
        ArrayList<Bus> listofbus = new ArrayList<>();


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            //@Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Bus bus = snap.getValue(Bus.class);
                    System.out.println("BUSSSS: " + bus.getPlateNo());
                    listofbus.add(new Bus(bus.getBusNumber(), bus.getBusType(), bus.getCompany(),bus.isMiniBus(), bus.getPlateNo()));

                }
                dotaListener.dataReceived(listofbus);
            }

            //@Override
            public void onCancelled(DatabaseError error) {

            }
        });
       // return listofbus;
    }
}
