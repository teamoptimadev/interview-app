package com.example.interview_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

public class InterviewActivity extends AppCompatActivity {

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);

        Button submitBtn=new Button(this);



        LinearLayout container = findViewById(R.id.questionContainer);
        TextView interviewTitle=findViewById(R.id.interviewTitle);
        TextView interviewDesc=findViewById(R.id.interviewDesc);

        addMCQ(container, "1. Which HTML tag is used to include JavaScript code?",
                new String[]{"<script>", "<js>", "<javascript>", "<code>"});

        addTrueFalse(container, "2. React is a backend framework. (True/False)");

        addOneWord(container, "3. CSS stands for _______.");

//        addMCQ(container, "4. Which method is used to fetch data from APIs in React?",
//                new String[]{"useData()", "fetch()", "getAPI()", "loadData()"});
//
//        addTrueFalse(container, "5. JSX allows mixing HTML with JavaScript.");
//
//        addOneWord(container, "6. Name one React hook used for state management.");
//
//        addMCQ(container, "7. Which property is used to change text color in CSS?",
//                new String[]{"text-color", "font-color", "color", "foreground"});
//
//        addTrueFalse(container, "8. The <head> tag is visible in the browser window.");
//
//        addOneWord(container, "9. What keyword is used to declare a constant in JavaScript?");
//
//        addMCQ(container, "10. Which of these is NOT a frontend framework?",
//                new String[]{"Vue", "Angular", "Spring Boot", "Svelte"});



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



        Intent i=getIntent();
        String title=i.getStringExtra("title");
        String desc=i.getStringExtra("desc");

        interviewTitle.setText(title);
        interviewDesc.setText(desc);


        submitBtn.setOnClickListener(view -> {
            Toast.makeText(this, title+"\n interview completed", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(InterviewActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

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

        //margin
        int bottomMarginDp = 20;
        float scale = getResources().getDisplayMetrics().density;
        int bottomMarginPx = (int) (bottomMarginDp * scale + 0.5f);

        // Apply margins using LayoutParams
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, bottomMarginPx);
        group.setLayoutParams(params);
    //margin

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
        //margin
        int bottomMarginDp = 20;
        float scale = getResources().getDisplayMetrics().density;
        int bottomMarginPx = (int) (bottomMarginDp * scale + 0.5f);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, bottomMarginPx);
        group.setLayoutParams(params);
        //margin
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
        //margin
        int bottomMarginDp = 20;
        float scale = getResources().getDisplayMetrics().density;
        int bottomMarginPx = (int) (bottomMarginDp * scale + 0.5f);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, bottomMarginPx);
        answer.setLayoutParams(params);
        //margin
        parent.addView(answer);
    }
}
