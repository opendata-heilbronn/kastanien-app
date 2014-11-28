package de.opendatalab.kastanien;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GeoPoint {

    private String type = "Point";
    private double[] coordinates;

    public GeoPoint() {
    }

    public GeoPoint(double lat, double lng) {
        this.coordinates = new double[]{lng, lat};
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

    @JsonIgnore
    public double getLongitude() {
        return coordinates[0];
    }

    @JsonIgnore
    public double getLatitude() {
        return coordinates[1];
    }
}
