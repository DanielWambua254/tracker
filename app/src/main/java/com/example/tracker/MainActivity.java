package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
    DBHelper DB;
    SharedPreferences sharedPreferences;
    public static final String KEY_USERNAME = "name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.loginBtn);
        newUser = findViewById(R.id.newUser);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        DB = new DBHelper(this);

        sharedPreferences = getSharedPreferences(KEY_USERNAME,MODE_PRIVATE);


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
                            // check if user exists
                            Boolean userExists = DB.checkUser(myUsername);
                            if (userExists) {
                                Boolean authenticateUser = DB.authenticateUser(myUsername, myPassword);
                                if (authenticateUser) {

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(KEY_USERNAME,myUsername);
                                    editor.apply();

                                    Intent intent = new Intent(MainActivity.this,home_activity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "Login failed! \n Invalid credentials.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Username does not exist.", Toast.LENGTH_SHORT).show();
                            }
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