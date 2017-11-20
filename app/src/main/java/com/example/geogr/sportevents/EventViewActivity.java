package com.example.geogr.sportevents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.geogr.sportevents.ProjectMap.Map;
import com.example.geogr.sportevents.api.EventModel;
import com.google.android.gms.maps.model.LatLng;

public class EventViewActivity extends AppCompatActivity {
    Map map= new Map();
    Double latitude;
    Double longitude;
    EventModel eventModel;
    TextView id, type, metro, amount, description,adress;
    String notFound="Not Found";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        eventModel=(EventModel) getIntent().getSerializableExtra("event");
      //  id=(TextView) findViewById(R.id.activity_eventid);
        type=(TextView) findViewById(R.id.activity_eventtype);
        metro=(TextView) findViewById(R.id.activity_eventmetro);
        amount=(TextView) findViewById(R.id.activity_eventpeopleamount);
        adress=(TextView) findViewById(R.id.activity_eventadress);
        description=(TextView) findViewById(R.id.activity_eventpeopledescription);
        if(eventModel.getEventype()==null){
           type.setText(notFound);
        }else{ type.setText(eventModel.getEventype());}
        if(eventModel.getMetro()==null){     metro.setText(notFound);}else{     metro.setText(eventModel.getMetro());}
       if(eventModel.getPeoplesize()==null){ amount.setText("Amount of people:"+notFound);}else{ amount.setText("Amount of people:"+eventModel.getPeoplesize());}
        if(eventModel.getAdress()==null){ adress.setText("Adress:"+notFound);}else{ adress.setText("Adress:"+eventModel.getAdress());}
        if(eventModel.getEventDescription()==null){ description.setText("Ddescription of event:"+notFound);}else{description.setText("Ddescription of event:"+eventModel.getEventDescription());}


        if((eventModel.getLongitude()==0.0 && eventModel.getLatitude()==0.0)||eventModel.getLatitude()==null||eventModel.getLongitude()==null){
            latitude=55.7541679 ;
            longitude=37.62079239;
        }else {
                latitude=eventModel.getLatitude();
                longitude=eventModel.getLongitude();
        }
        map.createMapView(getFragmentManager(), R.id.activity_eventmapView);
        map.addMarker(latitude, longitude);
    }
}
