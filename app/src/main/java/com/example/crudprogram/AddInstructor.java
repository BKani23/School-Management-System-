package com.example.crudprogram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddInstructor extends AppCompatActivity {

    myDBHelper dbHelper;
    EditText instructorNameEditText,usernameEditText, passwordEditText;
    Button btnAdd, btnView,btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_instructor);

        dbHelper = new myDBHelper(this);

        instructorNameEditText = findViewById(R.id.editInstructorName);
        usernameEditText = findViewById(R.id.editInstructorUsername);
        passwordEditText = findViewById(R.id.editInstructorPassword);
        btnAdd = findViewById(R.id.btnAddInstructor);
        btnView = findViewById(R.id.btnViewInstructors);
        btnBack=findViewById(R.id.btnBack2Instructor);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String instructorName = instructorNameEditText.getText().toString().trim();
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (instructorName.isEmpty()) {

                    instructorNameEditText.setError("Enter instructor name");
                    return;
                }

                if (username.isEmpty()) {
                    usernameEditText.setError("Enter username");
                    return;
                }

                if (password.isEmpty()) {
                    passwordEditText.setError("Enter password");
                    return;
                }

                boolean success = dbHelper.insertInstructor(instructorName,username, password);


                if (success) {

                    Toast.makeText(AddInstructor.this, "Instructor added", Toast.LENGTH_SHORT).show();

                    instructorNameEditText.setText("");
                    usernameEditText.setText("");
                    passwordEditText.setText("");

                } else {

                    Toast.makeText(AddInstructor.this, "Failed to add instructor", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnView.setOnClickListener(v -> {

            Intent intent = new Intent(AddInstructor.this, ViewInstructors.class);
            startActivity(intent);


        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddInstructor.this, admin_dashboard.class);
                startActivity(intent);
            }
        });
    }
}
