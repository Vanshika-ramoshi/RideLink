package com.ridelink.controller;

import java.io.IOException;
import java.util.List;

import com.ridelink.model.Driver;
import com.ridelink.model.Ride;
import com.ridelink.model.User;
import com.ridelink.service.DriverService;
import com.ridelink.service.RideService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/driver/ride-history")
public class DriverRideHistoryServlet extends HttpServlet {

	private DriverService driverService;
	private RideService rideService;

	@Override
	public void init() {
		driverService = new DriverService();
		rideService = new RideService();
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
			Driver driver = driverService.getDriverByUserId(user.getUserId());

			if (driver == null) {
				response.sendRedirect(request.getContextPath() + "/driver/entry");
				return;
			}

			List<Ride> rideHistory = rideService.getRidesByDriverId(driver.getDriverId());

			request.setAttribute("driver", driver);
			request.setAttribute("rideHistory", rideHistory);

			request.getRequestDispatcher("/driver/ride-history.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();

			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load ride history.");
		}
	}

}
