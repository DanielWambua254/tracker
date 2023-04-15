package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class sign_up_activity extends AppCompatActivity {

    Button done;
    EditText username, myPassword1, myPassword2;
    DBHelper DB;
    public  static final String EXTRA_TEXT = "com.example.tracker.EXTRA_TEXT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        done = findViewById(R.id.doneBtn);
        username = findViewById(R.id.userName);
        myPassword1 = findViewById(R.id.password);
        myPassword2 = findViewById(R.id.confirmPassword);
        DB = new DBHelper(this);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = username.getText().toString();
                String password1 = myPassword1.getText().toString();
                String password2 = myPassword2.getText().toString();


                if (userName.equals("") && password1.equals("") && password2.equals("")) {
                    Toast.makeText(sign_up_activity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (userName.length() <= 4) {
                        Toast.makeText(sign_up_activity.this, "Username cannot be less than 4 characters.", Toast.LENGTH_SHORT).show();
                    } else if (userName.length() > 32) {
                        Toast.makeText(sign_up_activity.this, "username too long.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (password1.length() < 8) {
                            Toast.makeText(sign_up_activity.this, "Password cannot be less than 8 characters.", Toast.LENGTH_SHORT).show();
                        } else if (password1.length() >= 32) {
                            Toast.makeText(sign_up_activity.this, "Password too long.", Toast.LENGTH_SHORT).show();
                        } else {
                            if (password1.equals(password2)) {
                                Boolean userExists = DB.checkUser(userName);

                                if (!userExists) {
                                    Boolean addUser = DB.newUser(userName, password1);
                                    if (addUser) {
                                        Toast.makeText(sign_up_activity.this, "Account created successfully.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(sign_up_activity.this,welcome_activity.class);
                                        // passing this data to next activity
                                        intent.putExtra(EXTRA_TEXT, userName);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(sign_up_activity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(sign_up_activity.this, "user already exists!", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(sign_up_activity.this, "Entered passwords do not match.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }
}