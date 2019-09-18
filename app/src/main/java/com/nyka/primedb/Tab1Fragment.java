package com.nyka.primedb;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.nyka.primedb.adapter.MovieAdapter;
import com.nyka.primedb.model.MovieItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tab1Fragment extends Fragment {
    RequestQueue mQueue;
    ArrayList<MovieItem> mMovieList;
    MovieAdapter mMovieAdapter;
    RecyclerView rcCast;
    ImageView ivDirector;
    TextView lbDirectorName;
    String MovieID="384018";
    String apiKey="1469231605651a4f67245e5257160b5f";
    String movieCreditUrl = "https://api.themoviedb.org/3/movie/" + MovieID + "/credits?api_key=" + apiKey;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.tabcast_fragment,container,false);


            rcCast= v.findViewById(R.id.rc_cast);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rcCast.setLayoutManager(layoutManager);
            mMovieList= new ArrayList<>();
            CastRequest();
            rcCast.setHasFixedSize(true);
            rcCast.setLayoutManager(new LinearLayoutManager(getContext()));

            return v;
    }

    public void CastRequest(){

        JsonObjectRequest creditRequest = new JsonObjectRequest(Request.Method.GET, movieCreditUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("cast");
                    for (int i = 0; i < 3; i++) {
                        JSONObject cast = jsonArray.getJSONObject(i);
                        String CastName = cast.getString("name");
                        String CharracterName = "as " + cast.getString("character");
                        String aa = cast.getString("profile_path");
                        String imageUrl = "https://image.tmdb.org/t/p/w185" + aa;
                        mMovieList.add(new MovieItem(imageUrl, CastName, CharracterName));
                    }
                    mMovieAdapter = new MovieAdapter(this, mMovieList);
                    rcCast.setAdapter(mMovieAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Get crew director
                try {
                    JSONArray jsonArray = response.getJSONArray("crew");

                    for (int i = 0; i <= jsonArray.length(); i++) {
                        JSONObject crew = jsonArray.getJSONObject(i);

                        String getDirector = crew.optString("job");
                        String Director_Image_Profile = "https://image.tmdb.org/t/p/w500" + crew.optString("profile_path");

                        if (getDirector.equals("Director")) {
                            String DirectorName = crew.optString("name");
                            Glide.with(Tab1Fragment.this).load(Director_Image_Profile).into(ivDirector);

                            if (lbDirectorName.getText() == "") {
                                lbDirectorName.append(DirectorName);
                            } else if (lbDirectorName.getText() != null)
                                lbDirectorName.append(" & " + DirectorName);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mQueue.add(creditRequest);
    }
}