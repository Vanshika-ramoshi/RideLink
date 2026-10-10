package com.ridelink.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ridelink.model.Driver;
import com.ridelink.util.DBConnection;

public class DriverDAO {

	public boolean registerDriver(Driver driver) {

		String sql = "INSERT INTO drivers " + "(user_id, license_number, status, rating) " + "VALUES (?, ?, ?, ?)";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, driver.getUserId());
			ps.setString(2, driver.getLicenseNumber());
			ps.setString(3, "OFFLINE");
			ps.setDouble(4, driver.getRating());

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateDriverStatus(int userId, String status) {

		String sql = "UPDATE drivers SET status = ? " + "WHERE user_id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, status);
			ps.setInt(2, userId);

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Driver getDriverByUserId(int userId) {

		String sql = "SELECT * FROM drivers " + "WHERE user_id = ? " + "LIMIT 1";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				return mapDriver(rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * Finds ONLINE drivers having the required vehicle type.
	 *
	 * Driver rating is used for ranking.
	 */
	public List<Driver> findMatchingDrivers(String vehicleType) {

		List<Driver> drivers = new ArrayList<>();

		String sql = "SELECT DISTINCT d.* " + "FROM drivers d " + "JOIN vehicles v " + "ON d.driver_id = v.driver_id "
				+ "WHERE d.status = 'ONLINE' " + "AND v.vehicle_type = ? " + "ORDER BY d.rating DESC";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, vehicleType);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				drivers.add(mapDriver(rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return drivers;
	}

	private Driver mapDriver(ResultSet rs) throws Exception {

		Driver driver = new Driver();

		driver.setDriverId(rs.getInt("driver_id"));

		driver.setUserId(rs.getInt("user_id"));

		driver.setLicenseNumber(rs.getString("license_number"));

		driver.setStatus(rs.getString("status"));

		driver.setRating(rs.getDouble("rating"));

		return driver;
	}

	public boolean updateDriverRating(int driverId, double rating) {

		String sql = "UPDATE drivers " + "SET rating = ? " + "WHERE driver_id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setDouble(1, rating);
			ps.setInt(2, driverId);

			return ps.executeUpdate() > 0;

		} catch (Exception e) {

			e.printStackTrace();

			return false;
		}

	}

}
