package com.ridelink.model;

public class Driver {

    private int driverId;
    private int userId;
    private String licenseNumber;
    private String status;
    private double rating;

    public Driver() {
    }

    public Driver(int driverId, int userId, String licenseNumber,
                  String status, double rating) {

        this.driverId = driverId;
        this.userId = userId;
        this.licenseNumber = licenseNumber;
        this.status = status;
        this.rating = rating;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}