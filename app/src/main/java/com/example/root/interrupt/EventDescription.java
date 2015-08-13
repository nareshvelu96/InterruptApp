package com.example.root.interrupt;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

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

/**
 * Created by root on 26/7/15.
 */
public class EventDescription extends AppCompatActivity  {

    private MaterialViewPager mViewPager;
    private HeaderDesign h;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    static String[] event_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventdescription);
        TextView eventname= (TextView)findViewById(R.id.logo_white);

 /*
*/



        switch(getIntent().getExtras().getString("event_id")){
    case "2":
        event_array=  getResources().getStringArray(R.array.tabloid);
        h=HeaderDesign.fromColorAndDrawable(0xcc000000,getResources().getDrawable(R.drawable.dataland));
        break;
    case "3":
         event_array = getResources().getStringArray(R.array.syntaxis);
        h=HeaderDesign.fromColorAndDrawable(0xcc000000,getResources().getDrawable(R.drawable.syntland));
        break;
    case "4":
        event_array = getResources().getStringArray(R.array.trifact);
        h=HeaderDesign.fromColorAndDrawable(0xcc000000,getResources().getDrawable(R.drawable.trifactaland));
        break;
    case "5":
        event_array = getResources().getStringArray(R.array.bombsquad);
        h=HeaderDesign.fromColorAndDrawable(0xcc000000,getResources().getDrawable(R.drawable.bombland));
        break;
    case "6":
        event_array = getResources().getStringArray(R.array.dumbc);
        h=HeaderDesign.fromColorAndDrawable(0xcc000000,getResources().getDrawable(R.drawable.dumbcland));
        break;
    case "7":
        event_array = getResources().getStringArray(R.array.onlinecoding);
        h=HeaderDesign.fromColorAndDrawable(0xcc000000,getResources().getDrawable(R.drawable.codefraternityls));
        break;
    case "8":
        event_array = getResources().getStringArray(R.array.treasurehunt);
        h=HeaderDesign.fromColorAndDrawable(0xcc000000,getResources().getDrawable(R.drawable.treasureland));
        break;
    case "1":
        event_array = getResources().getStringArray(R.array.myb);
        h=HeaderDesign.fromColorAndDrawable(0xcc000000,getResources().getDrawable(R.drawable.mybland));
        break;
    default:
        event_array = getResources().getStringArray(R.array.myb);
        h=HeaderDesign.fromColorAndDrawable(0xcc000000,getResources().getDrawable(R.drawable.mybland));


}

        eventname.setText(event_array[0]);
      /*

*/
       // if(!BuildConfig.DEBUG)
        //    Fabric.with(this, new Crashlytics());

        setTitle("");

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        mViewPager.setBackgroundResource(R.drawable.bg_card);
        toolbar = mViewPager.getToolbar();
       // mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

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

       mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
       // mDrawer.setDrawerListener(mDrawerToggle);

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 4) {
                    //case 0:
                    //    return RecyclerViewFragment.newInstance();
                    case 1:
                        return RecyclerViewFragment.newInstance();
                    //case 2:
                    //    return WebViewFragment.newInstance();
                    default:
                        return ScrollFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case 0:
                        return "Description";
                    case 1:
                        return "Rules";
                    case 2:
                        return "Professionnel";
                    case 3:
                        return "Divertissement";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
            return h;

                //execute others actions if needed (ex : modify your header logo)


            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setTextColor(0xffffffff);
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        mViewPager.getViewPager().setCurrentItem(1);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
