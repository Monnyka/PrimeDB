package com.nyka.primedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button movie_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movie_detail.findViewById(R.id.ll_Button_Brows);

        movie_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenScreenDetail();
            }
        });
    }

    public void OpenScreenDetail(){
        Intent intent =new Intent(this, activity_moviedetail.class);
        startActivity(intent);
    }

}
