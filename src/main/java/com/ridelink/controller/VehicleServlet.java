package com.ridelink.controller;

import java.io.IOException;

import com.ridelink.model.Driver;
import com.ridelink.model.User;
import com.ridelink.model.Vehicle;
import com.ridelink.service.DriverService;
import com.ridelink.service.VehicleService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/driver/vehicle-register")
public class VehicleServlet extends HttpServlet {

	private DriverService driverService;
	private VehicleService vehicleService;

	@Override
	public void init() {
		driverService = new DriverService();
		vehicleService = new VehicleService();
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

		Driver driver = driverService.getDriverByUserId(user.getUserId());

		if (driver == null) {
			response.sendRedirect(request.getContextPath() + "/driver/entry");
			return;
		}

		Vehicle vehicle = vehicleService.getVehicleByDriverId(driver.getDriverId());

		request.setAttribute("vehicle", vehicle);

		request.getRequestDispatcher("/driver/vehicle-register.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

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

			String vehicleNumber = request.getParameter("vehicleNumber");

			String vehicleType = request.getParameter("vehicleType");

			String model = request.getParameter("model");

			if (vehicleNumber == null || vehicleType == null || model == null || vehicleNumber.trim().isEmpty()
					|| vehicleType.trim().isEmpty() || model.trim().isEmpty()) {

				request.setAttribute("vehicleError", "Please fill in all vehicle details.");

				request.getRequestDispatcher("/driver/vehicle-register.jsp").forward(request, response);

				return;
			}

			Vehicle existingVehicle = vehicleService.getVehicleByDriverId(driver.getDriverId());

			Vehicle vehicle = new Vehicle();

			vehicle.setDriverId(driver.getDriverId());
			vehicle.setVehicleNumber(vehicleNumber.trim());
			vehicle.setVehicleType(vehicleType.trim());
			vehicle.setModel(model.trim());

			boolean success;

			if (existingVehicle != null) {
				session.setAttribute("vehicleError",
						"A vehicle is already registered. Update functionality needs to be implemented separately.");

				response.sendRedirect(request.getContextPath() + "/driver/dashboard");

				return;
			} else {
				success = vehicleService.registerVehicle(vehicle);
			}

			if (success) {
				session.setAttribute("driverSuccess", "Vehicle registered successfully!");
			} else {
				session.setAttribute("driverError",
						"Vehicle registration failed. Please check your details and try again.");
			}

			response.sendRedirect(request.getContextPath() + "/driver/dashboard");

		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute("vehicleError", "Unable to register vehicle. Please check the server console.");

			request.getRequestDispatcher("/driver/vehicle-register.jsp").forward(request, response);
		}
	}
}
