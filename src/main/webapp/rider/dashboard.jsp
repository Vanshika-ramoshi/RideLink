<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%
if (request.getAttribute("locations") == null) {
	request.setAttribute("locations", new com.ridelink.service.LocationService().getAllLocations());
}
%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Rider Dashboard - RideLink</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css?v=4">
</head>

<body class="rl-dashboard">

	<nav class="rl-navbar">
		<div class="rl-nav-inner">

			<a href="${pageContext.request.contextPath}/rider/dashboard"
				class="rl-logo"> <span class="rl-logo-mark">R</span> RideLink
			</a>

			<div class="rl-nav-links">
				<a href="${pageContext.request.contextPath}/rider/dashboard">Dashboard</a>
				<a href="${pageContext.request.contextPath}/driver/entry">Become
					Driver</a> <a href="${pageContext.request.contextPath}/logout">Logout</a>
			</div>

		</div>
	</nav>

	<header class="rl-dashboard-header">
		<div class="rl-container rl-dashboard-head">

			<div class="rl-dashboard-title">
				<h1>
					Welcome,
					<c:out value="${sessionScope.user.name}" />
				</h1>
				<p>Where are you going today?</p>
			</div>

		</div>
	</header>

	<main class="rl-main">

		<div class="rl-container">

			<!-- Success Messages -->

			<c:if test="${not empty bookingSuccess}">
				<div class="rl-alert rl-alert-success">
					<c:out value="${bookingSuccess}" />
				</div>
			</c:if>

			<c:if test="${not empty paymentSuccess}">
				<div class="rl-alert rl-alert-success">
					<c:out value="${paymentSuccess}" />
				</div>
			</c:if>

			<c:if test="${not empty ratingSuccess}">
				<div class="rl-alert rl-alert-success">
					<c:out value="${ratingSuccess}" />
				</div>
			</c:if>

			<!-- Error Messages -->

			<c:if test="${not empty error}">
				<div class="rl-alert rl-alert-error">
					<c:out value="${error}" />
				</div>
			</c:if>

			<c:if test="${not empty bookingError}">
				<div class="rl-alert rl-alert-error">
					<c:out value="${bookingError}" />
				</div>
			</c:if>

			<c:if test="${not empty paymentError}">
				<div class="rl-alert rl-alert-error">
					<c:out value="${paymentError}" />
				</div>
			</c:if>

			<c:if test="${not empty ratingError}">
				<div class="rl-alert rl-alert-error">
					<c:out value="${ratingError}" />
				</div>
			</c:if>

			<div class="rl-dashboard-grid">

				<!-- BOOKING SECTION -->

				<section class="rl-card">

					<div class="rl-card-title">
						<div>
							<h2>Book a ride</h2>
							<span>Choose your route and vehicle</span>
						</div>
					</div>

					<c:if test="${empty locations}">
						<div class="rl-alert rl-alert-error">No locations found.
							Please check the locations table in the database.</div>
					</c:if>

					<form id="bookingForm"
						action="${pageContext.request.contextPath}/ride" method="post"
						class="rl-booking-form">

						<!-- LOCATION -->

						<div class="rl-location-row">

							<!-- PICKUP -->

							<div>
								<label class="rl-form-group" for="pickup"> <strong>Pickup</strong>
								</label> <select name="pickupLocationId" id="pickup" class="rl-select"
									required>

									<option value="">Select pickup location</option>

									<c:forEach var="location" items="${locations}">
										<option value="${location.locationId}"
											data-lat="${location.latitude}"
											data-lng="${location.longitude}">

											<c:out value="${location.locationName}" />,
											<c:out value="${location.city}" />

										</option>
									</c:forEach>

								</select>
							</div>

							<!-- DESTINATION -->

							<div>
								<label class="rl-form-group" for="destination"> <strong>Destination</strong>
								</label> <select name="destinationLocationId" id="destination"
									class="rl-select" required>

									<option value="">Select destination</option>

									<c:forEach var="location" items="${locations}">
										<option value="${location.locationId}"
											data-lat="${location.latitude}"
											data-lng="${location.longitude}">

											<c:out value="${location.locationName}" />,
											<c:out value="${location.city}" />

										</option>
									</c:forEach>

								</select>
							</div>

						</div>

						<!-- VEHICLE -->

						<div>

							<label class="rl-form-group"> <strong>Select
									vehicle</strong>
							</label>

							<div class="rl-vehicle-grid">

								<div class="rl-vehicle-option">
									<input type="radio" id="bike" name="vehicleType" value="BIKE"
										required> <label for="bike" class="rl-vehicle-label">
										<strong>Bike</strong> <small>₹10 / km</small>
									</label>
								</div>

								<div class="rl-vehicle-option">
									<input type="radio" id="auto" name="vehicleType" value="AUTO">

									<label for="auto" class="rl-vehicle-label"> <strong>Auto</strong>
										<small>₹15 / km</small>
									</label>
								</div>

								<div class="rl-vehicle-option">
									<input type="radio" id="car" name="vehicleType" value="CAR">

									<label for="car" class="rl-vehicle-label"> <strong>Car</strong>
										<small>₹20 / km</small>
									</label>
								</div>

							</div>

						</div>

						<!-- FARE -->

						<div class="rl-fare-box">

							<div>
								<strong>Estimated fare</strong> <small id="distance">Select
									locations</small>
							</div>

							<div id="fare" class="rl-fare-price">₹0</div>

						</div>

						<!-- BOOK BUTTON -->

						<button type="submit" class="rl-btn rl-btn-yellow">Book
							Ride</button>

					</form>

				</section>

				<!-- CURRENT RIDE -->

				<aside>

					<c:if test="${not empty currentRide}">

						<div class="rl-card">

							<div class="rl-card-title">
								<h3>Current ride</h3>

								<span class="rl-status rl-status-${currentRide.rideStatus}">
									<c:out value="${currentRide.rideStatus}" />
								</span>
							</div>

							<div class="rl-route">

								<div class="rl-route-line">
									<span class="rl-dot"></span>
									<c:out value="${currentRide.pickupLocationName}" />
								</div>

								<div class="rl-route-line">
									<span class="rl-dot rl-dot-yellow"></span>
									<c:out value="${currentRide.destinationLocationName}" />
								</div>

							</div>

							<div class="rl-ride-meta">

								<span> Ride #<c:out value="${currentRide.rideId}" />
								</span> <span> <c:out value="${currentRide.vehicleType}" />
								</span> <span> ₹<c:out value="${currentRide.fare}" />
								</span>

							</div>

							<div class="rl-ride-actions">

								<a class="rl-btn rl-btn-black"
									href="${pageContext.request.contextPath}/ride/status">
									Track Ride </a>

							</div>

						</div>

					</c:if>

					<c:if test="${empty currentRide}">

						<div class="rl-card">

							<div class="rl-card-title">
								<h3>Quick access</h3>
							</div>

							<div class="rl-empty">
								No active ride right now. <br> <br> Book your next
								ride from the booking section.
							</div>

						</div>

					</c:if>

				</aside>

			</div>

			<!-- RIDE HISTORY -->

			<section class="rl-card" style="margin-top: 22px;">

				<div class="rl-card-title">

					<div>
						<h2>Ride history</h2>
						<span>Your previous rides</span>
					</div>

				</div>

				<c:choose>

					<c:when test="${empty rideHistory}">

						<div class="rl-empty">No rides found.</div>

					</c:when>

					<c:otherwise>

						<c:forEach var="ride" items="${rideHistory}">

							<div class="rl-ride-card">

								<div class="rl-ride-top">

									<strong> Ride #<c:out value="${ride.rideId}" />
									</strong> <span class="rl-status rl-status-${ride.rideStatus}"> <c:out
											value="${ride.rideStatus}" />
									</span>

								</div>

								<div class="rl-route">

									<div class="rl-route-line">
										<span class="rl-dot"></span>
										<c:out value="${ride.pickupLocationName}" />
									</div>

									<div class="rl-route-line">
										<span class="rl-dot rl-dot-yellow"></span>
										<c:out value="${ride.destinationLocationName}" />
									</div>

								</div>

								<div class="rl-ride-meta">

									<span> <c:out value="${ride.vehicleType}" />
									</span> <span> ₹<c:out value="${ride.fare}" />
									</span>

								</div>

								<!-- COMPLETED RIDE ACTIONS -->

								<c:if test="${ride.rideStatus eq 'COMPLETED'}">

									<div class="rl-ride-actions">

										<a
											href="${pageContext.request.contextPath}/payment?rideId=${ride.rideId}"
											class="rl-mini-btn"> Pay / View Payment </a>

										<c:if test="${not ride.rated}">

											<a
												href="${pageContext.request.contextPath}/rating?rideId=${ride.rideId}"
												class="rl-mini-btn"> Rate Driver </a>

										</c:if>

									</div>

								</c:if>

							</div>

						</c:forEach>

					</c:otherwise>

				</c:choose>

			</section>

		</div>

	</main>

	<footer class="rl-footer">

		<div class="rl-container rl-footer-inner">

			<div>

				<div class="rl-logo">
					<span class="rl-logo-mark">R</span> RideLink
				</div>

				<p>Smart rides. Simple journeys.</p>

			</div>

		</div>

	</footer>

	<script src="${pageContext.request.contextPath}/js/script.js?v=4"></script>
	<script src="${pageContext.request.contextPath}/js/booking.js?v=1"></script>

</body>

</html>
