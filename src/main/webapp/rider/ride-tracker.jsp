<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Ride Tracker | RideLink</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css">

<style>
* {
	box-sizing: border-box;
}

body {
	margin: 0;
	font-family: Arial, Helvetica, sans-serif;
	background: #f4f7fb;
	color: #101828;
}

.navbar {
	background: #101828;
	padding: 15px 7%;
	display: flex;
	align-items: center;
	justify-content: space-between;
	gap: 15px;
}

.logo {
	color: #ffc107;
	font-size: 25px;
	font-weight: bold;
	text-decoration: none;
}

.nav-links {
	display: flex;
	gap: 22px;
	align-items: center;
}

.nav-links a {
	color: white;
	text-decoration: none;
	font-size: 14px;
}

.nav-links a:hover {
	color: #ffc107;
}

.container-main {
	width: 90%;
	max-width: 1050px;
	margin: 35px auto;
	min-height: 65vh;
}

.page-heading {
	display: flex;
	justify-content: space-between;
	align-items: center;
	gap: 15px;
	flex-wrap: wrap;
	margin-bottom: 25px;
}

.page-heading h1 {
	font-size: 29px;
	font-weight: 700;
	margin: 0;
}

.card-box {
	background: #fff;
	border-radius: 12px;
	padding: 25px;
	margin-bottom: 22px;
	box-shadow: 0 4px 16px rgba(16, 24, 40, 0.06);
}

.card-box h2 {
	font-size: 20px;
	font-weight: 700;
	margin-bottom: 20px;
}

.status-badge {
	display: inline-block;
	padding: 8px 14px;
	border-radius: 20px;
	background: #fff3cd;
	color: #664d03;
	font-size: 13px;
	font-weight: bold;
}

.route-grid {
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 20px;
}

.route-item {
	padding: 18px;
	border: 1px solid #e4e7ec;
	border-radius: 10px;
	background: #fcfcfd;
}

.route-label {
	display: block;
	color: #667085;
	font-size: 13px;
	margin-bottom: 8px;
}

.route-value {
	font-size: 16px;
	font-weight: 600;
	overflow-wrap: anywhere;
}

.details-grid {
	display: grid;
	grid-template-columns: repeat(3, 1fr);
	gap: 18px;
}

.detail-item {
	padding: 15px;
	border: 1px solid #eaecf0;
	border-radius: 10px;
}

.detail-label {
	display: block;
	color: #667085;
	font-size: 13px;
	margin-bottom: 8px;
}

.detail-value {
	font-size: 16px;
	font-weight: bold;
	overflow-wrap: anywhere;
}

#rideMap {
	height: 400px;
	width: 100%;
	border-radius: 10px;
	border: 1px solid #eaecf0;
	z-index: 1;
}

.map-note {
	margin-top: 12px;
	color: #667085;
	font-size: 13px;
	line-height: 1.6;
}

.map-legend {
	display: flex;
	flex-wrap: wrap;
	gap: 18px;
	margin-top: 15px;
	font-size: 13px;
}

.legend-item {
	display: flex;
	align-items: center;
	gap: 7px;
}

.legend-dot {
	width: 11px;
	height: 11px;
	display: inline-block;
	border-radius: 50%;
}

.pickup-dot {
	background: #198754;
}

.destination-dot {
	background: #dc3545;
}

.progress-list {
	position: relative;
	padding-left: 10px;
}

.progress-item {
	position: relative;
	padding: 0 0 27px 35px;
	border-left: 2px solid #e4e7ec;
	margin-left: 9px;
}

.progress-item:last-child {
	border-left-color: transparent;
	padding-bottom: 0;
}

.progress-dot {
	position: absolute;
	left: -10px;
	top: 0;
	height: 18px;
	width: 18px;
	border-radius: 50%;
	background: #d0d5dd;
	border: 3px solid #fff;
	box-shadow: 0 0 0 1px #d0d5dd;
}

.progress-item.completed .progress-dot, .progress-item.active .progress-dot
	{
	background: #198754;
	box-shadow: 0 0 0 1px #198754;
}

.progress-title {
	font-weight: bold;
	font-size: 15px;
}

