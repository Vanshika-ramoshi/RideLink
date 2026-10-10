<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Register Vehicle | RideLink</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css?v=8">

<style>
.vehicle-container {
	width: 92%;
	max-width: 650px;
	margin: 40px auto;
}

.vehicle-panel {
	background: #fff;
	padding: 30px;
	border-radius: 14px;
	box-shadow: 0 5px 25px rgba(16, 24, 40, 0.08);
}

.vehicle-panel h2 {
	margin-top: 0;
	margin-bottom: 24px;
	color: #101828;
}

.vehicle-group {
	margin-bottom: 20px;
}

.vehicle-group label {
	display: block;
	margin-bottom: 8px;
	font-weight: 600;
	color: #344054;
}

.vehicle-group input, .vehicle-group select {
	width: 100%;
	padding: 13px;
	border: 1px solid #d0d5dd;
	border-radius: 8px;
	font-size: 15px;
	background: #fff;
}

.vehicle-group input:focus, .vehicle-group select:focus {
	outline: none;
	border-color: #e5ac00;
	box-shadow: 0 0 0 3px rgba(255, 193, 7, 0.15);
}

.vehicle-actions {
	display: flex;
	flex-direction: column;
	gap: 12px;
	margin-top: 25px;
}

.vehicle-actions a {
	text-align: center;
	text-decoration: none;
}

.vehicle-message {
	padding: 14px;
	margin-bottom: 20px;
	border-radius: 8px;
	background: #fee2e2;
	color: #991b1b;
}

.vehicle-success {
	padding: 14px;
	margin-bottom: 20px;
	border-radius: 8px;
	background: #d1fae5;
	color: #065f46;
}

.vehicle-detail {
	padding: 14px 0;
	border-bottom: 1px solid #eaecf0;
	display: flex;
	justify-content: space-between;
	gap: 15px;
	overflow-wrap: anywhere;
}

.vehicle-detail span:first-child {
	color: #667085;
}

.vehicle-detail span:last-child {
	font-weight: 600;
	text-align: right;
}

@media ( max-width : 480px) {
	.vehicle-panel {
		padding: 20px;
	}
	.vehicle-detail {
		flex-direction: column;
		gap: 5px;
	}
	.vehicle-detail span:last-child {
		text-align: left;
	}
}
</style>
</head>

<body class="rl-dashboard-page">

	<header class="rl-header">
		<div class="rl-header-inner">

			<a href="${pageContext.request.contextPath}/driver/dashboard"
				class="rl-logo"> <span class="rl-logo-mark">R</span> <span>RideLink</span>
			</a>

			<nav class="rl-nav">
				<a href="${pageContext.request.contextPath}/driver/dashboard">
					Dashboard </a> <a href="${pageContext.request.contextPath}/logout">
					Logout </a>
			</nav>

		</div>
	</header>

	<main class="vehicle-container">

		<section class="rl-section">

			<div class="rl-dashboard-heading">
				<div>
					<p class="rl-eyebrow">DRIVER PORTAL</p>
					<h1>Vehicle Information</h1>
					<p class="rl-muted">Register your vehicle to start receiving
						matching ride requests.</p>
				</div>
			</div>

			<c:if test="${not empty vehicleError}">
				<div class="vehicle-message">
					<c:out value="${vehicleError}" />
				</div>
			</c:if>

			<c:choose>

				<c:when test="${not empty vehicle}">

					<div class="vehicle-panel">

						<h2>Vehicle Already Registered</h2>

						<div class="vehicle-detail">
							<span>Vehicle ID</span> <span><c:out
									value="${vehicle.vehicleId}" /></span>
						</div>

						<div class="vehicle-detail">
							<span>Registration Number</span> <span><c:out
									value="${vehicle.vehicleNumber}" /></span>
						</div>

						<div class="vehicle-detail">
							<span>Vehicle Type</span> <span><c:out
									value="${vehicle.vehicleType}" /></span>
						</div>

						<div class="vehicle-detail">
							<span>Vehicle Model</span> <span><c:out
									value="${vehicle.model}" /></span>
						</div>

						<div class="vehicle-actions">
							<a href="${pageContext.request.contextPath}/driver/dashboard"
								class="rl-btn rl-btn-yellow"> Back to Dashboard </a>
						</div>

					</div>

				</c:when>

				<c:otherwise>

					<div class="vehicle-panel">

						<h2>Register Your Vehicle</h2>

						<form
							action="${pageContext.request.contextPath}/driver/vehicle-register"
							method="post">

							<div class="vehicle-group">
								<label for="vehicleNumber"> Vehicle Registration Number
								</label> <input type="text" id="vehicleNumber" name="vehicleNumber"
									placeholder="Example: MH12AB1234" maxlength="20" required>
							</div>

							<div class="vehicle-group">
								<label for="vehicleType"> Vehicle Type </label> <select
									id="vehicleType" name="vehicleType" required>

									<option value="">Select vehicle type</option>
									<option value="BIKE">Bike</option>
									<option value="AUTO">Auto Rickshaw</option>
									<option value="CAR">Car</option>

								</select>
							</div>

							<div class="vehicle-group">
								<label for="model">Vehicle Model</label> <input type="text"
									id="model" name="model" placeholder="Example: Honda Activa"
									maxlength="50" required>
							</div>

							<div class="vehicle-actions">

								<button type="submit" class="rl-btn rl-btn-yellow">
									Register Vehicle</button>

								<a href="${pageContext.request.contextPath}/driver/dashboard"
									class="rl-btn rl-btn-outline"> Back to Dashboard </a>

							</div>

						</form>

					</div>

				</c:otherwise>

			</c:choose>

		</section>

	</main>

	<footer class="rl-footer">
		<p>&copy; 2026 RideLink. Smart rides, simpler journeys.</p>
	</footer>

</body>
</html>