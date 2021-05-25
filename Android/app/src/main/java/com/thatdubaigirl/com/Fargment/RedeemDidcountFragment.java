package com.thatdubaigirl.com.Fargment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;

import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thatdubaigirl.com.Activity.RedeemDiscount;
import com.thatdubaigirl.com.R;
import com.thatdubaigirl.com.Utils.Const;


public class RedeemDidcountFragment extends Fragment {
    CircleImageView ivprofile;
    ImageView ivimage;
    TextView txtusername, txtmembernumber, txttitle;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    //    Categori_Model categori_model;
    String path_img;

    public RedeemDidcountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_redeem_didcount, container, false);

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ed = sp.edit();

//        categori_model = (Categori_Model) getIntent().getSerializableExtra("data");
        path_img = getArguments().getString("path_img");

        ivprofile = v.findViewById(R.id.ivprofile);
        txtusername = v.findViewById(R.id.txtusername);
        txtmembernumber = v.findViewById(R.id.txtmembernumber);
        txttitle = v.findViewById(R.id.txttitle);
        ivimage = v.findViewById(R.id.ivimage);

        txtusername.setText("" + sp.getString("username", ""));
        txtmembernumber.setText("" + sp.getString("userUniqueId", ""));
        txttitle.setText("" +getArguments().getString("Title"));
        try {
            Picasso.with(getActivity()).load("" + path_img + sp.getString("photo", "")).error(R.drawable.profile).into(ivprofile);
            Picasso.with(getActivity()).load("" + path_img + getArguments().getString("Photo")).error(R.drawable.slider).into(ivimage);
        } catch (Exception e) {
        }

        return v;
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
                    bundle.putString("path_img",path_img);
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