package com.cashier.ui;


import com.database.Fee;
import com.database.RangeOR;
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

import java.io.IOException;
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
    private TextField totalCashier;

    @FXML
    private TextField quantity1;

    @FXML
    private TextField quantity2;

    @FXML
    private TextField quantity3;

    @FXML
    private TextField quantity4;

    @FXML
    private TextField quantity5;

    @FXML
    private TextField quantity6;

    @FXML
    private TextField quantity7;

    @FXML
    private TextField quantity8;

    @FXML
    private TextField quantity9;

    @FXML
    private TextField amount1;

    @FXML
    private TextField amount2;

    @FXML
    private TextField amount3;

    @FXML
    private TextField amount4;

    @FXML
    private TextField amount5;

    @FXML
    private TextField amount6;

    @FXML
    private TextField amount7;

    @FXML
    private TextField amount8;

    @FXML
    private TextField amount9;

    @FXML
    private JFXButton voidButton;

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
    private int ORNUM;

    @FXML
    void busPrintButtonPressed(ActionEvent event) {
        noCheck.setText("");
        noPlate.setText("");

        String plateNo = plateNumber.getText();
        plateNo = plateNo.replaceAll("\\s","");//removes spaces

        if(plateNo.equals("")){
            //todo throw error nga empty
            noPlate.setText("* - Plate number cannot be empty");
            System.out.println("no input");
        }else{
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
                        if(snapshot.getChildrenCount() == 0){
                            //todo error here bus doesnt exist
                            noPlate.setText("* - bus  does not exist");
                            //noPlate.setText("* - bus with plate " + plateNum + " does not exist");
                            System.out.println("no bus");
                        }
                        else{
                            DatabaseReference nref = database.child("Range").child("current");
                            nref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    RangeOR range = snapshot.getValue(RangeOR.class);
                                    ORNUM = range.getCurrent();

                                    if(ORNUM > range.getEnd()){
                                        //todo throw error nga out of range na
                                    }else{
                                        Map<String, Object> newRange = new HashMap<>();
                                        newRange.put("current", ORNUM += 1);
                                        nref.updateChildren(newRange);

                                        DatabaseReference aref = database.child("Fees");
                                        Fee forDatabase = new Fee(hasArrival, hasLoading, dateFormat, "" + ORNUM, "Cashier 01", localDate, plateNum);
                                        aref.child(forDatabase.getOrNum()).setValue(forDatabase);
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

        /**
         *   CASH COUNT VARIABLES
         */
        String one = quantity1.getText();
        String two = quantity2.getText();
        String three = quantity3.getText();
        String four = quantity4.getText();
        String five = quantity5.getText();
        String six = quantity6.getText();
        String seven = quantity7.getText();
        String eight = quantity8.getText();
        String nine = quantity9.getText();

        amount1.setText("3000");
        amount2.setText("500");
        amount3.setText("200");
        amount4.setText("100");
        amount5.setText("50");
        amount6.setText("20");
        amount7.setText("10");
        amount8.setText("5");
        amount9.setText("1");

        totalCashier.setText("1886");

        currentORNumber.setText("000142");
        noCheck.setText("");
        noPlate.setText("");
        database = FirebaseDatabase.getInstance().getReference();

    }
}