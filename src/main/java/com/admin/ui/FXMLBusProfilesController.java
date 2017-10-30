/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.ui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author alboresallyssa
 */
public class FXMLBusProfilesController implements Initializable {
    
    @FXML
    private JFXButton busProfilesAdminButton;

    @FXML
    private JFXButton busProfilesLogoutButton;

    @FXML
    private JFXTreeTableView<BusProfile> busProfilesTable;
    
    @FXML
    private JFXButton busProfilesCreateProfileButton;
    
    @FXML
    private JFXComboBox busProfilesMenu;
    
    @FXML
    private JFXButton busProfilesGoButton;

    @FXML
    void busProfilesAdminButton(ActionEvent event) {

    }

    @FXML
    void busProfilesLogoutButtonPressed(ActionEvent event) {

    }
    
    @FXML
    void busProfilesCreateProfilePressed(ActionEvent event) {

    }
    
    @FXML
    void busProfilesGoButtonPressed(ActionEvent event) throws IOException {
        if(busProfilesMenu.getValue().equals("CURRENT")) {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLCurrentWindow.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } else if(busProfilesMenu.getValue().equals("RECORDS")) {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLRecordsWindow.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } else if(busProfilesMenu.getValue().equals("VOID REQUESTS")) {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLAdminVoidRequestsWindow.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } else if(busProfilesMenu.getValue().equals("BUS PROFILES")) {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLBusProfiles.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * This part will be used to initialize the tree table view.
         * TODO: Create profile window
         *       Delete and edit a bus profile (with the database)
         */
        
        JFXTreeTableColumn<BusProfile, String> franchise0 = new JFXTreeTableColumn<>("FRANCHISE");
        franchise0.setPrefWidth(95);
        franchise0.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BusProfile, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BusProfile, String> param) {
                return param.getValue().getValue().franchise;
            }
        });
        
        JFXTreeTableColumn<BusProfile, String> contactPerson0 = new JFXTreeTableColumn<>("CONTACT PERSON");
        contactPerson0.setPrefWidth(149);
        contactPerson0.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BusProfile, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BusProfile, String> param) {
                return param.getValue().getValue().contactPerson;
            }
        });
        
        JFXTreeTableColumn<BusProfile, String> contactNum0 = new JFXTreeTableColumn<>("CONTACT NUMBER");
        contactNum0.setPrefWidth(135);
        contactNum0.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BusProfile, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BusProfile, String> param) {
                return param.getValue().getValue().contactNum;
            }
        });
        
        JFXTreeTableColumn<BusProfile, String> plateNum0 = new JFXTreeTableColumn<>("PLATE#");
        plateNum0.setPrefWidth(85);
        plateNum0.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BusProfile, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BusProfile, String> param) {
                return param.getValue().getValue().plateNum;
            }
        });
        
        JFXTreeTableColumn<BusProfile, String> busSize0 = new JFXTreeTableColumn<>("SIZE");
        busSize0.setPrefWidth(90);
        busSize0.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BusProfile, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BusProfile, String> param) {
                return param.getValue().getValue().busSize;
            }
        });
        
        JFXTreeTableColumn<BusProfile, String> route0 = new JFXTreeTableColumn<>("ROUTE");
        route0.setPrefWidth(115);
        route0.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BusProfile, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BusProfile, String> param) {
                return param.getValue().getValue().route;
            }
        });
        
        JFXTreeTableColumn<BusProfile, String> busType0 = new JFXTreeTableColumn<>("TYPE");
        busType0.setPrefWidth(100);
        busType0.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BusProfile, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BusProfile, String> param) {
                return param.getValue().getValue().busType;
            }
        });
        
        JFXTreeTableColumn<BusProfile, String> capacity0 = new JFXTreeTableColumn<>("CAPACITY");
        capacity0.setPrefWidth(80);
        capacity0.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BusProfile, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BusProfile, String> param) {
                return param.getValue().getValue().capacity;
            }
        });
        
        JFXTreeTableColumn<BusProfile, String> fare0 = new JFXTreeTableColumn<>("FARE");
        fare0.setPrefWidth(60);
        fare0.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BusProfile, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BusProfile, String> param) {
                return param.getValue().getValue().fare;
            }
        });
        
        /**
         *  A create profile window will be made and the data 
         *  inputted in the window will be sent to the database
         *  and the table.
         *  Below are just sample profiles:
         */
        
        ObservableList<BusProfile> busProfile = FXCollections.observableArrayList();
        busProfile.add(new BusProfile("CERES", "Rodney E. Santarin", "09569081042", "ABC 123", "regular", 
            "Toledo", "aircon", "65", "160"));
        busProfile.add(new BusProfile("CERES", "Rodney E. Santarin", "09569081042", "DEF 456", "regular", 
            "Pinamungajan", "non-aircon", "56", "75"));
        busProfile.add(new BusProfile("CERES", "Rodney E. Santarin", "09569081042", "GHI 789", "minibus", 
            "Balamban", "aircon", "50", "85"));
        busProfile.add(new BusProfile("SUNRAYS", "Rey Figues", "09163204123", "KJL 101", "regular", 
            "Argao", "aircon", "53", "83"));
        busProfile.add(new BusProfile("JHADE", "Alison Nator", "09339772585", "MNO 112", "minibus", 
            "Bato/Oslob", "aircon", "53", "107"));
        
        final TreeItem<BusProfile> root = new RecursiveTreeItem<BusProfile>(busProfile, RecursiveTreeObject::getChildren);
        busProfilesTable.getColumns().setAll(franchise0, contactPerson0, contactNum0, plateNum0, busSize0, route0,
                busType0, capacity0, fare0);
        busProfilesTable.setRoot(root);
        busProfilesTable.setShowRoot(false);
        
        /**
         * This part is for the initialization of the Combo Box.
         * TODO: Every item in the menu when chosen, another scene will be 
         * opened to the item's corresponding scene (change scene/stage).
         */
        
        busProfilesMenu.getItems().addAll("CURRENT", "RECORDS", "VOID REQUESTS", "BUS PROFILES");
        busProfilesMenu.setVisibleRowCount(4);
        busProfilesMenu.setEditable(true);
        busProfilesMenu.setPromptText("BUS PROFILES");
    }

    public class BusProfile extends RecursiveTreeObject<BusProfile> {
        StringProperty fare;
        StringProperty capacity;
        StringProperty busType;
        StringProperty route;
        StringProperty busSize;
        StringProperty plateNum;
        StringProperty contactNum;
        StringProperty contactPerson;
        StringProperty franchise;
        
        // constructor for the bus profiles
        public BusProfile(String franchise, String contactPerson, String contactNum, 
                String plateNum, String busSize, String route, String busType, String capacity, String fare) {
            this.franchise = new SimpleStringProperty(franchise);
            this.contactPerson = new SimpleStringProperty(contactPerson);
            this.contactNum = new SimpleStringProperty(contactNum);
            this.plateNum = new SimpleStringProperty(plateNum);
            this.busSize = new SimpleStringProperty(busSize);
            this.route = new SimpleStringProperty(route);
            this.busType = new SimpleStringProperty(busType);
            this.capacity = new SimpleStringProperty(capacity);
            this.fare = new SimpleStringProperty(fare);
        }
    }
    
}
