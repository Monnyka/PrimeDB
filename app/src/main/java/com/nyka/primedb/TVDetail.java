package com.nyka.primedb;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.nyka.primedb.adapter.EpisodeAdapter;
import com.nyka.primedb.model.EpisodeModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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

    ImageView ivTVPoster;

    RecyclerView mRcEpisode;
    EpisodeAdapter mEpisodeAdapter;
    ArrayList<EpisodeModel> mEpisodeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvdetail);
        NoStatusBar();

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

        //Episode List
        mRcEpisode.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mEpisodeList=new ArrayList<>();


        mQueue=Volley.newRequestQueue(this);
        getTVDetail();
        getSeasonEpisode();

    }

    private void getTVDetail(){
        String requestTVDetail="https://api.themoviedb.org/3/tv/1412?api_key=1469231605651a4f67245e5257160b5f&language=en-US";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, requestTVDetail, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String tVTitle = response.optString("name");
                String tVSynopsis = response.optString("overview");
                String tVSeason ="Season "+response.optString("number_of_seasons");
                String tVPoster="https://image.tmdb.org/t/p/w185"+ response.optString("poster_path");
                String tVVote = response.optString("vote_average");
                String tVLike = response.optString("vote_count");
                String tVLAD="LAD: "+response.optString("last_air_date");

                lbTVTitle.setText(tVTitle);
                lbTVSynopsis.setText(tVSynopsis);
                lbTVSeason.setText(tVSeason);
                lbVote.setText(tVVote);
                lbLike.setText(tVLike);
                lbLAD.setText(tVLAD);
                Glide.with(TVDetail.this).load(tVPoster).into(ivTVPoster);


                //Next Air Release
                JSONObject jsonObjectNA = response.optJSONObject("next_episode_to_air");

                String airDate=jsonObjectNA.optString("air_date");
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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    mQueue.add(request);

    }
    private void getSeasonEpisode(){
        String requestEpisode="https://api.themoviedb.org/3/tv/1412/season/8?api_key=1469231605651a4f67245e5257160b5f&language=en-US";
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, requestEpisode, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray jsonArray=response.optJSONArray("episodes");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.optJSONObject(i);

                    String title="Episode "+(i+1)+": "+jsonObject.optString("name");
                    String releaseDate=jsonObject.optString("air_date");
                    String overview=jsonObject.optString("overview");

                    mEpisodeList.add(new EpisodeModel(title,releaseDate,overview));
                }
                    mEpisodeAdapter=new EpisodeAdapter(TVDetail.this,mEpisodeList);
                    mRcEpisode.setAdapter(mEpisodeAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);
    }

}
