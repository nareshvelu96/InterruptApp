package com.example.root.interrupt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.facebook.*;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.SignInButton;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
//import com.nispok.snackbar.Snackbar;
//import com.nispok.snackbar.SnackbarManager;
//import com.nispok.snackbar.listeners.ActionClickListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;



public class Launcher extends Activity implements   ConnectionCallbacks, OnConnectionFailedListener,View.OnClickListener{
    private static final int STATE_DEFAULT = 0;
    private static final int STATE_SIGN_IN = 1;
    private static final int STATE_IN_PROGRESS = 2;
    private static final int RC_SIGN_IN = 0;
    private static final String SAVED_PROGRESS = "sign_in_progress";
    private static final String WEB_CLIENT_ID = "WEB_CLIENT_ID";
    private static final String SERVER_BASE_URL = "SERVER_BASE_URL";
    private static final String TAG = "android-plus-quickstart";
    public static Activity act;
    static String url="http://webtest.netai.net/i15/register.php";
    public Button login,signup;
     List<String> permissions;
    Boolean fb=false;
    ViewPager mPager;
    GoogleApiClient googleApiClient;
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;
    ProfileTracker profileTracker;
    Profile fbprofile;
    ProgressDialog progressDialog;
    String name,email,gender,dob,profile_picture,userid,fname,lname,json,provider;
    private int mSignInProgress;
    private PendingIntent mSignInIntent;
   private int mSignInError;
    private boolean mRequestServerAuthCode = false;
    private SignInButton mSignInButton;
    private Button authButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_launcher);
        act = this;

        mPager = (ViewPager) findViewById(R.id.pager);

        mSignInButton=(SignInButton)findViewById(R.id.sign_in_button);
        LinearLayout l = (LinearLayout)findViewById(R.id.layout_launcher);
        l.setBackgroundResource(R.drawable.launcher_bg);

        final float density = getResources().getDisplayMetrics().density;

        authButton = (Button) findViewById(R.id.authButton);
        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);
        permissions = new ArrayList<String>();
       permissions.add("email");
        permissions.add("public_profile");
        permissions.add("user_birthday");
        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackManager = CallbackManager.Factory.create();
                fb=true;
                LoginManager.getInstance().logInWithReadPermissions(Launcher.this, permissions);
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                accessToken = loginResult.getAccessToken();
                                Log.e("accessToken",accessToken.getToken());
                                GraphRequest.newMeRequest(accessToken,  new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                                        try {
                                            Log.e("json",jsonObject.toString());
                                            name = jsonObject.getString("name");
                                           // fname = jsonObject.getString("first_name");
                                            //lname = jsonObject.getString("last_name");
                                            dob = " ";
                                            provider = "facebook";
                                            //gender = jsonObject.getString("gender");
                                            //userid = jsonObject.getString("id");
                                          //  profile_picture = "https://graph.facebook.com/" + userid + "/picture?width=200&height=200";
                                            email = jsonObject.optString("email");
                                            Log.e("emai", email + "");
                                            Log.e("", dob + "");
                                            Log.e("", name + "");
                                            Log.e("userid", userid + "");
                                           new UserRegister().execute();
                                            Toast.makeText(getApplicationContext(),name+email,Toast.LENGTH_LONG).show();

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).executeAsync();
                            }
                            @Override
                            public void onCancel() {
                            }
                            @Override
                            public void onError(FacebookException e) {
                                e.printStackTrace();
                            }
                        });
            }
        });
      
        profileTracker=new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile profile, Profile profile1) {
                fbprofile=profile1;
            }
        };
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(Launcher.this,Login.class);
                startActivity(login);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(Launcher.this,Register.class);
                startActivity(signup);
            }
        });
        mSignInButton.setOnClickListener(this);
        // CheckBox listeners
        if (savedInstanceState != null) {
            mSignInProgress = savedInstanceState
                    .getInt(SAVED_PROGRESS, STATE_DEFAULT);
        }
       googleApiClient = buildGoogleApiClient();

    }
    private GoogleApiClient buildGoogleApiClient() {
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN);
        if (mRequestServerAuthCode) {
            checkServerAuthConfiguration();
        }
        return builder.build();
    }

    @Override
    protected void onStart() {
        super.onStart();
    //   MaterialDesignApplication.mGoogleApiClient.connect();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }
 @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_PROGRESS, mSignInProgress);
    }
  @Override
    public void onClick(View v) {
        if (!googleApiClient.isConnecting()) {
            // We only process button clicks when GoogleApiClient is not transitioning
            // between connected and not connected.
            switch (v.getId()) {
                case R.id.sign_in_button:
                    mSignInProgress = STATE_SIGN_IN;
                    googleApiClient.connect();
                    break;
            }
        }
    }
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "onConnected");
       mSignInButton.setEnabled(false);
       // Log.e("Constant",ConstantValues.google+"goog");
            Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
            mSignInButton.setEnabled(false);
            if (Plus.PeopleApi.getCurrentPerson(googleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(googleApiClient);
                name = currentPerson.getDisplayName();
                fname = currentPerson.getDisplayName();
                lname = currentPerson.getDisplayName();
                userid = currentPerson.getId();
                provider = "google_oauth2";
                int genderInt = currentPerson.getGender();
                if (genderInt == 0) {
                    gender = "male";
                } else {
                    gender = "female";
                }
                dob = currentPerson.getBirthday() + " ";

               // profile_picture = currentPerson.getImage().get();
                //profile_picture = profile_picture.substring(0, profile_picture.length() - 2);
               // profile_picture = profile_picture + "200";

             //   Log.e("profile_picture", profile_picture);
                //ConstantValues.google="signin";
                email = Plus.AccountApi.getAccountName(googleApiClient);

                Toast.makeText(getApplicationContext(),name + email,Toast.LENGTH_LONG).show();
                Log.e("email", "email" + userid);
                new UserRegister().execute();
        }
        mSignInProgress = STATE_DEFAULT;
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
        if (result.getErrorCode() == ConnectionResult.API_UNAVAILABLE) {
            Log.w(TAG, "API Unavailable.");
        } else if (mSignInProgress != STATE_IN_PROGRESS) {
            mSignInIntent = result.getResolution();
            mSignInError = result.getErrorCode();

            if (mSignInProgress == STATE_SIGN_IN) {
                resolveSignInError();
            }
        }

        onSignedOut();
    }

    private void resolveSignInError() {
        if (mSignInIntent != null) {
            try {
                mSignInProgress = STATE_IN_PROGRESS;
                startIntentSenderForResult(mSignInIntent.getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (SendIntentException e) {
                Log.i(TAG, "Sign in intent could not be sent: "
                        + e.getLocalizedMessage());
                // The intent was canceled before it was sent.  Attempt to connect to
                // get an updated ConnectionResult.
                mSignInProgress = STATE_SIGN_IN;
               googleApiClient.connect();
            }
        } else {
            createErrorDialog().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
            if(requestCode== RC_SIGN_IN) {
                if (resultCode == RESULT_OK) {
                    mSignInProgress = STATE_SIGN_IN;
                } else {
                    mSignInProgress = STATE_DEFAULT;
                }

                if (!googleApiClient.isConnecting()) {
                    googleApiClient.connect();
                }
            }
             else  callbackManager.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);
    }
    private void onSignedOut() {
        mSignInButton.setEnabled(true);
    }

    @Override
    public void onConnectionSuspended(int cause) {
        googleApiClient.connect();
    }

    private Dialog createErrorDialog() {
        if (GooglePlayServicesUtil.isUserRecoverableError(mSignInError)) {
            return GooglePlayServicesUtil.getErrorDialog(
                    mSignInError,
                    this,
                    RC_SIGN_IN,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            Log.e(TAG, "Google Play services resolution cancelled");
                            mSignInProgress = STATE_DEFAULT;

                        }
                    });
        } else {
            return new AlertDialog.Builder(this)
                    .setMessage("play services error")
                    .setPositiveButton("Close",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.e(TAG, "Google Play services error could not be "
                                            + "resolved: " + mSignInError);
                                    mSignInProgress = STATE_DEFAULT;

                                }
                            }).create();
        }
    }


    private void checkServerAuthConfiguration() {
        if ("WEB_CLIENT_ID".equals(WEB_CLIENT_ID) ||
                "SERVER_BASE_URL".equals(SERVER_BASE_URL)) {
            Log.w(TAG, "WEB_CLIENT_ID or SERVER_BASE_URL configured incorrectly.");
            Dialog dialog = new AlertDialog.Builder(this)
                    .setMessage("Configuration Error")
                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();

            dialog.show();
        }
    }

    private void savePreferences(String key, String value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void showSnack(String message){
       /* SnackbarManager.show(
                Snackbar.with(getApplicationContext()) // context
                        .text(message) // text to display
                        .actionLabel("Dismiss") // action button label
                        .actionListener(new ActionClickListener() {
                            @Override
                            public void onActionClicked(Snackbar snackbar) {
                                Log.d("TAG", "Undoing something");
                            }
                        }) // action button's ActionClickListener
                , this);*/

    }

    private class UserRegister extends AsyncTask<String,String,Boolean>
    {
        EditText temp;
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(Launcher.this);
            nDialog.setTitle("Verifying Phone Number");
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
            params2.add(new BasicNameValuePair("tag","insert"));
            params2.add(new BasicNameValuePair("name",name));
            params2.add(new BasicNameValuePair("password",""));
            params2.add(new BasicNameValuePair("email",email));
            params2.add(new BasicNameValuePair("phone_no",""));
            params2.add(new BasicNameValuePair("college",""));
            params2.add(new BasicNameValuePair("dept",""));
            params2.add(new BasicNameValuePair("year",""));
            jsonobject = jParser2.makeHttpRequest(url, "GET", params2);

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
                editor.putString("name", name);
                editor.putString("email", email);

                editor.putString("auth_token", Values.auth_token);
                editor.putString("id", Values.id);
                Values.is_loggedin=true;
                editor.commit();
                Values.email=email;
                Values.name=name;

                Intent i = new Intent(Launcher.this, MainMenu.class);


                startActivity(i);


            }
            else{
                nDialog.dismiss();
              //  temp.setText("");
                showSnack("Registration Failed");

            }
        }
    }
}