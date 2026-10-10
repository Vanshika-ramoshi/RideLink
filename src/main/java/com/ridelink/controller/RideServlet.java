package com.ridelink.controller;

import java.io.IOException;

import com.ridelink.model.Location;
import com.ridelink.model.User;
import com.ridelink.service.FareService;
import com.ridelink.service.LocationService;
import com.ridelink.service.RideService;
import com.ridelink.util.DistanceCalculator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ride")
public class RideServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private RideService rideService;
	private LocationService locationService;
	private FareService fareService;

	@Override
	public void init() throws ServletException {
		rideService = new RideService();
		locationService = new LocationService();
		fareService = new FareService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		User user = (User) session.getAttribute("user");

		try {
			int userId = user.getUserId();

			String pickupParam = request.getParameter("pickupLocationId");

			String destinationParam = request.getParameter("destinationLocationId");

			String vehicleType = request.getParameter("vehicleType");

			if (pickupParam == null || destinationParam == null || pickupParam.trim().isEmpty()
					|| destinationParam.trim().isEmpty()) {

				session.setAttribute("bookingError", "Please select pickup and destination locations.");

				response.sendRedirect(request.getContextPath() + "/locations");
				return;
			}

			int pickupLocationId = Integer.parseInt(pickupParam);
			int destinationLocationId = Integer.parseInt(destinationParam);

			if (pickupLocationId <= 0 || destinationLocationId <= 0) {
				session.setAttribute("bookingError", "Please select valid locations.");

				response.sendRedirect(request.getContextPath() + "/locations");
				return;
			}

			if (pickupLocationId == destinationLocationId) {
				session.setAttribute("bookingError", "Pickup and destination cannot be the same.");

				response.sendRedirect(request.getContextPath() + "/locations");
				return;
			}

			if (vehicleType == null || vehicleType.trim().isEmpty()) {
				session.setAttribute("bookingError", "Please select a vehicle.");

				response.sendRedirect(request.getContextPath() + "/locations");
				return;
			}

			vehicleType = vehicleType.trim().toUpperCase();

			if (!vehicleType.equals("BIKE") && !vehicleType.equals("AUTO") && !vehicleType.equals("CAR")) {

				session.setAttribute("bookingError", "Invalid vehicle type.");

				response.sendRedirect(request.getContextPath() + "/locations");
				return;
			}

			Location pickup = locationService.getLocationById(pickupLocationId);

			Location destination = locationService.getLocationById(destinationLocationId);

			if (pickup == null || destination == null) {
				session.setAttribute("bookingError", "Invalid pickup or destination location.");

				response.sendRedirect(request.getContextPath() + "/locations");
				return;
			}

			double distance = DistanceCalculator.calculateDistance(pickup.getLatitude(), pickup.getLongitude(),
					destination.getLatitude(), destination.getLongitude());

			double fare = fareService.calculateFare(distance, vehicleType);

			int rideId = rideService.bookRide(userId, pickupLocationId, destinationLocationId, vehicleType, fare);

			if (rideId > 0) {
				session.setAttribute("bookingSuccess", "Ride booked successfully.");

				response.sendRedirect(request.getContextPath() + "/ride-tracker?rideId=" + rideId);
				return;

			} else {
				session.setAttribute("bookingError", "Unable to book your ride. Please try again.");

				response.sendRedirect(request.getContextPath() + "/rider/dashboard");
				return;
			}

		} catch (NumberFormatException e) {
			getServletContext().log("Invalid location ID.", e);

			session.setAttribute("bookingError", "Invalid location selected.");

			response.sendRedirect(request.getContextPath() + "/locations");

		} catch (Exception e) {
			getServletContext().log("Error while booking ride.", e);

			session.setAttribute("bookingError", "Something went wrong while booking the ride.");

			response.sendRedirect(request.getContextPath() + "/rider/dashboard");
		}
	}
}
