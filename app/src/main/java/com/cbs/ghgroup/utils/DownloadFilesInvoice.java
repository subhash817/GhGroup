package com.cbs.ghgroup.utils;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;

public class DownloadFilesInvoice {

    private static final String TAG = "Download Task";
    private Context context;

    private String downloadUrl = "", downloadFileName = "";
    private ProgressDialog progressDialog;

    public DownloadFilesInvoice(Context context, String downloadUrl, String name) {
        this.context = context;

        Log.d(TAG, "DownloadFiles: " + URLUtil.guessFileName(downloadUrl, null, null));

//        this.downloadUrl = downloadUrl;
//
//
        downloadFileName = name;//Create file name by picking download file name from URL
////        Log.e(TAG, downloadFileName);
//
//        //Start Downloading Task
//        new DownloadingTask().execute();

        downloadFileWithNoti(context, downloadUrl);
    }

    private void downloadFileWithNoti(Context context, String url) {
        try {
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
       /* if (TextUtils.isEmpty(downloadFileName)) {
            Utils.showAlertDialog(context, "No file(s) to download.");
            return;
        }*/
            //request.setDestinationInExternalPublicDir("/RENOVO", downloadFileName);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, downloadFileName);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setMimeType("*/*");
          //  request.setMimeType("application/pdf");
            request.allowScanningByMediaScanner();
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            downloadManager.enqueue(request);
        }catch (Exception ex){}
       // Long reference = downloadManager.enqueue(request);
    }

}
