package com.example.root.interrupt;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

/**
 * Created by root on 30/7/15.
 */
public class AboutInterruptFragment extends Fragment {

    private FadingActionBarHelper mFadingHelper;
    private Bundle mArguments;
    public static final String ARG_IMAGE_RES = "image_source";
    public static final String ARG_ACTION_BG_RES = "image_action_bs_res";
    Button event;
    public AboutInterruptFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // View rootView = inflater.inflate(R.layout.aboutinterrupt, container, false);

        View view = mFadingHelper.createView(inflater);

        if (mArguments != null){
            ImageView img = (ImageView) view.findViewById(R.id.image_header);
            img.setImageResource(mArguments.getInt(ARG_IMAGE_RES));
        }

     event= (Button)view.findViewById(R.id.header2);
    Button letsgo = (Button)view.findViewById(R.id.header2);
        event.setClickable(true);
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new EventFragment();
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.layout_container, fragment).commit();

                    // update selected item and title, then close the drawer
                    MainMenu.mDrawerList.setItemChecked(1, true);
                    MainMenu.mDrawerList.setSelection(1);
                    //setTitle(navMenuTitles[position]);
                    MainMenu.mDrawerLayout.closeDrawer(MainMenu.mDrawerList);
                } else {
                    // error in creating fragment
                    Log.e("MainActivity", "Error in creating fragment");
                }

            }
        });


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mArguments = getArguments();
        int actionBarBg = R.drawable.headerbg;

        mFadingHelper = new FadingActionBarHelper()
                .actionBarBackground(actionBarBg)
                .headerLayout(R.layout.header)
                .contentLayout(R.layout.aboutinterrupt);


        mFadingHelper.initActionBar(activity);

    }
}
