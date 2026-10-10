package com.ridelink.model;

import java.sql.Timestamp;

public class Payment {

	private int paymentId;
	private int rideId;
	private double amount;
	private String paymentMethod;
	private String paymentStatus;
	private Timestamp paymentTime;

	public Payment() {
	}

	public Payment(int paymentId, int rideId, double amount, String paymentMethod, String paymentStatus,
			Timestamp paymentTime) {

		this.paymentId = paymentId;
		this.rideId = rideId;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.paymentTime = paymentTime;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getRideId() {
		return rideId;
	}

	public void setRideId(int rideId) {
		this.rideId = rideId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Timestamp getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Timestamp paymentTime) {
		this.paymentTime = paymentTime;
	}
}
