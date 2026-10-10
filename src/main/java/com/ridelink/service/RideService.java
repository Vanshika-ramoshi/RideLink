package com.ridelink.service;

import java.util.ArrayList;
import java.util.List;

import com.ridelink.dao.RideDAO;
import com.ridelink.model.Ride;

public class RideService {

	private RideDAO rideDAO;

	public RideService() {
		rideDAO = new RideDAO();
	}

	public int bookRide(Ride ride) {

		if (ride == null || ride.getUserId() <= 0 || ride.getPickupLocationId() <= 0
				|| ride.getDestinationLocationId() <= 0) {
			return 0;
		}

		if (ride.getPickupLocationId() == ride.getDestinationLocationId() || ride.getVehicleType() == null
				|| ride.getVehicleType().trim().isEmpty()) {
			return 0;
		}

		String vehicleType = ride.getVehicleType().trim().toUpperCase();

		if (!vehicleType.equals("BIKE") && !vehicleType.equals("AUTO") && !vehicleType.equals("CAR")) {
			return 0;
		}

		ride.setVehicleType(vehicleType);

		if (ride.getFare() <= 0) {
			return 0;
		}

		if (ride.getRideStatus() == null || ride.getRideStatus().trim().isEmpty()) {
			ride.setRideStatus("REQUESTED");
		}

		return rideDAO.bookRide(ride);
	}

	public List<Ride> getRequestedRides(String vehicleType) {

		if (vehicleType == null || vehicleType.trim().isEmpty()) {
			return new ArrayList<>();
		}

		return rideDAO.getRequestedRides(vehicleType.trim().toUpperCase());
	}

	public List<Ride> getActiveRidesByDriverId(int driverId) {

		if (driverId <= 0) {
			return new ArrayList<>();
		}

		return rideDAO.getActiveRidesByDriverId(driverId);
	}

	public boolean acceptRide(int rideId, int driverId, int vehicleId) {

		if (rideId <= 0 || driverId <= 0 || vehicleId <= 0) {
			return false;
		}

		return rideDAO.acceptRide(rideId, driverId, vehicleId);
	}

	public boolean rejectRide(int rideId) {

		if (rideId <= 0) {
			return false;
		}

		return rideDAO.rejectRide(rideId);
	}

	public boolean startRide(int rideId, int driverId) {

		if (rideId <= 0 || driverId <= 0) {
			return false;
		}

		return rideDAO.startRide(rideId, driverId);
	}

	public boolean completeRide(int rideId, int driverId) {

		if (rideId <= 0 || driverId <= 0) {
			return false;
		}

		return rideDAO.completeRide(rideId, driverId);
	}

	public int getCompletedRideCountByDriverId(int driverId) {

		if (driverId <= 0) {
			return 0;
		}

		return rideDAO.getCompletedRideCountByDriverId(driverId);
	}

	public double getTodayEarningsByDriverId(int driverId) {

		if (driverId <= 0) {
			return 0;
		}

		return rideDAO.getTodayEarningsByDriverId(driverId);
	}

	public List<Ride> getRidesByUserId(int userId) {

		if (userId <= 0) {
			return new ArrayList<>();
		}

		return rideDAO.getRidesByUserId(userId);
	}

	public List<Ride> getRidesByDriverId(int driverId) {

		if (driverId <= 0) {
			return new ArrayList<>();
		}

		return rideDAO.getRidesByDriverId(driverId);
	}

	public Ride getCurrentRideByUserId(int userId) {

		if (userId <= 0) {
			return null;
		}

		return rideDAO.getCurrentRideByUserId(userId);
	}

	public int bookRide(int userId, int pickupLocationId, int destinationLocationId, String vehicleType, double fare) {

		Ride ride = new Ride();

		ride.setUserId(userId);
		ride.setPickupLocationId(pickupLocationId);
		ride.setDestinationLocationId(destinationLocationId);
		ride.setVehicleType(vehicleType);
		ride.setFare(fare);
		ride.setRideStatus("REQUESTED");

		return bookRide(ride);
	}

	public Ride getRideByIdForUser(int rideId, int userId) {

		if (rideId <= 0 || userId <= 0) {
			return null;
		}

		return rideDAO.getRideByIdForUser(rideId, userId);
	}
}
