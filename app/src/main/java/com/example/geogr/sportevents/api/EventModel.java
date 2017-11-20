package com.example.geogr.sportevents.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by geogr on 16.11.2017.
 */

public class EventModel implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("eventype")
    @Expose
    private String eventype;
    @SerializedName("metro")
    @Expose
    private String metro;
    @SerializedName("peoplesize")
    @Expose
    private String peoplesize;
    @SerializedName("eventdescription")
    @Expose
    private String eventDescription;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longtitude")
    @Expose
    private double longitude;
    @SerializedName("adress")
    @Expose
    private String adress;

    public EventModel(int id, String eventype, String metro, String peoplesize, String eventDescription,String adress, double latitude, double longitude){
        this.id=id;
        this.eventype=eventype;
        this.metro=metro;
        this.peoplesize=peoplesize;
        this.eventDescription=eventDescription;
        this.adress=adress;
        this.latitude=latitude;
        this.longitude=longitude;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) { this.id = id; }

    public String getEventype() {
        return eventype;
    }

    public void setEventype(String eventype) {
        this.eventype = eventype;
    }

    public String getMetro() {
        return metro;
    }

    public void setMetro(String metro) {
        this.metro = metro;
    }

    public void setPeoplesize(String peoplesize) {
        this.peoplesize = peoplesize;
    }
    public String getPeoplesize() {
        return peoplesize;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
    public String getEventDescription() {
        return eventDescription;
    }
    public void setAdress(String adress) {
        this.adress = adress;
    }
    public String getAdress() {
        return adress;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public Double getLongitude() {
        return longitude;
    }
}