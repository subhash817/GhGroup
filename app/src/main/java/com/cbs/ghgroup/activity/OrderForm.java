package com.cbs.ghgroup.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.databinding.ActivityOrderFormBinding;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.PrefrenceKey;

public class OrderForm extends AppCompatActivity {
    ActivityOrderFormBinding orderFormBinding;
    Context mContext;
    String lastUpdatedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderFormBinding = DataBindingUtil.setContentView(this,R.layout.activity_order_form);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        mContext = OrderForm.this;
        lastUpdatedTime = CommonMethods.getPrefsData(mContext, PrefrenceKey.UPDATED_TIME ,"");
        getId();
    }

    private void getId() {
        ImageView ivBack = findViewById(R.id.img_BackButton);
        TextView setActivityName = findViewById(R.id.txtHeader);
        TextView lastUpdate = findViewById(R.id.txt_order_last_update);
        setActivityName.setText(R.string.order);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

        String adjusted = lastUpdatedTime.replaceAll("         \\r\\n ", "");

        lastUpdate.setText("Last updated on: " +adjusted);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        orderFormBinding.fabOrderPortLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenOrderFabLog();
            }
        });

        orderFormBinding.llOrderFormEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,OrderEntryForm.class);
                startActivity(intent);
            }
        });

        orderFormBinding.llOrderReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,OrderReport.class);
                startActivity(intent);
            }
        });
    }

    public void OpenOrderFabLog(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(OrderForm.this);
        mBuilder.setCancelable(true);
        View mView = getLayoutInflater().inflate(R.layout.contact_dialogbox, null);

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

}