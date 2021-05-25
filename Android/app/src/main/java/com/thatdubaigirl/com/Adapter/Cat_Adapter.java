package com.thatdubaigirl.com.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thatdubaigirl.com.Activity.DiscountsList;
import com.thatdubaigirl.com.Fargment.ProfileFragment;
import com.thatdubaigirl.com.Model.Categori_Model;
import com.thatdubaigirl.com.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

//public class Cat_Adapter extends RecyclerView.Adapter<Cat_Adapter.Holder> {
//
//    private Activity context;
//    private final ArrayList<Categori_Model> gridViewString;
//    String path_img;
//
//    public Cat_Adapter(Activity context, ArrayList<Categori_Model> gridViewString, String path_img) {
//        this.context = context;
//        this.gridViewString = gridViewString;
//        this.path_img = path_img;
//
//    }
//
//    @NonNull
//    @Override
//    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cat_list, parent, false);
//        return new Holder(view);
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
//        holder.txtname.setText("" + gridViewString.get(position).getCategory_name());
//        try {
//            Picasso.with(context).load(path_img + gridViewString.get(position).getCategory_photo()).error(R.drawable.logo).into(holder.ivimage);
//        } catch (Exception e) {
//        }
//        holder.linear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.startActivity(new Intent(context, DiscountsList.class).putExtra("layout", position+1));
//                context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return gridViewString.size();
//    }
//
//    class Holder extends RecyclerView.ViewHolder {
//        TextView txtname;
//        ImageView ivimage;
//        LinearLayout linear;
//
//        public Holder(@NonNull View itemView) {
//            super(itemView);
//            txtname = itemView.findViewById(R.id.name);
//            ivimage = itemView.findViewById(R.id.img);
//            linear = itemView.findViewById(R.id.linear);
//        }
//    }
//}
