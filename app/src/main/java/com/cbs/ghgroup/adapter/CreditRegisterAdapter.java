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
import com.cbs.ghgroup.model.creditregister.CreditRegisterDetail;

import java.util.List;

public class CreditRegisterAdapter extends RecyclerView.Adapter<CreditRegisterAdapter.myViewHolder> {
    private List<CreditRegisterDetail> creditRegisterDetails;
    Context context;

    public CreditRegisterAdapter(Context context, List<CreditRegisterDetail> creditRegisterDetails) {
        this.creditRegisterDetails = creditRegisterDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public CreditRegisterAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.credit_register_items, parent, false);
        return new myViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CreditRegisterAdapter.myViewHolder holder, int position) {
        CreditRegisterDetail creditRegisterDetail = creditRegisterDetails.get(position);
        // holder.tv_credNote_cust_srNo.setText("S.No. "+(position+1));
        if (position  % 2 == 0 ) {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.gray_color));
        }
        holder.tv_Balance.setText(creditRegisterDetail.getBalanceDue());
        holder.tv_Branch.setText(creditRegisterDetail.getBranch());
        holder.tv_CustomerName.setText(creditRegisterDetail.getCustomerName());
        holder.tv_Date.setText(creditRegisterDetail.getDate());
        holder.tv_DueDate.setText(creditRegisterDetail.getDueDate());
        holder.tv_LRDate.setText(creditRegisterDetail.getLRDate());
        holder.tv_LRNo.setText(creditRegisterDetail.getLRNo());
        holder.tv_Narration.setText(creditRegisterDetail.getNarration());
        holder.tv_TotalAmt.setText(creditRegisterDetail.getTotalAmt());
        holder.tv_TransporterName.setText(creditRegisterDetail.getTransporterName());
        holder.tv_VoucherNumber.setText(creditRegisterDetail.getVoucherNumber());

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
        return creditRegisterDetails.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Balance, tv_Branch, tv_CustomerName, tv_Date, tv_DueDate, tv_LRDate,
                tv_LRNo, tv_Narration, tv_TotalAmt, tv_TransporterName, tv_VoucherNumber,
                tv_credNote_cust_srNo,TextHide;
        ImageView imgMore;
        LinearLayout basicDetail;
        HorizontalScrollView detailView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_Balance = itemView.findViewById(R.id.tv_Balance);
            this.tv_Branch = itemView.findViewById(R.id.tv_Branch);
            this.tv_CustomerName = itemView.findViewById(R.id.tv_CustomerName);
            this.tv_Date = itemView.findViewById(R.id.tv_Date);
            this.tv_DueDate = itemView.findViewById(R.id.tv_DueDate);
            this.tv_LRDate = itemView.findViewById(R.id.tv_LRDate);
            this.tv_LRNo = itemView.findViewById(R.id.tv_LRNo);
            this.tv_Narration = itemView.findViewById(R.id.tv_Narration);
            this.tv_TotalAmt = itemView.findViewById(R.id.tv_TotalAmt);
            this.tv_TransporterName = itemView.findViewById(R.id.tv_TransporterName);
            this.tv_VoucherNumber = itemView.findViewById(R.id.tv_VoucherNumber);
            this.imgMore = itemView.findViewById(R.id.img_More);
            this.TextHide = itemView.findViewById(R.id.TextHide);
            this.basicDetail = itemView.findViewById(R.id.cr_basicll);
            this.detailView = itemView.findViewById(R.id.moreDetails);
            //this.tv_credNote_cust_srNo = itemView.findViewById(R.id.txt_credNote_cust_srNo);
        }
    }
}
