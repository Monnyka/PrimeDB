package com.nyka.primedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class activity_moviedetail extends BaseActivity {

    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoStatusBar();
        setContentView(R.layout.activity_moviedetail);

        btnBack = (ImageView)findViewById(R.id.btnBackDetail);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivity();
            }
        });
    }

    private void OpenActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
