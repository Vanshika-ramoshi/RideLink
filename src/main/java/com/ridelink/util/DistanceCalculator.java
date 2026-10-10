package com.ridelink.util;

public class DistanceCalculator {

	private static final double EARTH_RADIUS_KM = 6371.0;

	public static double calculateDistance(double pickupLatitude, double pickupLongitude, double destinationLatitude,
			double destinationLongitude) {

		double latitudeDifference = Math.toRadians(destinationLatitude - pickupLatitude);

		double longitudeDifference = Math.toRadians(destinationLongitude - pickupLongitude);

		double a = Math.sin(latitudeDifference / 2) * Math.sin(latitudeDifference / 2)
				+ Math.cos(Math.toRadians(pickupLatitude)) * Math.cos(Math.toRadians(destinationLatitude))
						* Math.sin(longitudeDifference / 2) * Math.sin(longitudeDifference / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		double distance = EARTH_RADIUS_KM * c;

		return Math.round(distance * 100.0) / 100.0;
	}
}