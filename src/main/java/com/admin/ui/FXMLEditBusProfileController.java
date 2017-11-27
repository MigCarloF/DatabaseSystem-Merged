package com.admin.ui;

import com.database.Bus;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLEditBusProfileController implements Initializable {

    @FXML
    private JFXButton editProfilesCancelButton;

    @FXML
    private JFXButton editProfilesEditButton;

    @FXML
    private JFXButton editProfilesDeleteButton;

    @FXML
    private TextField editProfilesAccountNo;

    @FXML
    private TextField editProfilesCPerson;

    @FXML
    private TextField editProfilesCNumber;

    @FXML
    private TextField editProfilesFranchise;

    @FXML
    private TextField editProfilesPlateNo;

    @FXML
    private TextField editProfilesBusNo;

    @FXML
    private TextField editProfilesSize;

    @FXML
    private TextField editProfilesCapacity;

    @FXML
    private TextField editProfilesType;

    @FXML
    private TextField editProfilesRoute;

    @FXML
    private TextField editProfilesFare;

    private Bus busToEdit;
    private DatabaseReference database;
    private ChildEventListener childEventListener;
    private Boolean errorFound = false;
    private String errorStatus = "";
    private Alert alert = new Alert(Alert.AlertType.ERROR);

    @FXML
    void editProfilesCancelPressed(ActionEvent event) {
        Stage stage = (Stage) editProfilesCancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void editProfilesDeletePressed(ActionEvent event) { //TODO: update this to suit the updated bus class
        //DELETE SA DATABASE
        DatabaseReference ref = database.child("Buses");
        ref.child(busToEdit.getPlateNo()).setValue(null);
    }

    @FXML
    void editProfilesEditPressed(ActionEvent event) {
        String contactPerson = editProfilesCPerson.getText();
        String contactNumber = editProfilesCNumber.getText();
        String franchise = editProfilesFranchise.getText();
        String plateNumber = editProfilesPlateNo.getText();
        String busNumber = editProfilesBusNo.getText();
        String size = editProfilesSize.getText();
        String capacity = editProfilesCapacity.getText();
        String type = editProfilesType.getText();
        String route = editProfilesRoute.getText();
        String fare = editProfilesFare.getText();
        Boolean minibus = true;
        if(size.equals("bus")){
            minibus = false;
        }

        if(contactPerson.equals("") || contactNumber.equals("") || franchise.equals("") || plateNumber.equals("") || busNumber.equals("")
                || capacity.equals("") || fare.equals("")){
            alert.setTitle("INCOMPLETE DATA");
            alert.setHeaderText("");
            alert.setContentText("Please fill in all the data needed.");
            alert.showAndWait();
        }else {
            DatabaseReference ref = database.child("Buses");
            Bus bus = new Bus(busNumber, size, franchise, minibus, plateNumber, contactPerson, contactNumber, type, route, capacity, fare, true,"");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.hasChild(plateNumber)) { // check if plate number already exists
                        setError(true);
                        errorStatus = "Plate_number";
                    }
                    ref.child(franchise).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) { // check if a bus in the same company has an existing
                            if(snapshot.hasChild(busNumber)){             // bus number equal to "busNumber"
                                setError(true);
                                errorStatus = "Franchise_Bus_number";
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError error2) {}
                    });

                    if(!errorFound) {
                        ref.child(busToEdit.getPlateNo()).setValue(null);
                        ref.child(plateNumber).setValue(bus);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {}
            });
        }
        if(!errorFound){
            Stage stage = (Stage) editProfilesCancelButton.getScene().getWindow();
            stage.close();
        }else{
            if(errorStatus.equals("Plate_number")){
                System.out.println("Plate number already exists.");
                alert.setTitle("E R R O R");
                alert.setHeaderText("A bus with the plate number " + plateNumber + " already exists.");
                alert.setContentText("Please enter another plate number.");
                alert.showAndWait();
            }else if(errorStatus.equals("Franchise_Bus_number")){
                System.out.println("A " + franchise + " Bus with the " + busNumber + " number already exists.");
                alert.setTitle("E R R O R");
                alert.setHeaderText("A " + franchise + " Bus with the " + busNumber + " number already exists.");
                alert.setContentText("Please enter another bus company or number.");
                alert.showAndWait();
            }
        }
    }

    private void setError(Boolean status){
        errorFound = status;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        database = FirebaseDatabase.getInstance().getReference();

        busToEdit = SingletonEditBus.getInstance().getBus();

        editProfilesAccountNo.setText("");  //the account number of the bus profile selected

        editProfilesCPerson.setText(busToEdit.getContactPerson());
        editProfilesCNumber.setText(busToEdit.getContactNumber());
        editProfilesFranchise.setText(busToEdit.getCompany());
        editProfilesPlateNo.setText(busToEdit.getPlateNo());
        editProfilesBusNo.setText(busToEdit.getBusNumber());
        editProfilesSize.setText(busToEdit.getBusSize());
        editProfilesCapacity.setText(busToEdit.getBusCapacity());
        editProfilesType.setText(busToEdit.getBusType());
        editProfilesRoute.setText(busToEdit.getBusRoute());
        editProfilesFare.setText(busToEdit.getBusFare());
    }
}