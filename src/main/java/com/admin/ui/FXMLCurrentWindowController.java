package com.admin.ui;

import com.database.Bus;
import com.database.Fee;
import com.database.FeeTable;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.joda.time.LocalDate;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class FXMLCurrentWindowController implements Initializable {


    @FXML
    private JFXButton currentAdminButton;

    @FXML
    private JFXButton currentLogoutButton;

    @FXML
    private JFXButton busProfilesCreateProfileButton;

    @FXML
    private JFXButton currentGoButton;

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

    @FXML
    private TextField lblTotalArrival;

    @FXML
    private TextField lblArrivalFees;

    @FXML
    private TextField lblTotalLoading;

    @FXML
    private TextField lblLoadingFees;

    @FXML
    private TextField lblTotal;

    @FXML
    private TextField lblLoading;

    @FXML
    private TextField lblDocking;

    @FXML
    private TextField lblPassengers;

    @FXML
    private TextField dateToday;


    private Stage currentStage = new Stage();
    private Stage createAccountStage = new Stage();
    private DatabaseReference database;
    private ObservableList<FeeTable> fees;
    private int intArrival, intLoading;

    @FXML
    void busProfilesCreateProfilePressed(ActionEvent event) {

    }

    @FXML
    void currentAdminButton(ActionEvent event) throws IOException {
        //BRANDON!!!!!
        FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLCreateAccount.fxml"));
        Parent anotherRoot = anotherLoader.load();
        Scene anotherScene = new Scene(anotherRoot);
        createAccountStage.setScene(anotherScene);
        createAccountStage.initStyle(StageStyle.UNDECORATED); //removes the title bar of the window

        /**
         *  The bus profiles window is "refreshed" every time the create profile
         *  button is pressed due to an error. The error is caused from removing
         *  the title bar of the window.
         */

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLCurrentWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();

        createAccountStage.show();
    }

    @FXML
    void currentLogoutButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../../loginform/FXMLLoginFormWindow.fxml"));
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
        columnRoute.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("busRoute"));
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

        currentMenu.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                Stage stage = (Stage) currentAdminButton.getScene().getWindow();
                if(currentMenu.getItems().get((Integer) number2).equals("CURRENT")) {
                    FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLCurrentWindow.fxml"));
                    Parent anotherRoot = null;
                    try {
                        anotherRoot = anotherLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene anotherScene = new Scene(anotherRoot);
                    currentStage.setScene(anotherScene);
                } else if (currentMenu.getItems().get((Integer) number2).equals("RECORDS")) {
                    FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLRecordsWindow.fxml"));
                    Parent anotherRoot = null;
                    try {
                        anotherRoot = anotherLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene anotherScene = new Scene(anotherRoot);
                    currentStage.setScene(anotherScene);
                } else if (currentMenu.getItems().get((Integer) number2).equals("VOID REQUESTS")) {
                    FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLAdminVoidRequestsWindow.fxml"));
                    Parent anotherRoot = null;
                    try {
                        anotherRoot = anotherLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene anotherScene = new Scene(anotherRoot);
                    currentStage.setScene(anotherScene);
                } else if (currentMenu.getItems().get((Integer) number2).equals("BUS PROFILES")) {
                    FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLBusProfiles.fxml"));
                    Parent anotherRoot = null;
                    try {
                        anotherRoot = anotherLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene anotherScene = new Scene(anotherRoot);
                    currentStage.setScene(anotherScene);
                }
                stage.close();
                currentStage.show();
            }
        });
    }


    private void startDataListener() {

        dateToday.setText("" + LocalDate.now());

        DatabaseReference ref = database.child("Fees");
        intArrival = 0; intLoading = 0;

        /**
         * ordered by date (firebase default method)
         * getting all items starting from the startDate to endDate
         */

        ref.orderByChild("datePaid").equalTo(LocalDate.now().toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snap : dataSnapshot.getChildren()){
                            Fee fee = snap.getValue(Fee.class);
                            if(fee.getPaidArrival()) intArrival += 1;
                            if(fee.getPaidLoading()) intLoading += 1;
                        }
                        /**
                         *
                         */
                        int arrive = intArrival * 50;
                        int load = intLoading * 150;
                        lblTotalArrival.setText("" + intArrival);
                        lblArrivalFees.setText("\u20B1" + arrive);
                        lblTotalLoading.setText("" + intLoading);
                        lblLoadingFees.setText("\u20B1" + load);
                        lblTotal.setText("\u20B1" + (arrive + load));
                        lblLoading.setText("" + load);      //what is loading??
                        lblDocking.setText("" + arrive);    //what is docking??
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
        //ref = database.child("Fees");
        ref.orderByKey().addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                Fee fee = snapshot.getValue(Fee.class);
                DatabaseReference bref = database.child("Buses").child(fee.getBus_plate());
                bref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot bussnapshot) {
                        Bus bus = bussnapshot.getValue(Bus.class);
                        fees.add(new FeeTable(fee, bus));
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
