package com.loginform;

import com.database.Employee;
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

    public static String password;
    public static String username;
    private static String role;

    // private Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLMainCashierWindow.fxml"));
    //private Parent tableViewParent2 = FXMLLoader.load(getClass().getResource("/FXMLCurrentWindow.fxml"));

    private DatabaseReference database;
    ObservableList<Employee> employees;

    public FXMLLoginFormWindowController() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        employees = FXCollections.observableArrayList();
        String user = usernameTextField.getText();
        String pass = passwordTextField.getText();
        password = "";
        username = "";
        role = "";
        database = FirebaseDatabase.getInstance().getReference();

        //todo store password and username
        DatabaseReference ref = database.child("Employees");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
//                if(snapshot.hasChild(user)){
//                    username = user;
//                    ref.child(user).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot snapshot) {
//                            Employee employee = snapshot.getValue(Employee.class);
//                            password = employee.getPassword();
//                            role = employee.getWorkType();
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError error) {
//
//                        }
//                    });
//                }else {
//                    //todo throw error nga password dont match
//                }
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

//        if(user.equals(username) && pass.equals(password)){
//            //todo switch to main page
//            if(role.equals("CASHIER")){
//                System.out.println("cash");
//                Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLMainCashierWindow.fxml"));
//                Scene tableViewScene = new Scene(tableViewParent);
//
//                //This line gets the Stage information
//                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//                window.setScene(tableViewScene);
//                window.show();
//            }
//            if(role.equals("ADMIN")){
//                System.out.println("Admin");
//                Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLMainAdminWindow.fxml"));
//                Scene tableViewScene = new Scene(tableViewParent);
//
//                //This line gets the Stage information
//                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//                window.setScene(tableViewScene);
//                window.show();
//            }
//        }else{
//            //todo error dont match
//        }



        //todo INPUT LOGIC HERE


//        DatabaseReference aref = database.child("CurrentLogin");




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

    private void openCashier(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLMainCashierWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    private void openAdmin(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLMainAdminWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
}
