package com.loginform;

import com.database.Employee;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
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

    private Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLMainCashierWindow.fxml"));
    private Parent tableViewParent2 = FXMLLoader.load(getClass().getResource("/FXMLCurrentWindow.fxml"));

    private DatabaseReference database;

    public FXMLLoginFormWindowController() throws IOException {
    }


    public void initialize(URL url, ResourceBundle rb) {
        database = FirebaseDatabase.getInstance().getReference();
    }

    @FXML
    void logInButtonPushed(ActionEvent event) throws IOException {
        String user = usernameTextField.getText();
        String pass = passwordTextField.getText();
        //todo INPUT LOGIC HERE


        DatabaseReference aref = database.child("CurrentLogin");


        DatabaseReference ref = database.child("Employees");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.hasChild(user)){
                    ref.child(user).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            Employee employee = snapshot.getValue(Employee.class);
                            if(employee.getPassword().equals(pass)){
                                if(employee.getWorkType().equals("CASHIER")){

                                    Scene tableViewScene = new Scene(tableViewParent);

                                    //This line gets the Stage information
                                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                                    window.setScene(tableViewScene);
                                    window.show();
                                }
                                if(employee.getWorkType().equals("ADMIN")){
                                    Scene tableViewScene = new Scene(tableViewParent2);

                                    //This line gets the Stage information
                                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                                    window.setScene(tableViewScene);
                                    window.show();
                                }
                            }else {
                                //todo throw error nga password dont match
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
                }else {
                    //todo throw error nga password dont match
                }
            }



            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


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
