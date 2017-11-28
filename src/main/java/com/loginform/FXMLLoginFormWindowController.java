package com.loginform;

import com.database.Employee;
import com.database.SingletonLogin;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLLoginFormWindowController implements Initializable{
    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private JFXButton logIn;

    private DatabaseReference database;
    private ObservableList<Employee> employees;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        employees = FXCollections.observableArrayList();
        database = FirebaseDatabase.getInstance().getReference();

        //todo store password and username
        DatabaseReference ref = database.child("Employees");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    Employee employee = snap.getValue(Employee.class);
                    employees.add(employee);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    @FXML
    void logInButtonPushed(ActionEvent event) throws IOException {
        String user = usernameTextField.getText();
        String pass = passwordTextField.getText();
        boolean flag = false;
        System.out.println(employees.size());
        for(Employee e : employees){
            if(e.getUsername().equals(user)){
                if(e.getPassword().equals(pass)){
                    flag = true;
                    SingletonLogin.getInstance().setCurrentLogin(e.getUsername());
                    if(e.getWorkType().equals("CASHIER")){
                        System.out.println("cash");
                        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLMainCashierWindow.fxml"));
                        Scene tableViewScene = new Scene(tableViewParent);

                        //This line gets the Stage information
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        window.setScene(tableViewScene);
                        window.show();
                    }
                    if(e.getWorkType().equals("ADMIN")){
                        System.out.println("Admin");
                        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLCurrentWindow.fxml"));
                        Scene tableViewScene = new Scene(tableViewParent);

                        //This line gets the Stage information
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        window.setScene(tableViewScene);
                        window.show();
                    }
                }else{
                    //todo password dont match
                }
            }
        }
        if(!flag){
            //todo doesnt match
        }


    }

}
