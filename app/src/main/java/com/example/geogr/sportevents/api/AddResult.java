package com.example.geogr.sportevents.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by geogr on 16.11.2017.
 */

public class AddResult{
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







