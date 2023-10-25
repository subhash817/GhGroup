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
import com.cbs.ghgroup.databinding.ActivityVendorBinding;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.GhPrefs;
import com.cbs.ghgroup.utils.PrefrenceKey;

public class VendorActivity extends AppCompatActivity {
    Context mContext;
    RelativeLayout GhBilling, DirectBill, Contact, UserManagement;
    ActivityVendorBinding vendorBinding;
    String lastUpdatedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_vendor);
        vendorBinding = DataBindingUtil.setContentView(this, R.layout.activity_vendor);
        mContext = VendorActivity.this;
        lastUpdatedTime = CommonMethods.getPrefsData(mContext, PrefrenceKey.UPDATED_TIME ,"");
        getId();
        buttonOnClick();
    }

    private void getId() {
        GhBilling = findViewById(R.id.rl_vendorGhBilling);
        //tvHeaderBack = findViewById(R.id.header_back);
        DirectBill = findViewById(R.id.rl_vendorDirectBilling);
        Contact = findViewById(R.id.rl_vendorContactPerson);
        UserManagement = findViewById(R.id.rl_vendorUserManagement);
        TextView lastUpdate = findViewById(R.id.txt_Vendlast_update);
        ImageView ivLogout = findViewById(R.id.logout);
        ImageView ivCustProfile = findViewById(R.id.iv_cust_profile);

        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.vendor);

        //ImageView imgHeader = findViewById(R.id.imgProfile);

        String adjusted = lastUpdatedTime.replaceAll("         \\r\\n ", "");

        lastUpdate.setText("Last updated on: " +adjusted);

        ImageView ivBack = findViewById(R.id.img_BackButton);
        setActivityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, MyProfileActivity.class));
            }
        });

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

        vendorBinding.fabVendPortLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenVendPortFab();
            }
        });
    }

    public void OpenVendPortFab(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(VendorActivity.this);
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
                // isValidate();
                startActivity(new Intent(mContext, GhBillingActivity.class));
            }

        });
        DirectBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // isValidate();
                startActivity(new Intent(mContext, VendorDirectBillActivity.class));
            }
        });
        Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // isValidate();
                startActivity(new Intent(mContext, ContactsActivity.class));
            }
        });
        UserManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // isValidate();
                //startActivity(new Intent(mContext, UserManagementActivity.class));
            }
        });
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}