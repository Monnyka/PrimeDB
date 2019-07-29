package com.nyka.primedb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserList_ViewHolder> {
    private Context mContext;
    private ArrayList<UserListItem> mUserList;

    UserListAdapter(Context context, ArrayList<UserListItem> userList){
        mContext=context;
        mUserList=userList;
    }

    @NonNull
    @Override
    public UserList_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(mContext).inflate(R.layout.userlist_item,viewGroup,false);
        return new UserList_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserList_ViewHolder userList_viewHolder, int i) {

        UserListItem currentItem = mUserList.get(i);
        if(i==mUserList.size()-1){
            userList_viewHolder.mSL_userList.setVisibility(View.INVISIBLE);
        }

        String listName=currentItem.getListName();
        String listDesc=currentItem.getListDesc();
        String listTotal=currentItem.getListTotal();

        userList_viewHolder.mListName.setText(listName);
        userList_viewHolder.mListDesc.setText(listDesc);
        userList_viewHolder.mListTotal.setText(listTotal);
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class UserList_ViewHolder extends RecyclerView.ViewHolder{
         TextView mListName;
         TextView mListDesc;
         TextView mListTotal;
         View mSL_userList;

        public UserList_ViewHolder(@NonNull View itemView) {
            super(itemView);
            mListName=itemView.findViewById(R.id.lbListTitle);
            mListDesc=itemView.findViewById(R.id.lbListDesc);
            mListTotal=itemView.findViewById(R.id.lbListTotal);
            mSL_userList=itemView.findViewById(R.id.sl_userList);
        }
    }

}
