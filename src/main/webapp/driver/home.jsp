<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html lang="en">

<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>RideLink | Driver Dashboard</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">

<style>
* {
	box-sizing: border-box;
}

body {
	margin: 0;
	font-family: Arial, sans-serif;
	background: #f4f6f9;
	color: #222;
}

.rl-header {
	background: #101828;
	color: white;
	padding: 18px 6%;
	display: flex;
	justify-content: space-between;
	align-items: center;
	flex-wrap: wrap;
	gap: 15px;
}

.rl-logo {
	font-size: 25px;
	font-weight: bold;
	color: #ffc107;
}

.rl-nav {
	display: flex;
	gap: 20px;
	flex-wrap: wrap;
}

.rl-nav a {
	color: white;
	text-decoration: none;
	font-size: 14px;
}

.rl-nav a*:hover* {
	color: #ffc107;
}

.rl-container {
	width: 88%;
	max-width: 1200px;
	margin: 35px auto;
}

.rl-heading {
	margin-bottom: 25px;
}

.rl-heading h1 {
	margin-bottom: 8px;
	font-size: 30px;
}

.rl-heading p {
	color: #667085;
}

.rl-alert {
	padding: 14px 18px;
	border-radius: 8px;
	margin-bottom: 18px;
	font-size: 14px;
}

.rl-alert-success {
	background: #d1fae5;
	color: #065f46;
	border: 1px solid #a7f3d0;
}

.rl-alert-error {
	background: #fee2e2;
	color: #991b1b;
	border: 1px solid #fecaca;
}

.rl-grid {
	display: grid;
	grid-template-columns: repeat(2, minmax(0, 1fr));
	gap: 24px;
}

.rl-card {
	background: white;
	padding: 25px;
	border-radius: 12px;
	box-shadow: 0 3px 15px rgba(0, 0, 0, 0.06);
	min-width: 0;
}

.rl-card h2 {
	font-size: 21px;
	margin-top: 0;
	margin-bottom: 20px;
}

.rl-detail {
	display: flex;
	justify-content: space-between;
	gap: 15px;
	padding: 13px 0;
	border-bottom: 1px solid #eaecf0;
}

.rl-detail:last-child {
	border-bottom: none;
}

.rl-label {
	color: #667085;
	flex-shrink: 0;
}

.rl-value {
	font-weight: 600;
	text-align: right;
	overflow-wrap: anywhere;
}

.rl-status {
	display: inline-block;
	padding: 5px 12px;
	border-radius: 20px;
	background: #d1fae5;
	color: #065f46;
	font-size: 12px;
	font-weight: bold;
}

.rl-btn {
	display: inline-block;
	padding: 12px 18px;
	border-radius: 7px;
	border: none;
	text-decoration: none;
	font-size: 14px;
	font-weight: bold;
	cursor: pointer;
	text-align: center;
}

.rl-btn-yellow {
	background: #ffc107;
	color: #101828;
}

.rl-btn-yellow:hover {
	background: #e5ac00;
}

.rl-btn-dark {
	background: #101828;
	color: white;
}

.rl-btn-dark:hover {
	background: #263449;
}

.rl-empty {
	padding: 20px;
	background: #f9fafb;
	border: 1px dashed #d0d5dd;
	border-radius: 8px;
	color: #667085;
	text-align: center;
	margin-bottom: 18px;
}

.rl-actions {
	display: flex;
	flex-wrap: wrap;
	gap: 12px;
	margin-top: 20px;
}

.rl-footer {
	text-align: center;
	padding: 25px;
	color: #667085;
	font-size: 13px;
}

.rl-tracking-status {
	padding: 12px 14px;
	border-radius: 8px;
	background: #f9fafb;
	border: 1px solid #eaecf0;
	color: #475467;
	font-size: 14px;
	line-height: 1.6;
	margin-bottom: 15px;
}

.rl-tracking-status.active {
	background: #d1fae5;
	border-color: #a7f3d0;
	color: #065f46;
}

.rl-tracking-status.error {
	background: #fee2e2;
	border-color: #fecaca;
	color: #991b1b;
}

@media ( max-width : 768px) {
	.rl-grid {
		grid-template-columns: 1fr;
	}
	.rl-header {
		align-items: flex-start;
	}
	.rl-container {
		width: 92%;
	}
	.rl-heading h1 {
		font-size: 25px;
	}
}
</style>

</head>

