package com.example.interview_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

import java.util.Arrays;

public class DashboardActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        ImageButton backButton = findViewById(R.id.backButton);
        SimpleBarChart chart = findViewById(R.id.barChart);
        TextView profileImage = findViewById(R.id.profileImage);
        TextView greetText = findViewById(R.id.greetText);
        TextView interviewsAttend = findViewById(R.id.interviewsAttend);
        TextView accuracyTv = findViewById(R.id.accuracy);
        TextView performancetv = findViewById(R.id.performancetv);

        MaterialCardView accuracyCard = findViewById(R.id.accuracyCard);
        MaterialCardView performanceCard = findViewById(R.id.performanceCard);


        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        int userId = (int) prefs.getLong("user_id", -1);
        String fullName = prefs.getString("full_name", "User");


        if (userId == -1) {
            Toast.makeText(this, "Session expired. Please log in again.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }


        DbHelper dbHelper = new DbHelper(this);
        UserStats stats = dbHelper.getUserStats(userId);


        int attended = 0;
        float accuracy = 0f;
        float performanceScore = 0f;
        String performanceLabel = "Not Rated";

        if (stats != null) {
            attended = stats.getInterviewsAttended();
            accuracy = stats.getAccuracy();


            String perf = (stats.getPerformance() == null) ? "" : stats.getPerformance().trim().toLowerCase();

            switch (perf) {
                case "excellent":
                    performanceScore = 90f;
                    performanceLabel = "Excellent";
                    break;
                case "good":
                    performanceScore = 70f;
                    performanceLabel = "Good";
                    break;
                case "moderate":
                    performanceScore = 50f;
                    performanceLabel = "Moderate";
                    break;
                case "bad":
                    performanceScore = 30f;
                    performanceLabel = "Bad";
                    break;
                default:
                    performanceScore = 0f;
                    performanceLabel = "Not Rated";
                    break;
            }
        }


        if (attended == 0) {
            accuracyTv.setText("No interviews yet");
            performancetv.setText("Not Rated");
        } else {
            accuracyTv.setText(String.format("%.2f%%", accuracy));
            performancetv.setText(performanceLabel);
        }

        interviewsAttend.setText(String.valueOf(attended));


        int accuracyColor;
        int accuracyRipple;
        if (accuracy >= 80) {
            accuracyColor = getColor(R.color.green_500);
            accuracyRipple = getColor(R.color.green_500);
        } else if (accuracy >= 50) {
            accuracyColor = getColor(R.color.yellow_500);
            accuracyRipple = getColor(R.color.yellow_500);
        } else {
            accuracyColor = getColor(R.color.red_500);
            accuracyRipple = getColor(R.color.red_500);
        }
        accuracyTv.setTextColor(accuracyColor);
        accuracyCard.setRippleColor(ColorStateList.valueOf(accuracyRipple));


        int performanceColor;
        int performanceRipple;
        switch (performanceLabel.toLowerCase()) {
            case "excellent":
            case "good":
                performanceColor = getColor(R.color.green_500);
                performanceRipple = getColor(R.color.green_500);
                break;
            case "moderate":
                performanceColor = getColor(R.color.yellow_500);
                performanceRipple = getColor(R.color.yellow_500);
                break;
            case "bad":
                performanceColor = getColor(R.color.red_500);
                performanceRipple = getColor(R.color.red_500);
                break;
            default:
                performanceColor = getColor(R.color.muted_foreground);
                performanceRipple = getColor(R.color.muted_foreground);
                break;
        }
        performancetv.setTextColor(performanceColor);
        performanceCard.setRippleColor(ColorStateList.valueOf(performanceRipple));


        fullName = fullName.trim();
        String initials;
        if (fullName.length() == 1) {
            initials = fullName.substring(0, 1).toUpperCase();
        } else if (fullName.contains(" ")) {
            String[] parts = fullName.split("\\s+");
            char first = parts[0].charAt(0);
            char last = parts[parts.length - 1].charAt(0);
            initials = ("" + first + last).toUpperCase();
        } else {
            char first = fullName.charAt(0);
            char last = fullName.charAt(fullName.length() - 1);
            initials = ("" + first + last).toUpperCase();
        }

        profileImage.setText(initials);
        greetText.setText("Hello " + fullName);


        chart.setData(
                Arrays.asList((float) attended, accuracy, performanceScore),
                Arrays.asList("Interviews", "Accuracy", "Performance")
        );


        backButton.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
    }
}
