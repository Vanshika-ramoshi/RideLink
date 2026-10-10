package com.ridelink.service;

import com.ridelink.dao.RatingDAO;
import com.ridelink.model.Rating;

public class RatingService {

	private RatingDAO ratingDAO;

	public RatingService() {

		ratingDAO = new RatingDAO();
	}

	public boolean addRating(Rating rating) {

		if ((rating == null) || (rating.getRideId() <= 0) || (rating.getUserId() <= 0) || (rating.getDriverId() <= 0)) {
			return false;
		}

		if (rating.getRating() < 1 || rating.getRating() > 5) {

			return false;
		}

		if (rating.getReview() == null) {

			rating.setReview("");
		}

		if (rating.getReview().length() > 255) {

			rating.setReview(rating.getReview().substring(0, 255));
		}

		return ratingDAO.addRating(rating);
	}

	public Rating getRatingByRideId(int rideId) {

		if (rideId <= 0) {
			return null;
		}

		return ratingDAO.getRatingByRideId(rideId);
	}

	public double getAverageRatingByDriverId(int driverId) {

		if (driverId <= 0) {
			return 5.0;
		}

		return ratingDAO.getAverageRatingByDriverId(driverId);
	}

}
