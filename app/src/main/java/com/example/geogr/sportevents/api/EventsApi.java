package com.example.geogr.sportevents.api;

import com.google.android.gms.maps.model.LatLng;

import org.reactivestreams.Subscriber;

import java.util.List;


import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by geogr on 16.11.2017.
 */

public interface EventsApi {
        @Headers("Content-Type: application/json")
        @GET("events")
        Observable<List<EventModel>> getData();

        @POST("events")
       Observable<AddResult> add(@Query("id") int id,
                                  @Query("eventype")String type,
                        /*    @Query("metro") String metro,*/
                            @Query("peoplesize") String peoplesize,
                                  @Query("eventdescription")String eventdescription,
                                  @Query("adress")String adress,
                                  @Query("vkid")String vkid,
                                  @Query("firstlastname")String firtstname,
                                  @Query("phonenumber")String phonenumber,
                                  @Query("datetime") String datetime,
                                  @Query("latitude") Double latitude,
                                  @Query("longtitude") Double longtitude);
}
