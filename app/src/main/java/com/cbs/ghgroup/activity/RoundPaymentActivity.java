package com.cbs.ghgroup.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.databinding.ActivityRoundPaymentBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RoundPaymentActivity extends AppCompatActivity {
    ActivityRoundPaymentBinding roundPaymentBinding;

    Context mContext;
    ArrayList<String> branchName = new ArrayList<>();
    ArrayList<String> branchCode = new ArrayList<>();
    private String branchList_name, branchList_code;
    public int mYear, mMonth, mDay;
    SimpleDateFormat sdf;
    AutoCompleteTextView roundPayment;
    String Code, Role, CvCode, type;
    private static  String[] customercodes = {"C03566","C05343","C07756","C04245","C00351","C03541","C04505","C02428","C06088"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_payment);

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        roundPayment = findViewById(R.id.edtPendingBillCode);
//        Code = CommonMethods.getPrefsData(mContext, PrefrenceKey.CODE, "");
//        CvCode = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");
//        Role = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");
//        type = CommonMethods.getPrefsData(mContext, PrefrenceKey.TYPE, "");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, customercodes);

        roundPayment.setThreshold(1);
        roundPayment.setAdapter(adapter);

        getInitView();
    }
    public void getInitView(){

        ImageView ivBack = findViewById(R.id.img_BackButton);
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.round_payment);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }
}