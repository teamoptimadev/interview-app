package com.example.interview_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class InterviewActivity extends AppCompatActivity {

    DbHelper dbHelper;
    private final List<Question> questionList = new ArrayList<>();
    private final Map<Integer, Object> userAnswers = new HashMap<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);

        LinearLayout container = findViewById(R.id.questionContainer);
        TextView interviewTitle = findViewById(R.id.interviewTitle);
        TextView interviewDesc = findViewById(R.id.interviewDesc);

        dbHelper = new DbHelper(this);

        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String desc = i.getStringExtra("desc");

        interviewTitle.setText(title);
        interviewDesc.setText(desc);

        int interviewId = dbHelper.getInterviewIdByTitle(title);
        questionList.addAll(dbHelper.getQuestionsByInterviewId(interviewId));

        if (questionList.isEmpty()) {
            TextView noQuestions = new TextView(this);
            noQuestions.setText("No questions available for this interview.");
            noQuestions.setTextSize(16);
            noQuestions.setPadding(16, 16, 16, 16);
            container.addView(noQuestions);
        } else {
            int index = 1;
            for (Question q : questionList) {
                switch (q.getType()) {
                    case "MCQ":
                        RadioGroup mcqGroup = addMCQ(container, index + ". " + q.getText(),
                                new String[]{q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4()});
                        userAnswers.put(index - 1, mcqGroup);
                        break;

                    case "TrueFalse":
                        RadioGroup tfGroup = addTrueFalse(container, index + ". " + q.getText());
                        userAnswers.put(index - 1, tfGroup);
                        break;

                    case "OneWord":
                        EditText et = addOneWord(container, index + ". " + q.getText());
                        userAnswers.put(index - 1, et);
                        break;
                }
                index++;
            }
        }

        Button submitBtn = new Button(this);
        submitBtn.setText("Submit");
        submitBtn.setTextColor(getResources().getColor(R.color.primary_foreground));
        submitBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        submitBtn.setBackground(getDrawable(R.drawable.rounded_button_bg));
        submitBtn.setBackgroundTintList(null);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics())
        );
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics());
        params.setMargins(margin, margin, margin, margin);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        submitBtn.setLayoutParams(params);
        container.addView(submitBtn);

        submitBtn.setOnClickListener(view -> {
            if (questionList.isEmpty()) {
                Toast.makeText(this, "No questions available.", Toast.LENGTH_SHORT).show();
                return;
            }

            int correctCount = checkAllAnswers();
            int total = questionList.size();

            double scorePercent = (correctCount * 100.0) / total;
            String performance;
            if (scorePercent >= 80) performance = "Excellent";
            else if (scorePercent >= 50) performance = "Good";
            else performance = "Needs Improvement";

            SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
            int userId = (int) prefs.getLong("user_id", -1);

            if (userId != -1 && interviewId != -1) {
                dbHelper.updateUserInterviewScore(userId, interviewId, scorePercent, performance);
            } else {
                Toast.makeText(this, "User session or interview data missing.", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(this,
                    "You answered " + correctCount + "/" + total + " correctly.\nPerformance: " + performance,
                    Toast.LENGTH_LONG).show();

            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });
    }

    private int checkAllAnswers() {
        int correct = 0;
        for (int i = 0; i < questionList.size(); i++) {
            Question q = questionList.get(i);
            String userAnswer = "";

            if (q.getType().equals("MCQ") || q.getType().equals("TrueFalse")) {
                RadioGroup group = (RadioGroup) userAnswers.get(i);
                int selectedId = group.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton selected = group.findViewById(selectedId);
                    userAnswer = selected.getText().toString().trim();
                }
            } else if (q.getType().equals("OneWord")) {
                EditText et = (EditText) userAnswers.get(i);
                userAnswer = et.getText().toString().trim();
            }

            if (userAnswer.equalsIgnoreCase(q.getAnswer().trim())) {
                correct++;
            }
        }
        return correct;
    }

    private RadioGroup addMCQ(LinearLayout parent, String question, String[] options) {
        TextView qView = new TextView(this);
        qView.setText(question);
        qView.setTextSize(16);
        qView.setTextColor(getResources().getColor(R.color.foreground));
        qView.setPadding(0, 8, 0, 4);
        parent.addView(qView);

        RadioGroup group = new RadioGroup(this);
        for (String opt : options) {
            if (opt == null) continue;
            RadioButton rb = new RadioButton(this);
            rb.setText(opt);
            group.addView(rb);
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, dpToPx(20));
        group.setLayoutParams(params);

        parent.addView(group);
        return group;
    }

    private RadioGroup addTrueFalse(LinearLayout parent, String question) {
        TextView qView = new TextView(this);
        qView.setText(question);
        qView.setTextSize(16);
        qView.setTextColor(getResources().getColor(R.color.foreground));
        qView.setPadding(0, 8, 0, 4);
        parent.addView(qView);

        RadioGroup group = new RadioGroup(this);
        String[] options = {"True", "False"};
        for (String opt : options) {
            RadioButton rb = new RadioButton(this);
            rb.setText(opt);
            group.addView(rb);
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, dpToPx(20));
        group.setLayoutParams(params);
        parent.addView(group);

        return group;
    }

    private EditText addOneWord(LinearLayout parent, String question) {
        TextView qView = new TextView(this);
        qView.setText(question);
        qView.setTextSize(16);
        qView.setTextColor(getResources().getColor(R.color.foreground));
        qView.setPadding(0, 8, 0, 4);
        parent.addView(qView);

        EditText answer = new EditText(this);
        answer.setHint("Your answer...");
        answer.setBackgroundResource(R.drawable.rounded_input_bg);
        answer.setPadding(16, 8, 16, 8);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, dpToPx(20));
        answer.setLayoutParams(params);

        parent.addView(answer);
        return answer;
    }

    private int dpToPx(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
