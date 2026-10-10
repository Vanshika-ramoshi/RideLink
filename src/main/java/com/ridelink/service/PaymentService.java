package com.ridelink.service;

import com.ridelink.dao.PaymentDAO;
import com.ridelink.model.Payment;

public class PaymentService {

	private PaymentDAO paymentDAO;

	public PaymentService() {
		paymentDAO = new PaymentDAO();
	}

	public boolean createPayment(Payment payment) {

		if ((payment == null) || (payment.getRideId() <= 0) || (payment.getAmount() <= 0)) {
			return false;
		}

		if (payment.getPaymentMethod() == null || payment.getPaymentMethod().trim().isEmpty() || !isValidPaymentMethod(payment.getPaymentMethod())) {
			return false;
		}

		payment.setPaymentStatus("PENDING");

		return paymentDAO.createPayment(payment);
	}

	public Payment getPaymentByRideId(int rideId) {

		if (rideId <= 0) {
			return null;
		}

		return paymentDAO.getPaymentByRideId(rideId);
	}

	public boolean updatePaymentStatus(int paymentId, String status) {

		if ((paymentId <= 0) || status == null || status.trim().isEmpty() || !isValidPaymentStatus(status)) {
			return false;
		}

		return paymentDAO.updatePaymentStatus(paymentId, status);
	}

	private boolean isValidPaymentMethod(String paymentMethod) {

		return paymentMethod.equalsIgnoreCase("CASH") || paymentMethod.equalsIgnoreCase("ONLINE");
	}

	private boolean isValidPaymentStatus(String status) {

		return status.equalsIgnoreCase("PENDING") || status.equalsIgnoreCase("PAID")
				|| status.equalsIgnoreCase("FAILED");
	}
}
