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



        btnCreateStudent = (Button)findViewById(R.id.btnCreateStudent);
        btnCreateModule = (Button)findViewById(R.id.btnCreateModule);
        btnBacktoLogin= findViewById(R.id.btnBack2Login);

        btnCreateInstructor = (Button)findViewById(R.id.btnCreateInstructor);


        btnCreateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(admin_dashboard.this, add_student.class);

                startActivity(intent);

            }
        });

        btnCreateModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(admin_dashboard.this, moduleCreation.class);

                startActivity(intent);

            }
        });

        btnCreateInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(admin_dashboard.this, AddInstructor.class);

                startActivity(intent);

            }
        });

        btnBacktoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_dashboard.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    }
