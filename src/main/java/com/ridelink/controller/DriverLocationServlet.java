package com.ridelink.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ridelink.dao.DriverDAO;
import com.ridelink.model.Driver;
import com.ridelink.model.User;
import com.ridelink.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/driver-location")
public class DriverLocationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private DriverDAO driverDAO;

	@Override
	public void init() {
		driverDAO = new DriverDAO();
	}

	private User getLoggedInUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null) {
			return null;
		}

		Object userObject = session.getAttribute("user");

		if (!(userObject instanceof User)) {
			return null;
		}

		return (User) userObject;
	}

	private void sendJson(HttpServletResponse response, int status, String json) throws IOException {

		response.setStatus(status);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = getLoggedInUser(request);

		if (user == null) {
			sendJson(response, HttpServletResponse.SC_UNAUTHORIZED,
					"{\"success\":false,\"message\":\"Please log in.\"}");
			return;
		}

		try {
			Driver driver = driverDAO.getDriverByUserId(user.getUserId());

			if (driver == null) {
				sendJson(response, HttpServletResponse.SC_FORBIDDEN,
						"{\"success\":false,\"message\":\"Driver profile not found.\"}");
				return;
			}

			String rideIdValue = request.getParameter("rideId");
			String latitudeValue = request.getParameter("latitude");
			String longitudeValue = request.getParameter("longitude");

			if (rideIdValue == null || latitudeValue == null || longitudeValue == null) {

				sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
						"{\"success\":false,\"message\":\"Missing location data.\"}");
				return;
			}

			int rideId;
			double latitude;
			double longitude;

			try {
				rideId = Integer.parseInt(rideIdValue);
				latitude = Double.parseDouble(latitudeValue);
				longitude = Double.parseDouble(longitudeValue);
			} catch (NumberFormatException e) {
				sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
						"{\"success\":false,\"message\":\"Invalid location data.\"}");
				return;
			}

			if (rideId <= 0 || !Double.isFinite(latitude) || !Double.isFinite(longitude) || latitude < -90
					|| latitude > 90 || longitude < -180 || longitude > 180) {

				sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
						"{\"success\":false,\"message\":\"Coordinates are out of range.\"}");
				return;
			}

			try (Connection connection = DBConnection.getConnection()) {

				String verifySql = "SELECT ride_id FROM rides " + "WHERE ride_id = ? AND driver_id = ? "
						+ "AND ride_status IN ('ACCEPTED', 'ONGOING')";

				try (PreparedStatement verify = connection.prepareStatement(verifySql)) {

					verify.setInt(1, rideId);
					verify.setInt(2, driver.getDriverId());

					try (ResultSet rs = verify.executeQuery()) {

						if (!rs.next()) {
							sendJson(response, HttpServletResponse.SC_FORBIDDEN,
									"{\"success\":false,\"message\":\"This ride is not assigned to you or is inactive.\"}");
							return;
						}
					}
				}

				String sql = "INSERT INTO driver_live_locations " + "(driver_id, ride_id, latitude, longitude) "
						+ "VALUES (?, ?, ?, ?) " + "ON DUPLICATE KEY UPDATE " + "ride_id = VALUES(ride_id), "
						+ "latitude = VALUES(latitude), " + "longitude = VALUES(longitude), "
						+ "updated_at = CURRENT_TIMESTAMP";

				try (PreparedStatement ps = connection.prepareStatement(sql)) {

					ps.setInt(1, driver.getDriverId());
					ps.setInt(2, rideId);
					ps.setDouble(3, latitude);
					ps.setDouble(4, longitude);

					ps.executeUpdate();
				}
			}

			sendJson(response, HttpServletResponse.SC_OK, "{\"success\":true,\"message\":\"Location updated.\"}");

		} catch (SQLException e) {
			getServletContext().log("Unable to update driver location.", e);

			sendJson(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"{\"success\":false,\"message\":\"Unable to update location.\"}");

		} catch (Exception e) {
			getServletContext().log("Driver location update failed.", e);

			sendJson(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"{\"success\":false,\"message\":\"An unexpected error occurred.\"}");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = getLoggedInUser(request);

		if (user == null) {
			sendJson(response, HttpServletResponse.SC_UNAUTHORIZED,
					"{\"success\":false,\"message\":\"Please log in.\"}");
			return;
		}

		String rideIdValue = request.getParameter("rideId");

		if (rideIdValue == null || rideIdValue.trim().isEmpty()) {
			sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
					"{\"success\":false,\"message\":\"Ride ID is required.\"}");
			return;
		}

		int rideId;

		try {
			rideId = Integer.parseInt(rideIdValue);
		} catch (NumberFormatException e) {
			sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
					"{\"success\":false,\"message\":\"Invalid ride ID.\"}");
			return;
		}

		if (rideId <= 0) {
			sendJson(response, HttpServletResponse.SC_BAD_REQUEST,
					"{\"success\":false,\"message\":\"Invalid ride ID.\"}");
			return;
		}

		String sql = "SELECT r.ride_status, l.latitude, l.longitude, l.updated_at " + "FROM rides r "
				+ "LEFT JOIN driver_live_locations l " + "ON l.ride_id = r.ride_id AND l.driver_id = r.driver_id "
				+ "WHERE r.ride_id = ? AND r.user_id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, rideId);
			ps.setInt(2, user.getUserId());

			try (ResultSet rs = ps.executeQuery()) {

				if (!rs.next()) {
					sendJson(response, HttpServletResponse.SC_FORBIDDEN,
							"{\"success\":false,\"message\":\"Ride not found or access denied.\"}");
					return;
				}

				String rideStatus = rs.getString("ride_status");

				if ("COMPLETED".equalsIgnoreCase(rideStatus) || "CANCELLED".equalsIgnoreCase(rideStatus)
						|| "REJECTED".equalsIgnoreCase(rideStatus)) {

					sendJson(response, HttpServletResponse.SC_OK,
							"{\"success\":true,\"tracking\":false,\"message\":\"Ride is no longer active.\"}");
					return;
				}

				double latitude = rs.getDouble("latitude");
				boolean latitudeIsNull = rs.wasNull();

				double longitude = rs.getDouble("longitude");
				boolean longitudeIsNull = rs.wasNull();

				if (latitudeIsNull || longitudeIsNull) {
					sendJson(response, HttpServletResponse.SC_OK,
							"{\"success\":true,\"tracking\":true,\"available\":false,\"message\":\"Driver location is not available yet.\"}");
					return;
				}

				String updatedAt = rs.getTimestamp("updated_at") == null ? ""
						: rs.getTimestamp("updated_at").toInstant().toString();

				String json = "{\"success\":true," + "\"tracking\":true," + "\"available\":true," + "\"latitude\":"
						+ latitude + "," + "\"longitude\":" + longitude + "," + "\"rideStatus\":\""
						+ escapeJson(rideStatus) + "\"," + "\"updatedAt\":\"" + escapeJson(updatedAt) + "\"}";

				sendJson(response, HttpServletResponse.SC_OK, json);
			}

		} catch (SQLException e) {
			getServletContext().log("Unable to retrieve driver location.", e);

			sendJson(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"{\"success\":false,\"message\":\"Unable to retrieve driver location.\"}");
		}
	}

	private String escapeJson(String value) {
		if (value == null) {
			return "";
		}

		return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
	}
}
