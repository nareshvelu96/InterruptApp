package com.example.root.interrupt;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;


public class EVENT_NAME extends Activity {
 int counter=0,checker;
    int total;
    boolean bol=true;
    Button b,a,c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String[] str = new String[20];
        final String[] optiona= new String[20];
        final String[] optionb= new String[20];
        final String[] optionc= new String[20];
        final String[] optiond= new String[20];
        final String lock="Your Option is ";
        final int[] ans= new int[20];


        // these questions are stored for c you have to remove this since some operators are considered as illegal in xml i have stored these haere
        //such questions can be stored in an string array in xml itself dont confuse with this part
        InputStream is = getResources().openRawResource(R.raw.json);
        Writer writer = new StringWriter();
       // char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n,k=93;
            while ((n = reader.read()) != -1) {
                n=n^k;

                writer.write(n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String jsonString = writer.toString();
        try {
            JSONObject jobj=new JSONObject(jsonString);

            JSONObject temp = jobj.getJSONObject("question");
            total=temp.length();

            for(int i=1;i<=temp.length();i++){
                str[i-1]=temp.getString("" + i);
            }
            temp = jobj.getJSONObject("optiona");
            for(int i=1;i<=temp.length();i++){
                optiona[i-1]=temp.getString("" + i);
            }
            temp = jobj.getJSONObject("optionb");
            for(int i=1;i<=temp.length();i++){
                optionb[i-1]=temp.getString("" + i);
            }
            temp = jobj.getJSONObject("optionc");
            for(int i=1;i<=temp.length();i++){
                optionc[i-1]=temp.getString("" + i);
            }
             temp = jobj.getJSONObject("optiond");
            for(int i=1;i<=temp.length();i++){
                optiond[i-1]=temp.getString("" + i);
            }
            temp = jobj.getJSONObject("answers");
            for(int i=1;i<=temp.length();i++){
                ans[i-1]=temp.getInt("" + i);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //end of questions

        //DECLARATION OF STRING VARIABLES FOR OPTIONS AND GETTING THE VALUES FROM A XML FILE

        //END OF RETRIVING VALUES FROM XML FILES
        final int[] getanswers=new int[20];
        setContentView(R.layout.activity_event__name);
        //DECLARATION OF BUTTONS AND TEXTVIEW
        a=(Button)findViewById(R.id.next);
        b=(Button)findViewById(R.id.finish);
        c=(Button)findViewById(R.id.prev);
        final TextView tv1=(TextView)findViewById(R.id.t1);
        final Button tv2=(Button)findViewById(R.id.t2);
        final Button tv3=(Button)findViewById(R.id.t3);
        final Button tv4=(Button)findViewById(R.id.t4);
        final Button tv5=(Button)findViewById(R.id.t5);
        //final TextView tv6=(TextView)findViewById(R.id.t6);
//SETTING UP THE INITIAL VALUES FOR TEXTVIEW i.e THE FIRST QUESTION
            tv1.setText(str[0]);
            tv2.setText(optiona[0]);
        tv3.setText(optionb[0]);
        tv4.setText(optionc[0]);
        tv5.setText(optiond[0]);
        //IMPLEMENTING EVENTLISTENER FOR NEXT BUTTON
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                c.setClickable(true);
                if(counter==total)
                {
                    a.setClickable(false);
                }
                else
                {
                   if(getanswers[counter]==0){
                    tv1.setText(str[counter]);
                      
                    tv2.setText(optiona[counter]);
                    tv3.setText(optionb[counter]);
                    tv4.setText(optionc[counter]);
                    tv5.setText(optiond[counter]);
                    tv2.setBackgroundResource(R.drawable.button);
                    tv3.setBackgroundResource(R.drawable.button);
                    tv4.setBackgroundResource(R.drawable.button);
                    tv5.setBackgroundResource(R.drawable.button);
                    //tv6.setText("");
                    }
                    else
                   {
                       if(getanswers[counter]==1)
                       {
                           tv2.setBackgroundResource(R.drawable.buttonclick);
                           tv3.setBackgroundResource(R.drawable.button);
                           tv4.setBackgroundResource(R.drawable.button);
                           tv5.setBackgroundResource(R.drawable.button);
                       }
                       if(getanswers[counter]==2){
                           tv3.setBackgroundResource(R.drawable.buttonclick);
                           tv2.setBackgroundResource(R.drawable.button);
                           tv4.setBackgroundResource(R.drawable.button);
                           tv5.setBackgroundResource(R.drawable.button);}
                       if(getanswers[counter]==3){
                           tv4.setBackgroundResource(R.drawable.buttonclick);
                           tv2.setBackgroundResource(R.drawable.button);
                           tv3.setBackgroundResource(R.drawable.button);
                           tv5.setBackgroundResource(R.drawable.button);}
                       if(getanswers[counter]==4){
                           tv5.setBackgroundResource(R.drawable.buttonclick);
                           tv2.setBackgroundResource(R.drawable.button);
                           tv3.setBackgroundResource(R.drawable.button);
                           tv4.setBackgroundResource(R.drawable.button);
                       }
                   }
                }
            }
        });

        //IMPLEMENTING EVENT LISTENER FOR PREVIOUS BUTTON
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                a.setClickable(true);
                if(counter==0)
                {
                    c.setClickable(false);
                }
                if(counter==-1)
                {
                    counter=0;
                }
              else
                {
                    if(getanswers[counter]==0)
                    {
                    tv1.setText(str[counter]);
                    tv2.setText(optiona[counter]);
                    tv3.setText(optionb[counter]);
                    tv4.setText(optionc[counter]);
                    tv5.setText(optiond[counter]);
                    tv2.setBackgroundResource(R.drawable.button);
                    tv3.setBackgroundResource(R.drawable.button);
                    tv4.setBackgroundResource(R.drawable.button);
                    tv5.setBackgroundResource(R.drawable.button);
                    //tv6.setText("");
                }
                else
                    {
                        tv1.setText(str[counter]);
                        tv2.setText(optiona[counter]);
                        tv3.setText(optionb[counter]);
                        tv4.setText(optionc[counter]);
                        tv5.setText(optiond[counter]);
                       if(getanswers[counter]==1)
                       {
                           tv2.setBackgroundResource(R.drawable.buttonclick);
                           tv3.setBackgroundResource(R.drawable.button);
                           tv4.setBackgroundResource(R.drawable.button);
                           tv5.setBackgroundResource(R.drawable.button);
                       }
                        if(getanswers[counter]==2){
                        tv3.setBackgroundResource(R.drawable.buttonclick);
                            tv2.setBackgroundResource(R.drawable.button);
                            tv4.setBackgroundResource(R.drawable.button);
                            tv5.setBackgroundResource(R.drawable.button);}
                        if(getanswers[counter]==3){
                        tv4.setBackgroundResource(R.drawable.buttonclick);
                            tv2.setBackgroundResource(R.drawable.button);
                            tv3.setBackgroundResource(R.drawable.button);
                            tv5.setBackgroundResource(R.drawable.button);}
                        if(getanswers[counter]==4){
                        tv5.setBackgroundResource(R.drawable.buttonclick);
                            tv2.setBackgroundResource(R.drawable.button);
                            tv3.setBackgroundResource(R.drawable.button);
                            tv4.setBackgroundResource(R.drawable.button);
                            }

                    }

                }

            }
        });
        //IMPLEMENTING EVENT LISTENER FOR FIRST TEXTVIEW
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locker=lock+"a";
                
                tv2.setBackgroundResource(R.drawable.buttonclick);
                tv3.setBackgroundResource(R.drawable.button);
                tv4.setBackgroundResource(R.drawable.button);
                tv5.setBackgroundResource(R.drawable.button);
                getanswers[counter]=1;
                //tv6.setText(locker);
            }
        });
        //IMPLEMENTING EVENT LISTENER FOR SECOND TEXTVIEW
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locker=lock+"b";
                tv3.setBackgroundResource(R.drawable.buttonclick);
                tv2.setBackgroundResource(R.drawable.button);
                tv4.setBackgroundResource(R.drawable.button);
                tv5.setBackgroundResource(R.drawable.button);
                getanswers[counter]=2;
               // tv6.setText(locker);
            }
        });
        //IMPLEMENTING EVENT LISTENER FOR THIRD TEXTVIEW
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locker=lock+"c";
                tv4.setBackgroundResource(R.drawable.buttonclick);
                tv3.setBackgroundResource(R.drawable.button);
                tv2.setBackgroundResource(R.drawable.button);
                tv5.setBackgroundResource(R.drawable.button);
                getanswers[counter]=3;
                //tv6.setText(locker);
            }
        });
        //IMPLEMENTING EVENT LISTENER FOR FOURTH TEXTVIEW
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locker=lock+"d";
                tv5.setBackgroundResource(R.drawable.buttonclick);
                tv3.setBackgroundResource(R.drawable.button);
                tv4.setBackgroundResource(R.drawable.button);
                tv2.setBackgroundResource(R.drawable.button);
                getanswers[counter]=4;
               // tv6.setText(locker);
            }
        });
        ////IMPLEMENTING EVENT LISTENER FOR FINISH BUTTON
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bol) {


                    b.setText("Confirm");


                    bol=false;
                }else{
                    int i;
                    checker = 0;
                    for (i = 0; i < total; i++) {
                        if (ans[i] == getanswers[i]) {
                            checker++;
                        }
                    }
                    Intent intent = new Intent(EVENT_NAME.this, QRinfo.class);
                    intent.putExtra("tag","event");
                    intent.putExtra("score","" + checker);
                    startActivity(intent);
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.event__name, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        b.performClick();
        a.setClickable(false);
        c.setClickable(false);

       // b.performClick();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            //Ask the user if they want to quit
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exit App?")
                    .setMessage("Do you really want to quit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //Stop the activity
                            EVENT_NAME.this.finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
