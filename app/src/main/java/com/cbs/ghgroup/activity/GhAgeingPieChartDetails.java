package com.cbs.ghgroup.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.adapter.AgeingPieChartAdapter;
import com.cbs.ghgroup.databinding.ActivityGhAgeingPieChartDetailsBinding;
import com.cbs.ghgroup.model.ageingpiechart.AgeingDetails;
import com.cbs.ghgroup.model.ageingpiechart.ObjAgeingDetail;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.CustomProgressbar;
import com.cbs.ghgroup.utils.PrefrenceKey;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GhAgeingPieChartDetails extends AppCompatActivity {
    ActivityGhAgeingPieChartDetailsBinding ghAgeingPieChartDetailsBinding;
    Context mContext;
    String fromdate, todate, cardcode, Branch,branch_code,type,flag;
    DownloadManager manager;
    RecyclerView rvGhAgeingPieChartDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ghAgeingPieChartDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_gh_ageing_pie_chart_details);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        mContext = GhAgeingPieChartDetails.this;

        fromdate = CommonMethods.getPrefsData(mContext, PrefrenceKey.FORM_DATE, "");
        todate = CommonMethods.getPrefsData(mContext, PrefrenceKey.TO_DATE, "");
        cardcode = CommonMethods.getPrefsData(mContext, PrefrenceKey.BP_CODE, "");
        Branch = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH, "");
        type = CommonMethods.getPrefsData(mContext, PrefrenceKey.TYPE, "");
        branch_code = CommonMethods.getPrefsData(mContext, PrefrenceKey.BRANCH_CODE, "");

        getId();
    }

    public void getId() {
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.gh_ageing_graph);
        ImageView ivBack = findViewById(R.id.img_BackButton);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        CustomProgressbar.showProgressBar(mContext, false);

        RetrofitClient.getClient().getAgeingPieChart(todate,cardcode,branch_code,type,"G").enqueue(new Callback<AgeingDetails>() {
            @Override
            public void onResponse(Call<AgeingDetails> call, Response<AgeingDetails> response) {
                CustomProgressbar.hideProgressBar();
                if (response.code() ==200 && response.body() !=null){
                    AgeingDetails ageingDetails= response.body();
                    if (ageingDetails.getAgeingDetailsResult().getObjAgeingDetail().size() > 0) {
                        ageingDetails.getAgeingDetailsResult().getObjAgeingDetail().get(0).getBranch();
                        Log.e("TAG", "Request: " + response.toString());
                        //CustomerLedger customerLedger = response.body();
                        List<ObjAgeingDetail> ageingDetailsList=ageingDetails.getAgeingDetailsResult().getObjAgeingDetail();
                        Log.e("TAG", "Request: " + response.toString());

                        rvGhAgeingPieChartDetails = (RecyclerView) findViewById(R.id.rcvGhAgeingPieChartDetails);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        ghAgeingPieChartDetailsBinding.rcvGhAgeingPieChartDetails.setLayoutManager(linearLayoutManager);
                        AgeingPieChartAdapter ageingPieChartAdapter = new AgeingPieChartAdapter(GhAgeingPieChartDetails.this,ageingDetailsList);
                        ghAgeingPieChartDetailsBinding.rcvGhAgeingPieChartDetails.setAdapter(ageingPieChartAdapter);

                    }
                    else if (ageingDetails.getAgeingDetailsResult().getLogMessage().equals("false")) {
                    } else {
                        Toast.makeText(mContext, "" + ageingDetails.getAgeingDetailsResult().getLogMessage().getErrorMsg().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<AgeingDetails> call, Throwable t) {
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