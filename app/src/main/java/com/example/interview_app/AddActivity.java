package com.example.interview_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    ListView listView;
    List<CardItem> cardItems;
    CardAdapter adapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        EditText searchBar=findViewById(R.id.searchBar);
        ImageButton backButton=findViewById(R.id.backButton);
        listView = findViewById(R.id.listView);
        cardItems = new ArrayList<>();


        cardItems.add(new CardItem("Frontend", "Modern UI development using React and Tailwind CSS for responsive and accessible web interfaces."));
        cardItems.add(new CardItem("Backend", "Server-side application development using Java Spring Boot with RESTful APIs and database integration."));
        cardItems.add(new CardItem("Full Stack", "Building scalable web applications integrating React frontend with Node.js and Express backend services."));
        cardItems.add(new CardItem("Android Development", "Developing native Android apps with Java and XML layouts, integrating APIs and local databases."));
        cardItems.add(new CardItem("React", "Developing interactive and modular React components with efficient state management using hooks and context."));
        cardItems.add(new CardItem("Next.js", "Creating optimized, SEO-friendly web applications with server-side rendering and API routes using Next.js."));
        cardItems.add(new CardItem("AIML", "Implementing machine learning models for predictive analytics, natural language processing, and computer vision tasks."));
        cardItems.add(new CardItem("Operating System", "Understanding process management, scheduling, memory allocation, and system calls in OS design."));
        cardItems.add(new CardItem("Computer Networks", "Studying network layers, protocols (TCP/IP, HTTP, DNS), and data transmission principles."));
        cardItems.add(new CardItem("Computer Architecture", "Analyzing CPU organization, instruction cycles, pipelining, and memory hierarchy in modern processors."));
        cardItems.add(new CardItem("Data Structures and Algorithms", "Solving computational problems using efficient data structures like trees, heaps, and graphs."));
        cardItems.add(new CardItem("Software Development", "Applying software engineering principles — version control, testing, and agile workflows for production systems."));




        adapter = new CardAdapter(this, cardItems);
        listView.setAdapter(adapter);

        searchBar.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        TextView footerView = new TextView(AddActivity.this);
        footerView.setText("End of List.");
        footerView.setTextSize(14);
        footerView.setGravity(Gravity.CENTER);
        footerView.setTextColor(ContextCompat.getColor(this, R.color.muted_foreground));
        footerView.setPadding(0, 20, 0, 0);

        listView.addFooterView(footerView, null, false);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });


    }
}