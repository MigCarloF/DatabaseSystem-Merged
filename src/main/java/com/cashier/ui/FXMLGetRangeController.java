package com.cashier.ui;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLGetRangeController implements Initializable {
    Stage anotherStage = new Stage();

    @FXML
    private TextField lowRange;

    @FXML
    private TextField highRange;

    @FXML
    private JFXButton enter;

    @FXML
    private JFXButton cancel;

    @FXML
    void cancelPressed(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancel.getScene().getWindow();
        CashierMain.cancelPressed = true;
        stage.close();
    }

    @FXML
    void enterPressed(ActionEvent event) throws IOException {
        if(lowRange.equals("") || highRange.equals("")) {
            //todo error checking here
        } else{
            String low = lowRange.getText();
            String high = highRange.getText();

            String original = "050";
            int valueLow  = Integer.parseInt( low, 10);
            int valueHigh = Integer.parseInt(high, 10);

            //FXMLMainCashierWindowController.setRange(valueLow, valueHigh, true);
            //close void window then open void request window
            Stage stage = (Stage) cancel.getScene().getWindow();
            CashierMain.cancelPressed = true;
            stage.close();
        }
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {

    }

}
