package com.twojanazwa.mojplan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView clockTextView, dayTextView, currentTitleTextView, currentTimeTextView;
    private TextView nextTitleTextView, nextTimeTextView, nextRoomTextView, nextCountdownTextView;
    private Spinner weekSpinner, groupSpinner;
    private LinearLayout scheduleListContainer;
    private SharedPreferences prefs;
    private Handler handler = new Handler(Looper.getMainLooper());
    private int accentColor;

    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            updateApp();
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefs = getSharedPreferences("PlanPrefs", Context.MODE_PRIVATE);
        boolean isDark = prefs.getBoolean("darkMode", false);
        AppCompatDelegate.setDefaultNightMode(isDark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // PYTANIE O UPRAWNIENIA POWIADOMIEŃ
        checkNotificationPermission();

        clockTextView = findViewById(R.id.clockTextView);
        dayTextView = findViewById(R.id.dayTextView);
        currentTitleTextView = findViewById(R.id.currentTitleTextView);
        currentTimeTextView = findViewById(R.id.currentTimeTextView);
        nextTitleTextView = findViewById(R.id.nextTitleTextView);
        nextTimeTextView = findViewById(R.id.nextTimeTextView);
        nextRoomTextView = findViewById(R.id.nextRoomTextView);
        nextCountdownTextView = findViewById(R.id.nextCountdownTextView);
        weekSpinner = findViewById(R.id.weekSpinner);
        groupSpinner = findViewById(R.id.groupSpinner);
        scheduleListContainer = findViewById(R.id.scheduleListContainer);

        findViewById(R.id.settingsButton).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        });

        setupSpinners();
        loadAccentColor();
        handler.post(updateRunnable);
    }

    private void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAccentColor();
        updateApp();
    }

    private void loadAccentColor() {
        int index = prefs.getInt("accentColorIndex", 0);
        int[] colorRes = {R.color.acc_turkusowy, R.color.acc_niebieski, R.color.acc_rozowy, 
                          R.color.acc_fioletowy, R.color.acc_zielony, R.color.acc_pomaranczowy};
        accentColor = ContextCompat.getColor(this, colorRes[index]);
        if (dayTextView != null) dayTextView.setTextColor(accentColor);
    }

    private void setupSpinners() {
        String[] weeks = {"Tydzień 0", "Tydzień I", "Tydzień II", "Tydzień III", "Tydzień IV", "Tydzień V", "Tydzień VI", "Tydzień VII"};
        weekSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, weeks));
        weekSpinner.setSelection(prefs.getInt("savedWeek", 0));

        String[] groups = {"Grupa A", "Grupa B", "Grupa C"};
        groupSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, groups));
        groupSpinner.setSelection(prefs.getInt("savedGroup", 0));

        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                if (p == weekSpinner) prefs.edit().putInt("savedWeek", pos).apply();
                else prefs.edit().putInt("savedGroup", pos).apply();
                updateApp();
            }
            @Override public void onNothingSelected(AdapterView<?> p) {}
        };
        weekSpinner.setOnItemSelectedListener(listener);
        groupSpinner.setOnItemSelectedListener(listener);
    }

    private void updateApp() {
        if (weekSpinner.getSelectedItem() == null || groupSpinner.getSelectedItem() == null) return;

        Calendar cal = Calendar.getInstance();
        clockTextView.setText(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(cal.getTime()));
        dayTextView.setText(new SimpleDateFormat("EEEE", new Locale("pl", "PL")).format(cal.getTime()));

        int currentMins = cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        String selW = weekSpinner.getSelectedItem().toString();
        String selG = groupSpinner.getSelectedItem().toString();

        List<ScheduleEvent> today = ScheduleDatabase.getScheduleForDay(dayOfWeek);
        List<ScheduleEvent> myEvents = new ArrayList<>();
        for (ScheduleEvent e : today) {
            if (e.weeks.contains(selW) && e.groups.contains(selG)) myEvents.add(e);
        }

        ScheduleEvent cur = null, nxt = null;
        for (int i = 0; i < myEvents.size(); i++) {
            ScheduleEvent e = myEvents.get(i);
            int start = tToM(e.start), end = tToM(e.end);
            if (currentMins >= start && currentMins < end) {
                cur = e;
                if (i + 1 < myEvents.size()) nxt = myEvents.get(i + 1);
                break;
            } else if (currentMins < start) {
                nxt = e;
                break;
            }
        }

        applyCardStyle(currentTitleTextView, cur != null);
        applyCardStyle(nextTitleTextView, nxt != null);

        currentTitleTextView.setText(cur != null ? cur.title : "Brak zajęć");
        currentTimeTextView.setText(cur != null ? (cur.start + " - " + cur.end) : "--:--");

        if (nxt != null) {
            nextTitleTextView.setText(nxt.title);
            nextTimeTextView.setText(nxt.start + " - " + nxt.end);
            String r = nxt.getRoomForGroup(selG);
            nextRoomTextView.setText(r.isEmpty() ? "📍 ---" : "📍 " + r);
            nextCountdownTextView.setText("Za: " + (tToM(nxt.start) - currentMins) + " min");
            nextCountdownTextView.setVisibility(View.VISIBLE);
            nextCountdownTextView.setTextColor(accentColor);
        } else {
            nextTitleTextView.setText("To wszystko na dziś");
            nextCountdownTextView.setVisibility(View.GONE);
        }

        updateScheduleList(myEvents, selG, currentMins);
    }

    private void applyCardStyle(TextView tv, boolean active) {
        if (tv == null || !(tv.getParent() instanceof View)) return;
        View card = (View) tv.getParent();
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(40f);
        gd.setColor(ContextCompat.getColor(this, R.color.card_bg));
        gd.setStroke(active ? 8 : 2, active ? accentColor : ContextCompat.getColor(this, R.color.border_color));
        card.setBackground(gd);
    }

    private void updateScheduleList(List<ScheduleEvent> events, String group, int now) {
        if (scheduleListContainer == null) return;
        scheduleListContainer.removeAllViews();
        for (ScheduleEvent e : events) {
            boolean isCur = (now >= tToM(e.start) && now < tToM(e.end));
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setPadding(30, 40, 30, 40);
            row.setGravity(Gravity.CENTER_VERTICAL);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
            lp.setMargins(0, 0, 0, 20);
            row.setLayoutParams(lp);

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(30f);
            int alphaColor = (accentColor & 0x00FFFFFF) | 0x1A000000;
            gd.setColor(isCur ? alphaColor : ContextCompat.getColor(this, R.color.card_bg));
            gd.setStroke(isCur ? 6 : 2, isCur ? accentColor : ContextCompat.getColor(this, R.color.border_color));
            row.setBackground(gd);

            TextView timeTv = new TextView(this);
            timeTv.setText(e.start);
            timeTv.setMinWidth(160);
            timeTv.setTextColor(isCur ? accentColor : ContextCompat.getColor(this, R.color.text_main));
            timeTv.setTypeface(null, isCur ? Typeface.BOLD : Typeface.NORMAL);

            TextView titleTv = new TextView(this);
            String r = e.getRoomForGroup(group);
            titleTv.setText(e.title + (r.isEmpty() ? "" : "\n📍 " + r));
            titleTv.setTextColor(ContextCompat.getColor(this, R.color.text_main));
            titleTv.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1f));
            titleTv.setTypeface(null, isCur ? Typeface.BOLD : Typeface.NORMAL);

            row.addView(timeTv); row.addView(titleTv);
            scheduleListContainer.addView(row);
        }
    }

    private int tToM(String s) {
        try {
            String[] p = s.split(":");
            return Integer.parseInt(p[0]) * 60 + Integer.parseInt(p[1]);
        } catch (Exception e) { return 0; }
    }
}
