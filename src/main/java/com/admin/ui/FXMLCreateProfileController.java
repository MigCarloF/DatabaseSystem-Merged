package com.admin.ui;

import com.database.Bus;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLCreateProfileController implements Initializable {
    final ToggleGroup sizeGroup = new ToggleGroup();
    final ToggleGroup typeGroup = new ToggleGroup();

    static String type = "";
    static String size = "";
    //private boolean ismini = false;
    private boolean isbus = false;

    @FXML
    private JFXButton createProfileCancelButton;

    @FXML
    private JFXButton createProfileCreateButton;

    @FXML
    private TextField createProfileCPerson;

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

    private DatabaseReference database;

    private ChildEventListener childEventListener;
    private Alert alert = new Alert(Alert.AlertType.ERROR);
    private Boolean errorFound = false;
    private int latestNumberOfBusProfiles;

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

        System.out.println("Contact person: " + contactPerson + "\nContact Number: " + contactNumber + "\nFranchise: "
        + franchise + "\nPlate number: " + plateNumber + "\nSize: " + size +
        "\nType: " + type + "\nRoute: " + route1 + " - " + route2);
    
        plateNumber = plateNumber.replaceAll("\\s","");//removes spaces
        if(contactPerson.equals("") || contactNumber.equals("") || franchise.equals("") || plateNumber.equals("") 
                || route1.equals("") || route2.equals("")){
            alert.setTitle("INCOMPLETE DATA");
            alert.setHeaderText("");
            alert.setContentText("Please fill in all the data needed.");
            alert.showAndWait();
        }else {
            final  String plate = plateNumber;
            DatabaseReference ref = database.child("Buses").child(plateNumber);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(snapshot.getChildrenCount() > 0){
                        //todo throw bus w/ plateno already exist
                        errorFound = true;
                    }else{
                        Bus bus = new Bus("",size,franchise,isbus,plate,contactPerson,
                                contactNumber,type,route1 + " - " + route2,"","",true);
                        ref.setValue(bus);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }
        if(errorFound){
            System.out.println("Existing plate number found.");
            alert.setTitle("E R R O R");
            alert.setHeaderText("A bus with this plate number " + plateNumber + " already exists.");
            alert.setContentText("Please enter another plate number.");
            alert.showAndWait();
        }else{
            Stage stage = (Stage) createProfileCancelButton.getScene().getWindow();
            stage.close();
        }
        
        /**
         * Add data to database
         * TODO: if not all need inputs are inputted, the alert text will change color
         */
        

        //todo checking if such or exists
/*
        if(plateNumber == null || plateNumber.equals("")) {
            System.out.println("invalid");
            //noVoid.setText("* - bus  does not exist");
        }else{
            final  String plate = plateNumber;
            DatabaseReference ref = database.child("Buses").child(plateNumber);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(snapshot.getChildrenCount() > 0){
                        //todo throw bus w/ plateno already exist
                    }else{
                        Bus bus = new Bus("",size,franchise,isbus,plate,contactPerson,
                                contactNumber,type,route1 + " - " + route2,"","",true);
                        ref.setValue(bus);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }

//        if(plateNumber.equals("")) {
//            // change color of createProfileAlert to red
//        } else {
//            // change back to black
//        }

         // closes the window
        Stage stage = (Stage) createProfileCreateButton.getScene().getWindow();
        stage.close();*/
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
                isbus = true;
            }else{
                isbus = false;
            }
        });

        database = FirebaseDatabase.getInstance().getReference();

    }
    
}
