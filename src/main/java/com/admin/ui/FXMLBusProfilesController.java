/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.ui;

import com.database.Bus;
import com.database.FeeTable;
import com.database.FirebaseDB;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author alboresallyssa
 */
public class FXMLBusProfilesController implements Initializable {


    @FXML
    private TableView<Bus> busProfilesTable;

    @FXML
    private ComboBox busProfilesMenu;

    @FXML
    private TableColumn<Bus, String> columnFranchise;

    @FXML
    private TableColumn<Bus, String> columnContactNumber;

    @FXML
    private TableColumn<Bus, String> columnContactPerson;

    @FXML
    private TableColumn<Bus, String> columnSize;

    @FXML
    private TableColumn<Bus, String> columnRoute;

    @FXML
    private TableColumn<Bus, String> columnBusType;

    @FXML
    private TableColumn<Bus, String> columnCapacity;

    @FXML
    private TableColumn<Bus, String> columnPlateNo;

    @FXML
    private TableColumn<Bus, String> columnFare;

    @FXML
    private JFXButton busProfilesEditButton;

    private Stage createProfileStage = new Stage();
    private Stage createAccountStage = new Stage();

    private DatabaseReference database;
    private ObservableList<Bus> buses;


    @FXML
    void busProfilesAdminButton(ActionEvent event) throws IOException {
        //BRANDON!!!!!
        FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLCreateAccount.fxml"));
        Parent anotherRoot = anotherLoader.load();
        Scene anotherScene = new Scene(anotherRoot);
        createAccountStage.setScene(anotherScene);
        createAccountStage.initStyle(StageStyle.UNDECORATED); //removes the title bar of the window

        /**
         *  The bus profiles window is "refreshed" every time the create profile
         *  button is pressed due to an error. The error is caused from removing
         *  the title bar of the window. The same as what I did in void request.
         */

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLBusProfiles.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();

        createAccountStage.show();
    }

    @FXML
    void busProfilesEditProfileButtonPressed(ActionEvent event) throws IOException {
        //BRANDON!!!!!
        FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLEditBusProfile.fxml"));
        Parent anotherRoot = anotherLoader.load();
        Scene anotherScene = new Scene(anotherRoot);
        createProfileStage.setScene(anotherScene);
        createProfileStage.initStyle(StageStyle.UNDECORATED); //removes the title bar of the window

        /**
         *  The bus profiles window is "refreshed" every time the create profile
         *  button is pressed due to an error. The error is caused from removing
         *  the title bar of the window. The same as what I did in void request.
         */

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLBusProfiles.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();

        createProfileStage.show();
    }

    @FXML
    void busProfilesLogoutButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/LoginFormLayout.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    void busProfilesCreateProfilePressed(ActionEvent event) throws IOException {
        FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLCreateProfile.fxml"));
        Parent anotherRoot = anotherLoader.load();
        Scene anotherScene = new Scene(anotherRoot);
        createProfileStage.setScene(anotherScene);
        createProfileStage.initStyle(StageStyle.UNDECORATED); //removes the title bar of the window

        /**
         *  The bus profiles window is "refreshed" every time the create profile
         *  button is pressed due to an error. The error is caused from removing
         *  the title bar of the window. The same as what I did in void request.
         */

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLBusProfiles.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();

        createProfileStage.show();
    }
    
    @FXML
    void busProfilesGoButtonPressed(ActionEvent event) throws IOException {
        if(busProfilesMenu.getValue().equals("CURRENT")) {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLCurrentWindow.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } else if(busProfilesMenu.getValue().equals("RECORDS")) {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLRecordsWindow.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } else if(busProfilesMenu.getValue().equals("VOID REQUESTS")) {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLAdminVoidRequestsWindow.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } else if(busProfilesMenu.getValue().equals("BUS PROFILES")) {
            /**
             * Do nothing
             */
        }
    }

    private void startDataListener() {
        DatabaseReference ref = database.child("Buses");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                Bus bus = snapshot.getValue(Bus.class);
                buses.add(bus);
                busProfilesTable.setItems(buses);
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * This is to avoid a null pointer exception thrown by instantly pressing GO again right after changing
         */
        busProfilesMenu.setValue(("BUS PROFILES"));

        /**
         * This part will be used to initialize the tree table view.
         * TODO: Create profile window
         *       Delete and edit a bus profile (with the database)
         */
        buses = FXCollections.observableArrayList();

        columnPlateNo.setCellValueFactory(new PropertyValueFactory<Bus, String>("plateNo"));
        columnFranchise.setCellValueFactory(new PropertyValueFactory<Bus, String>("company"));
        columnBusType.setCellValueFactory(new PropertyValueFactory<Bus, String>("busType"));
        columnContactNumber.setCellValueFactory(new PropertyValueFactory<Bus, String>("contactNumber"));
        columnSize.setCellValueFactory(new PropertyValueFactory<Bus, String>("busSize"));
        columnCapacity.setCellValueFactory(new PropertyValueFactory<Bus, String>("busCapacity"));
        columnContactPerson.setCellValueFactory(new PropertyValueFactory<Bus, String>("contactPerson"));
        columnFare.setCellValueFactory(new PropertyValueFactory<Bus, String>("busFare"));
        columnRoute.setCellValueFactory(new PropertyValueFactory<Bus, String>("busRoute"));

        /**
         *
         private String plateNo;
         private String company;
         private String busType;
         private boolean miniBus = false;
         private String busNumber;
         private String contactPerson;
         private String contactNumber;
         private String busSize;
         private String busRoute;
         private String busCapacity;
         private String busFare;
         */

        database = FirebaseDatabase.getInstance().getReference();
        startDataListener();
        //Initialize columns on table

        /**
         * This part is for the initialization of the Combo Box.
         * TODO: Every item in the menu when chosen, another scene will be
         * opened to the item's corresponding scene (change scene/stage).
         */

        busProfilesMenu.getItems().addAll("CURRENT", "RECORDS", "VOID REQUESTS", "BUS PROFILES");
        busProfilesMenu.setVisibleRowCount(4);
        busProfilesMenu.setEditable(false);
        busProfilesMenu.setPromptText("BUS PROFILES");
    }
}
