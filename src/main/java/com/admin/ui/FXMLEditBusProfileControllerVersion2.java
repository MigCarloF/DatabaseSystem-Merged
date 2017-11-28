package com.admin.ui;

import com.database.Bus;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class FXMLEditBusProfileControllerVersion2 implements Initializable {
    final ToggleGroup sizeGroup = new ToggleGroup();
    final ToggleGroup typeGroup = new ToggleGroup();

    static String type = "";
    static String size = "";
    private boolean isMinibus = false;

    @FXML
    private JFXButton editProfilesCancelButton;

    @FXML
    private JFXButton editProfilesEditButton;

    @FXML
    private JFXButton editProfilestTerminateButton;

    @FXML
    private TextField editProfilesAccountNo;

    @FXML
    private TextField editProfilesCPerson;

    @FXML
    private TextField editProfilesCNumber;

    @FXML
    private TextField editProfilesFranchise;

    @FXML
    private TextField editProfilesPlateNo;

    @FXML
    private TextField editProfilesSize;

    @FXML
    private TextField editProfilesType;

    @FXML
    private TextField editProfilesRoute1;

    @FXML
    private TextField editProfilesRoute2;

    @FXML
    private TextField editProfilesRfid;

    @FXML
    private JFXRadioButton radioBoxMiniBus;

    @FXML
    private JFXRadioButton radioBoxBus;

    @FXML
    private JFXRadioButton radioBoxAircon;

    @FXML
    private JFXRadioButton radioBoxNonAircon;

    @FXML
    private Label contactPersonErr;

    @FXML
    private Label contactNumberErr;

    @FXML
    private Label franchiseErr;

    @FXML
    private Label plateNoErr;

    @FXML
    private Label routeErr;

    @FXML
    private Label typeErr;

    @FXML
    private Label sizeErr;

    @FXML
    private Label rfidErr;

    private Bus busToEdit;
    private DatabaseReference database;
    private ChildEventListener childEventListener;
    private ArrayList<Bus> buses = new ArrayList<>();

    @FXML
    void editProfilesCancelPressed(ActionEvent event) {
        Stage stage = (Stage) editProfilesCancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void editProfilesTerminatePressed(ActionEvent event) {
        // terminate bus account
        busToEdit.setActiveBus(false);
    }

    @FXML
    private void radioBoxAirconSelected(){
        radioBoxNonAircon.setSelected(false);
    }

    @FXML
    private void radioBoxNonAirconSelected(){
        radioBoxAircon.setSelected(false);
    }

    @FXML
    private void radioBoxMinibusSelected(){
        radioBoxBus.setSelected(false);
    }

    @FXML
    private void radioBoxBusSelected(){
        radioBoxMiniBus.setSelected(false);
    }

    @FXML
    void editProfilesEditPressed(ActionEvent event) {
        System.out.println("hi from editProfilesEditPressed");
        String contactPerson = editProfilesCPerson.getText();
        String contactNumber = editProfilesCNumber.getText();
        String franchise = editProfilesFranchise.getText();
        String plateNumber = editProfilesPlateNo.getText();
        String route1 = editProfilesRoute1.getText();
        String route2 = editProfilesRoute2.getText();
        String rfid = editProfilesRfid.getText();

        System.out.println("Contact person: " + contactPerson + "\nContact Number: " + contactNumber + "\nFranchise: "
                + franchise + "\nPlate number: " + plateNumber + "\nSize: " + size +
                "\nType: " + type + "\nRoute: " + route1 + " - " + route2);

        plateNumber = plateNumber.replaceAll("\\s","");//removes spaces
        Boolean error = false;

        if(contactPerson.equals("")){
            contactPersonErr.setVisible(true);
            error = true;
        }else{
            contactPersonErr.setVisible(false);
        }

        if(contactNumber.equals("")){
            contactNumberErr.setVisible(true);
            error = true;
        }else{
            contactNumberErr.setVisible(false);
        }

        if(franchise.equals("")){
            franchiseErr.setVisible(true);
            error = true;
        }else{
            franchiseErr.setVisible(false);
        }

        if(route2.equals("")){ //TODO if naa pay time, get list of all possible destinations para macheck if valid ba
            routeErr.setVisible(true); // TODO: ang destination nga gibutang
            error = true;
        }else{
            routeErr.setVisible(false);
        }

        if(!radioBoxAircon.isSelected() && !radioBoxNonAircon.isSelected()){
            typeErr.setVisible(true);
            error = true;
        }else{
            typeErr.setVisible(false);
            if(radioBoxNonAircon.isSelected()){
                type = "NON-AIRCON";
                radioBoxAircon.setSelected(false);
            }else{
                radioBoxNonAircon.setSelected(false);
                type = "AIRCON";
            }
        }

        if(!radioBoxBus.isSelected() && !radioBoxMiniBus.isSelected()){
            sizeErr.setVisible(true);
            error = true;
        }else{
            sizeErr.setVisible(false);
            if(radioBoxMiniBus.isSelected()){
                isMinibus = true;
                size = "MINIBUS";
                radioBoxBus.setSelected(false);
            }else{
                isMinibus = false;
                size = "BUS";
                radioBoxMiniBus.setSelected(false);
            }
        }

        if(plateNumber.equals("")){
            plateNoErr.setText("! Error: Please enter a plate number.");
            plateNoErr.setVisible(true);
            error = true;
        }else if(plateNumber != null && !plateNumber.equals("")){
            System.out.println("------------------B U S E S --------------------");
            System.out.println("size: " + buses.size());
            if(buses.size() != 0) {
                for(Bus b : buses){
                    System.out.println("Franchise: " + b.getCompany() + " | Plate Number: " + b.getPlateNo());
                    if(plateNumber.equals(b.getPlateNo())){
                        if(b.getRfid().equals(busToEdit.getRfid())){
                            System.out.println("no problem here, it's the same bus.");
                            plateNoErr.setVisible(false);
                        }else{
                            System.out.println("Plate no already exists within the database.");
                            plateNoErr.setText("! Error: Plate number already exists.");
                            plateNoErr.setVisible(true);
                            error = true;
                            break;
                        }
                    }
                }
            }
        }else{
            plateNoErr.setVisible(false);
        }

        if(rfid.equals("")){
            rfidErr.setText("! Error: Please enter a value.");
            rfidErr.setVisible(true);
            error = true;
        }else if(rfid != null && !rfid.equals("")){
            if(buses.size() != 0){
                for(Bus b : buses){
                    if(rfid.equals(b.getRfid())){
                        if(b.getPlateNo().equals(busToEdit.getPlateNo())){
                            System.out.println("no problem here. it's just the same bus.");
                            rfidErr.setVisible(false);
                        }else{
                            System.out.println("rfid exists.");
                            rfidErr.setText("RFID value already exists.");
                            rfidErr.setVisible(true);
                            error = true;
                            break;
                        }
                    }
                }
            }
        }else{
            rfidErr.setVisible(false);
        }

        if(!error){
            final String plate = plateNumber;
            DatabaseReference ref = database.child("Buses");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Bus bus = new Bus("", size, franchise, isMinibus, plate, contactPerson,
                            contactNumber, type, route1, route2,"","",true, rfid);
                    ref.child(busToEdit.getPlateNo()).removeValue();
                    ref.child(plate).setValue(bus);
                }

                @Override
                public void onCancelled(DatabaseError error) {}
            });
            Stage stage = (Stage) editProfilesCancelButton.getScene().getWindow();
            stage.close();
        }
    }

    private void getExistingBuses(){
        DatabaseReference ref = database.child("Buses");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    Bus bus = snap.getValue(Bus.class);
                    buses.add(bus);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        database = FirebaseDatabase.getInstance().getReference();
        getExistingBuses();

        busToEdit = SingletonEditBus.getInstance().getBus();

        System.out.println("Contact person: " + busToEdit.getContactPerson() + "\nContact Number: " + busToEdit.getContactNumber() + "\nFranchise: "
                + busToEdit.getCompany() + "\nPlate number: " + busToEdit.getPlateNo() + "\nSize: " + busToEdit.getBusSize() +
                "\nType: " + busToEdit.getBusType() + "\nRoute: " + busToEdit.getRoute1() + " - " + busToEdit.getRoute2());

        editProfilesCPerson.setText(busToEdit.getContactPerson());
        editProfilesCNumber.setText(busToEdit.getContactNumber());
        editProfilesFranchise.setText(busToEdit.getCompany());
        editProfilesPlateNo.setText(busToEdit.getPlateNo());
        editProfilesRfid.setText(busToEdit.getRfid());
        editProfilesRoute1.setText(busToEdit.getRoute1());
        editProfilesRoute2.setText(busToEdit.getRoute2());

        if(busToEdit.isMiniBus()){
            radioBoxMiniBus.setSelected(true);
            radioBoxBus.setSelected(false);
        }else{
            radioBoxBus.setSelected(true);
            radioBoxMiniBus.setSelected(false);
        }
        if(busToEdit.getBusType().equals("AIRCON")){
            radioBoxAircon.setSelected(true);
            radioBoxNonAircon.setSelected(false);
        }else{
            radioBoxNonAircon.setSelected(true);
            radioBoxAircon.setSelected(false);
        }
    }
}