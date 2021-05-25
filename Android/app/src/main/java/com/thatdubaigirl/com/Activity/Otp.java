package com.thatdubaigirl.com.Activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thatdubaigirl.com.Model.Common_Model_A;
import com.thatdubaigirl.com.R;
import com.thatdubaigirl.com.Utils.Api;
import com.thatdubaigirl.com.Utils.OnSingleClickListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class Otp extends AppCompatActivity {
    EditText edcode;
    TextView submit, txtresend;
    String email;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        edcode = findViewById(R.id.edcode);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        submit = findViewById(R.id.submit);
        txtresend = findViewById(R.id.txtresend);
        email=getIntent().getStringExtra("email");

        PushDownAnim.setPushDownAnimTo(findViewById(R.id.submit)).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (edcode.getText().toString().length() == 0) {
                    Toast.makeText(Otp.this, "Please Enter Code!!", Toast.LENGTH_SHORT).show();
                } else {
                    validateCode();
                }
            }
        });

        PushDownAnim.setPushDownAnimTo(findViewById(R.id.txtresend)).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                emailverify();
            }
        });
    }

    /*get validateCode APi*/
    public void validateCode() {
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.commn_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api loginservice = retrofit.create(Api.class);
        Call<Common_Model_A> call = loginservice.validateCode(email, edcode.getText().toString());
        call.enqueue(new Callback<Common_Model_A>() {
            @Override
            public void onResponse(Call<Common_Model_A> call, Response<Common_Model_A> response) {
                if (response.code() == 200) {
                    dialog.dismiss();
                    Log.e("adffadada", "" + response.toString());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        Toast.makeText(Otp.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Otp.this, Register.class);
                        i.putExtra("email", email);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    } else {
                        Toast.makeText(Otp.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Common_Model_A> call, Throwable t) {
                dialog.dismiss();
            }

        });
    }


    /*get emailverify APi*/
    public void emailverify() {
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.commn_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api loginservice = retrofit.create(Api.class);
        Call<Common_Model_A> call = loginservice.emailverify(email);
        call.enqueue(new Callback<Common_Model_A>() {
            @Override
            public void onResponse(Call<Common_Model_A> call, Response<Common_Model_A> response) {
                if (response.code() == 200) {
                    dialog.dismiss();
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        Toast.makeText(Otp.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Otp.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Common_Model_A> call, Throwable t) {
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