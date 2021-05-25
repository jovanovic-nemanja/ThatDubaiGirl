package com.thatdubaigirl.com.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.thatdubaigirl.com.Adapter.ReviewRate_Adapter;
import com.thatdubaigirl.com.Model.Categori_Model;
import com.thatdubaigirl.com.R;
import com.thatdubaigirl.com.Utils.Api;
import com.thatdubaigirl.com.Utils.Const;
import com.thatdubaigirl.com.Utils.OnSingleClickListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class DetalisPage extends AppCompatActivity {
    TextView name, txtvendername, txtcatname, txtOverView, txtReviews, txtbtn, ivNoDataId, txtdescription;
    ImageView icon, ivcall, ivinsta, ivcrome, ivbanner;
    CircleImageView ivimage;
    View view, view1;
    Categori_Model categori_model;
    String path_img;
    LinearLayout lloverview, llreview;
    ProgressDialog dialog;
    private ArrayList<Categori_Model> rate_list = new ArrayList<>();
    RecyclerView recyclerviewreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalis_page);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        Const.review_type = "0";
        Const.review_type_a = "0";
        categori_model = (Categori_Model) getIntent().getSerializableExtra("data");
        path_img = getIntent().getStringExtra("path_img");

        name = findViewById(R.id.name);
        icon = findViewById(R.id.icon);
        ivimage = findViewById(R.id.ivimage);
        txtvendername = findViewById(R.id.txtvendername);
        txtcatname = findViewById(R.id.txtcatname);
        txtOverView = findViewById(R.id.txtOverView);
        txtReviews = findViewById(R.id.txtReviews);
        txtdescription = findViewById(R.id.txtdescription);
        view = findViewById(R.id.view);
        view1 = findViewById(R.id.view1);
        txtbtn = findViewById(R.id.txtbtn);
        ivbanner = findViewById(R.id.ivbanner);

        ivcall = findViewById(R.id.ivcall);
        ivinsta = findViewById(R.id.ivinsta);
        ivcrome = findViewById(R.id.ivcrome);

        lloverview = findViewById(R.id.lloverview);
        llreview = findViewById(R.id.llreview);

        ivNoDataId = findViewById(R.id.ivNoDataId);

        recyclerviewreview = findViewById(R.id.recyclerviewreview);

        name.setText("" + categori_model.getTitle());
        txtvendername.setText("" + categori_model.getVendorname());
        txtcatname.setText("" + categori_model.getLocation());
        txtdescription.setText("" + Html.fromHtml(categori_model.getDescription()));
        if (categori_model.getStatus().equalsIgnoreCase("2")) {
            ivbanner.setVisibility(View.VISIBLE);
        } else {
            ivbanner.setVisibility(View.GONE);
        }
        try {
            Picasso.with(DetalisPage.this).load("" + path_img + categori_model.getDiscount_photo()).error(R.drawable.slider).into(icon);
            Picasso.with(DetalisPage.this).load("" + path_img + categori_model.getPhoto()).error(R.drawable.slider).into(ivimage);
        } catch (Exception e) {
        }

        lloverview.setVisibility(View.VISIBLE);
        llreview.setVisibility(View.GONE);
        txtbtn.setText("Redeem Discount");
        txtOverView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lloverview.setVisibility(View.VISIBLE);
                llreview.setVisibility(View.GONE);
                view.setBackgroundColor(ContextCompat.getColor(DetalisPage.this, R.color.bule));
                view1.setBackgroundColor(ContextCompat.getColor(DetalisPage.this, R.color.black));
                txtbtn.setText("Redeem Discount");
            }
        });
        txtReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lloverview.setVisibility(View.GONE);
                llreview.setVisibility(View.VISIBLE);
                view.setBackgroundColor(ContextCompat.getColor(DetalisPage.this, R.color.black));
                view1.setBackgroundColor(ContextCompat.getColor(DetalisPage.this, R.color.bule));
                txtbtn.setText("Write a Review");
            }
        });
        PushDownAnim.setPushDownAnimTo(ivcrome).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                try {
                    String url = categori_model.getWebsite_link();
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(DetalisPage.this, "No application can handle this request."
                            + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        PushDownAnim.setPushDownAnimTo(ivinsta).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                try {
                    String url = categori_model.getInstagram_id();
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(DetalisPage.this, "No application can handle this request."
                            + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        PushDownAnim.setPushDownAnimTo(ivcall).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                String call_no = categori_model.getPhone();
                Intent callIntent = new Intent(Intent.ACTION_VIEW);
                callIntent.setData(Uri.parse("tel:" + call_no));
                startActivity(callIntent);
            }
        });
        PushDownAnim.setPushDownAnimTo(txtbtn).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (txtbtn.getText().toString().equalsIgnoreCase("Redeem Discount")) {
                    startActivity(new Intent(DetalisPage.this, RedeemDiscount.class)
                            .putExtra("Photo", categori_model.getPhoto())
                            .putExtra("Title", categori_model.getTitle())
                            .putExtra("path_img", path_img));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    if (Const.review_type_a.equalsIgnoreCase("0")) {
                        startActivity(new Intent(DetalisPage.this, WriteReview.class)
                                .putExtra("Id", categori_model.getId())
                                .putExtra("path_img", path_img));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }else {
                        Toast.makeText(DetalisPage.this, "You have already left a review.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        getdetaildiscountbyid(categori_model.getId());
    }

    /*get getdetaildiscountbyid APi*/
    public void getdetaildiscountbyid(String id) {
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.commn_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api loginservice = retrofit.create(Api.class);
        Call<Categori_Model> call = loginservice.getdetaildiscountbyid(id);
        call.enqueue(new Callback<Categori_Model>() {
            @Override
            public void onResponse(Call<Categori_Model> call, Response<Categori_Model> response) {
                if (response.code() == 200) {
                    dialog.dismiss();
                    Log.e("adffadada", "" + response.toString());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        rate_list = response.body().getData().get(0).getReviews();
                        if (rate_list.size() > 0) {
                            ReviewRate_Adapter adapter_home_offer_list = new ReviewRate_Adapter(DetalisPage.this, rate_list, path_img);
                            recyclerviewreview.setAdapter(adapter_home_offer_list);
                            adapter_home_offer_list.notifyDataSetChanged();
                            recyclerviewreview.setVisibility(View.VISIBLE);
                            ivNoDataId.setVisibility(View.GONE);
                        } else {
                            recyclerviewreview.setVisibility(View.GONE);
                            ivNoDataId.setVisibility(View.VISIBLE);
                        }

                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<Categori_Model> call, Throwable t) {
                dialog.dismiss();
            }

        });
    }

    public void back(View view) {
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Const.review_type.equalsIgnoreCase("0")) {

        } else {
            getdetaildiscountbyid(categori_model.getId());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }
}