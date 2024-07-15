package com.example.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import com.example.ui.ShowInfoActivity;
import androidx.core.app.NotificationCompat;
import android.content.SharedPreferences;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            String notificationBody = remoteMessage.getNotification().getBody();
            String notificationTitle = remoteMessage.getNotification().getTitle();
            HashMap<String, String> data = new HashMap<>(remoteMessage.getData());

            // Save FCM data to SharedPreferences
            saveDataToPreferences(data);

            sendNotification(notificationTitle, notificationBody, data);
        }
    }

    private void saveDataToPreferences(HashMap<String, String> data) {
        SharedPreferences sharedPreferences = getSharedPreferences("FCM_DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (String key : data.keySet()) {
            editor.putString(key, data.get(key));
        }
        editor.apply();
    }

    private void sendNotification(String title, String body, HashMap<String, String> data) {
        Intent intentInfo = new Intent(this, LauncherActivity.class);
        intentInfo.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentInfo.putExtra("data", data);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentInfo, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        String channelId = "fcm_default_channel";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }
}

