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

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventViewActivity extends AppCompatActivity {
    Map map= new Map();

    Double latitude;
    Double longitude;
    EventModel eventModel;
    @BindView(R.id.activity_eventtype)
    TextView type;
    @BindView(R.id.activity_eventpeopleamount)
    TextView amount;
    @BindView(R.id.activity_eventpeopledescription)
    TextView description;
    @BindView(R.id.activity_eventadress)
    TextView adress;
    @BindView(R.id.eventCreator)
    TextView creator;
    @BindView(R.id.activity_eventphoneNumber)
    TextView phonenumber;
    @BindView(R.id.activity_eventdatetime)
    TextView datetime;
    @BindView(R.id.plus)
    TextView plus;
    @BindView(R.id.minus)
    TextView minus;
    String notFound="Not Found";
    String url;
    @BindView(R.id.vkbutton)
    ImageView vk;
    int zoom=12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);
        eventModel=(EventModel) getIntent().getSerializableExtra("event");

        url="http://vk.com/id"+eventModel.getVkid();

        if(eventModel.getEventype()==null){
           type.setText(notFound);
        }else{ type.setText(eventModel.getEventype());}
       if(eventModel.getPeoplesize()==null){ amount.setText("Amount of people: "+notFound);}else{ amount.setText("Amount of people: "+eventModel.getPeoplesize());}
        if(eventModel.getAdress()==null){ adress.setText("Adress: "+notFound);}else{ adress.setText("Adress: "+eventModel.getAdress());}
        if(eventModel.getEventDescription()==null){ description.setText("Description of event: "+notFound);}else{description.setText("Description of event: "+eventModel.getEventDescription());}
        if(eventModel.getFirstlastname()==null){creator.setText("Event creator: "+notFound);}else{creator.setText("Event creator: "+eventModel.getFirstlastname());}
        if(eventModel.getPhonenumber()==null){phonenumber.setText("Phone number: "+notFound);}else{phonenumber.setText("Phone number: "+eventModel.getPhonenumber());}
        if(eventModel.getDatetime()==null){datetime.setText("Date time: "+notFound);}else{datetime.setText(eventModel.getDatetime());}


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

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(zoom==22){
                    Toast.makeText(EventViewActivity.this, "Maximum", Toast.LENGTH_SHORT).show();
                }
                else {
                zoom++;
                map.setzoom(zoom);
                }
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(zoom==0){
                    Toast.makeText(EventViewActivity.this, "Minimum", Toast.LENGTH_SHORT).show();
                }
                else {
                    zoom--;
                    map.setzoom(zoom);
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
//    @OnClick(R.id.vkbutton)
//    public void calling(){
//        if(eventModel.getPhonenumber()!=null){
//            Uri number = Uri.parse("tel:"+eventModel.getPhonenumber());
//            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
//            startActivity(callIntent);
//        }
//        else {
//            Toast.makeText(EventViewActivity.this, "Date error", Toast.LENGTH_LONG).show();
//        }
//
//    }
}
