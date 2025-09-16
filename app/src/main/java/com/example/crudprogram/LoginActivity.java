package com.example.crudprogram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    Button buttonLogin;
    myDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new myDBHelper(this);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if(username.isEmpty() || password.isEmpty()) {

                    Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();

                    return;

                }

                if (username.equals("admin") && password.equals("admin123")) {

                    // Admin login
                    Intent intent = new Intent(LoginActivity.this, admin_dashboard.class);
                    startActivity(intent);

                    finish();

                } else if (dbHelper.checkInstructorCredentials(username, password)) {

                    // Instructor login
                    Intent intent = new Intent(LoginActivity.this, CreateTasks.class);
                    intent.putExtra("username", username);
                    startActivity(intent);


                    finish();

                } else if (dbHelper.checkStudentCredentials(username, password)) {

                    // Student login
                    Intent intent = new Intent(LoginActivity.this, StudentTasks.class);
                    intent.putExtra("username", username);
                    startActivity(intent);


                    finish();

                } else {

                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}
