<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Driver Earnings | RideLink</title>


<style>
* {
	box-sizing: border-box;
}

body {
	margin: 0;
	font-family: Arial, sans-serif;
	background: #f4f7fb;
	color: #1e293b;
}

nav {
	background: #101828;
	padding: 20px 7%;
	display: flex;
	justify-content: space-between;
	align-items: center;
	flex-wrap: wrap;
	gap: 15px;
}

.logo {
	color: white;
	font-size: 25px;
	font-weight: bold;
}

nav a {
	color: white;
	text-decoration: none;
	margin-left: 18px;
}

nav a:hover {
	color: #60a5fa;
}

.container {
	max-width: 900px;
	margin: 45px auto;
	padding: 20px;
}

.card {
	background: white;
	padding: 30px;
	margin-top: 25px;
	border-radius: 14px;
	box-shadow: 0 5px 20px rgba(0, 0, 0, 0.06);
}

.amount {
	font-size: 38px;
	font-weight: bold;
	color: #15803d;
	margin: 15px 0;
}

.muted {
	color: #64748b;
}

.button {
	display: inline-block;
	margin-top: 20px;
	padding: 12px 20px;
	background: #2563eb;
	color: white;
	text-decoration: none;
	border-radius: 7px;
}

@media ( max-width : 600px) {
	nav {
		padding: 18px 5%;
	}
	nav a {
		margin-left: 0;
		margin-right: 12px;
	}
}
</style>


</head>

<body>

	<nav>
		<div class="logo">RideLink</div>


		<div>
			<a href="${pageContext.request.contextPath}/driver/dashboard">Dashboard</a>
			<a href="${pageContext.request.contextPath}/driver/earnings">Earnings</a>
			<a href="${pageContext.request.contextPath}/driver/ride-history">Ride
				History</a>
		</div>

	</nav>

	<div class="container">


		<h1>My Earnings</h1>
		<p class="muted">Track your daily earnings and completed rides.</p>

		<div class="card">
			<p class="muted">Today's Earnings</p>

			<div class="amount">
				₹
				<c:out value="${todayEarnings}" default="0.00" />
			</div>

			<p class="muted">Earnings from completed rides today.</p>
		</div>

		<div class="card">
			<h3>Completed Rides</h3>

			<div class="amount">
				<c:out value="${completedRides}" default="0" />
			</div>

			<p class="muted">Total completed rides.</p>

			<a class="button"
				href="${pageContext.request.contextPath}/driver/dashboard"> Back
				to Dashboard </a>
		</div>


	</div>

</body>
</html>
