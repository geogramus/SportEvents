package com.example.geogr.sportevents;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geogr.sportevents.ProjectMap.Map;
import com.example.geogr.sportevents.api.EventModel;
import com.google.android.gms.maps.model.LatLng;

public class EventViewActivity extends AppCompatActivity {
    Map map= new Map();
    Double latitude;
    Double longitude;
    EventModel eventModel;
    TextView type, amount, description,adress, creator, phonenumber;
    String notFound="Not Found";
    String url;
    ImageView vk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        eventModel=(EventModel) getIntent().getSerializableExtra("event");

        url="http://vk.com/id"+eventModel.getVkid();

        type=(TextView) findViewById(R.id.activity_eventtype);
        amount=(TextView) findViewById(R.id.activity_eventpeopleamount);
        adress=(TextView) findViewById(R.id.activity_eventadress);
        description=(TextView) findViewById(R.id.activity_eventpeopledescription);
        creator=(TextView) findViewById(R.id.eventCreator);
        phonenumber=(TextView) findViewById(R.id.activity_eventphoneNumber);
        vk=(ImageView) findViewById(R.id.vkbutton);


        if(eventModel.getEventype()==null){
           type.setText(notFound);
        }else{ type.setText(eventModel.getEventype());}
       if(eventModel.getPeoplesize()==null){ amount.setText("Amount of people: "+notFound);}else{ amount.setText("Amount of people: "+eventModel.getPeoplesize());}
        if(eventModel.getAdress()==null){ adress.setText("Adress: "+notFound);}else{ adress.setText("Adress: "+eventModel.getAdress());}
        if(eventModel.getEventDescription()==null){ description.setText("Description of event: "+notFound);}else{description.setText("Description of event: "+eventModel.getEventDescription());}
        if(eventModel.getFirstlastname()==null){creator.setText("Event creator: "+notFound);}else{creator.setText("Event creator: "+eventModel.getFirstlastname());}
        if(eventModel.getPhonenumber()==null){phonenumber.setText("Phone number: "+notFound);}else{phonenumber.setText("Phone number: "+eventModel.getPhonenumber());}


        if((eventModel.getLongitude()==0.0 && eventModel.getLatitude()==0.0)||eventModel.getLatitude()==null||eventModel.getLongitude()==null){
            latitude=55.7541679 ;
            longitude=37.62079239;
        }else {
                latitude=eventModel.getLatitude();
                longitude=eventModel.getLongitude();
        }
        map.createMapView(getFragmentManager(), R.id.activity_eventmapView);
        map.addMarker(latitude, longitude);
        vk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eventModel.getVkid()!=null){
                Intent vkintent= new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(vkintent);}
                else {
                    Toast.makeText(EventViewActivity.this, "Date error", Toast.LENGTH_LONG).show();
                }
            }
        });
        phonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eventModel.getPhonenumber()!=null){
                Uri number = Uri.parse("tel:"+eventModel.getPhonenumber());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
                }
                else {
                    Toast.makeText(EventViewActivity.this, "Date error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
