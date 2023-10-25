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
import com.cbs.ghgroup.databinding.ActivityGraphDashboardBinding;

public class Graph_Dashboard extends AppCompatActivity {
    ActivityGraphDashboardBinding graphDashboardBinding;
    Context mContext;
    RelativeLayout graphGhBilling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        graphDashboardBinding = DataBindingUtil.setContentView(this,R.layout.activity_graph_dashboard);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        mContext = Graph_Dashboard.this;
        getId();
    }
    public void getId(){
        graphGhBilling = findViewById(R.id.rl_cust_ghBilling_graph);
        ImageView ivBack = findViewById(R.id.img_BackButton);
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.graph_dash);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        graphDashboardBinding.rlCustGhBillingGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,Gh_Billing_Graph.class);
                startActivity(intent);
            }
        });

        graphDashboardBinding.rlCustDirectBillingGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,DirectBillingGraph.class);
                startActivity(intent);
            }
        });

        graphDashboardBinding.rlCustGhAgeingDetailsGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,GhAgeingGraph.class);
                startActivity(intent);
            }
        });
    }
}