package com.example.root.interrupt;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 1/8/15.
 */
public class RegisterEvent extends Fragment {

    List<NameValuePair> params2 = new ArrayList<NameValuePair>();
    final String url="http://webtest.netai.net/i15/events.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

final String[] events = {"ds","dc","db","myb","oc","oh","c","b","p"};

        final View rootView = inflater.inflate(R.layout.event_registration, container, false);
        final CheckBox[] e=new CheckBox[9];
        Button register= (Button)rootView.findViewById(R.id.register_e);
        e[0]=(CheckBox)rootView.findViewById(R.id.syncb);
        e[1]=(CheckBox)rootView.findViewById(R.id.dumbcb);
        e[2]=(CheckBox)rootView.findViewById(R.id.tabcb);

        e[3]=(CheckBox)rootView.findViewById(R.id.mybcb);
        e[4]=(CheckBox)rootView.findViewById(R.id.cfcb);
        e[5]=(CheckBox)rootView.findViewById(R.id.thcb);

        e[6]=(CheckBox)rootView.findViewById(R.id.tricb);
        e[7]=(CheckBox)rootView.findViewById(R.id.bombcb);
        e[8]=(CheckBox)rootView.findViewById(R.id.paper);
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
                    params2.add(new BasicNameValuePair("id",Values.id));
                    params2.add(new BasicNameValuePair("auth_token",Values.auth_token));

                    for(int i=0;i<9;i++){
                        if(e[i].isChecked()){
                            params2.add(new BasicNameValuePair(events[i],"1"));
                        }else{
                            params2.add(new BasicNameValuePair(events[i],"0"));
                        }

                    }

                    new RegisterEvents().execute();



                }


            }
        });

        return rootView;
    }

    private class RegisterEvents extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;
        //EditText temp;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setTitle("Registering ...");
            nDialog.setMessage("Please Wait..");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args){


            JSONObject jsonobject;
            final JSONParser jParser2 = new JSONParser();
            jsonobject = jParser2.makeHttpRequest(url, "GET", params2);

            try{

                String message = jsonobject.getString("success").toString();
                if( !(new String(message).equals("0"))){

                    return true;
                }

            }catch (Exception e){
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return false;

        }
        @Override
        protected void onPostExecute(Boolean th){

            if(th == true){
                nDialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(),
                        "Registration Successful", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity(), MainMenu.class);
                startActivity(i);


            }
            else{
                nDialog.dismiss();
                //  temp.setText("");
                Toast.makeText(getActivity().getApplicationContext(),
                        "Registration Failed", Toast.LENGTH_LONG).show();

            }
        }
    }

}
