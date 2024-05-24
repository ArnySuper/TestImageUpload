package com.example.testimageupload.utiidades;

public class URLToDownload {

    public URLToDownload() {
    }

    public static String getUrlImage(){
        String url = "https://drive.google.com/uc?export=download&id=1JzmkLLmEX-hNqcOUBGNBr_ihGsOE4C44";
        return url;
    }

    public static String getNameArchivoImageExtensionJPG(){
        String encabezado = "partitura";
        String id = "1";
        String titulo = "SOY DISPENSADOR";
        String pista = encabezado+"<datoh>"+id+"<datoh>"+titulo+".hch";
        return pista;
    }
    public static String getNameArchivoAudioExtensionMP3(){
        String encabezado = "Track";
        String id = "1";
        String titulo = "SOY DISPENSADOR";
        String pista = encabezado+"<datoh>"+id+"<datoh>"+titulo+ ".hch";
        return pista;
    }

    public static String getUrlAudio(){
        String url = "https://drive.google.com/uc?export=download&id=1JfZa3Gegh36qwV4l4us6Ozgm_jrJgQiS";
        return url;
    }

    public static String getNameFolderDownloderExternal(){
        String folder = "FileDownloadAppHimnos";
        return folder;
    }
    public static String getNameFolderDownloderInternal(){
        String folder = "File_internal";
        return folder;
    }
}
