/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.ui;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLCreateAccountController implements Initializable {
    //BRANDON!!!!! kani tanan haha
    @FXML
    private JFXButton createAccountCancelButton;

    @FXML
    private JFXButton createAccountCreateButton;

    @FXML
    private TextField createAccountUsername;

    @FXML
    private TextField createAccountPassword;

    @FXML
    private ComboBox createAccountType;

    @FXML
    void createAccountCancelPressed(ActionEvent event) {
        Stage stage = (Stage) createAccountCancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void createProfileCreatePressed(ActionEvent event) {
        String username = createAccountUsername.getText();
        String password = createAccountPassword.getText();
        String accountType = createAccountType.getValue().toString();

        System.out.println("Username: " + username + "\nPassword: " + password + "\nAccount type: " + accountType);

        // closes the window
        Stage stage = (Stage) createAccountCreateButton.getScene().getWindow();
        stage.close();
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createAccountType.getItems().addAll("ADMIN", "ACCOUNTANT", "CASHIER");
        createAccountType.setVisibleRowCount(3);
        createAccountType.setEditable(false);
        createAccountType.setPromptText("ACCOUNT TYPE");
    }    
    
}
