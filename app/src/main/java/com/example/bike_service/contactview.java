package com.example.bike_service;

public class contactview
{

    //property basics

    private String name;
    private String mobile;
    private String vehicle;
    private boolean checked;




    //constructor
    public contactview( String name, String mobile, String vehicle, boolean checked){

        this.name = name;
        this.mobile = mobile;
        this.vehicle = vehicle;
        this.checked = checked;

    }

    //getters
    public String getName() { return name; }
    public String getMobile() {return mobile; }
    public String getVehicle() {return vehicle; }
    public boolean getChecked(){return checked;}

    public void setChecked(boolean checked){this.checked=checked;}
}
