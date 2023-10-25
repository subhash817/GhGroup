package com.cbs.ghgroup.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.adapter.PaymentRegisterAdapter;
import com.cbs.ghgroup.databinding.ActivityPaymentRegisterDetailsBinding;
import com.cbs.ghgroup.model.paymentregister.PaymentRegister;
import com.cbs.ghgroup.model.paymentregister.PaymentRegisterDetail;
import com.cbs.ghgroup.model.userlist.ExcelData;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.CustomProgressbar;
import com.cbs.ghgroup.utils.LogMsg;
import com.cbs.ghgroup.utils.PrefrenceKey;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentRegisterDetailsActivity extends AppCompatActivity {
    ActivityPaymentRegisterDetailsBinding payRegDetailsBinding;
    Context mContext;
    String fromDate, toDate, code, BRANCH,branch_code,IsCV;
    private File filePath = new File(Environment.getExternalStorageDirectory() + "/PaymentReg.xls");
    String fileName;
    //private File file;
    private String fileExtName;
    RecyclerView rvPayReg;
    DownloadManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        payRegDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_payment_register_details);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        mContext = PaymentRegisterDetailsActivity.this;
        fromDate = CommonMethods.getPrefsData(mContext, PrefrenceKey.FORM_DATE, "");
        toDate = CommonMethods.getPrefsData(mContext, PrefrenceKey.TO_DATE, "");
        code = CommonMethods.getPrefsData(mContext, PrefrenceKey.BP_CODE, "");
        BRANCH = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH, "");
        branch_code = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH_CODE, "");
        getId();
        getPaymentRegDetails();

    }

    public void getId(){
        TextView tvPayTotalBill = findViewById(R.id.tv_payTotalBill);
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.payment_register);

        ImageView ivBack = findViewById(R.id.img_BackButton);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        payRegDetailsBinding.paymentRegExcelDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = branch_code;
                String[] parts = string.split("-");
                String branch = parts[0];
                String type = parts[1];
                ExcelData excelData = new ExcelData();
                excelData.setScreenName("Ledger");
                excelData.setCode(code);
                excelData.setProcName("Receipt_Register_Mobile");
                excelData.setDataBaseName(branch);
                excelData.setRptFileName("");
                excelData.setType("3");
                excelData.setBranch(type);
                excelData.setFromDate(fromDate);
                excelData.setToDate(toDate);

                CustomProgressbar.showProgressBar(mContext, false);

                RetrofitClient.getClient1().downloadExcel(excelData).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        CustomProgressbar.hideProgressBar();
                        Log.e("Download Excel  ", response.toString());
                        if (response.code() == 200 && response.body() != null) {
                            manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse(response.body());
                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            long reference = manager.enqueue(request);
                        } else {
                            Toast.makeText(PaymentRegisterDetailsActivity.this, "Unable to download excel", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("error", "" + t.getMessage());
                        CustomProgressbar.hideProgressBar();
                    }
                });
            }
        });
    }

    public void getPaymentRegDetails(){
        CustomProgressbar.showProgressBar(mContext, false);

        RetrofitClient.getClient().getPaymentRegister(fromDate,toDate,code,branch_code,"C").enqueue(new Callback<PaymentRegister>() {
            @Override
            public void onResponse(Call<PaymentRegister> call, Response<PaymentRegister> response) {
                CustomProgressbar.hideProgressBar();
                LogMsg.d("Payment_Register",response.toString());
                if (response.code() == 200 && response.body() != null) {
                    PaymentRegister paymentRegister = response.body();
                    if (paymentRegister.getPaymentRegisterResult().getPaymentRegisterDetail().size() > 0) {
                        paymentRegister.getPaymentRegisterResult().getPaymentRegisterDetail().get(0).getBranch();
                        List<PaymentRegisterDetail> paymentRegisterDetails = paymentRegister.getPaymentRegisterResult().getPaymentRegisterDetail();
                        rvPayReg = (RecyclerView) findViewById(R.id.rcvPayment);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        payRegDetailsBinding.rcvPayment.setLayoutManager(linearLayoutManager);
                        PaymentRegisterAdapter paymentRegisterAdapter = new PaymentRegisterAdapter(PaymentRegisterDetailsActivity.this, paymentRegisterDetails);
                        payRegDetailsBinding.rcvPayment.setAdapter(paymentRegisterAdapter);

                        int paybill = 0;

                        for (int i = 0;i<paymentRegisterDetails.size();i++){
                            String abc = paymentRegisterDetails.get(i).getTotalAmt().replaceAll(",","");
                            int billamt = Integer.parseInt(abc);
                            paybill = paybill+billamt;
                            DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                            payRegDetailsBinding.payRegShowTotal.setText("Total:  " +formatter.format(paybill));
                            //payRegDetailsBinding.tvPayTotalBill.setText(""+paybill);

                            //tv.setText(""+bill);
                        }
                    }
                    else if (paymentRegister.getPaymentRegisterResult().getLogMessage().equals("false")) {
                    } else {
                        Toast.makeText(mContext, "" + paymentRegister.getPaymentRegisterResult().getLogMessage().getErrorMsg().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentRegister> call, Throwable t) {
                Toast.makeText(mContext,t.getMessage(), Toast.LENGTH_SHORT).show();
                CustomProgressbar.hideProgressBar();

            }
        });
    }

}