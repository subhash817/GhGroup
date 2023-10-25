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
import com.cbs.ghgroup.databinding.ActivityDirectBillBinding;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.PrefrenceKey;

public class DirectBillActivity extends AppCompatActivity {
    LinearLayout llCommGenLedger,llBillRegister,llGRRegister,llPayRegister;
    Context mContext;
    String lastUpdatedTime;
    ActivityDirectBillBinding directBillBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        directBillBinding = DataBindingUtil.setContentView(this, R.layout.activity_direct_bill);
        mContext=DirectBillActivity.this;
        lastUpdatedTime = CommonMethods.getPrefsData(mContext, PrefrenceKey.UPDATED_TIME ,"");
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

        getId();
        buttonOnClick();
    }
    private void getId(){
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.direct_billings);
        llCommGenLedger=findViewById(R.id.ll_Comm_gen_led_Register);
        llBillRegister=findViewById(R.id.ll_BillRegister);
        llGRRegister=findViewById(R.id.ll_GRRegister);
        llPayRegister=findViewById(R.id.ll_PayRegister);
        ImageView ivBack = findViewById(R.id.img_BackButton);
        TextView lastUpdate = findViewById(R.id.txt_custDirectlast_update);

        String adjusted = lastUpdatedTime.replaceAll("         \\r\\n ", "");

        lastUpdate.setText("Last updated on: " +adjusted);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        directBillBinding.fabCustDirectBillingLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCustDirectBillingFab();
            }
        });

    }

    public void OpenCustDirectBillingFab(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(DirectBillActivity.this);
        mBuilder.setCancelable(true);
        View mView = getLayoutInflater().inflate(R.layout.contact_dialogbox, null);

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void buttonOnClick(){
        llCommGenLedger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,CommGeneralLedger.class);
                startActivity(intent);
            }
        });

        llBillRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,BillRegisterActivity.class);
                startActivity(intent);
            }
        });
        llGRRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,GRRegisterActivity.class);
                startActivity(intent);
            }
        });
        llPayRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,PaymentCommunicationActivity.class);
                startActivity(intent);
            }
        });
        directBillBinding.llDirectPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, DirectPendingBillsActivity.class);
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