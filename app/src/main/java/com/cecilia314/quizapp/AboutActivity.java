package com.cecilia314.quizapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cecilia314.quizapp.databinding.ActivityAboutBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AboutActivity extends AppCompatActivity {

    private ActivityAboutBinding binding;
    // public static int checked;
   // public static final String MyPREFERENCES = "QuizPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applyTheme(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        final SharedPreferences.Editor editor = sharedPreferences.edit();
//        int default1;
//        switch (Configuration.UI_MODE_NIGHT_MASK & AppCompatDelegate.getDefaultNightMode()) {
//            case AppCompatDelegate.MODE_NIGHT_NO:
//                default1 = 0;
//                break;
//            case AppCompatDelegate.MODE_NIGHT_YES:
//                default1 = 1;
//                break;
//            default:
//                default1 = 2;
//        }


//        checked = sharedPreferences.getInt("checked", default1);
//        switch (checked) {
//            case 0:
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                break;
//            case 1:
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                break;
//            case 2:
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
//                break;
//            case 3:
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
//                break;
//        }

        final String[] themes = {getString(R.string.light), getString(R.string.dark), getString(R.string.system_default), getString(R.string.set_battery)};
        binding.themeCard.setOnClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(AboutActivity.this);
            builder.setTitle(R.string.dark_mode);
            builder.setSingleChoiceItems(themes, getCurrentTheme(), (dialog, which) -> ThemeManager.saveTheme(AboutActivity.this, which));
            builder.setPositiveButton(android.R.string.ok, (dialog, which) -> ThemeManager.applyTheme(AboutActivity.this));
            builder.show();
        });
    }

    private int getCurrentTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(ThemeManager.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(ThemeManager.KEY_THEME, 2); // Default is System
    }



}