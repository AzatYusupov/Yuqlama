package com.example.avto.yuqlama;

import android.app.Application;

import com.example.avto.yuqlama.models.Migration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by AzatYusupov on 16.01.2018.
 */

public class AttendanceApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("attendance.realm")
                .schemaVersion(1)
                .migration(new Migration())
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
