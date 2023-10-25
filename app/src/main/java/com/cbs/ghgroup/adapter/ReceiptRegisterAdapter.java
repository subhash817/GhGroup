package com.cbs.ghgroup.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.model.receiptregister.ReceiptDetail;

import java.util.List;

public class ReceiptRegisterAdapter extends RecyclerView.Adapter<ReceiptRegisterAdapter.myViewHolder> {
    private List<ReceiptDetail> receiptRegisters;
    Context context;
    String voucher;
    String fromdate, type,todate, bpcode, branches,DocEntry;
    DownloadManager manager;

    public ReceiptRegisterAdapter(Context context,List<ReceiptDetail> receiptRegisters){
        this.receiptRegisters = receiptRegisters;
        this.context = context;
    }

    @NonNull
    @Override
    public ReceiptRegisterAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.receipt_reg_item_details, parent, false);
        return new ReceiptRegisterAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptRegisterAdapter.myViewHolder holder, int position) {
        ReceiptDetail receiptDetail = receiptRegisters.get(position);

        if (position  % 2 == 0 ) {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.gray_color));
        }

        holder.tv_DocNumber.setText(receiptDetail.getDocNumber());
        holder.tv_Branch.setText(receiptDetail.getBranch());
        holder.tv_modeOfPayment.setText(receiptDetail.getModeOfPayment());
        holder.tv_Amount.setText(receiptDetail.getTotalAmount());
        holder.tv_seriesName.setText(receiptDetail.getSeriesName());
        holder.tv_cheque_refNo.setText(receiptDetail.getChequeRefNo());
        holder.tv_chaque_refNoDate.setText(receiptDetail.getChequeRefDate());
        holder.tv_bankAccountName.setText(receiptDetail.getBankAccountName());
        holder.tv_InstrumentNo.setText(receiptDetail.getInstNo());
        holder.tv_InstrumentDate.setText(receiptDetail.getInstDate());

        if (receiptDetail.getBranch().equalsIgnoreCase("Total :")) {
            holder.tv_Amount.setVisibility(View.INVISIBLE);

            if (receiptRegisters.get(0).getTotalAmount().equalsIgnoreCase(receiptRegisters.get(0).getTotalAmount())){
                holder.tv_Amount.setVisibility(View.INVISIBLE);
                holder.tv_Amount.setTypeface(holder.tv_Amount.getTypeface(), Typeface.BOLD);
            }

            if (receiptDetail.getDocNumber().equalsIgnoreCase("0")) {
                holder.tv_DocNumber.setVisibility(View.INVISIBLE);
            }
        }

        holder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imgMore.setVisibility(View.GONE);
                holder.detailView.setVisibility(View.VISIBLE);
                holder.basicDetail.setVisibility(View.VISIBLE);
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
        return receiptRegisters.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_DocNumber,tv_Branch,tv_modeOfPayment,tv_Amount,tv_seriesName,tv_cheque_refNo,
                tv_bankAccountName,tv_chaque_refNoDate,tv_InstrumentNo,tv_InstrumentDate,TextHide;
        Button btnPdf,btnExcel;
        ImageView imgMore,btDownload;
        LinearLayout customListPage,basicDetail;
        HorizontalScrollView detailView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_Branch = itemView.findViewById(R.id.tv_Branch);
            this.tv_DocNumber = itemView.findViewById(R.id.tv_DocNumber);
            this.tv_modeOfPayment = itemView.findViewById(R.id.tv_modeOfPayment);
            this.tv_Amount = itemView.findViewById(R.id.tv_Amount);
            this.tv_seriesName = itemView.findViewById(R.id.tv_seriesName);
            this.tv_cheque_refNo = itemView.findViewById(R.id.tv_cheque_refNo);
            this.tv_bankAccountName = itemView.findViewById(R.id.tv_bankAccountName);
            this.tv_chaque_refNoDate = itemView.findViewById(R.id.tv_chaque_refNoDate);
            this.tv_InstrumentNo = itemView.findViewById(R.id.tv_InstrumentNo);
            this.tv_InstrumentDate = itemView.findViewById(R.id.tv_InstrumentDate);

            this.imgMore = itemView.findViewById(R.id.img_More);
            this.TextHide = itemView.findViewById(R.id.TextHide);
            this.basicDetail = itemView.findViewById(R.id.basicll);
            this.detailView = itemView.findViewById(R.id.moreDetails);
        }
    }
}
