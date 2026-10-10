package com.ridelink.util;

public class ValidationUtil {

	public static boolean isNullOrEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

	public static boolean isValidEmail(String email) {
		if (isNullOrEmpty(email)) {
			return false;
		}

		return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
	}

	public static boolean isValidPhone(String phone) {
		if (isNullOrEmpty(phone)) {
			return false;
		}

		return phone.matches("\\d{10}");
	}

	public static boolean isValidPassword(String password) {
		return password != null && password.length() >= 6;
	}

	public static boolean isValidRating(int rating) {
		return rating >= 1 && rating <= 5;
	}

	public static boolean isValidVehicleType(String vehicleType) {
		if (isNullOrEmpty(vehicleType)) {
			return false;
		}

		return vehicleType.equalsIgnoreCase("Bike") || vehicleType.equalsIgnoreCase("Auto")
				|| vehicleType.equalsIgnoreCase("Car");
	}

	public static boolean isValidPaymentMethod(String paymentMethod) {
		if (isNullOrEmpty(paymentMethod)) {
			return false;
		}

		return paymentMethod.equalsIgnoreCase("CASH") || paymentMethod.equalsIgnoreCase("ONLINE");
	}
}