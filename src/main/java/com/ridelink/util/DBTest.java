package com.ridelink.util;

import java.sql.Connection;

public class DBTest {

	public static void main(String[] args) {

		try {
			Connection connection = DBConnection.getConnection();

			if (connection != null) {
				System.out.println("Database connected successfully!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
