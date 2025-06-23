package com.pluralsight.dealership_api.dao;

import com.pluralsight.dealership_api.dealership.Vehicle;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcVehicleDao implements VehicleDao {

    private DataSource dataSource;

    // Constructor injection
    public JdbcVehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Get all vehicles in the database
    @Override
    public List<Vehicle> getAll() {
        List<Vehicle> vehicles = new ArrayList<>();

        String sql = """
                
                SELECT * FROM vehicles
                
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                vehicles.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }


    // Add a new vehicle to the database
    @Override
    public void add(Vehicle vehicle) {
        String sql = """
                
                INSERT INTO vehicles (vin, year, make, model, vehicle_type, color, odometer, price)
                
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                
                """;

        try (Connection conn = dataSource.getConnection();

             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehicle.getVin());
            stmt.setInt(2, vehicle.getYear());
            stmt.setString(3, vehicle.getMake());
            stmt.setString(4, vehicle.getModel());
            stmt.setString(5, vehicle.getVehicleType());
            stmt.setString(6, vehicle.getColor());
            stmt.setInt(7, vehicle.getOdometer());
            stmt.setDouble(8, vehicle.getPrice());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Vehicle vehicle) {

        String sql = """
       UPDATE vehicles
       SET year = ?, make = ?, model = ?, vehicle_type = ?, color = ?, odometer = ?, price = ?
       WHERE vin = ?
       """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, vehicle.getYear());
            stmt.setString(2, vehicle.getMake());
            stmt.setString(3, vehicle.getModel());
            stmt.setString(4, vehicle.getVehicleType());
            stmt.setString(5, vehicle.getColor());
            stmt.setInt(6, vehicle.getOdometer());
            stmt.setDouble(7, vehicle.getPrice());
            stmt.setString(8, vehicle.getVin());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String vin) {

        String sql = """
                
                DELETE FROM vehicles
                
                WHERE vin = ?
                
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vin);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Helper method to convert a row from ResultSet to Vehicle object
    private Vehicle mapRow(ResultSet rs) throws SQLException {
        return new Vehicle(

                rs.getString("vin"),
                rs.getInt("year"),
                rs.getString("make"),
                rs.getString("model"),
                rs.getString("vehicle_type"),
                rs.getString("color"),
                rs.getInt("odometer"),
                rs.getDouble("price")
        );
    }
}
