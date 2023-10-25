package com.cbs.ghgroup.activity;

import android.app.AlertDialog;
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
import com.cbs.ghgroup.databinding.ActivityCustomerBinding;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.GhPrefs;
import com.cbs.ghgroup.utils.PrefrenceKey;
public class CustomerActivity extends AppCompatActivity {
    ActivityCustomerBinding customerBinding;
    Context mContext;
    String lastUpdatedTime;
    RelativeLayout GhBilling, DirectBill, Contact, UserManagement,PayNow,Dashboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerBinding = DataBindingUtil.setContentView(this, R.layout.activity_customer);
        mContext = CustomerActivity.this;
        lastUpdatedTime = CommonMethods.getPrefsData(mContext, PrefrenceKey.UPDATED_TIME ,"");
        getId();
        buttonOnClick();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    private void getId() {
        GhBilling = findViewById(R.id.rl_GhBilling);
        DirectBill = findViewById(R.id.rl_DirectBilling);
        Contact = findViewById(R.id.rl_ContactPerson);
        PayNow = findViewById(R.id.rl_PayNow);
        Dashboard = findViewById(R.id.rl_Dashboard);
        UserManagement = findViewById(R.id.rl_UserManagement);
        ImageView ivBack = findViewById(R.id.img_BackButton);
        ImageView ivLogout = findViewById(R.id.logout);
        ImageView ivCustProfile = findViewById(R.id.iv_cust_profile);
        TextView setActivityName = findViewById(R.id.txtHeader);
        TextView lastUpdate = findViewById(R.id.txt_last_update);
        setActivityName.setText(R.string.customer);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

        //String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
         // textView is the TextView view that should display it

        String adjusted = lastUpdatedTime.replaceAll("         \\r\\n ", "");

        lastUpdate.setText("Last updated on: " +adjusted);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivCustProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, MyProfileActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });

        Intent in = getIntent();
        String string = in.getStringExtra("message");

        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertdlg = new AlertDialog.Builder(mContext);
                alertdlg.setMessage("Are you sure want to logout?");
                alertdlg.setCancelable(true);

                alertdlg.setPositiveButton("Yes", (dialog, id) -> {
                    dialog.cancel();
                    GhPrefs.removeAllPrefValue(mContext);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                });

                alertdlg.setNegativeButton("No", (dialog, id) -> dialog.cancel());
                AlertDialog alertDialog = alertdlg.create();
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(false);
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setAllCaps(false);

            }
        });

        customerBinding.fabCustPortLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCustFabLog();
            }
        });
    }

    public void OpenCustFabLog(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CustomerActivity.this);
        mBuilder.setCancelable(true);
        View mView = getLayoutInflater().inflate(R.layout.contact_dialogbox, null);

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
    private void buttonOnClick() {
        GhBilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, CustomerGHBillActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });
        DirectBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, DirectBillActivity.class));
            }
        });

        Dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, GraphMainDashboard.class));
            }
        });


        PayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,GHBillingPayNowActivity.class);
                startActivity(intent);
//                startActivity(new Intent(mContext, GHBillingPayNowActivity.class));
            }
        });
        Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ContactsActivity.class));
            }
        });
        UserManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, UserManagementActivity.class));
            }
        });

    }
}