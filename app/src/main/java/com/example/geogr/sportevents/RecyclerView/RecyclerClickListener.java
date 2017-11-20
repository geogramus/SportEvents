package com.example.geogr.sportevents.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by geogr on 19.11.2017.
 */

public abstract class RecyclerClickListener implements RecyclerView.OnItemTouchListener{
    private GestureDetector gestureDetector;
    private GestureDetector.OnGestureListener gestureListener=
            new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            };
    Context context;
    public RecyclerClickListener(Context context){
        gestureDetector = new GestureDetector(context, gestureListener);
        this.context=context;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if(gestureDetector.onTouchEvent(e)){
            View clickchield=rv.findChildViewUnder(e.getX(), e.getY());
            if(clickchield!=null && !clickchield.dispatchTouchEvent(e)){
                int clickedPosition = rv.getChildPosition(clickchield);
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    onItemClick(rv, clickchield, clickedPosition);
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    public abstract void onItemClick(RecyclerView recyclerView, View itemView, int position);
}
