package com.example.avto.yuqlama.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avto.yuqlama.Db.SqliteHelper;
import com.example.avto.yuqlama.Model.Data;
import com.example.avto.yuqlama.Model.Group;
import com.example.avto.yuqlama.Model.Student;
import com.example.avto.yuqlama.R;
import com.example.avto.yuqlama.adapters.GroupListAdapter;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class GroupListActivity extends AppCompatActivity {

    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        realm = Realm.getDefaultInstance();
        RealmResults<Group> groups = new RealmResults<Group>();
        groups.add(new Group(1, "911-16", 10));
        groups.add(new Group(2, "912-16", 10));
        groups.add(new Group(3, "913-16", 10));
        groups.add(new Group(4, "914-16", 10));
        groups.add(new Group(5, "921-16", 10));
        groups.add(new Group(6, "922-16", 10));
        groups.add(new Group(7, "931-16", 10))
        groups.add(new Group(8, "932-16", 10));
        groups.add(new Group(9, "941-16", 10));
        groups.add(new Group(10, "942-16", 10));

        GroupListAdapter groupListAdapter = 
    }
}
