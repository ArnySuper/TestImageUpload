package com.example.testimageupload.utiidades;
import android.media.MediaPlayer;
import java.io.File;
import java.io.IOException;

public class PrepareMediaPlayer {

    public PrepareMediaPlayer() {
    }
    public static MediaPlayer inicializarMediaPlayer(String filePath) {
        File audioFile = new File(filePath);
        MediaPlayer mediaPlayer = null;

        if (audioFile.exists()) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(filePath);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Puedes manejar el caso en que el archivo no existe de alguna manera, si es necesario
            // Por ejemplo, podrías lanzar una excepción o devolver null.
            System.out.println("Archivo no encontrado: " + filePath);
        }

        return mediaPlayer;
    }

}
