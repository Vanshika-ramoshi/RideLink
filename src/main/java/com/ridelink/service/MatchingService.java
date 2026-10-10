package com.ridelink.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.ridelink.model.Driver;

public class MatchingService {

	public double calculateMatchingScore(Driver driver, double distanceToPickupKm, String requiredVehicleType,
			String driverVehicleType) {

		if ((driver == null) || driver.getStatus() == null || !driver.getStatus().equalsIgnoreCase("ONLINE")) {

			return 0;
		}

		if (requiredVehicleType == null || driverVehicleType == null
				|| !requiredVehicleType.equalsIgnoreCase(driverVehicleType) || (distanceToPickupKm < 0)) {
			return 0;
		}

		double score = 0;

		score += 40;

		double ratingScore = driver.getRating() * 10;

		score += ratingScore;

		if (distanceToPickupKm <= 2) {

			score += 30;

		} else if (distanceToPickupKm <= 5) {

			score += 20;

		} else if (distanceToPickupKm <= 10) {

			score += 10;
		}

		return Math.round(score * 100.0) / 100.0;
	}

	public boolean isEligibleDriver(Driver driver, String requiredVehicleType, String driverVehicleType) {

		if ((driver == null) || driver.getStatus() == null || !driver.getStatus().equalsIgnoreCase("ONLINE")) {

			return false;
		}

		if (requiredVehicleType == null || driverVehicleType == null) {

			return false;
		}

		return requiredVehicleType.equalsIgnoreCase(driverVehicleType);
	}

	/*
	 * Ranks drivers by:
	 *
	 * 1. Matching eligibility 2. Driver rating
	 *
	 * Current project does not yet store driver's live GPS position.
	 */
	public List<Driver> rankDrivers(List<Driver> drivers) {

		if (drivers == null || drivers.isEmpty()) {

			return new ArrayList<>();
		}

		List<Driver> rankedDrivers = new ArrayList<>(drivers);

		rankedDrivers.sort(Comparator.comparingDouble(Driver::getRating).reversed());

		return rankedDrivers;
	}

}
