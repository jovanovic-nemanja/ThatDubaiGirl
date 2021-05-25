package com.thatdubaigirl.com.Fargment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.thatdubaigirl.com.Activity.WriteReview;
import com.thatdubaigirl.com.Model.Common_Model;
import com.thatdubaigirl.com.R;
import com.thatdubaigirl.com.Utils.Api;
import com.thatdubaigirl.com.Utils.Const;
import com.thatdubaigirl.com.Utils.OnSingleClickListener;


public class WriteReviewFragment extends Fragment {
    EditText edReview;
    RatingBar comment_rating;
    TextView txtcancel, txtsubmit;
    ProgressDialog dialog;
    SharedPreferences sp;
    SharedPreferences.Editor ed;

    public WriteReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_write_review, container, false);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ed = sp.edit();
//        categori_model = (Categori_Model) getIntent().getSerializableExtra("data");
        comment_rating = v.findViewById(R.id.comment_rating);
        edReview = v.findViewById(R.id.edReview);
        txtsubmit = v.findViewById(R.id.txtsubmit);


        txtsubmit.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (edReview.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "Please enter review", Toast.LENGTH_SHORT).show();
                } else {
                    putReviewsbyAPI(getArguments().getString("Id"), String.valueOf(comment_rating.getRating()));
                }

            }
        });
        return v;
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
                        Toast.makeText(getActivity(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        getView().setFocusableInTouchMode(true);
                        getView().requestFocus();
                        getView().setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                                    Fragment fr=new DetalisPageFragment();
                                    FragmentManager fm=getFragmentManager();
                                    FragmentTransaction ft=fm.beginTransaction();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("Type",getArguments().getString("Type"));
                                    bundle.putString("path_img",getArguments().getString("path_img"));
                                    bundle.putString("Title",getArguments().getString("Title"));
                                    bundle.putString("Vendorname",getArguments().getString("Vendorname"));
                                    bundle.putString("Location",getArguments().getString("Location"));
                                    bundle.putString("Description",getArguments().getString("Description"));
                                    bundle.putString("Status",getArguments().getString("Status"));
                                    bundle.putString("Discount_photo",getArguments().getString("Discount_photo"));
                                    bundle.putString("Photo",getArguments().getString("Photo"));
                                    bundle.putString("Website_link",getArguments().getString("Website_link"));
                                    bundle.putString("Instagram_id",getArguments().getString("Instagram_id"));
                                    bundle.putString("Phone",getArguments().getString("Phone"));
                                    bundle.putString("Id",getArguments().getString("Id"));
                                    bundle.putString("layout",""+getArguments().getString("layout"));
                                    fr.setArguments(bundle);
                                    ft.replace(R.id.fragment_frame, fr);
                                    ft.commit();
                                }
                                return false;
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
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
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Fragment fr=new DetalisPageFragment();
                    FragmentManager fm=getFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString("Type",getArguments().getString("Type"));
                    bundle.putString("path_img",getArguments().getString("path_img"));
                    bundle.putString("Title",getArguments().getString("Title"));
                    bundle.putString("Vendorname",getArguments().getString("Vendorname"));
                    bundle.putString("Location",getArguments().getString("Location"));
                    bundle.putString("Description",getArguments().getString("Description"));
                    bundle.putString("Status",getArguments().getString("Status"));
                    bundle.putString("Discount_photo",getArguments().getString("Discount_photo"));
                    bundle.putString("Photo",getArguments().getString("Photo"));
                    bundle.putString("Website_link",getArguments().getString("Website_link"));
                    bundle.putString("Instagram_id",getArguments().getString("Instagram_id"));
                    bundle.putString("Phone",getArguments().getString("Phone"));
                    bundle.putString("Id",getArguments().getString("Id"));
                    bundle.putString("layout",""+getArguments().getString("layout"));
                    fr.setArguments(bundle);
                    ft.replace(R.id.fragment_frame, fr);
                    ft.commit();
                }
                return false;
            }
        });
    }


}