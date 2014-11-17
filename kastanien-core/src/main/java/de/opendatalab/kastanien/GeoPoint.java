package de.opendatalab.kastanien;

public class GeoPoint {

	private String type = "Point";
	private double[] coordinates;

	public GeoPoint() {
	}

	public GeoPoint(double lat, double lng) {
		this.coordinates = new double[] { lng, lat };
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(double[] coordinates) {
		this.coordinates = coordinates;
	}
}
