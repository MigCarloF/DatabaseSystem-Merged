package com.accountant.ui;

import com.database.Bus;
import com.database.Fee;
import com.database.FeeTable;
import com.google.firebase.database.*;
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
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

public class FXMLAccountantWindowController implements Initializable {
    @FXML
    private TableView<FeeTable> tableView;
    @FXML
    private TableColumn<FeeTable, String> columnFranchise;
    @FXML
    private TableColumn<FeeTable, String> columnBusSize;
    @FXML
    private TableColumn<FeeTable, String> columnArrivalFee;
    @FXML
    private TableColumn<FeeTable, String> columnLoadingFee;
    @FXML
    private TableColumn<FeeTable, String> columnOrNum;
    @FXML
    private Label lblTotalEarnings;
    @FXML
    private TextField txtTotalArrivalFees;
    @FXML
    private TextField txtTotalLoadingFees;
    @FXML
    private TextField txtTotalAllFees;
    @FXML
    private DatePicker dateStartDate;
    @FXML
    private DatePicker dateEndDate;

    private ObservableList<FeeTable> fees;
    private DatabaseReference database;
    //private Database database;

    /**
     * Code after End Date is update
     */
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
        //updateTable(convertDate(dateStartDate.getValue()), convertDate(dateEndDate.getValue()));
        updateTable(dateStartDate.getValue(), dateEndDate.getValue());
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
        dateEndDate.setDayCellFactory(dayCellFactory);
        updateTable(dateStartDate.getValue(), dateEndDate.getValue());
    }

    public void logoutButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/LoginFormLayout.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //disable manual input of dates and disables selection of days after current day
        //code taken from https://stackoverflow.com/questions/26330348/javafx-datepicker-how-to-customize
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
        dateEndDate.setDayCellFactory(dayCellFactory);
        dateStartDate.setDayCellFactory(dayCellFactory);
        dateStartDate.setEditable(false);
        dateEndDate.setEditable(false);

        //initialize columns on table
        columnFranchise.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("busCompany"));
        columnBusSize.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("busSize"));
        columnArrivalFee.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("arrivalFee"));
        columnLoadingFee.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("loadingFee"));
        columnOrNum.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("orNum"));


        database = FirebaseDatabase.getInstance().getReference();
        fees = FXCollections.observableArrayList();
        updateTable(LocalDate.of(1990, Month.JANUARY, 1), LocalDate.now());

    }

    //accepts local date
    @SuppressWarnings("Duplicates")
    private void updateTable(LocalDate startDate, LocalDate endDate){
        fees.clear();
        DatabaseReference dref = database.child("Fees");

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
                            DatabaseReference bref = database.child("Buses").child(fee.getBus_plate());
                            bref.addListenerForSingleValueEvent(new ValueEventListener() { //functions just the same sa listener above pero lain lang reference (instead of Fees, Buses na na table)
                                @Override
                                public void onDataChange(DataSnapshot bussnapshot) {
                                    Bus bus = bussnapshot.getValue(Bus.class);
                                    fees.add(new FeeTable(fee,bus));
                                    tableView.setItems(fees);
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {

                                }
                            });
                        }
                        int totalArrival = 0, totalLoading = 0;
                        int unsortedTotal = 0;
                        ObservableList<FeeTable> feeList = tableView.getItems();
                        for (FeeTable f : feeList) {
                            totalArrival += Integer.parseInt(f.getArrivalFee());
                            totalLoading += Integer.parseInt(f.getLoadingFee());
                        }
                        for (FeeTable f : fees) {
                            unsortedTotal += Integer.parseInt(f.getArrivalFee());
                            unsortedTotal += Integer.parseInt(f.getLoadingFee());
                        }
                        txtTotalArrivalFees.setText(String.valueOf(totalArrival));
                        txtTotalLoadingFees.setText(String.valueOf(totalLoading));
                        txtTotalAllFees.setText(String.valueOf(totalArrival + totalLoading));
                        lblTotalEarnings.setText(String.valueOf(unsortedTotal));
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

    }
//---------------------------------decided not to use this in order for the updatetable using localdate to be used---------------------------------------------------------//

    private void startDataListener() { //called sa initialize. everytime nay ma add, mu update automatic ang table
        /**
         * Naa ra ni sa documentation sa Firebase sa ila site adtos retrieving data for more specific and detailed info
         * use of DatabaseReference ref kay para asa siya na table mag kuha ug node/child/data
         * childEventListener kay maoy mag listen if naay child ( new data ) na add sa gi reference na table
         * datasnapshot is understandable na. pero if you want explanation, naa ra man nis documentation sa Firebase. I cant explain it well
         */
        DatabaseReference ref = database.child("Fees");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                /**
                 * referenced the bus classes. so naay listener sulod listener because
                 * once i-get the fee to be added sa table, I need a reference sa bus table para ma get nako
                 * ang corresponding bus (plate and type) to be added sa table
                 */
                Fee fee = dataSnapshot.getValue(Fee.class);
                DatabaseReference bref = database.child("Buses").child(fee.getBus_plate());
                bref.addListenerForSingleValueEvent(new ValueEventListener() { //functions just the same sa listener above pero lain lang reference (instead of Fees, Buses na na table)
                    @Override
                    public void onDataChange(DataSnapshot bussnapshot) {
                        Bus bus = bussnapshot.getValue(Bus.class);
                        fees.add(new FeeTable(fee,bus));
                        tableView.setItems(fees);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

                /**
                 * your methods/functions idk what you call them basta hahaha
                 * for some reasin di mu display ang total :(
                 */

                int totalArrival = 0, totalLoading = 0;
                int unsortedTotal = 0;
                ObservableList<FeeTable> feeList = tableView.getItems();
                for (FeeTable f : feeList) {
                    totalArrival += Integer.parseInt(f.getArrivalFee());
                    totalLoading += Integer.parseInt(f.getLoadingFee());
                }
                for (FeeTable f : fees) {
                    unsortedTotal += Integer.parseInt(f.getArrivalFee());
                    unsortedTotal += Integer.parseInt(f.getLoadingFee());
                }
                txtTotalArrivalFees.setText(String.valueOf(totalArrival));
                txtTotalLoadingFees.setText(String.valueOf(totalLoading));
                txtTotalAllFees.setText(String.valueOf(totalArrival + totalLoading));
                lblTotalEarnings.setText(String.valueOf(unsortedTotal));
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

}