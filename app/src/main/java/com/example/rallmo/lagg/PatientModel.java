package com.example.rallmo.lagg;

import java.io.Serializable;

/**
 * Created by rallmo on 2015-04-29.
 */
public class PatientModel implements Serializable {
    private String name;
    private String medicine;
    private String image;

    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMedicine(){
        return medicine;
    }
    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }
    public String getImage(){
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
