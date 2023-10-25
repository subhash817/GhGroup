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
import com.cbs.ghgroup.model.billregister.BillRegisterDetail;

import java.util.List;

public class BillRegisterAdapter extends RecyclerView.Adapter<BillRegisterAdapter.myViewHolder> {
    private List<BillRegisterDetail> billRegisterDetails;
    Context context;
    public BillRegisterAdapter(Context context,List<BillRegisterDetail> billRegisterDetails){
        this.billRegisterDetails=billRegisterDetails;
        this.context=context;
    }
    @NonNull
    @Override
    public BillRegisterAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.bill_register_detail,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillRegisterAdapter.myViewHolder holder, int position) {
        BillRegisterDetail billRegisterDetail=billRegisterDetails.get(position);
        //holder.tv_billReg_cust_srNo.setText("S.No. "+(position+1));
        if (position  % 2 == 0 ) {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.gray_color));
        }
        holder.tv_BillAmt.setText(billRegisterDetail.getBillAmt());
        holder.tv_BpCode.setText(billRegisterDetail.getBpCode());
        holder.tv_BpName.setText(billRegisterDetail.getBpName());
        holder.tv_Branch.setText(billRegisterDetail.getBranch());
        holder.tv_Date.setText(billRegisterDetail.getDate());
        holder.tv_DocNumber.setText(billRegisterDetail.getDocNumber());
        holder.tv_LRDate.setText(billRegisterDetail.getLRDate());
        holder.tv_LRNo.setText(billRegisterDetail.getLRNo());
        holder.tv_NoOfParcels.setText(billRegisterDetail.getNoOfParcels());
        holder.tv_TransporterName.setText(billRegisterDetail.getTransporterName());
        holder.tv_VendorBillNo.setText(billRegisterDetail.getVendorBillNo());
        holder.tv_VendorName.setText(billRegisterDetail.getVendorName());

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
        return billRegisterDetails.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tv_BillAmt,tv_BpCode,tv_BpName,tv_Branch,tv_Date,tv_DocNumber,tv_LRDate,tv_LRNo,tv_NoOfParcels,
                tv_TransporterName,tv_VendorBillNo,tv_VendorName,tv_billReg_cust_srNo,TextHide;
        ImageView imgMore;
        LinearLayout basicDetail;
        HorizontalScrollView detailView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_BillAmt = itemView.findViewById(R.id.tv_BillAmt);
            this.tv_BpCode = itemView.findViewById(R.id.tv_BpCode);
            this.tv_BpName = itemView.findViewById(R.id.tv_BpName);
            this.tv_Branch = itemView.findViewById(R.id.tv_Branch);
            this.tv_Date = itemView.findViewById(R.id.tv_Date);
            this.tv_DocNumber = itemView.findViewById(R.id.tv_DocNumber);
            this.tv_LRDate = itemView.findViewById(R.id.tv_LRDate);
            this.tv_LRNo = itemView.findViewById(R.id.tv_LRNo);
            this.tv_NoOfParcels = itemView.findViewById(R.id.tv_NoOfParcels);
            this.tv_TransporterName = itemView.findViewById(R.id.tv_TransporterName);
            this.tv_VendorBillNo = itemView.findViewById(R.id.tv_VendorBillNo);
            this.tv_VendorName = itemView.findViewById(R.id.tv_VendorName);
            this.imgMore = itemView.findViewById(R.id.img_More);
            this.TextHide = itemView.findViewById(R.id.TextHide);
            this.basicDetail = itemView.findViewById(R.id.bill_basicll);
            this.detailView = itemView.findViewById(R.id.moreDetails);
            //this.tv_billReg_cust_srNo = itemView.findViewById(R.id.txt_billReg_cust_srNo);
        }
    }
}
