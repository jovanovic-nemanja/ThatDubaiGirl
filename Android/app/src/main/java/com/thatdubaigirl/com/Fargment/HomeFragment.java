package com.thatdubaigirl.com.Fargment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.thatdubaigirl.com.Activity.DetalisPage;
import com.thatdubaigirl.com.Activity.SerachDiscount;
import com.thatdubaigirl.com.Model.Categori_Model;
import com.thatdubaigirl.com.R;
import com.thatdubaigirl.com.Utils.Api;
import com.thatdubaigirl.com.Utils.Const;
import com.thatdubaigirl.com.Utils.OnSingleClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class HomeFragment extends Fragment {
    RecyclerView rlyofferlist;
    ArrayList<Categori_Model> productlist;
    ArrayList<Categori_Model> productlist1;
    String Path_img;
    Adapter_Home_Offer_List adapter_home_offer_list;
    Adapter_Home_Offer_List1 adapter_home_offer_list1;
    ProgressDialog dialog;
    SwipeRefreshLayout pullToRefresh;
    EditText etSearchId;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        rlyofferlist = v.findViewById(R.id.rlyofferlist);
        pullToRefresh = v.findViewById(R.id.pullToRefresh);
        etSearchId = v.findViewById(R.id.etSearchId);

        if (Const.Home_page.equalsIgnoreCase("0")) {
            getDiscountlists();
        } else {
            if (Const.home_list.size() > 0) {
                adapter_home_offer_list = new Adapter_Home_Offer_List(getActivity(), Const.home_list1, Const.path_img);
                adapter_home_offer_list1 = new Adapter_Home_Offer_List1(getActivity(), Const.home_list, Const.path_img);
                rlyofferlist.setAdapter(adapter_home_offer_list);
                adapter_home_offer_list.notifyDataSetChanged();
                rlyofferlist.setVisibility(View.VISIBLE);
            } else {
                rlyofferlist.setVisibility(View.GONE);
            }
        }

        etSearchId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                try {
                    if (s == null) {

                    } else {
                        if (etSearchId.getText().toString().length()==0){
                            rlyofferlist.setAdapter(adapter_home_offer_list);
                            adapter_home_offer_list.notifyDataSetChanged();
                        }else {
                            adapter_home_offer_list1.filter(s.toString());
                            rlyofferlist.setAdapter(adapter_home_offer_list1);
                            adapter_home_offer_list1.notifyDataSetChanged();
                        }

                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
//        etSearchId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), SerachDiscount.class));
//            }
//        });

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDiscountlists(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });
        return v;
    }

    /*get getDiscountlists APi*/
    public void getDiscountlists() {
        productlist = new ArrayList<>();
        productlist1 = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams requestParams = new RequestParams();
        requestParams.put("category_id", "");
        requestParams.put("vendor_name", "");
        client.get(getString(R.string.commn_url) + "getDiscountlists?", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                dialog.dismiss();
                try {
                    Log.e("datatattatat", "" + response.toString());
                    if (response.getString("status").equalsIgnoreCase("success")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject j = jsonArray.getJSONObject(i);
                            Categori_Model m = new Categori_Model();
                            m.setId(j.getString("id"));
                            m.setTitle(j.getString("title"));
                            m.setDescription(j.getString("description"));
                            m.setCategory_id(j.getString("category_id"));
                            m.setDiscount_photo(j.getString("discount_photo"));
                            m.setVendor_id(j.getString("vendor_id"));
                            m.setCoupon(j.getString("coupon"));
                            m.setSign_date(j.getString("sign_date"));
                            m.setStatus(j.getString("status"));
                            m.setVendorname(j.getString("vendorname"));
                            m.setLocation(j.getString("location"));
                            m.setPhoto(j.getString("photo"));
                            m.setEmail(j.getString("email"));
                            m.setPhone(j.getString("phone"));
                            m.setInstagram_id(j.getString("instagram_id"));
                            m.setWebsite_link(j.getString("website_link"));
                            m.setCategory_name(j.getString("category_name"));
                            m.setAvg_marks(j.getString("avg_marks"));
                            m.setCount_reviews(j.getString("count_reviews"));
                            productlist.add(m);
                            if (j.getString("status").equalsIgnoreCase("2")) {
                                productlist1.add(m);
                            } else {
                                productlist1.remove(m);
                            }
                        }
                        Path_img = response.getString("path");
                        Log.e("safgsfgsahsa", "" + productlist.size() + "   " + Path_img);
                        Const.Home_page = "1";
                        Const.home_list = productlist;
                        Const.home_list1 = productlist1;
                        Const.path_img = Path_img;

                        if (productlist1.size() > 0) {
                            adapter_home_offer_list = new Adapter_Home_Offer_List(getActivity(), productlist1, Path_img);
                            rlyofferlist.setAdapter(adapter_home_offer_list);
                            adapter_home_offer_list1 = new Adapter_Home_Offer_List1(getActivity(), productlist, Const.path_img);

                            adapter_home_offer_list1.notifyDataSetChanged();
                            adapter_home_offer_list.notifyDataSetChanged();
                            rlyofferlist.setVisibility(View.VISIBLE);
                        } else {
                            rlyofferlist.setVisibility(View.GONE);
                        }

                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class Adapter_Home_Offer_List extends RecyclerView.Adapter<Adapter_Home_Offer_List.Holder> {
        private Activity context;
        ArrayList<Categori_Model> Product_models;
        String path_img;
        ArrayList<Categori_Model> array_b;

        public Adapter_Home_Offer_List(Activity context, ArrayList<Categori_Model> product_models, String path_img) {
            this.context = context;
            this.Product_models = product_models;
            this.path_img = path_img;
            this.array_b = new ArrayList<>();
            this.array_b.addAll(product_models);
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_offer_list, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final Holder holder, final int position) {


            holder.name.setText("" + Product_models.get(position).getTitle());
            holder.txtdescription.setText("" + Product_models.get(position).getDescription());
            holder.txtcatname.setText("" + Product_models.get(position).getLocation());
            holder.txtvendername.setText("" + Product_models.get(position).getVendorname());
            Log.e("dsfddfsafd", "" + Product_models.get(position).getAvg_marks());

            if (Product_models.get(position).getStatus().equalsIgnoreCase("1")) {
                holder.ivbanner.setVisibility(View.GONE);
            } else {
                holder.ivbanner.setVisibility(View.VISIBLE);
            }

            if (Product_models.get(position).getStatus().equalsIgnoreCase("2")) {
                holder.card.setVisibility(View.VISIBLE);
            } else {
                holder.card.setVisibility(View.GONE);
            }

            if (Product_models.get(position).getCount_reviews().equalsIgnoreCase("0")) {
                holder.headerrate.setVisibility(View.GONE);
            } else {
                holder.headerrate.setVisibility(View.VISIBLE);
                holder.headerrate.setRating(Float.parseFloat("" + Product_models.get(position).getAvg_marks()));
            }

            Picasso.with(context).load("" + path_img + Product_models.get(position).getDiscount_photo()).error(R.drawable.slider).into(holder.icon);
            Picasso.with(context).load("" + path_img + Product_models.get(position).getPhoto()).error(R.drawable.slider).into(holder.ivimage);

            holder.card.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    Fragment fr = new DetalisPageFragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Bundle bundle = new Bundle();
//                    bundle.putParcelableArrayList("arraylist", (ArrayList<? extends Parcelable>) Product_models);
                    bundle.putString("Type", "0");
                    bundle.putString("path_img", path_img);
                    bundle.putString("Title", Product_models.get(position).getTitle());
                    bundle.putString("Vendorname", Product_models.get(position).getVendorname());
                    bundle.putString("Location", Product_models.get(position).getLocation());
                    bundle.putString("Description", Product_models.get(position).getDescription());
                    bundle.putString("Status", Product_models.get(position).getStatus());
                    bundle.putString("Discount_photo", Product_models.get(position).getDiscount_photo());
                    bundle.putString("Photo", Product_models.get(position).getPhoto());
                    bundle.putString("Website_link", Product_models.get(position).getWebsite_link());
                    bundle.putString("Instagram_id", Product_models.get(position).getInstagram_id());
                    bundle.putString("Phone", Product_models.get(position).getPhone());
                    bundle.putString("Id", Product_models.get(position).getId());
                    fr.setArguments(bundle);
                    ft.replace(R.id.fragment_frame, fr);
                    ft.commit();

//                    context.startActivity(new Intent(context, DetalisPage.class).putExtra("data", Product_models.get(position)).putExtra("path_img", path_img));
//                    context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
        }

        @Override
        public int getItemCount() {
            return Product_models.size();
        }

        private Categori_Model getItem(int pos) {
            return Product_models.get(pos);
        }

        class Holder extends RecyclerView.ViewHolder {
            ImageView icon, ivbanner;
            CircleImageView ivimage;
            TextView name, txtdescription, txtvendername, txtcatname;
            CardView card;
            RatingBar headerrate;
            LinearLayout liner;

            public Holder(@NonNull View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.icon);
                ivbanner = itemView.findViewById(R.id.ivbanner);
                name = itemView.findViewById(R.id.name);
                txtdescription = itemView.findViewById(R.id.txtdescription);
                txtvendername = itemView.findViewById(R.id.txtvendername);
                txtcatname = itemView.findViewById(R.id.txtcatname);
                ivimage = itemView.findViewById(R.id.ivimage);
                card = itemView.findViewById(R.id.card);
                headerrate = itemView.findViewById(R.id.headerrate);
                liner = itemView.findViewById(R.id.liner);
            }
        }

        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            Product_models = new ArrayList<>();
            if (charText.length() == 0) {
                Product_models.addAll(array_b);
            } else {
                for (Categori_Model wp : array_b) {
                    if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                        Product_models.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }
    public class Adapter_Home_Offer_List1 extends RecyclerView.Adapter<Adapter_Home_Offer_List1.Holder> {
        private Activity context;
        ArrayList<Categori_Model> Product_models;
        String path_img;
        ArrayList<Categori_Model> array_b;

        public Adapter_Home_Offer_List1(Activity context, ArrayList<Categori_Model> product_models, String path_img) {
            this.context = context;
            this.Product_models = product_models;
            this.path_img = path_img;
            this.array_b = new ArrayList<>();
            this.array_b.addAll(product_models);
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_offer_list, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final Holder holder, final int position) {

            holder.name.setText("" + Product_models.get(position).getTitle());
            holder.txtdescription.setText("" + Product_models.get(position).getDescription());
            holder.txtcatname.setText("" + Product_models.get(position).getLocation());
            holder.txtvendername.setText("" + Product_models.get(position).getVendorname());
            Log.e("dsfddfsafd", "" + Product_models.get(position).getAvg_marks());

            if (Product_models.get(position).getStatus().equalsIgnoreCase("1")) {
                holder.ivbanner.setVisibility(View.GONE);
            } else {
                holder.ivbanner.setVisibility(View.VISIBLE);
            }

            if (Product_models.get(position).getCount_reviews().equalsIgnoreCase("0")) {
                holder.headerrate.setVisibility(View.GONE);
            } else {
                holder.headerrate.setVisibility(View.VISIBLE);
                holder.headerrate.setRating(Float.parseFloat("" + Product_models.get(position).getAvg_marks()));
            }

            Picasso.with(context).load("" + path_img + Product_models.get(position).getDiscount_photo()).error(R.drawable.slider).into(holder.icon);
            Picasso.with(context).load("" + path_img + Product_models.get(position).getPhoto()).error(R.drawable.slider).into(holder.ivimage);

            holder.card.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    Fragment fr = new DetalisPageFragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Bundle bundle = new Bundle();
//                    bundle.putParcelableArrayList("arraylist", (ArrayList<? extends Parcelable>) Product_models);
                    bundle.putString("Type", "0");
                    bundle.putString("path_img", path_img);
                    bundle.putString("Title", Product_models.get(position).getTitle());
                    bundle.putString("Vendorname", Product_models.get(position).getVendorname());
                    bundle.putString("Location", Product_models.get(position).getLocation());
                    bundle.putString("Description", Product_models.get(position).getDescription());
                    bundle.putString("Status", Product_models.get(position).getStatus());
                    bundle.putString("Discount_photo", Product_models.get(position).getDiscount_photo());
                    bundle.putString("Photo", Product_models.get(position).getPhoto());
                    bundle.putString("Website_link", Product_models.get(position).getWebsite_link());
                    bundle.putString("Instagram_id", Product_models.get(position).getInstagram_id());
                    bundle.putString("Phone", Product_models.get(position).getPhone());
                    bundle.putString("Id", Product_models.get(position).getId());
                    fr.setArguments(bundle);
                    ft.replace(R.id.fragment_frame, fr);
                    ft.commit();

//                    context.startActivity(new Intent(context, DetalisPage.class).putExtra("data", Product_models.get(position)).putExtra("path_img", path_img));
//                    context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
        }

        @Override
        public int getItemCount() {
            return Product_models.size();
        }

        private Categori_Model getItem(int pos) {
            return Product_models.get(pos);
        }

        class Holder extends RecyclerView.ViewHolder {
            ImageView icon, ivbanner;
            CircleImageView ivimage;
            TextView name, txtdescription, txtvendername, txtcatname;
            CardView card;
            RatingBar headerrate;
            LinearLayout liner;

            public Holder(@NonNull View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.icon);
                ivbanner = itemView.findViewById(R.id.ivbanner);
                name = itemView.findViewById(R.id.name);
                txtdescription = itemView.findViewById(R.id.txtdescription);
                txtvendername = itemView.findViewById(R.id.txtvendername);
                txtcatname = itemView.findViewById(R.id.txtcatname);
                ivimage = itemView.findViewById(R.id.ivimage);
                card = itemView.findViewById(R.id.card);
                headerrate = itemView.findViewById(R.id.headerrate);
                liner = itemView.findViewById(R.id.liner);
            }
        }

        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            Product_models = new ArrayList<>();
            if (charText.length() == 0) {
                Product_models.addAll(array_b);
            } else {
                for (Categori_Model wp : array_b) {
                    if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                        Product_models.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }


}