package com.ridelink.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ridelink.model.Vehicle;
import com.ridelink.util.DBConnection;

public class VehicleDAO {

	public boolean registerVehicle(Vehicle vehicle) {

		String sql = "INSERT INTO vehicles " + "(driver_id, vehicle_number, vehicle_type, model) "
				+ "VALUES (?, ?, ?, ?)";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, vehicle.getDriverId());
			ps.setString(2, vehicle.getVehicleNumber().trim());
			ps.setString(3, vehicle.getVehicleType().trim());
			ps.setString(4, vehicle.getModel().trim());

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Vehicle getVehicleByDriverId(int driverId) {

		String sql = "SELECT vehicle_id, driver_id, vehicle_number, " + "vehicle_type, model FROM vehicles "
				+ "WHERE driver_id = ? LIMIT 1";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, driverId);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					Vehicle vehicle = new Vehicle();

					vehicle.setVehicleId(rs.getInt("vehicle_id"));
					vehicle.setDriverId(rs.getInt("driver_id"));
					vehicle.setVehicleNumber(rs.getString("vehicle_number"));
					vehicle.setVehicleType(rs.getString("vehicle_type"));
					vehicle.setModel(rs.getString("model"));

					return vehicle;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
