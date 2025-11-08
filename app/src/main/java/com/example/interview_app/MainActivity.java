package com.example.interview_app;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView loginTv=findViewById(R.id.loginText);
        Button signupBtn=findViewById(R.id.signupBtn);
        EditText nameEt=findViewById(R.id.fullNametv);
        EditText emailEt=findViewById(R.id.emailtv);
        EditText passwordEt=findViewById(R.id.passwordtv);

        dbHelper = new DbHelper(this);


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameEt.getText().toString().trim();
                String email = emailEt.getText().toString().trim();
                String pass = passwordEt.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "All fields are required.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dbHelper.checkEmailExists(email)) {
                    Toast.makeText(MainActivity.this, "Email already registered", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean inserted = dbHelper.registerUser(name, email, pass);


                if (inserted) {
                    Toast.makeText(MainActivity.this, "Signup successful!", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                }


            }
        });

        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}