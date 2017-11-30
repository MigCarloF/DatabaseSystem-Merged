package com.cashier.ui;

import com.database.Fee;
import com.database.RangeOR;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FXMLGetRangeController implements Initializable {
    Stage anotherStage = new Stage();

    @FXML
    private TextField lowRange;

    @FXML
    private TextField highRange;

    @FXML
    private Label lblError;

    @FXML
    private JFXButton cancel;

    private DatabaseReference database;
    private ArrayList<Fee> fees;
    private Lock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();

    @FXML
    void cancelPressed(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancel.getScene().getWindow();
        CashierMain.cancelPressed = true;
        stage.close();
    }

    private boolean lowFeeExists() {
        for(Fee f : fees) {
            int orInt = Integer.parseInt(f.getOrNum());
            if (f.getOrNum().equals(lowRange.getText())){
                return true;
            }
        }
        return false;
    }

    private boolean highFeeExists() {
        for (Fee f : fees) {
            int orInt = Integer.parseInt(f.getOrNum());
            if (f.getOrNum().equals(highRange.getText())) {
                return true;
            }
        }
        return false;
    }

    @FXML
    void enterPressed(ActionEvent event) throws IOException {
        String low = lowRange.getText();
        String high = highRange.getText();
        lblError.setText("Processing");
        low = low.replaceAll("\\s","");//removes spaces
        high = high.replaceAll("\\s","");
        if(low.equals("") || high.equals("")) {
            lblError.setText("* - Fill in both blanks");
        }
        else{
            //String original = "050";
            int valueLow  = Integer.parseInt(low, 10);
            int valueHigh = Integer.parseInt(high, 10);
            if(valueLow >= valueHigh){
                lblError.setText("* - Invalid Range");
            }else {
                DatabaseReference gatherFeesRef = database.child("Fees");
                gatherFeesRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                        lock.lock();

                        Fee fee = snapshot.getValue(Fee.class);
                        fees.add(fee);

                        cond.signal();
                        lock.unlock();
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
                lock.lock();
                try { //try block for lock catching
                    cond.await();
                    if(lowFeeExists()) {
                        lblError.setText("* - Low Range OR already exists");
                    } else if(highFeeExists()) {
                        lblError.setText("* - High Range OR already exists");
                    } else {
                        lblError.setText("");
                        DatabaseReference ref = database.child("Range");
                        RangeOR range = new RangeOR(valueLow, valueHigh);
                        ref.setValue(range);
                        //FXMLMainCashierWindowController.setRange(valueLow, valueHigh, true);
                        //close void window then open void request window
                        Stage stage = (Stage) cancel.getScene().getWindow();
                        CashierMain.cancelPressed = true;
                        stage.close();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }

        }
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        fees = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference();
        lblError.setText("");
    }

}
