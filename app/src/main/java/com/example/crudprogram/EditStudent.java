package com.example.crudprogram;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditStudent extends AppCompatActivity {
    private EditText nameEdit, surnameEdit, dobEdit, usernameEdit, passwordEdit;
    private Button btnUpdate, btnDelete;
    private myDBHelper dbHelper;
    private int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        dbHelper = new myDBHelper(this);

        nameEdit = findViewById(R.id.editStudentName);
        surnameEdit = findViewById(R.id.editStudentSurname);
        dobEdit = findViewById(R.id.editDOB);
        usernameEdit = findViewById(R.id.editUsername);
        passwordEdit = findViewById(R.id.editPassword);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        studentId = getIntent().getIntExtra("id", -1);
        // You should fetch full student info here based on ID

        btnUpdate.setOnClickListener(v -> {
            dbHelper.updateStudent(

                    studentId,
                    nameEdit.getText().toString(),
                    surnameEdit.getText().toString(),
                    dobEdit.getText().toString(),
                    usernameEdit.getText().toString(),
                    passwordEdit.getText().toString()

            );
            Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(EditStudent.this, add_student.class);
            startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> {
            dbHelper.deleteStudent(studentId);
            Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(EditStudent.this, add_student.class);
            startActivity(intent);

        });
    }
}

