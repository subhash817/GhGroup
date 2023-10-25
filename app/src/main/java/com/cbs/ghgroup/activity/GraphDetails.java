package com.cbs.ghgroup.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.databinding.ActivityGraphDetailsBinding;
import com.cbs.ghgroup.model.ghcustomerdashboard.GhCustomerDashboard;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.CustomProgressbar;
import com.cbs.ghgroup.utils.LogMsg;
import com.cbs.ghgroup.utils.PrefrenceKey;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GraphDetails extends AppCompatActivity {
    ActivityGraphDetailsBinding graphDetailsBinding;
    Context mContext;
    String fromdate,todate, cusCode, branches;
    LinearLayout llGhBillGraph,llDirectBillGraph,llGhAgeingGraph,llDirectAgeingGraph;
    public static List<BarEntry> dataIndikator;
    BarEntry dataItemGet;
    String idTipe;
    int start, end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        graphDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_graph_details);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        mContext = GraphDetails.this;

        dataIndikator = new ArrayList<>();

        fromdate = CommonMethods.getPrefsData(mContext, PrefrenceKey.FORM_DATE, "");
        todate = CommonMethods.getPrefsData(mContext, PrefrenceKey.TO_DATE, "");
        cusCode = CommonMethods.getPrefsData(mContext, PrefrenceKey.BP_CODE, "");
        branches = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH_CODE, "");

        getId();
        getGhGraphDetails();
    }

    public void getGhGraphDetails(){
        CustomProgressbar.showProgressBar(mContext, false);
        RetrofitClient.getClient().getGhBillingGraphDashboard(cusCode,fromdate,todate,branches).enqueue(new Callback<GhCustomerDashboard>() {
            @Override
            public void onResponse(Call<GhCustomerDashboard> call, Response<GhCustomerDashboard> response) {
                CustomProgressbar.hideProgressBar();
                if (response.code() == 200 && response.body() != null) {
                    GhCustomerDashboard ghCustomerDashboard = response.body();
                    LogMsg.d("GhCustomerDashboard",response.toString());

                    if (ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar().size() > 0) {

                        ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar().get(0).getPayment();
                        ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar().get(0).getBilling();
                        ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar().get(0).getGr();

                        setData(ghCustomerDashboard);

                    }


                   /* Log.d("GhCustomerDashboard", "" + response.body().getCustomerDashBoardsResult().getObjDirectBar().toString());
                    response.body().getCustomerDashBoardsResult().getLogMessage().getErrorMsg();
                    setData(response.body());*/
                }
            }

            @Override
            public void onFailure(Call<GhCustomerDashboard> call, Throwable t) {
                Toast.makeText(mContext, "Bad Request 400", Toast.LENGTH_SHORT).show();
                CustomProgressbar.hideProgressBar();
            }
        });
    }
    private void setData(GhCustomerDashboard ghCustomerDashboard){
        BarChart barChartList = findViewById(R.id.barchart_cust_gh_billing);
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> billing = new ArrayList<>();
        for (int i = 0; i < ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar().size(); i++) {
            Float floatValue= Float.valueOf(ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar().get(0).getBilling());
            BarEntry value = new BarEntry(floatValue, i);
            //BarEntry value = new BarEntry(Float.parseFloat(ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar().get(i).getBilling()),i); // purchase
            billing.add(value);
        }
        BarDataSet billingData = new BarDataSet(billing, "Billing");
        billingData.setColor(Color.GREEN);

        ArrayList<BarEntry> payment = new ArrayList<>();
        for (int i = 0; i < ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar().size(); i++) {
            Float floatValue= Float.valueOf(ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar().get(0).getPayment());
            BarEntry value = new BarEntry(floatValue, i);
            //BarEntry value = new BarEntry(Float.parseFloat(ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar().get(i).getPayment()),i); // payment
            payment.add(value);
        }

        BarDataSet paymentdata = new BarDataSet(payment, "Payment");
        paymentdata.setColor(Color.RED);

        ArrayList<BarEntry> gr = new ArrayList<>();
        for (int i = 0; i < ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar().size(); i++) {
            Float floatValue= Float.valueOf(ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar().get(0).getGr());
            BarEntry value = new BarEntry(floatValue, i);
            //BarEntry value = new BarEntry(Float.parseFloat(ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar().get(i).getGr()),i); // credit
            gr.add(value);
        }

        BarDataSet grData = new BarDataSet(gr, "GR");
        grData.setColor(Color.BLUE);

        dataSets = new ArrayList<>();
        dataSets.add(billingData);
        dataSets.add(paymentdata);
        dataSets.add(grData);

        ArrayList<String> xVals = new ArrayList<>();

        xVals.add("Billing = " + billingData);
        xVals.add("Payment = " + paymentdata);
        xVals.add("GR = " + grData);

        /*ArrayList<String> xAxis = new ArrayList<>();
        for (String getObjDirectBar : ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar()) {
            Log.d("CHART_RESPONSE", "" + getObjDirectBar.toString());
            xAxis.add("" + getObjDirectBar);
        }
        com.github.mikephil.charting.charts.BarChart chart = (com.github.mikephil.charting.charts.BarChart) findViewById(R.id.barchart_cust_gh_billing);
*/
        BarData data = new BarData();

        barChartList.setData(data);

        barChartList.animateY(2000);

        barChartList.invalidate();

    }

    public void getId(){
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.gh_bill_graph);
        ImageView ivBack = findViewById(R.id.img_BackButton);
        llGhBillGraph = findViewById(R.id.ll_ghBilling_graph);
        llDirectBillGraph = findViewById(R.id.ll_directBilling_graph);
        llGhAgeingGraph = findViewById(R.id.ll_ghAgeing_graph);
        llDirectAgeingGraph = findViewById(R.id.ll_directAgeing_graph);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        llGhBillGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(), GHBilling_Graph_Details.class);
                startActivity(intent);*/
            }
        });

        llDirectBillGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(), GHBilling_Graph_Details.class);
                startActivity(intent);*/
            }
        });

        llGhAgeingGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(), GHBilling_Graph_Details.class);
                startActivity(intent);*/
            }
        });

        llDirectAgeingGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(), DirectAgeing_Graph_Details.class);
                startActivity(intent);*/
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}