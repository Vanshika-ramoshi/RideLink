package com.ridelink.controller;

import java.io.IOException;

import com.ridelink.model.Ride;
import com.ridelink.model.User;
import com.ridelink.service.RideService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ride/status")
public class RideStatusServlet extends HttpServlet {

	private RideService rideService;

	@Override
	public void init() {
		rideService = new RideService();
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

			Ride currentRide = rideService.getCurrentRideByUserId(userId);

			if (currentRide == null) {

				request.setAttribute("message", "You don't have an active ride.");

				request.getRequestDispatcher("/rider/current-ride.jsp").forward(request, response);

				return;
			}

			request.setAttribute("currentRide", currentRide);

			request.getRequestDispatcher("/rider/current-ride.jsp").forward(request, response);

		} catch (Exception e) {

			e.printStackTrace();

			request.setAttribute("error", "Unable to load current ride.");

			request.getRequestDispatcher("/rider/current-ride.jsp").forward(request, response);
		}
	}
}
