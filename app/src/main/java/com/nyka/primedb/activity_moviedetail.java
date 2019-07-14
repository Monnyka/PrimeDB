package com.nyka.primedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class activity_moviedetail extends BaseActivity {

    String mm="";
    ImageView btnBack;
    TextView lbMovieTitle;
    RequestQueue mQueue;
    String MovieID = "";
    String imdb_id="";

    ArrayList<String> mArrayList;


    /*  String[] imageUrls = new String[]{
                "https://image.tmdb.org/t/p/w185/kZv92eTc0Gg3mKxqjjDAM73z9cy.jpg"
        };*/
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
    ImageView ivDirector;
    TextView lbCastMore;
    ImageView btnAddFavorite;
    String favorite="false";

    RecyclerView mRecyclerView;
    MovieAdapter mMovieAdapter;
    ArrayList<MovieItem> mMovieList;
    ArrayList<String> imageUrl;
    ScrollView svMovieDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetail);
        NoStatusBar();
        mQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        //movieID
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
        lbDirectorName = findViewById(R.id.lbDirectorName);
        ivDirector = findViewById(R.id.ivDirector);
        lbDistributeDetail = findViewById(R.id.lbDistributeDetail);
        lbCastMore = findViewById(R.id.lbCastMore);
        lbSynopsisDetail = findViewById(R.id.lbSynopsisDetail);
        btnAddFavorite=findViewById(R.id.btnAddFavorite);
        mRecyclerView = findViewById(R.id.recycler_view_cast);
        svMovieDetail=findViewById(R.id.svMovieDetail);
        svMovieDetail.setVisibility(View.INVISIBLE);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mMovieAdapter);

        mMovieList = new ArrayList<>();
        imageUrl = new ArrayList<>();

        getMovieDetail();
        getBanner();
        getAccountState();



        svMovieDetail.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivity();
            }
        });
        btnAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favorite.equals("false")) {
                    AddFavorite();
                    favorite="true";
                }else{
                    RemoveFavorite();
                    favorite="false";
                }
            }
        });
    }

    private void getAccountState(){

        String urlAccountState="https://api.themoviedb.org/3/movie/"+MovieID+"/account_states?api_key=1469231605651a4f67245e5257160b5f&session_id=4bff39b4c68a29530cbba35c119ae8ac4feb0f09";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlAccountState, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                boolean fav=response.optBoolean("favorite");
                if(fav) {
                    favorite="true";
                    btnAddFavorite.setImageResource(R.drawable.ic_heart_clicked);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
mQueue.add(request);
    }

    private void AddFavorite() {
        String urlAddFavorite="https://api.themoviedb.org/3/account/{account_id}/favorite?api_key=1469231605651a4f67245e5257160b5f&session_id=4bff39b4c68a29530cbba35c119ae8ac4feb0f09";
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
                Toast.makeText(getApplicationContext(),"Successfully added this movie to favorite list.",Toast.LENGTH_SHORT).show();
                btnAddFavorite.setImageResource(R.drawable.ic_heart_clicked);
                btnAddFavorite.setPadding(15,15,15,15);
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
                Toast.makeText(getApplicationContext(),"Successfully removed this movie from favorite list.",Toast.LENGTH_SHORT).show();
                btnAddFavorite.setImageResource(R.drawable.ic_heart);
                btnAddFavorite.setPadding(15,15,15,15);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Request Fail: Please check you internet connection.",Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);
    }

    private void OpenActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void getMovieDetail() {
        String movieUrl = "https://api.themoviedb.org/3/movie/" + MovieID + "?api_key=" + apiKey + "&language=en-US";
        String movieCreditUrl = "https://api.themoviedb.org/3/movie/" + MovieID + "/credits?api_key=" + apiKey;

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

                    for (int i = 0; i < 10; i++) {

                        JSONObject cast = jsonArray.getJSONObject(i);

                        int CastMore = jsonArray.length() - 10;
                        String maa = String.valueOf(CastMore);
                        lbCastMore.setText(maa);
                        lbCastMore.append(" More");
                        String CastName = cast.getString("name");
                        String CharrecterName = "as " + cast.getString("character");
                        String aa = cast.getString("profile_path");
                        String imageUrl = "https://image.tmdb.org/t/p/w185" + aa;
                        mMovieList.add(new MovieItem(imageUrl, CastName, CharrecterName));

                    }
                    mMovieAdapter = new MovieAdapter(activity_moviedetail.this, mMovieList);
                    mRecyclerView.setAdapter(mMovieAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Get crew director
                try {
                    JSONArray jsonArray = response.getJSONArray("crew");

                    for (int i = 0; i <= jsonArray.length(); i++) {
                        JSONObject crew = jsonArray.getJSONObject(i);

                        String getDirector = crew.optString("job");
                        String Director_Image_Profile = "https://image.tmdb.org/t/p/w500" + crew.optString("profile_path");

                        if (getDirector.equals("Director")) {
                            String DirectorName = crew.optString("name");
                            Glide.with(activity_moviedetail.this).load(Director_Image_Profile).into(ivDirector);

                            if (lbDirectorName.getText() == "") {
                                lbDirectorName.append(DirectorName);
                            } else if (lbDirectorName.getText() != null)
                                lbDirectorName.append(" & " + DirectorName);
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

        mQueue.add(request);
        mQueue.add(creditRequest);
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

        //String url="https://api.themoviedb.org/3/movie/"+MovieID+"/images?api_key=1469231605651a4f67245e5257160b5f&language=en-US&include_image_language=en";
       String url="https://api.themoviedb.org/3/movie/"+MovieID+"/images?api_key=1469231605651a4f67245e5257160b5f";
        JsonObjectRequest requestBanner = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("backdrops");
                    //JSONArray jsonArrayPoster = response.getJSONArray("posters");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject banner = jsonArray.getJSONObject(i);
                        mm=("https://image.tmdb.org/t/p/w500"+banner.getString("file_path"));
                        //mm=banner.getString("");
                        Log.d("god","hell: "+mm);
                        imageUrl.add(mm);
                    }
//                    for(int i=0;i<jsonArrayPoster.length();i++){
//                        JSONObject poster = jsonArrayPoster.getJSONObject(i);
//                        mm=("https://image.tmdb.org/t/p/original"+poster.getString("file_path"));
//                        Log.d("god1","hell: "+mm);
//                        imageUrl.add(mm);
 //                   }

                    ViewPager viewPager =findViewById(R.id.view_pager);
                    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(activity_moviedetail.this, imageUrl);
                    viewPager.setAdapter(viewPagerAdapter);

                    Log.d("ffda",""+imageUrl);

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

}

