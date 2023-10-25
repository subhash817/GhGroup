package com.cbs.ghgroup.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import com.cbs.ghgroup.adapter.PendingBillAdapter;
import com.cbs.ghgroup.databinding.ActivityPendingBillDetailsBinding;
import com.cbs.ghgroup.model.DownlodLedgerRequest;
import com.cbs.ghgroup.model.pendingbill.PendingBills;
import com.cbs.ghgroup.model.pendingbill.PendingbillsDetail;
import com.cbs.ghgroup.model.userlist.ExcelData;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.CustomProgressbar;
import com.cbs.ghgroup.utils.PrefrenceKey;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingBillDetailsActivity extends AppCompatActivity {
     String upTodate,bpCode,type,branches,branch_code,IsCV;
     ActivityPendingBillDetailsBinding pendingBillDetailsBinding;
     Context mContext;
    RecyclerView rvPendingBill;
    DownloadManager manager;
    TextView penBillBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_pending_bill_details);
        pendingBillDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_pending_bill_details);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        mContext=PendingBillDetailsActivity.this;

        upTodate= CommonMethods.getPrefsData(mContext, PrefrenceKey.TO_DATE,"");
        bpCode= CommonMethods.getPrefsData(mContext, PrefrenceKey.BP_CODE,"");
        type= CommonMethods.getPrefsData(mContext, PrefrenceKey.TYPE,"");
        branches= CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH,"");
        branch_code = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH_CODE, "");
        //IsCV = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CV, "");


        penBillBalance = findViewById(R.id.tv_PendingTotalBalance);

        getId();
        getPendingBillDetails();
    }

    public void getId() {
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.PendingBill_Details);
       // CustomProgressbar.showProgressBar(mContext,false);

        rvPendingBill = (RecyclerView) findViewById(R.id.rv_pen_bill_details);
        ImageView ivBack = findViewById(R.id.img_BackButton);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pendingBillDetailsBinding.pendingBillPdfDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = branch_code;
                String[] parts = string.split("-");
                String branch = parts[0];
                String type = parts[1];
                DownlodLedgerRequest downlodLedgerRequest = new DownlodLedgerRequest();
                downlodLedgerRequest.setScreenName("PendingBills");
                downlodLedgerRequest.setCode(bpCode);
                downlodLedgerRequest.setProcName("Bill_Wise_Pending");
                downlodLedgerRequest.setDataBaseName(branch);
                downlodLedgerRequest.setRptFileName("BillWiseBPReport.rpt");
                downlodLedgerRequest.setType("1");
                downlodLedgerRequest.setBranch(type);
                downlodLedgerRequest.setFromDate(upTodate);
                downlodLedgerRequest.setToDate(upTodate);

                CustomProgressbar.showProgressBar(mContext, false);
                RetrofitClient.getClient1().downloadPdf(downlodLedgerRequest).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        CustomProgressbar.hideProgressBar();
                        Log.e("Download PDF", response.toString());
                        if (response.code() == 200 && response.body() != null) {
                            manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse(response.body());
                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            long reference = manager.enqueue(request);
                        } else {
                            Toast.makeText(PendingBillDetailsActivity.this, "Unable to download file", Toast.LENGTH_SHORT).show();
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

        pendingBillDetailsBinding.pendingBilExcelDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new DownloadFilesInvoice(PendingBillDetailsActivity.this, "https://pdf.theghgroup.com/Content/ExportedFiles/PendingBills_C03566.pdf","PendingBills");

                String string = branch_code;
                String[] parts = string.split("-");
                String branch = parts[0];
                String type = parts[1];
                ExcelData excelData = new ExcelData();
                excelData.setScreenName("Ledger");
                excelData.setCode(bpCode);
                excelData.setProcName("Bill_Wise_Pending_Grid_Mobile");
                excelData.setDataBaseName(branch);
                excelData.setRptFileName("");
                excelData.setType("3");
                excelData.setBranch(type);
                excelData.setFromDate(upTodate);
                excelData.setToDate(upTodate);

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
                            Toast.makeText(PendingBillDetailsActivity.this, "Unable to download excel", Toast.LENGTH_SHORT).show();
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

    public void getPendingBillDetails(){
        CustomProgressbar.showProgressBar(mContext, false);
        RetrofitClient.getClient().getPendingBills(upTodate,bpCode,type,branch_code,"C").enqueue(new Callback<PendingBills>() {
            @Override
            public void onResponse(Call<PendingBills> call, Response<PendingBills> response)  {
                CustomProgressbar.hideProgressBar();
                if (response.code() ==200 && response.body() !=null){
                    PendingBills pendingBills=response.body();
                    if (pendingBills.getPendingBillsResult().getPendingbillsDetail().size() > 0) {
                        pendingBills.getPendingBillsResult().getPendingbillsDetail().get(0).getBranch();
                        Log.e("TAG", "Request: " + response.toString());
                        //CustomerLedger customerLedger = response.body();
                        List<PendingbillsDetail> pendingbillsDetails=pendingBills.getPendingBillsResult().getPendingbillsDetail();
                        Log.e("TAG", "Request: " + response.toString());

                        try {
                            penBillBalance.setText("" + pendingbillsDetails.get(0).getBalance());
                        }catch (Exception ex){

                        }

                        rvPendingBill = (RecyclerView) findViewById(R.id.rv_pen_bill_details);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        pendingBillDetailsBinding.rvPenBillDetails.setLayoutManager(linearLayoutManager);
                        PendingBillAdapter pendingBillAdapter = new PendingBillAdapter(PendingBillDetailsActivity.this,pendingbillsDetails);
                        pendingBillDetailsBinding.rvPenBillDetails.setAdapter(pendingBillAdapter);

                        try {
                            if (pendingbillsDetails.get(0).getBalance().equalsIgnoreCase(pendingbillsDetails.get(0).getBalance())){
                                pendingBillDetailsBinding.pendingBillsShowBalance.setText("Total:  " + pendingbillsDetails.get(0).getBalance());
                            }

                        } catch (Exception ex){

                        }
                    }
                    else if (pendingBills.getPendingBillsResult().getLogMessage().equals("false")) {
                    } else {
                        Toast.makeText(mContext, "" + pendingBills.getPendingBillsResult().getLogMessage().getErrorMsg().toString(), Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<PendingBills> call, Throwable t) {
                Toast.makeText(mContext, "Bad Request 400", Toast.LENGTH_SHORT).show();
                CustomProgressbar.hideProgressBar();

            }
        });
    }

}