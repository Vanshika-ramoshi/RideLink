<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Ride History | RideLink</title>

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

.navbar {
	background: #101828;
	padding: 20px 7%;
	display: flex;
	justify-content: space-between;
	align-items: center;
	flex-wrap: wrap;
	gap: 15px;
}

.logo {
	font-size: 25px;
	font-weight: bold;
	color: white;
}

.navbar a {
	color: white;
	text-decoration: none;
	margin-left: 18px;
}

.navbar a:hover {
	color: #60a5fa;
}

.container {
	max-width: 1150px;
	margin: 40px auto;
	padding: 20px;
}

.subtitle {
	color: #64748b;
	margin-bottom: 25px;
}

.table-box {
	background: white;
	padding: 20px;
	border-radius: 12px;
	box-shadow: 0 5px 20px rgba(0, 0, 0, 0.06);
	overflow-x: auto;
}

table {
	width: 100%;
	min-width: 650px;
	border-collapse: collapse;
}

th, td {
	padding: 15px;
	text-align: left;
	border-bottom: 1px solid #e2e8f0;
}

th {
	background: #f1f5f9;
}

.empty {
	text-align: center;
	padding: 30px;
	color: #64748b;
}

.status {
	background: #e2e8f0;
	padding: 6px 10px;
	border-radius: 15px;
	font-size: 12px;
}

.button {
	display: inline-block;
	margin-top: 25px;
	padding: 12px 20px;
	background: #2563eb;
	color: white;
	text-decoration: none;
	border-radius: 7px;
}

@media ( max-width : 600px) {
	.navbar {
		padding: 18px 5%;
	}
	.navbar a {
		margin-left: 0;
		margin-right: 10px;
	}
}
</style>

</head>

<body>

	<div class="navbar">
		<div class="logo">RideLink</div>


		<div>
			<a href="${pageContext.request.contextPath}/driver/dashboard">Dashboard</a>
			<a href="${pageContext.request.contextPath}/driver/earnings">Earnings</a>
			<a href="${pageContext.request.contextPath}/driver/ride-history">Ride
				History</a>
		</div>


	</div>

	<div class="container">


		<h1>My Ride History</h1>

		<p class="subtitle">View the rides assigned to you.</p>

		<div class="table-box">
			<table>
				<thead>
					<tr>
						<th>Ride ID</th>
						<th>Pickup Location</th>
						<th>Destination</th>
						<th>Vehicle</th>
						<th>Fare</th>
						<th>Status</th>
					</tr>
				</thead>

				<tbody>
					<c:choose>
						<c:when test="${not empty rideHistory}">
							<c:forEach var="ride" items="${rideHistory}">
								<tr>
									<td><c:out value="${ride.rideId}" /></td>
									<td><c:out value="${ride.pickupLocation}" /></td>
									<td><c:out value="${ride.destinationLocation}" /></td>
									<td><c:out value="${ride.vehicleType}" /></td>
									<td>₹ <c:out value="${ride.fare}" /></td>
									<td><span class="status"> <c:out
												value="${ride.rideStatus}" />
									</span></td>
								</tr>
							</c:forEach>
						</c:when>

						<c:otherwise>
							<tr>
								<td colspan="6" class="empty">No ride history available
									yet.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>

		<a class="button"
			href="${pageContext.request.contextPath}/driver/dashboard"> Back
			to Dashboard </a>


	</div>

</body>
</html>
