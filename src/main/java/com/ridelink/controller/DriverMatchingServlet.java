package com.ridelink.controller;

import java.io.IOException;
import java.util.List;

import com.ridelink.model.Driver;
import com.ridelink.service.DriverService;
import com.ridelink.service.MatchingService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/driver/matching")
public class DriverMatchingServlet extends HttpServlet {

	private DriverService driverService;
	private MatchingService matchingService;

	@Override
	public void init() {

		driverService = new DriverService();

		matchingService = new MatchingService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String vehicleType = request.getParameter("vehicleType");

		if (vehicleType == null || vehicleType.trim().isEmpty()) {

			response.sendRedirect(request.getContextPath() + "/driver/dashboard.jsp");

			return;
		}

		List<Driver> drivers = driverService.findMatchingDrivers(vehicleType);

		List<Driver> rankedDrivers = matchingService.rankDrivers(drivers);

		request.setAttribute("matchingDrivers", rankedDrivers);

		request.setAttribute("vehicleType", vehicleType);

		request.getRequestDispatcher("/driver/matching.jsp").forward(request, response);
	}

}
