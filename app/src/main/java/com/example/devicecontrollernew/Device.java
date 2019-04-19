package com.example.devicecontrollernew;

public class Device {
    String name;
    String number;
    String id;
    public Device()
    {

    }
    public Device(String id,String name,String number)
    {
        this.id=id;
        this.name=name;
        this.number=number;
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
}
