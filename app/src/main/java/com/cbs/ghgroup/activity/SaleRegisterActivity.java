package com.cbs.ghgroup.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.databinding.ActivitySaleRegisterBinding;

public class SaleRegisterActivity extends AppCompatActivity {
    Context mContext;
    ActivitySaleRegisterBinding activitySaleRegisterBinding;
    private EditText edtText;
    private LinearLayout LinearMain;
    private Button btnAdd, btnClear;
    private int no;
    private final String CLASSNAME = "CategoryEditorActivity";
    LinearLayout itsLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sale_register);
        activitySaleRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_sale_register);
        mContext = SaleRegisterActivity.this;
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        getId();

    }

    public void getId() {
        ImageView ivBack = findViewById(R.id.img_BackButton);
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.sale_register);

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