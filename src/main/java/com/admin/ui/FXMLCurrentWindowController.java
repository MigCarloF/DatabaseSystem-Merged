package com.admin.ui;

import com.database.Bus;
import com.database.Fee;
import com.database.FeeTable;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.joda.time.LocalDate;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLCurrentWindowController implements Initializable {


    @FXML
    private ComboBox currentMenu;

    @FXML
    private TableView<FeeTable> tableView;

    @FXML
    private TableColumn<FeeTable, String> columnFranchise;

    @FXML
    private TableColumn<FeeTable, String> columnBusType;

    @FXML
    private TableColumn<FeeTable, String> columnPlateNo;

    @FXML
    private TableColumn<FeeTable, String> columnRoute;

    @FXML
    private TableColumn<FeeTable, String> columnStatus;

    @FXML
    private TableColumn<FeeTable, String> columnArrivalTime;

    @FXML
    private TableColumn<FeeTable, String> columnDepartureTime;

    @FXML
    private TableColumn<FeeTable, String> columnArrivalFee;

    @FXML
    private TableColumn<FeeTable, String> columnLoadingFee;

    @FXML
    private TableColumn<FeeTable, String> columnOrNum;

    private DatabaseReference database;
    private ObservableList<FeeTable> fees;

    @FXML
    void busProfilesCreateProfilePressed(ActionEvent event) {

    }

    @FXML
    void currentAdminButton(ActionEvent event) {

    }

    @FXML
    void currentGoButtonPressed(ActionEvent event) throws IOException {

        if(currentMenu.getValue().equals("RECORDS")) {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLRecordsWindow.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } else if(currentMenu.getValue().equals("VOID REQUESTS")) {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLAdminVoidRequestsWindow.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } else if(currentMenu.getValue().equals("BUS PROFILES")) {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLBusProfiles.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }
    }

    @FXML
    void currentLogoutButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../../../../resources/LoginFormLayout.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void initialize(URL url, ResourceBundle rb) {
        /**
         * This is to avoid a null pointer exception thrown by instantly pressing GO again right after changing
         */
        currentMenu.setValue(("CURRENT"));

        /**
         * Initializing columns
         */
        columnFranchise.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("busCompany"));
        columnArrivalFee.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("arrivalFee"));
        columnLoadingFee.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("loadingFee"));
        columnArrivalTime.setCellValueFactory(new PropertyValueFactory<FeeTable, String>(""));
        columnDepartureTime.setCellValueFactory(new PropertyValueFactory<FeeTable, String>(""));
        columnBusType.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("busType"));
        columnOrNum.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("orNum"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<FeeTable, String>(""));
        columnRoute.setCellValueFactory(new PropertyValueFactory<FeeTable, String>(""));
        columnPlateNo.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("plateNo"));

        /**
         * Listener for database here
         */

        database = FirebaseDatabase.getInstance().getReference();
        fees = FXCollections.observableArrayList();
        startDataListener();

        /**
         * This part is for the initialization of the Combo Box.
         * TODO: Every item in the menu when chosen, another scene will be
         * opened to the item's corresponding scene (change scene/stage).
         */

        currentMenu.getItems().addAll("CURRENT", "RECORDS", "VOID REQUESTS", "BUS PROFILES");
        currentMenu.setVisibleRowCount(4);
        currentMenu.setEditable(false);
        currentMenu.setPromptText("CURRENT");
    }

    private void startDataListener() {
        DatabaseReference ref = database.child("Fees");
        ref.orderByKey().addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                Fee fee = snapshot.getValue(Fee.class);
                DatabaseReference bref = database.child("Buses").child(fee.getBus_plate());
                bref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot bussnapshot) {
                        Bus bus = bussnapshot.getValue(Bus.class);
                        //if(LocalDate.parse(fee.getDatePaid()).equals(LocalDate.now())) {
                        System.out.println("gap1");
                        fees.add(new FeeTable(fee, bus));
                        System.out.println("gap2");
                        System.out.println(fees.get(0).getBusType());
                        System.out.println("gap!");
                        //}
                        tableView.setItems(fees);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        System.out.println("ERROR!");

                    }
                });

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
}
