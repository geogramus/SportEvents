package com.example.geogr.sportevents;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geogr.sportevents.Adapters.CustomSpinnerAdapter;
import com.example.geogr.sportevents.ProjectMap.Map;
import com.example.geogr.sportevents.api.EventModel;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.wallet.Address;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

/**
 * Created by geogr on 16.11.2017.
 */

public class AddNewEvent extends AppCompatActivity{
    public static final int RC_ADD_ITEM = 55;
    private String type;
    int PLACE_PICKER_REQUEST = 1;
    private static final double TARGET_LATITUDE = 55.7541679;
    private static final double TARGET_LONGITUDE = 37.62079239;


    FloatingActionButton floatingActionButton;
    Spinner sportType;
    Spinner metro;
    Spinner amountOfPeople;
    TextView description;
    TextView adress;
    String metroResult, typeResult;
    CustomSpinnerAdapter adapter;
    String[] metroList={"...","Sokol","Kievskaya", "Belarusskaya"};
    Button mapButton;
    int id;
    Map map=new Map();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewevent);

        id=getIntent().getIntExtra("id",0);
        mapButton=(Button) findViewById(R.id.mapButton);
        floatingActionButton=(FloatingActionButton) findViewById(R.id.addNewEventButton);
        sportType=(Spinner) findViewById(R.id.spinnersport);
        adress=(TextView) findViewById(R.id.addneweventAdress);
        metro=(Spinner) findViewById(R.id.spinnermetro);
        amountOfPeople=(Spinner) findViewById(R.id.spinerAmountOfPeople);
        description=(TextView) findViewById(R.id.eventDescription);
        adapter=new CustomSpinnerAdapter(AddNewEvent.this, R.layout.spinnermetrorow, metroList);
        metro.setAdapter(adapter);
        map.createMapView(getFragmentManager(), R.id.mapView);
        map.addMarker(TARGET_LATITUDE, TARGET_LONGITUDE);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    intent = builder.build(getApplicationContext());
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }



            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check="...";
                String descriptioncheck="";
                if( sportType.getSelectedItem().toString().equals(check)||
                        adress.getText().equals("Select Address")||
                        amountOfPeople.getSelectedItem().toString().equals(check)||
                        description.getText().toString().equals(descriptioncheck)||
                        adress.getText().toString().equals("Select Address")||
                        map.markerGetPosition().latitude==TARGET_LATITUDE||
                        map.markerGetPosition().longitude==TARGET_LONGITUDE){
                    Toast.makeText(AddNewEvent.this, R.string.errorfieldstoast, Toast.LENGTH_LONG).show();
                }
                else{
                Intent result=new Intent();
                result.putExtra("item", new EventModel(id,
                        sportType.getSelectedItem().toString(),
                        metro.getSelectedItem().toString(),
                        amountOfPeople.getSelectedItem().toString(),
                        description.getText().toString(),
                        adress.getText().toString(),
                        map.markerGetPosition().latitude,
                        map.markerGetPosition().longitude)
                        );
                setResult(RESULT_OK, result);
                finish();}
            }
        });
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String adressText=String.valueOf(place.getAddress());
                adress.setText(adressText);
                LatLng latLng = place.getLatLng();
                map.markerSetPosition(latLng);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

            }
        }
    }
}
