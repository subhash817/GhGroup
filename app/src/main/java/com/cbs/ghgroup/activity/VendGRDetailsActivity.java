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
import com.cbs.ghgroup.adapter.VendGRAdapter;
import com.cbs.ghgroup.databinding.ActivityVendGrdetailsBinding;
import com.cbs.ghgroup.model.DownlodLedgerRequest;
import com.cbs.ghgroup.model.grcommunication.CommunicationRegisterDetail;
import com.cbs.ghgroup.model.grcommunication.GRCommunication;
import com.cbs.ghgroup.model.userlist.ExcelData;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.CustomProgressbar;
import com.cbs.ghgroup.utils.LogMsg;
import com.cbs.ghgroup.utils.PrefrenceKey;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendGRDetailsActivity extends AppCompatActivity {
    ActivityVendGrdetailsBinding vendGrdetailsBinding;
    Context mContext;
    String fileName;
    String fromdate, type, todate, bpcode, branches, cardcode,branch_code,IsCV;
    DownloadManager manager;
    RecyclerView rvGRLedger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vendGrdetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_vend_grdetails);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        mContext = VendGRDetailsActivity.this;

        fromdate = CommonMethods.getPrefsData(mContext, PrefrenceKey.FORM_DATE, "");
        todate = CommonMethods.getPrefsData(mContext, PrefrenceKey.TO_DATE, "");
        bpcode = CommonMethods.getPrefsData(mContext, PrefrenceKey.BP_CODE, "");
        branches = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH, "");
        branch_code = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH_CODE, "");
        type = CommonMethods.getPrefsData(mContext, PrefrenceKey.TYPE, "");
        getId();
        getVendGeneralReport();
    }

    public void getId() {
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.g_r_register);
        rvGRLedger = (RecyclerView) findViewById(R.id.rv_gr_Comm_details);
        ImageView ivBack = findViewById(R.id.img_BackButton);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        vendGrdetailsBinding.GRPDFDOWNLOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = branch_code;
                String[] parts = string.split("-");
                String branch = parts[0];
                String type = parts[1];
                DownlodLedgerRequest downlodLedgerRequest = new DownlodLedgerRequest();
                downlodLedgerRequest.setScreenName("DirectLedger");
                downlodLedgerRequest.setCode(bpcode);
                downlodLedgerRequest.setProcName("Comm_Genral_Report");
                downlodLedgerRequest.setDataBaseName(branch);
                downlodLedgerRequest.setRptFileName("CommunicationGeneralReport.rpt");
                downlodLedgerRequest.setType("1");
                downlodLedgerRequest.setBranch(type);
                downlodLedgerRequest.setFromDate(fromdate);
                downlodLedgerRequest.setToDate(todate);

                CustomProgressbar.showProgressBar(mContext, false);
                RetrofitClient.getClient1().downloadPdf(downlodLedgerRequest).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        CustomProgressbar.hideProgressBar();
                        LogMsg.d("Download_Pdf",response.toString());
                        if (response.code() == 200 && response.body() != null) {
                            manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse(response.body());
                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            long reference = manager.enqueue(request);
                        } else {
                            Toast.makeText(VendGRDetailsActivity.this, "Unable to download file", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                        CustomProgressbar.hideProgressBar();
                    }
                });
            }

        });
        vendGrdetailsBinding.GRExcelDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // new DownloadFilesInvoice(GeneralReportActivity.this, "https://pdf.theghgroup.com/Content/ExportedFiles/DirectLedger_C03566.pdf","GeneralReport");

                String string = branch_code;
                String[] parts = string.split("-");
                String branch = parts[0];
                String type = parts[1];
                ExcelData excelData = new ExcelData();
                excelData.setScreenName("Ledger");
                excelData.setCode(bpcode);
                excelData.setProcName("GR_Comm_Register_Mobile");
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
                            Toast.makeText(VendGRDetailsActivity.this, "Unable to download excel", Toast.LENGTH_SHORT).show();
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


    public void getVendGeneralReport() {
        CustomProgressbar.showProgressBar(mContext, false);
        RetrofitClient.getClient().getGRCommunication(fromdate, todate,bpcode,branch_code,"V").enqueue(new Callback<GRCommunication>() {
            @Override
            public void onResponse(Call<GRCommunication> call, Response<GRCommunication> response) {
                CustomProgressbar.hideProgressBar();
                if (response.code() == 200 && response.body() != null) {
                    GRCommunication grCommunication = response.body();
                    LogMsg.d("GRCommunication", response.toString());

                    if (grCommunication.getCommunicationRegisterResult().getCommunicationRegisterDetail().size() > 0) {
                        grCommunication.getCommunicationRegisterResult().getCommunicationRegisterDetail().get(0).getBranch();
                        Log.e("TAG", "Request: " + response.toString());
                        //CustomerLedger customerLedger = response.body();
                        List<CommunicationRegisterDetail> communicationRegisterDetails = grCommunication.getCommunicationRegisterResult().getCommunicationRegisterDetail();
                        Log.e("TAG", "Request: " + response.toString());

                        rvGRLedger = (RecyclerView) findViewById(R.id.rv_Vend_gr_Comm_details);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        vendGrdetailsBinding.rvVendGrCommDetails.setLayoutManager(linearLayoutManager);
                        VendGRAdapter generalReportAdapter = new VendGRAdapter(VendGRDetailsActivity.this,communicationRegisterDetails);
                        vendGrdetailsBinding.rvVendGrCommDetails.setAdapter(generalReportAdapter);

                        /*int vendGrTotalAmt = 0;

                        for (int i = 0;i<communicationRegisterDetails.size();i++){
                            String abc = communicationRegisterDetails.get(i).getTotalAmt().replaceAll(",","");
                            int totalamt = Integer.parseInt(abc);
                            vendGrTotalAmt = vendGrTotalAmt+totalamt;
                            DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                            vendGrdetailsBinding.vendGrRegShowTotal.setText("Total:  " +formatter.format(vendGrTotalAmt));

                            //tv.setText(""+bill);
                        }*/

                        try {
                            int paybill = 0;
                            for (int i = 0; i < communicationRegisterDetails.size(); i++) {
                                String abc = communicationRegisterDetails.get(i).getTotalAmt().replaceAll(",", "");
                                int billamt = Integer.parseInt(abc);
                                fileName = communicationRegisterDetails.get(i).getTotalAmt();
                                if (communicationRegisterDetails.get(i).getCustomerName() != "Total :") {
//                                    DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                                    //paymentCommDetailsBinding.payCommShowTotal.setText("Total:  " +formatter.format(paybill));
                                    vendGrdetailsBinding.vendGrRegShowTotal.setText(fileName);

                                }
                                else {
                                    paybill = paybill + billamt;
                                    DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                                    //paymentCommDetailsBinding.payCommShowTotal.setText("Total:  " +formatter.format(paybill));
                                    vendGrdetailsBinding.vendGrRegShowTotal.setText("Total:  " + formatter.format(paybill));
                                }
                            }

                        } catch (Exception ex) {

                        }

                    }
                    else if (grCommunication.getCommunicationRegisterResult().getLogMessage().equals("false")) {
                    }
                    else {
                        Toast.makeText(mContext, "" + grCommunication.getCommunicationRegisterResult().getLogMessage().getErrorMsg().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GRCommunication> call, Throwable t) {
                //CustomProgressbar.hideProgressBar();
                Toast.makeText(mContext, "Bad Request 400", Toast.LENGTH_SHORT).show();
                CustomProgressbar.hideProgressBar();

            }
        });

    }
}