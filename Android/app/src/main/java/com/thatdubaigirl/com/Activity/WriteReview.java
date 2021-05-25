package com.thatdubaigirl.com.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.thatdubaigirl.com.Model.Categori_Model;
import com.thatdubaigirl.com.Model.Common_Model;
import com.thatdubaigirl.com.R;
import com.thatdubaigirl.com.Utils.Api;
import com.thatdubaigirl.com.Utils.Const;
import com.thatdubaigirl.com.Utils.OnSingleClickListener;

public class WriteReview extends AppCompatActivity {
    EditText edReview;
    RatingBar comment_rating;
    TextView txtcancel, txtsubmit;
    ProgressDialog dialog;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
//    Categori_Model categori_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        ed = sp.edit();
//        categori_model = (Categori_Model) getIntent().getSerializableExtra("data");
        comment_rating = findViewById(R.id.comment_rating);
        edReview = findViewById(R.id.edReview);
        txtcancel = findViewById(R.id.txtcancel);
        txtsubmit = findViewById(R.id.txtsubmit);

        txtcancel.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                onBackPressed();
            }
        });

        txtsubmit.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (edReview.getText().toString().length() == 0) {
                    Toast.makeText(WriteReview.this, "Please enter review", Toast.LENGTH_SHORT).show();
                } else {
                    putReviewsbyAPI(getIntent().getStringExtra("Id"), String.valueOf(comment_rating.getRating()));
                }

            }
        });
    }

    /*get categories APi*/
    public void putReviewsbyAPI(String id, String rate) {
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.commn_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api loginservice = retrofit.create(Api.class);
        Call<Common_Model> call = loginservice.putReviewsbyAPI(sp.getString("user_id", ""), id, rate, edReview.getText().toString());
        call.enqueue(new Callback<Common_Model>() {
            @Override
            public void onResponse(Call<Common_Model> call, Response<Common_Model> response) {
                if (response.code() == 200) {
                    dialog.dismiss();
                    Log.e("adffadada", "" + response.toString());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        Const.review_type = "1";
                        Toast.makeText(WriteReview.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(WriteReview.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Common_Model> call, Throwable t) {
                dialog.dismiss();
            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

}