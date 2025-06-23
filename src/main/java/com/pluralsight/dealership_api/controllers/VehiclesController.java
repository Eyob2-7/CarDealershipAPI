package com.pluralsight.dealership_api.controllers;

import com.pluralsight.dealership_api.dao.VehicleDao;
import com.pluralsight.dealership_api.dealership.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehiclesController {

    private VehicleDao vehicleDao;

    @Autowired
    public VehiclesController(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    // CREATE
    @PostMapping
    public void addVehicle(@RequestBody Vehicle vehicle) {
        vehicleDao.add(vehicle);
    }

    // READ ALL
    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleDao.getAll();
    }

    // UPDATE by VIN
    @PutMapping("/{vin}")
    public void updateVehicle(@PathVariable String vin, @RequestBody Vehicle vehicle) {
        vehicle.setVin(vin); // Ensure VIN matches path
        vehicleDao.update(vehicle);

    }

    // DELETE by VIN
    @DeleteMapping("/{vin}")
    public void deleteVehicle(@PathVariable String vin) {
        vehicleDao.delete(vin);
    }

}
