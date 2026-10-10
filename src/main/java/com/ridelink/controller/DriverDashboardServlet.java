package com.ridelink.controller;

import java.io.IOException;
import java.util.List;

import com.ridelink.model.Driver;
import com.ridelink.model.Ride;
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

@WebServlet("/driver/dashboard")
public class DriverDashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

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

			Driver driver = driverService.getDriverByUserId(userId);

			if (driver == null) {
				response.sendRedirect(request.getContextPath() + "/driver/entry");
				return;
			}

			Vehicle vehicle = vehicleService.getVehicleByDriverId(driver.getDriverId());

			String vehicleType = vehicle != null ? vehicle.getVehicleType() : "";

			List<Ride> requestedRides = rideService.getRequestedRides(vehicleType);

			List<Ride> activeRides = rideService.getActiveRidesByDriverId(driver.getDriverId());

			List<Ride> rideHistory = rideService.getRidesByDriverId(driver.getDriverId());

			int completedRides = rideService.getCompletedRideCountByDriverId(driver.getDriverId());

			double todayEarnings = rideService.getTodayEarningsByDriverId(driver.getDriverId());

			// Existing dashboard attributes
			request.setAttribute("user", user);
			request.setAttribute("driver", driver);
			request.setAttribute("vehicle", vehicle);

			request.setAttribute("requestedRides", requestedRides);
			request.setAttribute("activeRides", activeRides);
			request.setAttribute("rideHistory", rideHistory);

			request.setAttribute("completedRides", completedRides);
			request.setAttribute("todayEarnings", todayEarnings);

			// Active ride ID for live GPS tracking
			Integer activeRideId = null;

			if (activeRides != null && !activeRides.isEmpty()) {
				for (Ride ride : activeRides) {
					if (ride != null && ("ACCEPTED".equalsIgnoreCase(ride.getRideStatus())
							|| "ONGOING".equalsIgnoreCase(ride.getRideStatus()))) {

						activeRideId = ride.getRideId();
						break;
					}
				}
			}

			request.setAttribute("activeRideId", activeRideId);

			// One-time success message
			if (session.getAttribute("driverSuccess") != null) {
				request.setAttribute("driverSuccess", session.getAttribute("driverSuccess"));

				session.removeAttribute("driverSuccess");
			}

			// One-time error message
			if (session.getAttribute("driverError") != null) {
				request.setAttribute("driverError", session.getAttribute("driverError"));

				session.removeAttribute("driverError");
			}

			request.getRequestDispatcher("/driver/home.jsp").forward(request, response);

		} catch (Exception e) {
			getServletContext().log("Unable to load driver dashboard.", e);

			request.setAttribute("error", "Unable to load driver home.");

			request.getRequestDispatcher("/driver/home.jsp").forward(request, response);
		}
	}
}