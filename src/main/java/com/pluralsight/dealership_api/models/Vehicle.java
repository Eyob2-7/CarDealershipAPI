package com.pluralsight.dealership_api.models;

public class Vehicle {
    // Fields that describe a vehicle
    private String vin;                // Unique identifier for the vehicle
    private int year;               // Year the vehicle was made
    private String make;            // Vehicle manufacturer
    private String model;           // Model name of the vehicle
    private String vehicleType;     // Category like SUV, Sedan, Truck, etc.
    private String color;           // Exterior color of the vehicle
    private int odometer;           // Mileage on the vehicle
    private double price;           // Price in dollars

    // Constructor: creates a vehicle using the values provided
    public Vehicle(String vin, int year, String make, String model,
                   String vehicleType, String color, int odometer, double price) {
        this.vin = vin;
        this.year = year;
        this.make = make;
        this.model = model;
        this.vehicleType = vehicleType;
        this.color = color;
        this.odometer = odometer;
        this.price = price;
    }

    // Get and set each field

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Converts all fields into a single line of text
    @Override
    public String toString() {
        return vin + " | " + year + " | " + make + " | " + model + " | " +
                vehicleType + " | " + color + " | " + odometer + " | $" + price;
    }
}

