package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button login;
    TextView newUser;
    EditText userName, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.loginBtn);
        newUser = findViewById(R.id.newUser);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myUsername = userName.getText().toString();
                String myPassword = password.getText().toString();
                if (myPassword.equals("") && myUsername.equals("")) {
                    Toast.makeText(MainActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (myUsername.length() <= 4) {
                        Toast.makeText(MainActivity.this, "Username cannot be less than 4 characters.", Toast.LENGTH_SHORT).show();
                    } else if (myUsername.length() > 32) {
                        Toast.makeText(MainActivity.this, "username too long.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (myPassword.length() < 8) {
                            Toast.makeText(MainActivity.this, "Password cannot be less than 8 characters.", Toast.LENGTH_SHORT).show();
                        } else if (myPassword.length() >= 32) {
                            Toast.makeText(MainActivity.this, "Password too long.", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(MainActivity.this, home_activity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,sign_up_activity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}