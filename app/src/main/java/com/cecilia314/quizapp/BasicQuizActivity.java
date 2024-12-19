package com.cecilia314.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cecilia314.quizapp.databinding.ActivityBasicQuizBinding;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BasicQuizActivity extends AppCompatActivity {
    private ActivityBasicQuizBinding binding;
    public static int checked;
    List<QuestionItem> questionList;
    int currentQuestion = 0;
    int correctAnswers = 0, wrongAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applyTheme(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityBasicQuizBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        String level = intent.getStringExtra("level");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        loadAllQuestions(level);
        Collections.shuffle(questionList);
        setQuestionScreen(currentQuestion);
        setupAnswerClickListeners();
    }

    private void setQuestionScreen(int currentQuestion) {
        binding.quizTest.setText(questionList.get(currentQuestion).getQuestion());
        binding.answerOneCard.setText(questionList.get(currentQuestion).getAnswerOne());
        binding.answerTwoCard.setText(questionList.get(currentQuestion).getAnswerTwo());
        binding.answerThreeCard.setText(questionList.get(currentQuestion).getAnswerThree());
        binding.answerFourCard.setText(questionList.get(currentQuestion).getAnswerFour());

    }


    private void loadAllQuestions(String level) {
        try {
            InputStream inputStream = Objects.equals(level, "easy") ? getAssets().open("easyQuestions.json") : getAssets().open("difficultQuestions.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, StandardCharsets.UTF_8);

            if (Objects.equals(level, "easy")) {
                EasyQuizData easyQuizData = new Gson().fromJson(json, EasyQuizData.class);
                questionList = easyQuizData.easyQuestions;
            } else {
                DifficultQuizData difficultQuizData = new Gson().fromJson(json, DifficultQuizData.class);
                questionList = difficultQuizData.difficultQuestions;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupAnswerClickListeners() {
        View.OnClickListener answerClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedAnswer = ((TextView) view).getText().toString();
                boolean isCorrect = questionList.get(currentQuestion).getCorrectAnswer().equals(selectedAnswer);

                if (isCorrect) {
                    correctAnswers++;
                    view.setBackgroundResource(R.color.light_blue);
                    ((TextView) view).setTextColor(getResources().getColor(R.color.white));
                } else {
                    wrongAnswers++;
                    view.setBackgroundResource(R.color.orange);
                    ((TextView) view).setTextColor(getResources().getColor(R.color.white));
                }

                if (currentQuestion < questionList.size() - 1) {
                    new Handler().postDelayed(() -> {
                        currentQuestion++;
                        setQuestionScreen(currentQuestion);
                        resetAnswerViews();
                    }, 500);
                } else {
                    navigateToResults();
                }
            }
        };

        binding.answerOneCard.setOnClickListener(answerClickListener);
        binding.answerTwoCard.setOnClickListener(answerClickListener);
        binding.answerThreeCard.setOnClickListener(answerClickListener);
        binding.answerFourCard.setOnClickListener(answerClickListener);
    }

    private void resetAnswerViews() {
        resetView(binding.answerOneCard);
        resetView(binding.answerTwoCard);
        resetView(binding.answerThreeCard);
        resetView(binding.answerFourCard);
    }

    private void resetView(View view) {
        view.setBackgroundResource(R.color.white);
        ((TextView) view).setTextColor(getResources().getColor(R.color.black));
    }

    private void navigateToResults() {
        Intent intent = new Intent(BasicQuizActivity.this, ResultActivity.class);
        intent.putExtra("correct", correctAnswers);
        intent.putExtra("wrong", wrongAnswers);
        startActivity(intent);
        finish();
    }
}