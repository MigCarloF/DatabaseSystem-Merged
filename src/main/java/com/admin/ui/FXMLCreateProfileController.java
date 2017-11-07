/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.ui;

import com.database.Bus;
import com.database.Fee;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLCreateProfileController implements Initializable {

    @FXML
    private JFXButton createProfileCancelButton;

    @FXML
    private JFXButton createProfileCreateButton;

    @FXML
    private TextField createProfileAccountNo;

    @FXML
    private TextField createProfileCPerson;

    @FXML
    private TextField createProfileCNumber;

    @FXML
    private TextField createProfileFranchise;

    @FXML
    private TextField createProfilePlateNo;

    @FXML
    private TextField createProfileBusNo;

    @FXML
    private TextField createProfileCapacity;

    @FXML
    private TextField createProfileRoute1;

    @FXML
    private TextField createProfileFare;

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

    private final ToggleGroup sizeGroup = new ToggleGroup();
    private final ToggleGroup typeGroup = new ToggleGroup();

    private static String type = "";
    private static String size = "";
    private DatabaseReference database;
    private boolean successfulCreate;

    @FXML
    void createProfileCancelPressed(ActionEvent event) {
            Stage stage = (Stage) createProfileCancelButton.getScene().getWindow();
            stage.close();
    }

    @FXML
    void createProfileCreatePressed(ActionEvent event) {
        //TODO: get inputs
//        String contactPerson = createProfileCPerson.getText();
//        String contactNumber = createProfileCNumber.getText();
//        String franchise = createProfileFranchise.getText();
//        String plateNumber = createProfilePlateNo.getText();
//        String busNumber = createProfileBusNo.getText();
//        String size = createProfileSize.getText();
//        String capacity = createProfileCapacity.getText();
//        String type = createProfileType.getText();
//        String route = createProfileRoute.getText();
//        String fare = createProfileFare.getText();

        String contactPerson = createProfileCPerson.getText();
        String contactNumber = createProfileCNumber.getText();
        String franchise = createProfileFranchise.getText();
        String plateNumber = createProfilePlateNo.getText();
        String busNumber = createProfileBusNo.getText();
        String capacity = createProfileCapacity.getText();
        String route1 = createProfileRoute1.getText();
        String route2 = createProfileRoute2.getText();
        String fare = createProfileFare.getText();
        String route = "" + route1 + " - " + route2;

        DatabaseReference ref = database.child("Buses");


        ref.child(plateNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.getChildrenCount() == 0){
                    Bus bus;
                    if(size.equals("bus")){
                        bus = new Bus(plateNumber,franchise,busNumber,contactPerson,contactNumber,type,
                                route + route2,capacity,fare);
                    }else {
                        bus = new Bus(plateNumber,franchise,contactPerson,contactNumber,type,
                                route,capacity,fare);
                    }
                    ref.child(plateNumber).setValue(bus);
                }else {
                    showError();
                    System.out.println("Data error");
                    /**
                     * alert nga bus with plate number xxxx already exists
                     * also walay bus number if minibus unta :)
                     * also dapat mag add pa kog check if ever sa certain franchise nag exist na ba na nga
                     * bus number :(
                     */

                    /**
                     * 11/7/2017
                     * Error checking popup added //REVERTED
                     */
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("HMMMM");
            }
        });

//        System.out.println("Contact person: " + contactPerson + "\nContact Number: " + contactNumber + "\nFranchise: "
//        + franchise + "\nPlate number: " + plateNumber + "\nBus number: " + busNumber + "\nSize: " + size +
//        "\nCapacity: " + capacity + "\nType: " + type + "\nRoute: " + route + "\nFare: " + fare);

        /**
         * Add data to database
         */

         // closes the window
            Stage stage = (Stage) createProfileCancelButton.getScene().getWindow();
            stage.close();
    }

    private void showError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("INCOMPLETE DATA");
        alert.setHeaderText("Please check your data");
        alert.setContentText("");
        alert.showAndWait();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int latestNumberOfBusProfiles = 71; //TODO: get the current number of bus profiles in the database
        createProfileAccountNo.setText(String.valueOf(latestNumberOfBusProfiles + 1));

        //BRANDON!!!!!
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
        });

        database = FirebaseDatabase.getInstance().getReference();
    }    
    
}
