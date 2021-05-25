package com.thatdubaigirl.com.Activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thatdubaigirl.com.Model.Common_Model_A;
import com.thatdubaigirl.com.R;
import com.thatdubaigirl.com.Utils.Api;
import com.thatdubaigirl.com.Utils.OnSingleClickListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class Emailverifiy extends AppCompatActivity {
    EditText edemailid;
    TextView submit;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailverifiy);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        edemailid = findViewById(R.id.edemailid);
        submit = findViewById(R.id.submit);

        PushDownAnim.setPushDownAnimTo(findViewById(R.id.submit)).setScale(MODE_SCALE, 0.89f).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (edemailid.getText().toString().length() == 0) {
                    Toast.makeText(Emailverifiy.this, "Please enter your email address!!", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(edemailid.getText().toString())) {
                    Toast.makeText(Emailverifiy.this, "Please Enter Valid Email Id", Toast.LENGTH_SHORT).show();
                } else{
                    emailverify();
                }
            }
        });
    }

    /*get emailverify APi*/
    public void emailverify() {
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.commn_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api loginservice = retrofit.create(Api.class);
        Call<Common_Model_A> call = loginservice.emailverify(edemailid.getText().toString());
        call.enqueue(new Callback<Common_Model_A>() {
            @Override
            public void onResponse(Call<Common_Model_A> call, Response<Common_Model_A> response) {
                if (response.code() == 200) {
                    dialog.dismiss();
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        Toast.makeText(Emailverifiy.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Emailverifiy.this, Otp.class);
                        i.putExtra("email", edemailid.getText().toString());
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        Toast.makeText(Emailverifiy.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Common_Model_A> call, Throwable t) {
                dialog.dismiss();
            }

        });
    }


    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void back(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

}