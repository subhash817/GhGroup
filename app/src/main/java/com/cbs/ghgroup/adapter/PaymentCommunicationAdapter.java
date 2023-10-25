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
import com.cbs.ghgroup.model.paymentcommunication.PaymentCommunicationDetail;

import java.util.List;

public class PaymentCommunicationAdapter extends RecyclerView.Adapter<PaymentCommunicationAdapter.myViewHolder> {
    List<PaymentCommunicationDetail> paymentCommunicationDetails;
    Context context;
    public PaymentCommunicationAdapter(Context context, List<PaymentCommunicationDetail > paymentCommunicationDetails){
        this.paymentCommunicationDetails=paymentCommunicationDetails;
        this.context=context;
    }
    @NonNull
    @Override
    public PaymentCommunicationAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.payment_communication_detail,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentCommunicationAdapter.myViewHolder holder, int position) {
        PaymentCommunicationDetail paymentCommunicationDetail=paymentCommunicationDetails.get(position);
        //holder.tv_payComm_cust_srNo.setText("S.No. "+(position+1));
        if (position  % 2 == 0 ) {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.gray_color));
        }
        holder.tv_BillDate.setText(paymentCommunicationDetail.getBillDate());
        holder.tv_BillNO.setText(paymentCommunicationDetail.getBillNO());
        holder.tv_Branch.setText(paymentCommunicationDetail.getBranch());
        holder.tv_DocNumber.setText(paymentCommunicationDetail.getDocNumber());
        holder.tv_ModeOfPayment.setText(paymentCommunicationDetail.getModeOfPayment());
        holder.tv_Narration.setText(paymentCommunicationDetail.getNarration());
        holder.tv_PostingDate.setText(paymentCommunicationDetail.getPostingDate());
        holder.tv_TotalAmt.setText(paymentCommunicationDetail.getTotalAmt());
        holder.tv_VendorBillNo.setText(paymentCommunicationDetail.getVendorBillNo());
        holder.tv_VendorName.setText(paymentCommunicationDetail.getVendorName());

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
        return paymentCommunicationDetails.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tv_BillDate,tv_BillNO,tv_Branch,tv_DocNumber,
                tv_ModeOfPayment,tv_Narration,tv_PostingDate,tv_TotalAmt,
                tv_VendorBillNo,tv_VendorName,tv_payComm_cust_srNo,TextHide;
        ImageView imgMore;
        LinearLayout basicDetail;
        HorizontalScrollView detailView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_BillDate = itemView.findViewById(R.id.tv_BillDate);
            this.tv_BillNO = itemView.findViewById(R.id.tv_BillNO);
            this.tv_Branch = itemView.findViewById(R.id.tv_Branch);
            this.tv_DocNumber = itemView.findViewById(R.id.tv_DocNumber);
            this.tv_ModeOfPayment = itemView.findViewById(R.id.tv_ModeOfPayment);
            this.tv_Narration = itemView.findViewById(R.id.tv_Narration);
            this.tv_PostingDate = itemView.findViewById(R.id.tv_PostingDate);
            this.tv_TotalAmt = itemView.findViewById(R.id.tv_TotalAmt);
            this.tv_VendorBillNo = itemView.findViewById(R.id.tv_VendorBillNo);
            this.tv_VendorName = itemView.findViewById(R.id.tv_VendorName);
            this.imgMore = itemView.findViewById(R.id.img_More);
            this.TextHide = itemView.findViewById(R.id.TextHide);
            this.basicDetail = itemView.findViewById(R.id.pay_comm_basicll);
            this.detailView = itemView.findViewById(R.id.moreDetails);
            //this.tv_payComm_cust_srNo = itemView.findViewById(R.id.txt_payComm_cust_srNo);
        }
    }
}
