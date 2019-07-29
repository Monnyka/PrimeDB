package com.nyka.primedb;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import java.util.ArrayList;

public class ProfileActivity extends BaseActivity {

    RequestQueue mQueue;
    ImageView imFavoriteBackdrop;
    ImageView im_watchlist_backdrop;
    ImageView iV_Profile;
    TextView lbUserName;
    TextView btnCreateList;
    Toolbar toolbarNav;
    RecyclerView mRecyclerView;
    UserListAdapter mUserListAdapter;
    ArrayList<UserListItem> mUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        NoStatusBar();

        mQueue = Volley.newRequestQueue(this);
        imFavoriteBackdrop=findViewById(R.id.imFavoriteBackdrop);
        im_watchlist_backdrop=findViewById(R.id.im_watchlist_backdrop);
        iV_Profile=findViewById(R.id.iV_Profile);
        lbUserName=findViewById(R.id.lbUserName);
        btnCreateList=findViewById(R.id.btnCreateList);

        mRecyclerView=findViewById(R.id.recyclerUserList);
        mRecyclerView.hasFixedSize();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUserList= new ArrayList<>();
        toolbarNav=findViewById(R.id.toolbarNav);
        setSupportActionBar(toolbarNav);
        setTitle("");
        toolbarNav.setElevation(4);
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
    }
    private void RequestProfile(){

        String url = "https://api.themoviedb.org/3/account?api_key=1469231605651a4f67245e5257160b5f&session_id=4bff39b4c68a29530cbba35c119ae8ac4feb0f09";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String Username=response.optString("name");
                lbUserName.setText(Username);
                try {
                    JSONArray jsonArray= response.getJSONArray("avatar");
                    for(int i=0;i<=jsonArray.length();i++){
                        String imageProfile="https://www.gravatar.com/avatar/"+response.getString("hash");
                        Glide.with(ProfileActivity.this).load(imageProfile).into(iV_Profile);
                        Log.d("Imaeef",""+imageProfile);

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

        String watchlisturl="https://api.themoviedb.org/3/account/{account_id}/watchlist/movies?api_key=1469231605651a4f67245e5257160b5f&language=en-US&session_id=4bff39b4c68a29530cbba35c119ae8ac4feb0f09&sort_by=created_at.desc&page=1";
        String url="https://api.themoviedb.org/3/account/{account_id}/favorite/movies?api_key=1469231605651a4f67245e5257160b5f&session_id=4bff39b4c68a29530cbba35c119ae8ac4feb0f09&language=en-US&sort_by=created_at.desc&page=1";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i = 0; i < 1; i++) {
                        JSONObject backdrop = jsonArray.getJSONObject(i);
                        String imageUrl = "https://image.tmdb.org/t/p/w500" + backdrop.getString("backdrop_path");
                        Glide.with(ProfileActivity.this).load(imageUrl).into(imFavoriteBackdrop);
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

                    for (int i = 0; i <=1; i++) {
                        JSONObject backdrop = jsonArray.getJSONObject(i);
                        String watchlist_backdrop = "https://image.tmdb.org/t/p/w500" + backdrop.getString("backdrop_path");
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
        String url="https://api.themoviedb.org/3/account/%7Baccount_id%7D/lists?api_key=1469231605651a4f67245e5257160b5f&language=en-US&session_id=4bff39b4c68a29530cbba35c119ae8ac4feb0f09&page=1";
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject list = jsonArray.getJSONObject(i);
                        String listName=list.getString("name");
                        String listDesc=list.optString("description");
                        String listTotal="Total Movie: "+list.optString("item_count");
                        mUserList.add(new UserListItem(listName,listDesc,listTotal));
                    }
                    mUserListAdapter=new UserListAdapter(ProfileActivity.this,mUserList);
                    mRecyclerView.setAdapter(mUserListAdapter);

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
}
