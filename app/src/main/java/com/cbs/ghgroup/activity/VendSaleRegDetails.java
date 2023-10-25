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
import com.cbs.ghgroup.adapter.SaleRegisterAdapter;
import com.cbs.ghgroup.databinding.ActivityVendSaleRegDetailsBinding;
import com.cbs.ghgroup.model.saleregister.SaleDetail;
import com.cbs.ghgroup.model.saleregister.SaleRegister;
import com.cbs.ghgroup.model.userlist.ExcelData;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.CustomProgressbar;
import com.cbs.ghgroup.utils.LogMsg;
import com.cbs.ghgroup.utils.PrefrenceKey;

import org.library.worksheet.cellstyles.WorkSheet;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendSaleRegDetails extends AppCompatActivity {
    ActivityVendSaleRegDetailsBinding vendSaleRegDetailsBinding;
    Context mContext;
    String fromDate, toDate, code, BRANCH,Branch_Code,type,bpcode,IsCV;
    String file;
    private WorkSheet workSheet;
    String fileName;
    //private File file;
    private String fileExtName;
    RecyclerView rvSaleReg;
    DownloadManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vendSaleRegDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_vend_sale_reg_details);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        mContext = VendSaleRegDetails.this;
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
        setActivityName.setText(R.string.sale_register);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        CustomProgressbar.showProgressBar(mContext, false);
        RetrofitClient.getClient().getSaleRegister(fromDate,toDate,bpcode,Branch_Code,"V").enqueue(new Callback<SaleRegister>() {

            @Override
            public void onResponse(Call<SaleRegister> call, Response<SaleRegister> response) {
                CustomProgressbar.hideProgressBar();
                if (response.code() == 200 && response.body() != null) {
                    SaleRegister saleRegister = response.body();
                    LogMsg.d("Sale_Register", response.toString());

                    if (saleRegister.getSaleRegisterResult().getSaleDetail().size() > 0) {
                        saleRegister.getSaleRegisterResult().getSaleDetail().get(0).getBranch();
                        Log.e("TAG", "Request: " + response.toString());
                        List<SaleDetail> registerDetails = saleRegister.getSaleRegisterResult().getSaleDetail();
                        Log.e("TAG", "Request: " + response.toString());

                        rvSaleReg = (RecyclerView) findViewById(R.id.rcvSaleRegRegister);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rvSaleReg.setLayoutManager(linearLayoutManager);
                        SaleRegisterAdapter customAdapter = new SaleRegisterAdapter(VendSaleRegDetails.this, registerDetails);
                        rvSaleReg.setAdapter(customAdapter);

                        /*int saleRegbill = 0;

                        for (int i = 0;i<registerDetails.size();i++){
                            String abc = registerDetails.get(i).getBillAmount().replaceAll(",","");
                            int totalamt = Integer.parseInt(abc);
                            saleRegbill = saleRegbill+totalamt;
                            DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                            vendSaleRegDetailsBinding.vendSaleRegShowTotal.setText("Total:  " +formatter.format(saleRegbill));

                            //tv.setText(""+bill);
                        }*/

                        try {
                            int paybill = 0;
                            for (int i = 0; i < registerDetails.size(); i++) {
                                String abc = registerDetails.get(i).getBillAmount().replaceAll(",", "");
                                int billamt = Integer.parseInt(abc);
                                fileName = registerDetails.get(i).getBillAmount();
                                if (registerDetails.get(i).getCustomerName() != "Total :") {
//                                    DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                                    //paymentCommDetailsBinding.payCommShowTotal.setText("Total:  " +formatter.format(paybill));
                                    vendSaleRegDetailsBinding.vendSaleRegShowTotal.setText(fileName);

                                }
                                else {
                                    paybill = paybill + billamt;
                                    DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                                    //paymentCommDetailsBinding.payCommShowTotal.setText("Total:  " +formatter.format(paybill));
                                    vendSaleRegDetailsBinding.vendSaleRegShowTotal.setText("Total:  " + formatter.format(paybill));
                                }
                            }

                        } catch (Exception ex) {

                        }

                    }
                    else if (saleRegister.getSaleRegisterResult().getLogMessage().equals("false")) {
                    }
                    else {
                        Toast.makeText(mContext, "" + saleRegister.getSaleRegisterResult().getLogMessage().getErrorMsg().toString(), Toast.LENGTH_SHORT).show();
                    }

                }
//
            }

            @Override
            public void onFailure(Call<SaleRegister> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                CustomProgressbar.hideProgressBar();

            }
        });

        vendSaleRegDetailsBinding.saleRegisterExcelDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = Branch_Code;
                String[] parts = string.split("-");
                String branch = parts[0];
                String type = parts[1];
                ExcelData excelData = new ExcelData();
                excelData.setScreenName("Ledger");
                excelData.setCode(bpcode);
                excelData.setProcName("A/P_Invice_QueryReport_Mobile");
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
                            Toast.makeText(VendSaleRegDetails.this, "Unable to download excel", Toast.LENGTH_SHORT).show();
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