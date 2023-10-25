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
import com.cbs.ghgroup.model.ageingpiechart.ObjAgeingDetail;

import java.util.List;

public class AgeingPieChartAdapter extends RecyclerView.Adapter<AgeingPieChartAdapter.myViewHolder> {
    List<ObjAgeingDetail> ageingDetailsList;
    Context context;
    public AgeingPieChartAdapter(Context context,List<ObjAgeingDetail> ageingDetailsList){
        this.ageingDetailsList=ageingDetailsList;
        this.context=context;
    }
    @NonNull
    @Override
    public AgeingPieChartAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.ageing_piechart_items,parent,false);

        return new AgeingPieChartAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgeingPieChartAdapter.myViewHolder holder, int position) {
        ObjAgeingDetail objAgeingDetail=ageingDetailsList.get(position);

        if (position  % 2 == 0 ) {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.gray_color));
        }
        holder.tv_Date.setText(objAgeingDetail.getDate());
        holder.tv_customerCode.setText(objAgeingDetail.getCardCode());
        holder.tv_CustomerName.setText(objAgeingDetail.getCardName());
        holder.tv_BillAmt.setText(objAgeingDetail.getBillAmt());
        holder.tv_vchrType.setText(objAgeingDetail.getVoucherType());
        holder.tv_dueDate.setText(objAgeingDetail.getDueDate());
        holder.tv_vchrNumber.setText(objAgeingDetail.getVoucherNumber());
        holder.tv_billSixtyDays.setText(objAgeingDetail.getUpToSixty());
        holder.tv_fromSixtyToNinty.setText(objAgeingDetail.getFromSixtyToNinty());
        holder.tv_fromNintyToOneTwenty.setText(objAgeingDetail.getFromNintyOneToNinty());
        holder.tv_OneTwentyAbove.setText(objAgeingDetail.getBillAbhoveOneTwenty());

    }

    @Override
    public int getItemCount() {
        return ageingDetailsList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Date,tv_customerCode,tv_CustomerName,tv_BillAmt,
                tv_vchrType,tv_dueDate,tv_vchrNumber,tv_billSixtyDays,
                tv_fromSixtyToNinty,tv_fromNintyToOneTwenty,tv_OneTwentyAbove;
        ImageView imgMore;
        LinearLayout basicDetail;
        HorizontalScrollView detailView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_Date = itemView.findViewById(R.id.tv_Date);
            this.tv_customerCode = itemView.findViewById(R.id.tv_customerCode);
            this.tv_CustomerName = itemView.findViewById(R.id.tv_CustomerName);
            this.tv_BillAmt = itemView.findViewById(R.id.tv_BillAmt);
            this.tv_vchrType = itemView.findViewById(R.id.tv_vchrType);
            this.tv_dueDate = itemView.findViewById(R.id.tv_dueDate);
            this.tv_vchrNumber = itemView.findViewById(R.id.tv_vchrNumber);
            this.tv_billSixtyDays = itemView.findViewById(R.id.tv_billSixtyDays);
            this.tv_fromSixtyToNinty = itemView.findViewById(R.id.tv_fromSixtyToNinty);
            this.tv_fromNintyToOneTwenty = itemView.findViewById(R.id.tv_fromNintyToOneTwenty);
            this.tv_OneTwentyAbove = itemView.findViewById(R.id.tv_OneTwentyAbove);
        }
    }
}
