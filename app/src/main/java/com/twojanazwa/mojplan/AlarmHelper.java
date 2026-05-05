package com.twojanazwa.mojplan;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import java.util.Calendar;
import java.util.List;

public class AlarmHelper {

    public static void scheduleNextAlarm(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("PlanPrefs", Context.MODE_PRIVATE);
        int notifyIdx = prefs.getInt("notifyTimeIndex", 5);
        if (notifyIdx == 5) { cancel(context); return; }

        int[] mins = {5, 10, 15, 20, 30};
        int offset = mins[notifyIdx];

        Calendar now = Calendar.getInstance();
        int day = now.get(Calendar.DAY_OF_WEEK) - 1;
        
        String[] romans = {"0", "I", "II", "III", "IV", "V", "VI", "VII"};
        String w = "Tydzień " + romans[prefs.getInt("savedWeek", 0)];
        String g = "Grupa " + (char)('A' + prefs.getInt("savedGroup", 0));

        List<ScheduleEvent> events = ScheduleDatabase.getScheduleForDay(day);
        for (ScheduleEvent e : events) {
            if (e.weeks.contains(w) && e.groups.contains(g)) {
                Calendar alarmTime = Calendar.getInstance();
                String[] p = e.start.split(":");
                alarmTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(p[0]));
                alarmTime.set(Calendar.MINUTE, Integer.parseInt(p[1]));
                alarmTime.set(Calendar.SECOND, 0);
                alarmTime.add(Calendar.MINUTE, -offset);

                if (alarmTime.after(now)) {
                    set(context, alarmTime.getTimeInMillis(), e.title, "Zaczyna się za " + offset + " min!");
                    return;
                }
            }
        }
    }

    private static void set(Context context, long time, String title, String msg) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, NotificationReceiver.class);
        i.putExtra("title", title);
        i.putExtra("message", msg);
        PendingIntent pi = PendingIntent.getBroadcast(context, 1, i, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pi);
            } else {
                am.setExact(AlarmManager.RTC_WAKEUP, time, pi);
            }
        } catch (Exception e) {
            am.set(AlarmManager.RTC_WAKEUP, time, pi);
        }
    }

    public static void cancel(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, NotificationReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 1, i, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        if (am != null) am.cancel(pi);
    }
}
