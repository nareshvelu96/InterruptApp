package com.example.root.interrupt;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by root on 22/7/15.
 */
public class Application extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "CeB6hB4zqKTQ657m6NZDSiKopmAcj6qtWCtproxB", "TrjSS86Baa6VSmLhhWiF9gT6WGqeyufp0Hnzp9gT");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
