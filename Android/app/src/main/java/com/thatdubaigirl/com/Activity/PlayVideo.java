package com.thatdubaigirl.com.Activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.potyvideo.library.AndExoPlayerView;
import com.thatdubaigirl.com.Model.Common_Model;
import com.thatdubaigirl.com.Model.Common_Model_A;
import com.thatdubaigirl.com.R;
import com.thatdubaigirl.com.Utils.Api;

public class PlayVideo extends AppCompatActivity {
    AndExoPlayerView andExoPlayerView;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        andExoPlayerView = findViewById(R.id.andExoPlayerView);
        getvideolink();
    }

    /*get getvideolink APi*/
    public void getvideolink() {
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.commn_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api loginservice = retrofit.create(Api.class);
        Call<Common_Model_A> call = loginservice.getvideolink();
        call.enqueue(new Callback<Common_Model_A>() {
            @Override
            public void onResponse(Call<Common_Model_A> call, Response<Common_Model_A> response) {
                if (response.code() == 200) {
                    dialog.dismiss();
                    Log.e("adffadada123", "" + response.toString());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        String url = "" + response.body().getData();
                        andExoPlayerView.setSource(String.valueOf(Uri.parse(url)));
                    } else {
                        Toast.makeText(PlayVideo.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(PlayVideo.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        andExoPlayerView.stopPlayer();
    }
}