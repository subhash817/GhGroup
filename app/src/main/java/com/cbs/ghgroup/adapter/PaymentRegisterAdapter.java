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
import com.cbs.ghgroup.model.paymentregister.PaymentRegisterDetail;

import java.util.List;

public class PaymentRegisterAdapter extends RecyclerView.Adapter<PaymentRegisterAdapter.myViewHolder> {
    private List<PaymentRegisterDetail> paymentRegisterDetails;
    Context context;

    public PaymentRegisterAdapter(Context context,List<PaymentRegisterDetail> paymentRegisterDetails){
        this.paymentRegisterDetails=paymentRegisterDetails;
        this.context=context;
    }
    @NonNull
    @Override
    public PaymentRegisterAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.payment_items, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentRegisterAdapter.myViewHolder holder, int position) {
        PaymentRegisterDetail paymentRegisterDetail=paymentRegisterDetails.get(position);
        //holder.tv_payReg_cust_srNo.setText("S.No. "+(position+1));
        if (position  % 2 == 0 ) {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.gray_color));
        }
        holder.tv_Branch.setText(paymentRegisterDetail.getBranch());
        holder.tv_Date.setText(paymentRegisterDetail.getDate());
        holder.tv_DocNumber.setText(paymentRegisterDetail.getDocNumber());
        holder.tv_InstrumentDate.setText(paymentRegisterDetail.getInstrumentDate());
        holder.tv_ModeOfPayment.setText(paymentRegisterDetail.getModeOfPayment());
        holder.tv_Narration.setText(paymentRegisterDetail.getNarration());
        holder.tv_RefBills.setText(paymentRegisterDetail.getRefBills());
        holder.tv_TotalAmt.setText(paymentRegisterDetail.getTotalAmt());

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
        return paymentRegisterDetails.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Branch,tv_Date,tv_DocNumber,tv_InstrumentDate,tv_InstrumentNo,tv_ModeOfPayment,tv_Narration,
                tv_RefBills,tv_TotalAmt,tv_payReg_cust_srNo,TextHide;
        ImageView imgMore;
        LinearLayout customListPage,basicDetail;
        HorizontalScrollView detailView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_Branch = itemView.findViewById(R.id.tv_Branch);
            this.tv_Date = itemView.findViewById(R.id.tv_Date);
            this.tv_DocNumber = itemView.findViewById(R.id.tv_DocNumber);
            this.tv_InstrumentDate = itemView.findViewById(R.id.tv_InstrumentDate);
            this.tv_InstrumentNo = itemView.findViewById(R.id.tv_InstrumentNo);
            this.tv_ModeOfPayment = itemView.findViewById(R.id.tv_ModeOfPayment);
            this.tv_Narration = itemView.findViewById(R.id.tv_Narration);
            this.tv_RefBills = itemView.findViewById(R.id.tv_RefBills);
            this.tv_TotalAmt = itemView.findViewById(R.id.tv_TotalAmt);
            this.imgMore = itemView.findViewById(R.id.img_More);
            this.TextHide = itemView.findViewById(R.id.TextHide);
            this.basicDetail = itemView.findViewById(R.id.payment_basicll);
            this.detailView = itemView.findViewById(R.id.moreDetails);
            // this.tv_payReg_cust_srNo = itemView.findViewById(R.id.txt_payReg_cust_srNo);
        }
    }
}
