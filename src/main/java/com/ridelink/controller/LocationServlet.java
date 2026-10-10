package com.ridelink.controller;

import java.io.IOException;
import java.util.List;

import com.ridelink.model.Location;
import com.ridelink.service.LocationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/locations")
public class LocationServlet extends HttpServlet {

	private LocationService locationService;

	@Override
	public void init() {
		locationService = new LocationService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Location> locations = locationService.getAllLocations();

		System.out.println("Total locations received by Servlet: " + locations.size());

		request.setAttribute("locations", locations);

		request.getRequestDispatcher("/rider/booking.jsp").forward(request, response);
	}
}