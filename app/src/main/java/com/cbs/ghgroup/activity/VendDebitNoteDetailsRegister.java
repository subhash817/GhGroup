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
import com.cbs.ghgroup.adapter.DebitRegisterAdapter;
import com.cbs.ghgroup.databinding.ActivityVendDebitNoteDetailsRegisterBinding;
import com.cbs.ghgroup.model.debitregister.DebitNoteDetail;
import com.cbs.ghgroup.model.debitregister.DebitNoteRegister;
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

public class VendDebitNoteDetailsRegister extends AppCompatActivity {
    ActivityVendDebitNoteDetailsRegisterBinding vendDebitNoteDetailsRegisterBinding;
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
        vendDebitNoteDetailsRegisterBinding = DataBindingUtil.setContentView(this,R.layout.activity_vend_debit_note_details_register);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        mContext = VendDebitNoteDetailsRegister.this;

        fromDate = CommonMethods.getPrefsData(mContext, PrefrenceKey.FORM_DATE, "");
        toDate = CommonMethods.getPrefsData(mContext, PrefrenceKey.TO_DATE, "");
        code = CommonMethods.getPrefsData(mContext, PrefrenceKey.BP_CODE, "");
        branch_code = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH_CODE, "");
        getId();
    }
    public void getId() {
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.debit_note_register);

        ImageView ivBack = findViewById(R.id.img_BackButton);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        CustomProgressbar.showProgressBar(mContext, false);

        RetrofitClient.getClient().getDebitRegister(fromDate, toDate, code, branch_code,"V").enqueue(new Callback<DebitNoteRegister>() {
            @Override
            public void onResponse(Call<DebitNoteRegister> call, Response<DebitNoteRegister> response) {
                CustomProgressbar.hideProgressBar();
                if (response.code() == 200 && response.body() != null) {
                    DebitNoteRegister debitNoteRegister = response.body();
                    LogMsg.d("CreditRegister", response.toString());
                    if (debitNoteRegister.getDebitNoteRegisterResult().getDebitNoteDetail().size() > 0) {
                        debitNoteRegister.getDebitNoteRegisterResult().getDebitNoteDetail().get(0).getBranch();
                        List<DebitNoteDetail> debitNoteDetails = debitNoteRegister.getDebitNoteRegisterResult().getDebitNoteDetail();
                        rvCredNoteReg = (RecyclerView) findViewById(R.id.rcvCreditRegister);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        vendDebitNoteDetailsRegisterBinding.rcvDebitRegister.setLayoutManager(linearLayoutManager);
                        DebitRegisterAdapter debitRegisterAdapter = new DebitRegisterAdapter(VendDebitNoteDetailsRegister.this, debitNoteDetails);
                        vendDebitNoteDetailsRegisterBinding.rcvDebitRegister.setAdapter(debitRegisterAdapter);

                        /*int debitnotRegbill = 0;

                        for (int i = 0;i<debitNoteDetails.size();i++){
                            String abc = debitNoteDetails.get(i).getTotalAmount().replaceAll(",","");
                            int totalamt = Integer.parseInt(abc);
                            debitnotRegbill = debitnotRegbill+totalamt;
                            DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                            vendDebitNoteDetailsRegisterBinding.vendDebitNoteRegShowTotal.setText("Total:  " +formatter.format(debitnotRegbill));

                            //tv.setText(""+bill);
                        }*/

                        try {
                            int paybill = 0;
                            for (int i = 0; i < debitNoteDetails.size(); i++) {
                                String abc = debitNoteDetails.get(i).getTotalAmount().replaceAll(",", "");
                                int billamt = Integer.parseInt(abc);
                                fileName = debitNoteDetails.get(i).getTotalAmount();
                                if (debitNoteDetails.get(i).getCustomerName() != "Total :") {
//                                    DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                                    //paymentCommDetailsBinding.payCommShowTotal.setText("Total:  " +formatter.format(paybill));
                                    vendDebitNoteDetailsRegisterBinding.vendDebitNoteRegShowTotal.setText(fileName);

                                }
                                else {
                                    paybill = paybill + billamt;
                                    DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                                    //paymentCommDetailsBinding.payCommShowTotal.setText("Total:  " +formatter.format(paybill));
                                    vendDebitNoteDetailsRegisterBinding.vendDebitNoteRegShowTotal.setText("Total:  " + formatter.format(paybill));
                                }
                            }

                        } catch (Exception ex) {

                        }

                    }
                    else if (debitNoteRegister.getDebitNoteRegisterResult().getLogMessage().equals("false")) {
                    }
                    else {
                        Toast.makeText(mContext, "" + debitNoteRegister.getDebitNoteRegisterResult().getLogMessage().getErrorMsg().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DebitNoteRegister> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                CustomProgressbar.hideProgressBar();

            }
        });

        vendDebitNoteDetailsRegisterBinding.debitNoteRegExcelDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = branch_code;
                String[] parts = string.split("-");
                String branch = parts[0];
                String type = parts[1];
                ExcelData excelData = new ExcelData();
                excelData.setScreenName("Ledger");
                excelData.setCode(code);
                excelData.setProcName("DebitNote_Register_Mobile");
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
                            Toast.makeText(VendDebitNoteDetailsRegister.this, "Unable to download excel", Toast.LENGTH_SHORT).show();
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