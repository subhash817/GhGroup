package com.cbs.ghgroup.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.databinding.ActivityDirectAgeingPieChartBinding;
import com.cbs.ghgroup.model.ghcustomerdashboard.GhCustomerDashboard;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.CustomProgressbar;
import com.cbs.ghgroup.utils.LogMsg;
import com.cbs.ghgroup.utils.PrefrenceKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectAgeingPieChart extends AppCompatActivity {
    ActivityDirectAgeingPieChartBinding directAgeingPieChartBinding;
    Context mContext;
    TextView tvDirectAgeingUpToSixtyDays,tvDirectAgeingSixtyToNintyDays,
            tvDirectAgeingNintyToOneTwentyDays,tvDirectAgeingOneTwentyAboveDays;
    String fromdate,todate, cusCode, branches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        directAgeingPieChartBinding = DataBindingUtil.setContentView(this,R.layout.activity_direct_ageing_pie_chart);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        mContext = DirectAgeingPieChart.this;

        fromdate = CommonMethods.getPrefsData(mContext, PrefrenceKey.FORM_DATE, "");
        todate = CommonMethods.getPrefsData(mContext, PrefrenceKey.TO_DATE, "");
        cusCode = CommonMethods.getPrefsData(mContext, PrefrenceKey.BP_CODE, "");
        branches = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH_CODE, "");


        getId();
        getDirectAgeingPieChartDetails();
    }

    public void getId() {
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.direct_ageing_graph);
        ImageView ivBack = findViewById(R.id.img_BackButton);

        tvDirectAgeingUpToSixtyDays = findViewById(R.id.tv_directAgeingUpToSixtyDays);
        tvDirectAgeingSixtyToNintyDays = findViewById(R.id.tv_directAgeingSixtyToNintyDays);
        tvDirectAgeingNintyToOneTwentyDays = findViewById(R.id.tv_directAgeingNintyToOneTwentyDays);
        tvDirectAgeingOneTwentyAboveDays = findViewById(R.id.tv_directAgeingOneTwentyAboveDays);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void getDirectAgeingPieChartDetails(){
        CustomProgressbar.showProgressBar(mContext, false);
        RetrofitClient.getClient().getGhBillingGraphDashboard(cusCode,fromdate,todate,branches).enqueue(new Callback<GhCustomerDashboard>() {
            @Override
            public void onResponse(Call<GhCustomerDashboard> call, Response<GhCustomerDashboard> response) {
                CustomProgressbar.hideProgressBar();
                if (response.code() == 200 && response.body() != null) {
                    GhCustomerDashboard ghCustomerDashboard = response.body();
                    LogMsg.d("GhCustomerDashboard",response.toString());

                    if (ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectPie().size() > 0) {

                        //ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar().get(0).getPayment();
                        // ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar().get(0).getBilling();
                        // ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectBar().get(0).getGr();

                        tvDirectAgeingUpToSixtyDays.setText(ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectPie().get(0).getSixty());
                        tvDirectAgeingSixtyToNintyDays.setText(ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectPie().get(0).getSixtyNinety());
                        tvDirectAgeingNintyToOneTwentyDays.setText(ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectPie().get(0).getNineTyOneTwety());
                        tvDirectAgeingOneTwentyAboveDays.setText(ghCustomerDashboard.getCustomerDashBoardsResult().getObjDirectPie().get(0).getOneTwetyabove());
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}