package com.cbs.ghgroup.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.adapter.CommGenLedgerAdapter;
import com.cbs.ghgroup.databinding.ActivityCommGenLedgerDetailsBinding;
import com.cbs.ghgroup.model.DownlodLedgerRequest;
import com.cbs.ghgroup.model.commgenledger.CommGenLedger;
import com.cbs.ghgroup.model.commgenledger.GeneralReportDetail;
import com.cbs.ghgroup.model.userlist.ExcelData;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.CustomProgressbar;
import com.cbs.ghgroup.utils.PrefrenceKey;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Comm_GenLedger_Details extends AppCompatActivity {
    Context mContext;
    ActivityCommGenLedgerDetailsBinding commGenLedgerDetailsBinding;
    RecyclerView rvcCommGenLedger;
    String fromdate, type, todate, bpcode, branches, branch_code,IsCV;
    private ArrayList<CommGenLedger> genLedgers;

    private Button btnDownload;
    long refid;
    DownloadManager manager;
    private File file;
    private String fileExtName;
    String fileName, str_outstring;
    String url;
    TextView outstanding, tvTextHide;
    ImageView ImimgMore;
    LinearLayout llbasicDetail;
    HorizontalScrollView hsdetailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commGenLedgerDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_comm_gen_ledger_details);

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        mContext = Comm_GenLedger_Details.this;
        fromdate = CommonMethods.getPrefsData(mContext, PrefrenceKey.FORM_DATE, "");
        todate = CommonMethods.getPrefsData(mContext, PrefrenceKey.TO_DATE, "");
        bpcode = CommonMethods.getPrefsData(mContext, PrefrenceKey.BP_CODE, "");
        branches = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH, "");
        branch_code = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH_CODE, "");
        type = CommonMethods.getPrefsData(mContext, PrefrenceKey.TYPE, "");

        outstanding = findViewById(R.id.tv_Comm_gen_OutStanding);
        getId();
        getCommGenledDetails();

    }

    public void getCommGenledDetails() {

        CustomProgressbar.showProgressBar(mContext, false);
        RetrofitClient.getClient().getCommGenLedger(fromdate, todate, bpcode, type, branch_code,"C").enqueue(new Callback<CommGenLedger>() {
            @Override
            public void onResponse(Call<CommGenLedger> call, Response<CommGenLedger> response) {
                CustomProgressbar.hideProgressBar();
                if (response.code() == 200 && response.body() != null) {
                    CommGenLedger commGenLedger = response.body();
                    Log.d("TAG", "Request: " + response.toString());


                    if (commGenLedger.getGeneralReportResult().getGeneralReportDetail().size() > 0) {
                       // CommonMethods.setPrefsData(mContext, PrefrenceKey.DOC_ENTRY, response.body().getGeneralReportResult().getGeneralReportDetail().get(0).getDocEntry());
                        commGenLedger.getGeneralReportResult().getGeneralReportDetail().get(0).getBranch();

                        Log.e("TAG", "Request: " + response.toString());
                        //CustomerLedger customerLedger = response.body();
                        List<GeneralReportDetail> generalReportDetails = commGenLedger.getGeneralReportResult().getGeneralReportDetail();
                        Log.e("TAG", "Request: " + response.toString());
                        try {

                            if (generalReportDetails.get(0).getFlag().equalsIgnoreCase("D") ){
                                outstanding.setText("" + generalReportDetails.get(0).getOutStanding());
                                outstanding.setTextColor(ContextCompat.getColor(mContext,R.color.darkblack));
                            } else if(generalReportDetails.get(0).getFlag().equalsIgnoreCase("C") ) {
                                outstanding.setText("" + generalReportDetails.get(0).getOutStanding());
                                outstanding.setTextColor(ContextCompat.getColor(mContext,R.color.darkblack));
                            } else if(generalReportDetails.get(0).getFlag().equalsIgnoreCase("") ){
                                outstanding.setText("" + generalReportDetails.get(0).getOutStanding());
                                outstanding.setTextColor(ContextCompat.getColor(mContext,R.color.darkblack));
                            }

                            //outstanding.setText("" + generalReportDetails.get(0).getOutStanding());
                        } catch (Exception ex) {
                        }
                        rvcCommGenLedger = (RecyclerView) findViewById(R.id.rv_Comm_gen_ledger_details);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rvcCommGenLedger.setLayoutManager(linearLayoutManager);
                        CommGenLedgerAdapter customAdapter = new CommGenLedgerAdapter(Comm_GenLedger_Details.this, generalReportDetails);
                        rvcCommGenLedger.setAdapter(customAdapter);

                        try {
                            if (generalReportDetails.get(0).getOutStanding().equalsIgnoreCase(generalReportDetails.get(0).getOutStanding())){
                                commGenLedgerDetailsBinding.CommGenLedgerShowTotal.setText("Total:  " + generalReportDetails.get(0).getOutStanding());
                            }

                        } catch (Exception ex){

                        }

                    }
                    else if (commGenLedger.getGeneralReportResult().getLogMessage().equals("false")) {
                    } else {
                        Toast.makeText(mContext, "" + commGenLedger.getGeneralReportResult().getLogMessage().getErrorMsg().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<CommGenLedger> call, Throwable t) {
                Toast.makeText(mContext, "Bad Request 400", Toast.LENGTH_SHORT).show();
                CustomProgressbar.hideProgressBar();
            }
        });

    }

    public void getId() {
        rvcCommGenLedger = (RecyclerView) findViewById(R.id.rv_Comm_gen_ledger_details);
        TextView setActivityName = findViewById(R.id.txtHeader);

        setActivityName.setText(R.string.comm_general_ledger);
        ImageView ivBack = findViewById(R.id.img_BackButton);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        commGenLedgerDetailsBinding.btnCommGenLedPdf.setOnClickListener(new View.OnClickListener() {

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
                        Log.e("Download PDF", response.toString());
                        if (response.code() == 200 && response.body() != null) {
                            manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse(response.body());
                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            long reference = manager.enqueue(request);
                        }
                        else {
                            Toast.makeText(Comm_GenLedger_Details.this, "Unable to download file", Toast.LENGTH_SHORT).show();
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

        commGenLedgerDetailsBinding.btnCommGenLedExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = branch_code;
                String[] parts = string.split("-");
                String branch = parts[0];
                String type = parts[1];
                ExcelData excelData=new ExcelData();
                excelData.setScreenName("Ledger");
                excelData.setCode(bpcode);
                excelData.setProcName("Comm_Genral_Report_GRID_Mobile");
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
                        if (response.code()==200 && response.body()!=null){
                            Log.d("Download Excel",response.toString());
                            manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse(response.body());
                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            long reference = manager.enqueue(request);
                        }
                        else{
                            Toast.makeText(Comm_GenLedger_Details.this, "Unable to download file Excel", Toast.LENGTH_SHORT).show();

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