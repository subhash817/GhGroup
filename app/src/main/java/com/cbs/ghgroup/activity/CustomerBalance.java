package com.cbs.ghgroup.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cbs.ghgroup.ItemClickListener;
import com.cbs.ghgroup.R;
import com.cbs.ghgroup.adapter.UserListAdapter;
import com.cbs.ghgroup.databinding.ActivityCustomerBalanceBinding;
import com.cbs.ghgroup.model.userlist.UsersDetail;
import com.cbs.ghgroup.model.userlist.UsersList;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.CustomProgressbar;
import com.cbs.ghgroup.utils.LogMsg;
import com.cbs.ghgroup.utils.PrefrenceKey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerBalance extends AppCompatActivity implements ItemClickListener {
    ActivityCustomerBalanceBinding customerBalanceBinding;
    Context mContext;
    Dialog custDialog;
    UserListAdapter userListAdapter;
    ArrayList<String> branchName = new ArrayList<>();
    ArrayList<String> branchCode = new ArrayList<>();
    ArrayList<String> userName = new ArrayList<>();
    ArrayList<String> userCode = new ArrayList<>();
    private String branchList_name,branchList_code;
    String code, role, cv_Code,isCustomerVendor;
    AutoCompleteTextView actvCBCustCode,actvCBCustName;
    public int mYear, mMonth, mDay;
    String codeData,codeName,customerCode,customerName,getValue;
    SimpleDateFormat sdf;
    TextView tv120DaysBalanceT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerBalanceBinding = DataBindingUtil.setContentView(this,R.layout.activity_customer_balance);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        mContext = CustomerBalance.this;
        actvCBCustCode = findViewById(R.id.edt_CB_custCode);
        actvCBCustName = findViewById(R.id.edt_CB_custName);
        code = CommonMethods.getPrefsData(mContext, PrefrenceKey.CODE, "");
        cv_Code = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");
        role = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");
        isCustomerVendor = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");


        /*Intent intent = getIntent();
        String getValue = intent.getStringExtra("Balance");
        tv120DaysBalanceT.setText(getValue);*/

        getId();
    }

    private void getId(){
        ImageView ivBack = findViewById(R.id.img_BackButton);
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.customer_balance);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

        //tv120DaysBalanceT = findViewById(R.id.tv_120_daysBalance_T);
        getValue = CommonMethods.getPrefsData(mContext, PrefrenceKey.Balance_120_Days_T, "");
        customerBalanceBinding.tv120DaysBalanceT.setText(getValue);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        customerBalanceBinding.imgCBSearchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserList();
            }
        });

        customerBalanceBinding.imgCBSearchCode.setOnClickListener(new View.OnClickListener() {
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
                                userListAdapter = new UserListAdapter(mContext, usersDetails,CustomerBalance.this);
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

            }
        });

    }

    private void getUserList() {

        /*custDialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog);
        custDialog.setContentView(R.layout.pop_design);
        custDialog.show();*/
       // final RecyclerView userName = custDialog.findViewById(R.id.customer_list);
        //final SearchView searchView = custDialog.findViewById(R.id.search_client);

        CustomProgressbar.showProgressBar(mContext, false);
        RetrofitClient.getClient().getUsersList(codeData, isCustomerVendor, "C").enqueue(new Callback<UsersList>() {
            @Override
            public void onResponse(Call<UsersList> call, Response<UsersList> response) {
                CustomProgressbar.hideProgressBar();
                UsersList userList = response.body();
                //List<UsersDetail> usersDetails = userList.getGetUsersListResult().getUsersDetails();
                Log.d("User_List", response.toString());
                //userCode.add(0, "");
                userName.add(0, "");
                if (response.code() == 200 && response.body() != null) {
                    for (int i = 0; i < userList.getGetUsersListResult().getUsersDetails().size(); i++) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        /*userName.setLayoutManager(linearLayoutManager);
                        userListAdapter = new UserListAdapter(mContext, usersDetails,CustomerBalance.this);
                        userName.setAdapter(userListAdapter);*/
                        //userCode.add(userList.getGetUsersListResult().getUsersDetails().get(i).getUserCode());
                        userName.add(userList.getGetUsersListResult().getUsersDetails().get(i).getUserName());

                    }

                    /*searchView.setQueryHint("Search Customer");
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
*/
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

    private void getDataOnPopup() {
        final CharSequence contract[] = userName.toArray(new CharSequence[userName.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerBalance.this);

        builder.setItems(contract, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                actvCBCustName.setText(userName.get(position));
            }
        });
        builder.show();
    }

    @Override
    public void onClick(String customerCode) {
        this.customerCode = customerCode;
        if (custDialog != null) {
            actvCBCustCode.setText(customerCode);
        }
        custDialog.dismiss();
    }
}