.progress-description {
	font-size: 13px;
	color: #667085;
	margin-top: 5px;
}

.action-buttons {
	display: flex;
	gap: 12px;
	flex-wrap: wrap;
	margin-top: 22px;
}

.btn-custom {
	display: inline-block;
	border: none;
	border-radius: 7px;
	background: #ffc107;
	color: #101828;
	text-decoration: none;
	font-weight: bold;
	padding: 11px 18px;
	cursor: pointer;
}

.btn-custom:hover {
	background: #e5ac00;
	color: #101828;
}

.btn-secondary-custom {
	background: #101828;
	color: #fff;
}

.btn-secondary-custom:hover {
	background: #273449;
	color: #fff;
}

.alert-box {
	padding: 15px 18px;
	background: #fef3f2;
	color: #b42318;
	border: 1px solid #fecdca;
	border-radius: 9px;
	margin-bottom: 22px;
}

.footer {
	text-align: center;
	background: #101828;
	color: #d0d5dd;
	padding: 20px;
	font-size: 13px;
}

@media ( max-width : 700px) {
	.navbar {
		padding: 15px 5%;
	}
	.container-main {
		width: 92%;
		margin: 25px auto;
	}
	.page-heading h1 {
		font-size: 24px;
	}
	.card-box {
		padding: 18px;
	}
	.route-grid, .details-grid {
		grid-template-columns: 1fr;
	}
	#rideMap {
		height: 320px;
	}
	.nav-links {
		gap: 12px;
	}
}
</style>
</head>

