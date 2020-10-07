package com.thegalos.critemp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final boolean isDarkMode = sp.getBoolean("dark", false);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        Thread splash_screen = new Thread() {
            public void run() {

                try {
                    sleep(1500);

                } catch(Exception e) {
                    e.printStackTrace();

                } finally {
                    Intent main_menu = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(main_menu);
                }
            }
        };
        splash_screen.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
