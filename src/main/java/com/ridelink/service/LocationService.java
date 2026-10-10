package com.ridelink.service;

import java.util.List;

import com.ridelink.dao.LocationDAO;
import com.ridelink.model.Location;

public class LocationService {

    private final LocationDAO locationDAO;

    public LocationService() {
        locationDAO = new LocationDAO();
    }

    public List<Location> getAllLocations() {
        return locationDAO.getAllLocations();
    }

    public Location getLocationById(int locationId) {

        if (locationId <= 0) {
            return null;
        }

        return locationDAO.getLocationById(locationId);
    }
}