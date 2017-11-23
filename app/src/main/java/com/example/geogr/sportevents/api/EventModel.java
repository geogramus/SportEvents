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
 /*   @SerializedName("metro")
    @Expose
    private String metro;*/
    @SerializedName("peoplesize")
    @Expose
    private String peoplesize;
    @SerializedName("eventdescription")
    @Expose
    private String eventDescription;
    @SerializedName("adress")
    @Expose
    private String adress;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longtitude")
    @Expose
    private double longitude;
    @SerializedName("vkid")
    @Expose
    private String vkid;
    @SerializedName("firstlastname")
    @Expose
    private String firstlastname;
    @SerializedName("phonenumber")
    @Expose
    private String phonenumber;

    public EventModel(int id, String eventype, /*String metro,*/ String peoplesize, String eventDescription,String adress,String vkid, String firstlastname, String phonenumber, double latitude, double longitude){
        this.id=id;
        this.eventype=eventype;
      /*  this.metro=metro;*/
        this.peoplesize=peoplesize;
        this.eventDescription=eventDescription;
        this.adress=adress;
        this.vkid=vkid;
        this.firstlastname=firstlastname;
        this.phonenumber=phonenumber;
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
    public void setVkid(String vkid) {
        this.vkid = vkid;
    }
    public String getVkid() {
        return vkid;
    }
    public void setFirstlastname(String firstlastname) {
        this.firstlastname= firstlastname;
    }
    public String getFirstlastname() {
        return firstlastname;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber= phonenumber;
    }
    public String getPhonenumber() {
        return phonenumber;
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