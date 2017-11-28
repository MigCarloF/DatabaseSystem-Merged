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

public class FXMLCreateProfileController implements Initializable {
    final ToggleGroup sizeGroup = new ToggleGroup();
    final ToggleGroup typeGroup = new ToggleGroup();

    static String type = "";
    static String size = "";
    private boolean isMinibus = false;

    @FXML
    private JFXButton createProfileCancelButton;

    @FXML
    private JFXButton createProfileCreateButton;

    @FXML
    private TextField createProfileCPerson;

    @FXML
    private TextField createProfileRfid;

    @FXML
    private TextField createProfileCNumber;

    @FXML
    private TextField createProfileFranchise;

    @FXML
    private TextField createProfilePlateNo;

    @FXML
    private TextField createProfileRoute1;

    @FXML
    private JFXRadioButton radioBoxMiniBus;

    @FXML
    private JFXRadioButton radioBoxBus;

    @FXML
    private JFXRadioButton radioBoxAircon;

    @FXML
    private JFXRadioButton radioBoxNonAircon;

    @FXML
    private TextField createProfileRoute2;

    @FXML
    private TextField createProfileAlert;

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

    private DatabaseReference database;

    private ChildEventListener childEventListener;
    private Boolean errorFound = false;
    private ArrayList<Bus> buses = new ArrayList<>();


    @FXML
    void createProfileCancelPressed(ActionEvent event) {
        Stage stage = (Stage) createProfileCancelButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void createProfileCreatePressed(ActionEvent event) {
        String contactPerson = createProfileCPerson.getText();
        String contactNumber = createProfileCNumber.getText();
        String franchise = createProfileFranchise.getText();
        String plateNumber = createProfilePlateNo.getText();
        String route1 = createProfileRoute1.getText();
        String route2 = createProfileRoute2.getText();
        String rfid = createProfileRfid.getText();

        System.out.println("Contact person: " + contactPerson + "\nContact Number: " + contactNumber + "\nFranchise: "
        + franchise + "\nPlate number: " + plateNumber + "\nSize: " + size +
        "\nType: " + type + "\nRoute: " + route1 + " - " + route2 + "\nRFID: " + rfid);

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

        if(route2.equals("")){ //TODO: if naa pay time, get list of all possible destinations para macheck if valid ba
            routeErr.setVisible(true); // TODO: (cont.) ang destination nga gibutang
            error = true;
        }else{
            routeErr.setVisible(false);
        }

        if(!radioBoxAircon.isSelected() && !radioBoxNonAircon.isSelected()){
            typeErr.setVisible(true);
            error = true;
        }else{
            typeErr.setVisible(false);
        }

        if(!radioBoxBus.isSelected() && !radioBoxMiniBus.isSelected()){
            sizeErr.setVisible(true);
            error = true;
        }else{
            sizeErr.setVisible(false);
        }


        if(plateNumber.equals("")){
            plateNoErr.setText("! Error: Please enter a plate number.");
            plateNoErr.setVisible(true);
            error = true;
        }else if(plateNumber != null && !plateNumber.equals("")){
            System.out.println("------------------B U S E S --------------------");
            System.out.println("size: " + buses.size());
            if(buses.size() != 0) {
                for(Bus b : buses) {
                    System.out.println("Franchise: " + b.getCompany() + " | Plate Number: " + b.getPlateNo());
                    if (plateNumber.equals(b.getPlateNo())) {
                        System.out.println("Plate no already exists.");
                        plateNoErr.setText("! Error: Plate number already exists.");
                        plateNoErr.setVisible(true);
                        error = true;
                        break;
                    }else{
                        plateNoErr.setVisible(false);
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
                        rfidErr.setText("RFID value already exists.");
                        rfidErr.setVisible(true);
                        error = true;
                        break;
                    }else{
                        rfidErr.setVisible(false);
                    }
                }
            }
        }else{
            rfidErr.setVisible(false);
        }
        
        if(!error){
            final String plate = plateNumber;
            DatabaseReference ref = database.child("Buses").child(plateNumber);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(snapshot.getChildrenCount() > 0){
                        System.out.println("Error, plate number already exists"); //just in case the above conditions fail
                    }else{
                        Bus bus = new Bus("", size, franchise, isMinibus, plate, contactPerson,
                                contactNumber, type, route1, route2, "", "",true, rfid);
                        ref.setValue(bus);
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {}
            });
            Stage stage = (Stage) createProfileCancelButton.getScene().getWindow();
            stage.close();
        }
    }

    private void getBuses(){
        buses.clear();
        DatabaseReference ref = database.child("Buses");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    buses.add(snap.getValue(Bus.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        database = FirebaseDatabase.getInstance().getReference();
        getBuses();

        radioBoxMiniBus.setToggleGroup(sizeGroup);
        radioBoxBus.setToggleGroup(sizeGroup);

        radioBoxAircon.setToggleGroup(typeGroup);
        radioBoxNonAircon.setToggleGroup(typeGroup);

        typeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            type = observable.getValue().toString();
            type = type.substring(type.indexOf("'")+1, type.lastIndexOf("'"));
            System.out.println(type);
        });

        sizeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            size = observable.getValue().toString();
            size = size.substring(size.indexOf("'")+1, size.lastIndexOf("'"));
            System.out.println(size);
            if(size.equals("BUS")){
                isMinibus = false;
            }else{
                isMinibus = true;
            }
        });
    }
}