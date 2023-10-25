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
import com.cbs.ghgroup.adapter.PurchesRegisterAdapter;
import com.cbs.ghgroup.databinding.ActivityPurchesRegisterDeatilsBinding;
import com.cbs.ghgroup.model.purchesregister.PurchaseRegister;
import com.cbs.ghgroup.model.purchesregister.RegisterDetail;
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

public class PurchesRegisterDeatilsActivity extends AppCompatActivity {
    ActivityPurchesRegisterDeatilsBinding PurRegDeatilsBinding;
    Context mContext;
    String fromDate, toDate, code, BRANCH,Branch_Code,type,bpcode,IsCV;
    String file;
    //private ArrayList<RegisterDetail> registerDetails;
   // PurchaseRegisterResult purchaseRegisterResult;
    private WorkSheet workSheet;
    private File filePath = new File(Environment.getExternalStorageDirectory() + "/PurchaseReg.xls");
    String fileName;
    //private File file;
    private String fileExtName;
    RecyclerView rvPurchaseReg;
    DownloadManager manager;
    //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo_round);
    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purches_register_deatils);
        PurRegDeatilsBinding = DataBindingUtil.setContentView(this, R.layout.activity_purches_register_deatils);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        mContext = PurchesRegisterDeatilsActivity.this;

        fromDate = CommonMethods.getPrefsData(mContext,PrefrenceKey.FORM_DATE,"");
        toDate = CommonMethods.getPrefsData(mContext,PrefrenceKey.TO_DATE,"");
        code = CommonMethods.getPrefsData(mContext,PrefrenceKey.CODE,"");
        bpcode = CommonMethods.getPrefsData(mContext, PrefrenceKey.BP_CODE, "");
        BRANCH = CommonMethods.getPrefsData(mContext,PrefrenceKey.BRANCH,"");
        Branch_Code = CommonMethods.getPrefsData(mContext,PrefrenceKey.BRANCH_CODE,"");
        type=CommonMethods.getPrefsData(mContext,PrefrenceKey.TYPE,"");


        getId();

    }

    public void getId(){
        TextView tvBillAmt = findViewById(R.id.tv_BillAmt);
        TextView tv_BillAmt = findViewById(R.id.txt_purchaseTotal);
        ImageView ivBack = findViewById(R.id.img_BackButton);
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.purchase_register);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        CustomProgressbar.showProgressBar(mContext, false);
        RetrofitClient.getClient().getPurchaseRegister(fromDate,toDate,bpcode,Branch_Code,"C").enqueue(new Callback<PurchaseRegister>() {

            @Override
            public void onResponse(Call<PurchaseRegister> call, Response<PurchaseRegister> response) {
                CustomProgressbar.hideProgressBar();
                if (response.code() ==200 && response.body() !=null) {
                    PurchaseRegister purchaseRegister = response.body();
                    LogMsg.d("Purchase_Register",response.toString());

                    if (purchaseRegister.getPurchaseRegisterResult().getRegisterDetail().size() > 0) {
                        purchaseRegister.getPurchaseRegisterResult().getRegisterDetail().get(0).getBranch();
                        Log.e("TAG", "Request: " + response.toString());
                        List<RegisterDetail> registerDetails = purchaseRegister.getPurchaseRegisterResult().getRegisterDetail();
                        Log.e("TAG", "Request: " + response.toString());

                        rvPurchaseReg = (RecyclerView) findViewById(R.id.rcvPurchageRegister);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rvPurchaseReg.setLayoutManager(linearLayoutManager);
                        PurchesRegisterAdapter customAdapter = new PurchesRegisterAdapter(PurchesRegisterDeatilsActivity.this, registerDetails);
                        rvPurchaseReg.setAdapter(customAdapter);

                        int bill = 0;

                        for (int i = 0;i<registerDetails.size();i++){
                            String abc = registerDetails.get(i).getBillAmt().replaceAll(",","");
                            int billamt = Integer.parseInt(abc);
                            bill = bill+billamt;

                            DecimalFormat formatter = new DecimalFormat("##,##,##,##,###");
                            tv_BillAmt.setText("Total:  " +formatter.format(bill));

                        }

                    }
                    else if (purchaseRegister.getPurchaseRegisterResult().getLogMessage().equals("false")) {
                    } else {
                        Toast.makeText(mContext, "" + purchaseRegister.getPurchaseRegisterResult().getLogMessage().getErrorMsg().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PurchaseRegister> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                CustomProgressbar.hideProgressBar();

            }
        });


        //final String path  = "ExternalFilePath";

        PurRegDeatilsBinding.purchaseRegisterExcelDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = Branch_Code;
                String[] parts = string.split("-");
                String branch = parts[0];
                String type = parts[1];
                ExcelData excelData = new ExcelData();
                excelData.setScreenName("Ledger");
                excelData.setCode(bpcode);
                excelData.setProcName("A/R_Invice_QueryReport_MOBILE");
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
                            Toast.makeText(PurchesRegisterDeatilsActivity.this, "Unable to download excel", Toast.LENGTH_SHORT).show();
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