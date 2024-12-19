package com.cecilia314.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cecilia314.quizapp.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {

    private ActivityResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applyTheme(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityResultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int correctScore = getIntent().getIntExtra("correct", 0);
        int wrongScore = getIntent().getIntExtra("wrong", 0);
        int totalScore = correctScore * 5;

        binding.correctScore.setText(String.valueOf(correctScore));
        binding.wrongScore.setText(String.valueOf(wrongScore));
        binding.resultScore.setText(String.valueOf(totalScore));

        if (correctScore >= 0 && correctScore <= 3) {
            binding.resultImage.setImageResource(R.drawable.round_sad);
            binding.resultInfo.setText("Maybe you should try again!");
        } else if (correctScore >= 4 && correctScore <= 6) {
            binding.resultImage.setImageResource(R.drawable.round_neutral);
            binding.resultInfo.setText("You can do better!");
        } else {
            binding.resultImage.setImageResource(R.drawable.round_happy);
            binding.resultInfo.setText("Congratulations! You're a true quiz master!");
        }

        binding.returnHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}