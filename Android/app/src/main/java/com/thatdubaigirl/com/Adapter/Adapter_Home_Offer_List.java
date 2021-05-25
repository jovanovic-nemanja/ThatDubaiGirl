package com.thatdubaigirl.com.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thatdubaigirl.com.Activity.DetalisPage;
import com.thatdubaigirl.com.Model.Categori_Model;
import com.thatdubaigirl.com.R;
import com.thatdubaigirl.com.Utils.OnSingleClickListener;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

//public class Adapter_Home_Offer_List extends RecyclerView.Adapter<Adapter_Home_Offer_List.Holder> {
//    private Activity context;
//    private ArrayList<Categori_Model> Product_models;
//    String path_img;
//    ArrayList<Categori_Model> array_b;
//    public Adapter_Home_Offer_List(Activity context, ArrayList<Categori_Model> product_models, String path_img) {
//        this.context = context;
//        this.Product_models = product_models;
//        this.path_img = path_img;
//        this.array_b = new ArrayList<>();
//        this.array_b.addAll(product_models);
//    }
//
//    @NonNull
//    @Override
//    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_offer_list, parent, false);
//        return new Holder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
//
//
//        holder.name.setText("" + Product_models.get(position).getTitle());
//        holder.txtdescription.setText("" + Product_models.get(position).getDescription());
//        holder.txtcatname.setText("" + Product_models.get(position).getLocation());
//        holder.txtvendername.setText("" + Product_models.get(position).getVendorname());
//        Log.e("dsfddfsafd",""+Product_models.get(position).getAvg_marks());
//
//        if (Product_models.get(position).getStatus().equalsIgnoreCase("1")){
//            holder.ivbanner.setVisibility(View.GONE);
//        }else {
//            holder.ivbanner.setVisibility(View.VISIBLE);
//        }
//
//            if (Product_models.get(position).getCount_reviews().equalsIgnoreCase("0")) {
//                holder.headerrate.setVisibility(View.GONE);
//            } else {
//                holder.headerrate.setVisibility(View.VISIBLE);
//                holder.headerrate.setRating(Float.parseFloat("" + Product_models.get(position).getAvg_marks()));
//            }
//
//        Picasso.with(context).load("" + path_img + Product_models.get(position).getDiscount_photo()).error(R.drawable.slider).into(holder.icon);
//        Picasso.with(context).load("" + path_img + Product_models.get(position).getPhoto()).error(R.drawable.slider).into(holder.ivimage);
//
//        holder.card.setOnClickListener(new OnSingleClickListener() {
//            @Override
//            public void onSingleClick(View v) {
//
//
//                context.startActivity(new Intent(context, DetalisPage.class).putExtra("data", Product_models.get(position)).putExtra("path_img", path_img));
//                context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return Product_models.size();
//    }
//
//    private Categori_Model getItem(int pos) {
//        return Product_models.get(pos);
//    }
//
//    class Holder extends RecyclerView.ViewHolder {
//        ImageView icon,ivbanner;
//        CircleImageView ivimage;
//        TextView name, txtdescription, txtvendername, txtcatname;
//        CardView card;
//        RatingBar headerrate;
//        LinearLayout liner;
//
//        public Holder(@NonNull View itemView) {
//            super(itemView);
//            icon = itemView.findViewById(R.id.icon);
//            ivbanner = itemView.findViewById(R.id.ivbanner);
//            name = itemView.findViewById(R.id.name);
//            txtdescription = itemView.findViewById(R.id.txtdescription);
//            txtvendername = itemView.findViewById(R.id.txtvendername);
//            txtcatname = itemView.findViewById(R.id.txtcatname);
//            ivimage = itemView.findViewById(R.id.ivimage);
//            card = itemView.findViewById(R.id.card);
//            headerrate = itemView.findViewById(R.id.headerrate);
//            liner = itemView.findViewById(R.id.liner);
//        }
//    }
//
//    public void filter(String charText) {
//        charText = charText.toLowerCase(Locale.getDefault());
//        Product_models = new ArrayList<>();
//        if (charText.length() == 0) {
//            Product_models.addAll(array_b);
//        } else {
//            for (Categori_Model wp : array_b) {
//                if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
//                    Product_models.add(wp);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }
//}
