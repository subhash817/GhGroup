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
import com.cbs.ghgroup.adapter.DirectPendingAdapter;
import com.cbs.ghgroup.databinding.ActivityDirectPenDeatilsBinding;
import com.cbs.ghgroup.model.DownlodLedgerRequest;
import com.cbs.ghgroup.model.directpending.DirectPending;
import com.cbs.ghgroup.model.directpending.DirectPendingDetail;
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

public class DirectPenDeatilsActivity extends AppCompatActivity {
    ActivityDirectPenDeatilsBinding directPenDeatilsBinding;
    Context mContext;
    String fromdate, todate, cardcode, Branch,branch_code,type,IsCV;
    DownloadManager manager;
    RecyclerView rvDirectPendBills;
    private File filePath = new File(Environment.getExternalStorageDirectory() + "/PaymentComm.xls");
    String fileName;
    //private File file;
    private String fileExtName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_direct_pen_deatils);
        directPenDeatilsBinding = DataBindingUtil.setContentView(this, R.layout.activity_direct_pen_deatils);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        mContext = DirectPenDeatilsActivity.this;
        fromdate = CommonMethods.getPrefsData(mContext, PrefrenceKey.FORM_DATE, "");
        todate = CommonMethods.getPrefsData(mContext, PrefrenceKey.TO_DATE, "");
        cardcode = CommonMethods.getPrefsData(mContext, PrefrenceKey.BP_CODE, "");
        Branch = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH, "");
        type = CommonMethods.getPrefsData(mContext, PrefrenceKey.TYPE, "");
        branch_code = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH_CODE, "");
        getId();

    }
    public void getId(){
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.direct_pending);
        ImageView ivBack = findViewById(R.id.img_BackButton);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        CustomProgressbar.showProgressBar(mContext, false);

        RetrofitClient.getClient().getDirectPending(fromdate,todate,cardcode,branch_code,type,"C").enqueue(new Callback<DirectPending>() {
            @Override
            public void onResponse(Call<DirectPending> call, Response<DirectPending> response) {
                CustomProgressbar.hideProgressBar();
                if (response.code() ==200 && response.body() !=null){
                    DirectPending directPending= response.body();
                    if (directPending.getDirectPendingResult().getDirectPendingDetail().size() > 0) {
                        directPending.getDirectPendingResult().getDirectPendingDetail().get(0).getBranch();
                        Log.e("TAG", "Request: " + response.toString());
                        //CustomerLedger customerLedger = response.body();
                        List<DirectPendingDetail> directPendingDetails=directPending.getDirectPendingResult().getDirectPendingDetail();
                        Log.e("TAG", "Request: " + response.toString());

                        rvDirectPendBills = (RecyclerView) findViewById(R.id.rcvDirectPenDetails);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        directPenDeatilsBinding.rcvDirectPenDetails.setLayoutManager(linearLayoutManager);
                        DirectPendingAdapter directPendingAdapter = new DirectPendingAdapter(DirectPenDeatilsActivity.this,directPendingDetails);
                        directPenDeatilsBinding.rcvDirectPenDetails.setAdapter(directPendingAdapter);

                        try {
                            int paybill = 0;
                            for (int i = 0;i<directPendingDetails.size();i++) {
                                String abc = directPendingDetails.get(i).getBillAmt().replaceAll(",", "");
                                int billamt = Integer.parseInt(abc);
                                paybill = paybill + billamt;
                                DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                                //paymentCommDetailsBinding.payCommShowTotal.setText("Total:  " +formatter.format(paybill));
                                directPenDeatilsBinding.dirPendShowTotal.setText("Total:  " + formatter.format(paybill));
                            }

                        } catch (Exception ex){

                        }

                       /* int  paybill = 0;

                        for (int i = 0;i<directPendingDetails.size();i++) {
                            String abc = directPendingDetails.get(i).getBillAmt().replaceAll(",", "");
                            int billamt = Integer.parseInt(abc);
                            paybill = paybill + billamt;
                            directPenDeatilsBinding.dirPendShowTotal.setText("Total:  " + paybill);
                        }*/


                       /* int paybill = 0;

                        for (int i = 0;i<directPendingDetails.size();i++){
                            String abc = directPendingDetails.get(i).getBillAmt().replaceAll(",","");
                            int billamt = Integer.parseInt(abc);
                            paybill = paybill+billamt;
                            DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                            directPenDeatilsBinding.dirPendShowTotal.setText("Total:  " +formatter.format(paybill));
                        }*/
                    }
                    else if (directPending.getDirectPendingResult().getLogMessage().equals("false")) {
                    } else {
                        Toast.makeText(mContext, "" + directPending.getDirectPendingResult().getLogMessage().getErrorMsg().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<DirectPending> call, Throwable t) {
                Toast.makeText(mContext, "Bad Request 400", Toast.LENGTH_SHORT).show();
                CustomProgressbar.hideProgressBar();

            }
        });

        directPenDeatilsBinding.directPendBillPDFDOWNLOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = branch_code;
                String[] parts = string.split("-");
                String branch = parts[0];
                String type = parts[1];
                DownlodLedgerRequest downlodLedgerRequest = new DownlodLedgerRequest();
                downlodLedgerRequest.setScreenName("DirectPendingBills");
                downlodLedgerRequest.setCode(cardcode);
                downlodLedgerRequest.setProcName("Comm_General_PendingReport");
                downlodLedgerRequest.setDataBaseName(branch);
                downlodLedgerRequest.setRptFileName("BillWiseCommunicationReport.rpt");
                downlodLedgerRequest.setType("1");
                downlodLedgerRequest.setBranch(type);
                downlodLedgerRequest.setFromDate(fromdate);
                downlodLedgerRequest.setToDate(todate);
                RetrofitClient.getClient1().downloadPdf(downlodLedgerRequest).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        CustomProgressbar.hideProgressBar();
                        LogMsg.d("Download_PDF", response.toString());
                        if (response.code() == 200 && response.body() != null) {
                            manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse(response.body());
                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            long reference = manager.enqueue(request);
                        } else {
                            Toast.makeText(DirectPenDeatilsActivity.this, "Unable to download file", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(mContext,t.getMessage(), Toast.LENGTH_SHORT).show();
                        CustomProgressbar.hideProgressBar();
                    }
                });

            }
        });

        directPenDeatilsBinding.directPendBillExcelDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = branch_code;
                String[] parts = string.split("-");
                String branch = parts[0];
                String type = parts[1];
                ExcelData excelData = new ExcelData();
                excelData.setScreenName("Ledger");
                excelData.setCode(cardcode);
                excelData.setProcName("Comm_General_PendingReport_grid_Mobile");
                excelData.setDataBaseName(branch);
                excelData.setRptFileName("");
                excelData.setType("C");
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
                            Toast.makeText(DirectPenDeatilsActivity.this, "Unable to download excel", Toast.LENGTH_SHORT).show();
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