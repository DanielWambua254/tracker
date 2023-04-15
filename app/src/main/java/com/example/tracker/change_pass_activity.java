package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class change_pass_activity extends AppCompatActivity {

    Button backBtn, cancelBtn, doneBtn;
    EditText username, myPassword1, myPassword2;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        backBtn = findViewById(R.id.back);
        cancelBtn = findViewById(R.id.cancelBtn);
        doneBtn = findViewById(R.id.doneBtn);
        username = findViewById(R.id.userName);
        myPassword1 = findViewById(R.id.password);
        myPassword2 = findViewById(R.id.confirmPassword);
        DB = new DBHelper(this);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
                myPassword1.setText("");
                myPassword2.setText("");

            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString();
                String password1 = myPassword1.getText().toString();
                String password2 = myPassword2.getText().toString();


                if (userName.equals("") && password1.equals("") && password2.equals("")) {
                    Toast.makeText(change_pass_activity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (userName.length() <= 4) {
                        Toast.makeText(change_pass_activity.this, "Username cannot be less than 4 characters.", Toast.LENGTH_SHORT).show();
                    } else if (userName.length() > 32) {
                        Toast.makeText(change_pass_activity.this, "username too long.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (password1.length() < 8) {
                            Toast.makeText(change_pass_activity.this, "Password cannot be less than 8 characters.", Toast.LENGTH_SHORT).show();
                        } else if (password1.length() >= 32) {
                            Toast.makeText(change_pass_activity.this, "Password too long.", Toast.LENGTH_SHORT).show();
                        } else {
                            if (password1.equals(password2)) {

                                Boolean updateBalance2 = DB.updateBalance(userName,password1);
                                if (updateBalance2) {
                                    Toast.makeText(change_pass_activity.this, "WITHDRAW SUCCESSFUL !", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(change_pass_activity.this, "Password Changed Successfully !", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                            } else {
                                Toast.makeText(change_pass_activity.this, "Entered passwords do not match.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }

        });
    }
}