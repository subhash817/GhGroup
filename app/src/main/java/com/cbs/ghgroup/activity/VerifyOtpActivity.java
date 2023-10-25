package com.cbs.ghgroup.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.databinding.ActivityVerifyOtpBinding;
import com.cbs.ghgroup.model.ghlogin.GhLogin;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.LogMsg;
import com.cbs.ghgroup.utils.PrefrenceKey;
import com.cbs.ghgroup.utils.SmsBroadcastReceiver;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpActivity extends AppCompatActivity {
    Context mContext;
    String code, is_customer_or_vendor, is_vaild, mobNo, Name;
    ActivityVerifyOtpBinding verifyBinding;
    TextView counttime, tvStartMobNo;
    String otpGenerated,lastUpdatedOn;
    public int counter;
    GhLogin ghLogin;
    //otp auto
    private static final int REQ_USER_CONSENT = 200;
    SmsBroadcastReceiver smsBroadcastReceiver;
    TextInputEditText otpEDT;

    ActivityResultLauncher<Intent> startForResult=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result !=null && result.getResultCode() == RESULT_OK){
                if (result.getData() !=null &&  result.getData().getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)!=null){
                    getOtpFromMessage(result.getData().getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE));
                }
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyBinding = DataBindingUtil.setContentView(this, R.layout.activity_verify_otp);
        mContext = VerifyOtpActivity.this;
        tvStartMobNo = findViewById(R.id.txt_start_mob_no);
        otpEDT = findViewById(R.id.otpEDT);
        code = CommonMethods.getPrefsData(mContext, PrefrenceKey.CODE, "");
        mobNo = CommonMethods.getPrefsData(mContext, PrefrenceKey.MOBILE_NO, "");
        lastUpdatedOn = CommonMethods.getPrefsData(mContext, PrefrenceKey.UPDATED_TIME, "");
        getId();
        mobNo = mobNo.replaceAll("\\w(?=\\w{4})", "*");    //for last 4 digit mob no show
        tvStartMobNo.setText("OTP send to mobile no end with " + mobNo);
        startSmartUserConsent();
    }
    public void getId() {
        counttime = findViewById(R.id.counttime);
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                counttime.setText(String.valueOf(counter));
                counter++;
                NumberFormat f = new DecimalFormat("0");
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                counttime.setText("Resend OTP in: " + f.format(min) + ":" + f.format(sec) + "Seconds");
            }

            @Override
            public void onFinish() {

            }
        }.start();

        verifyBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CommonMethods.hideKeyboard(mContext);
                if (CommonMethods.isOnline(mContext)) {

                    String otp = verifyBinding.otpEDT.getText().toString();
                    if (TextUtils.isEmpty(otp)) {
                        Toast.makeText(mContext, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (verifyBinding.btnLogin.getText().toString().equals(otpGenerated)) {
                        Toast.makeText(VerifyOtpActivity.this, "OTP Verified Successfully!", Toast.LENGTH_SHORT).show();
                    }

                    RetrofitClient.getClient().login(code, otp).enqueue(new Callback<GhLogin>() {
                        @Override
                        public void onResponse(Call<GhLogin> call, Response<GhLogin> response) {
                            if (response.code() == 200 && response.body() != null) {
                                ghLogin = response.body();
                                LogMsg.d("OTP_VARIFY", response.toString());

                                if (ghLogin.getLoginListResult().getLoginDetail().size() > 0) {
                                    String IscustomerOrVendor = ghLogin.getLoginListResult().getLoginDetail().get(0).getIscustomerOrVendor();
                                    if (ghLogin.getLoginListResult().getLoginDetail().size() > 0) {
                                        if (IscustomerOrVendor.equalsIgnoreCase("S")) {
                                            CommonMethods.setPrefsData(mContext, PrefrenceKey.IS_VALID, response.body().getLoginListResult().getLoginDetail().get(0).getIsValid());
                                            Intent intent = new Intent(mContext, DashBoardActivity.class);
                                            intent.putExtra(PrefrenceKey.CODE, code);
                                            intent.putExtra(PrefrenceKey.IS_VALID, is_vaild);
                                            intent.putExtra(PrefrenceKey.MOBILE_NO, mobNo);
                                            intent.putExtra(PrefrenceKey.IS_CUSTOMER_OR_VENDOR, is_customer_or_vendor);
                                            CommonMethods.setPrefsData(mContext,PrefrenceKey.UPDATED_TIME, response.body().getLoginListResult().getLoginDetail().get(0).getLastUpdatedOn());
                                            startActivity(intent);
                                            finish();
                                        } else if (IscustomerOrVendor.equalsIgnoreCase("BU")) {
                                           // response.body().getLoginListResult().getLoginDetail().get(0).getLastUpdatedOn().toString();
                                            CommonMethods.setPrefsData(mContext, PrefrenceKey.IS_VALID, response.body().getLoginListResult().getLoginDetail().get(0).getIsValid());
                                            Intent intent = new Intent(mContext, DashBoardActivity.class);
                                            intent.putExtra(PrefrenceKey.CODE, code);
                                            intent.putExtra(PrefrenceKey.IS_VALID, is_vaild);
                                            intent.putExtra(PrefrenceKey.MOBILE_NO, mobNo);
                                            intent.putExtra(PrefrenceKey.IS_CUSTOMER_OR_VENDOR, is_customer_or_vendor);
                                            CommonMethods.setPrefsData(mContext,PrefrenceKey.UPDATED_TIME, response.body().getLoginListResult().getLoginDetail().get(0).getLastUpdatedOn());
                                            CommonMethods.setPrefsData(mContext,PrefrenceKey.IS_CUSTOMER_OR_VENDOR, response.body().getLoginListResult().getLoginDetail().get(0).getIscustomerOrVendor());

                                            startActivity(intent);
                                            finish();

                                        } else if (IscustomerOrVendor.equalsIgnoreCase("V")) {
                                            Intent intent = new Intent(mContext, VendorActivity.class);
                                            intent.putExtra(PrefrenceKey.CODE, code);
                                            CommonMethods.setPrefsData(mContext,PrefrenceKey.UPDATED_TIME, response.body().getLoginListResult().getLoginDetail().get(0).getLastUpdatedOn());
                                            startActivity(intent);
                                            finish();
                                        } else if (IscustomerOrVendor.equalsIgnoreCase("C")) {
                                            Intent intent = new Intent(mContext, CustomerActivity.class);
                                            intent.putExtra(PrefrenceKey.CODE, code);
                                            CommonMethods.setPrefsData(mContext,PrefrenceKey.UPDATED_TIME, response.body().getLoginListResult().getLoginDetail().get(0).getLastUpdatedOn());
                                            startActivity(intent);
                                            finish();

                                        } else {

                                            Toast.makeText(mContext, " No Record Found" + ghLogin.getLoginListResult().getLogMessage().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(mContext, " No Record Found", Toast.LENGTH_SHORT).show();
                                    }


                                } else {
                                    Toast.makeText(mContext, " No Record Found", Toast.LENGTH_SHORT).show();
                                }
                            } else {

                                Toast.makeText(mContext, " failed", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<GhLogin> call, Throwable t) {
                            Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                } else {
                    CommonMethods.setSnackBar(verifyBinding.rlLayout, getString(R.string.net));
                }

            }
        });
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        verifyBinding.fabVerifyOTPLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFabVerifyOtpLog();

            }

        });

    }
    public void OpenFabVerifyOtpLog(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(VerifyOtpActivity.this);
        mBuilder.setCancelable(true);
        View mView = getLayoutInflater().inflate(R.layout.contact_dialogbox, null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

    }
    // auto otp
    private void startSmartUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        client.startSmsUserConsent(null);

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQ_USER_CONSENT) {
//            if ((resultCode == RESULT_OK) && (data != null)) {
//                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
//                getOtpFromMessage(message);
//            }
//
//        }
//    }

    private void getOtpFromMessage(String message) {

        Pattern otpPattern = Pattern.compile("(|^)\\d{6}");
        Matcher matcher = otpPattern.matcher(message);
        if (matcher.find()) {

            otpEDT.setText(matcher.group(0));

        }

    }

    private void registerBroadcastReceiver() {

        smsBroadcastReceiver = new SmsBroadcastReceiver();

        smsBroadcastReceiver.smsBroadcastReceiverListener = new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(Intent intent) {

               // startActivityForResult(intent, REQ_USER_CONSENT);
                startForResult.launch(intent);

            }

            @Override
            public void onFailure() {

            }
        };

        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver, intentFilter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }



}
