package com.example.geogr.sportevents;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.geogr.sportevents.Adapters.ItemsAdapter;
import com.example.geogr.sportevents.RecyclerView.RecyclerClickListener;
import com.example.geogr.sportevents.api.AddResult;
import com.example.geogr.sportevents.api.Controller;
import com.example.geogr.sportevents.api.EventModel;
import com.example.geogr.sportevents.api.EventsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.example.geogr.sportevents.AddNewEvent.RC_ADD_ITEM;

/**
 * Created by geogr on 16.11.2017.
 */

public class SportEventsListFragment extends Fragment {
    private ItemsAdapter adapter;
    private static final int LOADER_ITEMS = 0;
    private static final int LOADER_ADD = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sporteventsitems, null);
    }
    final List<EventModel> eventitems = new ArrayList<>();
    private static EventsApi eventsApi;
    SwipeRefreshLayout refresh;
    Controller controller;
    FloatingActionButton fab;
    AddResult status;
    String statusstring;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView items=(RecyclerView) view.findViewById(R.id.items);
        controller=new Controller();
        eventsApi= controller.getApi();
        MainActivity.logoutMI.setVisible(true);
        adapter=new ItemsAdapter(eventitems);
        items.setAdapter(adapter);

        loadItems();

        refresh=(SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });
        fab=(FloatingActionButton) view.findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),AddNewEvent.class);
                if(adapter.getItemCount()==0){
                    Toast.makeText(getContext(), "Loading id Error", Toast.LENGTH_SHORT).show();
                    intent.putExtra("id", 0);
                }else{
                     int id=eventitems.get(0).getId()+1;
                     intent.putExtra("id", id);
                }

                startActivityForResult(intent, RC_ADD_ITEM);
            }
        });
        items.addOnItemTouchListener(new RecyclerClickListener(getContext()) {
            @Override
            public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
                Intent intent=new Intent(getContext(), EventViewActivity.class);
                EventModel event = eventitems.get(position);
                intent.putExtra("event", new EventModel(event.getId(), event.getEventype(),
                        event.getMetro(),event.getPeoplesize(),
                        event.getEventDescription(), event.getAdress(),
                        event.getLatitude(), event.getLongitude()));
                startActivity(intent);
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }
    private void loadItems(){
        getLoaderManager().initLoader(LOADER_ITEMS, null, new LoaderManager.LoaderCallbacks<List<EventModel>>() {
            @Override
            public Loader<List<EventModel>> onCreateLoader(int id, Bundle args) {
               return new AsyncTaskLoader<List<EventModel>>(getContext()) {
                   @Override
                   public List<EventModel> loadInBackground() {
                        try{
                           Call<List<EventModel>> modelItems=eventsApi.getData();
                            Response<List<EventModel>> execute=modelItems.execute();
                            List<EventModel> body=execute.body();
                            return body;
                        }
                        catch (Exception e){
                            e.printStackTrace();
                            return null;
                        }
                   }
               };
            }

            @Override
            public void onLoadFinished(Loader<List<EventModel>> loader, List<EventModel> data) {
                if (data == null) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.clear();
                    adapter.addAll(data);
                }
                refresh.setRefreshing(false);
                eventitems.size();
            }

            @Override
            public void onLoaderReset(Loader<List<EventModel>> loader) {

            }
        }).forceLoad();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==RC_ADD_ITEM && resultCode==RESULT_OK){
            EventModel eventModel=(EventModel) data.getSerializableExtra("item");
            addItem(eventModel);
        }
    }

    private void addItem(final EventModel eventModel) {

        getLoaderManager().restartLoader(LOADER_ADD, null, new LoaderManager.LoaderCallbacks<AddResult>() {
            @Override
            public Loader<AddResult> onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader<AddResult>(getContext()) {
                    @Override
                    public AddResult loadInBackground() {
                        try{
                            status= eventsApi.add(eventModel.getId(),
                                    eventModel.getEventype(),
                                    eventModel.getMetro(),
                                    eventModel.getPeoplesize(),
                                    eventModel.getEventDescription(),
                                    eventModel.getAdress(),
                                    eventModel.getLatitude(),
                                    eventModel.getLongitude()).execute().body();
                            return status;
                        }
                        catch (Exception e){
                            e.printStackTrace();
                            return null;
                        }
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<AddResult> loader, AddResult data) {
                loadItems();
                adapter.notifyDataSetChanged();
                Toast toast = Toast.makeText(getContext(), "Event Add", Toast.LENGTH_LONG);
                toast.show();

            }

            @Override
            public void onLoaderReset(Loader<AddResult> loader) {

            }
        }).forceLoad();
    }
}
