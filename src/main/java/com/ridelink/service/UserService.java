package com.ridelink.service;

import com.ridelink.dao.UserDAO;
import com.ridelink.model.User;

public class UserService {

	private UserDAO userDAO;

	public UserService() {
		userDAO = new UserDAO();
	}

	public boolean registerUser(User user) {

		if (user.getName() == null || user.getName().trim().isEmpty() || user.getEmail() == null || user.getEmail().trim().isEmpty()) {
			return false;
		}

		if (user.getPhone() == null || user.getPhone().trim().isEmpty() || user.getPassword() == null || user.getPassword().trim().isEmpty()) {
			return false;
		}

		return userDAO.registerUser(user);
	}

	public User loginUser(String email, String password) {

		if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			return null;
		}

		return userDAO.loginUser(email, password);
	}
}
