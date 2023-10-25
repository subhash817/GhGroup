package com.cbs.ghgroup.activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.cbs.ghgroup.R;
import com.cbs.ghgroup.databinding.ActivityDashBoardBinding;
public class DashBoardActivity extends AppCompatActivity {
    ActivityDashBoardBinding dashBoardBinding;
    Context mContext;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = DashBoardActivity.this;
        dashBoardBinding = DataBindingUtil.setContentView(this, R.layout.activity_dash_board);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        buttonOnClick();

    }


    private void buttonOnClick() {
        dashBoardBinding.btnVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,VendorActivity.class);
                startActivity(intent);

                //startActivity(new Intent(mContext, VendorActivity.class));
            }
        });
        dashBoardBinding.btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,CustomerActivity.class);
                startActivity(intent);
            }
        });
        dashBoardBinding.btnOrderForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,OrderForm.class);
                startActivity(intent);
            }
        });

    }

    @Override
      public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(mContext, "Please click BACK again to exit", Toast.LENGTH_LONG).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

   }