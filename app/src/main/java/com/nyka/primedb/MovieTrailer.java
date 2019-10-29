package com.nyka.primedb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class MovieTrailer extends BaseActivity {

    String videoId;
    String movieTitle="";
    TextView lbTrailerTitle;
    YouTubePlayerView youTubePlayerView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);
        NoStatusBar();

        lbTrailerTitle=findViewById(R.id.lbMovieTitle);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent=getIntent();
        movieTitle=intent.getStringExtra("movieTitle")+ " - Official Trailer";
        videoId=intent.getStringExtra("trailerID");

        lbTrailerTitle.setText(movieTitle);

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        if(videoId.equals("")){
            youTubePlayerView.setVisibility(View.GONE);
        }

        PlayTrailer();
    }

    public void PlayTrailer(){
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(videoId, 1f);
            }
        });
    }

}
