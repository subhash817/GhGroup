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
import com.cbs.ghgroup.adapter.CreditRegisterAdapter;
import com.cbs.ghgroup.databinding.ActivityCreditRegisterDetailsBinding;
import com.cbs.ghgroup.model.creditregister.CreditRegister;
import com.cbs.ghgroup.model.creditregister.CreditRegisterDetail;
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

public class CreditRegisterDetailsActivity extends AppCompatActivity {
    ActivityCreditRegisterDetailsBinding creRegDetailsBinding;
    Context mContext;
    String fromDate, toDate, code, branch_code,IsCV;
    private File filePath = new File(Environment.getExternalStorageDirectory() + "/CreditNoteReg.xls");
    String fileName;
    //private File file;
    private String fileExtName;
    RecyclerView rvCredNoteReg;
    DownloadManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        creRegDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_credit_register_details);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        mContext = CreditRegisterDetailsActivity.this;

        fromDate = CommonMethods.getPrefsData(mContext, PrefrenceKey.FORM_DATE, "");
        toDate = CommonMethods.getPrefsData(mContext, PrefrenceKey.TO_DATE, "");
        code = CommonMethods.getPrefsData(mContext, PrefrenceKey.BP_CODE, "");
        branch_code = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH_CODE, "");
        //IsCV = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CV, "");
        getId();


    }

    public void getId() {
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.credit_note_register);

        ImageView ivBack = findViewById(R.id.img_BackButton);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        CustomProgressbar.showProgressBar(mContext, false);

        RetrofitClient.getClient().getCreditRegister(fromDate, toDate, code, branch_code,"C").enqueue(new Callback<CreditRegister>() {
            @Override
            public void onResponse(Call<CreditRegister> call, Response<CreditRegister> response) {
                CustomProgressbar.hideProgressBar();
                if (response.code() == 200 && response.body() != null) {
                    CreditRegister creditRegister = response.body();
                    LogMsg.d("CreditRegister", response.toString());
                    if (creditRegister.getCreditRegisterResult().getCreditRegisterDetail().size() > 0) {
                        creditRegister.getCreditRegisterResult().getCreditRegisterDetail().get(0).getBranch();
                        List<CreditRegisterDetail> creditRegisterDetails = creditRegister.getCreditRegisterResult().getCreditRegisterDetail();
                        rvCredNoteReg = (RecyclerView) findViewById(R.id.rcvCreditRegister);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        creRegDetailsBinding.rcvCreditRegister.setLayoutManager(linearLayoutManager);
                        CreditRegisterAdapter creditRegisterAdapter = new CreditRegisterAdapter(CreditRegisterDetailsActivity.this, creditRegisterDetails);
                        creRegDetailsBinding.rcvCreditRegister.setAdapter(creditRegisterAdapter);

                        int creditbill = 0;

                        for (int i = 0;i<creditRegisterDetails.size();i++){
                            String abc = creditRegisterDetails.get(i).getTotalAmt().replaceAll(",","");
                            int totalamt = Integer.parseInt(abc);
                            creditbill = creditbill+totalamt;

                            DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                            creRegDetailsBinding.credNoteRegShowTotal.setText("Total:  " +formatter.format(creditbill));
                            //creRegDetailsBinding.tvCreditTotalAmt.setText(""+creditbill);

                            //tv.setText(""+bill);
                        }

                    }
                    else if (creditRegister.getCreditRegisterResult().getLogMessage().equals("false")) {
                    } else {
                        Toast.makeText(mContext, "" + creditRegister.getCreditRegisterResult().getLogMessage().getErrorMsg().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CreditRegister> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                CustomProgressbar.hideProgressBar();

            }
        });

        creRegDetailsBinding.creditNoteRegExcelDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = branch_code;
                String[] parts = string.split("-");
                String branch = parts[0];
                String type = parts[1];
                ExcelData excelData = new ExcelData();
                excelData.setScreenName("Ledger");
                excelData.setCode(code);
                excelData.setProcName("CreditNote_Register_Mobile");
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
                            Toast.makeText(CreditRegisterDetailsActivity.this, "Unable to download excel", Toast.LENGTH_SHORT).show();
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