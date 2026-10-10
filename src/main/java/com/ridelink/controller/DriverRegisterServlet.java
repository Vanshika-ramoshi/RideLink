package com.ridelink.controller;

import java.io.IOException;

import com.ridelink.model.Driver;
import com.ridelink.model.User;
import com.ridelink.service.DriverService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/driver/register")
public class DriverRegisterServlet extends HttpServlet {

	private DriverService driverService;

	@Override
	public void init() {
		driverService = new DriverService();
	}

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

			int userId = user.getUserId();

			if (driverService.getDriverByUserId(userId) != null) {

				session.setAttribute("driverError", "You are already registered as a driver.");

				response.sendRedirect(request.getContextPath() + "/driver/dashboard");

				return;
			}

			String licenseNumber = request.getParameter("licenseNumber");

			Driver driver = new Driver();

			driver.setUserId(userId);
			driver.setLicenseNumber(licenseNumber);
			driver.setStatus("OFFLINE");
			driver.setRating(5.0);

			boolean registered = driverService.registerDriver(driver);

			if (registered) {

				session.setAttribute("driverSuccess", "Driver registration successful.");

				response.sendRedirect(request.getContextPath() + "/driver/dashboard");

			} else {

				request.setAttribute("error", "Driver registration failed. License number may already exist.");

				request.getRequestDispatcher("/driver/register.jsp").forward(request, response);
			}

		} catch (Exception e) {

			e.printStackTrace();

			request.setAttribute("error", "Unable to register driver.");

			request.getRequestDispatcher("/driver/register.jsp").forward(request, response);
		}
	}
}