package com.nyka.primedb;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class BaseActivity extends AppCompatActivity {

    public String apiKey="1469231605651a4f67245e5257160b5f";


    public void NoStatusBar(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }



}
