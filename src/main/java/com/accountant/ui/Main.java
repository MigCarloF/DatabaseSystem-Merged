package com.accountant.ui;

import com.database.FirebaseDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/accountant-ui.fxml"));
        primaryStage.setTitle("Accountant");
        primaryStage.setScene(new Scene(root, 853, 593));
        //primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        FirebaseDB.initFirebase(); //ari i-call ang initialize sa firebase;
        launch(args);
    }
}