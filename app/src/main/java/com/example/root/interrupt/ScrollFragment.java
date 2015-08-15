package com.example.root.interrupt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;


/**
 * Created by florentchampigny on 24/04/15.
 */
public class ScrollFragment extends Fragment {


    private ObservableScrollView mScrollView;

    public static ScrollFragment newInstance() {
        return new ScrollFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_scroll, container, false);
        TextView eventdesc= (TextView)rootView.findViewById(R.id.event_description);
        TextView eventrounds= (TextView)rootView.findViewById(R.id.event_rounds);
        Button register =(Button)rootView.findViewById(R.id.event_register);
        eventdesc.setText(EventDescription.event_array[1]);
        eventrounds.setText(EventDescription.event_array[2]);
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
        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);

        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
    }
}
