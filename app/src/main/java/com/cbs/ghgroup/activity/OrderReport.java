package com.cbs.ghgroup.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.databinding.ActivityOrderReportBinding;

public class OrderReport extends AppCompatActivity {
    ActivityOrderReportBinding orderReportBinding;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderReportBinding = DataBindingUtil.setContentView(this,R.layout.activity_order_report);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        mContext = OrderReport.this;

        getId();
    }
    private void getId(){
        ImageView ivBack = findViewById(R.id.img_BackButton);
        TextView setActivityName = findViewById(R.id.txtHeader);
        RelativeLayout rlORCustBalance = findViewById(R.id.rl_OR_custBalance);
        setActivityName.setText(R.string.order_report);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        orderReportBinding.llPendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,OrderReportDetails.class);
                startActivity(intent);
            }
        });

        orderReportBinding.llApprovedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,OrderReportDetails.class);
                startActivity(intent);
            }
        });

        orderReportBinding.llInvoiceGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,OrderReportDetails.class);
                startActivity(intent);
            }
        });

        orderReportBinding.llLrUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,OrderReportDetails.class);
                startActivity(intent);
            }
        });

        orderReportBinding.rlORCustBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,CustomerBalance.class);
                startActivity(intent);
            }
        });

        orderReportBinding.rlOROrderReportDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,OrderReportDetails.class);
                startActivity(intent);
            }
        });

    }
}