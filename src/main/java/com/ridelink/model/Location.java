package com.ridelink.model;

public class Location {

	private int locationId;
	private String locationName;
	private String city;
	private double latitude;
	private double longitude;

	public Location() {
	}

	public Location(int locationId, String locationName, String city, double latitude, double longitude) {
		this.locationId = locationId;
		this.locationName = locationName;
		this.city = city;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}