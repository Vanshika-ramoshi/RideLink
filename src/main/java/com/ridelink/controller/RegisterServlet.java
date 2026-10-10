package com.ridelink.controller;

import java.io.IOException;

import com.ridelink.model.User;
import com.ridelink.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	private UserService userService;

	@Override
	public void init() {
		userService = new UserService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");

		User user = new User();

		user.setName(name);
		user.setEmail(email);
		user.setPhone(phone);
		user.setPassword(password);

		boolean registered = userService.registerUser(user);

		if (registered) {
			response.sendRedirect("login.jsp");
		} else {
			request.setAttribute("error", "Registration failed. Please check your details.");
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}
	}
}
