package com.thegalos.critemp;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {
    private TextView tvCricketResult;
    private DecimalFormat formatter;
    private SeekBar seekBar;
    private RadioGroup rGroup;
    private boolean isDarkMode;
    private boolean show = false;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ConstraintLayout constraintLayout = findViewById(R.id.layout_portrait);
        final ImageView ivDarkMode = findViewById(R.id.ivDarkMode);
        final ImageView ivGalos= findViewById(R.id.ivGalos);
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final TextView tvInst = findViewById(R.id.tvInstruction);
        tvCricketResult = findViewById(R.id.tvCricketResult);
        rGroup = findViewById(R.id.rGroup);
        formatter = new DecimalFormat("#0.0");
        seekBar = findViewById(R.id.seekBar);

        isDarkMode = sp.getBoolean("dark", false);
        if (isDarkMode) {
            constraintLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.grass_night, null));
            ivDarkMode.setImageResource(R.drawable.dark_mode_on);
            ivGalos.setAlpha(1f);
        }

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ivDarkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout layout_portrait = findViewById(R.id.layout_portrait);
                if (isDarkMode) {
                    layout_portrait.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.grass_day, null));
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    ivDarkMode.setImageResource(R.drawable.dark_mode_off);
                    ivGalos.setAlpha(0.5f);

                } else {
                    layout_portrait.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.grass_night, null));
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    ivDarkMode.setImageResource(R.drawable.dark_mode_on);
                    ivGalos.setAlpha(1f);
                }
                isDarkMode = !isDarkMode;
                sp.edit().putBoolean("dark", isDarkMode).apply();
            }
        });

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int j = rGroup.getCheckedRadioButtonId();
                if (j == R.id.rbFahrenheit) {
                    tvInst.setText(getString(R.string.tempInstrF));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        seekBar.setMin(1);
                    }
                    seekBar.setMax(91);
                    seekBar.setVisibility(View.VISIBLE);

                } else if (j == R.id.rbCelsius) {
                    tvInst.setText(getString(R.string.tempInstrC));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        seekBar.setMin(1);
                    }
                    seekBar.setMax(153);
                    seekBar.setVisibility(View.VISIBLE);
                }
                seekBar.setEnabled(true);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress > 1)
                    show = true;
                if (show) {
                    if (rGroup.getCheckedRadioButtonId() == R.id.rbFahrenheit) {
                        double temp = progress + 40;
                        String tempResult = getString(R.string.tempAns1) + " " + progress + " " +
                                getString(R.string.tempAns2) + " " + formatter.format(temp) + " " +
                                getString(R.string.tempF);
                        tvCricketResult.setText(tempResult);
                        tvCricketResult.setVisibility(View.VISIBLE);

                    } else if (rGroup.getCheckedRadioButtonId() == R.id.rbCelsius) {
                        double temp = (progress / 3.0) + 4;
                        String tempResult = getString(R.string.tempAns1) + " " + progress + " " +
                                getString(R.string.tempAns2) + " " + formatter.format(temp) + " " +
                                getString(R.string.tempC);
                        tvCricketResult.setText(tempResult);
                        tvCricketResult.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }
}