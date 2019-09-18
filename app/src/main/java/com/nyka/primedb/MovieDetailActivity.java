package com.nyka.primedb;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.nyka.primedb.adapter.DetailUserListAdapter;
import com.nyka.primedb.adapter.ViewPagerAdapter;
import com.nyka.primedb.model.DetailUserListModel;
import com.nyka.primedb.model.MovieItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MovieDetailActivity extends BaseActivity {

    String mm="";
    TextView lbMovieTitle;
    RequestQueue mQueue;
    String MovieID = "";
    String imdb_id="";

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
    TextView lbDirectorName;
    TextView lbYourRating;
    ImageView ivDirector;
    ImageView ivWatchLater;
    TextView lbRating;
    TextView lbMovieTitleRating;
    String favorite="false";
    String watchListStatus="false";
    Dialog successDialog;
    Dialog ratingDialog;
    Button btnOkay;
    Button btnSubmit;
    RatingBar ratingMovie;
    ViewPager viewPager;
    int current_position=0;
    ViewPagerAdapter viewPagerAdapter;

    private TabAdapter tabAdapter;
    ViewPager viewPagerDetail;
    TabLayout tabLayout;


    TextView lbMessage;
    BottomSheetBehavior mBottomSheetBehavior;
    RelativeLayout btnPersonalRate;
    ImageView ivFavorite;
    RelativeLayout rl_Favorite;
    RelativeLayout rl_PersonalRate;
    RecyclerView mRecyclerView;
    ArrayList<MovieItem> mMovieList;
    ArrayList<String> imageUrl;
    ScrollView svMovieDetail;
    Toolbar toolbars;
    RecyclerView detailUserListRecycler;
    ArrayList<DetailUserListModel> mDetailUserList;
    DetailUserListAdapter mDetailUserListAdapter;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetail);
        NoStatusBar();

        toolbars=findViewById(R.id.toolbars);
        setSupportActionBar(toolbars);
        toolbars.setBackgroundColor(getColor(R.color.colorMainBackground));
        setTitle("");

        toolbars.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        //movieID
        MovieID = intent.getStringExtra("movieID");
        lbMovieTitle = findViewById(R.id.lbMovieTitle);
        ivPoster = findViewById(R.id.ivPoster);
        ivFavorite=findViewById(R.id.ivFavorite);
        ivWatchLater=findViewById(R.id.ivWatchLater);
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
        lbDirectorName = findViewById(R.id.lbDirectorName);
        lbYourRating=findViewById(R.id.lbYourRating);
        ivDirector = findViewById(R.id.ivDirector);
        lbDistributeDetail = findViewById(R.id.lbDistributeDetail);
        btnPersonalRate=findViewById(R.id.rl_PersonalRate);
        lbSynopsisDetail = findViewById(R.id.lbSynopsisDetail);
        rl_Favorite=findViewById(R.id.rl_Favorite);
        rl_PersonalRate=findViewById(R.id.rl_PersonalRate);
        successDialog = new Dialog(this);
        ratingDialog=new Dialog(this);
        View bottomSheet=findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior=BottomSheetBehavior.from(bottomSheet);
        viewPager=findViewById(R.id.view_pager);
        tabLayout=findViewById(R.id.tabLayout);

        mRecyclerView = findViewById(R.id.rc_cast);
        svMovieDetail=findViewById(R.id.svMovieDetail);
        svMovieDetail.setVisibility(View.INVISIBLE);

        detailUserListRecycler=findViewById(R.id.rv_userList);
        detailUserListRecycler.setHasFixedSize(true);
        detailUserListRecycler.setLayoutManager(new LinearLayoutManager(this));
        detailUserListRecycler.setAdapter(mDetailUserListAdapter);

        mMovieList = new ArrayList<>();
        imageUrl = new ArrayList<>();
        mDetailUserList=new ArrayList<>();

        rl_Favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favorite.equals("false")) {
                    AddFavorite();
                    favorite = "true";
                } else {
                    favorite = "false";
                    RemoveFavorite();
                }
            }
        });
        btnPersonalRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserList();
            }
        });
        ivWatchLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(watchListStatus.equals("false")) {
                    ivWatchLater.setImageResource(R.drawable.ic_bookmarked);
                    AddWatchList();
                    watchListStatus="true";
                }
                else
                {
                    ivWatchLater.setImageResource(R.drawable.ic_bookmark);
                    RemoveWatchList();
                    watchListStatus="false";
                }
            }
        });
        rl_PersonalRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=lbMovieTitle.getText().toString();
                ShowDialogRating(title);
            }
        });

        getAccountState();
        getMovieDetail();
        getBanner();

        viewPagerDetail =  findViewById(R.id.viewPagerDetail);
        tabLayout =  findViewById(R.id.tabLayout);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new Tab1Fragment(), "Cast/Crew");
        tabAdapter.addFragment(new Tab2Fragment(), "Review");
        viewPagerDetail.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPagerDetail);

        svMovieDetail.setVisibility(View.VISIBLE);
    }
    private void getAccountState(){
        String urlAccountState=requestRoute+"/3/movie/"+MovieID+"/account_states?api_key="+apiKey+"&session_id=4bff39b4c68a29530cbba35c119ae8ac4feb0f09";
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlAccountState, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                boolean fav=response.optBoolean("favorite");
                boolean wat=response.optBoolean("watchlist");
                if(fav) {
                    favorite="true";
                    ivFavorite.setImageResource(R.drawable.ic_heart_clicked);
                }
                if(wat){
                    watchListStatus="true";
                    ivWatchLater.setImageResource(R.drawable.ic_bookmarked);
                }

                try {
                    JSONObject jsonObject=response.getJSONObject("rated");
                    String userScore=jsonObject.getString("value");
                    lbYourRating.setText(userScore);
                    lbYourRating.setTextColor(getResources().getColor(R.color.colorLightGreen));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });
