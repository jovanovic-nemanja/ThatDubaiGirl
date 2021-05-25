package com.thatdubaigirl.com.Fargment;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.thatdubaigirl.com.Activity.DetalisPage;
import com.thatdubaigirl.com.Activity.RedeemDiscount;
import com.thatdubaigirl.com.Activity.WriteReview;
import com.thatdubaigirl.com.Adapter.ReviewRate_Adapter;
import com.thatdubaigirl.com.Model.Categori_Model;
import com.thatdubaigirl.com.R;
import com.thatdubaigirl.com.Utils.Api;
import com.thatdubaigirl.com.Utils.Const;
import com.thatdubaigirl.com.Utils.OnSingleClickListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class DetalisPageFragment extends Fragment {
    TextView name, txtvendername, txtcatname, txtOverView, txtReviews, txtbtn, ivNoDataId,txtdescription;
    ImageView icon, ivcall, ivinsta, ivcrome, ivbanner;
    CircleImageView ivimage;
    View view, view1;
    //    Categori_Model categori_model;
    String path_img;
    LinearLayout lloverview, llreview;
    ProgressDialog dialog;
    private ArrayList<Categori_Model> rate_list = new ArrayList<>();
    RecyclerView recyclerviewreview;


    public DetalisPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detalis_page, container, false);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        Const.review_type = "0";
        Const.review_type_a = "0";

        path_img = "" + getArguments().getString("path_img");

//        categori_model  =  getArguments().getParcelableArrayList("arraylist");
        name = v.findViewById(R.id.name);
        icon = v.findViewById(R.id.icon);
        ivimage = v.findViewById(R.id.ivimage);
        txtvendername = v.findViewById(R.id.txtvendername);
        txtcatname = v.findViewById(R.id.txtcatname);
        txtOverView = v.findViewById(R.id.txtOverView);
        txtReviews = v.findViewById(R.id.txtReviews);
        txtdescription = v.findViewById(R.id.txtdescription);
        view = v.findViewById(R.id.view);
        view1 = v.findViewById(R.id.view1);
        txtbtn = v.findViewById(R.id.txtbtn);
        ivbanner = v.findViewById(R.id.ivbanner);

        ivcall = v.findViewById(R.id.ivcall);
        ivinsta = v.findViewById(R.id.ivinsta);
        ivcrome = v.findViewById(R.id.ivcrome);

        lloverview = v.findViewById(R.id.lloverview);
        llreview = v.findViewById(R.id.llreview);

        ivNoDataId = v.findViewById(R.id.ivNoDataId);

        recyclerviewreview = v.findViewById(R.id.recyclerviewreview);

        name.setText("" + getArguments().getString("Title"));
        txtvendername.setText("" + getArguments().getString("Vendorname"));
        txtcatname.setText("" + getArguments().getString("Location"));
        txtdescription.setText("" + getArguments().getString("Description"));

