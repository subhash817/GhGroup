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
import com.cbs.ghgroup.model.purchesregister.RegisterDetail;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.CustomProgressbar;
import com.cbs.ghgroup.utils.PrefrenceKey;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchesRegisterAdapter extends RecyclerView.Adapter<PurchesRegisterAdapter.myViewHolder> {
    private List<RegisterDetail> registerDetails;
    Context context;
    String fromdate, type, todate, bpcode, branches, PurchaseDocEntry;
    DownloadManager manager;
    int sum = 0;
    String s = Integer.toString(sum);

    public PurchesRegisterAdapter(Context context, List<RegisterDetail> registerDetails) {
        this.registerDetails = registerDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public PurchesRegisterAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.purchase_register_items, parent, false);
        return new myViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PurchesRegisterAdapter.myViewHolder holder, int position) {
        RegisterDetail registerDetail = registerDetails.get(position);


       /* if (ledgerDetail.getBranch().equalsIgnoreCase("Total :")) {
            holder.tv_Date.setText(holder.tv_Branch.getText());
        }*/

        /*s = s + registerDetail.getBillAmt().equalsIgnoreCase(registerDetail.getBillAmt());
        holder.tv_BillAmt.setText(s);*/

        if (position  % 2 == 0 ) {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.gray_color));
        }
        //holder.tv_purReg_cust_srNo.setText("S.No. "+(position+1));
        holder.tv_BalanceDue.setText(registerDetail.getBalanceDue());
        holder.tv_BillAmt.setText(registerDetail.getBillAmt());
        holder.tv_Branch.setText(registerDetail.getBranch());
        holder.tv_CustomerName.setText(registerDetail.getCustomerName());
        holder.tv_Date.setText(registerDetail.getDate());
        holder.tv_DocEntry.setText(registerDetail.getDocEntry());
        holder.tv_DueDate.setText(registerDetail.getDueDate());
        holder.tv_LRDate.setText(registerDetail.getLRDate());
        holder.tv_LRNo.setText(registerDetail.getLRNo());
        holder.tv_NoOfParcels.setText(registerDetail.getNoOfParcels());
        holder.tv_TaxAmt.setText(registerDetail.getTaxAmt());
        holder.tv_TaxableAmt.setText(registerDetail.getTaxableAmt());
        holder.tv_TransporterName.setText(registerDetail.getTransporterName());
        holder.tv_VoucherNumber.setText(registerDetail.getVoucherNumber());

        //holder.itemView.setVisibility(View.VISIBLE);
       // holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

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

        holder.btnPurchaseDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String purchaseData = "";
                fromdate = CommonMethods.getPrefsData(context, PrefrenceKey.FORM_DATE, "");
                todate = CommonMethods.getPrefsData(context, PrefrenceKey.TO_DATE, "");
                bpcode = CommonMethods.getPrefsData(context, PrefrenceKey.BP_CODE, "");
                branches = CommonMethods.getPrefsData(context, PrefrenceKey.BRANCH, "");
                purchaseData = CommonMethods.getPrefsData(context, PrefrenceKey.BRANCH_CODE, "");
                type = CommonMethods.getPrefsData(context, PrefrenceKey.TYPE, "");
                PurchaseDocEntry = registerDetail.getDocEntry();
                String[] parts = purchaseData.split("-");
                String branch = parts[0];
                String type = parts[1];
                DownlodLedgerRequest downlodLedgerRequest = new DownlodLedgerRequest();
                downlodLedgerRequest.setScreenName("LedgerPurchaseInvoice");
                downlodLedgerRequest.setCode(PurchaseDocEntry);
                downlodLedgerRequest.setProcName("CBS_GST_Invoice_QRCODE");
                downlodLedgerRequest.setDataBaseName(branch);
                downlodLedgerRequest.setRptFileName("SalesInvoice.rpt");
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
        return registerDetails.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tv_BalanceDue, tv_BillAmt, tv_Branch, tv_CustomerName, tv_Date,
                tv_DocEntry, tv_DueDate, tv_LRDate, tv_LRNo, tv_NoOfParcels, tv_TaxAmt,
                tv_TaxableAmt, tv_TransporterName, tv_VoucherNumber, tv_purReg_cust_srNo, TextHide;
        ImageView imgMore,btnPurchaseDownload;
        LinearLayout basicDetail;
        HorizontalScrollView detailView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_BalanceDue = itemView.findViewById(R.id.tv_BalanceDue);
            this.tv_BillAmt = itemView.findViewById(R.id.tv_BillAmt);
            this.tv_Branch = itemView.findViewById(R.id.tv_Branch);
            this.tv_CustomerName = itemView.findViewById(R.id.tv_CustomerName);
            this.tv_Date = itemView.findViewById(R.id.tv_Date);
            this.tv_DocEntry = itemView.findViewById(R.id.tv_DocEntry);
            this.tv_DueDate = itemView.findViewById(R.id.tv_DueDate);
            this.tv_LRDate = itemView.findViewById(R.id.tv_LRDate);
            this.tv_LRNo = itemView.findViewById(R.id.tv_LRNo);
            this.tv_NoOfParcels = itemView.findViewById(R.id.tv_NoOfParcels);
            this.tv_TaxAmt = itemView.findViewById(R.id.tv_TaxAmt);
            this.tv_TaxableAmt = itemView.findViewById(R.id.tv_TaxableAmt);
            this.tv_TransporterName = itemView.findViewById(R.id.tv_TransporterName);
            this.tv_VoucherNumber = itemView.findViewById(R.id.tv_VoucherNumber);
            this.imgMore = itemView.findViewById(R.id.img_More);
            this.TextHide = itemView.findViewById(R.id.TextHide);
            this.basicDetail = itemView.findViewById(R.id.purchase_basicll);
            this.detailView = itemView.findViewById(R.id.moreDetails);
            // this.tv_purReg_cust_srNo = itemView.findViewById(R.id.txt_purReg_cust_srNo);
            this.btnPurchaseDownload = itemView.findViewById(R.id.btn_cust_purchase_sales_download);
        }
    }
}
