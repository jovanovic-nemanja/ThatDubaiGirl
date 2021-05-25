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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thatdubaigirl.com.Activity.DetalisPage;
import com.thatdubaigirl.com.Model.Categori_Model;
import com.thatdubaigirl.com.R;
import com.thatdubaigirl.com.Utils.Api;
import com.thatdubaigirl.com.Utils.Const;
import com.thatdubaigirl.com.Utils.OnSingleClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class AllOfferFragment extends Fragment {
    ProgressDialog dialog;
    public static RecyclerView subcatlist, recymainCat;
    ArrayList<Categori_Model> main_cat_list = new ArrayList<>();
    HomeCategoryHorizontalRecycleAdapter bAdapter;
    private ArrayList<Categori_Model> productlist = new ArrayList<>();
    public static int tab_value;
    String Path_img, Cat_id;
    EditText etSearchId;
    Adapter_Offer_List adapter_offer_list;
    SwipeRefreshLayout pullToRefresh;


    public AllOfferFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_all_offer, container, false);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        subcatlist = v.findViewById(R.id.subcatlist);
        recymainCat = v.findViewById(R.id.recymainCat);
        pullToRefresh = v.findViewById(R.id.pullToRefresh);
        etSearchId = v.findViewById(R.id.etSearchId);
        String tab_value1 = "" + getArguments().getString("layout");
        Log.e("asfasfasfsa", "" + tab_value1);
        tab_value = Integer.parseInt(tab_value1);
        categories(tab_value);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDiscountlists(Cat_id);
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
                        adapter_offer_list.filter(s.toString());
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        return v;
    }


    /*get categories APi*/
    public void categories(final int tab_value) {
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.commn_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api loginservice = retrofit.create(Api.class);
        Call<Categori_Model> call = loginservice.categories();
        call.enqueue(new Callback<Categori_Model>() {
            @Override
            public void onResponse(Call<Categori_Model> call, Response<Categori_Model> response) {
                if (response.code() == 200) {
                    dialog.dismiss();
                    Log.e("adffadada", "" + response.toString());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {

//                        main_cat_list = response.body().getData();
                        for (int i = -1; i < response.body().getData().size(); i++) {
                            Categori_Model m2 = new Categori_Model();
                            if (i == -1) {
                                m2.setId("0");
                                m2.setCategory_name("   All   ");
                            } else {
                                m2.setCategory_name(response.body().getData().get(i).getCategory_name());
                                m2.setId(response.body().getData().get(i).getId());
                            }
                            main_cat_list.add(m2);
                        }
                        recymainCat.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        recymainCat.setItemAnimator(new DefaultItemAnimator());
                        bAdapter = new HomeCategoryHorizontalRecycleAdapter(getActivity(), main_cat_list);
                        recymainCat.setAdapter(bAdapter);
                        bAdapter.notifyDataSetChanged();
                        recymainCat.scrollToPosition(tab_value);
                        Cat_id = main_cat_list.get(tab_value).getId();
                        getDiscountlists11(Cat_id);
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

    /*get getDiscountlists APi*/
    public void getDiscountlists(String category_id) {
        productlist = new ArrayList<>();
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.commn_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api loginservice = retrofit.create(Api.class);
        Call<Categori_Model> call = loginservice.getDiscountlists(category_id, "");
        call.enqueue(new Callback<Categori_Model>() {
            @Override
            public void onResponse(Call<Categori_Model> call, Response<Categori_Model> response) {
                if (response.code() == 200) {
                    dialog.dismiss();
                    Log.e("adffadada", "" + response.toString());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        productlist = response.body().getData();
                        Path_img = response.body().getPath();
                        if (productlist.size() > 0) {
                            adapter_offer_list = new Adapter_Offer_List(getActivity(), productlist, Path_img, category_id);
                            subcatlist.setAdapter(adapter_offer_list);
                            subcatlist.setVisibility(View.VISIBLE);
                            adapter_offer_list.notifyDataSetChanged();
                        } else {
                            subcatlist.setVisibility(View.GONE);
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


    public class HomeCategoryHorizontalRecycleAdapter extends RecyclerView.Adapter<HomeCategoryHorizontalRecycleAdapter.MyViewHolder> {
        Activity context;
        private ArrayList<Categori_Model> productlist = new ArrayList<>();
        //        Adapter_Offer_List adapter_offer_list;
        private List<Categori_Model> OfferList;
        int myPos = tab_value;
        ProgressDialog dialog;
        String Path_img;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            LinearLayout linear;
            View view1;

            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.title);
                linear = (LinearLayout) view.findViewById(R.id.linear);
                view1 = view.findViewById(R.id.view);
            }
        }

        public HomeCategoryHorizontalRecycleAdapter(Activity context, List<Categori_Model> offerList) {
            this.OfferList = offerList;
            this.context = context;
        }

        @Override
        public HomeCategoryHorizontalRecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_category_horizontal_list, parent, false);

            dialog = new ProgressDialog(context);
            dialog.setMessage("Loading...");
            return new HomeCategoryHorizontalRecycleAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull HomeCategoryHorizontalRecycleAdapter.MyViewHolder holder, final int position) {
            final Categori_Model lists = OfferList.get(position);
            holder.title.setText(lists.getCategory_name());

            if (myPos == position) {
                holder.view1.setVisibility(View.VISIBLE);

            } else {
                holder.view1.setVisibility(View.GONE);
            }

            holder.linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myPos = position;
                    notifyDataSetChanged();
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myPos = position;
                    notifyDataSetChanged();

                }
            });
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myPos = position;
                    notifyDataSetChanged();
                    Cat_id = OfferList.get(position).getId();
                    getDiscountlists(OfferList.get(position).getId());
                }
            });

        }

        /*get getDiscountlists APi*/
        public void getDiscountlists(String category_id) {
            productlist = Const.product_list;
            Path_img = Const.path_img;
            if (productlist.size() > 0) {
                adapter_offer_list = new Adapter_Offer_List(context, productlist, Path_img, category_id);
                subcatlist.setAdapter(adapter_offer_list);
                subcatlist.setVisibility(View.VISIBLE);
                adapter_offer_list.notifyDataSetChanged();
            } else {
                subcatlist.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return OfferList.size();
        }
    }

    /*get getDiscountlists APi*/
    public void getDiscountlists11(String category_id) {
        try {
            productlist = Const.product_list;
            Path_img = Const.path_img;
            if (productlist.size() > 0) {
                adapter_offer_list = new Adapter_Offer_List(getActivity(), productlist, Path_img, category_id);
                subcatlist.setAdapter(adapter_offer_list);
                subcatlist.setVisibility(View.VISIBLE);
                adapter_offer_list.notifyDataSetChanged();
            } else {
                subcatlist.setVisibility(View.GONE);
            }
        } catch (Exception e) {

        }

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

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    CategoryFragment fragmentAdminRegistration = new CategoryFragment();
                    fragmentTransaction.replace(R.id.fragment_frame, fragmentAdminRegistration);
                    fragmentTransaction.commit();

                    return true;
                }

                return false;
            }
        });
    }

    public class Adapter_Offer_List extends RecyclerView.Adapter<Adapter_Offer_List.Holder> {
        private Activity context;
        private ArrayList<Categori_Model> Product_models;
        ArrayList<Categori_Model> array_b;
        String path_img, cat_id;

        public Adapter_Offer_List(Activity context, ArrayList<Categori_Model> product_models, String path_img, String cat_id) {
            this.context = context;
            this.Product_models = product_models;
            this.path_img = path_img;
            this.cat_id = cat_id;
            this.array_b = new ArrayList<>();
            this.array_b.addAll(product_models);
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer_list, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final Holder holder, final int position) {

            holder.name.setText("" + Product_models.get(position).getTitle());
            holder.txtdescription.setText("" + Product_models.get(position).getDescription());
            holder.txtcatname.setText("" + Product_models.get(position).getLocation());
            holder.txtvendername.setText("" + Product_models.get(position).getVendorname());
            if (Product_models.get(position).getStatus().equalsIgnoreCase("1")) {
                holder.ivbanner.setVisibility(View.GONE);
            } else {
                holder.ivbanner.setVisibility(View.VISIBLE);
            }
            if (cat_id.equalsIgnoreCase("0")){
                holder.card.setVisibility(View.VISIBLE);
            }else {
                if (cat_id.equalsIgnoreCase(Product_models.get(position).getCategory_id())) {
                    holder.card.setVisibility(View.VISIBLE);
                } else {
                    holder.card.setVisibility(View.GONE);
                }
            }
            try {
                if (Product_models.get(position).getAvg_marks() == null) {
                    holder.headerrate.setVisibility(View.GONE);
                } else {
                    holder.headerrate.setVisibility(View.VISIBLE);
                    holder.headerrate.setRating(Float.parseFloat("" + Product_models.get(position).getAvg_marks()));
                }

                Picasso.with(context).load("" + path_img + Product_models.get(position).getDiscount_photo()).error(R.drawable.slider).into(holder.icon);
                Picasso.with(context).load("" + path_img + Product_models.get(position).getPhoto()).error(R.drawable.slider).into(holder.ivimage);
            } catch (Exception e) {
            }

            holder.card.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    Fragment fr=new DetalisPageFragment();
                    FragmentManager fm=getFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    Bundle bundle = new Bundle();
//                    bundle.putParcelableArrayList("arraylist", (ArrayList<? extends Parcelable>) Product_models);
                    bundle.putString("Type","1");
                    bundle.putString("path_img",path_img);
                    bundle.putString("Title",Product_models.get(position).getTitle());
                    bundle.putString("Vendorname",Product_models.get(position).getVendorname());
                    bundle.putString("Location",Product_models.get(position).getLocation());
                    bundle.putString("Description",Product_models.get(position).getDescription());
                    bundle.putString("Status",Product_models.get(position).getStatus());
                    bundle.putString("Discount_photo",Product_models.get(position).getDiscount_photo());
                    bundle.putString("Photo",Product_models.get(position).getPhoto());
                    bundle.putString("Website_link",Product_models.get(position).getWebsite_link());
                    bundle.putString("Instagram_id",Product_models.get(position).getInstagram_id());
                    bundle.putString("Phone",Product_models.get(position).getPhone());
                    bundle.putString("Id",Product_models.get(position).getId());
                    bundle.putString("layout",""+getArguments().getString("layout"));
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