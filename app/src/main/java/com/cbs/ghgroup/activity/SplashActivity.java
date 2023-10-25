package com.cbs.ghgroup.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.databinding.ActivitySplashBinding;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.PrefrenceKey;

public class SplashActivity extends AppCompatActivity {
    Context mContext;
    ImageView Logo;
    private String userID;
    ActivitySplashBinding splashBinding;
    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        mContext = SplashActivity.this;
        Logo = findViewById(R.id.img_Logo);
        userID = CommonMethods.getPrefsData(mContext, PrefrenceKey.USER_ID, "0");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userID.equals("0")) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                } else {

                    startActivity(new Intent(mContext, DashBoardActivity.class));
                }

                finish();


            }
        }, SPLASH_TIME_OUT);
        Animation myanim = AnimationUtils.loadAnimation(mContext, R.anim.splash_anim);
        Logo.startAnimation(myanim);
    }
}