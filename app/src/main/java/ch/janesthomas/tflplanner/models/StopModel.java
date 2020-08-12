package ch.janesthomas.tflplanner.models;

public class StopModel {

    private String id;
    private String commonName;
    private double lat;
    private double lon;

    public StopModel(String id, String commonName, double lat, double lon) {
        this.id = id;
        this.commonName = commonName;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
