package com.loginform;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLLoginFormWindowController {
    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private JFXButton logIn;


    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void logInButtonPushed(ActionEvent event) throws IOException {
        usernameTextField.getText();
        passwordTextField.getText();
        //INPUT LOGIC HERE

        /**
         *  if cashier, kay
         */

        /*Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLMainCashierWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();*/

        /**
         *  if admin, kay
         */

        /*Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLMainAdminWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();*/
    }
}
