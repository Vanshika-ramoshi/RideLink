<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>RideLink - Smart Ride Booking</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css?v=3">

</head>

<body>

	<nav class="rl-navbar">

		<div class="rl-nav-inner">

			<a href="${pageContext.request.contextPath}/index.jsp"
				class="rl-logo"> <span class="rl-logo-mark">R</span> RideLink

			</a>

			<div class="rl-nav-links">

				<a href="#how">How it works</a> <a href="#features">Features</a> <a
					href="${pageContext.request.contextPath}/login.jsp"> Login </a> <a
					href="${pageContext.request.contextPath}/register.jsp"
					class="rl-nav-btn"> Get Started </a>

			</div>

		</div>

	</nav>


	<section class="rl-hero">

		<div class="rl-hero-grid">

			<div>

				<h1>Your ride. Your way.</h1>

				<p>RideLink makes everyday travel simple with smart driver
					matching, transparent fares and a smooth ride-booking experience.</p>

				<div class="rl-hero-actions">

					<a href="${pageContext.request.contextPath}/register.jsp"
						class="rl-btn rl-btn-black"> Create Account </a> <a
						href="${pageContext.request.contextPath}/login.jsp"
						class="rl-btn rl-btn-outline"> Login </a>

				</div>

			</div>


			<div class="rl-hero-card">

				<h3>Book your ride</h3>

				<p>Choose your route and vehicle. RideLink calculates your fare
					by distance.</p>

				<div class="rl-feature-row">

					<div class="rl-feature">
						<span>↗</span> <strong>Smart Match</strong>
					</div>

					<div class="rl-feature">
						<span>₹</span> <strong>Fair Fare</strong>
					</div>

					<div class="rl-feature">
						<span>✓</span> <strong>Easy Booking</strong>
					</div>

				</div>

			</div>

		</div>

	</section>


	<section class="rl-section" id="features">

		<div class="rl-container">

			<div class="rl-section-title">

				<h2>Built for everyday rides.</h2>

				<p>Everything you need to book, manage and complete your ride in
					one place.</p>

			</div>


			<div class="rl-three-grid">

				<div class="rl-info-card">

					<div class="rl-info-number">01</div>

					<h3>Smart Driver Matching</h3>

					<p>Find available drivers based on vehicle type, availability,
						rating and distance.</p>

				</div>


				<div class="rl-info-card">

					<div class="rl-info-number">02</div>

					<h3>Transparent Fare</h3>

					<p>Bike ₹10/km, Auto ₹15/km and Car ₹20/km. No hidden platform
						or time charges.</p>

				</div>


				<div class="rl-info-card">

					<div class="rl-info-number">03</div>

					<h3>Complete Ride Flow</h3>

					<p>Book, accept, start, complete, pay and rate your ride
						through one platform.</p>

				</div>

			</div>

		</div>

	</section>


	<section class="rl-section" id="how">

		<div class="rl-container">

			<div class="rl-section-title">

				<h2>How RideLink works</h2>

				<p>A simple process from booking to destination.</p>

			</div>


			<div class="rl-three-grid">

				<div class="rl-info-card">
					<div class="rl-info-number">01</div>
					<h3>Choose your route</h3>
					<p>Select your pickup and destination.</p>
				</div>

				<div class="rl-info-card">
					<div class="rl-info-number">02</div>
					<h3>Choose a vehicle</h3>
					<p>Select Bike, Auto or Car.</p>
				</div>

				<div class="rl-info-card">
					<div class="rl-info-number">03</div>
					<h3>Enjoy the ride</h3>
					<p>Your driver accepts the ride and completes your journey.</p>
				</div>

			</div>

		</div>

	</section>


	<footer class="rl-footer">

		<div class="rl-container rl-footer-inner">

			<div>

				<div class="rl-logo">
					<span class="rl-logo-mark">R</span> RideLink
				</div>

				<p>Smart rides. Simple journeys.</p>

			</div>

			<div class="rl-footer-links">
				<span>Rider</span> <span>Driver</span> <span>RideLink</span>
			</div>

		</div>

	</footer>


	<script src="${pageContext.request.contextPath}/js/script.js?v=3"></script>

</body>
</html>