mQueue.add(request);
    }
    private void AddFavorite() {
        String urlAddFavorite="https://api.themoviedb.org/3/account/{account_id}/favorite?api_key="+apiKey+"&session_id=4bff39b4c68a29530cbba35c119ae8ac4feb0f09";
            JSONObject JsonFavorite = new JSONObject();

        try {
            JsonFavorite.put("media_type","movie");
            JsonFavorite.put("media_id",""+MovieID);
            JsonFavorite.put("favorite",true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlAddFavorite, JsonFavorite, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String message="Successfully, Added this movie to your favorite list.";
                ShowDialogSuccess(message);

                ivFavorite.setImageResource(R.drawable.ic_heart_clicked);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Request Fail: Please check you internet connection.",Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);
    }
    private void RemoveFavorite(){
        String urlAddFavorite="https://api.themoviedb.org/3/account/{account_id}/favorite?api_key=1469231605651a4f67245e5257160b5f&session_id=4bff39b4c68a29530cbba35c119ae8ac4feb0f09";
        JSONObject JsonFavorite = new JSONObject();

        try {
            JsonFavorite.put("media_type","movie");
            JsonFavorite.put("media_id",""+MovieID);
            JsonFavorite.put("favorite",false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlAddFavorite, JsonFavorite, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String message="Successfully, Removed this movie from your favorite list.";
                ShowDialogSuccess(message);
                ivFavorite.setImageResource(R.drawable.ic_heart);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Request Fail: Please check you internet connection.",Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);
    }
    private void AddWatchList(){
        String urlAddFavorite="https://api.themoviedb.org/3/account/{account_id}/watchlist?api_key=1469231605651a4f67245e5257160b5f&session_id=4bff39b4c68a29530cbba35c119ae8ac4feb0f09";
        JSONObject JsonFavorite = new JSONObject();

        try {
            JsonFavorite.put("media_type","movie");
            JsonFavorite.put("media_id",""+MovieID);
            JsonFavorite.put("watchlist",true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlAddFavorite, JsonFavorite, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String message="Successfully, Added this movie to your watchlist.";
                ShowDialogSuccess(message);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Request Fail: Please check you internet connection.",Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);
    }
    private void RemoveWatchList(){
        String urlAddFavorite="https://api.themoviedb.org/3/account/{account_id}/watchlist?api_key=1469231605651a4f67245e5257160b5f&session_id=4bff39b4c68a29530cbba35c119ae8ac4feb0f09";
        JSONObject JsonFavorite = new JSONObject();

        try {
            JsonFavorite.put("media_type","movie");
            JsonFavorite.put("media_id",""+MovieID);
            JsonFavorite.put("watchlist",false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlAddFavorite, JsonFavorite, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String message="Successfully, Removed this movie from your watchlist.";
                ShowDialogSuccess(message);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Request Fail: Please check you internet connection.",Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);
    }
    public void getMovieDetail() {
        String movieUrl = "https://api.themoviedb.org/3/movie/" + MovieID + "?api_key=" + apiKey + "&language=en-US";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, movieUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String imdbID=response.optString("imdb_id");
                    String MovieTitle = response.getString("original_title");
                    String poster = "https://image.tmdb.org/t/p/w185" + response.getString("poster_path");
                    String Synopsis = response.getString("overview");
                    String MovieRate = response.getString("vote_average");
                    String MovieTagLine = response.getString("tagline");
                    String MovieYear = response.getString("release_date");
                    String MovieRunTime = response.getString("runtime") + "";
                    String MovieLanguage = response.getString("original_language");
                    boolean MovieRateR = response.getBoolean("adult");
                    String Revenue = response.getString("revenue");
                    String MovieVoteCount = response.getString("vote_count");
                    String MovieBudget = response.getString("budget");
                    imdb_id=imdbID;
                    String DateFormat = ConvertDateString(MovieYear);
                    lbYear.setText(DateFormat);
                    if (Revenue != null && Revenue.length() > 6) {
                        String MovieRevenue = Revenue.substring(0, Revenue.length() - 6) + "M";
                        lbRevenue.setText(MovieRevenue);
                    } else lbRevenue.setText(Revenue);

                    Long mBudget = Long.parseLong(MovieBudget) / 1000000;
                    if (mBudget > 0) {
                        String mBudgetConvert = (mBudget) + "M";
                        lbBudget.setText(mBudgetConvert);
                    } else {
                        String mBudgetConvert = "" + (mBudget);
                        lbBudget.setText(mBudgetConvert);
                    }

                    if (MovieRateR) {
                        lbRatedR.append("Rated-R");
                    } else lbRatedR.append("Everyone");

                    if (MovieRunTime.equals("null")) {
                        lbRunTime.append("0");
                    } else lbRunTime.append((MovieRunTime) + "Minute");

                    lbMovieTitle.setText(MovieTitle);
                    lbSynopsisDetail.setText(Synopsis);
                    lbRated.setText(MovieRate);
                    lbLanguage.setText(MovieLanguage.toUpperCase());
                    lbTagLine.setText(MovieTagLine);
                    lbVoteCount.setText(MovieVoteCount);

                    if(MovieTagLine.equals("")){
                        lbTagLine.setVisibility(View.GONE);
                    }

                    Glide.with(MovieDetailActivity.this).load(poster).into(ivPoster);

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
                        String P_company = "~" + jsonObject.optString("name") + "\n";
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

        mQueue.add(request);

    }
    public void getUserList(){
        String url="https://api.themoviedb.org/3/account/{account_id}/lists?api_key=1469231605651a4f67245e5257160b5f&language=en-US&session_id=4bff39b4c68a29530cbba35c119ae8ac4feb0f09&page=1";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject list = jsonArray.getJSONObject(i);
                        String listName =list.optString("name");
                        mDetailUserList.add(new DetailUserListModel(listName));
                    }
                    mDetailUserListAdapter= new DetailUserListAdapter(MovieDetailActivity.this,mDetailUserList);
                    detailUserListRecycler.setAdapter(mDetailUserListAdapter);

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
    private String ConvertDateString(String MovieYear) {
        String aa = MovieYear;
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = spf.parse(aa);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("dd MMM, yyyy");
        String newDateString = spf.format(newDate);

        return newDateString;
    }
    private void getBanner(){
       String url=requestRoute+"/3/movie/"+MovieID+"/images?api_key=1469231605651a4f67245e5257160b5f";
        JsonObjectRequest requestBanner = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("backdrops");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject banner = jsonArray.getJSONObject(i);
                        mm=("https://image.tmdb.org/t/p/w500"+banner.getString("file_path"));
                        imageUrl.add(mm);
                    }
                    viewPager =findViewById(R.id.view_pager);
                    viewPagerAdapter = new ViewPagerAdapter(MovieDetailActivity.this, imageUrl);
                    viewPager.setAdapter(viewPagerAdapter);
                    createSlideShow();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mQueue.add(requestBanner);
    }
    private void AddRating(final String rateScore){
        String url="https://api.themoviedb.org/3/movie/"+MovieID+"/rating?api_key=1469231605651a4f67245e5257160b5f&session_id=4bff39b4c68a29530cbba35c119ae8ac4feb0f09";

        JSONObject body =new JSONObject();
        try {
            body.put("value",rateScore);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(),"Successful, Rate this movie",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Fail, Rate this movie",Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);
    }
    private void ShowDialogSuccess(String message){
        successDialog.setContentView(R.layout.custom_popup_success);
        lbMessage=successDialog.findViewById(R.id.lbMessage);
        btnOkay=successDialog.findViewById(R.id.btnOkay);
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successDialog.dismiss();
            }
        });
        lbMessage.setText(message);
        Objects.requireNonNull(successDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        successDialog.show();
    }
    private void ShowDialogRating(String mMovieTitle){
        ratingDialog.setContentView(R.layout.custompopup_rating);
        btnSubmit=ratingDialog.findViewById(R.id.btnSubmit);
        ratingMovie=ratingDialog.findViewById(R.id.ratingMovie);
        lbMovieTitleRating=ratingDialog.findViewById(R.id.lbMovieTitle);
        lbMovieTitle.setText(mMovieTitle);
        lbRating=ratingDialog.findViewById(R.id.lbRating);
        ratingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ratingDialog.show();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating=ratingMovie.getRating();
                String score= String.valueOf(rating);
                AddRating(score);
                lbYourRating.setText(score);
                ratingDialog.dismiss();
            }
        });
        ratingMovie.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                float ratingChange=ratingMovie.getRating();
                String ratings= String.valueOf(ratingChange);
                lbRating.setText(ratings);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.it_add:
                getUserList();
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                return true;
            default:
                    return super.onOptionsItemSelected(item);
        }
    }
    private void createSlideShow() {

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (current_position == imageUrl.size())
                    current_position=1;
                    viewPager.setCurrentItem(current_position++,true);
            }
        };
        Timer timer= new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        },250,3000);
    }


}

