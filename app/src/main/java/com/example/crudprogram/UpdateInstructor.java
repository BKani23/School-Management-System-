package com.example.crudprogram;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateInstructor extends AppCompatActivity {

    EditText editInstructorName, editUsername, editPassword;
    Button btnUpdate;
    myDBHelper dbHelper;
    int instructorID;
    Button btnBackToinst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_instructor);

        editInstructorName = findViewById(R.id.editInstructorNameUpdate);
        editUsername = findViewById(R.id.editInstructorUsernameUpdate);
        editPassword = findViewById(R.id.editInstructorPasswordUpdate);
        btnUpdate = findViewById(R.id.btnUpdateInstructor);
        btnBackToinst=findViewById(R.id.btnBack2Inst);

        dbHelper = new myDBHelper(this);

        // Get instructor data from intent
        instructorID = getIntent().getIntExtra("id", -1);
        String name = getIntent().getStringExtra("name");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");

        // Set existing data
        editInstructorName.setText(name);
        editUsername.setText(username);
        editPassword.setText(password);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String updatedName = editInstructorName.getText().toString().trim();
                String updatedUsername = editUsername.getText().toString().trim();
                String updatedPassword = editPassword.getText().toString().trim();

                if (updatedName.isEmpty() || updatedUsername.isEmpty() || updatedPassword.isEmpty()) {
                    Toast.makeText(UpdateInstructor.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean updated = dbHelper.updateInstructor(instructorID, updatedName, updatedUsername, updatedPassword);

                if (updated) {

                    Toast.makeText(UpdateInstructor.this, "Instructor updated successfully", Toast.LENGTH_SHORT).show();
                    finish();

                } else {

                    Toast.makeText(UpdateInstructor.this, "Update failed", Toast.LENGTH_SHORT).show();

                }
            }

        });

        btnBackToinst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateInstructor.this, ViewInstructors.class);
                startActivity(intent);
            }
        });
    }
}
