package com.nyka.primedb;

import android.os.Bundle;
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
import com.nyka.primedb.adapter.OnAirTodayAdapter;
import com.nyka.primedb.model.OnAirTVModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TVSeriesFragment extends Fragment {


    RequestQueue mQueue;
    //OnAirToday
    private OnAirTodayAdapter mOnAirTodayAdapter;
    private ArrayList<OnAirTVModel> mOnAirTodayList;

    private RecyclerView mRcOnAirToday;

    private BaseActivity baseActivity = new BaseActivity();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_tvseries_main,container,false);

        mQueue=Volley.newRequestQueue(v.getContext());
        mRcOnAirToday=v.findViewById(R.id.rcOnAirToday);

        //OnAirToday
        mOnAirTodayList = new ArrayList<>();
        RecyclerView mRcOnAirToday = v.findViewById(R.id.rcOnAirToday);
        mRcOnAirToday.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        getTVOnAirToday();

        return v;
    }

    private void getTVOnAirToday(){

        String url = "https://api.themoviedb.org/3/tv/airing_today?api_key=1469231605651a4f67245e5257160b5f&language=en-US&page=1";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject results = jsonArray.getJSONObject(i);
                        String tvID = results.optString("id");
                        String tvTitle = results.optString("name");
                        String tvOnAirDate =baseActivity.convertDate(results.optString("first_air_date"));
                        String tvImage = "https://image.tmdb.org/t/p/w342" + results.optString("poster_path");

                        mOnAirTodayList.add(new OnAirTVModel(tvImage, tvTitle, tvOnAirDate, tvID));
                    }
                    mOnAirTodayAdapter = new OnAirTodayAdapter(getContext(), mOnAirTodayList);
                    mRcOnAirToday.setAdapter(mOnAirTodayAdapter);
//                    mOnAirTodayAdapter.SetOnItemClickListener(MainActivity.this);

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
