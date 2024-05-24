package com.example.testimageupload.utiidades;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import java.io.File;

public class MyDownloadManagerInternal {

    public interface DownloadCallback {
        void onDownloadComplete();
        void onDownloadFailed(String error);
    }

    public void downloadFile(Context context, String url, String fileName, String description, DownloadCallback callback) {
        // Obtener el directorio de almacenamiento interno
        File internalDir = new File(context.getFilesDir(), "files_halel");

        // Crear la carpeta si no existe
        if (!internalDir.exists()) {
            if (!internalDir.mkdirs()) {
                callback.onDownloadFailed("No se pudo crear el directorio interno");
                return;
            }
        }

        // Crear el archivo destino dentro de la carpeta creada
        File destinationFile = new File(internalDir, fileName);

        // Configurar la solicitud de descarga
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(fileName);
        request.setDescription(description);
        request.setDestinationUri(Uri.fromFile(destinationFile));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // Obtener el servicio DownloadManager y encolar la solicitud
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
            callback.onDownloadComplete(); // Normalmente, deberías usar un BroadcastReceiver para esto
        } else {
            callback.onDownloadFailed("No se pudo obtener el servicio DownloadManager");
        }
    }
}

