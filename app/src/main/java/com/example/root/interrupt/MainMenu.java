package com.example.root.interrupt;




import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;

/**
 * Created by root on 26/7/15.
 */
public class MainMenu extends AppCompatActivity{
static boolean isregistertrigger=false;
    static DrawerLayout mDrawerLayout;
    static ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
    private boolean drawerArrowColor;
   private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        new Values(MainMenu.this);
        if(Values.is_inevent) {
            Intent i = new Intent(MainMenu.this, InEvent.class);
            startActivity(i);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setTitle("");


        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);


            }
        }



      // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navdrawer);


        drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setAnimateEnabled(true);
        mDrawerToggle.syncState();


        String[] values = new String[]{
                "About Interrupt",
                "Events",
                "Transport",
                "Why App?",
                "SVCE Map",
                "Event Registration",
               // "Our Sponsors",
                "Contact us",
                "Let's Go"

        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        TextView listHeader = new TextView(MainMenu.this);
        Button share=new Button(MainMenu.this);

        listHeader.setText("7th September");
        listHeader.setTextSize(20);


        share.setText("Spread the Word");
        share.setBackground(getResources().getDrawable(R.drawable.register));
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "www.interrupt15.in";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Download the Interrupt15 App from");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        mDrawerList.addFooterView(share);

        mDrawerList.addHeaderView(listHeader);
        mDrawerList.setAdapter(adapter);
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(1);
        }

       mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               View view2 = getCurrentFocus();
               if (view2 != null) {
                   InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                   inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
               }
               displayView(position);

           }
       });


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }




    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 1:
                fragment = new AboutInterruptFragment();

                setTitle("Interrupt");

                break;
            case 2:
                fragment = new EventFragment();
                setTitle("Events");
                break;
           case 3:
                fragment = new TransportFragment();
               setTitle("Transport");
                break;
            case 4:
                fragment = new WhyAppFragment();
                setTitle("Why App?");
                break;
            case 8:
                fragment = new LetsGo();
                setTitle("Let's Go");
                break;
            case 5:
                fragment=new SVCEMapFragment();
                setTitle("Map");
                break;
            case 6:
                fragment=new RegisterEvent();
                setTitle("Event Registration");
                break;
            case -1:
                fragment=new SponsorFragment();
                setTitle("Sponsor");
                break;
            case 7:
                fragment=new ContactUsFragment();
                setTitle("Contact us");
                break;



            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.layout_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            //setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                //go to vera act
               // Intent i = new Intent(MainMenu.this, EVENT_NAME.class);
                //startActivity(i);
                if(result.getContents().equals("arise shenron")) {
                    SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor  editor = pref.edit();
                    editor.putBoolean("inevent",true);
                    editor.commit();
                    Values.is_inevent=true;
                    Intent i = new Intent(MainMenu.this, QRinfo.class);
                    i.putExtra("tag","main");
                    startActivity(i);
                }
            }
        } else {
            Log.d("MainActivity", "Weird");
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
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
                            MainMenu.this.finish();
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
    protected void onResume() {
        super.onResume();
        if(isregistertrigger){
            Fragment fragment = null;
            fragment = new RegisterEvent();
            if (fragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.layout_container, fragment).commit();

                // update selected item and title, then close the drawer
                MainMenu.mDrawerList.setItemChecked(5, true);
                MainMenu.mDrawerList.setSelection(5);
                //setTitle(navMenuTitles[position]);
                MainMenu.mDrawerLayout.closeDrawer(MainMenu.mDrawerList);
                isregistertrigger=false;
            } else {
                // error in creating fragment
                Log.e("MainActivity", "Error in creating fragment");
            }


        }
    }
}
