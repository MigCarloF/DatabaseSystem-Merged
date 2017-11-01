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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLRecordsWindowController implements Initializable {

    @FXML
    private JFXButton recordsAdminButton;

    @FXML
    private JFXButton recordsLogoutButton;

    @FXML
    private JFXButton busProfilesCreateProfileButton;

    @FXML
    private ComboBox recordsMenu;

    @FXML
    private JFXButton recordsGoButton;

    @FXML
    void busProfilesCreateProfilePressed(ActionEvent event) {

    }

    @FXML
    void recordsAdminButton(ActionEvent event) {

    }

    @FXML
    void recordsGoButtonPressed(ActionEvent event) throws IOException {
        if(recordsMenu.getValue().equals("CURRENT")) {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLCurrentWindow.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } else if(recordsMenu.getValue().equals("RECORDS")) {
            /**
             * Do nothing
             */
        } else if(recordsMenu.getValue().equals("VOID REQUESTS")) {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLAdminVoidRequestsWindow.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } else if(recordsMenu.getValue().equals("BUS PROFILES")) {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLBusProfiles.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }
    }

    @FXML
    void recordsLogoutButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../../../../resources/LoginFormLayout.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void initialize(URL url, ResourceBundle rb) {
        /**
         * This is to avoid a null pointer exception thrown by instantly pressing GO again right after changing
         */
        recordsMenu.setValue(("RECORDS"));
        /**
         * This part is for the initialization of the Combo Box.
         * TODO: Every item in the menu when chosen, another scene will be
         * opened to the item's corresponding scene (change scene/stage).
         */

        recordsMenu.getItems().addAll("CURRENT", "RECORDS", "VOID REQUESTS", "BUS PROFILES");
        recordsMenu.setVisibleRowCount(4);
        recordsMenu.setEditable(false);
        recordsMenu.setPromptText("RECORDS");
    }
}
