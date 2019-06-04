package com.nyka.primedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageViewUpcoming;
    private ImageView imageViewInThealter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewUpcoming=(ImageView) findViewById(R.id.ivComing1);
        imageViewInThealter=(ImageView)findViewById(R.id.imInTheater);

        imageViewUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDetailActivity();
            }
        });
        imageViewInThealter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDetailActivity();
            }
        });

    }
    public void OpenDetailActivity(){
        Intent intent = new Intent(this, activity_moviedetail.class);
        startActivity(intent);
    }



}
