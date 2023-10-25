package com.cbs.ghgroup.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.databinding.ActivityGhbillingPayNowBinding;

public class GHBillingPayNowActivity extends AppCompatActivity {
    ActivityGhbillingPayNowBinding payNowBinding;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_ghbilling_pay_now);
        payNowBinding = DataBindingUtil.setContentView(this, R.layout.activity_ghbilling_pay_now);
        mContext=GHBillingPayNowActivity.this;
        getIntiView();
    }
    public void getIntiView(){
        ImageView ivBack = findViewById(R.id.img_BackButton);
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.GH_Billing);

        payNowBinding.llPayBillToBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,PayBillToBillActivity.class);
                startActivity(intent);
            }
        });
        payNowBinding.llRoundAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,RoundPaymentActivity.class);
                startActivity(intent);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }});
    }
}