package com.cashier.ui;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLVoidRequestWindowController implements Initializable {
    //jimro
    @FXML
    private JFXButton cancel;

    @FXML
    private TextField busCompTextField1;

    @FXML
    private TextField DateTimeTextField1;

    @FXML
    private TextField arrivFeeTextField;

    @FXML
    private TextField plateTextField;

    @FXML
    private TextField loadFeeTextField;

    @FXML
    private TextField orNoTextField;

    @FXML
    private TextField totalTextField;

    @FXML
    void cancelPressed(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        CashierMain.cancelPressed = true;
        stage.close();
    }

    @FXML
    void sendVoidPressed(ActionEvent event) {
        //INSERT what do
        Stage stage = (Stage) cancel.getScene().getWindow();
        CashierMain.cancelPressed = true;
        stage.close();
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        busCompTextField1.setText("CERES");
        DateTimeTextField1.setText("07/07/98    2:37 AM");
        arrivFeeTextField.setText("50");
        plateTextField.setText("ACS 2047");
        loadFeeTextField.setText("50");
        orNoTextField.setText("0000024");
        totalTextField.setText("100");
    }
}
