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
import com.cbs.ghgroup.adapter.BillRegisterAdapter;
import com.cbs.ghgroup.databinding.ActivityBillRegisterDetailsBinding;
import com.cbs.ghgroup.model.billregister.BillRegister;
import com.cbs.ghgroup.model.billregister.BillRegisterDetail;
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

public class BillRegisterDetailsActivity extends AppCompatActivity {
    ActivityBillRegisterDetailsBinding billRegisterDetailsBinding;
    Context mContext;
    RecyclerView rvBillReg;
    String fromdate, type,todate, bpcode, branches,branch_code,IsCV;
    private File filePath = new File(Environment.getExternalStorageDirectory() + "/BillReg.xls");
    String fileName;
    //private File file;
    private String fileExtName;
    DownloadManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        billRegisterDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_bill_register_details);
        mContext = BillRegisterDetailsActivity.this;
        fromdate = CommonMethods.getPrefsData(mContext, PrefrenceKey.FORM_DATE, "");
        todate = CommonMethods.getPrefsData(mContext, PrefrenceKey.TO_DATE, "");
        bpcode = CommonMethods.getPrefsData(mContext, PrefrenceKey.BP_CODE, "");
        branch_code = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH_CODE, "");
        getId();
        getBillRegister();
    }
   public void getId(){
       TextView setActivityName = findViewById(R.id.txtHeader);
       setActivityName.setText(R.string.bill_register);
       ImageView ivBack = findViewById(R.id.img_BackButton);
       overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
       ivBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onBackPressed();
           }
       });


   }
    public void getBillRegister(){
        CustomProgressbar.showProgressBar(mContext, false);
        RetrofitClient.getClient().getBillRegister(fromdate,todate,bpcode,branch_code,"C").enqueue(new Callback<BillRegister>() {
            @Override
            public void onResponse(Call<BillRegister> call, Response<BillRegister> response) {
                CustomProgressbar.hideProgressBar();
                if (response.code() == 200 && response.body() != null) {
                    BillRegister billRegister = response.body();
                     LogMsg.d("BillRegister",response.toString());

                    if (billRegister.getBillRegisterResult().getBillRegisterDetail().size() > 0) {
                        billRegister.getBillRegisterResult().getBillRegisterDetail().get(0).getBranch();
                        List<BillRegisterDetail> billRegisterDetail = billRegister.getBillRegisterResult().getBillRegisterDetail();
                        rvBillReg = (RecyclerView) findViewById(R.id.rv_bill_reg_details);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rvBillReg.setLayoutManager(linearLayoutManager);
                        BillRegisterAdapter billRegisterAdapter = new BillRegisterAdapter(BillRegisterDetailsActivity.this, billRegisterDetail);
                        rvBillReg.setAdapter(billRegisterAdapter);

                        int billRegbill = 0;

                        for (int i = 0;i<billRegisterDetail.size();i++){
                            String abc = billRegisterDetail.get(i).getBillAmt().replaceAll(",","");
                            int billamt = Integer.parseInt(abc);
                            billRegbill = billRegbill+billamt;
                            DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                            billRegisterDetailsBinding.billRegShowAmount.setText("Total:  " +formatter.format(billRegbill));
                           // billRegisterDetailsBinding.tvBillRegAmt.setText(""+billRegbill);

                            //tv.setText(""+bill);
                        }

                    }
                    else if (billRegister.getBillRegisterResult().getLogMessage().equals("false")) {
                    } else {
                        Toast.makeText(mContext, "" + billRegister.getBillRegisterResult().getLogMessage().getErrorMsg().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BillRegister> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
                CustomProgressbar.hideProgressBar();

            }
        });

        billRegisterDetailsBinding.billRegExcelDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = branch_code;
                String[] parts = string.split("-");
                String branch = parts[0];
                String type = parts[1];
                ExcelData excelData = new ExcelData();
                excelData.setScreenName("Ledger");
                excelData.setCode(bpcode);
                excelData.setProcName("Bill_Comm_Register_Mobile");
                excelData.setDataBaseName(branch);
                excelData.setRptFileName("");
                excelData.setType("3");
                excelData.setBranch(type);
                excelData.setFromDate(fromdate);
                excelData.setToDate(todate);

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
                            Toast.makeText(BillRegisterDetailsActivity.this, "Unable to download excel", Toast.LENGTH_SHORT).show();
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
}