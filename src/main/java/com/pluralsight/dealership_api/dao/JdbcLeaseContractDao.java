package com.pluralsight.dealership_api.dao;

import com.pluralsight.dealership_api.models.LeaseContract;
import com.pluralsight.dealership_api.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO implementation for LeaseContract.
 * Handles reading from and writing to the lease_contracts table.
 */

@Component
public class JdbcLeaseContractDao implements LeaseContractDao {

    private final DataSource dataSource;

    @Autowired
    public JdbcLeaseContractDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Retrieves a LeaseContract by its unique ID.
     * This query joins the lease_contracts and vehicles tables to build the full object.
     *
     * @param id the lease_contract_id
     * @return a LeaseContract object, or null if not found
     */

    @Override
    public LeaseContract getById(int id) {

        String sql = """
                
                    SELECT lc.*, v.year, v.make, v.model, v.vehicle_type, v.color, v.odometer, v.price
                
                    FROM lease_contracts lc
                
                    JOIN vehicles v ON lc.vin = v.vin
                
                    WHERE lc.lease_contract_id = ?
                
                """;

        try (
                Connection conn = dataSource.getConnection();

                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                // Construct the Vehicle object from the result
                Vehicle vehicle = new Vehicle(

                        rs.getString("vin"),
                        rs.getInt("year"),
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getString("vehicle_type"),
                        rs.getString("color"),
                        rs.getInt("odometer"),
                        rs.getDouble("price")

                );

                // Create and return the LeaseContract object
                return new LeaseContract(
                        rs.getString("date"),
                        rs.getString("customer_name"),
                        rs.getString("customer_email"),
                        vehicle
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Inserts a new LeaseContract into the lease_contracts table.
     * Expected ending value and lease fee are pre-calculated in the model.
     *
     * @param contract the LeaseContract to save
     */

    @Override
    public void add(LeaseContract contract) {

        String sql = """
                
                    INSERT INTO lease_contracts
                
                    (vin, date, customer_name, customer_email, expected_ending_value, lease_fee)
                
                    VALUES (?, ?, ?, ?, ?, ?)
                
                    """;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            // Set all required parameters from the contract object
            stmt.setString(1, contract.getVehicle().getVin());
            stmt.setString(2, contract.getDate());
            stmt.setString(3, contract.getCustomerName());
            stmt.setString(4, contract.getCustomerEmail());
            stmt.setDouble(5, contract.getExpectedEndingValue());
            stmt.setDouble(6, contract.getLeaseFee());
            stmt.executeUpdate(); // Save the contract

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
