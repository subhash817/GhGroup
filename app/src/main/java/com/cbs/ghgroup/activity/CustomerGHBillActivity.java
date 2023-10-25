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
import com.cbs.ghgroup.databinding.ActivityCustomerGHBillBinding;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.PrefrenceKey;

public class CustomerGHBillActivity extends AppCompatActivity {
    LinearLayout llGenLedger,llPenBill,llPurchase,llPayment,llCreditNoRegister;
    Context mContext;
    String lastUpdatedTime;
    ActivityCustomerGHBillBinding customerGHBillBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = CustomerGHBillActivity.this;
        lastUpdatedTime = CommonMethods.getPrefsData(mContext, PrefrenceKey.UPDATED_TIME ,"");
        customerGHBillBinding = DataBindingUtil.setContentView(this, R.layout.activity_customer_g_h_bill);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        getID();
    }
    public void getID(){
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.gh_billing);
        llGenLedger = findViewById(R.id.ll_general_ledger);
        llPenBill = findViewById(R.id.ll_pending_bill);
        llPurchase = findViewById(R.id.ll_purchase_reg);
        llPayment = findViewById(R.id.ll_payment_reg);
        llCreditNoRegister = findViewById(R.id.ll_credit_note_reg);
        TextView lastUpdate = findViewById(R.id.txt_custGHlast_update);
        ImageView ivBack = findViewById(R.id.img_BackButton);

        String adjusted = lastUpdatedTime.replaceAll("         \\r\\n ", "");

        lastUpdate.setText("Last updated on: " +adjusted);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        llGenLedger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, GeneralLedgerActivity.class);
                startActivity(intent);
            }
        });
        llPenBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PendingBillActivity.class);
                startActivity(intent);
            }
        });
        llPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PurchaseRegisterActivity.class);
                startActivity(intent);
            }
        });
        llPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PaymentRegisterActivity.class);
                startActivity(intent);
            }
        });
        llCreditNoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CreditNoteRegisterActivity.class);
                startActivity(intent);
            }
        });

        customerGHBillBinding.fabCustGhBillLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCustGhBillFab();
            }
        });

    }

    public void OpenCustGhBillFab(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CustomerGHBillActivity.this);
        mBuilder.setCancelable(true);
        View mView = getLayoutInflater().inflate(R.layout.contact_dialogbox, null);

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }


    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}