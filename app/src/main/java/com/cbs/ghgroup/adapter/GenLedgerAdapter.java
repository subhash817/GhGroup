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
import android.widget.Button;
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
import com.cbs.ghgroup.model.customerledger.LedgerDetail;
import com.cbs.ghgroup.retrofit.RetrofitClient;
import com.cbs.ghgroup.utils.CommonMethods;
import com.cbs.ghgroup.utils.CustomProgressbar;
import com.cbs.ghgroup.utils.PrefrenceKey;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenLedgerAdapter extends RecyclerView.Adapter<GenLedgerAdapter.myViewHolder> {
    private List<LedgerDetail> customerLedgers;
    Context context;
    String voucher;
    String fromdate, type,todate, bpcode, branches,DocEntry;
    DownloadManager manager;

    public GenLedgerAdapter(Context context,List<LedgerDetail> customerLedgers){
        this.customerLedgers = customerLedgers;
        this.context = context;
    }

    @NonNull
    @Override
    public GenLedgerAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.general_ledger_item_details, parent, false);
        return new GenLedgerAdapter.myViewHolder(view);

    }

    @Override
    public void onBindViewHolder( GenLedgerAdapter.myViewHolder holder, int position) {
        LedgerDetail ledgerDetail = customerLedgers.get(position);


        if (position  % 2 == 0 ) {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.gray_color));
        }

        //holder.tvCustGenLedSrNo.setText("S.No. "+(position+1));
        holder.tv_Branch.setText(ledgerDetail.getBranch());
        // holder.tv_Credit.setText(ledgerDetail.getCredit());
        if (ledgerDetail.getFlag().equals("C")){
            holder.tv_Amount.setText(ledgerDetail.getAmount());
            holder.tv_Amount.setTextColor(ContextCompat.getColor(context,R.color.dark_green));
        }
        else if (ledgerDetail.getFlag().equalsIgnoreCase("D")){
            holder.tv_Amount.setText(ledgerDetail.getAmount());
            holder.tv_Amount.setTextColor(ContextCompat.getColor(context,R.color.red));
        } else if (ledgerDetail.getFlag().equalsIgnoreCase("")){
            holder.tv_Amount.setText(ledgerDetail.getAmount());
            holder.tv_Amount.setTextColor(ContextCompat.getColor(context,R.color.darkblack));
        }

        /*if (ledgerDetail.getAmount().equalsIgnoreCase("C")){
            holder.tv_OutStanding.setText(ledgerDetail.getOutStanding());
            holder.tv_OutStanding.setTextColor(ContextCompat.getColor(context,R.color.dark_green));
        }
        else if (ledgerDetail.getFlag().equalsIgnoreCase("D")){
            holder.tv_OutStanding.setText(ledgerDetail.getOutStanding());
            holder.tv_OutStanding.setTextColor(ContextCompat.getColor(context,R.color.red));
        }*/

        holder.tv_Date.setText(ledgerDetail.getDate());
        // holder.tv_Debit.setText(ledgerDetail.getDebit());
        holder.tv_DocEntry.setText(ledgerDetail.getDocEntry());
        holder.tv_LRDate.setText(ledgerDetail.getLRDate());
        holder.tv_LRNO.setText(ledgerDetail.getLRNo());
        holder.tv_NoOfParcels.setText(ledgerDetail.getNoOfParcels());
        holder.tv_OutStanding.setText(ledgerDetail.getOutStanding());
        holder.tv_TransporterName.setText(ledgerDetail.getTransporterName());
        holder.tv_VendorName.setText(ledgerDetail.getVendorName());
        holder.tv_VoucherNumber.setText(ledgerDetail.getVoucherNumber());
        holder.tv_VoucherType.setText(ledgerDetail.getVoucherType());
        holder.tv_Amount.setText(ledgerDetail.getAmount());

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

        if (ledgerDetail.getBranch().equalsIgnoreCase("Total :")){
            holder.tv_Date.setVisibility(View.INVISIBLE);
            holder.tv_Date.setText(holder.tv_Branch.getText());

            if (ledgerDetail.getAmount().equalsIgnoreCase(ledgerDetail.getAmount())){
                holder.tv_Amount.setVisibility(View.INVISIBLE);
                holder.tv_Amount.setTypeface(holder.tv_Amount.getTypeface(), Typeface.BOLD);
            }

            //holder.tv_Branch.setVisibility(View.GONE);
        }

        //holder.tv_VoucherType.setText(customerLedgers.get(position).getVoucherType());

        if (ledgerDetail.getVoucherType().equalsIgnoreCase("Sales")){
            holder.btDownload.setVisibility(View.VISIBLE);
        } else if (ledgerDetail.getVoucherType().equalsIgnoreCase("Purchase")) {
            holder.btDownload.setVisibility(View.VISIBLE);
        } /*else if (ledgerDetail.getVendorName().equalsIgnoreCase("VendorName")){
            holder.txt_CustomerName.setVisibility(View.VISIBLE);
        }*/
        else {
            holder.btDownload.setVisibility(View.GONE);
        }

        holder.btDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String barData="";
                fromdate = CommonMethods.getPrefsData(context, PrefrenceKey.FORM_DATE, "");
                todate = CommonMethods.getPrefsData(context, PrefrenceKey.TO_DATE, "");
                bpcode = CommonMethods.getPrefsData(context, PrefrenceKey.BP_CODE, "");
                branches = CommonMethods.getPrefsData(context, PrefrenceKey.BRANCH, "");
                barData = CommonMethods.getPrefsData(context, PrefrenceKey.BRANCH_CODE, "");
                type=CommonMethods.getPrefsData(context,PrefrenceKey.TYPE,"");
                //DocEntry=CommonMethods.getPrefsData(context,PrefrenceKey.DOC_ENTRY,"");
                DocEntry=ledgerDetail.getDocEntry();
                String string = barData;
                String[] parts = string.split("-");
                String branch = parts[0];
                String type = parts[1];
                DownlodLedgerRequest downlodLedgerRequest = new DownlodLedgerRequest();
                downlodLedgerRequest.setScreenName("LedgerPurchaseInvoice");
                downlodLedgerRequest.setCode(DocEntry);
                downlodLedgerRequest.setProcName("CBS_GST_Invoice_QRCODE");
                //downlodLedgerRequest.setProcName("CBS_GST_PUR_Invoice");
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
        return customerLedgers.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_Branch,tv_Credit,tv_Date,tv_Debit,tv_DocEntry,tv_LRDate,tv_LRNO,tv_NoOfParcels,tv_OutStanding,
                tv_TransporterName,tv_VendorName,tv_VoucherNumber,tv_VoucherType,tvCustGenLedSrNo,TextHide,
                txt_CustomerName,tv_Amount;
        Button btnPdf,btnExcel;
        ImageView imgMore,btDownload;
        LinearLayout customListPage1,basicDetail;
        HorizontalScrollView detailView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_Branch = itemView.findViewById(R.id.tv_Branch);
            this.tv_Amount = itemView.findViewById(R.id.tv_Amount);
            this.tv_Credit = itemView.findViewById(R.id.tv_Credit);
            this.tv_Date = itemView.findViewById(R.id.tv_Date);
            this.tv_Debit = itemView.findViewById(R.id.tv_Debit);
            this.tv_LRDate = itemView.findViewById(R.id.tv_LRDate);
            this.tv_DocEntry = itemView.findViewById(R.id.tv_DocEntry);
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
           // this.customListPage1 = itemView.findViewById(R.id.customPage1);

            /*customListPage1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CustomerActivity.class) ;
                    context.startActivity(intent);
                }
            });*/

        }
    }
}
