package com.admin.ui;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLAdminVoidRequestsWindowController implements Initializable {

    @FXML
    private JFXButton adminVoidRequestsButton;

    @FXML
    private JFXButton adminVoidRequestsLogoutButton;

    @FXML
    private JFXButton busProfilesCreateProfileButton;

    @FXML
    private ComboBox adminVoidRequestsMenu;

    @FXML
    private JFXButton adminVoidRequestsGoButton;

    Stage createAccountStage = new Stage();

    @FXML
    void adminVoidRequestsButtonPressed(ActionEvent event) throws IOException {
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

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLAdminVoidRequestsWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();

        createAccountStage.show();
    }

    @FXML
    void adminVoidRequestsGoButtonPressed(ActionEvent event) throws IOException {
        if(adminVoidRequestsMenu.getValue().equals("CURRENT")) {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLCurrentWindow.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } else if(adminVoidRequestsMenu.getValue().equals("RECORDS")) {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLRecordsWindow.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } else if(adminVoidRequestsMenu.getValue().equals("VOID REQUESTS")) {
            /**
             * Do nothing
             */
        } else if(adminVoidRequestsMenu.getValue().equals("BUS PROFILES")) {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLBusProfiles.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }
    }

    @FXML
    void adminVoidRequestsLogoutButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../../../../resources/LoginFormLayout.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    void adminVoidRequestButtonPressed(ActionEvent event) {

    }

    public void initialize(URL url, ResourceBundle rb) {
        /**
         * This is to avoid a null pointer exception thrown by instantly pressing GO again right after changing
         */
        adminVoidRequestsMenu.setValue(("VOID REQUESTS"));
        /**
         * This part is for the initialization of the Combo Box.
         * TODO: Every item in the menu when chosen, another scene will be
         * opened to the item's corresponding scene (change scene/stage).
         */

        adminVoidRequestsMenu.getItems().addAll("CURRENT", "RECORDS", "VOID REQUESTS", "BUS PROFILES");
        adminVoidRequestsMenu.setVisibleRowCount(4);
        adminVoidRequestsMenu.setEditable(false);
        adminVoidRequestsMenu.setPromptText("VOID REQUESTS");
    }

}

