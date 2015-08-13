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
import android.widget.Button;
import android.widget.EditText;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 23/7/15.
 */
public class Login extends Activity {

    EditText uname,password;
    String user,passwords;
    Button login;
    String tag;
    final String url="http://webtest.netai.net/i15/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        uname=(EditText)findViewById(R.id.uname);
        password=(EditText)findViewById(R.id.lpassword);
        login=(Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user=uname.getText().toString();
                passwords=password.getText().toString();
                if(TextUtils.isEmpty(user)||TextUtils.isEmpty(passwords)){
                    showSnack("Form not complete");
                }else {
                    if(user.length()==10){
                        tag="login_phone";
                    }else{
                        tag="login_email";
                    }
                new LoginUser().execute();

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


    private class LoginUser extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;
        //EditText temp;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(Login.this);
            nDialog.setTitle("Logging in ...");
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
            params2.add(new BasicNameValuePair("name",user));
            params2.add(new BasicNameValuePair("password",passwords));

            jsonobject = jParser2.makeHttpRequest(url, "POST", params2);

            try{

                String message = jsonobject.getString("success").toString();
                if( !(new String(message).equals("0"))){
                    SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor  editor = pref.edit();

                    Values.auth_token=jsonobject.getString("auth_token").toString();
                    Values.id=jsonobject.getString("id").toString();

                    editor.putString("auth_token", Values.auth_token);
                    editor.putString("id", Values.id);
                    editor.commit();

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
            Values.is_loggedin=true;
                SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor  editor = pref.edit();
                editor.putBoolean("islogged",true);
                editor.commit();
                Intent i = new Intent(Login.this, MainMenu.class);
                startActivity(i);


            }
            else{
                nDialog.dismiss();
                //  temp.setText("");
                showSnack("Login Failed");

            }
        }
    }
}
