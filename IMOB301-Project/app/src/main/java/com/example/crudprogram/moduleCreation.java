package com.example.crudprogram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class moduleCreation extends AppCompatActivity {

    private EditText moduleNameEditText, durationEditText;
    private Button btnAddModule,btnShowModules,btnBacktomodule;
    private myDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_creation);

        // Initialize UI elements
        moduleNameEditText = findViewById(R.id.editModuleName);
        durationEditText = findViewById(R.id.editDuration);
        btnAddModule = findViewById(R.id.btnAddModule);
        btnShowModules = findViewById(R.id.btnShowModules);
        btnBacktomodule=findViewById(R.id.btnBack2Module);

        // Initialize DB helper
        dbHelper = new myDBHelper(this);

        // Button click listener
        btnAddModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String moduleName = moduleNameEditText.getText().toString().trim();
                String duration = durationEditText.getText().toString().trim();

                if (moduleName.isEmpty()) {
                    moduleNameEditText.setError("Enter module name");
                    return;
                }

                if (duration.isEmpty()) {
                    durationEditText.setError("Enter duration");
                    return;
                }

                boolean success = dbHelper.insertModule(moduleName, duration);
                if (success) {

                    Toast.makeText(moduleCreation.this, "Module added successfully", Toast.LENGTH_SHORT).show();
                    moduleNameEditText.setText("");
                    durationEditText.setText("");

                } else {

                    Toast.makeText(moduleCreation.this, "Failed to add module", Toast.LENGTH_SHORT).show();

                }
            }
        });

        Button btnShowModules = findViewById(R.id.btnShowModules);

        btnShowModules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(moduleCreation.this, ViewModules.class);
                startActivity(intent);
            }
        });

        btnBacktomodule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(moduleCreation.this, admin_dashboard.class);
                startActivity(intent);
            }
        });

    }
}
