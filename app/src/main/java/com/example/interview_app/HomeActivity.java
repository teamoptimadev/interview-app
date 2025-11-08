package com.example.interview_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    DbHelper dbHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageButton logoutButton=findViewById(R.id.logoutButton);
        ImageButton fabAdd=findViewById(R.id.fabAdd);
        TextView profileImage=findViewById(R.id.profileImage);
        listView = findViewById(R.id.listView);

        dbHelper = new DbHelper(this);

        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        int userId = (int) prefs.getLong("user_id", -1);
        String fullName = prefs.getString("full_name", "User");

        if (userId == -1) {
            Toast.makeText(this, "Session expired. Please log in again.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        fullName = fullName.trim();
        String initials = "";
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




        List<CardItem> items = dbHelper.getAllHomeInterviews(userId);

        cardItems = new ArrayList<>();

        for (CardItem item : items) {
            Log.d("Interview", item.getTitle() + " - " + item.getDescription());
            cardItems.add(new CardItem(item.getTitle(), item.getDescription()));
        }

        adapter = new CardAdapter(this, cardItems);
        listView.setAdapter(adapter);


        //empty check

        LinearLayout emptyLayout = findViewById(R.id.emptyLayout);
        Button addInterviewBtn = findViewById(R.id.addInterviewBtn);


        if (cardItems.isEmpty()) {
            listView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        }

        addInterviewBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddActivity.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });

        //end of empty check




        TextView footerView = new TextView(this);
        footerView.setText("End of List. Click on add button to add more.");
        footerView.setTextSize(14);
        footerView.setGravity(Gravity.CENTER);
        footerView.setTextColor(ContextCompat.getColor(this, R.color.muted_foreground));
        footerView.setPadding(0, 20, 0, 0);

        listView.addFooterView(footerView, null, false);

        logoutButton.setOnClickListener(view -> {
            SharedPreferences prefsLogout = getSharedPreferences("UserSession", MODE_PRIVATE);
            prefsLogout.edit().clear().apply();
            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
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

    private void updateEmptyState() {
        LinearLayout emptyLayout = findViewById(R.id.emptyLayout);
        ListView listView = findViewById(R.id.listView);

        if (cardItems == null || cardItems.isEmpty()) {
            listView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        int userId = (int) prefs.getLong("user_id", -1);
        if (userId == -1) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        List<CardItem> updatedItems = dbHelper.getAllHomeInterviews(userId);
        cardItems.clear();
        cardItems.addAll(updatedItems);
        adapter.notifyDataSetChanged();

        updateEmptyState();
    }

}
