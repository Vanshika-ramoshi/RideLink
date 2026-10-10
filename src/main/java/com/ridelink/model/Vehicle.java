package com.ridelink.model;

public class Vehicle {

	    private int vehicleId;
	    private int driverId;
	    private String vehicleNumber;
	    private String vehicleType;
	    private String model;

	    public Vehicle() {
	    }

	    public Vehicle(int vehicleId, int driverId, String vehicleNumber, String vehicleType, String model) {
	        this.vehicleId = vehicleId;
	        this.driverId = driverId;
	        this.vehicleNumber = vehicleNumber;
	        this.vehicleType = vehicleType;
	        this.model = model;
	    }

	    public int getVehicleId() {
	        return vehicleId;
	    }

	    public void setVehicleId(int vehicleId) {
	        this.vehicleId = vehicleId;
	    }

	    public int getDriverId() {
	        return driverId;
	    }

	    public void setDriverId(int driverId) {
	        this.driverId = driverId;
	    }

	    public String getVehicleNumber() {
	        return vehicleNumber;
	    }

	    public void setVehicleNumber(String vehicleNumber) {
	        this.vehicleNumber = vehicleNumber;
	    }

	    public String getVehicleType() {
	        return vehicleType;
	    }

	    public void setVehicleType(String vehicleType) {
	        this.vehicleType = vehicleType;
	    }

	    public String getModel() {
	        return model;
	    }

	    public void setModel(String model) {
	        this.model = model;
	    }
	}
