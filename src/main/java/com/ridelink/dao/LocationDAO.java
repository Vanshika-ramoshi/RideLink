package com.ridelink.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ridelink.model.Location;
import com.ridelink.util.DBConnection;

public class LocationDAO {

	public List<Location> getAllLocations() {

		List<Location> locations = new ArrayList<>();

		String sql = "SELECT location_id, location_name, city, latitude, longitude " + "FROM locations "
				+ "ORDER BY city, location_name";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {

				Location location = new Location();

				location.setLocationId(rs.getInt("location_id"));
				location.setLocationName(rs.getString("location_name"));
				location.setCity(rs.getString("city"));
				location.setLatitude(rs.getDouble("latitude"));
				location.setLongitude(rs.getDouble("longitude"));

				locations.add(location);
			}

			System.out.println("Locations fetched: " + locations.size());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return locations;
	}

	public Location getLocationById(int locationId) {

		String sql = "SELECT location_id, location_name, city, latitude, longitude "
				+ "FROM locations WHERE location_id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, locationId);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {

					Location location = new Location();

					location.setLocationId(rs.getInt("location_id"));
					location.setLocationName(rs.getString("location_name"));
					location.setCity(rs.getString("city"));
					location.setLatitude(rs.getDouble("latitude"));
					location.setLongitude(rs.getDouble("longitude"));

					return location;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}