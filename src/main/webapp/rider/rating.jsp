<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Rate Driver - RideLink</title>

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

				<h1>Rate Your Driver</h1>

				<p>Share your experience with RideLink</p>

			</div>



			<c:if test="${not empty ratingError}">

				<div class="rl-alert rl-alert-error">${ratingError}</div>

			</c:if>



			<c:if test="${not empty error}">

				<div class="rl-alert rl-alert-error">${error}</div>

			</c:if>



			<c:if test="${not empty ride}">


				<div class="rl-dashboard-grid">


					<!-- RIDE SUMMARY -->

					<section class="rl-card">


						<div class="rl-card-title">

							<div>

								<h2>Ride Summary</h2>

								<span> Ride #${ride.rideId} </span>

							</div>


							<span
								class="rl-status
                            rl-status-${ride.rideStatus}">

								${ride.rideStatus} </span>

						</div>



						<div class="rl-route">


							<div class="rl-route-line">

								<span class="rl-dot"></span> ${ride.pickupLocationName}

							</div>


							<div class="rl-route-line">

								<span class="rl-dot rl-dot-yellow"></span>

								${ride.destinationLocationName}

							</div>


						</div>



						<div class="rl-ride-info-grid">


							<div>

								<small> Vehicle </small> <strong> ${ride.vehicleType} </strong>

							</div>


							<div>

								<small> Fare </small> <strong> ₹${ride.fare} </strong>

							</div>


							<div>

								<small> Driver ID </small> <strong> #${ride.driverId} </strong>

							</div>


							<div>

								<small> Status </small> <strong> Completed </strong>

							</div>


						</div>


					</section>



					<!-- RATING FORM -->

					<section class="rl-card">


						<div class="rl-card-title">

							<div>

								<h2>Your Feedback</h2>

								<span> Rate your driver </span>

							</div>

						</div>



						<form action="${pageContext.request.contextPath}/rating"
							method="post" class="rl-booking-form">


							<input type="hidden" name="rideId" value="${ride.rideId}">



							<!-- RATING -->

							<div>


								<label class="rl-form-group"> <strong> Rating </strong>

								</label>


								<div class="rl-rating-options">


									<label> <input type="radio" name="rating" value="1"
										required> <span> 1 </span>

									</label> <label> <input type="radio" name="rating" value="2">

										<span> 2 </span>

									</label> <label> <input type="radio" name="rating" value="3">

										<span> 3 </span>

									</label> <label> <input type="radio" name="rating" value="4">

										<span> 4 </span>

									</label> <label> <input type="radio" name="rating" value="5">

										<span> 5 </span>

									</label>


								</div>


							</div>



							<!-- REVIEW -->

							<div>


								<label for="review" class="rl-form-group"> <strong>
										Review </strong>

								</label>


								<textarea id="review" name="review" rows="5" maxlength="255"
									placeholder="Tell us about your ride experience..."
									class="rl-textarea"></textarea>


								<small> Maximum 255 characters </small>

							</div>



							<button type="submit" class="rl-btn rl-btn-yellow">

								Submit Rating</button>


						</form>


					</section>


				</div>


			</c:if>



			<c:if test="${empty ride}">


				<div class="rl-card rl-empty">

					<h2>Ride Not Found</h2>

					<p>This ride is not available for rating.</p>


					<a href="${pageContext.request.contextPath}/rider/dashboard"
						class="rl-btn rl-btn-yellow"> Back to Dashboard </a>

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
