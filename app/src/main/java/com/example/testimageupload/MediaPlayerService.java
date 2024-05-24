package com.example.testimageupload;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import java.io.IOException;

public class MediaPlayerService extends Service {

    private static final String CHANNEL_ID = "MediaPlayerServiceChannel";
    public static final String EXTRA_FILE_PATH = "EXTRA_FILE_PATH";
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private String filePath;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            if (intent.hasExtra(EXTRA_FILE_PATH)) {
                filePath = intent.getStringExtra(EXTRA_FILE_PATH);
                initializeMediaPlayer(filePath);
            }

            switch (intent.getAction()) {
                case "ACTION_PLAY":
                    playAudio();
                    break;
                case "ACTION_PAUSE":
                    pauseAudio();
                    break;
                case "ACTION_STOP":
                    stopAudio();
                    break;
            }
        }
        return START_NOT_STICKY;
    }

    private void initializeMediaPlayer(String filePath) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopSelf();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playAudio() {
        if (mediaPlayer != null && !isPlaying) {
            mediaPlayer.start();
            isPlaying = true;
            showNotification();
        }
    }

    private void pauseAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
            showNotification();
        }
    }

    private void stopAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            stopForeground(true);
            stopSelf();
        }
    }

    private void showNotification() {
        Intent playPauseIntent = new Intent(this, MediaPlayerService.class);
        playPauseIntent.setAction(isPlaying ? "ACTION_PAUSE" : "ACTION_PLAY");
        PendingIntent playPausePendingIntent = PendingIntent.getService(this, 0, playPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent stopIntent = new Intent(this, MediaPlayerService.class);
        stopIntent.setAction("ACTION_STOP");
        PendingIntent stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("HIMNOS NUEVOS APP")
                .setContentText("Reproduciendo...")
                .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .addAction(new NotificationCompat.Action(
                        isPlaying ? R.drawable.baseline_pause_circle_filled_48 : R.drawable.baseline_play_circle_filled_48,
                        isPlaying ? "Pause" : "Play",
                        playPausePendingIntent))
                .addAction(new NotificationCompat.Action(
                        R.drawable.baseline_stop_circle_filled_48, "Stop", stopPendingIntent))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true);

        startForeground(1, notificationBuilder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Media Player Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }
}
