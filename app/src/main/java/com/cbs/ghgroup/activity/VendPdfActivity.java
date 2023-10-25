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
import com.cbs.ghgroup.adapter.VendGenLedgerAdapter;
import com.cbs.ghgroup.databinding.ActivityVendPdfBinding;
import com.cbs.ghgroup.model.DownlodLedgerRequest;
import com.cbs.ghgroup.model.customerledger.CustomerLedger2;
import com.cbs.ghgroup.model.customerledger.CustomerLedger2Result;
import com.cbs.ghgroup.model.customerledger.LedgerDetail;
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

public class VendPdfActivity extends AppCompatActivity {
    Context mContext;
    ActivityVendPdfBinding vendPdfBinding;
    RecyclerView rvcGenLedger;
    String fromdate, type, todate, bpcode, branches, branch_code,IsCV;
    private ArrayList<CustomerLedger2> customerLedgers;
    CustomerLedger2Result customerLedgerResult;
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
        vendPdfBinding = DataBindingUtil.setContentView(this,R.layout.activity_vend_pdf);

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        mContext = VendPdfActivity.this;
        fromdate = CommonMethods.getPrefsData(mContext, PrefrenceKey.FORM_DATE, "");
        todate = CommonMethods.getPrefsData(mContext, PrefrenceKey.TO_DATE, "");
        bpcode = CommonMethods.getPrefsData(mContext, PrefrenceKey.BP_CODE, "");
        branches = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH, "");
        branch_code = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH_CODE, "");
        type = CommonMethods.getPrefsData(mContext, PrefrenceKey.TYPE, "");

        outstanding = findViewById(R.id.tv_OutStanding);
        getId();
        getGenledDetails();

    }

    public void getGenledDetails() {

        CustomProgressbar.showProgressBar(mContext, false);
        RetrofitClient.getClient().getCustomerLedger(fromdate, todate, bpcode, type, branch_code,"V").enqueue(new Callback<CustomerLedger2>() {
            @Override
            public void onResponse(Call<CustomerLedger2> call, Response<CustomerLedger2> response) {
                CustomProgressbar.hideProgressBar();
                if (response.code() == 200 && response.body() != null) {
                    CustomerLedger2 customerLedger = response.body();

                    if (customerLedger.getCustomerLedger2Result().getLedgerDetail().size() > 0) {
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.DOC_ENTRY, response.body().getCustomerLedger2Result().getLedgerDetail().get(0).getDocEntry());
                        customerLedger.getCustomerLedger2Result().getLedgerDetail().get(0).getBranch();

                        Log.e("TAG", "Request: " + response.toString());
                        //CustomerLedger customerLedger = response.body();
                        List<LedgerDetail> ledgerDetails = customerLedger.getCustomerLedger2Result().getLedgerDetail();
                        Log.e("TAG", "Request: " + response.toString());
                        try {

                            if (ledgerDetails.get(0).getFlag().equalsIgnoreCase("D") ){
                                outstanding.setText("" + ledgerDetails.get(0).getOutStanding());
                                outstanding.setTextColor(ContextCompat.getColor(mContext,R.color.darkblack));
                            } else if(ledgerDetails.get(0).getFlag().equalsIgnoreCase("C") ) {
                                outstanding.setText("" + ledgerDetails.get(0).getOutStanding());
                                outstanding.setTextColor(ContextCompat.getColor(mContext,R.color.darkblack));
                            } else if(ledgerDetails.get(0).getFlag().equalsIgnoreCase("") ){
                                outstanding.setText("" + ledgerDetails.get(0).getOutStanding());
                                outstanding.setTextColor(ContextCompat.getColor(mContext,R.color.darkblack));
                            }

                            //outstanding.setText("" + ledgerDetails.get(0).getOutStanding());
                        } catch (Exception ex) {
                        }
                        rvcGenLedger = (RecyclerView) findViewById(R.id.rv_vendgen_ledger_details);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rvcGenLedger.setLayoutManager(linearLayoutManager);
                        VendGenLedgerAdapter customAdapter = new VendGenLedgerAdapter(VendPdfActivity.this, ledgerDetails);
                        rvcGenLedger.setAdapter(customAdapter);

                        try {
                            if (ledgerDetails.get(0).getOutStanding().equalsIgnoreCase(ledgerDetails.get(0).getOutStanding())){
                                vendPdfBinding.vendGenLedgerShowTotal.setText("Total:  " + ledgerDetails.get(0).getOutStanding());
                            }

                        } catch (Exception ex){

                        }

                    }
                    else if (customerLedger.getCustomerLedger2Result().getLogMessage().equals("false")) {
                    } else {
                        Toast.makeText(mContext, "" + customerLedger.getCustomerLedger2Result().getLogMessage().getErrorMsg().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<CustomerLedger2> call, Throwable t) {
                Toast.makeText(mContext, "Bad Request 400", Toast.LENGTH_SHORT).show();
                CustomProgressbar.hideProgressBar();
            }
        });

    }

    public void getId() {
        rvcGenLedger = (RecyclerView) findViewById(R.id.rv_gen_ledger_details);
        TextView setActivityName = findViewById(R.id.txtHeader);

        setActivityName.setText(R.string.pdf_details);
        ImageView ivBack = findViewById(R.id.img_BackButton);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        vendPdfBinding.PDFDOWNLOD.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String string = branch_code;
                String[] parts = string.split("-");
                String branch = parts[0];
                String type = parts[1];
                DownlodLedgerRequest downlodLedgerRequest = new DownlodLedgerRequest();
                downlodLedgerRequest.setScreenName("Ledger");
                downlodLedgerRequest.setCode(bpcode);
                downlodLedgerRequest.setProcName("Business_Partner_Ledger");
                downlodLedgerRequest.setDataBaseName(branch);
                downlodLedgerRequest.setRptFileName("BusinessPartnerLedger.rpt");
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
                            Toast.makeText(VendPdfActivity.this, "Unable to download file", Toast.LENGTH_SHORT).show();
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

        vendPdfBinding.btnGenLedExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = branch_code;
                String[] parts = string.split("-");
                String branch = parts[0];
                String type = parts[1];
                ExcelData excelData=new ExcelData();
                excelData.setScreenName("Ledger");
                excelData.setCode(bpcode);
                excelData.setProcName("Business_Partner_Ledger_Grid_Mobile");
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
                        if (response.code()==200 && response.body()!=null){
                            Log.d("Download Excel",response.toString());
                            manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse(response.body());
                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            long reference = manager.enqueue(request);
                        }
                        else{
                            Toast.makeText(VendPdfActivity.this, "Unable to download file Excel", Toast.LENGTH_SHORT).show();

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