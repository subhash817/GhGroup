package com.cbs.ghgroup.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.model.customerledger.CustomerLedger2Result;
import com.cbs.ghgroup.model.customerledger.LedgerDetail;

import java.util.ArrayList;
import java.util.List;

public class CustomerPdfAdapter extends RecyclerView.Adapter<CustomerPdfAdapter.MyViewHolder> implements Filterable {

    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    //private List<CustPdfModel> custPdfList;
    private List<LedgerDetail> filteredCustPdfList;
    private ArrayList<LedgerDetail> customerLedgers;
    CustomerLedger2Result customerLedgerResult;
    private Context context;

    public CustomerPdfAdapter(Context context, List<LedgerDetail> customerLedgers) {
        this.context = context;
        this.customerLedgers = (ArrayList<LedgerDetail>) customerLedgers;
        this.filteredCustPdfList = customerLedgers;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_ROW_COLORFUL;
        }

        return TYPE_ROW;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ROW) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_cust_pdf, viewGroup, false);
            return new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_cust_pdf_colorful,
                    viewGroup, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //LedgerDetail ledgerDetail = filteredCustPdfList.get(position);

        LedgerDetail ledgerDetail = customerLedgers.get(position);
        holder.tvVchrType.setText(ledgerDetail.getVoucherType());
        holder.tvDate.setText(ledgerDetail.getDate());
        holder.tvvchrNumber.setText(ledgerDetail.getVoucherNumber());
        holder.tvBranch.setText(ledgerDetail.getBranch());
        // holder.tvDebit.setText(ledgerDetail.getDebit());
        // holder.tvCredit.setText(ledgerDetail.getCredit());
        holder.tvVendorName.setText(ledgerDetail.getVendorName());
        holder.tvlrNo.setText(ledgerDetail.getLRNo());
        holder.tvTransName.setText(ledgerDetail.getTransporterName());
        holder.tvlrDate.setText(ledgerDetail.getLRDate());
        holder.tvNo_of_parcels.setText(ledgerDetail.getNoOfParcels());
        holder.tvCredit.setTextColor(Color.GREEN);
        holder.tvDebit.setTextColor(Color.RED);

    }

    @Override
    public int getItemCount() {
        return filteredCustPdfList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvVchrType, tvDate, tvvchrNumber, tvBranch, tvDebit,
                tvCredit, tvVendorName, tvlrNo, tvTransName, tvlrDate, tvNo_of_parcels;

        public MyViewHolder(View view) {
            super(view);
            tvVchrType = view.findViewById(R.id.txtVchrType);
            tvDate = view.findViewById(R.id.txtDate);
            tvvchrNumber = view.findViewById(R.id.txtvchrNumber);
            tvBranch = view.findViewById(R.id.txtBranch);
            tvDebit = view.findViewById(R.id.txtDebit);
            tvCredit = view.findViewById(R.id.txtCredit);
            tvVendorName = view.findViewById(R.id.txtVendorName);
            tvlrNo = view.findViewById(R.id.txtlrNo);
            tvTransName = view.findViewById(R.id.txtTransName);
            tvlrDate = view.findViewById(R.id.txtlrDate);
            tvNo_of_parcels = view.findViewById(R.id.txtNo_of_parcels);

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredCustPdfList = customerLedgers;
                } else {
                    List<LedgerDetail> filteredList = new ArrayList<>();
                    for (LedgerDetail customerPdf : customerLedgers) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name
                        if (customerPdf.getVoucherType().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(customerPdf);
                        }
                    }

                    filteredCustPdfList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredCustPdfList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredCustPdfList = (ArrayList<LedgerDetail>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
}
