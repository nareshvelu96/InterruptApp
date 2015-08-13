package com.example.root.interrupt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by root on 4/8/15.
 */
public class InEvent extends Activity implements View.OnClickListener {
    String id;
    SharedPreferences pref;
    SharedPreferences.Editor  editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.event);
        pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
         editor= pref.edit();
        ImageView event1=(ImageView)findViewById(R.id.event1);
        ImageView event2=(ImageView)findViewById(R.id.event2);
        ImageView event3=(ImageView)findViewById(R.id.event3);
        ImageView event4=(ImageView)findViewById(R.id.event4);
        ImageView event5=(ImageView)findViewById(R.id.event5);
        ImageView event6=(ImageView)findViewById(R.id.event6);

        event1.setOnClickListener(this);
        event2.setOnClickListener(this);
        event3.setOnClickListener(this);
        event4.setOnClickListener(this);
        event5.setOnClickListener(this);
        event6.setOnClickListener(this);



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(InEvent.this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
               // Toast.makeText(InEvent.this, "tgis", Toast.LENGTH_LONG).show();
                if(result.getContents().equals("arise shenron")){
                    switch(id){
                        case "1":
                            if(Values.ismyb){

                            }else {
                                Values.ismyb = true;
                                editor.putBoolean("ismyb", true);
                                editor.commit();
                            }
                            break;
                        case"2":
                            if(Values.ismyb){

                            }else {
                                Values.istabloid = true;
                                editor.putBoolean("istabloid", true);
                                editor.commit();
                            }
                            break;
                        case"3" :
                            if(Values.ismyb){

                            }else {
                                Values.issynt = true;
                                editor.putBoolean("issynt", true);
                                editor.commit();
                            }
                            break;
                        case"4":
                            if(Values.ismyb){

                            }else {
                                Values.istrifacta = true;
                                editor.putBoolean("istrifacta", true);
                                editor.commit();
                            }
                            break;
                        case"5":
                            if(Values.ismyb){

                            }else {
                                Values.isbombquad = true;
                                editor.putBoolean("isbombquad", true);
                                editor.commit();
                            }
                            break;

                        case"6":
                            if(Values.ismyb){

                            }else {
                                Values.isdumbc = true;
                                editor.putBoolean("isdumbc", true);
                                editor.commit();
                            }
                            break;




                    }

                    Intent i = new Intent(InEvent.this, EVENT_NAME.class);
                    startActivity(i);

                }
            }
        } else {
            Log.d("MainActivity", "Weird");
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
            Toast.makeText(InEvent.this, "Scanned: " + result.getContents() + "Fragment", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onClick(View v) {
        id=getResources().getResourceEntryName(v.getId());
        id=""+id.charAt(id.length()-1);

        new IntentIntegrator(InEvent.this).initiateScan();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
