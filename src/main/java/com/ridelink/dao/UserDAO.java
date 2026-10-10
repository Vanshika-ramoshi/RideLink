package com.ridelink.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ridelink.model.User;
import com.ridelink.util.DBConnection;

public class UserDAO {

	public boolean registerUser(User user) {

		String sql = "INSERT INTO users (name, email, phone, password) VALUES (?, ?, ?, ?)";


		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPhone());
			ps.setString(4, user.getPassword());

			int rows = ps.executeUpdate();

			return rows > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public User loginUser(String email, String password) {

		String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, email);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				User user = new User();

				user.setUserId(rs.getInt("user_id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setPassword(rs.getString("password"));

				return user;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}