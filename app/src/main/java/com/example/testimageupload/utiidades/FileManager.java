package com.example.testimageupload.utiidades;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileManager {

    public static File getPublicDownloaderFolderIamgen() {
        // Obtiene el directorio de descargas público
        File publicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String folder = URLToDownload.getNameFolderDownloderExternal();
        // Crea una carpeta dentro del directorio de descargas, si no existe
        File myFolder = new File(publicDir, folder);
        if (!myFolder.exists()) {
            myFolder.mkdirs();
        }

        return myFolder;
    }

    public static File getPublicDownloaderFolderMusica() {
        // Obtiene el directorio de descargas público
        File publicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        String folder = URLToDownload.getNameFolderDownloderExternal();
        // Crea una carpeta dentro del directorio de descargas, si no existe
        File myFolder = new File(publicDir, folder);
        if (!myFolder.exists()) {
            myFolder.mkdirs();
        }

        return myFolder;
    }



}

