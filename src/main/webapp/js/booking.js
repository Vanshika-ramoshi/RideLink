// Distance + estimated fare for the rider dashboard booking form
(function() {
    var pickup = document.getElementById("pickup");
    var destination = document.getElementById("destination");
    var distanceEl = document.getElementById("distance");
    var fareEl = document.getElementById("fare");
    if (!pickup || !destination || !distanceEl || !fareEl) return;

    var RUPEE = "\u20B9";
    var RATES = { BIKE: 10, AUTO: 15, CAR: 20 };

    function point(select) {
        var opt = select.options[select.selectedIndex];
        if (!opt || !opt.value) return null;
        var lat = parseFloat(opt.getAttribute("data-lat"));
        var lng = parseFloat(opt.getAttribute("data-lng"));
        if (isNaN(lat) || isNaN(lng)) return null;
        return { lat: lat, lng: lng };
    }

    // Haversine distance in km
    function km(a, b) {
        var R = 6371, rad = Math.PI / 180;
        var dLat = (b.lat - a.lat) * rad;
        var dLng = (b.lng - a.lng) * rad;
        var x = Math.sin(dLat / 2) * Math.sin(dLat / 2)
            + Math.cos(a.lat * rad) * Math.cos(b.lat * rad)
            * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        return R * 2 * Math.atan2(Math.sqrt(x), Math.sqrt(1 - x));
    }

    function disableSamePickup() {
        Array.prototype.forEach.call(destination.options, function(o) {
            o.disabled = o.value !== "" && o.value === pickup.value;
        });
        if (destination.value === pickup.value) destination.value = "";
    }

    function update() {
        var a = point(pickup), b = point(destination);
        if (!a || !b) {
            distanceEl.textContent = "Select locations";
            fareEl.textContent = RUPEE + "0";
            return;
        }
        var d = km(a, b);
        distanceEl.textContent = d.toFixed(1) + " km";

        var vehicle = document.querySelector('input[name="vehicleType"]:checked');
        fareEl.textContent = vehicle
            ? RUPEE + Math.round(d * RATES[vehicle.value])
            : RUPEE + "0";
    }

    pickup.addEventListener("change", function() { disableSamePickup(); update(); });
    destination.addEventListener("change", update);
    Array.prototype.forEach.call(
        document.querySelectorAll('input[name="vehicleType"]'),
        function(r) { r.addEventListener("change", update); });
})();