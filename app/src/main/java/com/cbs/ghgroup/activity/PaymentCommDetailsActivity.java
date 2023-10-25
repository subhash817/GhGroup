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
import com.cbs.ghgroup.adapter.PaymentCommunicationAdapter;
import com.cbs.ghgroup.databinding.ActivityPaymentCommDetailsBinding;
import com.cbs.ghgroup.model.paymentcommunication.PaymentCommunication;
import com.cbs.ghgroup.model.paymentcommunication.PaymentCommunicationDetail;
import com.cbs.ghgroup.model.userlist.ExcelData;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.CustomProgressbar;
import com.cbs.ghgroup.utils.PrefrenceKey;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentCommDetailsActivity extends AppCompatActivity {
    ActivityPaymentCommDetailsBinding paymentCommDetailsBinding;
    Context mContext;
    String fromDate, toDate, code, BRANCH,branch_code,IsCV;
    private File filePath = new File(Environment.getExternalStorageDirectory() + "/PaymentComm.xls");
    String fileName;
    //private File file;
    private String fileExtName;
    RecyclerView rvPaymentComm;
    DownloadManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_comm_details);
        paymentCommDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_payment_comm_details);
        mContext = PaymentCommDetailsActivity.this;

        fromDate = CommonMethods.getPrefsData(mContext, PrefrenceKey.FORM_DATE, "");
        toDate = CommonMethods.getPrefsData(mContext, PrefrenceKey.TO_DATE, "");
        code = CommonMethods.getPrefsData(mContext, PrefrenceKey.BP_CODE, "");
        BRANCH = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH, "");
        branch_code = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH_CODE, "");
        getId();

    }

    public void getId(){
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.payment_communication);
        ImageView ivBack = findViewById(R.id.img_BackButton);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        CustomProgressbar.showProgressBar(mContext, false);
        RetrofitClient.getClient().getPaymentCommunication(fromDate,toDate,code,branch_code,"C").enqueue(new Callback<PaymentCommunication>() {
            @Override
            public void onResponse(Call<PaymentCommunication> call, Response<PaymentCommunication> response) {
                CustomProgressbar.hideProgressBar();
                if (response.code() ==200 && response.body() !=null){
                    PaymentCommunication paymentCommunication=response.body();
                    if (paymentCommunication.getPaymentCommunicationResult().getPaymentCommunicationDetail().size() > 0) {
                        paymentCommunication.getPaymentCommunicationResult().getPaymentCommunicationDetail().get(0).getBranch();
                        Log.e("TAG", "Request: " + response.toString());
                        List<PaymentCommunicationDetail> paymentCommunicationDetails=paymentCommunication.getPaymentCommunicationResult().getPaymentCommunicationDetail();
                        Log.e("TAG", "Request: " + response.toString());
                        rvPaymentComm = (RecyclerView) findViewById(R.id.rcvPurchageCommRegister);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    paymentCommDetailsBinding.rcvPurchageCommRegister.setLayoutManager(linearLayoutManager);
                    PaymentCommunicationAdapter paymentCommunicationAdapter = new PaymentCommunicationAdapter(PaymentCommDetailsActivity.this,paymentCommunicationDetails);
                    paymentCommDetailsBinding.rcvPurchageCommRegister.setAdapter(paymentCommunicationAdapter);

                        int paybill = 0;

                        for (int i = 0;i<paymentCommunicationDetails.size();i++){
                            String abc = paymentCommunicationDetails.get(i).getTotalAmt().replaceAll(",","");
                            int billamt = Integer.parseInt(abc);
                            paybill = paybill+billamt;
                            DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                            paymentCommDetailsBinding.payCommShowTotal.setText("Total:  " +formatter.format(paybill));

                        }

                }
                    else if (paymentCommunication.getPaymentCommunicationResult().getLogMessage().equals("false")) {
                    } else {
                        Toast.makeText(mContext, "" + paymentCommunication.getPaymentCommunicationResult().getLogMessage().getErrorMsg().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentCommunication> call, Throwable t) {
                Toast.makeText(mContext, "Bad Request 400", Toast.LENGTH_SHORT).show();
                CustomProgressbar.hideProgressBar();

            }
        });

        paymentCommDetailsBinding.paymentCommExcelDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = branch_code;
                String[] parts = string.split("-");
                String branch = parts[0];
                String type = parts[1];
                ExcelData excelData = new ExcelData();
                excelData.setScreenName("Ledger");
                excelData.setCode(code);
                excelData.setProcName("GR_Comm_Pay_Register_Mobile");
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
                            Toast.makeText(PaymentCommDetailsActivity.this, "Unable to download excel", Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}