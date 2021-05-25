package com.thatdubaigirl.com.Activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thatdubaigirl.com.Model.Common_Model;
import com.thatdubaigirl.com.R;
import com.thatdubaigirl.com.Utils.Api;
import com.thatdubaigirl.com.Utils.OnSingleClickListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class Login extends AppCompatActivity {
    EditText edemailid, edMPassword;
    TextView submit, forgot;
    ProgressDialog dialog;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        ed = sp.edit();
        edemailid = findViewById(R.id.edemailid);
        edMPassword = findViewById(R.id.edMPassword);
        submit = findViewById(R.id.submit);
        forgot = findViewById(R.id.forgot);
        PushDownAnim.setPushDownAnimTo(submit).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (edemailid.getText().toString().length() == 0) {
                    Toast.makeText(Login.this, "Please enter your email address!!", Toast.LENGTH_SHORT).show();
                } else if (edMPassword.getText().toString().length() == 0) {
                    Toast.makeText(Login.this, "Please Enter Password!!", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser();
                }
            }
        });

        PushDownAnim.setPushDownAnimTo(forgot).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                startActivity(new Intent(Login.this, Forgot.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }

    /*get loginUser APi*/
    public void loginUser() {
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.commn_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api loginservice = retrofit.create(Api.class);
        Call<Common_Model> call = loginservice.loginUser(edemailid.getText().toString(), edMPassword.getText().toString());
        call.enqueue(new Callback<Common_Model>() {
            @Override
            public void onResponse(Call<Common_Model> call, Response<Common_Model> response) {
                if (response.code() == 200) {
                    dialog.dismiss();
                    Log.e("adffadada", "" + response.toString());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        ed.putString("user_id", "" + response.body().getData().getId());
                        ed.putString("username", "" + response.body().getData().getUsername());
                        ed.putString("userUniqueId", "" + response.body().getData().getUserUniqueId());
                        ed.putString("email", "" + response.body().getData().getEmail());
                        ed.putString("photo", "" + response.body().getData().getPhoto());
                        ed.putString("address", "" + response.body().getData().getAddress());
                        ed.putString("birthday", "" + response.body().getData().getBirthday());
                        ed.commit();
                        Toast.makeText(Login.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, PlayVideo.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        Toast.makeText(Login.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Common_Model> call, Throwable t) {
                dialog.dismiss();
            }

        });
    }

}