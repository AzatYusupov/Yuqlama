package com.example.avto.yuqlama.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.avto.yuqlama.Model.Group;
import com.example.avto.yuqlama.R;

import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

/**
 * Created by AzatYusupov on 16.01.2018.
 */

public class GroupListAdapter extends RealmRecyclerViewAdapter<Group, GroupListAdapter.GroupViewHolder>{

    Context context;

    public GroupListAdapter(Context context, RealmResults<Group> groups) {
        super(groups, true);
        this.context = context;
    }

    @Override
    public GroupListAdapter.GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_group, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupListAdapter.GroupViewHolder holder, int position) {
        holder.groupName.setText(getData().get(position).getName());
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {

        TextView groupName;
        ImageView groupImage;
        public GroupViewHolder(View itemView) {
            super(itemView);
            groupName = (TextView) itemView.findViewById(R.id.group_name);
            groupImage = (ImageView) itemView.findViewById(R.id.group_image);
        }
    }
}