<body>

	<header class="rl-header">

		<div class="rl-logo">RideLink</div>

		<nav class="rl-nav">

			<a href="${pageContext.request.contextPath}/driver/dashboard">Dashboard</a>

			<a href="${pageContext.request.contextPath}/driver/earnings">Earnings</a>

			<a href="${pageContext.request.contextPath}/driver/ride-history">Ride

				History</a> <a href="${pageContext.request.contextPath}/logout">Logout</a>

		</nav>

	</header>

	<main class="rl-container">

		<section class="rl-heading">

			<h1>Driver Dashboard</h1>

			<p>Manage your profile, vehicle details and rides.</p>

		</section>

		<c:if test="${not empty driverSuccess}">

			<div class="rl-alert rl-alert-success">

				<c:out value="${driverSuccess}" />

			</div>

		</c:if>

		<c:if test="${not empty driverError}">

			<div class="rl-alert rl-alert-error">

				<c:out value="${driverError}" />

			</div>

		</c:if>

		<c:if test="${not empty vehicleSuccess}">

			<div class="rl-alert rl-alert-success">

				<c:out value="${vehicleSuccess}" />

			</div>

		</c:if>

		<c:if test="${not empty vehicleError}">

			<div class="rl-alert rl-alert-error">

				<c:out value="${vehicleError}" />

			</div>

		</c:if>

		<c:if test="${not empty error}">

			<div class="rl-alert rl-alert-error">

				<c:out value="${error}" />

			</div>

		</c:if>

		<c:choose>

			<c:when test="${empty driver}">

				<section class="rl-card">

					<h2>Complete Your Driver Profile</h2>

					<p>Your driver details are not available yet. Please complete

						driver registration before accessing the dashboard.</p>

					<div class="rl-actions">

						<a href="${pageContext.request.contextPath}/driver/register"
							class="rl-btn rl-btn-yellow"> Register as Driver </a> <a
							href="${pageContext.request.contextPath}/"
							class="rl-btn rl-btn-dark"> Back to Home </a>

					</div>

				</section>

			</c:when>

			<c:otherwise>

				<c:if test="${not empty activeRideId}">

					<section class="rl-card" style="margin-bottom: 24px;">

						<h2>Live GPS Tracking</h2>

						<p>Share your GPS location while completing your assigned

							ride.</p>

						<div id="gpsStatus" class="rl-tracking-status">

							Ready to start location sharing for ride #

							<c:out value="${activeRideId}" />

							.

						</div>

						<div class="rl-actions">

							<button type="button" id="startGpsButton"
								class="rl-btn rl-btn-yellow">Start Live Tracking</button>

							<button type="button" id="stopGpsButton"
								class="rl-btn rl-btn-dark" disabled>Stop Live Tracking

							</button>

						</div>

					</section>

				</c:if>

				<div class="rl-grid">

					<section class="rl-card">

						<h2>Personal Information</h2>

						<div class="rl-detail">

							<span class="rl-label">Full Name</span> <span class="rl-value">

								<c:out value="${user.name}" />

							</span>

						</div>

						<div class="rl-detail">

							<span class="rl-label">Email</span> <span class="rl-value">

								<c:out value="${user.email}" />

							</span>

						</div>

						<div class="rl-detail">

							<span class="rl-label">Phone</span> <span class="rl-value">

								<c:out value="${user.phone}" />

							</span>

						</div>

						<div class="rl-detail">

							<span class="rl-label">User ID</span> <span class="rl-value">

								<c:out value="${driver.userId}" />

							</span>

						</div>

					</section>

					<section class="rl-card">

						<h2>Driver Information</h2>

						<div class="rl-detail">

							<span class="rl-label">Driver ID</span> <span class="rl-value">

								<c:out value="${driver.driverId}" />

							</span>

						</div>

						<div class="rl-detail">

							<span class="rl-label">License Number</span> <span
								class="rl-value"> <c:out value="${driver.licenseNumber}" />

							</span>

						</div>

						<div class="rl-detail">

							<span class="rl-label">Driver Status</span> <span
								class="rl-value"> <c:out value="${driver.status}" />

							</span>

						</div>

						<div class="rl-detail">

							<span class="rl-label">Rating</span> <span class="rl-value">

								<c:out value="${driver.rating}" />

							</span>

						</div>

					</section>

					<section class="rl-card">

						<h2>Vehicle Information</h2>

						<c:choose>

							<c:when test="${not empty vehicle}">

								<div class="rl-detail">

									<span class="rl-label">Vehicle Type</span> <span
										class="rl-value"> <c:out value="${vehicle.vehicleType}" />

									</span>

								</div>

								<div class="rl-detail">

									<span class="rl-label">Vehicle Number</span> <span
										class="rl-value"> <c:out
											value="${vehicle.vehicleNumber}" />

									</span>

								</div>

								<div class="rl-detail">

									<span class="rl-label">Vehicle Model</span> <span
										class="rl-value"> <c:out value="${vehicle.model}" />

									</span>

								</div>

								<div class="rl-detail">

									<span class="rl-label">Registration Status</span> <span
										class="rl-value"> <span class="rl-status">Registered</span>

									</span>

								</div>

								<div class="rl-actions">

									<a
										href="${pageContext.request.contextPath}/driver/vehicle-register"
										class="rl-btn rl-btn-yellow"> Update Vehicle </a>

								</div>

							</c:when>

							<c:otherwise>

								<div class="rl-empty">

									<p>No vehicle registered yet.</p>

									<p>Register your vehicle to start accepting rides.</p>

								</div>

								<div class="rl-actions">

									<a
										href="${pageContext.request.contextPath}/driver/vehicle-register"
										class="rl-btn rl-btn-yellow"> Register Vehicle </a>

								</div>

							</c:otherwise>

						</c:choose>

					</section>

					<section class="rl-card">

						<h2>Ride Management</h2>

						<p>View your ride history and manage your driver activities.</p>

						<div class="rl-actions">

							<a href="${pageContext.request.contextPath}/driver/ride-history"
								class="rl-btn rl-btn-dark"> View Ride History </a>

						</div>

					</section>

					<section class="rl-card">

						<h2>My Earnings</h2>

						<p>Check your ride earnings and payment details.</p>

						<div class="rl-detail">

							<span class="rl-label">Completed Rides</span> <span
								class="rl-value"> <c:out value="${completedRides}" />

							</span>

						</div>

						<div class="rl-detail">

							<span class="rl-label">Today's Earnings</span> <span
								class="rl-value"> ₹<c:out value="${todayEarnings}" />

							</span>

						</div>

						<div class="rl-actions">

							<a href="${pageContext.request.contextPath}/driver/earnings"
								class="rl-btn rl-btn-dark"> View Earnings </a>

						</div>

					</section>

				</div>

			</c:otherwise>

		</c:choose>

	</main>

	<footer class="rl-footer">

		<p>© 2026 RideLink. Smart Rides, Safer Journeys.</p>

	</footer>

	<c:if test="${not empty activeRideId and not empty driver}">

		<script>
			(function() {

				"use strict";

				const contextPath = "${pageContext.request.contextPath}";

				const rideId = Number("${activeRideId}");

				const startButton = document.getElementById("startGpsButton");

				const stopButton = document.getElementById("stopGpsButton");

				const statusElement = document.getElementById("gpsStatus");

				let watchId = null;

				let sendInProgress = false;

				let lastSentAt = 0;

				let stopped = false;

				function showStatus(message, type) {

					statusElement.textContent = message;

					statusElement.className = "rl-tracking-status";

					if (type) {

						statusElement.classList.add(type);

					}

				}

				async

				function sendLocation(position) {

					if (stopped || sendInProgress) {

						return;

					}

					const now = Date.now();

					if (now - lastSentAt < 4000) {

						return;

					}

					sendInProgress = true;

					lastSentAt = now;

					try {

						const body = new URLSearchParams();

						body.set("rideId", String(rideId));

						body.set("latitude", String(position.coords.latitude));

						body

						.set("longitude",

						String(position.coords.longitude));

						const response = await

						fetch(

								contextPath + "/driver-location",

								{

									method : "POST",

									credentials : "same-origin",

									headers : {

										"Content-Type" : "application/x-www-form-urlencoded;charset=UTF-8"

									},

									body : body.toString()

								});

						const data = await

						response.json();

						if (!response.ok || !data.success) {

							showStatus(data.message

							|| "Unable to update your location.",

							"error");

							stopTracking(false);

							return;

						}

						showStatus("Live location sharing is active. "

						+ "GPS accuracy: approximately "

						+ Math.round(position.coords.accuracy)

						+ " metres.", "active");

					} catch (error) {

						showStatus(

						"Could not contact the server. Check your connection.",

						"error");

					} finally {

						sendInProgress = false;

					}

				}

				function onLocationError(error) {

					let message = "Unable to obtain your location.";

					if (error.code === 1) {

						message = "Location permission was denied. Allow location access in your browser.";

					} else if (error.code === 2) {

						message = "Your current location is unavailable. Check device location settings.";

					} else if (error.code === 3) {

						message = "GPS request timed out. Try again.";

					}

					showStatus(message, "error");

					stopTracking(false);

				}

				function stopTracking(showMessage) {

					stopped = true;

					if (watchId !== null) {

						navigator.geolocation.clearWatch(watchId);

						watchId = null;

					}

					startButton.disabled = false;

					stopButton.disabled = true;

					if (showMessage) {

						showStatus("Live location sharing stopped.");

					}

				}

				function startTracking() {

					if (!Number.isInteger(rideId) || rideId <= 0) {

						showStatus("A valid active ride ID is required.",

						"error");

						return;

					}

					if (!navigator.geolocation) {

						showStatus(

						"This browser does not support GPS location.",

						"error");

						return;

					}

					if (!window.isSecureContext) {

						showStatus(

								"GPS requires HTTPS or localhost. Open RideLink using localhost during development.",

								"error");

						return;

					}

					stopped = false;

					startButton.disabled = true;

					stopButton.disabled = false;

					showStatus("Requesting GPS permission...");

					watchId = navigator.geolocation.watchPosition(sendLocation,

					onLocationError, {

						enableHighAccuracy : true,

						maximumAge : 3000,

						timeout : 15000

					});

				}

				startButton.addEventListener("click", startTracking);

				stopButton.addEventListener("click", function() {

					stopTracking(true);

				});

				window.addEventListener("pagehide", function() {

					if (watchId !== null) {

						navigator.geolocation.clearWatch(watchId);

						watchId = null;

					}

				});

			})();
		</script>

	</c:if>

</body>

</html>