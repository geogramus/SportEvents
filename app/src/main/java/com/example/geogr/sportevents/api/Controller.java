package com.example.geogr.sportevents.api;

import android.app.Application;

import com.example.geogr.sportevents.BuildConfig;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by geogr on 16.11.2017.
 */

public class Controller extends Application{
    static final String BAASE_URL=" http://sportevents.getsandbox.com/";
    public static EventsApi getApi(){
        Gson gson=new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        final OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG?HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                .build();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BAASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        EventsApi eventsApi=retrofit.create(EventsApi.class);
        return eventsApi;
    }
}
