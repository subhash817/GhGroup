package com.cbs.ghgroup.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.databinding.ActivityUserManagementBinding;

public class UserManagementActivity extends AppCompatActivity {
    Context mContext;
    ActivityUserManagementBinding activityUserManagementBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user_management);
        activityUserManagementBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_management);
        mContext = UserManagementActivity.this;
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        getId();
    }

    public void getId() {

        ImageView ivBack = findViewById(R.id.img_BackButton);
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.user_management);

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