package com.example.bike_service;

public class prview {//property basics

    private String date;
    private String bill;
    private String amount;



    //constructor
    public prview( String date, String bill, String amount){

        this.date = date;
        this.bill = bill;
        this.amount = amount;

    }

    //getters
    public String getDate() { return date; }
    public String getBill() {return bill; }

    public String getAmount() {return amount; }

}
