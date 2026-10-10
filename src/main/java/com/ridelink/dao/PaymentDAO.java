package com.ridelink.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ridelink.model.Payment;
import com.ridelink.util.DBConnection;

public class PaymentDAO {

	public boolean createPayment(Payment payment) {

		String sql = "INSERT INTO payments " + "(ride_id, amount, payment_method, payment_status) "
				+ "VALUES (?, ?, ?, ?)";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, payment.getRideId());
			ps.setDouble(2, payment.getAmount());
			ps.setString(3, payment.getPaymentMethod());
			ps.setString(4, payment.getPaymentStatus());

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Payment getPaymentByRideId(int rideId) {

		String sql = "SELECT * FROM payments " + "WHERE ride_id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, rideId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				Payment payment = new Payment();

				payment.setPaymentId(rs.getInt("payment_id"));

				payment.setRideId(rs.getInt("ride_id"));

				payment.setAmount(rs.getDouble("amount"));

				payment.setPaymentMethod(rs.getString("payment_method"));

				payment.setPaymentStatus(rs.getString("payment_status"));

				payment.setPaymentTime(rs.getTimestamp("payment_time"));

				return payment;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean updatePaymentStatus(int paymentId, String status) {

		String sql = "UPDATE payments " + "SET payment_status = ? " + "WHERE payment_id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, status);
			ps.setInt(2, paymentId);

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
