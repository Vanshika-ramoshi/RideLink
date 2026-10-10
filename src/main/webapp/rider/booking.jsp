<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%-- Agar page seedha khola gaya (servlet se nahi), to pehle /locations servlet par bhejo --%>
<c:if test="${locations == null}">
	<c:redirect url="/locations" />
</c:if>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Book a Ride | RideLink</title>

<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: Arial, sans-serif;
}

body {
	min-height: 100vh;
	background: #f3f6fb;
	color: #172033;
}

nav {
	background: #101c35;
	color: white;
	padding: 18px 7%;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.logo {
	font-size: 25px;
	font-weight: bold;
	color: #54d6be;
}

nav a {
	color: white;
	text-decoration: none;
	margin-left: 20px;
}

.container {
	width: 92%;
	max-width: 650px;
	margin: 45px auto;
}

.booking-card {
	background: white;
	padding: 32px;
	border-radius: 16px;
	box-shadow: 0 8px 30px rgba(0, 0, 0, 0.08);
}

h1 {
	margin-bottom: 10px;
	font-size: 29px;
}

.subtitle {
	color: #687386;
	margin-bottom: 28px;
	line-height: 1.5;
}

.form-group {
	margin-bottom: 22px;
}

label {
	display: block;
	margin-bottom: 9px;
	font-weight: bold;
}

select {
	width: 100%;
	padding: 13px;
	border: 1px solid #d7deea;
	border-radius: 8px;
	background: white;
	font-size: 15px;
	outline: none;
}

select:focus {
	border-color: #16a895;
}

.error {
	background: #fff0f0;
	color: #b42318;
	padding: 12px;
	border-radius: 8px;
	margin-bottom: 18px;
}

.success {
	background: #e8fff6;
	color: #087c59;
	padding: 12px;
	border-radius: 8px;
	margin-bottom: 18px;
}

.book-btn {
	width: 100%;
	padding: 14px;
	border: none;
	border-radius: 8px;
	background: #10a891;
	color: white;
	font-size: 16px;
	font-weight: bold;
	cursor: pointer;
}

.book-btn:hover {
	background: #087e70;
}

.note {
	margin-top: 18px;
	font-size: 13px;
	color: #687386;
	line-height: 1.5;
}

@media ( max-width : 500px) {
	.booking-card {
		padding: 22px;
	}
	nav {
		padding: 16px 5%;
	}
	nav a {
		margin-left: 10px;
	}
}
</style>
</head>

<body>

	<nav>
		<div class="logo">RideLink</div>

		<div>
			<a href="${pageContext.request.contextPath}/">Home</a> <a
				href="${pageContext.request.contextPath}/logout">Logout</a>
		</div>
	</nav>

	<div class="container">

		<div class="booking-card">

			<h1>Book Your Ride</h1>

			<p class="subtitle">Select your pickup, destination and preferred
				vehicle.</p>

			<c:if test="${not empty error}">
				<div class="error">
					<c:out value="${error}" />
				</div>
			</c:if>

			<c:if test="${not empty success}">
				<div class="success">
					<c:out value="${success}" />
				</div>
			</c:if>

			<c:choose>

				<c:when test="${empty locations}">
					<div class="error">No locations found. Please check your
						database connection and locations table.</div>
				</c:when>

				<c:otherwise>

					<form action="${pageContext.request.contextPath}/ride"
						method="post">

						<div class="form-group">
							<label for="pickupLocationId"> Pickup Location </label> <select
								id="pickupLocationId" name="pickupLocationId" required>

								<option value="">Select pickup location</option>

								<c:forEach var="location" items="${locations}">
									<option value="${location.locationId}">
										<c:out value="${location.locationName}" />,
										<c:out value="${location.city}" />
									</option>
								</c:forEach>

							</select>
						</div>

						<div class="form-group">
							<label for="destinationLocationId"> Destination </label> <select
								id="destinationLocationId" name="destinationLocationId" required>

								<option value="">Select destination</option>

								<c:forEach var="location" items="${locations}">
									<option value="${location.locationId}">
										<c:out value="${location.locationName}" />,
										<c:out value="${location.city}" />
									</option>
								</c:forEach>

							</select>
						</div>

						<div class="form-group">
							<label for="vehicleType"> Choose Vehicle </label> <select
								id="vehicleType" name="vehicleType" required>
								<option value="">Select vehicle</option>
								<option value="BIKE">Bike</option>
								<option value="AUTO">Auto</option>
								<option value="CAR">Car</option>
							</select>
						</div>

						<button type="submit" class="book-btn">Book Ride</button>

					</form>

				</c:otherwise>

			</c:choose>

			<p class="note">Note: Selecting locations prepares your booking
				request. Ride confirmation requires the RideServlet and RideService
				booking logic to be implemented correctly.</p>

		</div>

	</div>

	<script>
		const pickup = document.getElementById("pickupLocationId");
		const destination = document.getElementById("destinationLocationId");

		if (pickup && destination) {
			pickup.addEventListener("change", function() {
				const selectedPickup = pickup.value;

				Array.from(destination.options).forEach(
						function(option) {
							option.disabled = option.value !== ""
									&& option.value === selectedPickup;
						});

				if (destination.value === selectedPickup) {
					destination.value = "";
				}
			});
		}
	</script>

</body>
</html>