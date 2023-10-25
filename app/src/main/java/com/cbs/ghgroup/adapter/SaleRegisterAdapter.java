package com.cbs.ghgroup.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
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
import com.cbs.ghgroup.model.saleregister.SaleDetail;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.CustomProgressbar;
import com.cbs.ghgroup.utils.PrefrenceKey;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleRegisterAdapter extends RecyclerView.Adapter<SaleRegisterAdapter.myViewHolder> {

    private List<SaleDetail> saleDetails;
    Context context;
    String fromdate, type, todate, bpcode, branches, SaleDocEntry;
    DownloadManager manager;

    public SaleRegisterAdapter(Context context, List<SaleDetail> saleDetails) {
        this.saleDetails = saleDetails;
        this.context = context;
    }


    @NonNull
    @Override
    public SaleRegisterAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.purchase_register_items, parent, false);
        return new SaleRegisterAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleRegisterAdapter.myViewHolder holder, int position) {
        SaleDetail saleDetail = saleDetails.get(position);
        if (position  % 2 == 0 ) {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.gray_color));
        }

        holder.tv_BillAmt.setText(saleDetail.getBillAmount());
        holder.tv_Branch.setText(saleDetail.getBranch());
        holder.tv_CustomerName.setText(saleDetail.getCustomerName());
        holder.tv_Date.setText(saleDetail.getDate());
        holder.tv_DocEntry.setText(saleDetail.getDocEntry());
        holder.tv_LRDate.setText(saleDetail.getLrDate());
        holder.tv_LRNo.setText(saleDetail.getLRNo());
        holder.tv_TransporterName.setText(saleDetail.getTransporterName());
        holder.tv_VoucherNumber.setText(saleDetail.getVchrNo());

        if (saleDetail.getBranch().equalsIgnoreCase("Total :")) {
            holder.tv_Branch.setVisibility(View.INVISIBLE);
            holder.btnSaleDownload.setVisibility(View.INVISIBLE);

            if (saleDetails.get(0).getBillAmount().equalsIgnoreCase(saleDetails.get(0).getBillAmount())){
                holder.tv_BillAmt.setVisibility(View.INVISIBLE);
            }

            if (saleDetail.getDocEntry().equalsIgnoreCase("0")) {
                holder.tv_DocEntry.setVisibility(View.INVISIBLE);
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

        holder.btnSaleDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String rptInvoice="BusinessPartnerLedger.rpt";
                String SaleData = "";
                fromdate = CommonMethods.getPrefsData(context, PrefrenceKey.FORM_DATE, "");
                todate = CommonMethods.getPrefsData(context, PrefrenceKey.TO_DATE, "");
                bpcode = CommonMethods.getPrefsData(context, PrefrenceKey.BP_CODE, "");
                branches = CommonMethods.getPrefsData(context, PrefrenceKey.BRANCH, "");
                SaleData = CommonMethods.getPrefsData(context, PrefrenceKey.BRANCH_CODE, "");
                type = CommonMethods.getPrefsData(context, PrefrenceKey.TYPE, "");
                SaleDocEntry = saleDetail.getDocEntry();
                String[] parts = SaleData.split("-");
                String branch = parts[0];
                String type = parts[1];
                DownlodLedgerRequest downlodLedgerRequest = new DownlodLedgerRequest();
                downlodLedgerRequest.setScreenName("LedgerSaleInvoice");
                downlodLedgerRequest.setCode(SaleDocEntry);
                downlodLedgerRequest.setProcName("CBS_GST_PUR_Invoice");
                downlodLedgerRequest.setDataBaseName(branch);
                downlodLedgerRequest.setRptFileName("PurchaseInvoice.rpt");
                downlodLedgerRequest.setType("4");
                downlodLedgerRequest.setBranch(type);
                downlodLedgerRequest.setFromDate(fromdate);
                downlodLedgerRequest.setToDate(todate);

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
        return saleDetails.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tv_BillAmt, tv_Branch, tv_CustomerName, tv_Date, tv_DocEntry,
                tv_LRDate, tv_LRNo, tv_TransporterName, tv_VoucherNumber,TextHide;
        ImageView imgMore,btnSaleDownload;
        LinearLayout basicDetail;
        HorizontalScrollView detailView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_BillAmt = itemView.findViewById(R.id.tv_BillAmt);
            this.tv_Branch = itemView.findViewById(R.id.tv_Branch);
            this.tv_CustomerName = itemView.findViewById(R.id.tv_CustomerName);
            this.tv_Date = itemView.findViewById(R.id.tv_Date);
            this.tv_DocEntry = itemView.findViewById(R.id.tv_DocEntry);
            this.tv_LRDate = itemView.findViewById(R.id.tv_LRDate);
            this.tv_LRNo = itemView.findViewById(R.id.tv_LRNo);
            this.tv_TransporterName = itemView.findViewById(R.id.tv_TransporterName);
            this.tv_VoucherNumber = itemView.findViewById(R.id.tv_VoucherNumber);

            this.imgMore = itemView.findViewById(R.id.img_More);
            this.TextHide = itemView.findViewById(R.id.TextHide);
            this.basicDetail = itemView.findViewById(R.id.sale_basicll);
            this.detailView = itemView.findViewById(R.id.moreDetails);

            this.btnSaleDownload = itemView.findViewById(R.id.btn_cust_purchase_sales_download);
        }
    }
}
