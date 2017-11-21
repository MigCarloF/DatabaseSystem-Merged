package com.cashier.ui;

import com.database.FirebaseDB;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class FXMLVoidWindowController implements Initializable {
    Stage anotherStage = new Stage();
    String orNo = "";

    @FXML
    private JFXButton voidWindowCancel;

    @FXML
    private JFXButton voidWindowSendRequest;

    @FXML
    private TextField voidWindowOrNo;

    @FXML
    private Label noVoid;

    DatabaseReference database;

    @FXML
    void voidWindowCancelPressed(ActionEvent event) {
        Stage stage = (Stage) voidWindowCancel.getScene().getWindow();
        CashierMain.cancelPressed = true;
        stage.close();
    }

    @FXML
    void voidWindowSendRequestPressed(ActionEvent event) throws IOException {
        orNo = voidWindowOrNo.getText();
        orNo = orNo.replaceAll("\\s","");//removes spaces
        System.out.println(orNo);
        //todo checking if such or exists

        if(orNo == null || orNo.equals("")) {
            System.out.println("invalid");
            noVoid.setText("* - bus  does not exist");
        } else{
            noVoid.setText("");
//            DatabaseReference ref = database.child("Fees").child(orNo);
//            ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot snapshot) {
//                    if(snapshot.getChildrenCount() == 0){
//                        noVoid.setText("* - or  does not exist");
//                    }else{
//                        SingletonVoid.getInstance().setOrNo(orNo);
//                        FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLVoidRequestWindow.fxml"));
//                        Parent anotherRoot = anotherLoader.load();
//                        Scene anotherScene = new Scene(anotherRoot);
//                        anotherStage.setScene(anotherScene);
//                        anotherStage.initStyle(StageStyle.UNDECORATED); //removes the title bar of the window
//
//
//                        //close void window then open void request window
//                        Stage stage = (Stage) voidWindowCancel.getScene().getWindow();
//                        CashierMain.cancelPressed = true;
//                        stage.close();
//
//                        voidWindowVoidPressed(event);
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError error) {
//
//                }
//            });
            SingletonVoid.getInstance().setOrNo(orNo);
            FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLVoidRequestWindow.fxml"));
            Parent anotherRoot = anotherLoader.load();
            Scene anotherScene = new Scene(anotherRoot);
            anotherStage.setScene(anotherScene);
            anotherStage.initStyle(StageStyle.UNDECORATED); //removes the title bar of the window


            //close void window then open void request window
            Stage stage = (Stage) voidWindowCancel.getScene().getWindow();
            CashierMain.cancelPressed = true;
            stage.close();

            voidWindowVoidPressed(event);
        }
    }

    @FXML
    void voidWindowVoidPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLVoidRequestWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noVoid.setText("");
        database = FirebaseDatabase.getInstance().getReference();
    }
}
