package com.nyka.primedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class activity_moviedetail extends BaseActivity {

    ImageView btnBack;
    TextView lbMovieTitle;
    RequestQueue mQueue;
    String MovieID = "";
    ImageView ivPoster;
    TextView lbSynopsisDetail;
    TextView lbRated;
    TextView lbBudget;
    TextView lbTagLine;
    TextView lbYear;
    TextView lbRunTime;
    TextView lbLanguage;
    TextView lbRatedR;
    TextView lbRevenue;
    TextView lbGenre;
    TextView lbVoteCount;
    TextView lbDistributeDetail;

    RecyclerView mRecyclerView;
    MovieAdapter mMovieAdapter;
    ArrayList<MovieItem> mMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetail);
        NoStatusBar();
        mQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        MovieID = intent.getStringExtra("movieID");
        btnBack = findViewById(R.id.btnBackDetail);
        lbMovieTitle = findViewById(R.id.lbMovieTitle);
        ivPoster = findViewById(R.id.ivPoster);
        lbRated = findViewById(R.id.lbRated);
        lbTagLine = findViewById(R.id.lbTagLine);
        lbBudget = findViewById(R.id.lbBudget);
        lbYear = findViewById(R.id.lbYear);
        lbRunTime = findViewById(R.id.lbRunTime);
        lbLanguage = findViewById(R.id.lbLanguage);
        lbRatedR = findViewById(R.id.lbRatedR);
        lbRevenue = findViewById(R.id.lbRevenue);
        lbGenre = findViewById(R.id.lbGenre);
        lbVoteCount = findViewById(R.id.lbVoteCount);
        lbDistributeDetail = findViewById(R.id.lbDistributeDetail);
        lbSynopsisDetail = findViewById(R.id.lbSynopsisDetail);

        mRecyclerView = findViewById(R.id.recycler_view_cast);
         mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mMovieAdapter);

        mMovieList =new ArrayList<>();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivity();
            }
        });
        getMovieDetail();
    }

    private void OpenActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void getMovieDetail() {
        String movieUrl = "https://api.themoviedb.org/3/movie/" + MovieID + "?api_key=" + apiKey + "&language=en-US";
        String movieCreditUrl="https://api.themoviedb.org/3/movie/"+MovieID+"/credits?api_key=1469231605651a4f67245e5257160b5f";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, movieUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String MovieTitle = response.getString("original_title");
                    String poster = "https://image.tmdb.org/t/p/w185" + response.getString("poster_path");
                    String Synopsis = response.getString("overview");
                    String MovieRate = response.getString("vote_average");
                    String MovieTagLine = response.getString("tagline");
                    String MovieYear = response.getString("release_date");
                    String MovieRunTime = response.getString("runtime");
                    String MovieLanguage = response.getString("original_language");
                    boolean MovieRateR = response.getBoolean("adult");
                    String Revenue = response.getString("revenue");
                    String MovieVoteCount = response.getString("vote_count");

                    if (Revenue != null && Revenue.length() > 6) {
                        String MovieRevenue = Revenue.substring(0, Revenue.length() - 6) + "M";
                        lbRevenue.setText(MovieRevenue);
                    } else lbRevenue.setText(Revenue);


                    String MovieBudget = response.getString("budget");
                    Long mBudget = Long.parseLong(MovieBudget) / 1000000;
                    if (mBudget > 0) {
                        String mBudgetConvert = (mBudget) + "M";
                        lbBudget.setText(mBudgetConvert);
                    } else {

                        String mBudgetConvert = "" + (mBudget);
                        lbBudget.setText(mBudgetConvert);
                    }

                    if (MovieYear.length() > 4) {
                        MovieYear.substring(MovieYear.length() - 4);
                        lbYear.setText(MovieYear);
                    }
                    if (MovieRateR == true) {
                        lbRatedR.setText("Rated-R");
                    } else lbRatedR.setText("Everyone");

                    String minute = " Minute";
                    lbRunTime.setText((MovieRunTime) + minute);
                    lbMovieTitle.setText(MovieTitle);
                    lbSynopsisDetail.setText(Synopsis);
                    lbRated.setText(MovieRate);
                    lbLanguage.setText(MovieLanguage.toUpperCase());
                    lbTagLine.setText("-" + MovieTagLine + "-");
                    lbVoteCount.setText(MovieVoteCount);

                    Glide.with(activity_moviedetail.this).load(poster).into(ivPoster);

                    JSONArray jsonArray = response.optJSONArray("genres");

                    //Iterate the jsonArray and print the info of JSONObjects
                    //Loop find genre
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String MovieGenre = jsonObject.optString("name") + "//";
                        if (i < jsonArray.length() - 1) {
                            String MovieGenres = MovieGenre.substring(0, MovieGenre.length() - 1);
                            lbGenre.append(MovieGenres);
                        } else {
                            String MovieGenres = MovieGenre.substring(0, MovieGenre.length() - 2);
                            lbGenre.append(MovieGenres);
                        }


                    }
                    //Loop find distribute company
                    JSONArray jsonArray1 = response.optJSONArray("production_companies");

                    for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject = jsonArray1.getJSONObject(i);
                        String P_company = "-" + jsonObject.optString("name") + "\n";
                        lbDistributeDetail.append(P_company);
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


        JsonObjectRequest creditRequest = new JsonObjectRequest(Request.Method.GET, movieCreditUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("cast");

                    for(int i=0;i<5;i++){

                        JSONObject cast =jsonArray.getJSONObject(i);

                        String CastName =cast.getString("name");
                        String CharrecterName ="as "+cast.getString("character");
                        String  aa=cast.getString("profile_path");
                        String imageUrl="https://image.tmdb.org/t/p/w185"+aa;
                        mMovieList.add(new MovieItem(imageUrl,CastName,CharrecterName));

                    }
                    mMovieAdapter = new MovieAdapter(activity_moviedetail.this,mMovieList);
                    mRecyclerView.setAdapter(mMovieAdapter);

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
        mQueue.add(creditRequest);
    }


}
