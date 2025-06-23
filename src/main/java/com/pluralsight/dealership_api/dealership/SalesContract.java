package com.pluralsight.dealership_api.dealership;

public class SalesContract extends Contract{
    private boolean isFinanced;
    private double salesTaxAmount;
    private double recordingFee = 100;
    private double processingFee;

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicle, boolean isFinanced) {
        super(date, customerName, customerEmail, vehicle);
        this.isFinanced = isFinanced;
        this.salesTaxAmount = vehicle.getPrice() * 0.05;
        this.processingFee = vehicle.getPrice() < 10000 ? 295 : 495;
    }

    @Override
    public double getTotalPrice() {
        return vehicle.getPrice() + salesTaxAmount + recordingFee + processingFee;
    }

    @Override
    public double getMonthlyPayment() {
        if (!isFinanced) return 0;
        double rate = vehicle.getPrice() >= 10000 ? 0.0425 : 0.0525;
        int months = vehicle.getPrice() >= 10000 ? 48 : 24;
        double interest = getTotalPrice() * rate * (months / 12.0);
        return (getTotalPrice() + interest) / months;
    }

    @Override
    public String toString() {
        return """
       SALE CONTRACT
       Date: %s
       Customer: %s
       Email: %s
       VIN: %s
       Total Price: $%.2f
       Monthly Payment: $%.2f
       Financed: %s
       """.formatted(
                getDate(),
                getCustomerName(),
                getCustomerEmail(),
                getVehicle().getVin(),
                getTotalPrice(),
                getMonthlyPayment(),
                isFinanced() ? "Yes" : "No"
        );
    }

    public boolean isFinanced() { return isFinanced; }
    public double getSalesTaxAmount() { return salesTaxAmount; }
    public double getRecordingFee() { return recordingFee; }
    public double getProcessingFee() { return processingFee; }
}

