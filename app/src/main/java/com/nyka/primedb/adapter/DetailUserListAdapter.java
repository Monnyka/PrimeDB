package com.nyka.primedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nyka.primedb.R;
import com.nyka.primedb.model.DetailUserListModel;

import java.util.ArrayList;

public class DetailUserListAdapter extends RecyclerView.Adapter<DetailUserListAdapter.DetailUserViewHolder> {
    private Context mContext;
    private ArrayList<DetailUserListModel> mDetailUserList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnListItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

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

     class DetailUserViewHolder extends RecyclerView.ViewHolder{
             TextView listName;
         DetailUserViewHolder(@NonNull View itemView) {
            super(itemView);
            listName=itemView.findViewById(R.id.lbListNameDetail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListener.OnListItemClick(position);
                        }
                    }
                }
            });




        }
         public void clear() {
             int size = mDetailUserList.size();
             mDetailUserList.clear();
             notifyItemRangeRemoved(0, size);
         }
    }
}
