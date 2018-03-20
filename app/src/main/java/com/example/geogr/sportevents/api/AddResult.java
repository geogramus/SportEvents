package com.example.geogr.sportevents.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by geogr on 16.11.2017.
 */

public class AddResult implements Serializable{
    @SerializedName("status")
    @Expose
    private String status;
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}







