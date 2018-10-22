package com.flashapps.smartgate;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by dietervaesen on 9/11/16.
 */

public class App extends Application {

    private static App instance;
    private static Context mContext;
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    /*private static final String TWITTER_KEY = "zz8pOkrwAppQmAPhu720XJRhF";
    private static final String TWITTER_SECRET = "Bf3AvptHL30JWENqSljtLZgWfIkhk02jYgPG0GrMRw3Ex6nmfY";*/

    @Override
    public void onCreate() {
        super.onCreate();
        //use vectors as drawable everywhere
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
    }

    @Override
    protected void attachBaseContext(Context base) {
        Locale defaultLocale = Locale.getDefault();
        super.attachBaseContext(base);
        mContext = this;
    }


    public static Context getContext() {
        return mContext;
    }

    public static App getInstance() {
        return instance;
    }
}
