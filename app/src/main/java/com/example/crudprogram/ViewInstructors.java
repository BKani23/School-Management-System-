package com.example.crudprogram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ViewInstructors extends AppCompatActivity {

    ListView listView;
    myDBHelper dbHelper;

    Button btnBackToInstructor;
    List<Instructor> instructorList;
    InstructorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_instructors);

        listView = findViewById(R.id.listViewInstructors);
        dbHelper = new myDBHelper(this);
        btnBackToInstructor=findViewById(R.id.btnBack2Instructor);

        loadInstructors();

        btnBackToInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewInstructors.this, AddInstructor.class);
                startActivity(intent);
            }
        });
    }

    private void loadInstructors() {

        instructorList = dbHelper.getAllInstructorsWithIDs(); // This returns List<Instructor>
        adapter = new InstructorAdapter(this, instructorList);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInstructors(); // Refresh list if returning from update activity
    }


}

