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
    TextView lbTagLine;
    TextView lbYear;
    TextView lbRunTime;
    TextView lbLanguage;
    TextView lbRatedR;
    TextView lbRevenue;
    TextView lbGenre;
    TextView lbVoteCount;
    TextView lbDistributeDetail;

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
        lbTagLine=findViewById(R.id.lbTagLine);
        lbBudget=findViewById(R.id.lbBudget);
        lbYear=findViewById(R.id.lbYear);
        lbRunTime=findViewById(R.id.lbRunTime);
        lbLanguage=findViewById(R.id.lbLanguage);
        lbRatedR=findViewById(R.id.lbRatedR);
        lbRevenue=findViewById(R.id.lbRevenue);
        lbGenre=findViewById(R.id.lbGenre);
        lbVoteCount=findViewById(R.id.lbVoteCount);
        lbDistributeDetail=findViewById(R.id.lbDistributeDetail);
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
                        String MovieTagLine=response.getString("tagline");
                        String MovieYear=response.getString("release_date");
                        String MovieRunTime=response.getString("runtime");
                        String MovieLanguage=response.getString("original_language");
                        boolean MovieRateR=response.getBoolean("adult");
                        String Revenue=response.getString("revenue");
                        String MovieStatus=response.getString("status");
                        String MovieVoteCount=response.getString("vote_count");

                        if(Revenue!=null&&Revenue.length()>6){
                            String MovieRevenue = Revenue.substring(0, Revenue.length() - 6)+"M";
                            lbRevenue.setText(MovieRevenue);
                        }else lbRevenue.setText(Revenue);


                    String MovieBudget=response.getString("budget");
                        Long mBudget =Long.parseLong(MovieBudget)/1000000;
                        if(mBudget>0) {
                            String mBudgetConvert =(mBudget)+"M";
                            lbBudget.setText(mBudgetConvert);
                        }else{

                            String mBudgetConvert =""+(mBudget);
                            lbBudget.setText(mBudgetConvert);
                        }

                        if(MovieYear.length()>4){
                            MovieYear .substring(MovieYear .length() - 4);
                            lbYear.setText(MovieYear);
                        }
                        if(MovieRateR==true){
                            lbRatedR.setText("Rated-R");
                        }else lbRatedR.setText("Everyone");

                        String minute=" Minute";
                        lbRunTime.setText((MovieRunTime)+minute);
                        lbMovieTitle.setText(MovieTitle);
                        lbSynopsisDetail.setText(Synopsis);
                        lbRated.setText(MovieRate);
                        lbLanguage.setText(MovieLanguage.toUpperCase());
                        lbTagLine.setText("-" + MovieTagLine + "-");
                        lbVoteCount.setText(MovieVoteCount);

                        Glide.with(activity_moviedetail.this).load(poster).into(ivPoster);

                        JSONArray jsonArray = response.optJSONArray("genres");

                         //Iterate the jsonArray and print the info of JSONObjects
                    //Loop find genre
                        for (int i = 0; i < jsonArray.length(); i++) {
                         JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String MovieGenre = jsonObject.optString("name") + "//";
                         if(i<jsonArray.length()-1) {
                             String MovieGenres=MovieGenre.substring(0, MovieGenre.length() - 1);
                             lbGenre.append(MovieGenres);
                         }else {
                             String MovieGenres = MovieGenre.substring(0, MovieGenre.length() - 2);
                             lbGenre.append(MovieGenres);
                         }






                    }
                        //Loop find distribute company
                        JSONArray jsonArray1 = response.optJSONArray("production_companies");

                        for(int i=0;i<jsonArray1.length();i++){

                            JSONObject jsonObject =jsonArray1.getJSONObject(i);
                            String P_company ="-"+jsonObject.optString("name")+"\n";
                            lbDistributeDetail.append(P_company);
                        }


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
