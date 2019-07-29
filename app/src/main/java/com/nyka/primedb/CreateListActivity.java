package com.nyka.primedb;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateListActivity extends BaseActivity {
    Toolbar toolbar;
    Button btnCreate;
    RequestQueue mQueue;
    EditText ed_Name;
    EditText ed_Desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        NoStatusBar();
        btnCreate=findViewById(R.id.btnCreate);
        mQueue=Volley.newRequestQueue(this);
        ed_Name=findViewById(R.id.ed_name);
        ed_Desc=findViewById(R.id.ed_desc);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewList();
            }
        });

    }

    private void CreateNewList(){

        String list_name = ed_Name.getText().toString();
        String list_desc=ed_Desc.getText().toString();

        String url="https://api.themoviedb.org/3/list?api_key=1469231605651a4f67245e5257160b5f&session_id=4bff39b4c68a29530cbba35c119ae8ac4feb0f09";
        JSONObject json= new JSONObject();
        try {

            json.put("name",list_name);
            json.put("description",list_desc);
            json.put("language","en");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request =new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(),"List has been create",Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    mQueue.add(request);
    }

}
