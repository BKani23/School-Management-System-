package com.example.crudprogram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.DatePickerDialog;
import java.util.Calendar;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class add_student extends AppCompatActivity {

    private myDBHelper dbHelper;

    private EditText nameEditText, surnameEditText, dobEditText, usernameEditText, passwordEditText;
    private Button btnAddStudent, btnViewStudents, btnStudentBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);

        // Initialize DB Helper
        dbHelper = new myDBHelper(this);

        // Bind UI elements
        nameEditText = findViewById(R.id.txtStudentName);
        surnameEditText = findViewById(R.id.txtStudentSurname);
        dobEditText = findViewById(R.id.txtDOB);
        usernameEditText = findViewById(R.id.txtUsername);
        passwordEditText = findViewById(R.id.txtPassword);



        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current date to set as default in DatePicker
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Create DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(add_student.this,
                        (view, selectedYear, selectedMonth, selectedDay) -> {
                            // Note: Month is zero-based, so add 1
                            String formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                            dobEditText.setText(formattedDate);
                        }, year, month, day);

                datePickerDialog.show();
            }
        });


        btnAddStudent = findViewById(R.id.btnAdd);
        btnViewStudents = findViewById(R.id.btnView);
        btnStudentBack = findViewById(R.id.btnBack);

        // Add Student button logic
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEditText.getText().toString().trim();
                String surname = surnameEditText.getText().toString().trim();
                String dob = dobEditText.getText().toString().trim();
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Input validation
                if (name.isEmpty()) {
                    nameEditText.setError("Please enter student name");
                    nameEditText.requestFocus();
                    return;
                }

                if (surname.isEmpty()) {
                    surnameEditText.setError("Please enter student surname");
                    surnameEditText.requestFocus();
                    return;
                }

                if (dob.isEmpty()) {
                    dobEditText.setError("Please enter date of birth");
                    dobEditText.requestFocus();
                    return;
                }

                if (username.isEmpty()) {
                    usernameEditText.setError("Please enter username");
                    usernameEditText.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    passwordEditText.setError("Please enter password");
                    passwordEditText.requestFocus();
                    return;
                }

                // Insert student
                boolean success = dbHelper.insertStudent(name, surname, dob, username, password);



                if (success) {
                    Toast.makeText(add_student.this, "Student added successfully", Toast.LENGTH_SHORT).show();
                    clearFields();

                } else {

                    Toast.makeText(add_student.this, "Failed to add student", Toast.LENGTH_SHORT).show();

                }
            }

        });




        // View Students button logic
        btnViewStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add_student.this, view_students.class);
                startActivity(intent);
            }
        });

        btnStudentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add_student.this, admin_dashboard.class);
                startActivity(intent);
            }
        });
    }

    private void clearFields() {

        nameEditText.setText("");
        surnameEditText.setText("");
        dobEditText.setText("");
        usernameEditText.setText("");
        passwordEditText.setText("");

    }

}
