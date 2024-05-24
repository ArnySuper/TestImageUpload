package com.example.testimageupload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testimageupload.utiidades.FileManager;
import com.example.testimageupload.utiidades.InterfazVisualFiles;
import com.example.testimageupload.utiidades.ManejadorDeDescargas;
import com.example.testimageupload.utiidades.MyDownloadManager;
import com.example.testimageupload.utiidades.MyDownloadManagerAll;
import com.example.testimageupload.utiidades.PrepareMediaPlayer;
import com.example.testimageupload.utiidades.URLToDownload;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_WRITE = 100;
    private static final int REQUEST_PERMISSION_READ = 200;

    private Button btnDescargarImagen,btnCargarImagen,btnDescargarMusica, btnListFile,btnStop,btnPrepare;
    private ImageButton btnPlayPause;
    private ImageView imgImagen;
    private ProgressBar progressBar;
    private String TAG = "Permission";

    private TextView tvDatos;

    //TODO: REPRODUCTOR
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        requestWritePermission();
    }

    private void setViews() {
        btnDescargarImagen = findViewById(R.id.buttonDescargar);
        btnCargarImagen = findViewById(R.id.buttonCargarImagen);
        btnDescargarMusica = findViewById(R.id.buttonMusica);
        btnPlayPause = findViewById(R.id.imgButtonPlay);
        imgImagen = findViewById(R.id.imageView);
        tvDatos = findViewById(R.id.tvDatos);
        btnListFile = findViewById(R.id.buttonListFile);
        btnStop = findViewById(R.id.buttonStop);
        btnPrepare = findViewById(R.id.buttonPrepare);

        setPublicWork();
        //setInternalWork();
        //TODO: PREPAAMOS EL MEDIA PLAYER PARA REPRODUCIR


    }



    private void setPublicWork(){
        btnDescargarImagen.setOnClickListener(v -> descargarImagenFromInternetToPublicDirectory());

        btnCargarImagen.setOnClickListener(v -> cargarImagenFromDirectoryExternal());

        btnListFile.setOnClickListener(v -> setListaArchivosEnDirecotorioPublic());

        btnDescargarMusica.setOnClickListener(v -> descargarMp3FromInternetToPublicDirectory());

        btnPlayPause.setOnClickListener(v -> getStatusFrommBotton());
        btnStop.setOnClickListener(v -> stopAudio());

        btnPrepare.setOnClickListener(v -> prepapreMediaPlayer());

    }

    private void setListaArchivosEnDirecotorioPublic(){
        File  downloadsDirectory  = FileManager.getPublicDownloaderFolderMusica();
        File[] files = downloadsDirectory.listFiles();

        if (files != null) {
            StringBuilder fileList = new StringBuilder("Archivos en la carpeta de descargas:\n\n");
            for (File file : files) {
                fileList.append(file.getName()).append("\n");
            }
            tvDatos.setText(fileList.toString());
        } else {
            tvDatos.setText("No se encontraron archivos en la carpeta de descargas.");
        }


    }



    //todo: cargar una imagen
    private void cargarImagenFromDirectoryExternal(){
        String filename = URLToDownload.getNameArchivoImageExtensionJPG();
        File downloader = FileManager.getPublicDownloaderFolderIamgen();
        InterfazVisualFiles.cargarImagenFromDirectory(imgImagen,downloader,filename);
    }



    //TODO: DESCARGAR iMAGEN FROM INTENET
    private void descargarImagenFromInternetToPublicDirectory(){
        String url = URLToDownload.getUrlImage();
        String fileName = URLToDownload.getNameArchivoImageExtensionJPG();
        File destinationPath = ManejadorDeDescargas.getMyDestinationPathPublicImage(fileName);
        //getFileFromInternet(fileName,url,destinationPath);
        getFileFromInternetAll(fileName,url,destinationPath);

    }


    //Todo: musica
    private void descargarMp3FromInternetToPublicDirectory(){
        String url = URLToDownload.getUrlAudio();
        String fileName = URLToDownload.getNameArchivoAudioExtensionMP3();
        File destinationPath = new File(ManejadorDeDescargas.getMyDestinationPathPublicAudio(fileName));
        //getFileFromInternet(fileName,url,destinationPath);
        getFileFromInternetAudio(fileName,url,destinationPath);
    }


    //TODO FUNCION PRINCIPAL

    private void getFileFromInternet(String fileName, String url, File destinationPath) {
        MyDownloadManager downloadManager = new MyDownloadManager();
        downloadManager.downloadFile(this, url, fileName, "Descripción del archivo", destinationPath, new MyDownloadManager.DownloadCallback() {
            @Override
            public void onDownloadComplete() {
                runOnUiThread(() -> {

                    Toast.makeText(MainActivity.this, "Descarga completa", Toast.LENGTH_SHORT).show();
                    // Aquí puedes actualizar la ImageView con la imagen descargada
                    // imgImagen.setImageBitmap(BitmapFactory.decodeFile(destinationPath));
                });
            }

            @Override
            public void onDownloadFailed(String error) {
                runOnUiThread(() -> {

                    Toast.makeText(MainActivity.this, "Error en la descarga: " + error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    // TODO: MODIFICAR DATOS DE DESCARGA

    private void getFileFromInternetAll(String fileName, String url, File destinationPath) {
        MyDownloadManagerAll downloadManager = new MyDownloadManagerAll();
        downloadManager.downloadFileAll(this, url, fileName, "Descripción del archivo", destinationPath, new MyDownloadManagerAll.DownloadCallbackAll() {
            @Override
            public void onDownloadComplete() {
                runOnUiThread(() -> {

                    Toast.makeText(MainActivity.this, "Descarga completa", Toast.LENGTH_SHORT).show();
                    // Aquí puedes actualizar la ImageView con la imagen descargada
                    // imgImagen.setImageBitmap(BitmapFactory.decodeFile(destinationPath));
                });
            }

            @Override
            public void onDownloadFailed(String error) {
                runOnUiThread(() -> {

                    Toast.makeText(MainActivity.this, "Error en la descarga: " + error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void getFileFromInternetAudio(String fileName, String url, File destinationPath) {
        MyDownloadManagerAll downloadManager = new MyDownloadManagerAll();
        downloadManager.downloadFileAll(this, url, fileName, "Descripción del archivo", destinationPath, new MyDownloadManagerAll.DownloadCallbackAll() {
            @Override
            public void onDownloadComplete() {
                runOnUiThread(() -> {

                    Toast.makeText(MainActivity.this, "Descarga de himno completa", Toast.LENGTH_LONG).show();
                });
            }

            @Override
            public void onDownloadFailed(String error) {
                runOnUiThread(() -> {

                    Toast.makeText(MainActivity.this, "Error en la descarga: " + error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void requestWritePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE);
            } else {
                requestReadPermission();
            }
        } else {
            onPermissionsGranted();
        }
    }

    private void requestReadPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_READ);
            } else {
                onPermissionsGranted();
            }
        } else {
            onPermissionsGranted();
        }
    }

    private void onPermissionsGranted() {
        Toast.makeText(getApplicationContext(), "Tienes permiso", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_WRITE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestReadPermission();
            } else {
                showPermissionDeniedMessage();
            }
        } else if (requestCode == REQUEST_PERMISSION_READ) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onPermissionsGranted();
            } else {
                showPermissionDeniedMessage();
            }
        }
    }

    private void showPermissionDeniedMessage() {
        Toast.makeText(getApplicationContext(), "Permiso denegado", Toast.LENGTH_LONG).show();
    }

    //TODO:REPRODUCTOR DE MUSICA

    private String filePath;

    private void prepapreMediaPlayer(){
        // Aquí puedes actualizar la ImageView con la imagen descargada
        String fileName = URLToDownload.getNameArchivoAudioExtensionMP3();
        filePath = ManejadorDeDescargas.getMyDestinationPathPublicAudio(fileName);

    }

    private void getStatusFrommBotton(){

        if (isPlaying) {
            pauseAudio();
        } else {
            playAudio();
        }

    }

    private void playAudio() {
        Intent playIntent = new Intent(this, MediaPlayerService.class);
        playIntent.setAction("ACTION_PLAY");
        playIntent.putExtra(MediaPlayerService.EXTRA_FILE_PATH, filePath);
        startService(playIntent);
        tvDatos.setText("Pause");
        isPlaying = true;
    }

    private void pauseAudio() {
        Intent pauseIntent = new Intent(this, MediaPlayerService.class);
        pauseIntent.setAction("ACTION_PAUSE");
        startService(pauseIntent);
        tvDatos.setText("Play");
        isPlaying = false;
    }

    private void stopAudio() {
        Intent stopIntent = new Intent(this, MediaPlayerService.class);
        stopIntent.setAction("ACTION_STOP");
        startService(stopIntent);
        tvDatos.setText("Play");
        isPlaying = false;
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            Toast.makeText(this, "MediaPlayer liberado", Toast.LENGTH_SHORT).show();
        }
    }
}
