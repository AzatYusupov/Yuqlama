package com.example.avto.yuqlama.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.avto.yuqlama.APIClient;
import com.example.avto.yuqlama.models.Group;
import com.example.avto.yuqlama.R;
import com.example.avto.yuqlama.adapters.GroupRealmAdapter;
import com.example.avto.yuqlama.services.APIInterface;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupActivity extends AppCompatActivity {

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);


        Call<List<Group>> groupList = apiInterface.doGetGroupList();
        groupList.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                System.out.println(response.body());
                realm.insertOrUpdate(response.body());
                realm.commitTransaction();
                RealmResults<Group> groups = realm.where(Group.class).findAll();

                GroupRealmAdapter adapter = new GroupRealmAdapter(GroupActivity.this, groups);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.group_recycler);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(GroupActivity.this));
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }
}
