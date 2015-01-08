package de.opendatalab.kastanien;

public class BoundingBox {

    private double west = Double.POSITIVE_INFINITY;
    private double east = Double.NEGATIVE_INFINITY;
    private double south = Double.POSITIVE_INFINITY;
    private double north = Double.NEGATIVE_INFINITY;

    public void addPosition(GeoPoint geoPoint) {
        if (west > geoPoint.getLongitude()) {
            west = geoPoint.getLongitude();
        }
        if (east < geoPoint.getLongitude()) {
            east = geoPoint.getLongitude();
        }
        if (south > geoPoint.getLatitude()) {
            south = geoPoint.getLatitude();
        }
        if (north < geoPoint.getLatitude()) {
            north = geoPoint.getLatitude();
        }
    }

    public double getWest() {
        return west;
    }

    public void setWest(double west) {
        this.west = west;
    }

    public double getEast() {
        return east;
    }

    public void setEast(double east) {
        this.east = east;
    }

    public double getSouth() {
        return south;
    }

    public void setSouth(double south) {
        this.south = south;
    }

    public double getNorth() {
        return north;
    }

    public void setNorth(double north) {
        this.north = north;
    }
}
