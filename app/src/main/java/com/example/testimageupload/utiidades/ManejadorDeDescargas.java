package com.example.testimageupload.utiidades;

import java.io.File;

public class ManejadorDeDescargas {

    public static File getMyDestinationPathPublicImage(String fileName){
        File downloadFolder = FileManager.getPublicDownloaderFolderIamgen();
        String destinationPath = downloadFolder.getAbsolutePath() + File.separator + fileName;
        return new File(destinationPath);
    }

    public static String getMyDestinationPathPublicAudio(String fileName){
        File downloadFolder = FileManager.getPublicDownloaderFolderMusica();
        String destinationPath = downloadFolder.getAbsolutePath() + File.separator + fileName;
        return destinationPath;
    }
}
