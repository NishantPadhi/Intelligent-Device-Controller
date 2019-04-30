package com.example.devicecontrollernew;

public class Device {
    String name;
    String number;
    String id;
    double latitude;
    double longitude;
    boolean d1,d2,d3;
    public Device()
    {

    }
    public Device(String id,String name,String number,double latitude,double longitude,boolean d1,boolean d2,boolean d3)
    {
        this.id=id;
        this.name=name;
        this.number=number;
        this.latitude=latitude;
        this.longitude=longitude;
        this.d1=d1;
        this.d2=d2;
        this.d3=d3;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public double getLatitude() {
        return latitude;
    }

    public boolean isD1() {
        return d1;
    }

    public boolean isD2() {
        return d2;
    }

    public boolean isD3() {
        return d3;
    }

    public double getLongitude() {
        return longitude;
    }
}
