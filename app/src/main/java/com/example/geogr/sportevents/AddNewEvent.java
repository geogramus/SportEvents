package com.example.geogr.sportevents;

import android.content.Context;
import android.content.Intent;
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

import java.io.Serializable;

/**
 * Created by geogr on 16.11.2017.
 */

public class AddNewEvent extends AppCompatActivity{
    public static final int RC_ADD_ITEM = 55;
    public static final String RESULT_ITEM = "item";
    public static final String EXTRA_TYPE = "type";
    private String type;
    int PLACE_PICKER_REQUEST = 1;
    private static final double TARGET_LATITUDE = 55.7541679;
    private static final double TARGET_LONGITUDE = 37.62079239;

    public static Marker marker;
    private GoogleApiClient mClient;
    GoogleMap googleMap;
    FloatingActionButton floatingActionButton;
    Spinner sportType;
    Spinner metro;
    Spinner amountOfPeople;
    TextView description;
    String metroResult, typeResult;
    CustomSpinnerAdapter adapter;
    String[] metroList={"Sokol","Kievskaya", "Belarusskaya"};
    Button mapButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewevent);
        mapButton=(Button) findViewById(R.id.mapButton);
        floatingActionButton=(FloatingActionButton) findViewById(R.id.addNewEventButton);
        sportType=(Spinner) findViewById(R.id.spinnersport);
        metro=(Spinner) findViewById(R.id.spinnermetro);
        amountOfPeople=(Spinner) findViewById(R.id.spinerAmountOfPeople);
        description=(TextView) findViewById(R.id.eventDescription);
        adapter=new CustomSpinnerAdapter(AddNewEvent.this, R.layout.spinnermetrorow, metroList);
        metro.setAdapter(adapter);
        createMapView();
        addMarker(TARGET_LATITUDE, TARGET_LONGITUDE);

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
                Intent result=new Intent();
                result.putExtra("item", new EventModel(76,
                        sportType.getSelectedItem().toString(),
                        metro.getSelectedItem().toString(),
                        amountOfPeople.getSelectedItem().toString(),
                        description.getText().toString(),
                        String.valueOf(marker.getPosition())));
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }

    private class CustomSpinnerAdapter extends ArrayAdapter<String> {
        public CustomSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, String[] metroList) {
            super(context, resource, metroList);
        }



        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }
        public View getCustomView(int position, View convertVview, ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.spinnermetrorow, parent, false);
            TextView label = (TextView) row.findViewById(R.id.spinnerMetroText);
            label.setText(metroList[position]);
            ImageView icon = (ImageView) row.findViewById(R.id.spinnerMetroImg);
            icon.setImageResource(R.drawable.original);
            return row;
        }
    }
    private void createMapView(){
        try {
            if(null == googleMap){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.mapView)).getMap();

                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == googleMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map",Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }
    private void addMarker(double x, double y){
        double lat=x;
        double lng=y;

        //устанавливаем позицию и масштаб отображения карты
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lng))
                .zoom(9)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMap.animateCamera(cameraUpdate);
         if(null != googleMap){
            marker = googleMap.addMarker(new MarkerOptions()
                     .draggable(true)
                     .position(new LatLng(lat, lng))
                     .title("Marker")
                     .draggable(true)
             );

            googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(Marker marker) {

                }
            });
        }
    }
    public void markerSetPosition(LatLng latLng){
        marker.setPosition(latLng);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                LatLng latLng = place.getLatLng();
                markerSetPosition(latLng);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
