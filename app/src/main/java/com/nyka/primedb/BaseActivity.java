package com.nyka.primedb;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    public String requestRoute="https://api.themoviedb.org";
    public String apiKey="1469231605651a4f67245e5257160b5f";
    public String sessionID="4bff39b4c68a29530cbba35c119ae8ac4feb0f09";

    public void NoStatusBar(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
}

