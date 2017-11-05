
package com.database;

public class Bus {
    /**
     * bus size = bus/mini
     * capacity = no. of passengers
     * type = ac/ noac
     */
    private String busCapacity;
    private String busFare;
    private String busNumber;
    private String busRoute;
    private String busSize;
    private String busType;
    private String company;
    private String contactNumber;
    private String contactPerson;
    private boolean miniBus = false;
    private String plateNo;

    public Bus(String busNumber, String busSize, String company, boolean miniBus, String plateNo, String contactPerson,
               String contactNumber, String busType, String busRoute, String busCapacity, String busFare){ //constructor added for Firebase
        this.plateNo = plateNo;
        this.company = company;
        this.miniBus = miniBus;
        this.busSize = busSize;
        this.busNumber = busNumber;
        this.contactPerson = contactPerson;
        this.contactNumber = contactNumber;
        this.busType = busType;
        this.busRoute = busRoute;
        this.busCapacity = busCapacity;
        this.busFare = busFare;
    }

    public Bus(String plateNo, String company, String busNumber, String contactPerson,
               String contactNumber, String busType, String busRoute, String busCapacity, String busFare) {
        this.plateNo = plateNo;
        this.company = company;
        this.miniBus = false;
        setBusSize("bus");
        this.busNumber = busNumber;
        this.contactPerson = contactPerson;
        this.contactNumber = contactNumber;
        this.busType = busType;
        this.busRoute = busRoute;
        this.busCapacity = busCapacity;
        this.busFare = busFare;
    }
    public Bus(String plateNo, String company, String contactPerson, String contactNumber,
               String busType, String busRoute, String busCapacity, String busFare){
        this.plateNo = plateNo;
        this.company = company;
        this.miniBus = true;
        setBusSize("minibus");
        this.busNumber = "0";
        this.contactPerson = contactPerson;
        this.contactNumber = contactNumber;
        this.busType = busType;
        this.busRoute = busRoute;
        this.busCapacity = busCapacity;
        this.busFare = busFare;
    }


    public Bus(){

    }
//    public Bus(){
//        plateNo = null;
//        company = null;
//        busNumber = "0";
//    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getPlateNo(){
        return plateNo;
    }
    
    public void setPlateNo(String plateNo){
        this.plateNo = plateNo;
    }
    
    public String getCompany(){
        return company;
    }
    
    public void setCompany(String company){
        this.company = company;
    }
    
    public boolean isMiniBus(){
        return miniBus;
    }
    
    public void setMiniBus(boolean flag){
        miniBus = flag;
    }
    
    public String getBusNumber(){
        return busNumber;
    }
    
    public void setBusNumber(String busNumber){
        this.busNumber = busNumber;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getBusSize() {
        return busSize;
    }

    public void setBusSize(String busSize) {
        this.busSize = busSize;
    }

    public String getBusRoute() {
        return busRoute;
    }

    public void setBusRoute(String busRoute) {
        this.busRoute = busRoute;
    }

    public String getBusCapacity() {
        return busCapacity;
    }

    public void setBusCapacity(String busCapacity) {
        this.busCapacity = busCapacity;
    }

    public String getBusFare() {
        return busFare;
    }

    public void setBusFare(String busFare) {
        this.busFare = busFare;
    }
}
