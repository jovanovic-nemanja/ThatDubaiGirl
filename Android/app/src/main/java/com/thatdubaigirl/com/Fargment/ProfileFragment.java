package com.thatdubaigirl.com.Fargment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.thatdubaigirl.com.Activity.Login;
import com.thatdubaigirl.com.Activity.MainActivity;
import com.thatdubaigirl.com.Activity.Welcome;
import com.thatdubaigirl.com.Model.Common_Model;
import com.thatdubaigirl.com.R;
import com.thatdubaigirl.com.Utils.Api;
import com.thatdubaigirl.com.Utils.Const;
import com.thatdubaigirl.com.Utils.OnSingleClickListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class ProfileFragment extends Fragment {
    EditText edname, edaddess;
    TextView eddob, txtsave, txtlogout, txtmembernumber;
    ProgressDialog dialog;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    int year1, monthOfYear1, dayOfMonth1;
    String date1, date, dob1, Image_url;
    CircleImageView ivimage;
    public Uri fileUri;
    int staus = 0;
    ByteArrayOutputStream bos;

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ed = sp.edit();
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        Const.image_type = "0";
        txtmembernumber = v.findViewById(R.id.txtmembernumber);
        txtlogout = v.findViewById(R.id.txtlogout);
        txtsave = v.findViewById(R.id.txtsave);
        edname = v.findViewById(R.id.edname);
        eddob = v.findViewById(R.id.eddob);
        edaddess = v.findViewById(R.id.edaddess);
        ivimage = v.findViewById(R.id.ivimage);

        edname.setText("" + sp.getString("username", ""));
        txtmembernumber.setText("Membership Number: " + sp.getString("userUniqueId", ""));
        if (sp.getString("address","").equalsIgnoreCase("null")){
            edaddess.setText("");

        }else {
            edaddess.setText("" + sp.getString("address", ""));
        }
        try {
            String date = sp.getString("birthday", "");
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = spf.parse(date);
            spf = new SimpleDateFormat("dd-MM-yyyy");
            dob1 = spf.format(newDate);
            eddob.setText("" + dob1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ivimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staus = 0;
                galleryIntent();
            }
        });
        Image_url = "" + sp.getString("photo", "");
        try {
            Picasso.with(getActivity()).load("https://tdguae.com/uploads/" + Image_url).error(R.drawable.profile).into(ivimage);

        } catch (Exception e) {
        }

        eddob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                                                                                                                                                          @Override
                                                                                                                                                          public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                                                                                                                                              if (dayOfMonth < 10) {
                                                                                                                                                                  if (monthOfYear < 9) {
                                                                                                                                                                      date = year + "-" + (monthOfYear + 1) + "-" + (dayOfMonth);
                                                                                                                                                                      date1 = " 0" + (dayOfMonth) + "-" + "0" + (monthOfYear + 1) + "-" + year;
                                                                                                                                                                      eddob.setText(date1);
                                                                                                                                                                      year1 = year;
                                                                                                                                                                      monthOfYear1 = (monthOfYear + 1);
                                                                                                                                                                      dayOfMonth1 = dayOfMonth;
                                                                                                                                                                  } else {
                                                                                                                                                                      date = year + "-" + (monthOfYear + 1) + "-" + (dayOfMonth);
                                                                                                                                                                      date1 = "0" + (dayOfMonth) + "-" + (monthOfYear + 1) + "-" + year;
                                                                                                                                                                      eddob.setText(date1);
                                                                                                                                                                      year1 = year;
                                                                                                                                                                      monthOfYear1 = (monthOfYear + 1);
                                                                                                                                                                      dayOfMonth1 = dayOfMonth;
                                                                                                                                                                  }
                                                                                                                                                              } else if (monthOfYear < 9) {
                                                                                                                                                                  if (dayOfMonth < 10) {
                                                                                                                                                                      date = year + "-" + (monthOfYear + 1) + "-" + (dayOfMonth);
                                                                                                                                                                      date1 = " 0" + (dayOfMonth) + "-" + "0" + (monthOfYear + 1) + "-" + year;
                                                                                                                                                                      eddob.setText(date1);
                                                                                                                                                                      year1 = year;
                                                                                                                                                                      monthOfYear1 = (monthOfYear + 1);
                                                                                                                                                                      dayOfMonth1 = dayOfMonth;
                                                                                                                                                                  } else {
                                                                                                                                                                      date = year + "-" + (monthOfYear + 1) + "-" + (dayOfMonth);
                                                                                                                                                                      date1 = (dayOfMonth) + "-" + "0" + (monthOfYear + 1) + "-" + year;
                                                                                                                                                                      eddob.setText(date1);
                                                                                                                                                                      year1 = year;
                                                                                                                                                                      monthOfYear1 = (monthOfYear + 1);
                                                                                                                                                                      dayOfMonth1 = dayOfMonth;
                                                                                                                                                                  }
                                                                                                                                                              } else {
                                                                                                                                                                  date = year + "-" + (monthOfYear + 1) + "-" + (dayOfMonth);
                                                                                                                                                                  date1 = (dayOfMonth) + "-" + (monthOfYear + 1) + "-" + year;
                                                                                                                                                                  eddob.setText(date1);
                                                                                                                                                                  year1 = year;
                                                                                                                                                                  monthOfYear1 = (monthOfYear + 1);
                                                                                                                                                                  dayOfMonth1 = dayOfMonth;
                                                                                                                                                              }
                                                                                                                                                          }
                                                                                                                                                      },

                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setMaxDate(now);
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }

        });

        PushDownAnim.setPushDownAnimTo(txtlogout).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                ed.clear().commit();
                startActivity(new Intent(getActivity(), Welcome.class));
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        PushDownAnim.setPushDownAnimTo(txtsave).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (edname.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "Please Enter Name", Toast.LENGTH_SHORT).show();
                } else if (eddob.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "Please Enter DOB", Toast.LENGTH_SHORT).show();
                } else if (edaddess.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "Please Enter Address", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String date = eddob.getText().toString();
                        SimpleDateFormat spf = new SimpleDateFormat("dd-MM-yyyy");
                        Date newDate = spf.parse(date);
                        spf = new SimpleDateFormat("yyyy-MM-dd");
                        dob1 = spf.format(newDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    updateAccount();
                }
            }
        });
        isStoragePermissionGranted();
        return v;
    }

    private void galleryIntent() {
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(getActivity());
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == -1) {
                if (staus == 0) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    fileUri = result.getUri();
                    ivimage.setImageURI(fileUri);

                    ivimage.setPadding(0, 0, 0, 0);
                    Image_url = "" + fileUri.getLastPathSegment();

                    File featured_image = new File(fileUri.getPath());
                    Bitmap bmp = BitmapFactory.decodeFile(featured_image.getAbsolutePath());
                    bos = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 30, bos);
                    Const.image_type = "1";
                } else {
                }
            }
        }
    }

    /*get updateAccount APi*/
    public void updateAccount() {
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.commn_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api loginservice = retrofit.create(Api.class);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (Const.image_type.equalsIgnoreCase("0")) {
            builder.addFormDataPart("email", sp.getString("email", ""))
                    .addFormDataPart("birthday", dob1)
                    .addFormDataPart("username", edname.getText().toString())
                    .addFormDataPart("address", edaddess.getText().toString());
//                    .addFormDataPart("photo", Image_url, RequestBody.create(MultipartBody.FORM, bos.toByteArray()));

        } else {
            builder.addFormDataPart("email", sp.getString("email", ""))
                    .addFormDataPart("birthday", dob1)
                    .addFormDataPart("username", edname.getText().toString())
                    .addFormDataPart("address", edaddess.getText().toString())
                    .addFormDataPart("photo", Image_url, RequestBody.create(MultipartBody.FORM, bos.toByteArray()));

        }
        RequestBody requestBody = builder.build();
        Call<Common_Model> call = loginservice.updateAccount(requestBody);
        call.enqueue(new Callback<Common_Model>() {
            @Override
            public void onResponse(Call<Common_Model> call, Response<Common_Model> response) {
                if (response.code() == 200) {
                    dialog.dismiss();
                    Log.e("adffadada", "" + response.toString());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        ed.putString("user_id", "" + response.body().getData().getId());
                        ed.putString("username", "" + response.body().getData().getUsername());
                        ed.putString("userUniqueId", "" + response.body().getData().getUserUniqueId());
                        ed.putString("email", "" + response.body().getData().getEmail());
                        ed.putString("photo", "" + response.body().getData().getPhoto());
                        ed.putString("address", "" + response.body().getData().getAddress());
                        ed.putString("birthday", "" + response.body().getData().getBirthday());
                        ed.commit();
                        Toast.makeText(getActivity(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

}