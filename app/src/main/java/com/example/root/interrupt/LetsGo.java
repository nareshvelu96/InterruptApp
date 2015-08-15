package com.example.root.interrupt;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by root on 30/7/15.
 */
public class LetsGo extends Fragment {

    public LetsGo(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lets_go, container, false);
        Button bt=(Button)rootView.findViewById(R.id.btn);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Values.prof_complete)
                new IntentIntegrator(getActivity()).initiateScan();
               else{
                    Intent i = new Intent(getActivity().getApplicationContext(), Register.class);
                    i.putExtra("tag","update");
                    startActivity(i);
                }

            }
        });



        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                //Toast.makeText(getActivity(), "tgis", Toast.LENGTH_LONG).show();
                if(result.getContents().equals("arise shenron")){

                }
            }
        } else {
            Log.d("MainActivity", "Weird");
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
            Toast.makeText(getActivity(), "Scanned: " + result.getContents() + "Fragment", Toast.LENGTH_LONG).show();
        }
    }
}
