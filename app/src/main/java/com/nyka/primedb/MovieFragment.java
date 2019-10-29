package com.nyka.primedb;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nyka.primedb.adapter.TrendingAdapter;
import com.nyka.primedb.adapter.UpcomingAdapter;
import com.nyka.primedb.adapter.YourWatchListAdapter;
import com.nyka.primedb.model.UpcomingModel;
import com.nyka.primedb.model.YourWatchlistModel;
import com.nyka.primedb.model.trending;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieFragment extends Fragment {

    RequestQueue mQueue;

    private TrendingAdapter mTrendingAdapter;
    private ArrayList<trending> mTrendingList;
    private RecyclerView tRecyclerView;

    private UpcomingAdapter mUpcomingAdapter;
    private ArrayList<UpcomingModel> mUpComingList;
    private RecyclerView mUpComingRecycler;

    private YourWatchListAdapter mYourWatchListAdapter;
    private ArrayList<YourWatchlistModel> mWatchList;
    private RecyclerView rv_WatchList;

    private BaseActivity baseActivity = new BaseActivity();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_main,container,false);

        mQueue = Volley.newRequestQueue(v.getContext());
        mTrendingList = new ArrayList<>();
        tRecyclerView=v.findViewById(R.id.rvTrending);

        tRecyclerView.hasFixedSize();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        tRecyclerView.setLayoutManager(linearLayoutManager);

        //Upcoming
        mUpComingList = new ArrayList<>();
        mUpComingRecycler =v.findViewById(R.id.rcUpComing);
        mUpComingRecycler.hasFixedSize();
        mUpComingRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        //Watchlist
        rv_WatchList =v.findViewById(R.id.rv_WatchList);
        rv_WatchList.setHasFixedSize(true);
        rv_WatchList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mWatchList = new ArrayList<>();

        getTrendingMovie();
        getUpcomingMovie();
        getYourWatchList();
        return v;
    }

    private void getTrendingMovie(){
        String Url = "https://api.themoviedb.org/3/movie/now_playing?api_key=1469231605651a4f67245e5257160b5f&language=en-US&page=1";
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i = 0; i <= 10; i++) {
                        JSONObject result = jsonArray.getJSONObject(i);
                        String movieID = result.getString("id");
                        String movieTitle = result.getString("title");
                        String date = result.optString("release_date");
                        String poster = result.getString("poster_path");
                        String Address = "https://image.tmdb.org/t/p/w185" + poster;
                        String genre =baseActivity.convertDate(result.optString("release_date"));
                        mTrendingList.add(new trending(movieTitle, Address, date, movieID, genre));
                    }
                    mTrendingAdapter = new TrendingAdapter(getActivity(), mTrendingList);
                    tRecyclerView.setAdapter(mTrendingAdapter);
                    mTrendingAdapter.setOnClickListener(getActivity());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);
    }
    private void getUpcomingMovie(){

        String Url = "https://api.themoviedb.org/3/discover/movie?api_key=1469231605651a4f67245e5257160b5f&language=en-US&region=US&sort_by=popularity.desc&include_video=false&page=1&primary_release_date.gte=2019-11-01&primary_release_date.lte=2019-12-01";
        Log.d("asdasqweqdascaq",""+Url);

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i = 0; i <= 10; i++) {
                        JSONObject result = jsonArray.getJSONObject(i);
                        String movieID =result.getString("id");
                        String movieTitle = result.getString("title");
                        String overView=result.optString("overview");
                        String releaseDate= baseActivity.convertDate(result.getString("release_date"));
                        String banner = result.getString("backdrop_path");
                        String Address = "https://image.tmdb.org/t/p/w185" + banner;
                        mUpComingList.add(new UpcomingModel(movieTitle, releaseDate, Address, movieID,overView));
                    }
                    mUpcomingAdapter = new UpcomingAdapter(getActivity(), mUpComingList);
                    mUpComingRecycler.setAdapter(mUpcomingAdapter);
                    mUpcomingAdapter.setOnClickListener(getActivity());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);

    }
    private void getYourWatchList(){

        String url = "https://api.themoviedb.org/3/account/{account_id}/watchlist/movies?api_key=1469231605651a4f67245e5257160b5f&language=en-US&session_id=4bff39b4c68a29530cbba35c119ae8ac4feb0f09&sort_by=created_at.desc&page=1";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject result = jsonArray.getJSONObject(i);
                        String movieImage = "https://image.tmdb.org/t/p/w185" + result.optString("poster_path");
                        String movieTitle = result.optString("original_title");
                        String movieYear =baseActivity.convertDate(result.optString("release_date")) ;
                        mWatchList.add(new YourWatchlistModel(movieImage, movieTitle, movieYear));
                    }
                    mYourWatchListAdapter = new YourWatchListAdapter(getContext(), mWatchList);
                    rv_WatchList.setAdapter(mYourWatchListAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);

    }
}
