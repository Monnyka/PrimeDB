package com.nyka.primedb;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.nyka.primedb.adapter.UserListAdapter;
import com.nyka.primedb.model.UserListItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ProfileActivity extends BaseActivity implements UserListAdapter.onItemClickListener{

    public static String EXTRA_LIST_ID="EXTRA_LIST_ID";
    RequestQueue mQueue;
    ImageView imFavoriteBackdrop;
    ImageView im_watchlist_backdrop;
    ImageView iV_Profile;
    TextView lbUserName;
    TextView btnCreateList;
    TextView lbUserID;
    ImageView btnLogOut;
    Toolbar toolbarNav;
    Dialog dialogLogOut;
    Button btnYes;
    Button btnNo;
    TextView lbMessage;
    boolean isLogin;
    public static final String SHARED_PREF = "Share_Pref";
    public static final String savesessionID="sessionID";
    String sessionID="";
    RecyclerView mRecyclerView;
    UserListAdapter mUserListAdapter;
    ArrayList<UserListItem> mUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        NoStatusBar();
        LoadData();

        mQueue = Volley.newRequestQueue(this);
        imFavoriteBackdrop=findViewById(R.id.imFavoriteBackdrop);
        im_watchlist_backdrop=findViewById(R.id.im_watchlist_backdrop);
        iV_Profile=findViewById(R.id.iV_Profile);
        lbUserName=findViewById(R.id.lbUserName);
        btnCreateList=findViewById(R.id.btnCreateList);
        lbUserID=findViewById(R.id.lbUserID);
        btnLogOut=findViewById(R.id.btnLogOut);
        mRecyclerView=findViewById(R.id.recyclerUserList);
        mRecyclerView.hasFixedSize();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUserList= new ArrayList<>();
        toolbarNav=findViewById(R.id.toolbarNav);
        dialogLogOut= new Dialog(this);
        setSupportActionBar(toolbarNav);
        setTitle("");
        toolbarNav.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnCreateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCreateList();
            }
        });
        RequestProfile();
        RequestProfileDetail();
        RequestUserList();
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallLogOut();
//                ShowDialogLogOut();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mUserListAdapter.clear();
        RequestUserList();
    }
    private void RequestProfile(){
        String url = "https://api.themoviedb.org/3/account?api_key=1469231605651a4f67245e5257160b5f&session_id="+sessionID;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String Username=response.optString("name");
                String UserID="User ID: "+response.optString("id");
                lbUserName.setText(Username);
                lbUserID.setText(UserID);
                try {
                    JSONArray jsonArray= response.getJSONArray("avatar");
                    for(int i=0;i<=jsonArray.length();i++){
                        String imageProfile="https://www.gravatar.com/avatar/"+response.getString("hash");
                        Glide.with(ProfileActivity.this).load(imageProfile).into(iV_Profile);
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
        mQueue.add(request);
    }
    private void RequestProfileDetail(){
        String watchlisturl=requestRoute+"/3/account/{account_id}/watchlist/movies?api_key="+apiKey+"&language=en-US&session_id="+sessionID+"&sort_by=created_at.desc&page=1";
        String url=requestRoute+"/3/account/{account_id}/favorite/movies?api_key="+apiKey+"&session_id="+sessionID+"&language=en-US&sort_by=created_at.desc&page=1";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i = 0; i < 1; i++) {
                        JSONObject backdrop = jsonArray.getJSONObject(i);
                        String imageUrl = "https://image.tmdb.org/t/p/w500" + backdrop.getString("backdrop_path");
                        if(imageUrl.equals("https://image.tmdb.org/t/p/w500")){
                            imFavoriteBackdrop.setImageResource(R.drawable.bg_newsfeede);
                        }else {
                            Glide.with(ProfileActivity.this).load(imageUrl).into(imFavoriteBackdrop);
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

        JsonObjectRequest requestWatchlist = new JsonObjectRequest(Request.Method.GET, watchlisturl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i = 0; i <1; i++) {
                        JSONObject backdrop = jsonArray.getJSONObject(i);
                        String watchlist_backdrop = "https://image.tmdb.org/t/p/w500" + backdrop.getString("backdrop_path");
                        if(watchlist_backdrop.equals("https://image.tmdb.org/t/p/w500")) {
                            im_watchlist_backdrop.setImageResource(R.drawable.bg_newsfeede);
                        }
                        else
                        Glide.with(ProfileActivity.this).load(watchlist_backdrop).into(im_watchlist_backdrop);
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
        mQueue.add(request);
        mQueue.add(requestWatchlist);
    }
    private void RequestUserList(){
        String url="https://api.themoviedb.org/3/account/%7Baccount_id%7D/lists?api_key="+apiKey+"&language=en-US&session_id="+sessionID+"&page=1";
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject list = jsonArray.getJSONObject(i);
                        String listID=list.optString("id");
                        String listName=list.getString("name");
                        String listDesc=list.optString("description");
                        String listTotal="Total Movie: "+list.optString("item_count");
                        mUserList.add(new UserListItem(listName,listDesc,listTotal,listID));
                    }
                    mUserListAdapter=new UserListAdapter(ProfileActivity.this,mUserList);
                    mRecyclerView.setAdapter(mUserListAdapter);
                    mUserListAdapter.setOnItemClickListener(ProfileActivity.this);

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
    private void OpenCreateList(){
        Intent intent = new Intent(this,CreateListActivity.class);
        startActivity(intent);
    }
    private void CallLogOut(){
        String url="http://api.themoviedb.org/3/authentication/session?api_key=1469231605651a4f67245e5257160b5f&session_id="+sessionID;
        JSONObject json = new JSONObject();
        try {
            json.put("session_id", sessionID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(savesessionID,"");
                editor.putBoolean(String.valueOf(isLogin),false);
                editor.apply();
                OpenLogInScreen();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Request Fail : " + error, Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);
    }
    private void LoadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        sessionID=sharedPreferences.getString(savesessionID,"");
        Log.d("asdafawx",""+sessionID);
    }
    private void OpenLogInScreen(){
        Intent intent= new Intent(this,LogInActivity.class);
        startActivity(intent);
    }
    private void ShowDialogLogOut(){
        dialogLogOut.setContentView(R.layout.custom_popup_confirm);
        lbMessage=dialogLogOut.findViewById(R.id.lbMessage);
        String message="Are you sure you want to log out?";
        lbMessage.setText(message);
        btnNo=dialogLogOut.findViewById(R.id.btnNo);
        btnYes=dialogLogOut.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallLogOut();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogOut.dismiss();
            }
        });
        dialogLogOut.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogLogOut.show();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this,UserListDetailActivity.class);
        UserListItem userListItem = mUserList.get(position);
        intent.putExtra(EXTRA_LIST_ID, userListItem.getListID());
        startActivity(intent);
    }
}
