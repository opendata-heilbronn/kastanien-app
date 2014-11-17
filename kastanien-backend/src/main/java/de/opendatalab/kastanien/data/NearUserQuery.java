package de.opendatalab.kastanien.data;

/**
 * Created by adrian on 23.10.14.
 */
public class NearUserQuery {

	private int distance;
	private double latitude;
	private double longitude;

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
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
