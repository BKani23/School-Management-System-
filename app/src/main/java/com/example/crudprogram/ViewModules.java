package com.example.crudprogram;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ViewModules extends AppCompatActivity {

    ListView listViewModules;
    ArrayAdapter<String> moduleAdapter;
    myDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_modules);

        // Initialize UI
        listViewModules = findViewById(R.id.listViewModules);
        dbHelper = new myDBHelper(this);

        // Get modules from DB
        List<String> moduleList = dbHelper.getAllModules(); // Make sure this method exists in myDBHelper

        // Create and set adapter
        moduleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,moduleList);

        listViewModules.setAdapter(moduleAdapter);
    }

}
