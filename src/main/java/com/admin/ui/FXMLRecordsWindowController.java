package com.admin.ui;

import com.database.Bus;
import com.database.Fee;
import com.database.FeeTable;
import com.google.firebase.database.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class FXMLRecordsWindowController implements Initializable {



    @FXML
    private TreeTableView<FeeTable> treeTableView;

    @FXML
    private TreeTableColumn<FeeTable, String> recordsFranchise;

    @FXML
    private TreeTableColumn<FeeTable, String> recordsBusType;

    @FXML
    private TreeTableColumn<FeeTable, String> recordsPlateNo;

    @FXML
    private TreeTableColumn<FeeTable, String> recordsRoute;

    @FXML
    private TreeTableColumn<FeeTable, String> recordsStatus;

    @FXML
    private TreeTableColumn<FeeTable, String> recordsArrivalTime;

    @FXML
    private TreeTableColumn<FeeTable, String> recordsDepartureTime;

    @FXML
    private TreeTableColumn<FeeTable, String> recordsArrivalFee;

    @FXML
    private TreeTableColumn<FeeTable, String> recordsLoadingFee;

    @FXML
    private TreeTableColumn<FeeTable, String> recordsORNum;

    @FXML
    private JFXButton recordsLogoutButton;

    @FXML
    private ComboBox recordsMenu;

    @FXML
    private DatePicker dateStartDate;
    @FXML
    private DatePicker dateEndDate;
    @FXML
    private TextField totalArrival;
    @FXML
    private TextField totalLoading;
    @FXML
    private TextField totalArrivalFees;
    @FXML
    private TextField totalLoadingFees;
    @FXML
    private TextField totalFees;
    @FXML
    private TextField tableTotalArrival;
    @FXML
    private TextField tableTotalLoading;
    @FXML
    private TextField tableTotal;
    @FXML
    private JFXButton recordsAdminButton;


    private Stage createAccountStage = new Stage();
    private Stage currentStage = new Stage();

    @FXML
    void recordsAdminButtonPressed(ActionEvent event) throws IOException {
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

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLRecordsWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();

        createAccountStage.show();
    }


    int intArrival = 0, intLoading = 0;
    private ObservableList<FeeTable> fees;
    private DatabaseReference database;
    TreeItem<FeeTable> root;

    private void updateTable(LocalDate startDate, LocalDate endDate){
        root.getChildren().clear();
        DatabaseReference dref = database.child("Fees");
        intArrival = 0; intLoading = 0;

        /**
         * ordered by date (firebase default method)
         * getting all items starting from the startDate to endDate
         */

        dref.orderByChild("datePaid").startAt(startDate.toString()).endAt(endDate.toString())
                .addListenerForSingleValueEvent(new ValueEventListener() { //functions just the same sa listener above pero lain lang reference (instead of Fees, Buses na na table)
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snap : dataSnapshot.getChildren()){
                            Fee fee = snap.getValue(Fee.class);
                            if(fee.getPaidArrival()) intArrival += 1;
                            if(fee.getPaidLoading()) intLoading += 1;
                            DatabaseReference bref = database.child("Buses").child(fee.getBus_plate());
                            bref.addListenerForSingleValueEvent(new ValueEventListener() { //functions just the same sa listener above pero lain lang reference (instead of Fees, Buses na na table)
                                @Override

                                public void onDataChange(DataSnapshot bussnapshot) {
                                    Bus bus = bussnapshot.getValue(Bus.class);
                                    TreeItem<FeeTable> item = new TreeItem<>(new FeeTable(fee,bus));
                                    root.getChildren().add(item);
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {

                                }
                            });
                        }
                        /**
                         *
                         */
                        int arrive = intArrival * 50;
                        int load = intLoading * 150;
                        totalArrival.setText("" + intArrival);
                        totalArrivalFees.setText("\u20B1" + arrive);
                        totalLoading.setText("" + intLoading);
                        totalLoadingFees.setText("\u20B1" + load);
                        totalFees.setText("\u20B1" + (arrive + load));
                        tableTotalArrival.setText("\u20B1 " + arrive);
                        tableTotalLoading.setText("\u20B1 " + load);
                        tableTotal.setText("\u20B1 " + (arrive + load));
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
    }

    public void dateEndDateUpdated() {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        if (item.isAfter(dateEndDate.getValue())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #b3b5b0;");
                        }
                        if (item.isAfter(LocalDate.now())){
                            setDisable(true);
                            setStyle("-fx-background-color: #b3b5b0;");
                        }
                    }
                };
            }
        };
        dateStartDate.setDayCellFactory(dayCellFactory);
        try{
            dateEndDate.getValue();
            updateTable(dateStartDate.getValue(), dateEndDate.getValue());
        }catch(NullPointerException e){
            updateTable(LocalDate.of(1990, Month.JANUARY, 1), dateEndDate.getValue());
        }

    }

    public void dateStartDateUpdated() {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        if (item.isBefore(dateStartDate.getValue())) {
                            setDisable(true);
                        }
                        if (item.isAfter(LocalDate.now())){
                            setDisable(true);
                        }
                    }
                };
            }
        };

        try{
            dateStartDate.getValue();
            updateTable(dateStartDate.getValue(), dateEndDate.getValue());
        }catch(NullPointerException e){
            updateTable(dateStartDate.getValue(), LocalDate.now());
        }
    }

    @FXML
    void recordsLogoutButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLLoginFormWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void initialize(URL url, ResourceBundle rb) {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #b3b5b0;");
                        }
                    }
                };
            }
        };

        recordsMenu.getItems().addAll("CURRENT", "RECORDS", "VOID REQUESTS", "BUS PROFILES");
        recordsMenu.setVisibleRowCount(4);
        recordsMenu.setEditable(false);
        recordsMenu.setPromptText("RECORDS");

        recordsMenu.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                Stage stage = (Stage) recordsAdminButton.getScene().getWindow();
                if(recordsMenu.getItems().get((Integer) number2).equals("CURRENT")) {
                    FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLCurrentWindow.fxml"));
                    Parent anotherRoot = null;
                    try {
                        anotherRoot = anotherLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene anotherScene = new Scene(anotherRoot);
                    currentStage.setScene(anotherScene);
                } else if (recordsMenu.getItems().get((Integer) number2).equals("RECORDS")) {
                    FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLRecordsWindow.fxml"));
                    Parent anotherRoot = null;
                    try {
                        anotherRoot = anotherLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene anotherScene = new Scene(anotherRoot);
                    currentStage.setScene(anotherScene);
                } else if (recordsMenu.getItems().get((Integer) number2).equals("VOID REQUESTS")) {
                    FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLAdminVoidRequestsWindow.fxml"));
                    Parent anotherRoot = null;
                    try {
                        anotherRoot = anotherLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene anotherScene = new Scene(anotherRoot);
                    currentStage.setScene(anotherScene);
                } else if (recordsMenu.getItems().get((Integer) number2).equals("BUS PROFILES")) {
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

        dateEndDate.setDayCellFactory(dayCellFactory);
        dateStartDate.setDayCellFactory(dayCellFactory);
        dateStartDate.setEditable(false);
        dateEndDate.setEditable(false);


        //TODO : status, arrival time , and departure time
        recordsFranchise.setCellValueFactory(new TreeItemPropertyValueFactory<FeeTable,String>("busCompany"));
        recordsBusType.setCellValueFactory(new TreeItemPropertyValueFactory<FeeTable, String>("busType"));
        recordsPlateNo.setCellValueFactory(new TreeItemPropertyValueFactory<FeeTable, String>("plateNo"));
        recordsRoute.setCellValueFactory(new TreeItemPropertyValueFactory<FeeTable, String>("busRoute"));
//        recordsStatus.setCellValueFactory(new TreeItemPropertyValueFactory<FeeTable, String>("loadingFee"));
//        recordsArrivalTime.setCellValueFactory(new TreeItemPropertyValueFactory<FeeTable, String>("orNum"));
//        recordsDepartureTime.setCellValueFactory(new TreeItemPropertyValueFactory<FeeTable, String>("busRoute"));
        recordsArrivalFee.setCellValueFactory(new TreeItemPropertyValueFactory<FeeTable, String>("arrivalFee"));
        recordsLoadingFee.setCellValueFactory(new TreeItemPropertyValueFactory<FeeTable, String>("loadingFee"));
        recordsORNum.setCellValueFactory(new TreeItemPropertyValueFactory<FeeTable, String>("orNum"));


        totalArrival.setEditable(false);
        totalArrivalFees.setEditable(false);
        totalLoading.setEditable(false);
        totalLoadingFees.setEditable(false);
        totalFees.setEditable(false);
        tableTotalArrival.setEditable(false);
        tableTotalLoading.setEditable(false);
        tableTotal.setEditable(false);
        /**
         *initialize root and make it hidden (please do read on treetableview,
         * mas maayo bitaw mog FXML nako so dali ra mo kasabot)
         */

        root = new TreeItem<>(new FeeTable(""));
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
        database = FirebaseDatabase.getInstance().getReference();
        fees = FXCollections.observableArrayList();
        updateTable(LocalDate.of(1990, Month.JANUARY, 1), LocalDate.now());
    }
}
