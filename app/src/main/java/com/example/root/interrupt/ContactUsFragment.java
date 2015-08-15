package com.example.root.interrupt;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by root on 9/8/15.
 */
public class ContactUsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contactus, container, false);

        Button call1,call2,call3;
        call1=(Button)rootView.findViewById(R.id.call1);
        call2=(Button)rootView.findViewById(R.id.call2);
        call3=(Button)rootView.findViewById(R.id.call3);

        call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberToDial = "tel:"+"";
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(numberToDial)));

            }
        });

        call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberToDial = "tel:"+"";
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(numberToDial)));


            }
        });

        call3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberToDial = "tel:"+"";
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(numberToDial)));


            }
        });

        return rootView;
    }
}
