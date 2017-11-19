package com.example.geogr.sportevents.api;

import com.google.android.gms.maps.model.LatLng;
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
    @SerializedName("position")
    @Expose
    private String position;

    public EventModel(int id, String eventype, String metro, String peoplesize, String eventDescription, String position){
        this.id=id;
        this.eventype=eventype;
        this.metro=metro;
        this.peoplesize=peoplesize;
        this.eventDescription=eventDescription;
        this.position=position;
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
    public void setPosition(String position) {
        this.position = position;
    }
    public String getPosition() {
        return position;
    }
}