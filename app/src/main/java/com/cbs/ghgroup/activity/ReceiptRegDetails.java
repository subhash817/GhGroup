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
import com.cbs.ghgroup.adapter.ReceiptRegisterAdapter;
import com.cbs.ghgroup.databinding.ActivityReceiptRegDetailsBinding;
import com.cbs.ghgroup.model.receiptregister.ReceiptDetail;
import com.cbs.ghgroup.model.receiptregister.ReceiptRegister;
import com.cbs.ghgroup.model.userlist.ExcelData;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.CustomProgressbar;
import com.cbs.ghgroup.utils.LogMsg;
import com.cbs.ghgroup.utils.PrefrenceKey;

import org.library.worksheet.cellstyles.WorkSheet;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiptRegDetails extends AppCompatActivity {
    ActivityReceiptRegDetailsBinding receiptRegDetailsBinding;
    Context mContext;
    String fromDate, toDate, code, BRANCH,Branch_Code,type,bpcode,IsCV;
    String file;
    private WorkSheet workSheet;
    private File filePath = new File(Environment.getExternalStorageDirectory() + "/ReceiptReg.xls");
    String fileName;
    private String fileExtName;
    RecyclerView rvReceiptReg;
    DownloadManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiptRegDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_receipt_reg_details);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        mContext = ReceiptRegDetails.this;

        fromDate = CommonMethods.getPrefsData(mContext, PrefrenceKey.FORM_DATE,"");
        toDate = CommonMethods.getPrefsData(mContext,PrefrenceKey.TO_DATE,"");
        code = CommonMethods.getPrefsData(mContext,PrefrenceKey.CODE,"");
        bpcode = CommonMethods.getPrefsData(mContext, PrefrenceKey.BP_CODE, "");
        BRANCH = CommonMethods.getPrefsData(mContext,PrefrenceKey.BRANCH,"");
        Branch_Code = CommonMethods.getPrefsData(mContext,PrefrenceKey.BRANCH_CODE,"");
        type=CommonMethods.getPrefsData(mContext,PrefrenceKey.TYPE,"");

        getId();
    }

    public void getId(){
        ImageView ivBack = findViewById(R.id.img_BackButton);
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.receipt_register);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        CustomProgressbar.showProgressBar(mContext, false);
        RetrofitClient.getClient().getReceiptRegister(fromDate,toDate,bpcode,Branch_Code,"V").enqueue(new Callback<ReceiptRegister>() {

            @Override
            public void onResponse(Call<ReceiptRegister> call, Response<ReceiptRegister> response) {
                CustomProgressbar.hideProgressBar();
                if (response.code() ==200 && response.body() !=null) {
                    ReceiptRegister receiptRegister = response.body();
                    LogMsg.d("Receipt_Register",response.toString());

                    if (receiptRegister.getReceiptRegisterResult().getReceiptDetail().size() > 0) {
                        receiptRegister.getReceiptRegisterResult().getReceiptDetail().get(0).getBranch();
                        Log.e("TAG", "Request: " + response.toString());
                        List<ReceiptDetail> receiptRegisters = receiptRegister.getReceiptRegisterResult().getReceiptDetail();
                        Log.e("TAG", "Request: " + response.toString());

                        rvReceiptReg = (RecyclerView) findViewById(R.id.rcvReceiptRegister);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rvReceiptReg.setLayoutManager(linearLayoutManager);
                        ReceiptRegisterAdapter customAdapter = new ReceiptRegisterAdapter(ReceiptRegDetails.this, receiptRegisters);
                        rvReceiptReg.setAdapter(customAdapter);

                        /*try {
                            if (receiptRegisters.get(0).getTotalAmount().equalsIgnoreCase(receiptRegisters.get(0).getTotalAmount())) {
                                int receiptRegbill = 0;

                                for (int i = 0; i < receiptRegisters.size(); i++) {
                                    String abc = receiptRegisters.get(i).getTotalAmount().replaceAll(",", "");
                                    int totalamt = Integer.parseInt(abc);
                                    receiptRegbill = receiptRegbill + totalamt;
                                    DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                                    receiptRegDetailsBinding.vendReceiptRegShowTotal.setText("Total:  " +formatter.format(receiptRegbill));
                                }
                            }

                        } catch (Exception ex){

                        }*/

                       /* try {
                            if (receiptRegister.getReceiptRegisterResult().getReceiptDetail().get(0).getBranch().equalsIgnoreCase(receiptRegisters.get(0).getBranch())){
                                receiptRegDetailsBinding.vendReceiptRegShowTotal.setText("Total:  " + receiptRegister.getReceiptRegisterResult().getReceiptDetail().get(0).getTotalAmount());
                            }

                        } catch (Exception ex){

                        }*/

                        /*int receiptRegbill = 0;

                        for (int i = 0;i<receiptRegisters.size();i++){
                            String abc = receiptRegisters.get(i).getTotalAmount().replaceAll(",","");
                            int totalamt = Integer.parseInt(abc);
                            receiptRegbill = receiptRegbill+totalamt;
                            DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                            receiptRegDetailsBinding.vendReceiptRegShowTotal.setText("Total:  " +formatter.format(receiptRegbill));

                            //tv.setText(""+bill);
                        }*/

                        try {
                            int paybill = 0;
                            for (int i = 0; i < receiptRegisters.size(); i++) {
                                String abc = receiptRegisters.get(i).getTotalAmount().replaceAll(",", "");
                                int billamt = Integer.parseInt(abc);
                                fileName = receiptRegisters.get(i).getTotalAmount();
                                if (receiptRegisters.get(i).getBranch() != "Total :") {
//                                    DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                                    //paymentCommDetailsBinding.payCommShowTotal.setText("Total:  " +formatter.format(paybill));
                                    receiptRegDetailsBinding.vendReceiptRegShowTotal.setText(fileName);

                                }
                                else {
                                    paybill = paybill + billamt;
                                    DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                                    //paymentCommDetailsBinding.payCommShowTotal.setText("Total:  " +formatter.format(paybill));
                                    receiptRegDetailsBinding.vendReceiptRegShowTotal.setText("Total:  " + formatter.format(paybill));
                                }
                            }

                        } catch (Exception ex) {

                        }


                    }
                    else if (receiptRegister.getReceiptRegisterResult().getLogMessage().equals("false")) {
                    } else {
                        Toast.makeText(mContext, "" + receiptRegister.getReceiptRegisterResult().getLogMessage().getErrorMsg().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReceiptRegister> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                CustomProgressbar.hideProgressBar();

            }
        });


        receiptRegDetailsBinding.receiptRegisterExcelDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = Branch_Code;
                String[] parts = string.split("-");
                String branch = parts[0];
                String type = parts[1];
                ExcelData excelData = new ExcelData();
                excelData.setScreenName("Ledger");
                excelData.setCode(bpcode);
                excelData.setProcName("Payment_Register_Mobile");
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
                            Toast.makeText(ReceiptRegDetails.this, "Unable to download excel", Toast.LENGTH_SHORT).show();
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