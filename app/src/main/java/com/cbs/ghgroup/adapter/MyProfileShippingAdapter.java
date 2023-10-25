package com.cbs.ghgroup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.model.profile.ShippingAddress;

import java.util.List;

public class MyProfileShippingAdapter extends RecyclerView.Adapter<MyProfileShippingAdapter.myViewHolder> {
    private List<ShippingAddress> userProfileDetails;
    Context context;

    public MyProfileShippingAdapter(Context context,List<ShippingAddress> userProfileDetails){
        this.userProfileDetails = userProfileDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public MyProfileShippingAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.myprofile_shipping_item, parent, false);
        return new MyProfileShippingAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyProfileShippingAdapter.myViewHolder holder, int position) {
        ShippingAddress shippingAddress = userProfileDetails.get(position);

        holder.tv_comp_shipping_Add.setText(shippingAddress.getAddress());
        holder.tv_comp_shipping_Gstin.setText(shippingAddress.getGstIn());

    }

    @Override
    public int getItemCount() {
        return userProfileDetails.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tv_comp_shipping_Add,tv_comp_shipping_Gstin;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_comp_shipping_Add = itemView.findViewById(R.id.txt_comp_shipping_Add);
            tv_comp_shipping_Gstin = itemView.findViewById(R.id.txt_comp_shipping_Gstin);
        }
    }
}
