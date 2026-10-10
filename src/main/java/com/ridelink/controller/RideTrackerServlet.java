package com.ridelink.controller;

import java.io.IOException;

import com.ridelink.model.Location;
import com.ridelink.model.Ride;
import com.ridelink.model.User;
import com.ridelink.service.LocationService;
import com.ridelink.service.RideService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ride-tracker")
public class RideTrackerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private RideService rideService;
	private LocationService locationService;

	@Override
	public void init() throws ServletException {
		rideService = new RideService();
		locationService = new LocationService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		User user = (User) session.getAttribute("user");

		try {
			String rideIdParam = request.getParameter("rideId");

			if (rideIdParam == null || rideIdParam.trim().isEmpty()) {
				request.setAttribute("error", "Ride ID is missing. Please select a booked ride.");

				request.getRequestDispatcher("/rider/ride-tracker.jsp").forward(request, response);
				return;
			}

			int rideId = Integer.parseInt(rideIdParam);

			if (rideId <= 0) {
				request.setAttribute("error", "Invalid ride ID.");

				request.getRequestDispatcher("/rider/ride-tracker.jsp").forward(request, response);
				return;
			}

			Ride ride = rideService.getRideByIdForUser(rideId, user.getUserId());

			if (ride == null) {
				request.setAttribute("error", "Ride not found or you are not authorized to view it.");

				request.getRequestDispatcher("/rider/ride-tracker.jsp").forward(request, response);
				return;
			}

			Location pickup = locationService.getLocationById(ride.getPickupLocationId());

			Location destination = locationService.getLocationById(ride.getDestinationLocationId());

			request.setAttribute("ride", ride);

			request.setAttribute("pickupLocation", pickup != null ? pickup.getLocationName() : "Pickup unavailable");

			request.setAttribute("destinationLocation",
					destination != null ? destination.getLocationName() : "Destination unavailable");

			request.setAttribute("pickupCoordinates", pickup);
			request.setAttribute("destinationCoordinates", destination);

			request.getRequestDispatcher("/rider/ride-tracker.jsp").forward(request, response);

		} catch (NumberFormatException e) {
			request.setAttribute("error", "Invalid ride ID.");

			request.getRequestDispatcher("/rider/ride-tracker.jsp").forward(request, response);

		} catch (Exception e) {
			getServletContext().log("Unable to load ride tracker.", e);

			request.setAttribute("error", "Unable to load ride details. Please try again.");

			request.getRequestDispatcher("/rider/ride-tracker.jsp").forward(request, response);
		}
	}
}
