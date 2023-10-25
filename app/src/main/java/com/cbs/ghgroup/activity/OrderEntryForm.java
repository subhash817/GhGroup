package com.cbs.ghgroup.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cbs.ghgroup.ItemClickListener;
import com.cbs.ghgroup.R;
import com.cbs.ghgroup.adapter.UserListAdapter;
import com.cbs.ghgroup.databinding.ActivityOrderEntryFormBinding;
import com.cbs.ghgroup.model.userlist.UsersDetail;
import com.cbs.ghgroup.model.userlist.UsersList;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.CustomProgressbar;
import com.cbs.ghgroup.utils.LogMsg;
import com.cbs.ghgroup.utils.PrefrenceKey;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderEntryForm extends AppCompatActivity implements ItemClickListener {
    ActivityOrderEntryFormBinding orderEntryFormBinding;
    Context mContext;
    Dialog orderFormDialog;
    String code,mobNo,userName;
    Dialog custDialog;
    UserListAdapter userListAdapter;
    AutoCompleteTextView actvVendorCode,actvVendorName,actvCustomerCode,actvCustomerName,
            actv_120DaysBalanceT,actvDateBalanceT,actv_120DaysBalanceC,actvDateBalanceC,
            actvNoCases,actvDispatchDate,actvLorry,actvBookingStation,actvShipOnlyName;
    Spinner spnTypeOrder,spnBillingOrder;
    String strTypeOrder;
    String customerCode,customerName,vendorCode,role, cv_Code, type, isCustomerVendor, userList;
    String codeData,getValue;
    Button btnOEFSubmit;
    Spinner spnLorry;
    //ArrayList<String> typeOrder = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderEntryFormBinding = DataBindingUtil.setContentView(this,R.layout.activity_order_entry_form);
        mContext = OrderEntryForm.this;
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

        mobNo = CommonMethods.getPrefsData(mContext, PrefrenceKey.MOBILE_NO, "");
        code = CommonMethods.getPrefsData(mContext, PrefrenceKey.CODE, "");
        userName = CommonMethods.getPrefsData(mContext, PrefrenceKey.NAME, "");
        cv_Code = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");
        role = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");
        type = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");
        isCustomerVendor = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");

            getId();
    }

    public void getId() {

        ImageView ivBack = findViewById(R.id.img_BackButton);
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.order_form);
        actvCustomerCode = findViewById(R.id.actv_orderForm_customerCode);
        actvCustomerName = findViewById(R.id.actv_orderForm_customerName);
        actvVendorCode = findViewById(R.id.actv_orderForm_vendorCode);
        actvVendorName = findViewById(R.id.actv_orderForm_vendorName);
        actv_120DaysBalanceT = findViewById(R.id.actv_120_daysBalanceT);
        actvDateBalanceT = findViewById(R.id.actv_dateBalanceT);
        actv_120DaysBalanceC = findViewById(R.id.actv_120_daysBalanceC);
        actvDateBalanceC = findViewById(R.id.actv_dateBalanceC);
        spnTypeOrder = findViewById(R.id.spn_OEF_typeOrder);
        spnBillingOrder = findViewById(R.id.spn_OEF_BillingType);
        actvNoCases = findViewById(R.id.actv_noOfCases);
        actvDispatchDate = findViewById(R.id.actv_dispatchLastdate);
       // actvLorry = findViewById(R.id.actv_lorry);
        spnLorry = findViewById(R.id.spn_lorry);
        actvBookingStation = findViewById(R.id.actv_bookingStation);
        actvShipOnlyName = findViewById(R.id.actv_shipOnlyName);
        btnOEFSubmit = findViewById(R.id.btn_OEF_submit);

        ImageView imgAddItem = (ImageView) findViewById(R.id.addItemTableRow);

        imgAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* LinearLayout llItem = findViewById(R.id.ll_itemDetails1);
                llItem.addView(llItem);*/

                TableRow row = new TableRow(OrderEntryForm.this);
                TextView txt = new TextView(OrderEntryForm.this);
                txt.setText("New Row");
                row.addView(txt);
                TableLayout table = (TableLayout) findViewById(R.id.tableLayout1);
                table.addView(row);
            }
        });

       /* btnAddItem.setOnClickListener(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(View v) {
                //create a new row to add
                TableRow row = new TableRow(OrderEntryForm.this);
                //add Layouts to your new row
                TextView txt = new TextView(OrderEntryForm.this);
                txt.setText("New Row");
                row.addView(txt);
                //add your new row to the TableLayout:
                TableLayout table = (TableLayout) findViewById(R.id.tableLayout1);
                table.addView(row);

            }
        });*/


        String[] spinner_typeOrder = {"Type of Order","Open","Self"};
        String[] spinner_billingOrder = {"Billing Type","Gh","Direct"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinner_typeOrder);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTypeOrder.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinner_billingOrder);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBillingOrder.setAdapter(adapter2);

        spnTypeOrder.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
               /* strTypeOrder = String.valueOf(spnTypeOrder.getSelectedItem());
                if (pos == 0){
                    spnTypeOrder.setSelected(Boolean.parseBoolean(strTypeOrder));
                }*/
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        spnBillingOrder.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
               /* strTypeOrder = String.valueOf(spnTypeOrder.getSelectedItem());
                if (pos == 0){
                    spnTypeOrder.setSelected(Boolean.parseBoolean(strTypeOrder));
                }*/
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


        orderEntryFormBinding.btnOEFSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String getValue = actv_120DaysBalanceT.getText().toString();
                Intent intent = new Intent(getApplicationContext(),OrderReportDetails.class);
                //intent.putExtra("Balance", getValue);
                //intent.putExtra(PrefrenceKey.Balance_120_Days_T, getValue);
                CommonMethods.setPrefsData(mContext,PrefrenceKey.Balance_120_Days_T,getValue);
                startActivity(intent);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        orderEntryFormBinding.imgOrderFormCustSearchCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                userListAdapter = new UserListAdapter(mContext, usersDetails,OrderEntryForm.this);
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

        orderEntryFormBinding.imgOrderFormVendSearchCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                userListAdapter = new UserListAdapter(mContext, usersDetails,OrderEntryForm.this);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(String customerCode) {
        this.customerCode = customerCode;
        this.vendorCode = customerCode;
        if (custDialog != null) {
            actvCustomerCode.setText(customerCode);
        } else if (custDialog != null){
            actvVendorCode.setText(vendorCode);
        }
        custDialog.dismiss();
    }


}