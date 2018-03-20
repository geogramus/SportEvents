package com.example.geogr.sportevents.ProjectMap;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.geogr.sportevents.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.vk.sdk.VKUIHelper.getApplicationContext;

/**
 * Created by geogr on 19.11.2017.
 */


public class Map{
 private Marker marker;
  private GoogleMap googleMap;
    private int zoom=12;
    public void createMapView(FragmentManager fragmentManager, int id){

        try {
            if(null == googleMap){
                googleMap = ((MapFragment) fragmentManager.findFragmentById(id)).getMap();

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
//    int zoom=12;
//    public void cameraPlus(){
//        zoom=zoom++;
//    }
//    public void cameraMinus(){
//        zoom=--zoom;
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(new LatLng(lat, lng))
//                .zoom(zoom)
//                .build();
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
//    }
    public void addMarker(double x, double y){

        //устанавливаем позицию и масштаб отображения карты
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(x, y))
                .zoom(zoom)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMap.animateCamera(cameraUpdate);
        if(null != googleMap){
            marker = googleMap.addMarker(new MarkerOptions()
                    .draggable(true)
                    .position(new LatLng(x, y))
                    .title("Marker")
                    .draggable(false)
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
        if(null != googleMap){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude))
                .zoom(12)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.animateCamera(cameraUpdate);
        }

    }
    public void setzoom(int setZoom) {

        if (setZoom == 20) {
            return;
        } else {

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude))
                    .zoom(setZoom)
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.animateCamera(cameraUpdate);
        }
    }

    public LatLng markerGetPosition(){
        return marker.getPosition();
    }
}