//        String htmlAsString = "" + getArguments().getString("Description");
//        txtdescription.loadDataWithBaseURL(null, htmlAsString, "text/html", "utf-8", null);


        if (getArguments().getString("Status").equalsIgnoreCase("2")) {
            ivbanner.setVisibility(View.VISIBLE);
        } else {
            ivbanner.setVisibility(View.GONE);
        }
        try {
            Picasso.with(getActivity()).load("" + path_img + getArguments().getString("Discount_photo")).error(R.drawable.slider).into(icon);
            Picasso.with(getActivity()).load("" + path_img + getArguments().getString("Photo")).error(R.drawable.slider).into(ivimage);
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
                view.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.bule));
                view1.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.black));
                txtbtn.setText("Redeem Discount");
            }
        });
        txtReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lloverview.setVisibility(View.GONE);
                llreview.setVisibility(View.VISIBLE);
                view.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.black));
                view1.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.bule));
                txtbtn.setText("Write a Review");
            }
        });
        PushDownAnim.setPushDownAnimTo(ivcrome).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                try {
                    String url = "" + getArguments().getString("Website_link");
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "No application can handle this request."
                            + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        PushDownAnim.setPushDownAnimTo(ivinsta).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                try {
                    String url = "" + getArguments().getString("Instagram_id");
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "No application can handle this request."
                            + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        PushDownAnim.setPushDownAnimTo(ivcall).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                String call_no = "" + getArguments().getString("Phone");
                Intent callIntent = new Intent(Intent.ACTION_VIEW);
                callIntent.setData(Uri.parse("tel:" + call_no));
                startActivity(callIntent);
            }
        });
        PushDownAnim.setPushDownAnimTo(txtbtn).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (txtbtn.getText().toString().equalsIgnoreCase("Redeem Discount")) {
//                    startActivity(new Intent(getActivity(), RedeemDiscount.class)
//                            .putExtra("Title",""+ getArguments().getString("Title"))
//                            .putExtra("Photo", ""+getArguments().getString("Photo"))
//                            .putExtra("path_img", path_img));
//                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    Fragment fr = new RedeemDidcountFragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString("Type", getArguments().getString("Type"));
                    bundle.putString("path_img", path_img);
                    bundle.putString("Title", getArguments().getString("Title"));
                    bundle.putString("Vendorname", getArguments().getString("Vendorname"));
                    bundle.putString("Location", getArguments().getString("Location"));
                    bundle.putString("Description", getArguments().getString("Description"));
                    bundle.putString("Status", getArguments().getString("Status"));
                    bundle.putString("Discount_photo", getArguments().getString("Discount_photo"));
                    bundle.putString("Photo", getArguments().getString("Photo"));
                    bundle.putString("Website_link", getArguments().getString("Website_link"));
                    bundle.putString("Instagram_id", getArguments().getString("Instagram_id"));
                    bundle.putString("Phone", getArguments().getString("Phone"));
                    bundle.putString("Id", getArguments().getString("Id"));
                    bundle.putString("layout", "" + getArguments().getString("layout"));
                    fr.setArguments(bundle);
                    ft.replace(R.id.fragment_frame, fr);
                    ft.commit();

                } else {
                    if (Const.review_type_a.equalsIgnoreCase("0")) {
//                        startActivity(new Intent(getActivity(), WriteReview.class)
//                                .putExtra("Id", getArguments().getString("Id"))
//                                .putExtra("path_img", path_img));
//                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//
                        Fragment fr = new WriteReviewFragment();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("Type", getArguments().getString("Type"));
                        bundle.putString("path_img", path_img);
                        bundle.putString("Title", getArguments().getString("Title"));
                        bundle.putString("Vendorname", getArguments().getString("Vendorname"));
                        bundle.putString("Location", getArguments().getString("Location"));
                        bundle.putString("Description", getArguments().getString("Description"));
                        bundle.putString("Status", getArguments().getString("Status"));
                        bundle.putString("Discount_photo", getArguments().getString("Discount_photo"));
                        bundle.putString("Photo", getArguments().getString("Photo"));
                        bundle.putString("Website_link", getArguments().getString("Website_link"));
                        bundle.putString("Instagram_id", getArguments().getString("Instagram_id"));
                        bundle.putString("Phone", getArguments().getString("Phone"));
                        bundle.putString("Id", getArguments().getString("Id"));
                        bundle.putString("layout", "" + getArguments().getString("layout"));
                        fr.setArguments(bundle);
                        ft.replace(R.id.fragment_frame, fr);
                        ft.commit();


                    } else {
                        Toast.makeText(getActivity(), "You have already left a review.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        getdetaildiscountbyid("" + getArguments().getString("Id"));
        return v;
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
                            ReviewRate_Adapter adapter_home_offer_list = new ReviewRate_Adapter(getActivity(), rate_list, path_img);
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

    @Override
    public void onResume() {
        super.onResume();
        if (Const.review_type.equalsIgnoreCase("0")) {

        } else {
            getdetaildiscountbyid("" + getArguments().getString("Id"));
        }
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (getArguments().getString("Type").equalsIgnoreCase("0")) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        HomeFragment fragmentAdminRegistration = new HomeFragment();
                        fragmentTransaction.replace(R.id.fragment_frame, fragmentAdminRegistration);
                        fragmentTransaction.commit();
                        return true;
                    } else {
                        Fragment fr = new AllOfferFragment();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("layout", "" + getArguments().getString("layout"));
                        fr.setArguments(bundle);
                        ft.replace(R.id.fragment_frame, fr);
                        ft.commit();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void back(View view) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment fragmentAdminRegistration = new HomeFragment();
        fragmentTransaction.replace(R.id.fragment_frame, fragmentAdminRegistration);
        fragmentTransaction.commit();
    }
}