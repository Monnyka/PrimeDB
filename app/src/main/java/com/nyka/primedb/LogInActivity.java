package com.nyka.primedb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LogInActivity extends BaseActivity {

    RequestQueue mQueue;
    Button btnLogin;
    EditText edUname;
    EditText edPassword;
    TextView txtTitle;

    String response_token = "";
    String verified_token = "";
    String session_ID = "";

    public static final String SHARED_PREF = "Share_Pref";
    public static String UserName = "UserName";
    public static String UserPassword = "UserPassword";
    public static String savesessionID = "sessionID";

    String textUname;
    String textPassword;
    String textSessionID;
    boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        btnLogin = findViewById(R.id.btnLogin);
        edUname = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPassword);
        txtTitle = findViewById(R.id.txtTitle);
        mQueue = Volley.newRequestQueue(this);

        LoadData();
        if (isLogin) {
            OpenMainScreen();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckValidationLogin();
            }
        });
        UpdateView();
    }

    public void RequestToken() {
        String url_request_token = requestRoute + "/3/authentication/token/new" + api_key;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url_request_token, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response_token = response.getString("request_token");
                    RequestVerifiedToken();
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
    public void RequestVerifiedToken() {

        String user_name = edUname.getText().toString();
        String user_password = edPassword.getText().toString();
        String url_request_verify_token = requestRoute + "/3/authentication/token/validate_with_login?api_key=" + apiKey + "&username=" + user_name + "&password=" + user_password + "&request_token=" + response_token;

        JsonObjectRequest request_verify_token = new JsonObjectRequest(Request.Method.POST, url_request_verify_token, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    verified_token = response.getString("request_token");
                    RequestSession();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Authentication Fail: Please check your user name and password.", Toast.LENGTH_LONG).show();

            }
        });
        mQueue.add(request_verify_token);
    }
    public void RequestSession() {

        String url_session = requestRoute + "/3/authentication/session/new" + api_key;

        JSONObject json = new JSONObject();
        try {

            json.put("request_token", verified_token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest create_sessionID = new JsonObjectRequest(Request.Method.POST, url_session, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    session_ID = response.getString("session_id");
                    OpenMainScreen();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Request Session Fail : " + error, Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(create_sessionID);
    }
    public void SaveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UserName, edUname.getText().toString());
        editor.putString(UserPassword, edPassword.getText().toString());
        editor.putString(savesessionID, session_ID);
        editor.putBoolean(String.valueOf(isLogin), true);
        editor.apply();
    }
    public void LoadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        textUname = sharedPreferences.getString(UserName, "");
        textPassword = sharedPreferences.getString(UserPassword, "");
        textSessionID = sharedPreferences.getString(savesessionID, "");
        isLogin = sharedPreferences.getBoolean(String.valueOf(isLogin), false);
    }
    public void UpdateView() {
        edUname.setText(textUname);
        edPassword.setText(textPassword);
    }
    public void OpenMainScreen() {
        SaveData();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void CheckValidationLogin() {

        if (edUname.getText().toString().equals("")||edPassword.getText().toString().equals("")) {

            new AlertDialog.Builder(LogInActivity.this)
                    .setTitle("Alert...")
                    .setMessage("Please enter your user name and password.")
                    .setCancelable(true)
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
        RequestToken();

    }
}