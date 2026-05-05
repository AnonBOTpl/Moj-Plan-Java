package com.twojanazwa.mojplan;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");
        if (title == null) title = "Mój Plan";

        SharedPreferences prefs = context.getSharedPreferences("PlanPrefs", Context.MODE_PRIVATE);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "plan_chan";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Powiadomienia o planie", NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(channel);
        }

        Intent main = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, main, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        String soundUri = prefs.getString("notificationSound", null);
        
        NotificationCompat.Builder b = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pi);

        if (soundUri != null) {
            b.setSound(Uri.parse(soundUri));
        }

        if (prefs.getBoolean("vibrate", true)) {
            b.setVibrate(new long[]{0, 500, 200, 500});
        }

        nm.notify((int) System.currentTimeMillis(), b.build());
        
        // Zaplanuj kolejny alarm tylko jeśli to nie jest test
        if (!"Test Powiadomienia".equals(title)) {
            AlarmHelper.scheduleNextAlarm(context);
        }
    }
}
