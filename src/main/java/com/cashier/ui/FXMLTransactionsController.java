package com.cashier.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLTransactionsController implements Initializable {

    @FXML
    private Text cashierUserText;

    @FXML
    private JFXButton transactLogoutButton;

    @FXML
    private TableView transactionsTable;

    @FXML
    private TableColumn<?, ?> transactDate;

    @FXML
    private TableColumn<?, ?> transactTime;

    @FXML
    private TableColumn<?, ?> transactOR;

    @FXML
    private TableColumn<?, ?> transactFee;

    @FXML
    private TableColumn<?, ?> transactAmount;

    @FXML
    private TableColumn<?, ?> transactStatus;

    @FXML
    private TextField transactQuantityAF;

    @FXML
    private TextField transactQuantityLF;

    @FXML
    private TextField transactAmountAF;

    @FXML
    private TextField transactAmountLF;

    @FXML
    private TextField transactTotalRevenue;

    @FXML
    private JFXDatePicker transactDateFrom;

    @FXML
    private JFXDatePicker transactDateTo;

    @FXML
    private JFXButton transactBackButton;

    @FXML
    void transactLogoutButtonPressed(ActionEvent event) {

    }

    @FXML
    void transactBackButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLMainCashierWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cashierUserText.setText("Sir Joey");
        transactQuantityAF.setText("9");
        transactQuantityLF.setText("9");
        transactAmountAF.setText("450");
        transactAmountLF.setText("1050");
        transactTotalRevenue.setText("1600");



    }
}
