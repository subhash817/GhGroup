package com.cbs.ghgroup.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.adapter.MyProfileShippingAdapter;
import com.cbs.ghgroup.databinding.ActivityMyProfileBinding;
import com.cbs.ghgroup.model.profile.ObjUserProfileDetail;
import com.cbs.ghgroup.model.profile.ShippingAddress;
import com.cbs.ghgroup.model.profile.UserProfile;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.CustomProgressbar;
import com.cbs.ghgroup.utils.LogMsg;
import com.cbs.ghgroup.utils.PrefrenceKey;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileActivity extends AppCompatActivity {
    Context mContext;
    ActivityMyProfileBinding profileBinding;
    String userName,CardCode,Branch,IsCV, strCompName, strGstin, strCompBillingAdd, strCompShippingAdd, strCompShippingGstin;
    TextView tvCompName, tvGstin, tvCompBillingAdd, tvCompShippingAdd, tvCompShippingGstin;
    RecyclerView rvcMyProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_profile);
        profileBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        mContext = MyProfileActivity.this;
        CardCode=CommonMethods.getPrefsData(mContext,PrefrenceKey.CODE,"");
        Branch=CommonMethods.getPrefsData(mContext,PrefrenceKey.BRANCH,"");
        IsCV=CommonMethods.getPrefsData(mContext,PrefrenceKey.IS_CUSTOMER_OR_VENDOR,"");

        getId();
        profile();

    }

    public void getId() {
        ImageView ivBack = findViewById(R.id.img_BackButton);
        TextView setActivityName = findViewById(R.id.txtHeader);
        tvCompName = findViewById(R.id.txt_comp_name);
        tvGstin = findViewById(R.id.txt_gst_in);
        tvCompBillingAdd = findViewById(R.id.txt_comp_billing_Add);
        //tvCompShippingAdd = findViewById(R.id.txt_comp_shipping_Add);
        //tvCompShippingGstin = findViewById(R.id.txt_comp_shipping_Gstin);
        userName = CommonMethods.getPrefsData(mContext, PrefrenceKey.NAME, "");
        profileBinding.txtusername.setText(userName);
        setActivityName.setText(R.string.my_profile);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void profile() {
        CustomProgressbar.showProgressBar(mContext, false);
        RetrofitClient.getClient().getUserProfile(CardCode, Branch, IsCV).enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                CustomProgressbar.hideProgressBar();
                if (response.code() == 200 && response.body() != null) {
                    LogMsg.d("User_profile", response.toString());
                    UserProfile userProfile = response.body();

                    List<ObjUserProfileDetail> objUserProfile =
                            userProfile.getUserProfileResult().getObjUserProfileDetail();

                    for (int i=0;i<objUserProfile.size();i++){
                        tvCompName.setText(objUserProfile.get(i).getCompany());
                        tvGstin.setText(objUserProfile.get(i).getGstIn());
                        tvCompBillingAdd.setText(objUserProfile.get(i).getBillingAddress());

                        List<ShippingAddress> shippingAddresses = userProfile.getUserProfileResult().getObjUserProfileDetail().get(i).getShippingAddresses();

                        rvcMyProfile = (RecyclerView) findViewById(R.id.rv_cust_profile_shipping_list);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rvcMyProfile.setLayoutManager(linearLayoutManager);
                        MyProfileShippingAdapter customAdapter = new MyProfileShippingAdapter(MyProfileActivity.this, shippingAddresses);
                        rvcMyProfile.setAdapter(customAdapter);
                        //tvCompShippingAdd.setText(objUserProfile.get(i).getShippingAddresses().get(i).getAddress());
                        //tvCompShippingGstin.setText(objUserProfile.get(i).getShippingAddresses().get(i).getGstIn());
                    }

                } else {
                    Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                CustomProgressbar.hideProgressBar();

            }
        });
    }
}
