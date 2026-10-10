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

@WebServlet("/driver/ride")
public class DriverRideServlet extends HttpServlet {

	private DriverService driverService;
	private VehicleService vehicleService;
	private RideService rideService;

	@Override
	public void init() {

		driverService = new DriverService();
		vehicleService = new VehicleService();
		rideService = new RideService();
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

			String action = request.getParameter("action");

			int rideId = Integer.parseInt(request.getParameter("rideId"));

			if (action == null || action.trim().isEmpty()) {

				session.setAttribute("driverError", "Invalid ride action.");

				response.sendRedirect(request.getContextPath() + "/driver/dashboard");

				return;
			}

			action = action.trim().toUpperCase();

			Driver driver = driverService.getDriverByUserId(user.getUserId());

			if (driver == null) {

				session.setAttribute("driverError", "Driver profile not found.");

				response.sendRedirect(request.getContextPath() + "/driver/dashboard");

				return;
			}

			boolean result = false;

			/*
			 * ACCEPT RIDE
			 */

			if ("ACCEPT".equals(action)) {

				String vehicleIdParameter = request.getParameter("vehicleId");

				if (vehicleIdParameter == null || vehicleIdParameter.trim().isEmpty()) {

					session.setAttribute("driverError", "Please register a vehicle before accepting rides.");

					response.sendRedirect(request.getContextPath() + "/driver/dashboard");

					return;
				}

				int vehicleId = Integer.parseInt(vehicleIdParameter);

				Vehicle vehicle = vehicleService.getVehicleByDriverId(driver.getDriverId());

				if (vehicle == null || vehicle.getVehicleId() != vehicleId) {

					session.setAttribute("driverError", "Invalid vehicle.");

					response.sendRedirect(request.getContextPath() + "/driver/dashboard");

					return;
				}

				if (!vehicle.getVehicleType()
						.equalsIgnoreCase(request.getParameter("vehicleType") == null ? vehicle.getVehicleType()
								: request.getParameter("vehicleType"))) {

					session.setAttribute("driverError", "Vehicle type does not match the ride.");

					response.sendRedirect(request.getContextPath() + "/driver/dashboard");

					return;
				}

				result = rideService.acceptRide(rideId, driver.getDriverId(), vehicleId);

				if (result) {

					session.setAttribute("driverSuccess", "Ride accepted successfully.");

				} else {

					session.setAttribute("driverError", "Unable to accept ride. It may already be accepted.");
				}
			}

			/*
			 * REJECT RIDE
			 */

			else if ("REJECT".equals(action)) {

				result = rideService.rejectRide(rideId);

				if (result) {

					session.setAttribute("driverSuccess", "Ride rejected successfully.");

				} else {

					session.setAttribute("driverError", "Unable to reject ride.");
				}
			}

			/*
			 * START RIDE
			 */

			else if ("START".equals(action)) {

				result = rideService.startRide(rideId, driver.getDriverId());

				if (result) {

					session.setAttribute("driverSuccess", "Ride started successfully.");

				} else {

					session.setAttribute("driverError", "Unable to start ride.");
				}
			}

			/*
			 * COMPLETE RIDE
			 */

			else if ("COMPLETE".equals(action)) {

				result = rideService.completeRide(rideId, driver.getDriverId());

				if (result) {

					session.setAttribute("driverSuccess", "Ride completed successfully.");

				} else {

					session.setAttribute("driverError", "Unable to complete ride.");
				}
			}

			/*
			 * INVALID ACTION
			 */

			else {

				session.setAttribute("driverError", "Invalid ride action.");
			}

			response.sendRedirect(request.getContextPath() + "/driver/dashboard");

		} catch (NumberFormatException e) {

			e.printStackTrace();

			session.setAttribute("driverError", "Invalid ride or vehicle ID.");

			response.sendRedirect(request.getContextPath() + "/driver/dashboard");

		} catch (Exception e) {

			e.printStackTrace();

			session.setAttribute("driverError", "Something went wrong while processing the ride.");

			response.sendRedirect(request.getContextPath() + "/driver/dashboard");
		}
	}
}
