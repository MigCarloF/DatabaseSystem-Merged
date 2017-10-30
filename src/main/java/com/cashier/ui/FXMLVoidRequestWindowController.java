package com.cashier.ui;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMLVoidRequestWindowController {

    @FXML
    private JFXButton voidRequestWindowCancel;

    @FXML
    private JFXButton voidRequestWindowSend;

    @FXML
    private Text voidRequestWindowText1;    //OR NO

    @FXML
    private Text voidRequestWindowText2;    //Requested by

    @FXML
    private Text voidRequestWindowText3;    //Reason for avoiding

    @FXML
    void voidRequestWindowCancelPressed(ActionEvent event) throws IOException {
        Stage stage = (Stage) voidRequestWindowCancel.getScene().getWindow();
        CashierMain.cancelPressed = true;
        stage.close();
    }

    @FXML
    void voidRequestWindowSendPressed(ActionEvent event) {

    }

}
