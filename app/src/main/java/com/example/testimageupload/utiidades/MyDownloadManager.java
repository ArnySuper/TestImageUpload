package com.example.testimageupload.utiidades;


import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import java.io.File;

public class MyDownloadManager {

    public interface DownloadCallback {
        void onDownloadComplete();
        void onDownloadFailed(String error);
    }

    public void downloadFile(Context context, String url, String fileName, String description, File destinationPath, DownloadCallback callback) {
        // Aquí iría la implementación de la descarga del archivo
        // Por ejemplo, puedes usar DownloadManager de Android

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(fileName);
        request.setDescription(description);
        request.setDestinationUri(Uri.fromFile(destinationPath));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
            callback.onDownloadComplete(); // Esto es solo un ejemplo; normalmente, querrías llamar esto después de que la descarga realmente se complete
        } else {
            callback.onDownloadFailed("No se pudo obtener el servicio DownloadManager");
        }
    }
}



