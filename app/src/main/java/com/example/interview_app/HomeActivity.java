package com.example.interview_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ListView listView;
    List<CardItem> cardItems;
    CardAdapter adapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageButton logoutButton=findViewById(R.id.logoutButton);
        ImageButton fabAdd=findViewById(R.id.fabAdd);
        ImageView profileImage=findViewById(R.id.profileImage);

        listView = findViewById(R.id.listView);
        cardItems = new ArrayList<>();


        cardItems.add(new CardItem("Frontend", "A responsive portfolio website built with React and Tailwind CSS."));
        cardItems.add(new CardItem("Backend", "A RESTful API for managing book inventory using Spring Boot."));
        cardItems.add(new CardItem("Full Stack", "A task management web app with authentication and CRUD features."));
        cardItems.add(new CardItem("Android Development", "A fitness tracker app with daily goals and progress charts."));
        cardItems.add(new CardItem("Backend", "A microservice-based e-commerce backend using Node.js and MongoDB."));
        cardItems.add(new CardItem("Frontend", "An interactive data visualization dashboard using Chart.js and D3.js."));
        cardItems.add(new CardItem("Full Stack", "A social media app clone with likes, comments, and image sharing."));
        cardItems.add(new CardItem("Android Development", "A weather app with location-based forecasts and dark mode."));
        cardItems.add(new CardItem("Backend", "A payment gateway integration service using Stripe and Express.js."));
        cardItems.add(new CardItem("Frontend", "A modern landing page with animations built using GSAP and React."));
        cardItems.add(new CardItem("Full Stack", "A blogging platform with Markdown editor and JWT authentication."));
        cardItems.add(new CardItem("Android Development", "A recipe app that fetches meal data from an external API."));
        cardItems.add(new CardItem("Backend", "A chatbot backend powered by Python Flask and OpenAI API."));
        cardItems.add(new CardItem("Frontend", "An AI-powered resume builder using React and local storage."));
        cardItems.add(new CardItem("Full Stack", "A real-time collaborative notes app using WebSockets and Node.js."));


        adapter = new CardAdapter(this, cardItems);
        listView.setAdapter(adapter);

        TextView footerView = new TextView(this);
        footerView.setText("End of List. Click on add button to add more.");
        footerView.setTextSize(14);
        footerView.setGravity(Gravity.CENTER);
        footerView.setTextColor(ContextCompat.getColor(this, R.color.muted_foreground));
        footerView.setPadding(0, 20, 0, 0);

        listView.addFooterView(footerView, null, false);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeActivity.this, InterviewActivity.class);
                startActivity(i);

            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeActivity.this,AddActivity.class);
                startActivity(i);
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeActivity.this, DashboardActivity.class);
                startActivity(i);
            }
        });

    }
}
