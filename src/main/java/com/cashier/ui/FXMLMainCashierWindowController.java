package com.cashier.ui;


import com.database.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.print.PrinterException;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class FXMLMainCashierWindowController implements Initializable {
    //These items are for the buttons in arrival window
    Stage anotherStage = new Stage();
    @FXML
    private Text cashierUserText;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private JFXButton busPrintButton;

    @FXML
    private TextField plateNumber;

    @FXML
    private JFXCheckBox arrivalFee;

    @FXML
    private JFXCheckBox loadingFee;

    @FXML
    private JFXButton transactionsButton;

    @FXML
    private JFXButton rangeButton;

    @FXML
    private Text currentORNumber;

    @FXML
    private Label noPlate;

    @FXML
    private Label noCheck;

    private DatabaseReference database;
    private DatabaseReference exitDatabase;
    private Fee forPrinting;
    private boolean loaded;
    //private FirebaseApp exitRFID;
    private int ORNUM;

    private void exitListener() {
//        DatabaseReference ref = exitDatabase.child("Exit");
//        Exit exit = new Exit("51:5C:AA:89","loaded",LocalDate.now().toString());
//        ref.push().setValue(exit);
        DatabaseReference ref = exitDatabase.child("Exit");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                Exit exit = snapshot.getValue(Exit.class);
                String rfid = exit.getRfid();
                String status = exit.getStatus();
                System.out.println(rfid);
                loaded = false;
                if (status.equals("loaded")) {
                    loaded = true;
                }

                DatabaseReference bref = database.child("Buses");
                bref.addListenerForSingleValueEvent(new ValueEventListener() {
                    Bus bus;

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            bus = snap.getValue(Bus.class);
                            if (bus.getRfid().equals(rfid)) {
                                arrivalFee.setSelected(true);
                                if (loaded) loadingFee.setSelected(true);
                                plateNumber.setText(bus.getPlateNo());
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
                //System.out.println(exit.getRfid());
                //System.out.println("tae");
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
//                Exit exit = snapshot.getValue(Exit.class);
//                System.out.println(exit.getRfid());
//                System.out.println("tae");
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    @FXML
    void busPrintButtonPressed(ActionEvent event) {
        noCheck.setText("");
        noPlate.setText("");
        forPrinting = null;

        String plateNo = plateNumber.getText();
        plateNo = plateNo.replaceAll("\\s", "");//removes spaces

        if (plateNo.equals("")) {
            //todo throw error nga empty
            noPlate.setText("* - Plate number cannot be empty");
            System.out.println("no input");
        } else {
            plateNo = plateNo.toUpperCase();  //all caps
            final String plateNum = plateNo;
            //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate localDate = LocalDate.now();

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String dateFormat = sdf.format(date.getTime());
            boolean arrival = false;
            boolean loading = false;
            if (arrivalFee.isSelected()) {
                arrival = true;
            }
            if (loadingFee.isSelected()) {
                loading = true;
            }

            if (!loadingFee.isSelected() && !arrivalFee.isSelected()) {
                noCheck.setText("* - Select Fee to be paid");
                //lblBusFeeTypeErr.setText("* - Select Fee to be paid");
                //todo error select fee to be paid
                System.out.println("unchecked");
            } else {
                noCheck.setText("");
                noPlate.setText("");
                //lblBusFeeTypeErr.setText("");
                final boolean hasArrival = arrival;   //inner class calls needs to be final
                final boolean hasLoading = loading;
                DatabaseReference ref = database.child("Buses");
                ref.child(plateNum).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.getChildrenCount() == 0) {
                            //todo error here bus doesnt exist
                            noPlate.setText("* - bus  does not exist");
                            //noPlate.setText("* - bus with plate " + plateNum + " does not exist");
                            System.out.println("no bus");
                        } else {
                            DatabaseReference nref = database.child("Range");//.child("current");
                            nref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    RangeOR range = snapshot.getValue(RangeOR.class);
                                    ORNUM = range.getCurrent();
                                    if (ORNUM > range.getEnd()) {
                                        //todo throw error nga out of range na
                                        System.out.println("out of paper");
                                    } else {
                                        DatabaseReference aref = database.child("Fees");
                                        Fee forDatabase = new Fee(hasArrival, hasLoading, dateFormat, "" + ORNUM, "Cashier 01", localDate, plateNum);
                                        aref.child(forDatabase.getOrNum()).setValue(forDatabase);

                                        /**
                                         * printing here
                                         */
                                        printOut(forDatabase);

                                        Map<String, Object> newRange = new HashMap<>();
                                        newRange.put("current", ORNUM += 1);
                                        nref.updateChildren(newRange);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        }
    }

    private void printOut(Fee f) {
        try {
            /**
             * Setting up strings for print arrangement
             */
            String date = f.getDatePaid();
            String payor = f.getBus_plate();
            String nature1 = "Arrival Fee";
            String nature2 = "Loading Fee";
            String arrivalFee = f.getArrivalFee();
            String loadingFee = f.getLoadingFee();

            /**
             * Initializing file writer to write text file
             */
            PrintWriter out = new PrintWriter("filename.txt");
            out.println("\n\n\n\n\n\n\n   " + date + "\n\n\n   " + payor + "\n\n\n\n\n\n   " + nature1 + "\t\t\t\t\t" +
                    arrivalFee + "\n\n  " + nature2 + "\t\t\t\t\t" + loadingFee);
            out.close();

            /**
             * Initializing file reader to read text file
             */
            InputStream is = new FileInputStream("filename.txt");
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();

            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }

            /**
             * Displays contents on console
             */
            String fileAsString = sb.toString();
            System.out.println("Contents : " + fileAsString + " -END");

            /**
             * Transfers txt file to EditorPane and prints out
             */
            JEditorPane text = new JEditorPane("file:filename.txt");
            PrintService service = PrintServiceLookup.lookupDefaultPrintService();
            text.print(null, null, true, service, null, false);

        } catch (FileNotFoundException e) {
            System.out.println("No file");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.printf("IOException");
            e.printStackTrace();
        } catch (PrinterException e) {
            System.out.println("PrinterException");
            e.printStackTrace();
        }
    }

    @FXML
    void transactionsButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLTransactions.fxml"));
        //Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLAccountantWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }


    @FXML
    void arrivalWindowLogoutPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLLoginFormWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    void rangeButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLGetRange.fxml"));
        Parent anotherRoot = anotherLoader.load();
        //anotherStage.centerOnScreen();  //does not really work idk
        Scene anotherScene = new Scene(anotherRoot);
        anotherStage.setScene(anotherScene);
        anotherStage.initStyle(StageStyle.UNDECORATED);

        voidWindowVoidPressed(event);
        anotherStage.show();
    }

    @FXML
    void arrivalWindowVoidPressed(ActionEvent event) throws IOException {
        FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLVoidWindow.fxml"));
        Parent anotherRoot = anotherLoader.load();
        //anotherStage.centerOnScreen();  //does not really work idk
        Scene anotherScene = new Scene(anotherRoot);
        anotherStage.setScene(anotherScene);
        anotherStage.initStyle(StageStyle.UNDECORATED);

        voidWindowVoidPressed(event);
        anotherStage.show();
    }

    @FXML
    void voidWindowVoidPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLMainCashierWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void initialize(URL url, ResourceBundle rb) {
        ORNUM = 0;
        cashierUserText.setText("JJuan");

        currentORNumber.setText("000142");
        noCheck.setText("");
        noPlate.setText("");
        database = FirebaseDatabase.getInstance().getReference();
        exitDatabase = FirebaseDatabase.getInstance(FirebaseDB.getExitRFID()).getReference();
        exitListener();
    }
}