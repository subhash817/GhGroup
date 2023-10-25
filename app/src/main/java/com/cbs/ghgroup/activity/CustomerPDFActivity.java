package com.cbs.ghgroup.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.adapter.CustomerPdfAdapter;
import com.cbs.ghgroup.databinding.ActivityCustomerPdfactivityBinding;
import com.cbs.ghgroup.model.customerledger.CustomerLedger2;
import com.cbs.ghgroup.model.customerledger.CustomerLedger2Result;
import com.cbs.ghgroup.model.customerledger.LedgerDetail;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.FixedGridLayoutManager;
import com.cbs.ghgroup.utils.PrefrenceKey;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerPDFActivity extends AppCompatActivity {

    int scrollX = 0;
    ActivityCustomerPdfactivityBinding customerPdf;
    Context mContext;

    private List<LedgerDetail> customerLedgers;
    CustomerLedger2Result customerLedgerResult;

    //List<CustPdfModel> custPdfList = new ArrayList<>();

    RecyclerView rvCustomer;

    HorizontalScrollView headerScroll;

    SearchView searchView;

    CustomerPdfAdapter customerPdfAdapter;

    String fromdate, todate, bpcode, type, branches,IsCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerPdf = DataBindingUtil.setContentView(this, R.layout.activity_customer_pdfactivity);
        mContext = CustomerPDFActivity.this;
        Intent intent = getIntent();

        fromdate = intent.getStringExtra(PrefrenceKey.FORM_DATE);
        todate = intent.getStringExtra(PrefrenceKey.TO_DATE);
        bpcode = intent.getStringExtra(PrefrenceKey.BP_CODE);
        type = intent.getStringExtra(PrefrenceKey.TYPE);
        branches = intent.getStringExtra(PrefrenceKey.BRANCH);

        initViews();
        getCustGenLedDetails();
//        setUpRecyclerView();

    }


    private void initViews()
    {
        rvCustomer = findViewById(R.id.rvCustPdf);
        headerScroll = findViewById(R.id.headerScrollCustomer);

        rvCustomer.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);

                scrollX += dx;

                headerScroll.scrollTo(scrollX, 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    public void getCustGenLedDetails()
    {

//        custPdfList.add(new CustPdfModel("pending", "11/02/2022", "12", "Delhi", "Debit", "Credit","adarsh","45","sap","12/04/2022","5"));
//        custPdfList.add(new CustPdfModel("outstanding", "20/03/2022", "14", "delhi", "debit", "credit","subhash","40","cbs","30/03/2022","7"));


        RetrofitClient.getClient().getCustomerLedger(fromdate,todate,bpcode,branches,type,IsCV).enqueue(new Callback<CustomerLedger2>() {
            @Override
            public void onResponse(Call<CustomerLedger2> call, Response<CustomerLedger2> response) {
                if (response.code() == 200 && response.body() != null) {
                    CustomerLedger2 customerLedger = response.body();
                    customerLedger.getCustomerLedger2Result().getLedgerDetail().get(0).getBranch();
                    //customerLedger.getCustomerLedgerResult().getLedgerDetail().get(0).getBranch();
                    Log.e("TAG", "Request: " + response.toString());
                    //CustomerLedger customerLedger = response.body();
                    List<LedgerDetail> ledgerDetails  =customerLedger.getCustomerLedger2Result().getLedgerDetail();
                    Log.e("TAG", "Request: "+response.toString());

                    rvCustomer = findViewById(R.id.rvCustPdf);

                    customerPdfAdapter = new CustomerPdfAdapter(CustomerPDFActivity.this, ledgerDetails);

                    FixedGridLayoutManager manager = new FixedGridLayoutManager();
                    manager.setTotalColumnCount(1);
                    rvCustomer.setLayoutManager(manager);
                    rvCustomer.setAdapter(customerPdfAdapter);
                    rvCustomer.addItemDecoration(new DividerItemDecoration(CustomerPDFActivity.this, DividerItemDecoration.VERTICAL));

                }


            }

            @Override
            public void onFailure(Call<CustomerLedger2> call, Throwable t) {

            }
        });
    }


}
