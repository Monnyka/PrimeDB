package com.nyka.primedb;

import android.os.Bundle;
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
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckForm();
            }
        });

        ed_Name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ed_Name.setHint("");
                }
                else ed_Name.setHint("List name");
            }
        });
        ed_Desc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ed_Desc.setHint("");
                } else {
                    ed_Desc.setHint("Description");
                }
            }
        });

    }

    private void CheckForm(){
        if(ed_Name.getText().toString().equals("")||ed_Desc.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Please check the list title and list description.",Toast.LENGTH_LONG).show();
        }
        else CreateNewList();


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
                Toast.makeText(getApplicationContext(),"Your list has been created successfully.",Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    mQueue.add(request);
    }

}
