package com.ridelink.service;

public class RideStatusService {

	public boolean canAccept(String currentStatus) {

		return "REQUESTED".equalsIgnoreCase(currentStatus);
	}

	public boolean canReject(String currentStatus) {

		return "REQUESTED".equalsIgnoreCase(currentStatus);
	}

	public boolean canStart(String currentStatus) {

		return "ACCEPTED".equalsIgnoreCase(currentStatus);
	}

	public boolean canComplete(String currentStatus) {

		return "ONGOING".equalsIgnoreCase(currentStatus);
	}

	public String getNextStatus(String currentStatus, String action) {

		if (currentStatus == null || action == null) {

			return null;
		}

		if ("ACCEPT".equalsIgnoreCase(action) && canAccept(currentStatus)) {

			return "ACCEPTED";
		}

		if ("REJECT".equalsIgnoreCase(action) && canReject(currentStatus)) {

			return "REJECTED";
		}

		if ("START".equalsIgnoreCase(action) && canStart(currentStatus)) {

			return "ONGOING";
		}

		if ("COMPLETE".equalsIgnoreCase(action) && canComplete(currentStatus)) {

			return "COMPLETED";
		}

		return null;
	}

}
