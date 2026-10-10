package com.ridelink.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ridelink.model.Ride;
import com.ridelink.util.DBConnection;

public class RideDAO {

	public int bookRide(Ride ride) {

		String sql = "INSERT INTO rides " + "(user_id, pickup_location_id, destination_location_id, "
				+ "vehicle_type, fare, ride_status) " + "VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, ride.getUserId());
			ps.setInt(2, ride.getPickupLocationId());
			ps.setInt(3, ride.getDestinationLocationId());
			ps.setString(4, ride.getVehicleType());
			ps.setDouble(5, ride.getFare());
			ps.setString(6, ride.getRideStatus());

			int rows = ps.executeUpdate();

			if (rows > 0) {
				try (ResultSet keys = ps.getGeneratedKeys()) {
					if (keys.next()) {
						int rideId = keys.getInt(1);
						ride.setRideId(rideId);
						return rideId;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public List<Ride> getRequestedRides(String vehicleType) {

		List<Ride> rides = new ArrayList<>();

		String sql = "SELECT r.*, " + "p.location_name AS pickup_name, " + "d.location_name AS destination_name "
				+ "FROM rides r " + "JOIN locations p ON r.pickup_location_id = p.location_id "
				+ "JOIN locations d ON r.destination_location_id = d.location_id "
				+ "WHERE r.ride_status = 'REQUESTED' " + "AND r.vehicle_type = ? " + "ORDER BY r.booking_time ASC";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, vehicleType);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					rides.add(mapRide(rs));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rides;
	}

	public List<Ride> getActiveRidesByDriverId(int driverId) {

		List<Ride> rides = new ArrayList<>();

		String sql = "SELECT r.*, " + "p.location_name AS pickup_name, " + "d.location_name AS destination_name "
				+ "FROM rides r " + "JOIN locations p ON r.pickup_location_id = p.location_id "
				+ "JOIN locations d ON r.destination_location_id = d.location_id " + "WHERE r.driver_id = ? "
				+ "AND r.ride_status IN ('ACCEPTED', 'ONGOING') " + "ORDER BY r.booking_time DESC";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, driverId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					rides.add(mapRide(rs));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rides;
	}

	public boolean acceptRide(int rideId, int driverId, int vehicleId) {

		String sql = "UPDATE rides SET driver_id = ?, vehicle_id = ?, " + "ride_status = 'ACCEPTED' "
				+ "WHERE ride_id = ? AND ride_status = 'REQUESTED'";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, driverId);
			ps.setInt(2, vehicleId);
			ps.setInt(3, rideId);

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean rejectRide(int rideId) {

		String sql = "UPDATE rides SET ride_status = 'REJECTED' " + "WHERE ride_id = ? AND ride_status = 'REQUESTED'";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, rideId);

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean startRide(int rideId, int driverId) {

		String sql = "UPDATE rides SET ride_status = 'ONGOING' " + "WHERE ride_id = ? AND driver_id = ? "
				+ "AND ride_status = 'ACCEPTED'";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, rideId);
			ps.setInt(2, driverId);

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean completeRide(int rideId, int driverId) {

		String sql = "UPDATE rides SET ride_status = 'COMPLETED' " + "WHERE ride_id = ? AND driver_id = ? "
				+ "AND ride_status = 'ONGOING'";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, rideId);
			ps.setInt(2, driverId);

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int getCompletedRideCountByDriverId(int driverId) {

		String sql = "SELECT COUNT(*) FROM rides " + "WHERE driver_id = ? AND ride_status = 'COMPLETED'";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, driverId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public double getTodayEarningsByDriverId(int driverId) {

		String sql = "SELECT COALESCE(SUM(fare), 0) FROM rides " + "WHERE driver_id = ? "
				+ "AND ride_status = 'COMPLETED' " + "AND DATE(booking_time) = CURDATE()";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, driverId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getDouble(1);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public List<Ride> getRidesByUserId(int userId) {

		List<Ride> rides = new ArrayList<>();

		String sql = "SELECT r.*, " + "p.location_name AS pickup_name, " + "d.location_name AS destination_name "
				+ "FROM rides r " + "JOIN locations p ON r.pickup_location_id = p.location_id "
				+ "JOIN locations d ON r.destination_location_id = d.location_id " + "WHERE r.user_id = ? "
				+ "ORDER BY r.booking_time DESC";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					rides.add(mapRide(rs));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rides;
	}

	public List<Ride> getRidesByDriverId(int driverId) {

		List<Ride> rides = new ArrayList<>();

		String sql = "SELECT r.*, " + "p.location_name AS pickup_name, " + "d.location_name AS destination_name "
				+ "FROM rides r " + "JOIN locations p ON r.pickup_location_id = p.location_id "
				+ "JOIN locations d ON r.destination_location_id = d.location_id " + "WHERE r.driver_id = ? "
				+ "ORDER BY r.booking_time DESC";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, driverId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					rides.add(mapRide(rs));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rides;
	}

	public Ride getCurrentRideByUserId(int userId) {

		String sql = "SELECT r.*, " + "p.location_name AS pickup_name, " + "d.location_name AS destination_name "
				+ "FROM rides r " + "JOIN locations p ON r.pickup_location_id = p.location_id "
				+ "JOIN locations d ON r.destination_location_id = d.location_id " + "WHERE r.user_id = ? "
				+ "AND r.ride_status IN ('REQUESTED', 'ACCEPTED', 'ONGOING') " + "ORDER BY r.booking_time DESC LIMIT 1";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRide(rs);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Ride getRideByIdForUser(int rideId, int userId) {

		String sql = "SELECT r.*, " + "p.location_name AS pickup_name, " + "d.location_name AS destination_name "
				+ "FROM rides r " + "JOIN locations p ON r.pickup_location_id = p.location_id "
				+ "JOIN locations d ON r.destination_location_id = d.location_id "
				+ "WHERE r.ride_id = ? AND r.user_id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, rideId);
			ps.setInt(2, userId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRide(rs);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private Ride mapRide(ResultSet rs) throws Exception {

		Ride ride = new Ride();

		ride.setRideId(rs.getInt("ride_id"));
		ride.setUserId(rs.getInt("user_id"));
		ride.setDriverId(rs.getInt("driver_id"));
		ride.setVehicleId(rs.getInt("vehicle_id"));
		ride.setPickupLocationId(rs.getInt("pickup_location_id"));
		ride.setDestinationLocationId(rs.getInt("destination_location_id"));
		ride.setPickupLocationName(rs.getString("pickup_name"));
		ride.setDestinationLocationName(rs.getString("destination_name"));
		ride.setVehicleType(rs.getString("vehicle_type"));
		ride.setFare(rs.getDouble("fare"));
		ride.setRideStatus(rs.getString("ride_status"));
		ride.setBookingTime(rs.getTimestamp("booking_time"));

		return ride;
	}
}
