package com.cbs.ghgroup.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.databinding.ActivityPayBillToBillBinding;
import com.cbs.ghgroup.model.branchdeatils.BranchList;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.LogMsg;
import com.cbs.ghgroup.utils.PrefrenceKey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayBillToBillActivity extends AppCompatActivity {
    ActivityPayBillToBillBinding payBillToBillBinding;
    Context mContext;
    ArrayList<String> branchName = new ArrayList<>();
    ArrayList<String> branchCode = new ArrayList<>();
    private String branchList_name, branchList_code;
    public int mYear, mMonth, mDay;
    SimpleDateFormat sdf;
    AutoCompleteTextView actPayBillToBill;
    String Code, Role, CvCode, type;
    private static  String[] customercodes = {"C03566","C05343","C07756","C04245","C00351","C03541","C04505","C02428","C06088"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_pay_bill_to_bill);
        payBillToBillBinding = DataBindingUtil.setContentView(this, R.layout.activity_pay_bill_to_bill);
        mContext=PayBillToBillActivity.this;
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        actPayBillToBill = findViewById(R.id.edtPendingBillCode);
        Code = CommonMethods.getPrefsData(mContext, PrefrenceKey.CODE, "");
        CvCode = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");
        Role = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");
        type = CommonMethods.getPrefsData(mContext, PrefrenceKey.TYPE, "");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, customercodes);

        actPayBillToBill.setThreshold(1);
        actPayBillToBill.setAdapter(adapter);

        getInitView();
        getBranchList();
    }

    public void getInitView() {

        ImageView ivBack = findViewById(R.id.img_BackButton);
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.Pay_BillToBill);

        payBillToBillBinding.tvUpToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        Calendar c = Calendar.getInstance();
                        c.set(selectedyear, selectedmonth, selectedday);
                        sdf = new SimpleDateFormat("dd/MM/yyyy");
                        payBillToBillBinding.tvUpToDate.setText(CommonMethods.pad(selectedday) + "/" + CommonMethods.pad(selectedmonth + 1) +
                                "/" + CommonMethods.pad(selectedyear));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.show();
            }

        });

        payBillToBillBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonMethods.isOnline(mContext)) {
                    String upToDate = payBillToBillBinding.tvUpToDate.getText().toString();
                    String code = payBillToBillBinding.edtPendingBillCode.getText().toString();
                    String branch = payBillToBillBinding.spinnerBranch.getSelectedItem().toString();
                    if (TextUtils.isEmpty(upToDate)) {

                        Toast.makeText(mContext, "UpToDate is empty", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (TextUtils.isEmpty(code)) {

                        Toast.makeText(mContext, "Code is empty", Toast.LENGTH_SHORT).show();
                        return;

                    }

                    if (branch.equalsIgnoreCase("--select--")) {
                        Toast.makeText(mContext, "Select Branch", Toast.LENGTH_SHORT).show();
                        return;

                    } else {
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.BRANCH, branch);
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.TO_DATE, upToDate);
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.TYPE, type);
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.BP_CODE, code);
                        CommonMethods.setPrefsData(mContext,PrefrenceKey.BRANCH_CODE,branchList_code);
//                        Intent intent = new Intent(mContext, PendingBillDetailsActivity.class);
//                        startActivity(intent);

                    }
                } else {
                    CommonMethods.setSnackBar(payBillToBillBinding.llLayout, getString(R.string.net));
                }

            }
        });



        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }});

        payBillToBillBinding.fabCustPendBillsLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCustPendBillsFab();
            }
        });






    }
    public void OpenCustPendBillsFab(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(PayBillToBillActivity.this);
        mBuilder.setCancelable(true);
        View mView = getLayoutInflater().inflate(R.layout.contact_dialogbox, null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
    public void getBranchList() {
        RetrofitClient.getClient().getBranchList(Code, CvCode, Role).enqueue(new Callback<BranchList>() {
            @Override
            public void onResponse(Call<BranchList> call, Response<BranchList> response) {
                BranchList branchList = response.body();
                LogMsg.d("BRANCH_List", response.toString());
                if (response.code() == 200 && response.body() != null) {
                    branchName.add(0, "--select--");
                    branchCode.add(0, "");
                    for (int i = 0; i < branchList.getBranchListResult().getBranchDetail().size(); i++) {
                        branchName.add(branchList.getBranchListResult().getBranchDetail().get(i).getBranchname());
                        branchCode.add(branchList.getBranchListResult().getBranchDetail().get(i).getBranchcode());

                    }
                    ArrayAdapter adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, branchName);
                    payBillToBillBinding.spinnerBranch.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<BranchList> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });
        payBillToBillBinding.spinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.getItemAtPosition(i).toString();

                branchList_name = branchName.get(i);
                branchList_code = branchCode.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}