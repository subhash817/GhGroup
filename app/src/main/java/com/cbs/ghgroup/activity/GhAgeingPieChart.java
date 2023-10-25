package com.cbs.ghgroup.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.databinding.ActivityGhAgeingPieChartBinding;
import com.cbs.ghgroup.model.ghcustomerdashboard.GhCustomerDashboard;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.CustomProgressbar;
import com.cbs.ghgroup.utils.LogMsg;
import com.cbs.ghgroup.utils.PrefrenceKey;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GhAgeingPieChart extends AppCompatActivity {
    ActivityGhAgeingPieChartBinding ghAgeingPieChartBinding;
    Context mContext;
    TextView tvGhAgeingUpToSixtyDays,tvGhAgeingSixtyToNintyDays,
            tvGhAgeingNintyToOneTwentyDays,tvGhAgeingOneTwentyAboveDays;
    String fromdate,todate, cusCode, branches;
    LinearLayout llGhPieChartDetail;
    private ArrayList<String> alxValues = new ArrayList<>();
    private ArrayList<Entry> alyValues = new ArrayList<>();
    private ArrayList<String> alzValues = new ArrayList<>();
    private ArrayList<String> alvalues = new ArrayList<>();
    private String branchList_name, branchList_code, user_code, user_name;
    String code, customerCode, role, cv_Code, type, isCustomerVendor, userList;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ghAgeingPieChartBinding = DataBindingUtil.setContentView(this,R.layout.activity_gh_ageing_pie_chart);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        mContext = GhAgeingPieChart.this;

        code = CommonMethods.getPrefsData(mContext, PrefrenceKey.CODE, "");
        cv_Code = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");
        role = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");
        type = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");
        isCustomerVendor = CommonMethods.getPrefsData(mContext, PrefrenceKey.IS_CUSTOMER_OR_VENDOR, "");


        fromdate = CommonMethods.getPrefsData(mContext, PrefrenceKey.FORM_DATE, "");
        todate = CommonMethods.getPrefsData(mContext, PrefrenceKey.TO_DATE, "");
        cusCode = CommonMethods.getPrefsData(mContext, PrefrenceKey.BP_CODE, "");
        branches = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH_CODE, "");


        getId();
        getGhAgeingPieChart();
    }

    public void getId() {
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.gh_ageing_graph);
        ImageView ivBack = findViewById(R.id.img_BackButton);
        pieChart = findViewById(R.id.piechart_cust_gh_AgeingDetails);

        tvGhAgeingUpToSixtyDays = findViewById(R.id.tv_ghAgeingUpToSixtyDays);
        tvGhAgeingSixtyToNintyDays = findViewById(R.id.tv_ghAgeingSixtyToNintyDays);
        tvGhAgeingNintyToOneTwentyDays = findViewById(R.id.tv_ghAgeingNintyToOneTwentyDays);
        tvGhAgeingOneTwentyAboveDays = findViewById(R.id.tv_ghAgeingOneTwentyAboveDays);
        llGhPieChartDetail = findViewById(R.id.ll_GhPieChartDetail);

        ghAgeingPieChartBinding.llGhAgeingGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethods.setPrefsData(mContext, PrefrenceKey.FORM_DATE, fromdate);
                CommonMethods.setPrefsData(mContext, PrefrenceKey.TO_DATE, todate);
                CommonMethods.setPrefsData(mContext, PrefrenceKey.BP_CODE, customerCode);
                CommonMethods.setPrefsData(mContext, PrefrenceKey.TYPE, "");
                CommonMethods.setPrefsData(mContext, PrefrenceKey.BRANCH, branches);
                CommonMethods.setPrefsData(mContext, PrefrenceKey.BRANCH_CODE, branchList_code);
                Intent intent = new Intent(getApplicationContext(), GhAgeingPieChartDetails.class);
                startActivity(intent);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void getGhAgeingPieChart(){
        CustomProgressbar.showProgressBar(mContext, false);
        RetrofitClient.getClient().getGhBillingGraphDashboard(cusCode,fromdate,todate,branches).enqueue(new Callback<GhCustomerDashboard>() {
            @Override
            public void onResponse(Call<GhCustomerDashboard> call, Response<GhCustomerDashboard> response) {
                CustomProgressbar.hideProgressBar();
                alyValues.clear();
                alxValues.clear();
                alzValues.clear();
                alvalues.clear();
                if (response.code() == 200 && response.body() != null) {
                    GhCustomerDashboard ghCustomerDashboard = response.body();
                    LogMsg.d("GhCustomerDashboard",response.toString());

                    if (ghCustomerDashboard.getCustomerDashBoardsResult().getObjGHPi().size() > 0) {

                        ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar().get(0).getPayment();
                        ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar().get(0).getBilling();
                        ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar().get(0).getGr();

                        tvGhAgeingUpToSixtyDays.setText(ghCustomerDashboard.getCustomerDashBoardsResult().getObjGHPi().get(0).getSixty());
                        tvGhAgeingSixtyToNintyDays.setText(ghCustomerDashboard.getCustomerDashBoardsResult().getObjGHPi().get(0).getSixtyNinety());
                        tvGhAgeingNintyToOneTwentyDays.setText(ghCustomerDashboard.getCustomerDashBoardsResult().getObjGHPi().get(0).getNineTyOneTwety());
                        tvGhAgeingOneTwentyAboveDays.setText(ghCustomerDashboard.getCustomerDashBoardsResult().getObjGHPi().get(0).getOneTwetyabove());
                        setData();

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

    public void setData(){
        pieChart.setUsePercentValues(true);
        PieDataSet dataSet = new PieDataSet(alyValues,"");
        PieData data = new PieData(alxValues, dataSet);
        data.setDrawValues(false);

        pieChart.setData(data);
        pieChart.setDescription("");
        pieChart.getLegend().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(5f);

        pieChart.setHoleRadius(30f);
        pieChart.setCenterTextSize(14f);
        pieChart.setCenterTextColor(Color.BLACK);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(Typeface.SERIF);
        pieChart.animateXY(1400, 1400);
        pieChart.invalidate();
        pieChart.setFitsSystemWindows(true);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}