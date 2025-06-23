package com.pluralsight.dealership_api.dao;

import com.pluralsight.dealership_api.models.SalesContract;
import com.pluralsight.dealership_api.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component // Marks this class as a Spring-managed component (for dependency injection)

public class JdbcSalesContractDao implements SalesContractDao {
    private final DataSource dataSource;

    // Constructor-based dependency injection of DataSource
    @Autowired
    public JdbcSalesContractDao(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    /**
     * Retrieves a SalesContract by its ID.
     * <p>
     * This method joins the sales_contracts and vehicles tables so we can build the full Vehicle object too.
     */

    @Override
    public SalesContract getById(int id) {

        String sql = """
                
                    SELECT sc.*, v.year, v.make, v.model, v.vehicle_type, v.color, v.odometer, v.price
                
                    FROM sales_contracts sc
                
                    JOIN vehicles v ON sc.vin = v.vin
                
                    WHERE sc.sales_contract_id = ?
                
                """;

        try (
                Connection conn = dataSource.getConnection(); // Open connection to DB
                PreparedStatement stmt = conn.prepareStatement(sql) // Prepare SQL query
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery(); // Execute query
            if (rs.next()) {
                // Build Vehicle object using data from joined result
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

                // Build and return the SalesContract object
                return new SalesContract(

                        rs.getString("date"),
                        rs.getString("customer_name"),
                        rs.getString("customer_email"),

                        vehicle,
                        rs.getBoolean("is_financed")

                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no match is found
    }

    /**
     * Adds a new SalesContract to the database.
     * All fees and tax amounts are pre-calculated by the SalesContract model.
     */

    @Override
    public void add(SalesContract contract) {
        String sql = """
                
                    INSERT INTO sales_contracts 
                
                    (vin, date, customer_name, customer_email, sales_tax_amount, recording_fee, processing_fee, is_financed)
                
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                
                """;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            // Set parameters for INSERT query
            stmt.setString(1, contract.getVehicle().getVin());
            stmt.setString(2, contract.getDate());
            stmt.setString(3, contract.getCustomerName());
            stmt.setString(4, contract.getCustomerEmail());
            stmt.setDouble(5, contract.getSalesTaxAmount());
            stmt.setDouble(6, contract.getRecordingFee());
            stmt.setDouble(7, contract.getProcessingFee());
            stmt.setBoolean(8, contract.isFinanced());

            stmt.executeUpdate(); // Execute INSERT

        } catch (SQLException e) {
            e.printStackTrace(); // Handle errors
        }
    }
}
