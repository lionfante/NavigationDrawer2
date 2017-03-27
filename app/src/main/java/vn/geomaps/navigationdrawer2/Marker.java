package vn.geomaps.navigationdrawer2;

/**
 * Created by lionf_000 on 27-Mar-17.
 */

public class Marker {
    private String type;
    private int id;
    private String name;
    private double latitude;
    private double longtitude;

    public Marker(String type,int id,String name,double latitude,double longtitude){
        this.type = type;
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }


}
