package com.example.root.interrupt;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;



/**
 * Created by root on 26/7/15.
 */
public class EventFragment extends Fragment implements View.OnClickListener{

    View rootView;


    public EventFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.eventsfragment, container, false);




        ImageView event1=(ImageView)rootView.findViewById(R.id.event1);
        ImageView event2=(ImageView)rootView.findViewById(R.id.event2);
        ImageView event3=(ImageView)rootView.findViewById(R.id.event3);
        ImageView event4=(ImageView)rootView.findViewById(R.id.event4);
        ImageView event5=(ImageView)rootView.findViewById(R.id.event5);
        ImageView event6=(ImageView)rootView.findViewById(R.id.event6);
        ImageView event7=(ImageView)rootView.findViewById(R.id.event7);
        ImageView event8=(ImageView)rootView.findViewById(R.id.event8);

        event1.setOnClickListener(this);
        event2.setOnClickListener(this);
        event3.setOnClickListener(this);
        event4.setOnClickListener(this);
        event5.setOnClickListener(this);
        event6.setOnClickListener(this);
        event7.setOnClickListener(this);
        event8.setOnClickListener(this);




        return rootView;
    }

    @Override
    public void onClick(View v) {
        String temp=getResources().getResourceName(v.getId());
String fin="" + temp.charAt(temp.length()-1);
        Intent i = new Intent(rootView.getContext(), EventDescription.class);

        i.putExtra("event_id",fin);
        startActivity(i);

    }


}
