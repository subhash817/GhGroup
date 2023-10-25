package com.cbs.ghgroup.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cbs.ghgroup.R;

public class ContactsActivity extends AppCompatActivity {
    Context mContext;
    TextView pop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        //activitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        mContext = ContactsActivity.this;
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        getId();
    }

    public void getId() {
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.contact_detail);
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