package com.ridelink.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.ridelink.model.Location;
import com.ridelink.service.FareService;
import com.ridelink.service.LocationService;
import com.ridelink.util.DistanceCalculator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/rideEstimate")
public class RideEstimateServlet extends HttpServlet {

	private LocationService locationService;
	private FareService fareService;

	@Override
	public void init() {

		locationService = new LocationService();
		fareService = new FareService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try {

			int pickupId = Integer.parseInt(request.getParameter("pickupId"));

			int destinationId = Integer.parseInt(request.getParameter("destinationId"));

			String vehicleType = request.getParameter("vehicleType");

			if (pickupId <= 0 || destinationId <= 0 || vehicleType == null || vehicleType.trim().isEmpty()) {

				sendError(response, "Invalid ride details.");
				return;
			}

			if (pickupId == destinationId) {

				sendError(response, "Pickup and destination cannot be the same.");

				return;
			}

			Location pickup = locationService.getLocationById(pickupId);

			Location destination = locationService.getLocationById(destinationId);

			if (pickup == null || destination == null) {

				sendError(response, "Location not found.");

				return;
			}

			double distanceKm = DistanceCalculator.calculateDistance(pickup.getLatitude(), pickup.getLongitude(),
					destination.getLatitude(), destination.getLongitude());

			double fare = fareService.calculateFare(distanceKm, vehicleType);

			if (fare <= 0) {

				sendError(response, "Unable to calculate fare.");

				return;
			}

			PrintWriter out = response.getWriter();

			out.print("{");

			out.print("\"success\":true,");
			out.print("\"distanceKm\":" + distanceKm + ",");
			out.print("\"fare\":" + fare + ",");
			out.print("\"vehicleType\":\"" + escapeJson(vehicleType) + "\"");

			out.print("}");

		} catch (Exception e) {

			e.printStackTrace();

			sendError(response, "Unable to calculate ride estimate.");
		}
	}

	private void sendError(HttpServletResponse response, String message) throws IOException {

		PrintWriter out = response.getWriter();

		out.print("{");

		out.print("\"success\":false,");
		out.print("\"message\":\"" + escapeJson(message) + "\"");

		out.print("}");
	}

	private String escapeJson(String value) {

		if (value == null) {
			return "";
		}

		return value.replace("\\", "\\\\").replace("\"", "\\\"");
	}
}
