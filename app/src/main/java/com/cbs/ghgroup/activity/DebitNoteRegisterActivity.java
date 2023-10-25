package com.cbs.ghgroup.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cbs.ghgroup.R;

public class DebitNoteRegisterActivity extends AppCompatActivity {
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit_note_register);
        //activitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        getId();

    }

    public void getId() {
        ImageView ivBack = findViewById(R.id.img_BackButton);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.debit_note_register);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}