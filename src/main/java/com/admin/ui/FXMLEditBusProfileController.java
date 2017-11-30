package com.admin.ui;

import com.database.Bus;
import com.database.Employee;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class FXMLEditBusProfileController implements Initializable {

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton editButton;

    @FXML
    private JFXButton delete;

    @FXML
    private TextField contactPerson;

    @FXML
    private TextField contactNumber;

    @FXML
    private TextField franchise;

    @FXML
    private TextField plateNo;

    @FXML
    private TextField route;

    @FXML
    private TextField type;

    @FXML
    private TextField size;

    @FXML
    private TextField rfid;

    @FXML
    private Label contactPersonErr;

    @FXML
    private Label contactNumberErr;

    @FXML
    private Label franchiseErr;

    @FXML
    private Label plateNoErr;

    @FXML
    private Label routeErr;

    @FXML
    private Label typeErr;

    @FXML
    private Label sizeErr;

    @FXML
    private Label rfidErr;


    private Bus busToEdit;
    private DatabaseReference database;

    private Boolean errorFound = false;
    private String errorStatus = "";
    private Alert alert = new Alert(Alert.AlertType.ERROR);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        database = FirebaseDatabase.getInstance().getReference();
        busToEdit = SingletonEditBus.getInstance().getBus();

        contactPerson.setText(busToEdit.getContactPerson());
        contactNumber.setText(busToEdit.getContactNumber());
        franchise.setText(busToEdit.getCompany());
        plateNo.setText(busToEdit.getPlateNo());
        route.setText(busToEdit.getBusRoute());
        type.setText(busToEdit.getBusType());
        size.setText(busToEdit.getBusSize());
        rfid.setText(busToEdit.getRfid());
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deleteButtonPressed(ActionEvent event) {
        String contactPersonText = contactPerson.getText();
        String contactNumberText = contactNumber.getText();
        String franchiseText = franchise.getText();
        String plateNoText = plateNo.getText();
        String routeText = route.getText();
        String typeText = type.getText();
        String sizeText = size.getText();
        String RFIDText = rfid.getText();

        if(contactNumberText.equals("") || contactPersonText.equals("") || franchiseText.equals("") ||
                plateNoText.equals("") || routeText.equals("") || typeText.equals("") || sizeText.equals("") ||
                RFIDText.equals("")) {
            //todo throw error nga empty
        }

        DatabaseReference ref = database.child("Buses");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map<String, Object> newEmployee = new HashMap<>();
                newEmployee.put("activeBus", true);
                ref.child(plateNoText).updateChildren(newEmployee);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Stage stage = (Stage) delete.getScene().getWindow();
        stage.close();
    }

    @FXML
    void editButtonPressed(ActionEvent event) {
        String contactPersonText = contactPerson.getText();
        String contactNumberText = contactNumber.getText();
        String franchiseText = franchise.getText();
        String plateNoText = plateNo.getText();
        String routeText = route.getText();
        String typeText = type.getText();
        String sizeText = size.getText();
        String RFIDText = rfid.getText();

        if(contactNumberText.equals("") || contactPersonText.equals("") || franchiseText.equals("") ||
                plateNoText.equals("") || routeText.equals("") || typeText.equals("") || sizeText.equals("") ||
                RFIDText.equals("")) {
            //todo throw error nga empty
        }

        DatabaseReference ref = database.child("Buses");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(plateNoText.equals(busToEdit.getPlateNo())){
                    Map<String, Object> newBus = new HashMap<>();
                    newBus.put("plateNo", plateNoText);
                    newBus.put("franchise", franchiseText);
                    newBus.put("contactPerson", contactPersonText);
                    newBus.put("contactNumber", contactNumberText);
                    newBus.put("busType", typeText);
                    newBus.put("busSize", sizeText);
                    newBus.put("busRoute", routeText);
                    newBus.put("rfid",RFIDText);

                    ref.child(plateNoText).updateChildren(newBus);
                }else {
                    if(snapshot.hasChild(plateNoText)){
                        //todo throw user exist
                        //System.out.println("geee");
                    }else {
                        System.out.println("hello");
                        boolean isbus = true;
                        if(sizeText.equals("MINIBUS")){
                            isbus = false;
                        }
                        Bus e = new Bus("",sizeText, franchiseText, isbus, plateNoText,contactPersonText,
                                contactNumberText, typeText, routeText, "","",true, RFIDText);
                        ref.child(busToEdit.getPlateNo()).setValue(null);
                        ref.child(plateNoText).setValue(e);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        // closes the window
        Stage stage = (Stage) editButton.getScene().getWindow();
        stage.close();
    }
}