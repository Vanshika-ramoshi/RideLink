package com.ridelink.service;

import com.ridelink.dao.VehicleDAO;
import com.ridelink.model.Vehicle;

public class VehicleService {

	private VehicleDAO vehicleDAO;

	public VehicleService() {
		vehicleDAO = new VehicleDAO();
	}

	public boolean registerVehicle(Vehicle vehicle) {

		if ((vehicle.getDriverId() <= 0) || vehicle.getVehicleNumber() == null || vehicle.getVehicleNumber().trim().isEmpty()) {
			return false;
		}

		if (vehicle.getVehicleType() == null || vehicle.getVehicleType().trim().isEmpty() || vehicle.getModel() == null || vehicle.getModel().trim().isEmpty()) {
			return false;
		}

		return vehicleDAO.registerVehicle(vehicle);
	}

	public Vehicle getVehicleByDriverId(int driverId) {

		if (driverId <= 0) {
			return null;
		}

		return vehicleDAO.getVehicleByDriverId(driverId);
	}
}