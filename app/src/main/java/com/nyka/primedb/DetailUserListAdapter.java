package com.nyka.primedb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;

import java.util.ArrayList;

public class DetailUserListAdapter extends RecyclerView.Adapter<DetailUserListAdapter.DetailUserViewHolder> {
    private Context mContext;
    private ArrayList<DetailUserListModel> mDetailUserList;

    public DetailUserListAdapter(Context mContext, ArrayList<DetailUserListModel> mDetailUserList) {
        this.mContext = mContext;
        this.mDetailUserList = mDetailUserList;
    }

    @NonNull
    @Override
    public DetailUserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.userlistdetail_item,viewGroup,false);
        return new DetailUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailUserViewHolder detailUserViewHolder, int i) {
        DetailUserListModel currentItem = mDetailUserList.get(i);
        String Name=currentItem.getListName();
        detailUserViewHolder.listName.setText(Name);

    }

    @Override
    public int getItemCount() {
        return mDetailUserList.size();
    }

    public class DetailUserViewHolder extends RecyclerView.ViewHolder{
            public TextView listName;
        public DetailUserViewHolder(@NonNull View itemView) {
            super(itemView);
            listName=itemView.findViewById(R.id.lbListNameDetail);
        }
    }
}
