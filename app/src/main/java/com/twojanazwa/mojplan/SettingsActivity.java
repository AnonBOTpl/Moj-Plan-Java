package com.twojanazwa.mojplan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private int accentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        prefs = getSharedPreferences("PlanPrefs", Context.MODE_PRIVATE);

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        Spinner accentSpinner = findViewById(R.id.accentColorSpinner);
        String[] colorNames = {"Turkusowy", "Niebieski", "Różowy", "Fioletowy", "Zielony", "Pomarańczowy"};
        accentSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, colorNames));
        accentSpinner.setSelection(prefs.getInt("accentColorIndex", 0));
        accentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                prefs.edit().putInt("accentColorIndex", pos).apply();
                updateUIColors();
            }
            @Override public void onNothingSelected(AdapterView<?> p) {}
        });

        Spinner notifySpinner = findViewById(R.id.notifyTimeSpinner);
        String[] times = {"5 min przed", "10 min przed", "15 min przed", "20 min przed", "30 min przed", "Wyłączone"};
        notifySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, times));
        notifySpinner.setSelection(prefs.getInt("notifyTimeIndex", 0));
        notifySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                prefs.edit().putInt("notifyTimeIndex", pos).apply();
                AlarmHelper.scheduleNextAlarm(SettingsActivity.this);
            }
            @Override public void onNothingSelected(AdapterView<?> p) {}
        });

        Switch dark = findViewById(R.id.darkModeSwitch);
        dark.setChecked(prefs.getBoolean("darkMode", false));
        dark.setOnCheckedChangeListener((b, isChecked) -> {
            prefs.edit().putBoolean("darkMode", isChecked).apply();
            AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        });

        Switch showDaysLeft = findViewById(R.id.showDaysLeftSwitch);
        showDaysLeft.setChecked(prefs.getBoolean("showDaysLeft", true));
        showDaysLeft.setOnCheckedChangeListener((b, isChecked) -> {
            prefs.edit().putBoolean("showDaysLeft", isChecked).apply();
        });

        findViewById(R.id.soundButton).setOnClickListener(v -> {
            Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
            startActivityForResult(intent, 999);
        });

        findViewById(R.id.testNotificationButton).setOnClickListener(v -> {
            // Sprawdzenie uprawnień przed testem
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
                    return;
                }
            }
            
            Intent i = new Intent(this, NotificationReceiver.class);
            i.putExtra("title", "Test Powiadomienia");
            i.putExtra("message", "Gratulacje! Uprawnienia działają! 🚀");
            sendBroadcast(i);
        });

        setupDatePickers();
        updateUIColors();
    }

    private void setupDatePickers() {
        TextView admissionDateTextView = findViewById(R.id.admissionDateTextView);
        TextView dischargeDateTextView = findViewById(R.id.dischargeDateTextView);

        long admissionDateMillis = prefs.getLong("admissionDate", 0);
        long dischargeDateMillis = prefs.getLong("dischargeDate", 0);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        if (admissionDateMillis > 0) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(admissionDateMillis);
            admissionDateTextView.setText(sdf.format(cal.getTime()));
        }

        if (dischargeDateMillis > 0) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(dischargeDateMillis);
            dischargeDateTextView.setText(sdf.format(cal.getTime()));
        }

        admissionDateTextView.setOnClickListener(v -> showDatePicker("admissionDate", admissionDateTextView));
        dischargeDateTextView.setOnClickListener(v -> showDatePicker("dischargeDate", dischargeDateTextView));
    }

    private void showDatePicker(String prefKey, TextView targetTextView) {
        Calendar cal = Calendar.getInstance();
        long savedMillis = prefs.getLong(prefKey, 0);
        if (savedMillis > 0) {
            cal.setTimeInMillis(savedMillis);
        }

        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            Calendar selected = Calendar.getInstance();
            selected.set(year, month, dayOfMonth);
            selected.set(Calendar.HOUR_OF_DAY, 0);
            selected.set(Calendar.MINUTE, 0);
            selected.set(Calendar.SECOND, 0);
            selected.set(Calendar.MILLISECOND, 0);

            long millis = selected.getTimeInMillis();
            prefs.edit().putLong(prefKey, millis).apply();

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            targetTextView.setText(sdf.format(selected.getTime()));
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        dialog.show();
    }

    private void updateUIColors() {
        int index = prefs.getInt("accentColorIndex", 0);
        int[] colorRes = {R.color.acc_turkusowy, R.color.acc_niebieski, R.color.acc_rozowy, 
                          R.color.acc_fioletowy, R.color.acc_zielony, R.color.acc_pomaranczowy};
        accentColor = ContextCompat.getColor(this, colorRes[index]);
        Button soundBtn = findViewById(R.id.soundButton);
        if (soundBtn != null) soundBtn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(accentColor));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (uri != null) prefs.edit().putString("notificationSound", uri.toString()).apply();
        }
    }
}
