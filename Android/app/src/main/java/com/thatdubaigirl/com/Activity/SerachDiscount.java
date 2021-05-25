package com.thatdubaigirl.com.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.thatdubaigirl.com.Adapter.Adapter_Offer_List;
import com.thatdubaigirl.com.Model.Categori_Model;
import com.thatdubaigirl.com.R;
import com.thatdubaigirl.com.Utils.Api;

import java.util.ArrayList;

public class SerachDiscount extends AppCompatActivity {
    RecyclerView rlyofferlist;
    ArrayList<Categori_Model> categori_models;
    EditText etSearchId;
    Adapter_Offer_List adapter_offer_list;
    ProgressDialog dialog;
    String Path_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach_discount);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        rlyofferlist = findViewById(R.id.rlyofferlist);
        etSearchId = findViewById(R.id.etSearchId);


        etSearchId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                try {
                    if (s == null) {
                    } else {
                        adapter_offer_list.filter(s.toString());
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        getDiscountlists();
    }

    /*get getDiscountlists APi*/
    public void getDiscountlists() {

        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.commn_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api loginservice = retrofit.create(Api.class);
        Call<Categori_Model> call = loginservice.getDiscountlists("", "");
        call.enqueue(new Callback<Categori_Model>() {
            @Override
            public void onResponse(Call<Categori_Model> call, Response<Categori_Model> response) {
                if (response.code() == 200) {
                    dialog.dismiss();
                    Log.e("adffadada", "" + response.toString());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        categori_models = response.body().getData();
                        Path_img = response.body().getPath();
                        if (categori_models.size() > 0) {
                            adapter_offer_list = new Adapter_Offer_List(SerachDiscount.this, categori_models, Path_img);
                            rlyofferlist.setAdapter(adapter_offer_list);
                            rlyofferlist.setVisibility(View.VISIBLE);
                            adapter_offer_list.notifyDataSetChanged();
                        } else {
                            rlyofferlist.setVisibility(View.GONE);
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


}