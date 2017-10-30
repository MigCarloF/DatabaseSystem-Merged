
package com.database;

public class Bus {
    private String plateNo;
    private String company;
    private String busType;
    private boolean miniBus = false;
    private String busNumber;

    public Bus(String busNumber, String busType, String company, boolean miniBus, String plateNo){ //constructor added for Firebase
        this.plateNo = plateNo;
        this.company = company;
        this.miniBus = miniBus;
        this.busType = busType;
        this.busNumber = busNumber;
    }

    public Bus(String plateNo, String company, String busNumber) {
        this.plateNo = plateNo;
        this.company = company;
        this.miniBus = false;
        setBusType("bus");
        this.busNumber = busNumber;
    }
    public Bus(String plateNo, String company){
        this.plateNo = plateNo;
        this.company = company;
        this.miniBus = true;
        setBusType("minibus");
        this.busNumber = "0";
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
    
    
    
    
}
