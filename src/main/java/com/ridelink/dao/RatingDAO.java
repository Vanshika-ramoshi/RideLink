package com.ridelink.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ridelink.model.Rating;
import com.ridelink.util.DBConnection;

public class RatingDAO {

	public boolean addRating(Rating rating) {

		String sql = "INSERT INTO ratings " + "(ride_id, user_id, driver_id, rating, review) "
				+ "VALUES (?, ?, ?, ?, ?)";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, rating.getRideId());
			ps.setInt(2, rating.getUserId());
			ps.setInt(3, rating.getDriverId());
			ps.setInt(4, rating.getRating());
			ps.setString(5, rating.getReview());

			return ps.executeUpdate() > 0;

		} catch (Exception e) {

			e.printStackTrace();

			return false;
		}
	}

	public Rating getRatingByRideId(int rideId) {

		String sql = "SELECT * FROM ratings " + "WHERE ride_id = ? " + "LIMIT 1";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, rideId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				Rating rating = new Rating();

				rating.setRatingId(rs.getInt("rating_id"));

				rating.setRideId(rs.getInt("ride_id"));

				rating.setUserId(rs.getInt("user_id"));

				rating.setDriverId(rs.getInt("driver_id"));

				rating.setRating(rs.getInt("rating"));

				rating.setReview(rs.getString("review"));

				return rating;
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	public double getAverageRatingByDriverId(int driverId) {

		String sql = "SELECT COALESCE(AVG(rating), 5.0) " + "FROM ratings " + "WHERE driver_id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, driverId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				return rs.getDouble(1);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return 5.0;
	}

}
