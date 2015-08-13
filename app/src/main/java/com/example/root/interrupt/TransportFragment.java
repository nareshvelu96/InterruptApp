package com.example.root.interrupt;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 26/7/15.
 */
public class TransportFragment extends Fragment {
    AutoCompleteTextView searchBar;
    custom_list adapter;
    Button Search;
    final String url="http://webtest.netai.net/i15/transport.php";
    //final String no="route_no",route="bus_route",start="starting_point",time="time",via="via";
    String[] number,start,time,via;
    ListView list;
    public TransportFragment(){}

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.transport_fragment, container, false);

        searchBar= (AutoCompleteTextView)rootView.findViewById(R.id.searchBar);
        Search=(Button)rootView.findViewById(R.id.search);


        String[] stops = getResources().getStringArray(R.array.stops);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,stops);
        searchBar.setAdapter(adapter);

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view2 = getActivity().getCurrentFocus();
                if (view2 != null) {
                    InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(view2.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                ConnectionDetector cd=new ConnectionDetector(getActivity());
                if(cd.isConnectingToInternet())
                new getBus().execute();
               // else
                    //showsnack("No Network Connectivity");

            }
        });


        return rootView;
    }



    private class getBus extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;
        //EditText temp;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setTitle("Finding Buses ...");
            nDialog.setMessage("Please Wait..");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args){


            JSONObject jsonobject;
            final JSONParser jParser2 = new JSONParser();
            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
            if(TextUtils.isEmpty(searchBar.getText().toString())) {

                params2.add(new BasicNameValuePair("tag", "retreive"));
            }else{
                params2.add(new BasicNameValuePair("stop", searchBar.getText().toString()));
                params2.add(new BasicNameValuePair("tag", "search"));
            }
            jsonobject = jParser2.makeHttpRequest(url, "GET", params2);

            try{

                String message = jsonobject.getString("success").toString();
                if( !(new String(message).equals("0"))){
                    JSONArray bus = jsonobject.getJSONArray("info");
                    int size=bus.length()-5;
                    number=new String[size];
                    start=new String[size];
                    time=new String[size];
                    via=new String[size];

                    for(int i=5;i<bus.length();i++){
                            JSONArray temp=bus.getJSONArray(i);
                        number[i-5]=temp.getString(0);
                        start[i-5]="From: " + temp.getString(2);
                        time[i-5]="At: " + temp.getString(3);
                        via[i-5]=temp.getString(4);
                    }
            adapter= new
                    custom_list(getActivity(), number, time,start,via);
                    list=(ListView)rootView.findViewById(R.id.list2);



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

            if(th == false){
                nDialog.dismiss();



            }
            else{
                nDialog.dismiss();
                list.setAdapter(adapter);
                //  temp.setText("");


            }
        }
    }


}
