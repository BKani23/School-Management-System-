package com.example.crudprogram;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.List;

public class CreateTasks extends AppCompatActivity {

    private EditText editTextTaskName, editTextDueDate;
    private Spinner spinnerModule, spinnerStudent;
    private Button buttonCreateTask,buttonViewTasks, btnBack;

    myDBHelper dbHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tasks);

        dbHelper = new myDBHelper(this);

        spinnerModule = findViewById(R.id.spinnerModule);
        spinnerStudent = findViewById(R.id.spinnerStudent);
        editTextTaskName = findViewById(R.id.editTextTaskName);
        editTextDueDate = findViewById(R.id.editTextDueDate);
        buttonCreateTask = findViewById(R.id.buttonCreateTask);

        buttonViewTasks = findViewById(R.id.buttonViewTasks);
        buttonViewTasks.setOnClickListener(v -> showTasksDialog());
        btnBack=findViewById(R.id.btnBack2AddTask);


        // Setup Module Spinner with hint
        List<String> moduleNames = dbHelper.getAllModulesNames();
        moduleNames.add(0, "Select Module"); // add hint at top
        ArrayAdapter<String> moduleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, moduleNames);
        moduleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerModule.setAdapter(moduleAdapter);

        // Setup Student Spinner with hint
        List<String> studentNames = dbHelper.getAllStudentsNames();
        studentNames.add(0, "Select Student"); // add hint at top
        ArrayAdapter<String> studentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, studentNames);
        studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStudent.setAdapter(studentAdapter);


        editTextDueDate.setOnClickListener(v -> showDatePickerDialog());

        buttonCreateTask.setOnClickListener(v -> addTask());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateTasks.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Date format: YYYY-MM-DD
                    String date = selectedYear + "-" +
                            String.format("%02d", selectedMonth + 1) + "-" +
                            String.format("%02d", selectedDay);
                    editTextDueDate.setText(date);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    private void addTask() {

        String taskName = editTextTaskName.getText().toString().trim();
        String dueDate = editTextDueDate.getText().toString().trim();

        if (taskName.isEmpty() || dueDate.isEmpty()) {
            Toast.makeText(this, "Please enter task name and due date", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get selected moduleID, studentID

        int moduleID = getSelectedModuleID(spinnerModule.getSelectedItemPosition());
        int studentID = getSelectedStudentID(spinnerStudent.getSelectedItemPosition());

        if (moduleID == -1 ) {

            Toast.makeText(this, "Please select a module.", Toast.LENGTH_SHORT).show();

            return;

        }

        else if ( studentID == -1){

            Toast.makeText(this, "Please select a student.", Toast.LENGTH_SHORT).show();

            return;

        }

        boolean inserted = dbHelper.insertTask(taskName, dueDate, moduleID, studentID);

        if (inserted) {

            Toast.makeText(this, "Task created successfully!", Toast.LENGTH_SHORT).show();
            // Optionally clear the fields

            editTextTaskName.setText("");
            editTextDueDate.setText("");
            spinnerModule.setSelection(0);
            spinnerStudent.setSelection(0);

        } else {

            Toast.makeText(this, "Failed to create task.", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper to get moduleID from spinner position
    private int getSelectedModuleID(int position) {

        List<Integer> moduleIDs = dbHelper.getAllModuleIDs();

        if (position > 0 && position <= moduleIDs.size()) {

            return moduleIDs.get(position - 1); // Adjust for hint
        }
        return -1;
    }

    private int getSelectedStudentID(int position) {

        List<Integer> studentIDs = dbHelper.getAllStudentIDs();

        if (position > 0 && position <= studentIDs.size()) {

            return studentIDs.get(position - 1); // Adjust for hint

        }
        return -1;
    }

    private void showTasksDialog() {
        // Get tasks from DB
        List<Task> taskList = dbHelper.getTasks();

        // Convert tasks to strings for display
        String[] taskStrings = new String[taskList.size()];

        for (int i = 0; i < taskList.size(); i++) {

            Task t = taskList.get(i);
            String status = t.isCompleted() ? "[Done]" : "[Pending]";
            taskStrings[i] = t.getTaskName() + " - Due: " + t.getDueDate() + " " + status;

        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tasks");
        builder.setItems(taskStrings, (dialog, which) -> {
            Task selectedTask = taskList.get(which);
            showTaskOptionsDialog(selectedTask);
        });
        builder.setNegativeButton("Close", null);
        builder.show();
    }

    private void showTaskOptionsDialog(Task task) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(task.getTaskName());

//        String[] options = {"Toggle Completion", "Delete Task", "Cancel"};

        String[] options = {"Update Task", "Delete Task", "Cancel"};

        builder.setItems(options, (dialog, which) -> {

            switch (which) {

                case 0:

                    showUpdateTaskDialog(task);

                    break;
                case 1:

                    confirmDeleteTask(task);

                    break;

                case 2:

                    dialog.dismiss();

                    break;
            }
        });
        builder.show();
    }

//    private void toggleTaskCompletion(Task task) {
//
//        boolean newStatus = !task.isCompleted();
//
//        boolean updated = dbHelper.updateTaskCompletion(task.getTaskID(), newStatus); // implement this in dbHelper
//
//        if (updated) {
//            Toast.makeText(this, "Task status updated.", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Failed to update task.", Toast.LENGTH_SHORT).show();
//        }
//        // Refresh the tasks dialog (if you want to keep it open)
//        showTasksDialog();
//    }

    private void showUpdateTaskDialog(Task task) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Task");

        // Use a custom layout for editing
        final EditText inputName = new EditText(this);

        inputName.setText(task.getTaskName());
        inputName.setHint("Task Name");

        final EditText inputDate = new EditText(this);

        inputDate.setText(task.getDueDate());
        inputDate.setHint("Due Date");

        inputDate.setFocusable(false);
        inputDate.setOnClickListener(v -> {

            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String date = selectedYear + "-" +
                                String.format("%02d", selectedMonth + 1) + "-" +
                                String.format("%02d", selectedDay);
                        inputDate.setText(date);
                    },
                    year, month, day
            );

            datePickerDialog.show();
        });

        // Create a layout to hold the inputs
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);
        layout.addView(inputName);
        layout.addView(inputDate);

        builder.setView(layout);

        builder.setPositiveButton("Update", (dialog, which) -> {
            String newName = inputName.getText().toString().trim();
            String newDate = inputDate.getText().toString().trim();

            if (!newName.isEmpty() && !newDate.isEmpty()) {
                task.setCompleted(false); // Or preserve the old state if you want
                Task updatedTask = new Task(task.getTaskID(), newName, newDate, task.isCompleted());

                boolean success = dbHelper.updateTask(updatedTask);
                if (success) {
                    Toast.makeText(this, "Task updated.", Toast.LENGTH_SHORT).show();
                    showTasksDialog();
                } else {
                    Toast.makeText(this, "Failed to update task.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Fields can't be empty.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }


    private void confirmDeleteTask(Task task) {

        new AlertDialog.Builder(this)

                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete \"" + task.getTaskName() + "\"?")
                .setPositiveButton("Yes", (dialog, which) -> {

                    boolean deleted = dbHelper.deleteTask(task.getTaskID()); // implement this in dbHelper
                    if (deleted) {
                        Toast.makeText(this, "Task deleted.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to delete task.", Toast.LENGTH_SHORT).show();
                    }
                    showTasksDialog();
                })
                .setNegativeButton("No", null)
                .show();

    }

}

