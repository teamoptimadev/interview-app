package com.example.interview_app;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    ListView listView;
    List<CardItem> cardItems;
    CardAdapter adapter;

    DbHelper dbHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        EditText searchBar=findViewById(R.id.searchBar);
        ImageButton backButton=findViewById(R.id.backButton);
        listView = findViewById(R.id.listView);
        cardItems = new ArrayList<>();

        dbHelper = new DbHelper(this);

        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        int userId = (int) prefs.getLong("user_id", -1);

        if (userId == -1) {
            Log.e("AddActivity", "No logged-in user found");
            finish();
            return;
        }

        List<CardItem> items = dbHelper.getAllInterviews();

        for (CardItem item : items) {
            Log.d("Interview", item.getTitle() + " - " + item.getDescription());
            cardItems.add(new CardItem(item.getTitle(), item.getDescription()));
        }


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