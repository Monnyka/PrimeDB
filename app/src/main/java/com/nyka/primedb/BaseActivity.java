package com.nyka.primedb;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    public String requestRoute="https://api.themoviedb.org";
    public String apiKey="1469231605651a4f67245e5257160b5f";
    public String api_key="?api_key=1469231605651a4f67245e5257160b5f";
    //public String sessionID="4bff39b4c68a29530cbba35c119ae8ac4feb0f09";


    public void NoStatusBar(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

//    @SuppressLint("ResourceAsColor")
//    public void OpenSnackbar(String message){
//        View contextView = findViewById(R.id.custom_snackbar);
//        Snackbar.make(contextView,message,Snackbar.LENGTH_LONG)
//                .setActionTextColor(R.color.colorWhite)
//                .show();
//    }

    public String convertDate(String date) {
            String mDate = date;
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = null;
            try {
                newDate = spf.parse(mDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            spf = new SimpleDateFormat("MMMM dd, yyyy");
            String newDateString = spf.format(newDate);
            return newDateString;
    }

}

