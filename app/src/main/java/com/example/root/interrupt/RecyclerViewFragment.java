package com.example.root.interrupt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewFragment extends Fragment {

    private ObservableScrollView mScrollView;

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_scroll2, container, false);

        TextView eventrules= (TextView)rootView.findViewById(R.id.event_rules);
        TextView eventsample= (TextView)rootView.findViewById(R.id.event_sample);
        Button register=(Button)rootView.findViewById(R.id.event_register2);
        eventsample.setText(EventDescription.event_array[4]);
        eventrules.setText(EventDescription.event_array[3]);
        if(EventDescription.paper){
            TextView temp= (TextView) rootView.findViewById(R.id.sample);
            temp.setText("Topics");
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Values.is_loggedin){
                    Intent i = new Intent(getActivity().getApplicationContext(), Launcher.class);
                    startActivity(i);
                }else if(!Values.prof_complete){
                    Intent i = new Intent(getActivity().getApplicationContext(), Register.class);
                    i.putExtra("tag","update");
                    startActivity(i);
                }else{

                    MainMenu.isregistertrigger=true;
                    //register evnet fragment
                    NavUtils.navigateUpFromSameTask(getActivity());

                }
            }
        });

        return rootView;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView2);

        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);

    }
}
