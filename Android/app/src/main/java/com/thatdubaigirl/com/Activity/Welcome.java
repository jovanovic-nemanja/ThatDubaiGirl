package com.thatdubaigirl.com.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.thatdubaigirl.com.Model.Common_Model;
import com.thatdubaigirl.com.R;
import com.thatdubaigirl.com.Utils.Api;
import com.thatdubaigirl.com.Utils.OnSingleClickListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.thatdubaigirl.com.Utils.RS_Application.ed;
import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class Welcome extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    private static final int RC_SIGN_IN = 8001;
    GoogleSignInAccount acct = null;
    GoogleSignInClient mGoogleSignInClient;
    protected String emailname, emial_id;
    protected Uri profile;
    GoogleSignInAccount mGoogleSignInAccount = null;
    private GoogleApiClient mGoogleApiClient;
    protected String mUserIdToken;
    ProgressDialog dialog;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        ed = sp.edit();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        PushDownAnim.setPushDownAnimTo(findViewById(R.id.login)).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent in = new Intent(Welcome.this, Login.class);
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        PushDownAnim.setPushDownAnimTo(findViewById(R.id.Signup)).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent in = new Intent(Welcome.this, Emailverifiy.class);
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        PushDownAnim.setPushDownAnimTo(findViewById(R.id.txtgmail)).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                signIn();
            }
        });

        PushDownAnim.setPushDownAnimTo(findViewById(R.id.ivfb)).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/thatdubaigirl/?ref=share"));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(Welcome.this, "No application can handle this request."
                            + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        PushDownAnim.setPushDownAnimTo(findViewById(R.id.ivinsta)).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/thatdubaigirl_/?hl=en"));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(Welcome.this, "No application can handle this request."
                            + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        CharSequence success = "Connection failed.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, success, duration);
        toast.show();
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient != null && !mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onPause() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//          Toast.makeText(this, "" + requestCode, Toast.LENGTH_SHORT).show();
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            Log.e("sfgsafgsa", "" + task.toString());
//            Toast.makeText(this, "faffa:  " + task.toString(), Toast.LENGTH_SHORT).show();
        } else {
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> result) {
        try {
            GoogleSignInAccount account = result.getResult(ApiException.class);
            Log.e("addadadadadad", "" + account);
            mGoogleSignInAccount = account;
            if (mGoogleSignInAccount != null) {
                mUserIdToken = mGoogleSignInAccount.getId();
                emailname = mGoogleSignInAccount.getDisplayName();
                profile = mGoogleSignInAccount.getPhotoUrl();
                emial_id = mGoogleSignInAccount.getEmail();
                Log.e("naem", "" + emailname);
                Log.e("emial_id", "" + emial_id);
                Log.e("mUserIdToken", "" + mUserIdToken);
                loginwithGoogle(mUserIdToken, emailname, emial_id);
            }
        } catch (Exception e) {

        }
    }


    /*get loginwithGoogle APi*/
    public void loginwithGoogle(String gmail_id, String name, String email) {
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.commn_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api loginservice = retrofit.create(Api.class);
        Call<Common_Model> call = loginservice.loginwithGoogle(gmail_id, name, email);
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
                        Toast.makeText(Welcome.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Welcome.this, PlayVideo.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        Toast.makeText(Welcome.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Common_Model> call, Throwable t) {
                dialog.dismiss();
            }

        });
    }




    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


}