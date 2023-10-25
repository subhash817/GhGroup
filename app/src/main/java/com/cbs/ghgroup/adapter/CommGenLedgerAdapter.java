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
import com.cbs.ghgroup.model.commgenledger.GeneralReportDetail;

import java.util.List;

public class CommGenLedgerAdapter extends RecyclerView.Adapter<CommGenLedgerAdapter.myViewHolder> {
    List<GeneralReportDetail> generalReportDetails;
    Context context;
    String voucher;
    String fromdate, type,todate, bpcode, branches,DocEntry;
    DownloadManager manager;

    public CommGenLedgerAdapter(Context context,List<GeneralReportDetail> generalReportDetails){
        this.generalReportDetails = generalReportDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.comm_genled_details, parent, false);
        return new CommGenLedgerAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
     GeneralReportDetail generalReportDetail = generalReportDetails.get(position);

        if (position  % 2 == 0 ) {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.gray_color));
        }


        if (generalReportDetail.getFlag().equals("C")){
            holder.tv_Amount.setText(generalReportDetail.getAmount());
            holder.tv_Amount.setTextColor(ContextCompat.getColor(context,R.color.dark_green));
        }
        else if (generalReportDetail.getFlag().equalsIgnoreCase("D")){
            holder.tv_Amount.setText(generalReportDetail.getAmount());
            holder.tv_Amount.setTextColor(ContextCompat.getColor(context,R.color.red));
        } else if (generalReportDetail.getFlag().equalsIgnoreCase("")){
            holder.tv_Amount.setText(generalReportDetail.getAmount());
            holder.tv_Amount.setTextColor(ContextCompat.getColor(context,R.color.darkblack));
        }

        holder.tv_Date.setText(generalReportDetail.getDate());
        holder.tv_Credit.setText(generalReportDetail.getCredit());
        holder.tv_Debit.setText(generalReportDetail.getDebit());
        //holder.tv_VendBillNo.setText(generalReportDetail.getVendorBillNo());
        holder.tv_LRDate.setText(generalReportDetail.getLRDate());
        holder.tv_LRNO.setText(generalReportDetail.getLRNo());
        holder.tv_NoOfParcels.setText(generalReportDetail.getNoOfParcels());
        holder.tv_OutStanding.setText(generalReportDetail.getOutStanding());
        holder.tv_TransporterName.setText(generalReportDetail.getTransporterName());
        holder.tv_VendorName.setText(generalReportDetail.getVendorName());
        //holder.tv_VendorName.setText(generalReportDetail.getCustomerName());
        holder.tv_VoucherNumber.setText(generalReportDetail.getVoucherNumber());
        holder.tv_Amount.setText(generalReportDetail.getAmount());

        if (!generalReportDetail.getVendorBillNo().equals("")){
            holder.tv_VoucherType.setText(generalReportDetail.getVoucherType()+" - "+generalReportDetail.getVendorBillNo());

        } else{
            holder.tv_VoucherType.setText(generalReportDetail.getVoucherType());
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

        if (generalReportDetail.getBranch().equalsIgnoreCase("Total :")){
            holder.tv_Date.setVisibility(View.INVISIBLE);
            holder.tv_Date.setText(generalReportDetail.getBranch());
            //holder.tv_Date.setText(holder.tv_Branch.getText());
            //holder.tv_Branch.setVisibility(View.GONE);

            if (generalReportDetail.getAmount().equalsIgnoreCase(generalReportDetail.getAmount())){
                holder.tv_Amount.setVisibility(View.INVISIBLE);
                holder.tv_Amount.setTypeface(holder.tv_Amount.getTypeface(), Typeface.BOLD);
            }
        }

    }

    @Override
    public int getItemCount() {
        return generalReportDetails.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_Branch,tv_Credit,tv_Date,tv_Debit,tv_VendBillNo,tv_LRDate,tv_LRNO,tv_NoOfParcels,tv_OutStanding,
                tv_TransporterName,tv_VendorName,tv_VoucherNumber,tv_VoucherType,tvCustGenLedSrNo,TextHide,
                txt_CustomerName,tv_Amount;
        Button btnPdf,btnExcel;
        ImageView imgMore,btDownload;
        LinearLayout customListPage,basicDetail;
        HorizontalScrollView detailView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_Branch = itemView.findViewById(R.id.tv_Branch);
            this.tv_Amount = itemView.findViewById(R.id.tv_Amounts);
            this.tv_Credit = itemView.findViewById(R.id.tv_Credit);
            this.tv_Date = itemView.findViewById(R.id.tv_Date);
            this.tv_Debit = itemView.findViewById(R.id.tv_Debit);
            this.tv_LRDate = itemView.findViewById(R.id.tv_LRDate);
            this.tv_VendBillNo = itemView.findViewById(R.id.tv_VendBillNo);
            this.tv_LRNO = itemView.findViewById(R.id.tv_LRNO);
            this.tv_NoOfParcels = itemView.findViewById(R.id.tv_NoOfParcels);
            this.tv_OutStanding = itemView.findViewById(R.id.tv_OutStanding);
            this.tv_TransporterName = itemView.findViewById(R.id.tv_TransporterName);
            this.tv_VendorName = itemView.findViewById(R.id.tv_VendorName);
            this.tv_VoucherNumber = itemView.findViewById(R.id.tv_VoucherNumber);
            this.tv_VoucherType = itemView.findViewById(R.id.tv_VoucherType);
            this.imgMore = itemView.findViewById(R.id.img_More);
            this.TextHide = itemView.findViewById(R.id.TextHide);

            //this.txt_CustomerName = itemView.findViewById(R.id.txt_CustomerName);
            this.btDownload = itemView.findViewById(R.id.btn_cust_genLed_sales_download);

            this.basicDetail = itemView.findViewById(R.id.basicll);
            this.detailView = itemView.findViewById(R.id.moreDetails);

        }
    }
}
