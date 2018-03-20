package com.example.geogr.sportevents;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.geogr.sportevents.DateTime.DatePickerFragment;
import com.example.geogr.sportevents.DateTime.TimePickerFragment;
import com.example.geogr.sportevents.DateTime.Utils;
import com.example.geogr.sportevents.ProjectMap.Map;
import com.example.geogr.sportevents.api.EventModel;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by geogr on 16.11.2017.
 */

public class AddNewEvent extends AppCompatActivity{
    public static final int RC_ADD_ITEM = 55;
    int PLACE_PICKER_REQUEST = 1;
    private static final double TARGET_LATITUDE = 55.7541679;
    private static final double TARGET_LONGITUDE = 37.62079239;


    @BindView(R.id.addNewEventButton) FloatingActionButton floatingActionButton;
    @BindView(R.id.spinnersport) Spinner sportType;
    @BindView(R.id.spinerAmountOfPeople) Spinner amountOfPeople;
    @BindView(R.id.eventDescription) TextView description;
    @BindView(R.id.addneweventAdress) TextView adress;
    @BindView(R.id.phoneNumber)  TextView phoneNumber;
    @BindView(R.id.mapButton) Button mapButton;
    @BindView(R.id.iddate) TextInputEditText date;
    @BindView(R.id.idtime) TextInputEditText time;
    int id;
    Map map=new Map();
    String uservkid, userfirstlastname;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewevent);

        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        id=getIntent().getIntExtra("id",0);

        final Calendar calendar=Calendar.getInstance();

        map.createMapView(getFragmentManager(), R.id.mapView);
        map.addMarker(TARGET_LATITUDE, TARGET_LONGITUDE);

        getCreatorInformation();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (date.length()==0){
                    date.setText(" ");
                }
                DialogFragment datePickerFragment= new DatePickerFragment(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.YEAR, i);
                        calendar.set(Calendar.MONTH, i1);
                        calendar.set(Calendar.DAY_OF_MONTH, i2);

                        date.setText(Utils.getDate(calendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        date.setText(null);
                    }
                };
                datePickerFragment.show(getFragmentManager(), "DatePickerFragment");
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(time.length()==0){
                    time.setText(" ");
                }
                DialogFragment timePickerFragment=new TimePickerFragment(){
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        calendar.set(Calendar.HOUR_OF_DAY, i);
                        calendar.set(Calendar.MINUTE, i1);
                        calendar.set(Calendar.SECOND, 0);
                        time.setText(Utils.getTime(calendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        time.setText(null);
                    }
                };
                timePickerFragment.show(getFragmentManager(),"TimePickerFragment");
            }
        });
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
                if( sportType.getSelectedItem().toString().equals("Select type of the event")||
                        adress.getText().equals("Select Address")||
                        amountOfPeople.getSelectedItem().toString().equals("Select number of people")||
                        description.getText().toString().equals(descriptioncheck)||
                        adress.getText().toString().equals("Select Address")||
                        map.markerGetPosition().latitude==TARGET_LATITUDE||
                        map.markerGetPosition().longitude==TARGET_LONGITUDE||
                        uservkid.equals(null)||
                        userfirstlastname.equals(null)||
                        date.getText().toString().equals("")||
                        time.getText().toString().equals("")||
                        phoneNumber.getText().equals("")){
                    Toast.makeText(AddNewEvent.this, R.string.errorfieldstoast, Toast.LENGTH_LONG).show();
                }
                else{
                String datetime=date.getText().toString()+" "+time.getText().toString();
                Intent result=new Intent();
                result.putExtra("item", new EventModel(id,
                        sportType.getSelectedItem().toString(),
                        amountOfPeople.getSelectedItem().toString(),
                        description.getText().toString(),
                        adress.getText().toString(),
                        uservkid,
                        userfirstlastname,
                        phoneNumber.getText().toString(),
                        datetime,
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
                String[] devied = adressText.split(",");
                adress.setText(devied[0]+" "+devied[1]+ " "+devied[2]);
                LatLng latLng = place.getLatLng();
                map.markerSetPosition(latLng);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

            }
        }
    }
    public void getCreatorInformation(){
        VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
                "id,first_name,last_name"));


        if(request!=null) {
            request.executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    super.onComplete(response);
                    VKList<VKApiUser> list = (VKList<VKApiUser>) response.parsedModel;
                    for (VKApiUser user : list) {
                        uservkid =  String.valueOf(user.id);
                        userfirstlastname =String.valueOf(user.first_name)+" "+String.valueOf(user.last_name);
                    }
                }
            });
        }
        else {
            Toast.makeText(AddNewEvent.this, "Vk request Errow", Toast.LENGTH_SHORT).show();
        }
    }
}
