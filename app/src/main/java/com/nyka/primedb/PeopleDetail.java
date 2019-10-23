package com.nyka.primedb;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

public class PeopleDetail extends BaseActivity {

    RequestQueue mQueue;
    TextView lbName;
    TextView lbSex;
    TextView lbJob;
    TextView lbDOB;
    TextView lbBiology;
    ImageView ivProfile;

    RelativeLayout rlPeople;

    Toolbar toolbar;

    String castID="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_detail);
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

        mQueue= Volley.newRequestQueue(this );

        Intent intent=getIntent();
        castID=intent.getStringExtra("Extra_castID");

        lbName=findViewById(R.id.lbPeopleName);
        lbSex=findViewById(R.id.lbSex);
        lbJob=findViewById(R.id.lbJob);
        lbDOB=findViewById(R.id.lbDOB);
        lbBiology=findViewById(R.id.lbBiology);
        ivProfile=findViewById(R.id.iV_Profile);

        rlPeople=findViewById(R.id.rlPeople);


        getPeopleDetail();

    }

    private void getPeopleDetail(){
        String url="https://api.themoviedb.org/3/person/73421?api_key=1469231605651a4f67245e5257160b5f&language=en-US";
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String name=response.optString("name");
                String sexCode=response.optString("gender");
                String sex;
                String job=response.optString("known_for_department");
                String dOB=response.optString("birthday");
                String bio=response.optString("biography");
                String profile="https://image.tmdb.org/t/p/w342"+response.optString("profile_path");
                String csDate= convertDate(dOB);

                if(sexCode.equals("2")){
                    sex="Male";
                }else sex="Female";

                lbName.setText(name);
                lbSex.setText(sex);
                lbJob.setText(job);
                lbDOB.setText(csDate);
                lbBiology.setText(bio);
                Glide.with(PeopleDetail.this).load(profile).into(ivProfile);

                rlPeople.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Connection error!, Please check your internet connection.",Toast.LENGTH_LONG).show();
            }
        });

    mQueue.add(request);
    }

}
