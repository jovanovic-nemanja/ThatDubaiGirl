package com.thatdubaigirl.com.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.thatdubaigirl.com.Model.Common_Model;
import com.thatdubaigirl.com.R;
import com.thatdubaigirl.com.Utils.Api;
import com.thatdubaigirl.com.Utils.OnSingleClickListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class Forgot extends AppCompatActivity {
    ProgressDialog dialog;
    EditText edemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        edemail = findViewById(R.id.edemail);

        PushDownAnim.setPushDownAnimTo(findViewById(R.id.submit)).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (edemail.getText().toString().length() == 0) {
                    Toast.makeText(Forgot.this, "Please enter your email address!!", Toast.LENGTH_SHORT).show();
                } else {
                    forgotpassword();
                }
            }
        });

    }

    /*get forgotpassword APi*/
    public void forgotpassword() {
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.commn_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api loginservice = retrofit.create(Api.class);
        Call<Common_Model> call = loginservice.forgotpassword(edemail.getText().toString());
        call.enqueue(new Callback<Common_Model>() {
            @Override
            public void onResponse(Call<Common_Model> call, Response<Common_Model> response) {
                if (response.code() == 200) {
                    dialog.dismiss();
                    Log.e("adffadada", "" + response.toString());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        Toast.makeText(Forgot.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(Forgot.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Common_Model> call, Throwable t) {
                dialog.dismiss();
            }

        });
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