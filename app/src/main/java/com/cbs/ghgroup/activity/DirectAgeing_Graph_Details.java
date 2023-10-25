package com.cbs.ghgroup.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.databinding.ActivityDirectAgeingGraphDetailsBinding;

public class DirectAgeing_Graph_Details extends AppCompatActivity {
    ActivityDirectAgeingGraphDetailsBinding directAgeingGraphDetailsBinding;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        directAgeingGraphDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_direct_ageing_graph_details);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        mContext = DirectAgeing_Graph_Details.this;

        getId();
    }
    public void getId(){
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.direct_ageing_graph);
        ImageView ivBack = findViewById(R.id.img_BackButton);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}