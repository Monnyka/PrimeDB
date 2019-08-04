package com.nyka.primedb;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    public String requestRoute="https://api.themoviedb.org";
    public String apiKey="1469231605651a4f67245e5257160b5f";
    public String api_key="?api_key=1469231605651a4f67245e5257160b5f";
    //public String sessionID="4bff39b4c68a29530cbba35c119ae8ac4feb0f09";
    Dialog successDialog;
    TextView lbMessage;
    Button btnOkay;

    public void NoStatusBar(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    @SuppressLint("ResourceAsColor")
    public void OpenSnackbar(){
        View contextView = findViewById(R.id.coordinatorLayout);
        Snackbar.make(contextView, "SnackText",Snackbar.LENGTH_LONG)
                .setActionTextColor(R.color.colorWhite)
                .show();
    }

//    public void ShowDialogSuccess(String message){
//        successDialog.setContentView(R.layout.custom_popup_success);
//        lbMessage=successDialog.findViewById(R.id.lbMessage);
//        btnOkay=successDialog.findViewById(R.id.btnOkay);
//        btnOkay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                successDialog.dismiss();
//            }
//        });
//        lbMessage.setText(message);
//        successDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        successDialog.show();
//    }

}

