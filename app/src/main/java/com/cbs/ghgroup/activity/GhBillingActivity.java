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
import com.cbs.ghgroup.databinding.ActivityGhBillingBinding;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.PrefrenceKey;

public class GhBillingActivity extends AppCompatActivity {
    Context mContext;
    LinearLayout llGenLedger, llPenBill, llRecRegister, llSaleRegister, llDeNoRegister;
    ActivityGhBillingBinding ghBillingBinding;
    String lastUpdatedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ghBillingBinding = DataBindingUtil.setContentView(this, R.layout.activity_gh_billing);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        mContext=GhBillingActivity.this;
        lastUpdatedTime = CommonMethods.getPrefsData(mContext, PrefrenceKey.UPDATED_TIME ,"");
        getId();
        buttonOnClick();
    }

    private void getId() {
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.gh_billing);
        llGenLedger = findViewById(R.id.ll_vendGeneral_ledger);
        llPenBill = findViewById(R.id.ll_vendPending_bill);
        llRecRegister = findViewById(R.id.ll_vendReceipt_reg);
        llSaleRegister = findViewById(R.id.ll_vendSale_reg);
        llDeNoRegister = findViewById(R.id.ll_vendDebit_note_reg);
        ImageView ivBack = findViewById(R.id.img_BackButton);
        TextView lastUpdate = findViewById(R.id.txt_vendGHlast_update);

        String adjusted = lastUpdatedTime.replaceAll("         \\r\\n ", "");

        lastUpdate.setText("Last updated on: " +adjusted);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ghBillingBinding.fabVendGHBillingLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenVendGHBillingFab();
            }
        });
    }

    public void OpenVendGHBillingFab(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(GhBillingActivity.this);
        mBuilder.setCancelable(true);
        View mView = getLayoutInflater().inflate(R.layout.contact_dialogbox, null);

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }



    private void buttonOnClick() {
        llGenLedger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, VendGeneralLedgerActivity.class);
                startActivity(intent);
            }
        });
        llPenBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GhBillingActivity.this, VendPendingBillActivity.class);
                startActivity(intent);
            }
        });
        llRecRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GhBillingActivity.this, ReceipetRegisterActivity.class);
                startActivity(intent);
            }
        });
        llSaleRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GhBillingActivity.this, VendSaleRegister.class);
                startActivity(intent);
            }
        });
        llDeNoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GhBillingActivity.this, VendDebitNoteRegister.class);
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