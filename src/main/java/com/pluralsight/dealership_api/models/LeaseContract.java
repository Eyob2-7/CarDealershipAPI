package com.pluralsight.dealership_api.models;

public class LeaseContract extends Contract {
    private double expectedEndingValue;
    private double leaseFee;

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicle) {
        super(date, customerName, customerEmail, vehicle);
        this.expectedEndingValue = vehicle.getPrice() * 0.5;
        this.leaseFee = vehicle.getPrice() * 0.07;
    }

    @Override
    public double getTotalPrice() {
        return expectedEndingValue + leaseFee;
    }

    @Override
    public double getMonthlyPayment() {
        int months = 36;
        double rate = 0.04;
        double interest = getTotalPrice() * rate * (months / 12.0);
        return (getTotalPrice() + interest) / months;
    }

    @Override

    public String toString() {
        return """
                LEASE CONTRACT
                Date: %s
                Customer: %s
                Email: %s
                VIN: %s
                Expected Ending Value: $%.2f
                Lease Fee: $%.2f
                Total Price: $%.2f
                Monthly Payment: $%.2f
                """
                .formatted(
                        getDate(),
                        getCustomerName(),
                        getCustomerEmail(),
                        getVehicle().getVin(),
                        getExpectedEndingValue(),
                        getLeaseFee(),
                        getTotalPrice(),
                        getMonthlyPayment()
                );
    }

    public double getExpectedEndingValue() { return expectedEndingValue; }
    public double getLeaseFee() { return leaseFee; }
}

