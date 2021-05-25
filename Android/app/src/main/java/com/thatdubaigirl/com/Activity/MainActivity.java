package com.thatdubaigirl.com.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.thatdubaigirl.com.Fargment.AllOfferFragment;
import com.thatdubaigirl.com.Fargment.CategoryFragment;
import com.thatdubaigirl.com.Fargment.HomeFragment;
import com.thatdubaigirl.com.Fargment.ProfileFragment;
import com.thatdubaigirl.com.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class MainActivity extends AppCompatActivity {
    LinearLayout llId1, llId2, llId3;
    ImageView ivId1, ivId2, ivId3;

    long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llId1 = findViewById(R.id.llId1);
        llId2 = findViewById(R.id.llId2);
        llId3 = findViewById(R.id.llId3);

        ivId1 = findViewById(R.id.ivId1);
        ivId2 = findViewById(R.id.ivId2);
        ivId3 = findViewById(R.id.ivId3);


        PushDownAnim.setPushDownAnimTo(llId1).setScale(MODE_SCALE, 0.89f).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load(new HomeFragment());
                ivId1.setImageResource(R.drawable.home);
                ivId2.setImageResource(R.drawable.bag_a);
                ivId3.setImageResource(R.drawable.pro);
                llId1.setBackgroundResource(R.drawable.btn_a);
                llId2.setBackgroundColor(Color.parseColor("#000000"));
                llId3.setBackgroundColor(Color.parseColor("#000000"));
            }
        });

        PushDownAnim.setPushDownAnimTo(llId2).setScale(MODE_SCALE, 0.89f).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load(new CategoryFragment());
                ivId1.setImageResource(R.drawable.home_a);
                ivId2.setImageResource(R.drawable.bag);
                ivId3.setImageResource(R.drawable.pro);
                llId1.setBackgroundColor(Color.parseColor("#000000"));
                llId2.setBackgroundResource(R.drawable.btn_a);
                llId3.setBackgroundColor(Color.parseColor("#000000"));
            }
        });

        PushDownAnim.setPushDownAnimTo(llId3).setScale(MODE_SCALE, 0.89f).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load(new ProfileFragment());
                ivId1.setImageResource(R.drawable.home_a);
                ivId2.setImageResource(R.drawable.bag_a);
                ivId3.setImageResource(R.drawable.pro);
                llId1.setBackgroundColor(Color.parseColor("#000000"));
                llId2.setBackgroundColor(Color.parseColor("#000000"));
                llId3.setBackgroundResource(R.drawable.btn_a);
            }
        });

        load(new HomeFragment());
        ivId1.setImageResource(R.drawable.home);
        ivId2.setImageResource(R.drawable.bag_a);
        ivId3.setImageResource(R.drawable.pro);
        llId1.setBackgroundResource(R.drawable.btn_a);
        llId2.setBackgroundColor(Color.parseColor("#000000"));
        llId3.setBackgroundColor(Color.parseColor("#000000"));
    }

    public void load(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment).commit();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_frame);
        fragment.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Are you sure you want to Exit?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        finishAffinity();
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
        int count = this.getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            if (mBackPressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                finishAffinity();
                return;
            } else {
//                Toast.makeText(getBaseContext(), "Tap Again To Exit", Toast.LENGTH_SHORT).show();
                mBackPressed = System.currentTimeMillis();
            }
        }
    }


}