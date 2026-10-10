package com.ridelink.controller;

import java.io.IOException;
import java.util.List;

import com.ridelink.model.Rating;
import com.ridelink.model.Ride;
import com.ridelink.model.User;
import com.ridelink.service.DriverService;
import com.ridelink.service.RatingService;
import com.ridelink.service.RideService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/rating")
public class RatingServlet extends HttpServlet {

	private RatingService ratingService;
	private RideService rideService;
	private DriverService driverService;

	@Override
	public void init() {

		ratingService = new RatingService();
		rideService = new RideService();
		driverService = new DriverService();
	}

// =========================
// OPEN RATING PAGE
// =========================

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null) {

			response.sendRedirect(request.getContextPath() + "/login.jsp");

			return;
		}

		User user = (User) session.getAttribute("user");

		if (user == null) {

			response.sendRedirect(request.getContextPath() + "/login.jsp");

			return;
		}

		try {

			int rideId = Integer.parseInt(request.getParameter("rideId"));

			Ride ride = findRide(rideId, user.getUserId());

			if ((ride == null) || !"COMPLETED".equalsIgnoreCase(ride.getRideStatus()) || (ride.getDriverId() <= 0)) {

				response.sendRedirect(request.getContextPath() + "/rider/dashboard");

				return;
			}

			Rating existingRating = ratingService.getRatingByRideId(rideId);

			if (existingRating != null) {

				session.setAttribute("ratingError", "This ride has already been rated.");

				response.sendRedirect(request.getContextPath() + "/rider/dashboard");

				return;
			}

			request.setAttribute("ride", ride);

			request.getRequestDispatcher("/rider/rating.jsp").forward(request, response);

		} catch (Exception e) {

			e.printStackTrace();

			response.sendRedirect(request.getContextPath() + "/rider/dashboard");
		}
	}

// =========================
// SUBMIT RATING
// =========================

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null) {

			response.sendRedirect(request.getContextPath() + "/login.jsp");

			return;
		}

		User user = (User) session.getAttribute("user");

		if (user == null) {

			response.sendRedirect(request.getContextPath() + "/login.jsp");

			return;
		}

		try {

			int rideId = Integer.parseInt(request.getParameter("rideId"));

			int ratingValue = Integer.parseInt(request.getParameter("rating"));

			String review = request.getParameter("review");

			Ride ride = findRide(rideId, user.getUserId());

			if (ride == null) {

				request.setAttribute("error", "Ride not found.");

				request.getRequestDispatcher("/rider/dashboard").forward(request, response);

				return;
			}

			if (!"COMPLETED".equalsIgnoreCase(ride.getRideStatus())) {

				request.setAttribute("error", "Only completed rides can be rated.");

				request.getRequestDispatcher("/rider/dashboard").forward(request, response);

				return;
			}

			if (ride.getDriverId() <= 0) {

				request.setAttribute("error", "No driver is associated with this ride.");

				request.getRequestDispatcher("/rider/dashboard").forward(request, response);

				return;
			}

			if (ratingValue < 1 || ratingValue > 5) {

				request.setAttribute("error", "Rating must be between 1 and 5.");

				request.setAttribute("ride", ride);

				request.getRequestDispatcher("/rider/rating.jsp").forward(request, response);

				return;
			}

			Rating existingRating = ratingService.getRatingByRideId(rideId);

			if (existingRating != null) {

				session.setAttribute("ratingError", "You have already rated this ride.");

				response.sendRedirect(request.getContextPath() + "/rider/dashboard");

				return;
			}

			Rating rating = new Rating();

			rating.setRideId(rideId);

			rating.setUserId(user.getUserId());

			rating.setDriverId(ride.getDriverId());

			rating.setRating(ratingValue);

			rating.setReview(review);

			boolean saved = ratingService.addRating(rating);

			if (saved) {

				// Calculate new driver average rating
				double averageRating = ratingService.getAverageRatingByDriverId(ride.getDriverId());

				// Update drivers.rating
				driverService.updateDriverRating(ride.getDriverId(), averageRating);

				session.setAttribute("ratingSuccess", "Thank you! Your rating has been submitted.");

				response.sendRedirect(request.getContextPath() + "/rider/dashboard");

			} else {

				request.setAttribute("error", "Unable to submit rating.");

				request.setAttribute("ride", ride);

				request.getRequestDispatcher("/rider/rating.jsp").forward(request, response);
			}

		} catch (NumberFormatException e) {

			request.setAttribute("error", "Invalid rating information.");

			response.sendRedirect(request.getContextPath() + "/rider/dashboard");

		} catch (Exception e) {

			e.printStackTrace();

			request.setAttribute("error", "Something went wrong.");

			response.sendRedirect(request.getContextPath() + "/rider/dashboard");
		}
	}

// =========================
// FIND USER'S RIDE
// =========================

	private Ride findRide(int rideId, int userId) {

		List<Ride> rides = rideService.getRidesByUserId(userId);

		for (Ride ride : rides) {

			if (ride.getRideId() == rideId) {

				return ride;
			}
		}

		return null;
	}

}
