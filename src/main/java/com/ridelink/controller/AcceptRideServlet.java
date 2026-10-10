package com.ridelink.controller;

import java.io.IOException;

import com.ridelink.model.Driver;
import com.ridelink.model.User;
import com.ridelink.model.Vehicle;
import com.ridelink.service.DriverService;
import com.ridelink.service.RideService;
import com.ridelink.service.VehicleService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/driver/accept-ride")
public class AcceptRideServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private DriverService driverService;
	private VehicleService vehicleService;
	private RideService rideService;

	@Override
	public void init() throws ServletException {
		driverService = new DriverService();
		vehicleService = new VehicleService();
		rideService = new RideService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String dashboard = request.getContextPath() + "/driver/dashboard";
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		try {
			User user = (User) session.getAttribute("user");

			String rideIdParam = request.getParameter("rideId");

			if (rideIdParam == null || rideIdParam.trim().isEmpty()) {
				session.setAttribute("driverError", "Invalid ride selected.");
				response.sendRedirect(dashboard);
				return;
			}

			int rideId = Integer.parseInt(rideIdParam);

			Driver driver = driverService.getDriverByUserId(user.getUserId());

			if (driver == null) {
				session.setAttribute("driverError", "Please complete driver registration.");
				response.sendRedirect(dashboard);
				return;
			}

			Vehicle vehicle = vehicleService.getVehicleByDriverId(driver.getDriverId());

			if (vehicle == null || vehicle.getVehicleId() <= 0) {
				session.setAttribute("driverError", "Please register your vehicle first.");
				response.sendRedirect(dashboard);
				return;
			}

			boolean accepted = rideService.acceptRide(rideId, driver.getDriverId(), vehicle.getVehicleId());

			if (accepted) {
				session.setAttribute("driverSuccess", "Ride accepted successfully!");
			} else {
				session.setAttribute("driverError", "Unable to accept this ride. It may already have been accepted.");
			}

		} catch (NumberFormatException e) {
			session.setAttribute("driverError", "Invalid ride ID.");
		} catch (Exception e) {
			getServletContext().log("Error while accepting ride.", e);
			session.setAttribute("driverError", "Something went wrong while accepting the ride.");
		}

		response.sendRedirect(dashboard);
	}

}
