package com.database;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class FeeTable {

    private SimpleStringProperty arrivalFee, loadingFee, timePaid,  orNum, emploeeID;
    private SimpleStringProperty busCompany, busType, plateNo;
    private LocalDate date;
    private Bus bus;

    public FeeTable(Fee fee, Bus bus) { //constructor added. because I cant call Firebase functions here,
                                        //I decided adding bus to the constructor instead
        this.arrivalFee = new SimpleStringProperty(fee.getArrivalFee());
        this.loadingFee = new SimpleStringProperty(fee.getLoadingFee());
        this.timePaid = new SimpleStringProperty(fee.getTimePaid());
        this.date = LocalDate.parse(fee.getDatePaid());
        this.orNum = new SimpleStringProperty(fee.getOrNum());
        this.emploeeID = new SimpleStringProperty(fee.getEmployeeID());
        this.plateNo = new SimpleStringProperty(fee.getBus_plate());
        this.bus = bus;
        busCompany = new SimpleStringProperty(bus.getCompany());
        busType = new SimpleStringProperty(bus.getBusType());
    }

    public FeeTable(Fee fee) {
        this.arrivalFee = new SimpleStringProperty(fee.getArrivalFee());
        this.loadingFee = new SimpleStringProperty(fee.getLoadingFee());
        this.timePaid = new SimpleStringProperty(fee.getTimePaid());
        this.date = LocalDate.parse(fee.getDatePaid());
        this.orNum = new SimpleStringProperty(fee.getOrNum());
        this.emploeeID = new SimpleStringProperty(fee.getEmployeeID());
        this.plateNo = new SimpleStringProperty(fee.getBus_plate());
        initBus();
    }

    /**
     * retrieve bus info from plate number
     */
    private void initBus() {
        Database database = Database.database;

        bus = database.getBus("plateNo", getPlateNo()).get(0);
        busCompany = new SimpleStringProperty(bus.getCompany());
        busType = new SimpleStringProperty(bus.getBusType());
    }


    /**
     * getters and setters
     */
    public String getArrivalFee() {
        return arrivalFee.get();
    }

    public SimpleStringProperty arrivalFeeProperty() {
        return arrivalFee;
    }

    public void setArrivalFee(String arrivalFee) {
        this.arrivalFee.set(arrivalFee);
    }

    public String getLoadingFee() {
        return loadingFee.get();
    }

    public SimpleStringProperty loadingFeeProperty() {
        return loadingFee;
    }

    public void setLoadingFee(String loadingFee) {
        this.loadingFee.set(loadingFee);
    }

    public String getTimePaid() {
        return timePaid.get();
    }

    public SimpleStringProperty timePaidProperty() {
        return timePaid;
    }

    public void setTimePaid(String timePaid) {
        this.timePaid.set(timePaid);
    }

    public String getOrNum() {
        return orNum.get();
    }

    public SimpleStringProperty orNumProperty() {
        return orNum;
    }

    public void setOrNum(String orNum) {
        this.orNum.set(orNum);
    }

    public String getEmploeeID() {
        return emploeeID.get();
    }

    public SimpleStringProperty emploeeIDProperty() {
        return emploeeID;
    }

    public void setEmploeeID(String emploeeID) {
        this.emploeeID.set(emploeeID);
    }

    public String getBusCompany() {
        return busCompany.get();
    }

    public SimpleStringProperty busCompanyProperty() {
        return busCompany;
    }

    public void setBusCompany(String busCompany) {
        this.busCompany.set(busCompany);
    }

    public String getBusType() {
        return busType.get();
    }

    public SimpleStringProperty busTypeProperty() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType.set(busType);
    }

    public String getPlateNo() {
        return plateNo.get();
    }

    public SimpleStringProperty plateNoProperty() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo.set(plateNo);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
