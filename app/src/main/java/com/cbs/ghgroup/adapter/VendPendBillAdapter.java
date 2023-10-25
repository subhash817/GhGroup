package com.cbs.ghgroup.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.model.DownlodLedgerRequest;
import com.cbs.ghgroup.model.pendingbill.PendingbillsDetail;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.CustomProgressbar;
import com.cbs.ghgroup.utils.PrefrenceKey;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendPendBillAdapter extends RecyclerView.Adapter<VendPendBillAdapter.myViewHolder> {
    private List<PendingbillsDetail> pendingbillsDetails;
    Context context;
    String upTodate, bpCode, type, branches, branch_code, PendBillsDocEntry;
    DownloadManager manager;

   /* private final int TYPE_HEADER = 0;
    private final int TYPE_ITEMS = 1;*/


    public VendPendBillAdapter(Context context, List<PendingbillsDetail> pendingbillsDetails) {
        this.pendingbillsDetails = pendingbillsDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public VendPendBillAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.pending_bill_items, parent, false);
        return new VendPendBillAdapter.myViewHolder(view);

       /* if (viewType == TYPE_HEADER) {
            View view = layoutInflater.from(parent.getContext()).inflate(R.layout.item_pb_content_header, parent, false);
            return new myViewHolder(view);

        } else if (viewType == TYPE_ITEMS) {
            View view = layoutInflater.from(parent.getContext()).inflate(R.layout.item_pb_content, parent, false);

            return new myViewHolder(view);

        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");*/

    }

    @Override
    public void onBindViewHolder(@NonNull VendPendBillAdapter.myViewHolder holder, int position) {
        PendingbillsDetail pendingbillsDetail = pendingbillsDetails.get(position);

        if (position  % 2 == 0 ) {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.gray_color));
        }

        // holder.tv_penBills_cust_srNo.setText("S.No. "+(position+1));
        holder.tv_Balance.setText(pendingbillsDetail.getBalance());
        holder.tv_BalanceDue.setText(pendingbillsDetail.getBalanceDue());
        holder.tv_BillAmt.setText(pendingbillsDetail.getBillAmt());
        holder.tv_Branch.setText(pendingbillsDetail.getBranch());
        holder.tv_Date.setText(pendingbillsDetail.getDate());
        holder.tv_DocEntry.setText(pendingbillsDetail.getDocEntry());
        holder.tv_DueDate.setText(pendingbillsDetail.getDueDate());
        holder.tv_LRDate.setText(pendingbillsDetail.getLRDate());
        holder.tv_LRNo.setText(pendingbillsDetail.getLRNo());
        holder.tv_TransporterName.setText(pendingbillsDetail.getTransporterName());
        holder.tv_VendorName.setText(pendingbillsDetail.getVendorName());
        holder.tv_VoucherNumber.setText(pendingbillsDetail.getVoucherNumber());
        holder.tv_Vouchertype.setText(pendingbillsDetail.getVoucherType());

        if (pendingbillsDetail.getBillAmt().equalsIgnoreCase("Total :")) {
            holder.tv_Date.setVisibility(View.INVISIBLE);
            holder.tv_Date.setText(holder.tv_BillAmt.getText());
             holder.tv_BillAmt.setVisibility(View.GONE);

            if (pendingbillsDetail.getBalanceDue().equalsIgnoreCase(pendingbillsDetail.getBalanceDue())){
                holder.tv_BalanceDue.setVisibility(View.INVISIBLE);
                holder.tv_BalanceDue.setTypeface(holder.tv_BalanceDue.getTypeface(), Typeface.BOLD);
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

        if (pendingbillsDetail.getVoucherType().equalsIgnoreCase("Sales")) {
            holder.btnDownload.setVisibility(View.VISIBLE);
        } else if (pendingbillsDetail.getVoucherType().equalsIgnoreCase("Purchase")) {
            holder.btnDownload.setVisibility(View.VISIBLE);
        } else {
            holder.btnDownload.setVisibility(View.GONE);
        }

        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String rptInvoice="BusinessPartnerLedger.rpt";
                String PendingbarData = "";
                upTodate = CommonMethods.getPrefsData(context, PrefrenceKey.TO_DATE, "");
                bpCode = CommonMethods.getPrefsData(context, PrefrenceKey.BP_CODE, "");
                type = CommonMethods.getPrefsData(context, PrefrenceKey.TYPE, "");
                branches = CommonMethods.getPrefsData(context, PrefrenceKey.BRANCH, "");
                branch_code = CommonMethods.getPrefsData(context, PrefrenceKey.BRANCH_CODE, "");
                PendingbarData = CommonMethods.getPrefsData(context, PrefrenceKey.BRANCH_CODE, "");
                PendBillsDocEntry = pendingbillsDetail.getDocEntry();
                String[] parts = PendingbarData.split("-");
                String branch = parts[0];
                String type = parts[1];
                DownlodLedgerRequest downlodLedgerRequest = new DownlodLedgerRequest();
                downlodLedgerRequest.setScreenName("LedgerSaleInvoice");
                downlodLedgerRequest.setCode(PendBillsDocEntry);
                downlodLedgerRequest.setProcName("CBS_GST_PUR_Invoice");
                downlodLedgerRequest.setDataBaseName(branch);
                downlodLedgerRequest.setRptFileName("PurchaseInvoice.rpt");
                downlodLedgerRequest.setType("4");
                downlodLedgerRequest.setBranch(type);
                downlodLedgerRequest.setFromDate(upTodate);
                downlodLedgerRequest.setToDate(upTodate);

                //DownlodLedgerRequest downlodLedgerRequest = new DownlodLedgerRequest();
                CustomProgressbar.showProgressBar(context, false);
                RetrofitClient.getClient1().downloadPdf(downlodLedgerRequest).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        CustomProgressbar.hideProgressBar();
                        Log.e("Download PDF", response.toString());
                        if (response.code() == 200 && response.body() != null) {
                            manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse(response.body());
                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            long reference = manager.enqueue(request);
                        } else {
                            Toast.makeText(context, "Unable to download file", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("error", "" + t.getMessage());
                        CustomProgressbar.hideProgressBar();
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return pendingbillsDetails.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Balance, tv_BalanceDue, tv_BillAmt, tv_Branch, tv_Date, tv_DocEntry, tv_DueDate, tv_LRDate, tv_LRNo,
                tv_TransporterName, tv_VendorName, tv_VoucherNumber, tv_Vouchertype, tv_penBills_cust_srNo, TextHide;
        ImageView imgMore,btnDownload;
        LinearLayout basicDetail;
        HorizontalScrollView detailView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_Balance = itemView.findViewById(R.id.tv_Balance);
            this.tv_BalanceDue = itemView.findViewById(R.id.tv_BalanceDue);
            this.tv_BillAmt = itemView.findViewById(R.id.tv_BillAmt);
            this.tv_Branch = itemView.findViewById(R.id.tv_Branch);
            this.tv_Date = itemView.findViewById(R.id.tv_Date);
            this.tv_DocEntry = itemView.findViewById(R.id.tv_DocEntry);
            this.tv_DueDate = itemView.findViewById(R.id.tv_DueDate);
            this.tv_LRDate = itemView.findViewById(R.id.tv_LRDate);
            this.tv_LRNo = itemView.findViewById(R.id.tv_LRNo);
            this.tv_TransporterName = itemView.findViewById(R.id.tv_TransporterName);
            this.tv_VendorName = itemView.findViewById(R.id.tv_VendorName);
            this.tv_VoucherNumber = itemView.findViewById(R.id.tv_VoucherNumber);
            this.tv_Vouchertype = itemView.findViewById(R.id.tv_VoucherType);
            this.imgMore = itemView.findViewById(R.id.img_More);
            this.TextHide = itemView.findViewById(R.id.TextHide);
            this.basicDetail = itemView.findViewById(R.id.pend_bills_basicll);
            this.detailView = itemView.findViewById(R.id.moreDetails);
            //this.tv_penBills_cust_srNo = itemView.findViewById(R.id.txt_penBills_cust_srNo);
            this.btnDownload = itemView.findViewById(R.id.btn_cust_pen_bills_sale_download);
        }
    }
}
