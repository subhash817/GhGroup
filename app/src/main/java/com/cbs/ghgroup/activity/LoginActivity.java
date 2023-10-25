package com.cbs.ghgroup.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.databinding.ActivityLoginBinding;
import com.cbs.ghgroup.model.getotp.GetOtp;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.LogMsg;
import com.cbs.ghgroup.utils.PrefrenceKey;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding loginBinding;
    Context mContext;
    FloatingActionButton faBtn;
    String error;
    boolean doubleBackToExitPressedOnce = false;
    SharedPreferences sp;
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mContext = LoginActivity.this;
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        getId();
    }

    public void getId() {
        faBtn = findViewById(R.id.fab_log);

        loginBinding.btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonMethods.hideKeyboard(mContext);
                if (CommonMethods.isOnline(mContext)) {
                    String userCode = loginBinding.edtUserID.getText().toString().trim();
                    if (TextUtils.isEmpty(userCode)) {
                        Toast.makeText(mContext, "Please Enter User Code", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    RetrofitClient.getClient().loginWithOtp(userCode).enqueue(new Callback<GetOtp>() {
                        @Override
                        public void onResponse(Call<GetOtp> call, Response<GetOtp> response) {
                            if (response.code() == 200 && response.body() != null) {
                                LogMsg.d("GET_OTP", response.toString());

                                if (response.body().getOtpListResult().getOtpDetail().size() > 0) {
                                    CommonMethods.setPrefsData(mContext, PrefrenceKey.CODE, userCode);
                                    CommonMethods.setPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, response.body().getOtpListResult().getOtpDetail().get(0).getIscustomerOrVendor());
                                    CommonMethods.setPrefsData(mContext, PrefrenceKey.OTP, response.body().getOtpListResult().getOtpDetail().get(0).getOtp());
                                    CommonMethods.setPrefsData(mContext, PrefrenceKey.MOBILE_NO, response.body().getOtpListResult().getOtpDetail().get(0).getMobileNo());
                                    CommonMethods.setPrefsData(mContext, PrefrenceKey.NAME, response.body().getOtpListResult().getOtpDetail().get(0).getName());
                                   // CommonMethods.setPrefsData(mContext, PrefrenceKey.UPDATED_TIME, response.body().getOtpListResult().getOtpDetail().get(0).getName());
                                    Intent intent = new Intent(mContext, VerifyOtpActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {


                                    Toast.makeText(LoginActivity.this, "Record not found", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Invalid Code", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetOtp> call, Throwable t) {
                            Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });

                } else {
                    CommonMethods.setSnackBar(loginBinding.rlRoot, getString(R.string.net));

                }

            }
        });

        faBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OpenFabLog();
            }

        });

    }

    public void OpenFabLog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
        mBuilder.setCancelable(true);
        View mView = getLayoutInflater().inflate(R.layout.contact_dialogbox, null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(mContext, "Please click BACK again to exit", Toast.LENGTH_LONG).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }
}


