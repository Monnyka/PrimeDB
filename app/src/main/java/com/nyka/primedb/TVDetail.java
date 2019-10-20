package com.nyka.primedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;
import com.nyka.primedb.adapter.EpisodeAdapter;
import com.nyka.primedb.model.EpisodeModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class TVDetail extends BaseActivity {

    RequestQueue mQueue;
    TextView lbTVTitle;
    TextView lbTVSynopsis;
    TextView lbTVSeason;
    TextView lbVote;
    TextView lbLike;
    TextView lbLAD;
    TextView lbNextAirDate;
    TextView lbNATitle;
    TextView lbESynopsis;
    TextView lbNetwork;
    TextView lbTVGenre;

    Toolbar toolbar;

    ImageView ivTVPoster;

    RecyclerView mRcEpisode;
    EpisodeAdapter mEpisodeAdapter;
    ArrayList<EpisodeModel> mEpisodeList;

    ScrollView mScroll;
    RelativeLayout rlNoInternet;
    SpinKitView imSpin_kit;

    String tvID="";
    String tVSeason="";
    String requestEpisode="";
    String dates="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvdetail);
        NoStatusBar();

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mScroll=findViewById(R.id.mScroll);
        mScroll.setVisibility(View.GONE);
        rlNoInternet=findViewById(R.id.rlNoInternet);
        imSpin_kit=findViewById(R.id.imSpin_kit);

        lbTVTitle=findViewById(R.id.lbTVTitle);
        lbTVSynopsis=findViewById(R.id.lbTVSynopsis);
        lbTVSeason=findViewById(R.id.lbTVSeason);
        lbVote=findViewById(R.id.lbOverAllRate);
        lbLike=findViewById(R.id.lbUserLike);
        lbLAD=findViewById(R.id.lbLAD);
        lbNextAirDate=findViewById(R.id.lbNextAirDate);
        lbNATitle=findViewById(R.id.lbNATitle);
        lbESynopsis=findViewById(R.id.lbESynopsis);
        lbNetwork=findViewById(R.id.lbNetwork);
        lbTVGenre=findViewById(R.id.lbTVGenre);

        mRcEpisode=findViewById(R.id.rcEpisode);

        ivTVPoster=findViewById(R.id.ivTVPoster);

        Intent intent=getIntent();
        tvID=intent.getStringExtra("tvID");

        //Episode List
        mRcEpisode.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mEpisodeList=new ArrayList<>();

        mQueue=Volley.newRequestQueue(this);
        getTVDetail();
    }

    private void getTVDetail(){
        String requestTVDetail="https://api.themoviedb.org/3/tv/"+tvID+"?api_key=1469231605651a4f67245e5257160b5f&language=en-US";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, requestTVDetail, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String tVTitle = response.optString("name");
                String tVSynopsis = response.optString("overview");
                tVSeason =response.optString("number_of_seasons");

                requestEpisode="https://api.themoviedb.org/3/tv/"+tvID+"/season/"+tVSeason+"?api_key=1469231605651a4f67245e5257160b5f&language=en-US";
                getSeasonEpisode();
                String tVPoster="https://image.tmdb.org/t/p/w185"+ response.optString("poster_path");
                String tVVote = response.optString("vote_average");
                String tVLike = response.optString("vote_count");
                String tVLAD=response.optString("last_air_date");
                String lastAirDate=convertDate(tVLAD);

                lbTVTitle.setText(tVTitle);
                lbTVSynopsis.setText(tVSynopsis);
                lbTVSeason.append(tVSeason);
                lbVote.setText(tVVote);
                lbLike.setText(tVLike);
                lbLAD.setText(lastAirDate);
                Glide.with(TVDetail.this).load(tVPoster).into(ivTVPoster);

                //Next Air Release
                JSONObject jsonObjectNA = response.optJSONObject("next_episode_to_air");

                String airDate=convertDate(jsonObjectNA.optString("air_date"));
                String nATitle="''"+jsonObjectNA.optString("name")+"''";
                String eSynopsis=jsonObjectNA.optString("overview");
                if(eSynopsis.isEmpty()){
                    lbESynopsis.setVisibility(View.GONE);
                }

                lbNextAirDate.setText(airDate);
                lbNATitle.setText(nATitle);
                lbESynopsis.setText(eSynopsis);

                JSONArray jsonArrayNW =response.optJSONArray("networks");
                for(int i=0;i<jsonArrayNW.length();i++){
                    JSONObject jsonObjectNW=jsonArrayNW.optJSONObject(i);
                    String networks=jsonObjectNW.optString("name")+" Network";
                    lbNetwork.setText(networks);
                }

                //Genre
                JSONArray jsonArrayGenre=response.optJSONArray("genres");
                for(int i=0;i<jsonArrayGenre.length();i++){
                    JSONObject jsonObjects=jsonArrayGenre.optJSONObject(i);
                    String genres=jsonObjects.optString("name");
                    if(jsonArrayGenre.length()!=i) {
                        lbTVGenre.append(genres);
                        lbTVGenre.append("/");
                    }else lbTVGenre.append(genres);
                }
                imSpin_kit.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                rlNoInternet.setVisibility(View.VISIBLE);
                imSpin_kit.setVisibility(View.GONE);
            }
        });
    mQueue.add(request);

    }
    public void getSeasonEpisode(){

        JsonObjectRequest requests= new JsonObjectRequest(Request.Method.GET, requestEpisode, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jsonArray=response.optJSONArray("episodes");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.optJSONObject(i);


                    String title="Episode "+(i+1)+": "+jsonObject.optString("name");
                    String date=""+jsonObject.optString("air_date");
                    String overview=jsonObject.optString("overview");

                    if(Objects.equals(date, "null")){
                        dates="NA";
                    }else{
                        dates=convertDate(date);}
                    mEpisodeList.add(new EpisodeModel(title, dates, overview));

                }
                    mEpisodeAdapter=new EpisodeAdapter(TVDetail.this,mEpisodeList);
                    mRcEpisode.setAdapter(mEpisodeAdapter);
                 mScroll.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                rlNoInternet.setVisibility(View.VISIBLE);
                imSpin_kit.setVisibility(View.GONE);
            }
        });
        mQueue.add(requests);
    }

}
