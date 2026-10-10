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

@WebServlet("/vehicle/register")
public class VehicleRegisterServlet extends HttpServlet {

	private VehicleService vehicleService;
	private DriverService driverService;

	@Override
	public void init() {
		vehicleService = new VehicleService();
		driverService = new DriverService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		Driver driver = driverService.getDriverByUserId(user.getUserId());

		if (driver == null) {
			response.sendRedirect(request.getContextPath() + "/driver-register.jsp");
			return;
		}

		String vehicleNumber = request.getParameter("vehicleNumber");
		String vehicleType = request.getParameter("vehicleType");
		String model = request.getParameter("model");

		Vehicle vehicle = new Vehicle();

		vehicle.setDriverId(driver.getDriverId());
		vehicle.setVehicleNumber(vehicleNumber);
		vehicle.setVehicleType(vehicleType);
		vehicle.setModel(model);

		boolean registered = vehicleService.registerVehicle(vehicle);

		if (registered) {

			response.sendRedirect(request.getContextPath() + "/driver/dashboard");
//			request.getSession().setAttribute("vehicleSuccess", "Vehicle Registered Successfully!");

		} else {

			request.setAttribute("error", "Vehicle registration failed. Please check your details.");

			request.getRequestDispatcher("/driver/vehicle-register.jsp").forward(request, response);
		}



	}
}