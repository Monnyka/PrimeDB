package com.nyka.primedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class activity_moviedetail extends BaseActivity {

    ImageView btnBack;
    TextView lbMovieTitle;
    RequestQueue mQueue;
    String MovieID="";
    ImageView ivPoster;
    TextView lbSynopsisDetail;
    TextView lbRated;
    TextView lbBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetail);
        NoStatusBar();
        mQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        MovieID=intent.getStringExtra("movieID");
        btnBack=findViewById(R.id.btnBackDetail);
        lbMovieTitle=findViewById(R.id.lbMovieTitle);
        ivPoster=findViewById(R.id.ivPoster);
        lbRated=findViewById(R.id.lbRated);
        lbBudget=findViewById(R.id.lbBudget);
        lbSynopsisDetail=findViewById(R.id.lbSynopsisDetail);


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
    public void getMovieDetail(){
        String movieUrl="https://api.themoviedb.org/3/movie/"+MovieID+"?api_key="+apiKey+"&language=en-US";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, movieUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{

                        String MovieTitle = response.getString("original_title");
                        String poster="https://image.tmdb.org/t/p/original"+response.getString("poster_path");
                        String Synopsis=response.getString("overview");
                        String MovieRate=response.getString("vote_average");
                        String MovieBudget=response.getString("budget");
                        Long mBudget =Long.parseLong(MovieBudget)/1000000;
                        if(mBudget>0) {
                            String mBudgetConvert =(mBudget)+"M";
                            lbBudget.setText(mBudgetConvert);
                        }else{

                            String mBudgetConvert ="   "+(mBudget);
                            lbBudget.setText(mBudgetConvert);
                        }

                        lbMovieTitle.setText(MovieTitle);
                        lbSynopsisDetail.setText(Synopsis);
                        lbRated.setText(MovieRate);

                        Glide.with(activity_moviedetail.this).load(poster).into(ivPoster);

                }catch (JSONException e){
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
