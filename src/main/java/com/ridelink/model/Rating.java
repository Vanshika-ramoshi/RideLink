package com.ridelink.model;

public class Rating {

	private int ratingId;
	private int rideId;
	private int userId;
	private int driverId;
	private int rating;
	private String review;

	public Rating() {
	}

	public Rating(int ratingId, int rideId, int userId, int driverId, int rating, String review) {
		this.ratingId = ratingId;
		this.rideId = rideId;
		this.userId = userId;
		this.driverId = driverId;
		this.rating = rating;
		this.review = review;
	}

	public int getRatingId() {
		return ratingId;
	}

	public void setRatingId(int ratingId) {
		this.ratingId = ratingId;
	}

	public int getRideId() {
		return rideId;
	}

	public void setRideId(int rideId) {
		this.rideId = rideId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
}
