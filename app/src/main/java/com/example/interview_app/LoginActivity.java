package com.example.interview_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        if (prefs.contains("user_id")) {
            // User already logged in — skip login screen
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            finish();
            return;
        }



        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        TextView signupTv=findViewById(R.id.signupText);
        Button loginBtn=findViewById(R.id.loginBtn);
        EditText emailEt=findViewById(R.id.loginemailtv);
        EditText passwordEt=findViewById(R.id.loginpasswordtv);
        dbHelper = new DbHelper(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEt.getText().toString().trim();
                String pass = passwordEt.getText().toString().trim();

                if (email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dbHelper.checkUser(email, pass)) {
                    SQLiteDatabase db = dbHelper.getReadableDatabase();
                    Cursor cursor = db.rawQuery("SELECT id, full_name FROM users WHERE email=? AND password=?",
                            new String[]{email, pass});

                    int userId = -1;
                    String fullName = null;

                    if (cursor.moveToFirst()) {
                        userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                        fullName = cursor.getString(cursor.getColumnIndexOrThrow("full_name"));
                    }
                    cursor.close();

                    if (userId != -1) {
                        // Save login session
                        android.content.SharedPreferences prefs =
                                getSharedPreferences("UserSession", MODE_PRIVATE);
                        android.content.SharedPreferences.Editor editor = prefs.edit();
                        editor.putLong("user_id", userId);
                        editor.putString("full_name", fullName);
                        editor.apply();

                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "User not found. Try again.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }




            }
        });


        signupTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}