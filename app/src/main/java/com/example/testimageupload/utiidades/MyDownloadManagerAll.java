package com.example.testimageupload.utiidades;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;

public class MyDownloadManagerAll {

    private Context context;
    private DownloadCallbackAll callback;
    private DownloadManager downloadManager;
    private long downloadId;


    public interface DownloadCallbackAll {
        void onDownloadComplete();
        void onDownloadFailed(String error);
    }

    public void downloadFileAll(Context context,String url, String fileName, String description, File destinationPath, DownloadCallbackAll callback) {
        this.callback = callback;

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(fileName);
        request.setDescription(description);
        request.setDestinationUri(Uri.fromFile(destinationPath));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadId = downloadManager.enqueue(request);
            context.registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        } else {
            callback.onDownloadFailed("No se pudo obtener el servicio DownloadManager");
        }
    }

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (id == downloadId) {
                //Toast.makeText(context,"downloadComplete",Toast.LENGTH_LONG).show();
                callback.onDownloadComplete();
            }
        }
    };
}

