package com.nyka.primedb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;

import com.nyka.primedb.R;
import com.nyka.primedb.model.EpisodeModel;

import java.util.ArrayList;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder> {

    private Context mContext;
    private ArrayList<EpisodeModel> mEpisodeList;

    public EpisodeAdapter(Context context, ArrayList<EpisodeModel> episodeList){
        mContext=context;
        mEpisodeList=episodeList;

    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.episode_item,viewGroup,false);
        return new EpisodeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder episodeViewHolder, int i) {
        EpisodeModel currentItem = mEpisodeList.get(i);
        String title=currentItem.getEpisodeTitle();
        String releaseDate=currentItem.getEpisodeRelease();
        String overall=currentItem.getEpisodeOverall();
        if(overall.isEmpty()){
            overall="Coming soon";
        }
        episodeViewHolder.episodeTitle.setText(title);
        episodeViewHolder.episodeReleaseDate.setText(releaseDate);
        episodeViewHolder.episodeOverview.setText(overall);

    }

    @Override
    public int getItemCount() {
        return mEpisodeList.size();
    }

    class EpisodeViewHolder extends RecyclerView.ViewHolder{
            TextView episodeTitle;
            TextView episodeReleaseDate;
            TextView episodeOverview;

         EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);
            episodeTitle=itemView.findViewById(R.id.lbEpisodeTitle);
            episodeReleaseDate=itemView.findViewById(R.id.lbEReleaseDate);
            episodeOverview=itemView.findViewById(R.id.lbEOverview);

        }
    }
}
