package com.example.bike_service;

public class svaview {  private String date;
    private String name;
    private String mobile;
    private String vehicle;



    //constructor
    public svaview( String date, String name, String mobile, String vehicle){

        this.date = date;
        this.name = name;
        this.mobile = mobile;
        this.vehicle = vehicle;

    }

    //getters
    public String getDate() { return date; }
    public String getName() {return name; }

    public String getMobile() {return mobile; }
    public String getVehicle() {return vehicle; }

}