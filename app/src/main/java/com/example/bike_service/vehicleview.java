package com.example.bike_service;



public class vehicleview {

    //property basics

    private String name;
    private String mobile;
    private String vehicle;
    private String date;



    //constructor
    public vehicleview( String name, String mobile, String vehicle, String date){

        this.name = name;
        this.mobile = mobile;
        this.vehicle = vehicle;
        this.date = date;

    }

    //getters
    public String getName() { return name; }
    public String getMobile() {return mobile; }
    public String getVehicle() {return vehicle; }
    public String getDate() {return date; }

}