<body>

	<nav class="navbar">
		<a class="logo" href="${pageContext.request.contextPath}/">
			RideLink </a>

		<div class="nav-links">
			<a href="${pageContext.request.contextPath}/rider/dashboard">
				Dashboard </a> <a href="${pageContext.request.contextPath}/logout">
				Logout </a>
		</div>
	</nav>

	<main class="container-main">

		<c:if test="${not empty sessionScope.bookingSuccess}">
			<div class="alert alert-success">
				<c:out value="${sessionScope.bookingSuccess}" />
			</div>
			<c:remove var="bookingSuccess" scope="session" />
		</c:if>

		<c:if test="${not empty error}">
			<div class="alert-box">
				<c:out value="${error}" />
			</div>
		</c:if>

		<c:choose>
			<c:when test="${not empty ride}">

				<div class="page-heading">
					<h1>Your Ride Tracker</h1>

					<span class="status-badge"> <c:out
							value="${ride.rideStatus}" />
					</span>
				</div>

				<!-- Existing route details -->
				<section class="card-box">
					<h2>Ride Route</h2>

					<div class="route-grid">
						<div class="route-item">
							<span class="route-label">Pickup Location</span>

							<div class="route-value">
								<c:out value="${pickupLocation}" />
							</div>
						</div>

						<div class="route-item">
							<span class="route-label">Destination</span>

							<div class="route-value">
								<c:out value="${destinationLocation}" />
							</div>
						</div>
					</div>
				</section>

				<!-- NEW: Map section -->
				<section class="card-box">
					<h2>Live Ride Map</h2>

					<c:choose>
						<c:when
							test="${not empty pickupCoordinates
                                  and not empty destinationCoordinates}">

							<div id="rideMap" data-pickup-lat="${pickupCoordinates.latitude}"
								data-pickup-lng="${pickupCoordinates.longitude}"
								data-destination-lat="${destinationCoordinates.latitude}"
								data-destination-lng="${destinationCoordinates.longitude}"
								data-pickup-name="${pickupCoordinates.locationName}"
								data-destination-name="${destinationCoordinates.locationName}">
							</div>

							<div class="map-legend">
								<div class="legend-item">
									<span class="legend-dot pickup-dot"></span> Pickup
								</div>

								<div class="legend-item">
									<span class="legend-dot destination-dot"></span> Destination
								</div>
							</div>

							<p id="mapMessage" class="map-note">Loading map and route...
							</p>

						</c:when>

						<c:otherwise>
							<div class="alert alert-warning mb-0">Map coordinates are
								unavailable for this ride. Please check the pickup and
								destination locations in the database.</div>
						</c:otherwise>
					</c:choose>
				</section>

				<!-- Existing ride details -->
				<section class="card-box">
					<h2>Ride Details</h2>

					<div class="details-grid">

						<div class="detail-item">
							<span class="detail-label">Ride ID</span>

							<div class="detail-value">
								#
								<c:out value="${ride.rideId}" />
							</div>
						</div>

						<div class="detail-item">
							<span class="detail-label">Vehicle Type</span>

							<div class="detail-value">
								<c:out value="${ride.vehicleType}" />
							</div>
						</div>

						<div class="detail-item">
							<span class="detail-label">Fare</span>

							<div class="detail-value">
								₹
								<c:out value="${ride.fare}" />
							</div>
						</div>

					</div>
				</section>

				<!-- Existing progress timeline -->
				<section class="card-box">
					<h2>Ride Progress</h2>

					<div class="progress-list">

						<div class="progress-item completed">
							<span class="progress-dot"></span>

							<div class="progress-title">Ride Booked</div>

							<div class="progress-description">Your ride request has
								been created.</div>
						</div>

						<div
							class="progress-item
                        ${ride.rideStatus == 'ACCEPTED'
                          || ride.rideStatus == 'ONGOING'
                          || ride.rideStatus == 'COMPLETED'
                          ? 'completed' : ''}">

							<span class="progress-dot"></span>

							<div class="progress-title">Driver Assigned</div>

							<div class="progress-description">
								<c:choose>
									<c:when
										test="${ride.rideStatus == 'ACCEPTED'
                                               || ride.rideStatus == 'ONGOING'
                                               || ride.rideStatus == 'COMPLETED'}">
                                    Your ride has been accepted.
                                </c:when>

									<c:otherwise>
                                    Waiting for a driver to accept your ride.
                                </c:otherwise>
								</c:choose>
							</div>
						</div>

						<div
							class="progress-item
                        ${ride.rideStatus == 'ONGOING'
                          || ride.rideStatus == 'COMPLETED'
                          ? 'completed' : ''}">

							<span class="progress-dot"></span>

							<div class="progress-title">On The Way</div>

							<div class="progress-description">
								<c:choose>
									<c:when
										test="${ride.rideStatus == 'ONGOING'
                                               || ride.rideStatus == 'COMPLETED'}">
                                    Your ride has started.
                                </c:when>

									<c:otherwise>
                                    Your ride will start when the driver begins
                                    the trip.
                                </c:otherwise>
								</c:choose>
							</div>
						</div>

						<div
							class="progress-item
                        ${ride.rideStatus == 'COMPLETED'
                          ? 'completed' : ''}">

							<span class="progress-dot"></span>

							<div class="progress-title">Completed</div>

							<div class="progress-description">
								<c:choose>
									<c:when test="${ride.rideStatus == 'COMPLETED'}">
                                    Your ride is completed.
                                </c:when>

									<c:otherwise>
                                    Your ride is not completed yet.
                                </c:otherwise>
								</c:choose>
							</div>
						</div>

					</div>

					<div class="action-buttons">
						<a class="btn-custom"
							href="${pageContext.request.contextPath}/rider/dashboard">
							Back to Dashboard </a> <a class="btn-custom btn-secondary-custom"
							href="${pageContext.request.contextPath}/locations"> Book
							Another Ride </a>
					</div>
				</section>

			</c:when>

			<c:otherwise>

				<div class="card-box">
					<h2>Ride Details Unavailable</h2>

					<p>We could not load your ride details. Please return to your
						dashboard and select a valid ride.</p>

					<a class="btn-custom"
						href="${pageContext.request.contextPath}/rider/dashboard">
						Back to Dashboard </a>
				</div>

			</c:otherwise>
		</c:choose>

	</main>

	<footer class="footer"> RideLink &copy; 2026. Your journey,
		connected. </footer>

	<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>

	<script>
