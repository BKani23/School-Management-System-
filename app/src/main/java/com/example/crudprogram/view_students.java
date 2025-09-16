package com.example.crudprogram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class view_students extends AppCompatActivity {

    Button btnBackToAddStudent;

    ListView listView;
    ArrayAdapter<String> adapter;
    myDBHelper dbHelper;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        dbHelper = new myDBHelper(this);

        listView = findViewById(R.id.listViewStudents);

        btnBackToAddStudent=findViewById(R.id.btnBack2AddStudent);


        List<Student> studentList = dbHelper.getAllStudentsDetailed();

        ArrayAdapter<Student> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {


            Student selected = studentList.get(position);

            Intent intent = new Intent(view_students.this, EditStudent.class);
            intent.putExtra("id", selected.id);

            startActivity(intent);


        });


        btnBackToAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_students.this, add_student.class);
                startActivity(intent);
            }
        });




    }


}