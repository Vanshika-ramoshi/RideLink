<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Payment - RideLink</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css?v=5">

<style>
.payment-method {
	display: flex;
	gap: 12px;
	margin: 18px 0;
}

.payment-option {
	flex: 1;
	padding: 16px;
	border: 1px solid #ddd;
	border-radius: 12px;
	cursor: pointer;
}

.payment-option input {
	margin-right: 8px;
}

.payment-option strong, .payment-option small {
	display: block;
	margin-top: 8px;
}

.payment-option small {
	color: #666;
}

.payment-note {
	padding: 14px;
	margin: 16px 0;
	border-radius: 10px;
	background: #f5f5f5;
}

.payment-status {
	display: inline-block;
	padding: 7px 12px;
	border-radius: 20px;
	background: #fff0c2;
	font-weight: bold;
}

@media ( max-width : 600px) {
	.payment-method {
		flex-direction: column;
	}
}
</style>
```

</head>

<body class="rl-dashboard">

	<nav class="rl-navbar">
		<div class="rl-nav-inner">
			<a href="${pageContext.request.contextPath}/rider/dashboard"
				class="rl-logo"> <span class="rl-logo-mark">R</span> RideLink
			</a> ```
			<div class="rl-nav-links">
				<a href="${pageContext.request.contextPath}/rider/dashboard">Dashboard</a>
				<a href="${pageContext.request.contextPath}/logout">Logout</a>
			</div>
		</div>
		```

	</nav>

	<main class="rl-main">
		<div class="rl-container">

			```
			<div class="rl-page-heading">
				<h1>Ride Payment</h1>
				<p>Review your ride details and payment information.</p>
			</div>

			<c:if test="${not empty paymentError}">
				<div class="rl-alert rl-alert-error">
					<c:out value="${paymentError}" />
				</div>
			</c:if>

			<c:if test="${not empty paymentSuccess}">
				<div class="rl-alert">
					<c:out value="${paymentSuccess}" />
				</div>
			</c:if>

			<div class="rl-dashboard-grid">

				<section class="rl-card">
					<div class="rl-card-title">
						<div>
							<h2>Ride Summary</h2>
							<span>Ride #<c:out value="${ride.rideId}" /></span>
						</div>
						<span class="payment-status"> <c:out
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

					<div class="rl-ride-info-grid">
						<div>
							<small>Vehicle</small> <strong><c:out
									value="${ride.vehicleType}" /></strong>
						</div>

						<div>
							<small>Ride ID</small> <strong>#<c:out
									value="${ride.rideId}" /></strong>
						</div>
					</div>
				</section>

				<section class="rl-card">
					<div class="rl-card-title">
						<div>
							<h2>Payment Details</h2>
							<span>Select your payment method.</span>
						</div>
					</div>

					<div class="rl-fare-box">
						<div>
							<strong>Total Fare</strong>
						</div>
						<div class="rl-fare-price">
							₹
							<c:out value="${ride.fare}" />
						</div>
					</div>

					<c:choose>

						<c:when test="${payment.paymentStatus eq 'PAID'}">
							<div class="payment-note">
								<h3>Payment Completed</h3>
								<p>This ride has already been paid.</p>
								<p>
									Payment method: <strong><c:out
											value="${payment.paymentMethod}" /></strong>
								</p>
								<p>
									Status: <strong><c:out
											value="${payment.paymentStatus}" /></strong>
								</p>
							</div>
						</c:when>

						<c:when test="${not empty payment}">
							<div class="payment-note">
								<h3>Existing Payment Record</h3>
								<p>
									Payment status: <strong><c:out
											value="${payment.paymentStatus}" /></strong>
								</p>
								<p>
									Method: <strong><c:out
											value="${payment.paymentMethod}" /></strong>
								</p>
								<p>If this is a cash payment, complete the cash collection
									before marking it as paid.</p>
							</div>
						</c:when>

						<c:otherwise>
							<form action="${pageContext.request.contextPath}/payment"
								method="post" class="rl-booking-form">

								<input type="hidden" name="rideId" value="${ride.rideId}">

								<strong>Choose Payment Method</strong>

								<div class="payment-method">

									<label class="payment-option" for="cash"> <input
										type="radio" id="cash" name="paymentMethod" value="CASH"
										required> <strong>Cash</strong> <small>Pay the
											driver directly.</small>
									</label> <label class="payment-option" for="online"> <input
										type="radio" id="online" name="paymentMethod" value="ONLINE"
										required> <strong>Online</strong> <small>Simulated
											payment for this project.</small>
									</label>

								</div>

								<div class="payment-note">Online payment is a
									demonstration only. No real money will be charged.</div>

								<button type="submit" class="rl-btn rl-btn-yellow">
									Continue to Payment</button>

							</form>
						</c:otherwise>

					</c:choose>