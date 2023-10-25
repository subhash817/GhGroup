package com.cbs.ghgroup.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.databinding.ActivityDirectBarGraphDetailsBinding;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectBarGraphDetails extends AppCompatActivity {
    Context mContext;
    ActivityDirectBarGraphDetailsBinding directBarGraphDetailsBinding;
    String fromdate,todate, cusCode, branches;
    private BarData data;
    BarChart barChart;
    private ArrayList<BarDataSet> alBarDataSet = new ArrayList<>();;
    private ArrayList<BarEntry> alBarEntry = new ArrayList<>();
    ArrayList<String> xAxis = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        directBarGraphDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_direct_bar_graph_details);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        mContext = DirectBarGraphDetails.this;

        fromdate = CommonMethods.getPrefsData(mContext, PrefrenceKey.FORM_DATE, "");
        todate = CommonMethods.getPrefsData(mContext, PrefrenceKey.TO_DATE, "");
        cusCode = CommonMethods.getPrefsData(mContext, PrefrenceKey.BP_CODE, "");
        branches = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH_CODE, "");

        getId();
        getDirectBarGraphDetails();
    }

    private void getId(){

        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.direct_bill_graph);
        ImageView ivBack = findViewById(R.id.img_BackButton);
        barChart = findViewById(R.id.barchart_cust_direct_billing);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void getDirectBarGraphDetails(){
        CustomProgressbar.showProgressBar(mContext, false);
        RetrofitClient.getClient().getGhBillingGraphDashboard(cusCode,fromdate,todate,branches).enqueue(new Callback<GhCustomerDashboard>() {
            @Override
            public void onResponse(Call<GhCustomerDashboard> call, Response<GhCustomerDashboard> response) {
                CustomProgressbar.hideProgressBar();
                if (response.code() == 200 && response.body() != null) {
                    GhCustomerDashboard ghCustomerDashboard = response.body();
                    LogMsg.d("GhCustomerDashboard", response.toString());

                    if (ghCustomerDashboard.getCustomerDashBoardsResult().getObjGHBar().size() > 0) {
                        ghCustomerDashboard.getCustomerDashBoardsResult().getObjGHBar().get(0).getPayment();
                        ghCustomerDashboard.getCustomerDashBoardsResult().getObjGHBar().get(0).getPurchase();
                        ghCustomerDashboard.getCustomerDashBoardsResult().getObjGHBar().get(0).getCreditNote();
                        setData(response.body());
                        //xAxis.add(ghCustomerDashboard.getCustomerDashBoardsResult().getObjGHBar());
                    }

                }
            }

            @Override
            public void onFailure(Call<GhCustomerDashboard> call, Throwable t) {
                Toast.makeText(mContext, "Bad Request 400", Toast.LENGTH_SHORT).show();
                CustomProgressbar.hideProgressBar();
            }
        });
    }

    private void setData(@NonNull GhCustomerDashboard ghCustomerDashboard){

        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> purchase = new ArrayList<>();

        for (int i = 0; i < ghCustomerDashboard.getCustomerDashBoardsResult().getObjGHBar().size(); i++) {
            Float floatValue= Float.valueOf(ghCustomerDashboard.getCustomerDashBoardsResult().getObjGHBar().get(0).getPurchase());
            BarEntry value = new BarEntry(floatValue, i); // purchase
            purchase.add(value);
        }
        BarDataSet purchaseData = new BarDataSet(purchase, "Purchase");
        purchaseData.setColor(Color.GREEN);

        ArrayList<BarEntry> payment = new ArrayList<>();
        for (int i = 0; i < ghCustomerDashboard.getCustomerDashBoardsResult().getObjGHBar().size(); i++) {
            Float floatValue= Float.valueOf(ghCustomerDashboard.getCustomerDashBoardsResult().getObjGHBar().get(0).getPayment());
            //BarEntry value = new BarEntry((float) Double.parseDouble(ghCustomerDashboard.getCustomerDashBoardsResult().getObjGHBar().get(i).getPayment()),i); // payment
            BarEntry value = new BarEntry(floatValue, i);
            payment.add(value);
        }

        BarDataSet paymentdata = new BarDataSet(payment, "Payment");
        paymentdata.setColor(Color.RED);

        ArrayList<BarEntry> creditNote = new ArrayList<>();
        for (int i = 0; i < ghCustomerDashboard.getCustomerDashBoardsResult().getObjGHBar().size(); i++) {
            Float floatValue= Float.valueOf(ghCustomerDashboard.getCustomerDashBoardsResult().getObjGHBar().get(0).getCreditNote());
            //BarEntry value = new BarEntry((float) Double.parseDouble(ghCustomerDashboard.getCustomerDashBoardsResult().getObjGHBar().get(i).getCreditNote()),i); // credit
            BarEntry value = new BarEntry(floatValue, i);
            creditNote.add(value);
        }

        BarDataSet creditNoteData = new BarDataSet(creditNote, "CreditNote");
        creditNoteData.setColor(Color.BLUE);


        /*data = new BarData(xAxis);
        barChart.setData(data);
        barChart.setDescription("");
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        // chart.getXAxis().setDrawGridLines(false);
        barChart.setDrawGridBackground(false);
        barChart.setHighlightEnabled(false);

        YAxis y = barChart.getAxisLeft();

        barChart.getAxisRight().setEnabled(false);
      *//* y.setAxisMaxValue(200);
        y.setAxisMinValue(20);
        y.setLabelCount(9);*//*


        barChart.getLegend().setEnabled(true);
        barChart.setDoubleTapToZoomEnabled(true);
        barChart.setPinchZoom(true);
        barChart.setDragEnabled(true);
        barChart.setVisibleXRange(10);
        barChart.setFitsSystemWindows(true);

        y.setDrawLabels(true);

        YAxis leftAxis = barChart.getAxisLeft();
        LimitLine ll = new LimitLine(140f, "");
        ll.setLineColor(Color.TRANSPARENT);
        ll.setLineWidth(4f);
        ll.setTextColor(Color.BLUE);
        ll.setTextSize(12f);
        leftAxis.addLimitLine(ll);

        //  chart.animateX(3000);
        //chart.animateY(3000);
        barChart.animateXY(3000, 3000);
        barChart.animateY(3000, AnimationEasing.EasingOption.EaseOutBack);
        barChart.invalidate();
        // chart.setScaleMinima(1.2f, 1.2f);
        barChart.fitScreen();*/

        dataSets = new ArrayList<>();
        dataSets.add(purchaseData);
        dataSets.add(paymentdata);
        dataSets.add(creditNoteData);


        ArrayList<String> xVals = new ArrayList<>();

        xVals.add("Purchase = " + purchaseData);
        xVals.add("Payment = " + paymentdata);
        xVals.add("CreditNote = " + creditNoteData);

        BarData data = new BarData();

        barChart.setData(data);

        barChart.animateXY(2000,0);
        barChart.invalidate();
        barChart.fitScreen();

    }

   /* private ArrayList<String> getXAxisValuesBARCHART() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Purchase");
        xAxis.add("Payment");
        xAxis.add("CreditNote");
        return xAxis;
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}