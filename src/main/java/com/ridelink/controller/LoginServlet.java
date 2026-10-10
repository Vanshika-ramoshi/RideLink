package com.ridelink.controller;

import java.io.IOException;

import com.ridelink.model.User;
import com.ridelink.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private UserService userService;

	@Override
	public void init() {
		userService = new UserService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User user = userService.loginUser(email, password);

		if (user != null) {

			HttpSession session = request.getSession();

			session.setAttribute("user", user);

			response.sendRedirect(request.getContextPath() + "/rider/dashboard.jsp");

		} else {

			request.setAttribute("error", "Invalid email or password.");

			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
}