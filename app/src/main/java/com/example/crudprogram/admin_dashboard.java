package com.example.crudprogram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class admin_dashboard extends AppCompatActivity {

    Button btnCreateStudent , btnCreateModule , btnCreateInstructor,btnBacktoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);

        // Accessing UI Elements

        btnCreateStudent = (Button)findViewById(R.id.btnCreateStudent);
        btnCreateModule = (Button)findViewById(R.id.btnCreateModule);
        btnCreateInstructor = (Button)findViewById(R.id.btnCreateInstructor);
        btnBacktoLogin= findViewById(R.id.btnBack2Login);

        // Setting event Listeners

        btnCreateStudent.setOnClickListener(v ->handleCreateStudent());
        btnCreateModule.setOnClickListener(v ->handleCreateModule());
        btnCreateInstructor.setOnClickListener(v ->handleCreateInstructor());
        btnBacktoLogin.setOnClickListener(v ->handleBack());

    }

    private void handleCreateStudent(){

        Intent intent = new Intent(admin_dashboard.this, add_student.class);
        startActivity(intent);

    }

    private void handleCreateModule(){

        Intent intent = new Intent(admin_dashboard.this, moduleCreation.class);
        startActivity(intent);

    }

    private void handleCreateInstructor(){

        Intent intent = new Intent(admin_dashboard.this, AddInstructor.class);
        startActivity(intent);


    }

    private void handleBack(){

        Intent intent = new Intent(admin_dashboard.this, AddInstructor.class);
        startActivity(intent);


    }



}
