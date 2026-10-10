package com.ridelink.service;

import java.util.ArrayList;
import java.util.List;

import com.ridelink.dao.DriverDAO;
import com.ridelink.model.Driver;

public class DriverService {

	private DriverDAO driverDAO;

	public DriverService() {
		driverDAO = new DriverDAO();
	}

	public boolean registerDriver(Driver driver) {

		if ((driver == null) || (driver.getUserId() <= 0) || driver.getLicenseNumber() == null || driver.getLicenseNumber().trim().isEmpty()) {

			return false;
		}

		return driverDAO.registerDriver(driver);
	}

	public boolean updateDriverStatus(int userId, String status) {

		if ((userId <= 0) || (status == null) || (!status.equalsIgnoreCase("ONLINE") && !status.equalsIgnoreCase("OFFLINE"))) {

			return false;
		}

		return driverDAO.updateDriverStatus(userId, status.toUpperCase());
	}

	public Driver getDriverByUserId(int userId) {

		if (userId <= 0) {
			return null;
		}

		return driverDAO.getDriverByUserId(userId);
	}

	/*
	 * Smart matching:
	 *
	 * 1. Driver must be ONLINE. 2. Vehicle type must match. 3. Drivers are ordered
	 * by rating.
	 */
	public List<Driver> findMatchingDrivers(String vehicleType) {

		if (vehicleType == null || vehicleType.trim().isEmpty()) {

			return new ArrayList<>();
		}

		return driverDAO.findMatchingDrivers(vehicleType.toUpperCase());
	}

	public boolean updateDriverRating(int driverId, double rating) {

		if ((driverId <= 0) || rating < 1 || rating > 5) {
			return false;
		}

		return driverDAO.updateDriverRating(driverId, rating);

	}

}
