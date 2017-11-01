package com.loginform;

import com.database.FirebaseDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginForm extends Application {

    public static void main(String[] args) throws IOException {
        FirebaseDB.initFirebase();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../../resources/LoginFormLayout.fxml"));
        primaryStage.setTitle("Login Form");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}
