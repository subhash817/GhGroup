package com.cbs.ghgroup.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.model.directpending.DirectPendingDetail;

import java.util.List;

public class VendDirectPendingAdapter extends RecyclerView.Adapter<VendDirectPendingAdapter.myViewHolder>{
    List<DirectPendingDetail> directPendingDetails;
    Context context;
    public VendDirectPendingAdapter(Context context,List<DirectPendingDetail> directPendingDetails){
        this.directPendingDetails=directPendingDetails;
        this.context=context;
    }
    @NonNull
    @Override
    public VendDirectPendingAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.direct_pending_detail,parent,false);
        return new VendDirectPendingAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendDirectPendingAdapter.myViewHolder holder, int position) {
        DirectPendingDetail directPendingDetail=directPendingDetails.get(position);
        //holder.tv_dirPend_cust_srNo.setText("S.No. "+(position+1));

        if (position  % 2 == 0 ) {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.gray_color));
        }
        holder.tv_BalanceDue.setText(directPendingDetail.getBalanceDue());
        holder.tv_BillAmt.setText(directPendingDetail.getBillAmt());
        holder.tv_Branch.setText(directPendingDetail.getBranch());
        holder.tv_Date.setText(directPendingDetail.getDate());
        holder.tv_DueDate.setText(directPendingDetail.getDueDate());
        holder.tv_LRDate.setText(directPendingDetail.getLRDate());
        holder.tv_LRNo.setText(directPendingDetail.getLRNo());
        holder.tv_NoOfParcels.setText(directPendingDetail.getNoOfParcels());
        holder.tv_TransporterName.setText(directPendingDetail.getTransporterName());
        holder.tv_VendorName.setText(directPendingDetail.getCustomerName());
        holder.tv_VoucherNumber.setText(directPendingDetail.getVoucherNumber());
        holder.tv_VoucherType.setText(directPendingDetail.getVoucherType());
        holder.tv_BillNo.setText(directPendingDetail.getBillNO());

        if (directPendingDetail.getBillAmt().equalsIgnoreCase("Total :")) {
            holder.tv_BillAmt.setVisibility(View.INVISIBLE);

            /*if (directPendingDetail.getBalanceDue().equalsIgnoreCase(directPendingDetail.getBalanceDue())){
                holder.tv_BalanceDue.setVisibility(View.INVISIBLE);
            }*/
        }

        holder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imgMore.setVisibility(View.GONE);
                holder.basicDetail.setVisibility(View.VISIBLE);
                holder.detailView.setVisibility(View.VISIBLE);
            }
        });

        holder.TextHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imgMore.setVisibility(View.VISIBLE);
                holder.detailView.setVisibility(View.GONE);
                holder.basicDetail.setVisibility(View.VISIBLE);
            }

        });


    }

    @Override
    public int getItemCount() {
        return directPendingDetails.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tv_BalanceDue,tv_BillAmt,tv_Branch,tv_Date,tv_DueDate,tv_LRDate,tv_LRNo,tv_NoOfParcels,
                tv_TransporterName,tv_VendorName,tv_VoucherNumber,tv_VoucherType,
                tv_dirPend_cust_srNo,TextHide,tv_BillNo;
        ImageView imgMore;
        LinearLayout basicDetail;
        HorizontalScrollView detailView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_BalanceDue = itemView.findViewById(R.id.tv_BalanceDue);
            this.tv_BillAmt = itemView.findViewById(R.id.tv_BillAmt);
            this.tv_Branch = itemView.findViewById(R.id.tv_Branch);
            this.tv_Date = itemView.findViewById(R.id.tv_Date);
            this.tv_DueDate = itemView.findViewById(R.id.tv_DueDate);
            this.tv_LRDate = itemView.findViewById(R.id.tv_LRDate);
            this.tv_LRNo = itemView.findViewById(R.id.tv_LRNo);
            this.tv_NoOfParcels = itemView.findViewById(R.id.tv_NoOfParcels);
            this.tv_TransporterName = itemView.findViewById(R.id.tv_TransporterName);
            this.tv_VendorName = itemView.findViewById(R.id.tv_VendorName);
            this.tv_VoucherNumber = itemView.findViewById(R.id.tv_VoucherNumber);
            this.tv_VoucherType = itemView.findViewById(R.id.tv_VoucherType);
            this.tv_BillNo = itemView.findViewById(R.id.tv_BillNo);
            this.imgMore = itemView.findViewById(R.id.img_More);
            this.TextHide = itemView.findViewById(R.id.TextHide);
            this.basicDetail = itemView.findViewById(R.id.dir_pend_bill_basicll);
            this.detailView = itemView.findViewById(R.id.moreDetails);
            //this.tv_dirPend_cust_srNo = itemView.findViewById(R.id.txt_dirPend_cust_srNo);
        }
    }
}
