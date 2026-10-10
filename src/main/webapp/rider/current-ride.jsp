<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Current Ride - RideLink</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css?v=4">

</head>

<body class="rl-dashboard">


	<nav class="rl-navbar">

		<div class="rl-nav-inner">

			<a href="${pageContext.request.contextPath}/rider/dashboard"
				class="rl-logo"> <span class="rl-logo-mark"> R </span> RideLink

			</a>


			<div class="rl-nav-links">

				<a href="${pageContext.request.contextPath}/rider/dashboard">
					Dashboard </a> <a href="${pageContext.request.contextPath}/logout">
					Logout </a>

			</div>

		</div>

	</nav>



	<main class="rl-main">

		<div class="rl-container">


			<div class="rl-page-heading">

				<h1>Current Ride</h1>

				<p>Track your RideLink journey</p>

			</div>



			<!-- Error -->

			<c:if test="${not empty error}">

				<div class="rl-alert rl-alert-error">${error}</div>

			</c:if>



			<!-- No Active Ride -->

			<c:if test="${not empty message}">

				<div class="rl-card rl-empty">

					<h2>No Active Ride</h2>

					<p>${message}</p>

					<a href="${pageContext.request.contextPath}/rider/dashboard"
						class="rl-btn rl-btn-yellow"> Book a Ride </a>

				</div>

			</c:if>



			<!-- Current Ride -->

			<c:if test="${not empty currentRide}">


				<div class="rl-dashboard-grid">


					<!-- RIDE DETAILS -->

					<section class="rl-card">


						<div class="rl-card-title">

							<div>

								<h2>Ride #${currentRide.rideId}</h2>

								<span> Your current journey </span>

							</div>


							<span
								class="rl-status
                            rl-status-${currentRide.rideStatus}">

								${currentRide.rideStatus} </span>

						</div>



						<!-- ROUTE -->

						<div class="rl-route rl-current-route">


							<div class="rl-route-line">

								<span class="rl-dot"></span>

								<div>

									<small> PICKUP </small> <strong>
										${currentRide.pickupLocationName} </strong>

								</div>

							</div>



							<div class="rl-route-line">

								<span class="rl-dot rl-dot-yellow"></span>

								<div>

									<small> DESTINATION </small> <strong>
										${currentRide.destinationLocationName} </strong>

								</div>

							</div>


						</div>



						<!-- RIDE INFORMATION -->

						<div class="rl-ride-info-grid">


							<div>

								<small> Vehicle </small> <strong>
									${currentRide.vehicleType} </strong>

							</div>


							<div>

								<small> Fare </small> <strong> ₹${currentRide.fare} </strong>

							</div>


							<div>

								<small> Ride ID </small> <strong>
									#${currentRide.rideId} </strong>

							</div>


							<div>

								<small> Status </small> <strong>
									${currentRide.rideStatus} </strong>

							</div>


						</div>


					</section>



					<!-- STATUS -->

					<aside class="rl-card">


						<div class="rl-card-title">

							<div>

								<h2>Ride Status</h2>

								<span> Journey progress </span>

							</div>

						</div>



						<div class="rl-status-timeline">


							<!-- REQUESTED -->

							<div
								class="rl-timeline-item
                            <c:if test="${currentRide.rideStatus == 'REQUESTED'
                                || currentRide.rideStatus == 'ACCEPTED'
                                || currentRide.rideStatus == 'ONGOING'
                                || currentRide.rideStatus == 'COMPLETED'}">
                                active
                            </c:if>">

								<span class="rl-timeline-dot"></span>

								<div>

									<strong> Ride Requested </strong> <small> Your ride
										request has been created. </small>

								</div>

							</div>



							<!-- ACCEPTED -->

							<div
								class="rl-timeline-item
                            <c:if test="${currentRide.rideStatus == 'ACCEPTED'
                                || currentRide.rideStatus == 'ONGOING'
                                || currentRide.rideStatus == 'COMPLETED'}">
                                active
                            </c:if>">

								<span class="rl-timeline-dot"></span>

								<div>

									<strong> Driver Accepted </strong> <small> A driver has
										accepted your ride. </small>

								</div>

							</div>



							<!-- ONGOING -->

							<div
								class="rl-timeline-item
                            <c:if test="${currentRide.rideStatus == 'ONGOING'
                                || currentRide.rideStatus == 'COMPLETED'}">
                                active
                            </c:if>">

								<span class="rl-timeline-dot"></span>

								<div>

									<strong> Ride Started </strong> <small> Your journey is
										in progress. </small>

								</div>

							</div>



							<!-- COMPLETED -->

							<div
								class="rl-timeline-item
                            <c:if test="${currentRide.rideStatus == 'COMPLETED'}">
                                active
                            </c:if>">

								<span class="rl-timeline-dot"></span>

								<div>

									<strong> Ride Completed </strong> <small> Your journey
										has been completed. </small>

								</div>

							</div>


						</div>


					</aside>


				</div>



				<!-- DRIVER INFORMATION -->

				<c:if test="${currentRide.driverId > 0}">

					<section class="rl-card" style="margin-top: 22px;">

						<div class="rl-card-title">

							<div>

								<h2>Driver Assigned</h2>

								<span> Your RideLink driver </span>

							</div>

						</div>


						<div class="rl-ride-info-grid">

							<div>

								<small> Driver ID </small> <strong>
									#${currentRide.driverId} </strong>

							</div>


							<div>

								<small> Vehicle </small> <strong>
									${currentRide.vehicleType} </strong>

							</div>


							<div>

								<small> Fare </small> <strong> ₹${currentRide.fare} </strong>

							</div>

						</div>

					</section>

				</c:if>



				<!-- ACTION -->

				<div class="rl-page-actions">

					<a href="${pageContext.request.contextPath}/rider/dashboard"
						class="rl-btn rl-btn-black"> Back to Dashboard </a>

				</div>


			</c:if>


		</div>

	</main>



	<footer class="rl-footer">

		<div class="rl-container rl-footer-inner">

			<div>

				<div class="rl-logo">

					<span class="rl-logo-mark"> R </span> RideLink

				</div>

				<p>Smart rides. Simple journeys.</p>

			</div>

		</div>

	</footer>


</body>

</html>
