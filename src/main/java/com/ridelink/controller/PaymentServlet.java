package com.ridelink.controller;

import java.io.IOException;

import com.ridelink.model.Payment;
import com.ridelink.model.Ride;
import com.ridelink.model.User;
import com.ridelink.service.PaymentService;
import com.ridelink.service.RideService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

	private PaymentService paymentService;
	private RideService rideService;

	@Override
	public void init() {
		paymentService = new PaymentService();
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

			int rideId = Integer.parseInt(request.getParameter("rideId"));

			Ride ride = null;

			for (Ride r : rideService.getRidesByUserId(user.getUserId())) {

				if (r.getRideId() == rideId) {
					ride = r;
					break;
				}
			}

			if (ride == null) {

				session.setAttribute("paymentError", "Ride not found.");

				response.sendRedirect(request.getContextPath() + "/rider/dashboard");

				return;
			}

			if (!"COMPLETED".equalsIgnoreCase(ride.getRideStatus())) {

				session.setAttribute("paymentError", "Payment is available only after ride completion.");

				response.sendRedirect(request.getContextPath() + "/rider/dashboard");

				return;
			}

			Payment existingPayment = paymentService.getPaymentByRideId(rideId);

			if (existingPayment != null && "PAID".equalsIgnoreCase(existingPayment.getPaymentStatus())) {

				session.setAttribute("paymentSuccess", "This ride has already been paid.");

				response.sendRedirect(request.getContextPath() + "/rider/dashboard");

				return;
			}

			request.setAttribute("ride", ride);

			request.setAttribute("payment", existingPayment);

			request.getRequestDispatcher("/payment/payment.jsp").forward(request, response);

		} catch (NumberFormatException e) {

			session.setAttribute("paymentError", "Invalid ride ID.");

			response.sendRedirect(request.getContextPath() + "/rider/dashboard");

		} catch (Exception e) {

			e.printStackTrace();

			session.setAttribute("paymentError", "Unable to load payment page.");

			response.sendRedirect(request.getContextPath() + "/rider/dashboard");
		}
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

			int rideId = Integer.parseInt(request.getParameter("rideId"));

			String paymentMethod = request.getParameter("paymentMethod");

			Ride ride = null;

			for (Ride r : rideService.getRidesByUserId(user.getUserId())) {

				if (r.getRideId() == rideId) {
					ride = r;
					break;
				}
			}

			if (ride == null) {

				session.setAttribute("paymentError", "Ride not found.");

				response.sendRedirect(request.getContextPath() + "/rider/dashboard");

				return;
			}

			if (!"COMPLETED".equalsIgnoreCase(ride.getRideStatus())) {

				session.setAttribute("paymentError", "Payment is available only for completed rides.");

				response.sendRedirect(request.getContextPath() + "/rider/dashboard");

				return;
			}

			if (paymentMethod == null || paymentMethod.trim().isEmpty()) {

				session.setAttribute("paymentError", "Please select a payment method.");

				response.sendRedirect(request.getContextPath() + "/payment?rideId=" + rideId);

				return;
			}

			paymentMethod = paymentMethod.trim().toUpperCase();

			if (!paymentMethod.equals("CASH") && !paymentMethod.equals("ONLINE")) {

				session.setAttribute("paymentError", "Invalid payment method.");

				response.sendRedirect(request.getContextPath() + "/payment?rideId=" + rideId);

				return;
			}

			Payment existingPayment = paymentService.getPaymentByRideId(rideId);

			if (existingPayment != null && "PAID".equalsIgnoreCase(existingPayment.getPaymentStatus())) {

				session.setAttribute("paymentSuccess", "This ride has already been paid.");

				response.sendRedirect(request.getContextPath() + "/rider/dashboard");

				return;
			}

			Payment payment = new Payment();

			payment.setRideId(rideId);

			payment.setAmount(ride.getFare());

			payment.setPaymentMethod(paymentMethod);

			payment.setPaymentStatus("PENDING");

			boolean created = paymentService.createPayment(payment);

			if (!created) {

				session.setAttribute("paymentError", "Unable to create payment.");

				response.sendRedirect(request.getContextPath() + "/payment?rideId=" + rideId);

				return;
			}

			boolean paid = paymentService.updatePaymentStatus(rideId, "PAID");

			if (paid) {

				session.setAttribute("paymentSuccess", "Payment completed successfully.");

			} else {

				session.setAttribute("paymentError", "Payment created but status update failed.");
			}

			response.sendRedirect(request.getContextPath() + "/rider/dashboard");

		} catch (NumberFormatException e) {

			session.setAttribute("paymentError", "Invalid ride ID.");

			response.sendRedirect(request.getContextPath() + "/rider/dashboard");

		} catch (Exception e) {

			e.printStackTrace();

			session.setAttribute("paymentError", "Something went wrong during payment.");

			response.sendRedirect(request.getContextPath() + "/rider/dashboard");
		}
	}
}