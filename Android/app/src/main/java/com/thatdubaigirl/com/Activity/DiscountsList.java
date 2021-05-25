package com.thatdubaigirl.com.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.thatdubaigirl.com.Model.Categori_Model;
import com.thatdubaigirl.com.R;
import com.thatdubaigirl.com.Utils.Api;
import com.thatdubaigirl.com.Utils.Const;
import java.util.ArrayList;
import java.util.List;

public class DiscountsList extends AppCompatActivity {
    ProgressDialog dialog;
    public static RecyclerView subcatlist, recymainCat;
    ArrayList<Categori_Model> main_cat_list = new ArrayList<>();
//    HomeCategoryHorizontalRecycleAdapter bAdapter;
    private ArrayList<Categori_Model> productlist = new ArrayList<>();
    public static int tab_value;
    String Path_img, Cat_id;
    EditText etSearchId;
//    Adapter_Offer_List adapter_offer_list;
    SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discounts_list);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        subcatlist = findViewById(R.id.subcatlist);
        recymainCat = findViewById(R.id.recymainCat);
        pullToRefresh = findViewById(R.id.pullToRefresh);
        etSearchId = findViewById(R.id.etSearchId);
        Intent intent = getIntent();
        tab_value = intent.getIntExtra("layout", 0);
//        categories(tab_value);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                getDiscountlists(Cat_id);
                pullToRefresh.setRefreshing(false);
            }
        });

        etSearchId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                try {
                    if (s == null) {
                    } else {
//                        adapter_offer_list.filter(s.toString());
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

//    /*get categories APi*/
//    public void categories(final int tab_value) {
//        dialog.show();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(getString(R.string.commn_url))
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        Api loginservice = retrofit.create(Api.class);
//        Call<Categori_Model> call = loginservice.categories();
//        call.enqueue(new Callback<Categori_Model>() {
//            @Override
//            public void onResponse(Call<Categori_Model> call, Response<Categori_Model> response) {
//                if (response.code() == 200) {
//                    dialog.dismiss();
//                    Log.e("adffadada", "" + response.toString());
//                    if (response.body().getStatus().equalsIgnoreCase("success")) {
//
////                        main_cat_list = response.body().getData();
//                        for (int i = -1; i < response.body().getData().size(); i++) {
//                            Categori_Model m2 = new Categori_Model();
//                            if (i == -1) {
//                                m2.setId("0");
//                                m2.setCategory_name("   All   ");
//                            } else {
//                                m2.setCategory_name(response.body().getData().get(i).getCategory_name());
//                                m2.setId(response.body().getData().get(i).getId());
//                            }
//                            main_cat_list.add(m2);
//                        }
//                        recymainCat.setLayoutManager(new LinearLayoutManager(DiscountsList.this, LinearLayoutManager.HORIZONTAL, false));
//                        recymainCat.setItemAnimator(new DefaultItemAnimator());
//                        bAdapter = new HomeCategoryHorizontalRecycleAdapter(DiscountsList.this, main_cat_list);
//                        recymainCat.setAdapter(bAdapter);
//                        bAdapter.notifyDataSetChanged();
//                        recymainCat.scrollToPosition(tab_value);
//                        Cat_id = main_cat_list.get(tab_value).getId();
//                        getDiscountlists11(Cat_id);
//                    } else {
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Categori_Model> call, Throwable t) {
//                dialog.dismiss();
//            }
//
//        });
//    }
//
//    /*get getDiscountlists APi*/
//    public void getDiscountlists(String category_id) {
//        productlist = new ArrayList<>();
//        dialog.show();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(getString(R.string.commn_url))
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        Api loginservice = retrofit.create(Api.class);
//        Call<Categori_Model> call = loginservice.getDiscountlists(category_id, "");
//        call.enqueue(new Callback<Categori_Model>() {
//            @Override
//            public void onResponse(Call<Categori_Model> call, Response<Categori_Model> response) {
//                if (response.code() == 200) {
//                    dialog.dismiss();
//                    Log.e("adffadada", "" + response.toString());
//                    if (response.body().getStatus().equalsIgnoreCase("success")) {
//                        productlist = response.body().getData();
//                        Path_img = response.body().getPath();
//                        if (productlist.size() > 0) {
//                            adapter_offer_list = new Adapter_Offer_List(DiscountsList.this, productlist, Path_img,category_id);
//                            subcatlist.setAdapter(adapter_offer_list);
//                            subcatlist.setVisibility(View.VISIBLE);
//                            adapter_offer_list.notifyDataSetChanged();
//                        } else {
//                            subcatlist.setVisibility(View.GONE);
//                        }
//
//                    } else {
//
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Categori_Model> call, Throwable t) {
//                dialog.dismiss();
//            }
//
//        });
//    }
//
//    public void back(View view) {
//        onBackPressed();
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        overridePendingTransition(R.anim.enter, R.anim.exit);
//    }
//
//    public class HomeCategoryHorizontalRecycleAdapter extends RecyclerView.Adapter<HomeCategoryHorizontalRecycleAdapter.MyViewHolder> {
//        Activity context;
//        private ArrayList<Categori_Model> productlist = new ArrayList<>();
//        //        Adapter_Offer_List adapter_offer_list;
//        private List<Categori_Model> OfferList;
//        int myPos = tab_value;
//        ProgressDialog dialog;
//        String Path_img;
//
//        public class MyViewHolder extends RecyclerView.ViewHolder {
//            TextView title;
//            LinearLayout linear;
//            View view1;
//
//            public MyViewHolder(View view) {
//                super(view);
//                title = (TextView) view.findViewById(R.id.title);
//                linear = (LinearLayout) view.findViewById(R.id.linear);
//                view1 = view.findViewById(R.id.view);
//            }
//        }
//
//        public HomeCategoryHorizontalRecycleAdapter(Activity context, List<Categori_Model> offerList) {
//            this.OfferList = offerList;
//            this.context = context;
//        }
//
//        @Override
//        public HomeCategoryHorizontalRecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View itemView = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_category_horizontal_list, parent, false);
//
//            dialog = new ProgressDialog(context);
//            dialog.setMessage("Loading...");
//            return new HomeCategoryHorizontalRecycleAdapter.MyViewHolder(itemView);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
//            final Categori_Model lists = OfferList.get(position);
//            holder.title.setText(lists.getCategory_name());
//
//            if (myPos == position) {
//                holder.view1.setVisibility(View.VISIBLE);
//
//            } else {
//                holder.view1.setVisibility(View.GONE);
//            }
//
//            holder.linear.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    myPos = position;
//                    notifyDataSetChanged();
//                }
//            });
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    myPos = position;
//                    notifyDataSetChanged();
//
//                }
//            });
//            holder.title.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    myPos = position;
//                    notifyDataSetChanged();
//                    Cat_id = OfferList.get(position).getId();
//                    getDiscountlists(OfferList.get(position).getId());
//                }
//            });
//
//        }
//
//        /*get getDiscountlists APi*/
//        public void getDiscountlists(String category_id) {
//            productlist = Const.product_list;
//            Path_img = Const.path_img;
//            if (productlist.size() > 0) {
//                adapter_offer_list = new Adapter_Offer_List(context, productlist, Path_img,category_id);
//                subcatlist.setAdapter(adapter_offer_list);
//                subcatlist.setVisibility(View.VISIBLE);
//                adapter_offer_list.notifyDataSetChanged();
//            } else {
//                subcatlist.setVisibility(View.GONE);
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            return OfferList.size();
//        }
//
//
//    }
//    /*get getDiscountlists APi*/
//    public void getDiscountlists11(String category_id) {
//        productlist = Const.product_list;
//        Path_img = Const.path_img;
//        if (productlist.size() > 0) {
////            adapter_offer_list = new Adapter_Offer_List(DiscountsList.this, productlist, Path_img,category_id);
////            subcatlist.setAdapter(adapter_offer_list);
////            subcatlist.setVisibility(View.VISIBLE);
////            adapter_offer_list.notifyDataSetChanged();
//        } else {
//            subcatlist.setVisibility(View.GONE);
//        }
//    }
}