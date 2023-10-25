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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cbs.ghgroup.ItemClickListener;
import com.cbs.ghgroup.R;
import com.cbs.ghgroup.adapter.UserListAdapter;
import com.cbs.ghgroup.databinding.ActivityPendingBillBinding;
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

public class PendingBillActivity extends AppCompatActivity implements ItemClickListener {

    Dialog custDialog;
    UserListAdapter userListAdapter;
    ActivityPendingBillBinding PendingBillBinding;
    Context mContext;
    ArrayList<String> branchName = new ArrayList<>();
    ArrayList<String> branchCode = new ArrayList<>();
    ArrayList<String> userName = new ArrayList<>();
    ArrayList<String> userCode = new ArrayList<>();
    private String branchList_name, branchList_code;
    public int mYear, mMonth, mDay;
    SimpleDateFormat sdf;
    private  String user_code, user_name;
    AutoCompleteTextView actvtPenBillCode;
    String Code, Role, CvCode, type,isCustomerVendor;
    String codeData,customerCode;
    //private static  String[] customercodes = {"C03566","C05343","C07756","C04245","C00351","C03541","C04505","C02428","C06088"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PendingBillBinding = DataBindingUtil.setContentView(this, R.layout.activity_pending_bill);
        mContext = PendingBillActivity.this;
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        actvtPenBillCode = findViewById(R.id.edtPendingBillCode);
        Code = CommonMethods.getPrefsData(mContext, PrefrenceKey.CODE, "");
        CvCode = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");
        Role = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");
        type = CommonMethods.getPrefsData(mContext, PrefrenceKey.TYPE, "");
        isCustomerVendor = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");

       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, customercodes);
        actvtPenBillCode.setThreshold(1);
        actvtPenBillCode.setAdapter(adapter);*/

        if ((Role.equals("C"))) {
            actvtPenBillCode.setText(Code);
            actvtPenBillCode.setEnabled(false);
            PendingBillBinding.imgPendSearchCode.setEnabled(false);
            PendingBillBinding.imgPendSearchCode.setVisibility(View.INVISIBLE);
            actvtPenBillCode.setCompoundDrawables(null, null, null, null);
        }
        else if ((Role.equals("V"))) {
            actvtPenBillCode.setText(Code);
            actvtPenBillCode.setEnabled(false);
            PendingBillBinding.imgPendSearchCode.setEnabled(false);
            PendingBillBinding.imgPendSearchCode.setVisibility(View.INVISIBLE);
            actvtPenBillCode.setCompoundDrawables(null, null, null, null);
        }else {
            //Toast.makeText(mContext, "Data Not found", Toast.LENGTH_SHORT).show();
        }

        getId();
        getBranchList();
       // getUserList();
    }

    public void getId() {
        ImageView ivBack = findViewById(R.id.img_BackButton);
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.pending_bill);

        PendingBillBinding.tvUpToDate.setOnClickListener(new View.OnClickListener() {
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
                        PendingBillBinding.tvUpToDate.setText(CommonMethods.pad(selectedday) + "/" + CommonMethods.pad(selectedmonth + 1) +
                                "/" + CommonMethods.pad(selectedyear));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.show();
            }

        });

        PendingBillBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonMethods.isOnline(mContext)) {
                    String upToDate = PendingBillBinding.tvUpToDate.getText().toString();
                    String code = PendingBillBinding.edtPendingBillCode.getText().toString();
                    String branch = PendingBillBinding.spinnerBranch.getSelectedItem().toString();
                    if (TextUtils.isEmpty(upToDate)) {

                        Toast.makeText(mContext, "Select Up To Date", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (TextUtils.isEmpty(code)) {

                        Toast.makeText(mContext, "Enter Code", Toast.LENGTH_SHORT).show();
                        return;

                    }

                    if (branch.equalsIgnoreCase("--Select--")) {
                        Toast.makeText(mContext, "Select Branch", Toast.LENGTH_SHORT).show();
                        return;

                    } else {
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.BRANCH, branch);
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.TO_DATE, upToDate);
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.TYPE, type);
                        CommonMethods.setPrefsData(mContext, PrefrenceKey.BP_CODE, code);
                        CommonMethods.setPrefsData(mContext,PrefrenceKey.BRANCH_CODE,branchList_code);
                        Intent intent = new Intent(mContext, PendingBillDetailsActivity.class);
                        startActivity(intent);

                    }
                } else {
                    CommonMethods.setSnackBar(PendingBillBinding.llLayout, getString(R.string.net));
                }

            }
        });


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        PendingBillBinding.fabCustPendBillsLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCustPendBillsFab();
            }
        });

        PendingBillBinding.imgPendSearchCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                custDialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog);
                custDialog.setContentView(R.layout.pop_design);
                custDialog.show();
                final RecyclerView custCode = custDialog.findViewById(R.id.customer_list);
                final SearchView searchView = custDialog.findViewById(R.id.search_client);

                CustomProgressbar.showProgressBar(mContext, false);
                RetrofitClient.getClient().getUsersList(codeData, isCustomerVendor, "C").enqueue(new Callback<UsersList>() {
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
                                userListAdapter = new UserListAdapter(mContext, usersDetails,PendingBillActivity.this);
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

                /*if (PendingBillBinding.edtPendingBillCode.getText().toString().isEmpty()){
                    //Toast.makeText(GeneralLedgerActivity.this, "", Toast.LENGTH_SHORT).show();
                }else {
                    getUserList(PendingBillBinding.edtPendingBillCode.getText().toString());
                }*/
            }
        });

    }

    public void OpenCustPendBillsFab(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(PendingBillActivity.this);
        mBuilder.setCancelable(true);
        View mView = getLayoutInflater().inflate(R.layout.contact_dialogbox, null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(PendingBillActivity.this);
        //builder.setTitle("");
        builder.setItems(contract, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                PendingBillBinding.edtPendingBillCode.setText(userCode.get(position));
            }
        });
        builder.show();
    }

    /*private void getUserList(){

        RetrofitClient.getClient().getUsersList("", isCustomerVendor, "C").enqueue(new Callback<UsersList>() {
            @Override
            public void onResponse(Call<UsersList> call, Response<UsersList> response) {
                UsersList userList = response.body();
                Log.d("User_List", response.toString());
                userCode.add(0,"");
                userName.add(0,"");
                if (response.code() == 200 && response.body() != null) {
                        userCode.add(userList.getGetUsersListResult().getUsersDetails().get(0).getUserCode());
                        userName.add(userList.getGetUsersListResult().getUsersDetails().get(0).getUserName());
                        user_code = userCode.toString();
                        user_name = userName.toString();
                    ArrayAdapter adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, userCode);
                    actvtPenBillCode.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<UsersList> call, Throwable t) {
                LogMsg.e("TAG", "onFailure :" + t.getMessage());
            }

        });

        actvtPenBillCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user_code = userCode.get(i);
                user_name = userName.get(i);

                actvtPenBillCode.setText((CharSequence) actvtPenBillCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }*/

    public void getBranchList() {
        RetrofitClient.getClient().getBranchList(Code, CvCode, Role).enqueue(new Callback<BranchList>() {
            @Override
            public void onResponse(Call<BranchList> call, Response<BranchList> response) {
                BranchList branchList = response.body();
                LogMsg.d("BRANCH_List", response.toString());
                if (response.code() == 200 && response.body() != null) {
                    branchName.add(0, "--Select--");
                    branchCode.add(0, "");
                    for (int i = 0; i < branchList.getBranchListResult().getBranchDetail().size(); i++) {
                        branchName.add(branchList.getBranchListResult().getBranchDetail().get(i).getBranchname());
                        branchCode.add(branchList.getBranchListResult().getBranchDetail().get(i).getBranchcode());

                    }
                    ArrayAdapter adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, branchName);
                    PendingBillBinding.spinnerBranch.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<BranchList> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });
        PendingBillBinding.spinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    @Override
    public void onClick(String customerCode) {
        this.customerCode = customerCode;
        if (custDialog != null) {
            actvtPenBillCode.setText(customerCode);

        }
        custDialog.dismiss();
    }
}