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
import com.cbs.ghgroup.model.debitregister.DebitNoteDetail;

import java.util.List;

public class DebitRegisterAdapter extends RecyclerView.Adapter<DebitRegisterAdapter.myViewHolder> {
    private List<DebitNoteDetail> debitNoteDetails;
    Context context;

    public DebitRegisterAdapter(Context context, List<DebitNoteDetail> debitNoteDetails) {
        this.debitNoteDetails = debitNoteDetails;
        this.context = context;
    }
    @NonNull
    @Override
    public DebitRegisterAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.debit_item_register, parent, false);
        return new DebitRegisterAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DebitRegisterAdapter.myViewHolder holder, int position) {
        DebitNoteDetail debitNoteDetail = debitNoteDetails.get(position);

        if (position  % 2 == 0 ) {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.gray_color));
        }

        holder.tv_Branch.setText(debitNoteDetail.getBranch());
        holder.tv_CustomerName.setText(debitNoteDetail.getCustomerName());
        holder.tv_Date.setText(debitNoteDetail.getDate());
        holder.tv_LRDate.setText(debitNoteDetail.getLrDate());
        holder.tv_LRNo.setText(debitNoteDetail.getLRNo());
        holder.tv_TotalAmt.setText(debitNoteDetail.getTotalAmount());
        holder.tv_TransporterName.setText(debitNoteDetail.getTransporterName());
        holder.tv_VoucherNumber.setText(debitNoteDetail.getVchrNo());

        if (debitNoteDetail.getBranch().equalsIgnoreCase("Total :")) {
            holder.tv_Branch.setVisibility(View.INVISIBLE);

            if (debitNoteDetails.get(0).getTotalAmount().equalsIgnoreCase(debitNoteDetails.get(0).getTotalAmount())){
                holder.tv_TotalAmt.setVisibility(View.INVISIBLE);
            }
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
        return debitNoteDetails.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Branch, tv_CustomerName, tv_Date, tv_DueDate, tv_LRDate,
                tv_LRNo, tv_Narration, tv_TotalAmt, tv_TransporterName, tv_VoucherNumber,
                tv_credNote_cust_srNo,TextHide;
        ImageView imgMore;
        LinearLayout basicDetail;
        HorizontalScrollView detailView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tv_Branch = itemView.findViewById(R.id.tv_Branch);
            this.tv_CustomerName = itemView.findViewById(R.id.tv_CustomerName);
            this.tv_Date = itemView.findViewById(R.id.tv_Date);
            this.tv_LRDate = itemView.findViewById(R.id.tv_LRDate);
            this.tv_LRNo = itemView.findViewById(R.id.tv_LRNo);
            this.tv_TotalAmt = itemView.findViewById(R.id.tv_TotalAmt);
            this.tv_TransporterName = itemView.findViewById(R.id.tv_TransporterName);
            this.tv_VoucherNumber = itemView.findViewById(R.id.tv_VoucherNumber);

            this.imgMore = itemView.findViewById(R.id.img_More);
            this.TextHide = itemView.findViewById(R.id.TextHide);
            this.basicDetail = itemView.findViewById(R.id.dr_basicll);
            this.detailView = itemView.findViewById(R.id.moreDetails);
        }
    }
}