document.addEventListener("DOMContentLoaded", function () {

    const mapElement = document.getElementById("rideMap");

    if (!mapElement || typeof L === "undefined") {
        return;
    }

    const pickupLat = Number(mapElement.dataset.pickupLat);
    const pickupLng = Number(mapElement.dataset.pickupLng);

    const destinationLat = Number(mapElement.dataset.destinationLat);
    const destinationLng = Number(mapElement.dataset.destinationLng);

    const pickupName = mapElement.dataset.pickupName || "Pickup";
    const destinationName =
        mapElement.dataset.destinationName || "Destination";

    const messageElement = document.getElementById("mapMessage");

    const coordinates = [
        pickupLat,
        pickupLng,
        destinationLat,
        destinationLng
    ];

    if (!coordinates.every(Number.isFinite)
            || Math.abs(pickupLat) > 90
            || Math.abs(destinationLat) > 90
            || Math.abs(pickupLng) > 180
            || Math.abs(destinationLng) > 180
            || (pickupLat === 0 && pickupLng === 0)
            || (destinationLat === 0 && destinationLng === 0)) {

        messageElement.textContent =
            "Valid pickup or destination coordinates are unavailable.";

        return;
    }

    const pickup = [pickupLat, pickupLng];
    const destination = [destinationLat, destinationLng];

    const map = L.map("rideMap");

    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        maxZoom: 19,
        attribution: "&copy; OpenStreetMap contributors"
    }).addTo(map);

    const pickupIcon = L.divIcon({
        className: "",
        html: '<div style="background:#198754;width:18px;height:18px;' +
              'border:3px solid white;border-radius:50%;' +
              'box-shadow:0 1px 5px #555;"></div>',
        iconSize: [18, 18],
        iconAnchor: [9, 9]
    });

    const destinationIcon = L.divIcon({
        className: "",
        html: '<div style="background:#dc3545;width:18px;height:18px;' +
              'border:3px solid white;border-radius:50%;' +
              'box-shadow:0 1px 5px #555;"></div>',
        iconSize: [18, 18],
        iconAnchor: [9, 9]
    });

    L.marker(pickup, { icon: pickupIcon })
        .addTo(map)
        .bindPopup("<b>Pickup:</b> " + escapeHtml(pickupName));

    L.marker(destination, { icon: destinationIcon })
        .addTo(map)
        .bindPopup("<b>Destination:</b> " + escapeHtml(destinationName));

    const routeBounds = L.latLngBounds([pickup, destination]);

    map.fitBounds(routeBounds, {
        padding: [40, 40],
        maxZoom: 14
    });

    const routeLine = L.polyline([pickup, destination], {
        color: "#ffc107",
        weight: 5,
        opacity: 0.85,
        dashArray: "8, 8"
    }).addTo(map);

    messageElement.textContent =
        "Showing pickup and destination. Loading road route...";

    const routeUrl =
        "https://router.project-osrm.org/route/v1/driving/" +
        encodeURIComponent(pickupLng + "," + pickupLat) + ";" +
        encodeURIComponent(destinationLng + "," + destinationLat) +
        "?overview=full&geometries=geojson";

    fetch(routeUrl)
        .then(function (response) {
            if (!response.ok) {
                throw new Error("Route service unavailable");
            }

            return response.json();
        })
        .then(function (data) {
            if (!data.routes || data.routes.length === 0) {
                throw new Error("No road route found");
            }

            const routeCoordinates =
                data.routes[0].geometry.coordinates.map(function (point) {
                    return [point[1], point[0]];
                });

            routeLine.setLatLngs(routeCoordinates);

            map.fitBounds(L.latLngBounds(routeCoordinates), {
                padding: [40, 40],
                maxZoom: 14
            });

            const distanceKm =
                (data.routes[0].distance / 1000).toFixed(1);

            const durationMinutes =
                Math.round(data.routes[0].duration / 60);

            messageElement.textContent =
                "Road distance: " + distanceKm +
                " km | Estimated driving time: " +
                durationMinutes + " minutes.";
        })
        .catch(function () {
            messageElement.textContent =
                "Pickup and destination are displayed. " +
                "The road route could not be loaded. " +
                "A straight-line connection is shown instead.";
        });

    function escapeHtml(value) {
        return String(value).replace(/[&<>"']/g, function (character) {
            const entities = {
                "&": "&amp;",
                "<": "&lt;",
                ">": "&gt;",
                '"': "&quot;",
                "'": "&#39;"
            };

            return entities[character];
        });
    }

});
</script>

</body>
</html>