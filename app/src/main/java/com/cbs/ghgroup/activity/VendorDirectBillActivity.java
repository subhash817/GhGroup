package com.cbs.ghgroup.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.databinding.ActivityVendorDirectBillBinding;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.PrefrenceKey;

public class VendorDirectBillActivity extends AppCompatActivity {
    LinearLayout llVendCommGenRegister,llVendBillRegister,llVendGRRegister,llVendPayRegister,llVendPendBillsRegister;
    Context mContext;
    ActivityVendorDirectBillBinding vendorDirectBillBinding;
    String lastUpdatedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vendorDirectBillBinding = DataBindingUtil.setContentView(this,R.layout.activity_vendor_direct_bill);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        mContext=VendorDirectBillActivity.this;
        lastUpdatedTime = CommonMethods.getPrefsData(mContext, PrefrenceKey.UPDATED_TIME ,"");
        getId();
        buttonOnClick();

    }

    private void getId(){
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.direct_billings);
        llVendCommGenRegister=findViewById(R.id.ll_vendCommGenRegister);
        llVendBillRegister=findViewById(R.id.ll_vendBillRegister);
        llVendGRRegister=findViewById(R.id.ll_vendGRRegister);
        llVendPayRegister=findViewById(R.id.ll_vendPayRegister);
        llVendPendBillsRegister=findViewById(R.id.ll_vendDirPendBillsRegister);
        ImageView ivBack = findViewById(R.id.img_BackButton);
        TextView lastUpdate = findViewById(R.id.txt_vendDirectlast_update);

        String adjusted = lastUpdatedTime.replaceAll("         \\r\\n ", "");

        lastUpdate.setText("Last updated on: " +adjusted);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        vendorDirectBillBinding.fabVendDirectBillingLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenVendDirectBillingFab();
            }
        });

    }

    public void OpenVendDirectBillingFab(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(VendorDirectBillActivity.this);
        mBuilder.setCancelable(true);
        View mView = getLayoutInflater().inflate(R.layout.contact_dialogbox, null);

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void buttonOnClick(){

        llVendCommGenRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,VendorCommGenLedger.class);
                startActivity(intent);
            }
        });

        llVendBillRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,VendorBillRegActivity.class);
                startActivity(intent);
            }
        });
        llVendGRRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,VendorGRActivity.class);
                startActivity(intent);
            }
        });
        llVendPayRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,VendorPaymentCommActivity.class);
                startActivity(intent);
            }
        });

        llVendPendBillsRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,VendorDirectPendBills.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}