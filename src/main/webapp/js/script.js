document.addEventListener("DOMContentLoaded", function() {

    const pickup = document.getElementById("pickup");
    const destination = document.getElementById("destination");

    const fare = document.getElementById("fare");
    const distance = document.getElementById("distance");

    const bookingForm = document.getElementById("bookingForm");

    const vehicleInputs =
        document.querySelectorAll('input[name="vehicleType"]');


    /* =========================
       FARE RATES
       ========================= */

    const rates = {
        BIKE: 10,
        AUTO: 15,
        CAR: 20
    };


    /* =========================
       HAVERSINE DISTANCE
       ========================= */

    function calculateDistance(
        lat1,
        lon1,
        lat2,
        lon2
    ) {

        const earthRadius = 6371;

        const latDifference =
            (lat2 - lat1) * Math.PI / 180;

        const lonDifference =
            (lon2 - lon1) * Math.PI / 180;

        const a =
            Math.sin(latDifference / 2) *
            Math.sin(latDifference / 2) +

            Math.cos(lat1 * Math.PI / 180) *
            Math.cos(lat2 * Math.PI / 180) *

            Math.sin(lonDifference / 2) *
            Math.sin(lonDifference / 2);

        const c =
            2 * Math.atan2(
                Math.sqrt(a),
                Math.sqrt(1 - a)
            );

        return earthRadius * c;
    }


    /* =========================
       GET SELECTED VEHICLE
       ========================= */

    function getSelectedVehicle() {

        const selected =
            document.querySelector(
                'input[name="vehicleType"]:checked'
            );

        if (!selected) {
            return null;
        }

        return selected.value.toUpperCase();
    }


    /* =========================
       UPDATE FARE
       ========================= */

    function updateFare() {

        if (!pickup || !destination || !fare) {
            return;
        }

        const pickupOption =
            pickup.options[pickup.selectedIndex];

        const destinationOption =
            destination.options[
            destination.selectedIndex
            ];

        if (
            !pickupOption ||
            !destinationOption ||
            !pickupOption.value ||
            !destinationOption.value
        ) {
            fare.textContent = "₹0";

            if (distance) {
                distance.textContent = "0 km";
            }

            return;
        }


        const pickupLat =
            parseFloat(
                pickupOption.dataset.lat
            );

        const pickupLng =
            parseFloat(
                pickupOption.dataset.lng
            );

        const destinationLat =
            parseFloat(
                destinationOption.dataset.lat
            );

        const destinationLng =
            parseFloat(
                destinationOption.dataset.lng
            );


        if (
            isNaN(pickupLat) ||
            isNaN(pickupLng) ||
            isNaN(destinationLat) ||
            isNaN(destinationLng)
        ) {
            return;
        }


        const distanceKm =
            calculateDistance(
                pickupLat,
                pickupLng,
                destinationLat,
                destinationLng
            );


        const roundedDistance =
            Math.round(
                distanceKm * 100
            ) / 100;


        const vehicleType =
            getSelectedVehicle();


        if (distance) {
            distance.textContent =
                roundedDistance + " km";
        }


        if (!vehicleType) {

            fare.textContent = "₹0";

            return;
        }


        const rate =
            rates[vehicleType];


        if (!rate) {

            fare.textContent = "₹0";

            return;
        }


        const calculatedFare =
            roundedDistance * rate;


        const roundedFare =
            Math.round(
                calculatedFare * 100
            ) / 100;


        fare.textContent =
            "₹" + roundedFare.toFixed(2);
    }


    /* =========================
       SAME LOCATION CHECK
       ========================= */

    function validateLocations() {

        if (!pickup || !destination) {
            return true;
        }

        if (
            pickup.value &&
            destination.value &&
            pickup.value === destination.value
        ) {

            alert(
                "Pickup and destination cannot be the same."
            );

            destination.value = "";

            updateFare();

            return false;
        }

        return true;
    }


    /* =========================
       EVENTS
       ========================= */

    if (pickup) {

        pickup.addEventListener(
            "change",
            function() {

                validateLocations();

                updateFare();
            }
        );
    }


    if (destination) {

        destination.addEventListener(
            "change",
            function() {

                if (!validateLocations()) {
                    return;
                }

                updateFare();
            }
        );
    }


    vehicleInputs.forEach(
        function(input) {

            input.addEventListener(
                "change",
                updateFare
            );

        }
    );


    /* =========================
       BOOKING FORM VALIDATION
       ========================= */

    if (bookingForm) {

        bookingForm.addEventListener(
            "submit",
            function(event) {

                if (!validateLocations()) {

                    event.preventDefault();

                    return;
                }


                if (
                    !pickup ||
                    !pickup.value
                ) {

                    event.preventDefault();

                    alert(
                        "Please select pickup location."
                    );

                    return;
                }


                if (
                    !destination ||
                    !destination.value
                ) {

                    event.preventDefault();

                    alert(
                        "Please select destination."
                    );

                    return;
                }


                const vehicleType =
                    getSelectedVehicle();


                if (!vehicleType) {

                    event.preventDefault();

                    alert(
                        "Please select a vehicle."
                    );

                    return;
                }


                if (!rates[vehicleType]) {

                    event.preventDefault();

                    alert(
                        "Invalid vehicle selected."
                    );

                    return;
                }
            }
        );
    }


    /* =========================
       AUTO HIDE ALERTS
       ========================= */

    const alerts =
        document.querySelectorAll(
            ".rl-alert"
        );


    alerts.forEach(
        function(alert) {

            setTimeout(
                function() {

                    alert.style.opacity = "0";

                    alert.style.transition =
                        "opacity 0.4s ease";

                    setTimeout(
                        function() {
                            alert.remove();
                        },
                        400
                    );

                },
                5000
            );

        }
    );


    /* =========================
       INITIAL FARE
       ========================= */

    updateFare();

});