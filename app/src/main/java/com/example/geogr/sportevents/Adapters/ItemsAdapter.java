package com.example.geogr.sportevents.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.geogr.sportevents.R;
import com.example.geogr.sportevents.api.EventModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by geogr on 16.11.2017.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>{
    List<EventModel> items = new ArrayList<>();

    public ItemsAdapter(List<EventModel> items){
       this.items= items;

    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sporteventsitem, null));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {


             EventModel eventModel=items.get(position);
            if(eventModel==null){
                holder.eventType.setText("0");
                holder.metro.setText("0");
                holder.numberofpeople.setText("0");
            }else {
                holder.eventType.setText(eventModel.getEventype());
                holder.metro.setText(eventModel.getAdress());
                holder.numberofpeople.setText("Number of people:"+eventModel.getPeoplesize());
                holder.datetime.setText(eventModel.getDatetime());
            }

    }
    public void updateId(EventModel item, int id) {
        item.setId(id);
    }
    @Override
    public int getItemCount() {
        if(items==null)
            return 0;
        return items.size();
    }
    public void clear(){
        items.clear();
    }
    public void addAll(List<EventModel> eventModels){
        this.items.addAll(eventModels);
        notifyDataSetChanged();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.eventType) TextView eventType;
        @BindView(R.id.eventMetro) TextView metro;
        @BindView(R.id.list_item_number) TextView numberofpeople;
        @BindView(R.id.eventDatetime) TextView datetime;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
