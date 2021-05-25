package com.thatdubaigirl.com.Utils;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.thatdubaigirl.com.R;

import androidx.annotation.RequiresApi;

public class RS_Application extends Application {
    public static int pcoutner = 0;
    public static boolean isopen = false;
    public static SharedPreferences sp;
    public static SharedPreferences.Editor ed;


    @Override
    public void onCreate() {
        super.onCreate();

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return true;
            else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        } else
            return false;
        return false;
    }

    public static void noInternet(final Activity activity) {
        Dialog dialog_internet = new Dialog(activity);
        dialog_internet.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_internet.setContentView(R.layout.popup_internet);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog_internet.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog_internet.getWindow().setAttributes(lp);
        dialog_internet.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog_internet.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finishAffinity();
            }
        });
        dialog_internet.show();
    }


}
