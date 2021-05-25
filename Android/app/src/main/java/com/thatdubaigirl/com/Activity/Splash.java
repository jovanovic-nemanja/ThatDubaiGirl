package com.thatdubaigirl.com.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.thatdubaigirl.com.R;
import com.thatdubaigirl.com.Utils.RS_Application;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Splash extends AppCompatActivity {
    protected static final int REQUEST_ACCESS_FINE_LOCATION_PERMISSION = 101;
    AlertDialog mAlertDialog;
    long Delay = 3000;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        ed = sp.edit();
        init();

    }
    public void init() {
        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                if (RS_Application.isNetworkAvailable(Splash.this) == true) {
                    if (sp.getString("user_id", "").equalsIgnoreCase("")) {
                        Intent intent = new Intent(Splash.this, Welcome.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        Intent intent = new Intent(Splash.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                } else {
                    RS_Application.noInternet(Splash.this);
                }
            }
        }, 2000);
    }



}