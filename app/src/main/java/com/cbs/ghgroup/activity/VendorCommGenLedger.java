package com.cbs.ghgroup.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cbs.ghgroup.ItemClickListener;
import com.cbs.ghgroup.R;
import com.cbs.ghgroup.adapter.UserListAdapter;
import com.cbs.ghgroup.databinding.ActivityVendorCommGenLedgerBinding;
import com.cbs.ghgroup.model.branchdeatils.BranchList;
import com.cbs.ghgroup.model.userlist.UsersDetail;
import com.cbs.ghgroup.model.userlist.UsersList;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.CustomProgressbar;
import com.cbs.ghgroup.utils.LogMsg;
import com.cbs.ghgroup.utils.PrefrenceKey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorCommGenLedger extends AppCompatActivity implements ItemClickListener {
    Context mContext;
    Dialog custDialog;
    UserListAdapter userListAdapter;
    ActivityVendorCommGenLedgerBinding vendorCommGenLedgerBinding;
    public int mYear, mMonth, mDay;
    SimpleDateFormat sdf;
    Spinner spinner_Branch, spnUserList;
    BranchList branchList;
    ArrayList<String> userName = new ArrayList<>();
    ArrayList<String> userCode = new ArrayList<>();
    ArrayList<String> branchName = new ArrayList<>();
    ArrayList<String> branchCode = new ArrayList<>();
    ArrayList<String> UserCode = new ArrayList<>();
    ArrayList<String> UserName = new ArrayList<>();
    private String branchList_name, branchList_code, user_code, user_name;
    AutoCompleteTextView acvtVendGenLedCode;
    String code, role, cv_Code, type, isCustomerVendor, userList;
    String codeData,customerCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vendorCommGenLedgerBinding = DataBindingUtil.setContentView(this,R.layout.activity_vendor_comm_gen_ledger);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        mContext=VendorCommGenLedger.this;
        spinner_Branch = findViewById(R.id.spinner_Branch);
        acvtVendGenLedCode = findViewById(R.id.edtVendCommGeneralCustCode);

        code = CommonMethods.getPrefsData(mContext, PrefrenceKey.CODE, "");
        cv_Code = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");
        role = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");
        type = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");
        isCustomerVendor = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");

        if ((role.equals("C"))) {
            acvtVendGenLedCode.setText(code);
            acvtVendGenLedCode.setEnabled(false);
            vendorCommGenLedgerBinding.imgSearchCode.setEnabled(false);
            vendorCommGenLedgerBinding.imgSearchCode.setVisibility(View.INVISIBLE);
            acvtVendGenLedCode.setCompoundDrawables(null, null, null, null);

        } else if ((role.equals("V"))) {
            acvtVendGenLedCode.setText(code);
            acvtVendGenLedCode.setEnabled(false);
            vendorCommGenLedgerBinding.imgSearchCode.setEnabled(false);
            vendorCommGenLedgerBinding.imgSearchCode.setVisibility(View.INVISIBLE);
            acvtVendGenLedCode.setCompoundDrawables(null, null, null, null);
        } else {
            //Toast.makeText(mContext, "Data Not found", Toast.LENGTH_SHORT).show();
        }

        getId();
        getBranchList();
    }
    private void getId(){
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.comm_general_ledger);
        ImageView ivBack = findViewById(R.id.img_BackButton);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        vendorCommGenLedgerBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //svSearch.setVisibility(View.VISIBLE);
                if (CommonMethods.isOnline(mContext)) {
                    CommonMethods.hideKeyboard(mContext);
                    String fromDate = vendorCommGenLedgerBinding.txtvFormDate.getText().toString();
                    String toDate = vendorCommGenLedgerBinding.txtvToDate.getText().toString();
                    String customerCode = vendorCommGenLedgerBinding.edtVendCommGeneralCustCode.getText().toString();
                    String branch = vendorCommGenLedgerBinding.spinnerBranch.getSelectedItem().toString();

                    if (TextUtils.isEmpty(fromDate)) {
                        Toast.makeText(mContext, "Select From Date", Toast.LENGTH_SHORT).show();
                        return;

                    } else if (TextUtils.isEmpty(toDate)) {
                        Toast.makeText(mContext, "Select To Date", Toast.LENGTH_SHORT).show();
                        return;

                    } else if (TextUtils.isEmpty(customerCode)) {
                        //etGenLedCode. setHint ("Hint 1");
                        Toast.makeText(mContext, "Enter Code", Toast.LENGTH_SHORT).show();
                        return;

                    } else if (branch.equalsIgnoreCase("--Select--")) {
                        Toast.makeText(mContext, "Select Branch", Toast.LENGTH_SHORT).show();
                        return;

                    } else {
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.FORM_DATE, fromDate);
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.TO_DATE, toDate);
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.BP_CODE, customerCode);
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.TYPE, "");
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.BRANCH, branch);
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.BRANCH_CODE, branchList_code);
                        Intent intent = new Intent(getApplicationContext(), VendorCommGenLedgerDetails.class);
                        startActivity(intent);

                    }
                } else {
                    CommonMethods.setSnackBar(vendorCommGenLedgerBinding.llLayout, getString(R.string.net));
                }

            }

        });

        vendorCommGenLedgerBinding.txtvFormDate.setOnClickListener(new View.OnClickListener() {
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
                        vendorCommGenLedgerBinding.txtvFormDate.setText(CommonMethods.pad(selectedday) + "/" + CommonMethods.pad(selectedmonth + 1) +
                                "/" + CommonMethods.pad(selectedyear));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.show();
            }

        });

        vendorCommGenLedgerBinding.txtvToDate.setOnClickListener(new View.OnClickListener() {
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
                        vendorCommGenLedgerBinding.txtvToDate.setText(CommonMethods.pad(selectedday) + "/" + CommonMethods.pad(selectedmonth + 1) +
                                "/" + CommonMethods.pad(selectedyear));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.show();
            }

        });

        vendorCommGenLedgerBinding.fabVendCommGenLedLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenVendGenLedFab();
            }
        });

        vendorCommGenLedgerBinding.imgSearchCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                custDialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog);
                custDialog.setContentView(R.layout.pop_design);
                custDialog.show();
                final RecyclerView custCode = custDialog.findViewById(R.id.customer_list);
                final SearchView searchView = custDialog.findViewById(R.id.search_client);

                CustomProgressbar.showProgressBar(mContext, false);
                RetrofitClient.getClient().getUsersList(codeData, isCustomerVendor, "V").enqueue(new Callback<UsersList>() {
                    @Override
                    public void onResponse(Call<UsersList> call, Response<UsersList> response) {
                        CustomProgressbar.hideProgressBar();
                        if (response.code() == 200 && response.body() != null) {
                            UsersList userList = response.body();
                            List<UsersDetail> usersDetails = userList.getGetUsersListResult().getUsersDetails();
                            Log.d("User_List", response.toString());
                            for (int i = 0; i < usersDetails.size(); i++) {
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                                custCode.setLayoutManager(linearLayoutManager);
                                userListAdapter = new UserListAdapter(mContext, usersDetails,VendorCommGenLedger.this);
                                custCode.setAdapter(userListAdapter);
                            }

                        }
                        searchView.setQueryHint("Search Customer");
                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String s) {
                                searchView.clearFocus();
                                return true;
                            }

                            @Override
                            public boolean onQueryTextChange(String s) {
                                userListAdapter.getFilter().filter(s);
                                return false;
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<UsersList> call, Throwable t) {
                        LogMsg.e("TAG", "onFailure :" + t.getMessage());
                        CustomProgressbar.hideProgressBar();
                    }

                });

                //getUserList();
                //getUserList(generalLedgerBinding.imgSearchCode.toString());
               /* if (generalLedgerBinding.edtGeneralCustCode.getText().toString().isEmpty()){
                    Toast.makeText(GeneralLedgerActivity.this, "Please Enter Code", Toast.LENGTH_SHORT).show();
                }else {
                    getUserList(generalLedgerBinding.edtGeneralCustCode.getText().toString());
                }*/
            }
        });
    }

    public void OpenVendGenLedFab() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(VendorCommGenLedger.this);
        mBuilder.setCancelable(true);
        View mView = getLayoutInflater().inflate(R.layout.contact_dialogbox, null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void getUserList(){

        CustomProgressbar.showProgressBar(mContext, false);
        RetrofitClient.getClient().getUsersList(codeData, isCustomerVendor, "C").enqueue(new Callback<UsersList>() {
            @Override
            public void onResponse(Call<UsersList> call, Response<UsersList> response) {
                CustomProgressbar.hideProgressBar();
                UsersList userList = response.body();
                Log.d("User_List", response.toString());
                userCode.add(0,"");
                userName.add(0,"");
                if (response.code() == 200 && response.body() != null) {
                    for (int i = 0; i < userList.getGetUsersListResult().getUsersDetails().size(); i++) {
                        userCode.add(userList.getGetUsersListResult().getUsersDetails().get(i).getUserCode());
                        userName.add(userList.getGetUsersListResult().getUsersDetails().get(i).getUserName());
//                        user_code = userCode.toString();
//                        user_name = userName.toString();
                    }
                    getDataOnPopup();

                }
            }

            @Override
            public void onFailure(Call<UsersList> call, Throwable t) {
                LogMsg.e("TAG", "onFailure :" + t.getMessage());
                CustomProgressbar.hideProgressBar();
            }

        });

    }

    private void getDataOnPopup(){
        final CharSequence contract[] = userCode.toArray(new CharSequence[userCode.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(VendorCommGenLedger.this);
        // builder.setTitle("");
        builder.setItems(contract, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                vendorCommGenLedgerBinding.edtVendCommGeneralCustCode.setText(userCode.get(position));
            }
        });
        builder.show();
    }

    public void getBranchList() {
        RetrofitClient.getClient().getBranchList(code, cv_Code, role).enqueue(new Callback<BranchList>() {
            @Override
            public void onResponse(Call<BranchList> call, Response<BranchList> response) {
                branchList = response.body();
                LogMsg.d("BRANCH_List", response.toString());

                if (response.code() == 200 && response.body() != null) {
                    branchName.add(0, "--Select--");
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
                LogMsg.e("TAG", "onFailure :" + t.getMessage());
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

    @Override
    public void onClick(String customerCode) {
        this.customerCode = customerCode;
        if (custDialog != null) {
            acvtVendGenLedCode.setText(customerCode);

        }
        custDialog.dismiss();
    }
}