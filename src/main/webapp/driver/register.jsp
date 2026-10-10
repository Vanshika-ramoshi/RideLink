<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>RideLink | Driver Registration</title>

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

.rl-nav a:hover {
	color: #ffc107;
}

.rl-container {
	width: 92%;
	max-width: 650px;
	margin: 40px auto;
}

.rl-heading {
	text-align: center;
	margin-bottom: 25px;
}

.rl-heading h1 {
	font-size: 30px;
	margin-bottom: 10px;
}

.rl-heading p {
	color: #667085;
	line-height: 1.6;
}

.rl-card {
	background: white;
	padding: 30px;
	border-radius: 12px;
	box-shadow: 0 3px 15px rgba(0, 0, 0, 0.06);
}

.rl-card h2 {
	font-size: 21px;
	margin: 0 0 25px;
}

.rl-form-group {
	margin-bottom: 20px;
}

.rl-form-group label {
	display: block;
	margin-bottom: 8px;
	font-size: 14px;
	font-weight: bold;
	color: #344054;
}

.rl-form-group input, .rl-form-group select {
	width: 100%;
	padding: 13px 14px;
	border: 1px solid #d0d5dd;
	border-radius: 7px;
	background: #fff;
	font-size: 14px;
	outline: none;
}

.rl-form-group input:focus, .rl-form-group select:focus {
	border-color: #ffc107;
	box-shadow: 0 0 0 3px rgba(255, 193, 7, 0.15);
}

.rl-form-note {
	color: #667085;
	font-size: 12px;
	margin-top: 7px;
	line-height: 1.5;
}

.rl-btn {
	display: inline-block;
	padding: 13px 18px;
	border: none;
	border-radius: 7px;
	font-size: 14px;
	font-weight: bold;
	text-decoration: none;
	text-align: center;
	cursor: pointer;
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

.rl-actions {
	display: flex;
	gap: 12px;
	flex-wrap: wrap;
	margin-top: 25px;
}

.rl-actions .rl-btn {
	flex: 1;
}

.rl-alert {
	padding: 14px 18px;
	border-radius: 8px;
	margin-bottom: 18px;
	font-size: 14px;
}

.rl-alert-error {
	background: #fee2e2;
	color: #991b1b;
	border: 1px solid #fecaca;
}

.rl-alert-success {
	background: #d1fae5;
	color: #065f46;
	border: 1px solid #a7f3d0;
}

.rl-footer {
	text-align: center;
	padding: 25px;
	color: #667085;
	font-size: 13px;
}

@media ( max-width : 600px) {
	.rl-header {
		align-items: flex-start;
	}
	.rl-container {
		margin: 25px auto;
	}
	.rl-heading h1 {
		font-size: 25px;
	}
	.rl-card {
		padding: 22px;
	}
	.rl-actions {
		flex-direction: column;
	}
}
</style>

</head>

<body>

	<header class="rl-header">
		<div class="rl-logo">RideLink</div>

		```
		<nav class="rl-nav">
			<a href="${pageContext.request.contextPath}/">Home</a> <a
				href="${pageContext.request.contextPath}/login">Login</a>
		</nav>
		```

	</header>

	<main class="rl-container">

		```
		<section class="rl-heading">
			<h1>Become a RideLink Driver</h1>
			<p>Register your driver details and get ready to start your
				journey with RideLink.</p>
		</section>

		<c:if test="${not empty error}">
			<div class="rl-alert rl-alert-error">
				<c:out value="${error}" />
			</div>
		</c:if>

		<c:if test="${not empty success}">
			<div class="rl-alert rl-alert-success">
				<c:out value="${success}" />
			</div>
		</c:if>

		<section class="rl-card">

			<h2>Driver Registration</h2>

			<%-- Keep the action and input names aligned with your existing servlet. --%>
			<form action="${pageContext.request.contextPath}/driver/register"
				method="post">

				<div class="rl-form-group">
					<label for="licenseNumber">Driving License Number</label> <input
						type="text" id="licenseNumber" name="licenseNumber"
						placeholder="Enter your driving license number" maxlength="50"
						required>
				</div>

				<div class="rl-form-group">
					<label for="vehicleType">Vehicle Type</label> <select
						id="vehicleType" name="vehicleType" required>
						<option value="">Select vehicle type</option>
						<option value="BIKE">Bike</option>
						<option value="AUTO">Auto Rickshaw</option>
						<option value="CAR">Car</option>
					</select>
				</div>

				<div class="rl-form-group">
					<label for="vehicleNumber">Vehicle Registration Number</label> <input
						type="text" id="vehicleNumber" name="vehicleNumber"
						placeholder="e.g. MH12AB1234" maxlength="20" required>
				</div>

				<div class="rl-form-group">
					<label for="model">Vehicle Model</label> <input type="text"
						id="model" name="model" placeholder="e.g. Honda Shine"
						maxlength="100" required>
					<p class="rl-form-note">You can manage additional vehicles from
						your driver dashboard after registration.</p>
				</div>

				<div class="rl-actions">
					<button type="submit" class="rl-btn rl-btn-yellow">
						Register as Driver</button>

					<a href="${pageContext.request.contextPath}/"
						class="rl-btn rl-btn-dark"> Back to Home </a>
				</div>

			</form>

		</section>

	</main>

	<footer class="rl-footer">
		<p>&copy; 2026 RideLink. Smart Rides, Safer Journeys.</p>
	</footer>

</body>
</html>
