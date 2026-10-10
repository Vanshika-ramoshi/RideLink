package com.ridelink.controller;

import java.io.IOException;

import com.ridelink.model.User;
import com.ridelink.service.DriverService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/driver/status")
public class DriverStatusServlet extends HttpServlet {

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

		String status = request.getParameter("status");

		// Sirf ONLINE ya OFFLINE allowed
		if (status == null || (!status.equals("ONLINE") && !status.equals("OFFLINE"))) {

			session.setAttribute("driverError", "Invalid driver status.");

			response.sendRedirect(request.getContextPath() + "/driver/dashboard");

			return;
		}

		boolean updated = driverService.updateDriverStatus(user.getUserId(), status);

		if (updated) {

			session.setAttribute("driverSuccess", "You are now " + status + ".");

		} else {

			session.setAttribute("driverError", "Unable to update driver status.");
		}

		response.sendRedirect(request.getContextPath() + "/driver/dashboard");
	}
}