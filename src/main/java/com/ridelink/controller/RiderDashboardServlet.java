package com.ridelink.controller;

import java.io.IOException;
import java.util.List;

import com.ridelink.model.Payment;
import com.ridelink.model.Rating;
import com.ridelink.model.Ride;
import com.ridelink.model.User;
import com.ridelink.service.LocationService;
import com.ridelink.service.PaymentService;
import com.ridelink.service.RatingService;
import com.ridelink.service.RideService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/rider/dashboard")
public class RiderDashboardServlet extends HttpServlet {

	private LocationService locationService;
	private RideService rideService;
	private PaymentService paymentService;
	private RatingService ratingService;

	@Override
	public void init() {

		locationService = new LocationService();
		rideService = new RideService();
		paymentService = new PaymentService();
		ratingService = new RatingService();
	}

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

			int userId = user.getUserId();

			// =========================
			// LOAD LOCATIONS
			// =========================

			request.setAttribute("locations", locationService.getAllLocations());

			// =========================
			// CURRENT RIDE
			// =========================

			Ride currentRide = rideService.getCurrentRideByUserId(userId);

			request.setAttribute("currentRide", currentRide);

			// =========================
			// RIDE HISTORY
			// =========================

			List<Ride> rideHistory = rideService.getRidesByUserId(userId);

			// =========================
			// PAYMENT + RATING STATUS
			// =========================

			if (rideHistory != null) {

				for (Ride ride : rideHistory) {

					if ("COMPLETED".equalsIgnoreCase(ride.getRideStatus())) {

						Payment payment = paymentService.getPaymentByRideId(ride.getRideId());

						if (payment != null) {

							ride.setPaymentStatus(payment.getPaymentStatus());

						} else {

							ride.setPaymentStatus(null);
						}

						Rating rating = ratingService.getRatingByRideId(ride.getRideId());

						ride.setRated(rating != null);

					} else {

						ride.setPaymentStatus(null);
						ride.setRated(false);
					}
				}
			}

			request.setAttribute("rideHistory", rideHistory);

			// =========================
			// SUCCESS / ERROR MESSAGES
			// =========================

			moveSessionMessage(session, request, "bookingSuccess");

			moveSessionMessage(session, request, "paymentSuccess");

			moveSessionMessage(session, request, "ratingSuccess");

			moveSessionMessage(session, request, "ratingError");

			moveSessionMessage(session, request, "paymentError");

			moveSessionMessage(session, request, "driverSuccess");

			moveSessionMessage(session, request, "driverError");

			// =========================
			// OPEN DASHBOARD
			// =========================

			request.getRequestDispatcher("/rider/dashboard.jsp").forward(request, response);

		} catch (Exception e) {

			e.printStackTrace();

			request.setAttribute("error", "Unable to load rider dashboard.");

			request.getRequestDispatcher("/rider/dashboard.jsp").forward(request, response);
		}
	}

	private void moveSessionMessage(HttpSession session, HttpServletRequest request, String attributeName) {

		Object message = session.getAttribute(attributeName);

		if (message != null) {

			request.setAttribute(attributeName, message);

			session.removeAttribute(attributeName);
		}
	}
}