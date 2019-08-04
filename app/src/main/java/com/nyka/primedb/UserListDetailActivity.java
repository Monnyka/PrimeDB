package com.nyka.primedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class UserListDetailActivity extends BaseActivity {

    String list_ID;
    TextView lbListName;
    TextView lbListDesc;
    TextView lbListTotal;
    Toolbar toolbar;
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_detail);
        NoStatusBar();
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        lbListName=findViewById(R.id.lbListName);
        lbListDesc=findViewById(R.id.lbListDesc);
        lbListTotal=findViewById(R.id.lbListTotal);
        mQueue= Volley.newRequestQueue(this);



        Intent intent =getIntent();
        list_ID=intent.getStringExtra("EXTRA_LIST_ID");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RequestListDetail();

    }

    private void RequestListDetail() {
        String url = "https://api.themoviedb.org/3/list/" + list_ID + "?api_key=1469231605651a4f67245e5257160b5f&language=en-US";
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String listName=response.optString("name");
                String listDesc=response.optString("description");
                String listTotal="Total: "+response.optString("item_count")+"Movies";

                lbListName.setText(listName);
                lbListDesc.setText(listDesc);
                lbListTotal.setText(listTotal);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);
    }
}
