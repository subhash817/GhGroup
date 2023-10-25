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
import com.cbs.ghgroup.model.grcommunication.CommunicationRegisterDetail;

import java.util.List;

public class GeneralReportAdapter extends RecyclerView.Adapter<GeneralReportAdapter.myViewHolder> {
    private List<CommunicationRegisterDetail> communicationRegisterDetails;
    Context context;

    public GeneralReportAdapter(Context context, List<CommunicationRegisterDetail> communicationRegisterDetails) {
        this.communicationRegisterDetails = communicationRegisterDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public GeneralReportAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.general_report_detail, parent, false);
        return new GeneralReportAdapter.myViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull GeneralReportAdapter.myViewHolder holder, int position) {
        CommunicationRegisterDetail communicationRegisterDetail=communicationRegisterDetails.get(position);
        //holder.txt_genRept_cust_srNo.setText("S.No. "+(position+1));
        if (position  % 2 == 0 ) {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.gray_color));
        }
        holder.tv_PostDate.setText(communicationRegisterDetail.getPostingDate());
        holder.tv_DocNo.setText(communicationRegisterDetail.getDocNumber());
        holder.tv_BillNos.setText(communicationRegisterDetail.getBillNO());
        holder.tv_Bill_Date.setText(communicationRegisterDetail.getBillDate());
        holder.tv_Vend_BillNo.setText(communicationRegisterDetail.getVendorBillNo());
        holder.tv_Branch.setText(communicationRegisterDetail.getBranch());
        holder.tv_VendName.setText(communicationRegisterDetail.getVendorName());
        holder.tv_TotalAmts.setText(communicationRegisterDetail.getTotalAmt());
        holder.tv_Narration.setText(communicationRegisterDetail.getNarration());

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
        return communicationRegisterDetails.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_PostDate,tv_DocNo,tv_BillNos,tv_Bill_Date,tv_Vend_BillNo,tv_Branch,tv_VendName,tv_TotalAmts,
                tv_Narration,txt_genRept_cust_srNo,TextHide;
        ImageView imgMore;
        LinearLayout basicDetail;
        HorizontalScrollView detailView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_PostDate = itemView.findViewById(R.id.tv_PostDate);
            this.tv_DocNo = itemView.findViewById(R.id.tv_DocNo);
            this.tv_BillNos = itemView.findViewById(R.id.tv_BillNos);
            this.tv_Branch = itemView.findViewById(R.id.tv_Branch);
            this.tv_Bill_Date = itemView.findViewById(R.id.tv_Bill_Date);
            this.tv_Vend_BillNo = itemView.findViewById(R.id.tv_Vend_BillNo);
            this.tv_VendName = itemView.findViewById(R.id.tv_VendName);
            this.tv_TotalAmts = itemView.findViewById(R.id.tv_TotalAmts);
            this.tv_Narration = itemView.findViewById(R.id.tv_Narration);
            this.imgMore = itemView.findViewById(R.id.img_More);
            this.TextHide = itemView.findViewById(R.id.TextHide);
            this.basicDetail = itemView.findViewById(R.id.gr_basicll);
            this.detailView = itemView.findViewById(R.id.moreDetails);
            //this.txt_genRept_cust_srNo = itemView.findViewById(R.id.txt_genRept_cust_srNo);


        }
    }
}
