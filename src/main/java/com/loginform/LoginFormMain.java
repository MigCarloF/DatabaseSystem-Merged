package com.loginform;

import com.database.FirebaseDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormMain extends Application {

    public static void main(String[] args) throws IOException {
        FirebaseDB.initFirebase();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLLoginFormWindow.fxml"));
        stage.setTitle("Cebu South Bus Terminal");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
