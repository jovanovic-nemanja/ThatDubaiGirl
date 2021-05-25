package com.thatdubaigirl.com.Activity;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thatdubaigirl.com.Model.Categori_Model;
import com.thatdubaigirl.com.R;

public class RedeemDiscount extends AppCompatActivity {
    CircleImageView ivprofile;
    ImageView ivimage;
    TextView txtusername, txtmembernumber, txttitle;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
//    Categori_Model categori_model;
    String path_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_discount);
        sp = PreferenceManager.getDefaultSharedPreferences(RedeemDiscount.this);
        ed = sp.edit();

//        categori_model = (Categori_Model) getIntent().getSerializableExtra("data");
        path_img = getIntent().getStringExtra("path_img");

        ivprofile = findViewById(R.id.ivprofile);
        txtusername = findViewById(R.id.txtusername);
        txtmembernumber = findViewById(R.id.txtmembernumber);
        txttitle = findViewById(R.id.txttitle);
        ivimage = findViewById(R.id.ivimage);

        txtusername.setText("" + sp.getString("username", ""));
        txtmembernumber.setText("" + sp.getString("userUniqueId", ""));
        txttitle.setText("" +getIntent().getStringExtra("Title"));
        try {
            Picasso.with(RedeemDiscount.this).load("" + path_img + sp.getString("photo", "")).error(R.drawable.profile).into(ivprofile);
            Picasso.with(RedeemDiscount.this).load("" + path_img + getIntent().getStringExtra("Photo")).error(R.drawable.slider).into(ivimage);
        } catch (Exception e) {
        }
    }

    public void back(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }
}