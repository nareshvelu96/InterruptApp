package com.example.root.interrupt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 23/7/15.
 */
public class Register extends Activity {
     String tag;
    Button register;
    EditText name,phone,email,password,rpassword;
    Spinner dept,year;
    AutoCompleteTextView college;
    String department="",collegeName="",Sname,Semail,Spassword,Srpassword,Sphone,Syear="";
    final String url="http://webtest.netai.net/i15/app/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        register=(Button)findViewById(R.id.register);
        name=(EditText)findViewById(R.id.name);
        phone=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        rpassword=(EditText)findViewById(R.id.rpassword);
        college=(AutoCompleteTextView)findViewById(R.id.college);
        dept=(Spinner)findViewById(R.id.dept);
        year=(Spinner)findViewById(R.id.year);
       name.setText(Values.name);
        email.setText(Values.email);

        tag=getIntent().getExtras().getString("tag");

        String[] colnames = getResources().getStringArray(R.array.college_names);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Register.this,android.R.layout.simple_list_item_1,colnames);


        college.setAdapter(adapter);




        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.departments, R.layout.custom_spinner);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dept.setAdapter(adapter2);



        dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                department = parent.getItemAtPosition(pos).toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.year, R.layout.custom_spinner);

        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        year.setAdapter(adapter3);
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Syear = parent.getItemAtPosition(pos).toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sname=name.getText().toString();
                Sphone=phone.getText().toString();
                Semail = email.getText().toString();
                Spassword=password.getText().toString();
                Srpassword=rpassword.getText().toString();
                collegeName=college.getText().toString();
                ConnectionDetector cd = new ConnectionDetector(Register.this);
                if(!cd.isConnectingToInternet()){
                    showSnack("No Internet Conectivity");

                }else  if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
                    showSnack("Invalid E-mail");
                else if(phone.getText().toString().length()!=10)
                    showSnack("Phone Number Must Have 10 Digits");
                else if(TextUtils.isEmpty(Sname)||TextUtils.isEmpty(Semail)||TextUtils.isEmpty(Sphone)||TextUtils.isEmpty(Spassword)||TextUtils.isEmpty(Srpassword)||TextUtils.isEmpty(collegeName)){
                    showSnack("Complete The Form");
                }else if(!Spassword.equals(Srpassword)){
                    showSnack("Passwords Don't Match");
                }else if(Spassword.length()>6){
                    showSnack("Password must have minimum 6 characters");
                }
                else{
                    Hasher h =new Hasher();
                    try {
                        Spassword=h.SHA1(h.md5(h.SHA1(Spassword)));
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    new RegisterUser().execute();

                }




            }
        });


    }

    public void showSnack(String message){
        SnackbarManager.show(
                Snackbar.with(getApplicationContext()) // context
                        .text(message) // text to display
                        .actionLabel("Dismiss") // action button label
                        .actionListener(new ActionClickListener() {
                            @Override
                            public void onActionClicked(Snackbar snackbar) {
                                Log.d("TAG", "Undoing something");
                            }
                        }) // action button's ActionClickListener
                , this);

    }




    private class RegisterUser extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;
        //EditText temp;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(Register.this);
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
            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
            params2.add(new BasicNameValuePair("tag",tag));
            if(tag.equals("update"))
               params2.add(new BasicNameValuePair("id",Values.id));
          else
               params2.add(new BasicNameValuePair("id",""));
            params2.add(new BasicNameValuePair("name",Sname));
            params2.add(new BasicNameValuePair("password",Spassword));
            params2.add(new BasicNameValuePair("email",Semail));
            params2.add(new BasicNameValuePair("phone_no",Sphone));
            params2.add(new BasicNameValuePair("college",collegeName));
            params2.add(new BasicNameValuePair("department",department));
            params2.add(new BasicNameValuePair("year",Syear));
            jsonobject = jParser2.makeHttpRequest(url, "POST", params2);

            try{

                String message = jsonobject.getString("success").toString();
                if( !(new String(message).equals("0"))){
                    Values.auth_token=jsonobject.getString("auth_token").toString();
                    Values.id=jsonobject.getString("id").toString();
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
                SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor  editor = pref.edit();
               // if(pref.getString("done", null)==null){
                    editor.putString("name", Sname);
                    editor.putString("email", Semail);
                    editor.putString("phone", Sphone);
                    editor.putString("auth_token", Values.auth_token);
                    editor.putString("id", Values.id);
                    editor.putBoolean("complete", true);
                     editor.commit();
                Values.email=Semail;
                Values.name=Sname;
                Values.phone=Sphone;
                if(tag.equals("insert")) {
                    Intent i = new Intent(Register.this, Launcher.class);
                    startActivity(i);
               }else{
                    Intent i = new Intent(Register.this, MainMenu.class);
                    startActivity(i);
                }

            }
            else{
                nDialog.dismiss();
              //  temp.setText("");
                showSnack("Registration Failed");

            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
