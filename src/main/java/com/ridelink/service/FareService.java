package com.ridelink.service;

public class FareService {

	private static final double BIKE_RATE = 10.0;
	private static final double AUTO_RATE = 15.0;
	private static final double CAR_RATE = 20.0;

	public double calculateFare(double distanceKm, String vehicleType) {

		if (distanceKm <= 0 || vehicleType == null) {
			return 0;
		}

		double rate;

		switch (vehicleType.toUpperCase()) {

		case "BIKE":
			rate = BIKE_RATE;
			break;

		case "AUTO":
			rate = AUTO_RATE;
			break;

		case "CAR":
			rate = CAR_RATE;
			break;

		default:
			return 0;
		}

		double fare = distanceKm * rate;

		return Math.round(fare * 100.0) / 100.0;
	}

	public double getRate(String vehicleType) {

		if (vehicleType == null) {
			return 0;
		}

		switch (vehicleType.toUpperCase()) {

		case "BIKE":
			return BIKE_RATE;

		case "AUTO":
			return AUTO_RATE;

		case "CAR":
			return CAR_RATE;

		default:
			return 0;
		}
	}
}