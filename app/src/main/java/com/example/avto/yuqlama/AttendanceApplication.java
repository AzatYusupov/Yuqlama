package com.example.avto.yuqlama;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by AzatYusupov on 16.01.2018.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
