package com.cbs.ghgroup.activity;

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
import com.cbs.ghgroup.databinding.ActivityGraphMainDashboradBinding;

public class GraphMainDashboard extends AppCompatActivity {
    ActivityGraphMainDashboradBinding graphMainDashboradBinding;
    Context mContext;
    LinearLayout llGhBilling,llDirectBilling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        graphMainDashboradBinding = DataBindingUtil.setContentView(this,R.layout.activity_graph_main_dashborad);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        mContext = GraphMainDashboard.this;

        getId();
    }
    private void getId(){

        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.dashboard);
        ImageView ivBack = findViewById(R.id.img_BackButton);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        graphMainDashboradBinding.llGhBilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,Gh_Billing_Graph.class);
                startActivity(intent);
            }
        });

        graphMainDashboradBinding.llDirectBilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DirectBillingGraph.class);
                startActivity(intent);
            }
        });
        graphMainDashboradBinding.llGhAgeing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,GhAgeingGraph.class);
                startActivity(intent);
            }
        });
        graphMainDashboradBinding.llDirectAgeing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DirectAgeingGraph.class);
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