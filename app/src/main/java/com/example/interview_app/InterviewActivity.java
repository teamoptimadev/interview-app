package com.example.interview_app;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class InterviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);

        LinearLayout container = findViewById(R.id.questionContainer);

        addMCQ(container, "1. Which HTML tag is used to include JavaScript code?",
                new String[]{"<script>", "<js>", "<javascript>", "<code>"});

        addTrueFalse(container, "2. React is a backend framework. (True/False)");

        addOneWord(container, "3. CSS stands for _______.");

        addMCQ(container, "4. Which method is used to fetch data from APIs in React?",
                new String[]{"useData()", "fetch()", "getAPI()", "loadData()"});

        addTrueFalse(container, "5. JSX allows mixing HTML with JavaScript.");

        addOneWord(container, "6. Name one React hook used for state management.");

        addMCQ(container, "7. Which property is used to change text color in CSS?",
                new String[]{"text-color", "font-color", "color", "foreground"});

        addTrueFalse(container, "8. The <head> tag is visible in the browser window.");

        addOneWord(container, "9. What keyword is used to declare a constant in JavaScript?");

        addMCQ(container, "10. Which of these is NOT a frontend framework?",
                new String[]{"Vue", "Angular", "Spring Boot", "Svelte"});
    }

    private void addMCQ(LinearLayout parent, String question, String[] options) {
        TextView qView = new TextView(this);
        qView.setText(question);
        qView.setTextSize(16);
        qView.setTextColor(getResources().getColor(R.color.foreground));
        qView.setPadding(0, 8, 0, 4);
        parent.addView(qView);

        RadioGroup group = new RadioGroup(this);
        for (String opt : options) {
            RadioButton rb = new RadioButton(this);
            rb.setText(opt);
            group.addView(rb);
        }
        parent.addView(group);
    }

    private void addTrueFalse(LinearLayout parent, String question) {
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
        parent.addView(group);
    }

    private void addOneWord(LinearLayout parent, String question) {
        TextView qView = new TextView(this);
        qView.setText(question);
        qView.setTextSize(16);
        qView.setTextColor(getResources().getColor(R.color.foreground));
        qView.setPadding(0, 8, 0, 4);
        parent.addView(qView);

        android.widget.EditText answer = new android.widget.EditText(this);
        answer.setHint("Your answer...");
        answer.setBackgroundResource(R.drawable.rounded_input_bg);
        answer.setPadding(16, 8, 16, 8);
        parent.addView(answer);
    }
}
