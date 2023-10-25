package com.cbs.ghgroup.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.databinding.ActivityVendorGenLedgerBinding;
import com.cbs.ghgroup.model.branchdeatils.BranchList;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.PrefrenceKey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorGenLedger extends AppCompatActivity {

    Context mContext;
    public int mYear, mMonth, mDay;
    SimpleDateFormat sdf;
    ActivityVendorGenLedgerBinding vendorGenLedgerBinding;
    Spinner spinner_Branch;
    BranchList branchList;
    ArrayList<String> branchName = new ArrayList<>();
    ArrayList<String> branchCode = new ArrayList<>();
    private String branchList_name, branchList_code;
    AutoCompleteTextView acvtGenLedCode;
    String code, role, cv_Code;
    //String[] data = terms.toArray(new String[terms.size()]);
    private static  String[] customercodes = {"V00311"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vendorGenLedgerBinding = DataBindingUtil.setContentView(this,R.layout.activity_vendor_gen_ledger);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        mContext = VendorGenLedger.this;
        spinner_Branch = findViewById(R.id.spinner_Branch);
        acvtGenLedCode = findViewById(R.id.edtGeneralCustCode);
        //svSearch = findViewById(R.id.sv_search);
        code = CommonMethods.getPrefsData(mContext, PrefrenceKey.CODE, "");
        cv_Code = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");
        role = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");
        //edtVendorCode.setText(code);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, customercodes);
        acvtGenLedCode.setThreshold(1);
        acvtGenLedCode.setAdapter(adapter);

        if ((role.equals("V"))) {
            acvtGenLedCode.setText(code);
            acvtGenLedCode.setCompoundDrawables(null, null, null, null);

        }
        else {
            //Toast.makeText(mContext, "Data Not found", Toast.LENGTH_SHORT).show();
        }

        getId();

        getBranchList();
    }

    public void getId() {
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.general_ledger);
        ImageView ivBack = findViewById(R.id.img_BackButton);

        vendorGenLedgerBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CommonMethods.isOnline(mContext)) {
                    CommonMethods.hideKeyboard(mContext);
                    String fromDate = vendorGenLedgerBinding.txtvFormDate.getText().toString();
                    String toDate = vendorGenLedgerBinding.txtvToDate.getText().toString();
                    String customerCode = vendorGenLedgerBinding.edtGeneralCustCode.getText().toString();
                    String branch = vendorGenLedgerBinding.spinnerBranch.getSelectedItem().toString();

                    if (TextUtils.isEmpty(fromDate)) {
                        Toast.makeText(mContext, "select fromDate", Toast.LENGTH_SHORT).show();
                        return;

                    } else if (TextUtils.isEmpty(toDate)) {
                        Toast.makeText(mContext, "select toDate", Toast.LENGTH_SHORT).show();
                        return;

                    } else if (TextUtils.isEmpty(customerCode)) {
                        //etGenLedCode. setHint ("Hint 1");
                        Toast.makeText(mContext, "Enter customerCode", Toast.LENGTH_SHORT).show();
                        return;

                    } else if (branch.equalsIgnoreCase("--select--")) {
                        Toast.makeText(mContext, "select Branch", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.FORM_DATE, fromDate);
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.TO_DATE, toDate);
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.BP_CODE, customerCode);
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.TYPE, "");
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.BRANCH, branch);
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.BRANCH_CODE, branchList_code);
                        Intent intent = new Intent(getApplicationContext(), PdfActivity.class);
                        startActivity(intent);
                    }
                }
                else {
                    CommonMethods.setSnackBar(vendorGenLedgerBinding.llLayoutVendGen, getString(R.string.net));
                }

            }

        });

        vendorGenLedgerBinding.txtvFormDate.setOnClickListener(new View.OnClickListener() {
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
                        vendorGenLedgerBinding.txtvFormDate.setText(CommonMethods.pad(selectedday) + "/" + CommonMethods.pad(selectedmonth + 1) +
                                "/" + CommonMethods.pad(selectedyear));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.show();
            }

        });

        vendorGenLedgerBinding.txtvToDate.setOnClickListener(new View.OnClickListener() {
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
                        vendorGenLedgerBinding.txtvToDate.setText(CommonMethods.pad(selectedday) + "/" + CommonMethods.pad(selectedmonth + 1) +
                                "/" + CommonMethods.pad(selectedyear));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.show();
            }

        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        vendorGenLedgerBinding.fabVendGenLedLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCustGenLedFab();
            }
        });
    }

    public void OpenCustGenLedFab(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(VendorGenLedger.this);
        mBuilder.setCancelable(true);
        View mView = getLayoutInflater().inflate(R.layout.contact_dialogbox, null);

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public void getBranchList() {
        RetrofitClient.getClient().getBranchList(code, cv_Code, role).enqueue(new Callback<BranchList>() {
            @Override
            public void onResponse(Call<BranchList> call, Response<BranchList> response) {
                branchList = response.body();

                if (response.code() == 200 && response.body() != null) {
                    branchName.add(0, "--select--");
                    branchCode.add(0, "");
                    for (int i = 0; i < branchList.getBranchListResult().getBranchDetail().size(); i++) {
                        branchName.add(branchList.getBranchListResult().getBranchDetail().get(i).getBranchname());
                        branchCode.add(branchList.getBranchListResult().getBranchDetail().get(i).getBranchcode());
                        branchList_code = branchCode.toString();

                    }

                    ArrayAdapter adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, branchName);
                    spinner_Branch.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<BranchList> call, Throwable t) {
                Log.e("TAG", "onFailure :" + t.getMessage());
            }

        });
        spinner_Branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                branchList_name = branchName.get(i);
                branchList_code = branchCode.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}