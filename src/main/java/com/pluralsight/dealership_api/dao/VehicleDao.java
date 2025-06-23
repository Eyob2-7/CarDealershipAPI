package com.pluralsight.dealership_api.dao;

import com.pluralsight.dealership_api.dealership.Vehicle;

import java.util.List;

public interface VehicleDao {
    List<Vehicle> getAll();
    void add(Vehicle vehicle);
    void update(Vehicle vehicle);
    void delete(String vin);
}
