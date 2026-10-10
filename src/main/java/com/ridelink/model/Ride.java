package com.ridelink.model;

import java.sql.Timestamp;

public class Ride {

	private int rideId;
	private int userId;
	private int driverId;
	private int vehicleId;

	private int pickupLocationId;
	private int destinationLocationId;

	private String pickupLocationName;
	private String destinationLocationName;

	private String vehicleType;
	private double fare;
	private String rideStatus;
	private Timestamp bookingTime;

	private String paymentStatus;
	private boolean rated;

	public Ride() {
	}

	public Ride(int rideId, int userId, int driverId, int vehicleId, int pickupLocationId, int destinationLocationId,
			String pickupLocationName, String destinationLocationName, String vehicleType, double fare,
			String rideStatus, Timestamp bookingTime) {

		this.rideId = rideId;
		this.userId = userId;
		this.driverId = driverId;
		this.vehicleId = vehicleId;
		this.pickupLocationId = pickupLocationId;
		this.destinationLocationId = destinationLocationId;
		this.pickupLocationName = pickupLocationName;
		this.destinationLocationName = destinationLocationName;
		this.vehicleType = vehicleType;
		this.fare = fare;
		this.rideStatus = rideStatus;
		this.bookingTime = bookingTime;
	}

	public int getRideId() {
		return rideId;
	}

	public void setRideId(int rideId) {
		this.rideId = rideId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public int getPickupLocationId() {
		return pickupLocationId;
	}

	public void setPickupLocationId(int pickupLocationId) {
		this.pickupLocationId = pickupLocationId;
	}

	public int getDestinationLocationId() {
		return destinationLocationId;
	}

	public void setDestinationLocationId(int destinationLocationId) {

		this.destinationLocationId = destinationLocationId;
	}

	public String getPickupLocationName() {
		return pickupLocationName;
	}

	public void setPickupLocationName(String pickupLocationName) {

		this.pickupLocationName = pickupLocationName;
	}

	public String getDestinationLocationName() {
		return destinationLocationName;
	}

	public void setDestinationLocationName(String destinationLocationName) {

		this.destinationLocationName = destinationLocationName;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	public String getRideStatus() {
		return rideStatus;
	}

	public void setRideStatus(String rideStatus) {
		this.rideStatus = rideStatus;
	}

	public Timestamp getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(Timestamp bookingTime) {
		this.bookingTime = bookingTime;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public boolean isRated() {
		return rated;
	}

	public void setRated(boolean rated) {
		this.rated = rated;
	}
}
