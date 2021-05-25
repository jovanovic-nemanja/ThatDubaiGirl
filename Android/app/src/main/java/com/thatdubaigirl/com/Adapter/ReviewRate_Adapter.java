package com.thatdubaigirl.com.Adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thatdubaigirl.com.Model.Categori_Model;
import com.thatdubaigirl.com.R;
import com.thatdubaigirl.com.Utils.Const;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewRate_Adapter extends RecyclerView.Adapter<ReviewRate_Adapter.Holder> {

    private Activity context;
    ArrayList<Categori_Model> gridViewString;
    String path_img;
    SharedPreferences sp;
    SharedPreferences.Editor ed;

    public ReviewRate_Adapter(Activity context, ArrayList<Categori_Model> gridViewString, String path_img) {
        this.context = context;
        this.gridViewString = gridViewString;
        this.path_img = path_img;
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        ed = sp.edit();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_revie_rate, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        holder.review.setText("" + gridViewString.get(position).getComments());
        try {
            if (gridViewString.get(position).getPutter().equalsIgnoreCase(sp.getString("user_id", ""))) {
                Const.review_type_a = "1";
            } else {

            }
            holder.headerrate.setRating(Float.parseFloat(gridViewString.get(position).getMark()));
            Picasso.with(context).load("" + path_img + gridViewString.get(position).getPhoto()).error(R.drawable.logo).into(holder.ivimage);
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return gridViewString.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView review;
        CircleImageView ivimage;
        RatingBar headerrate;

        public Holder(@NonNull View itemView) {
            super(itemView);
            review = itemView.findViewById(R.id.review);
            ivimage = itemView.findViewById(R.id.image);
            headerrate = itemView.findViewById(R.id.headerrate);
        }
    }

}