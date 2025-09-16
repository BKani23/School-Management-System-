package com.example.crudprogram;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class StudentTasks extends AppCompatActivity {

    private LinearLayout taskContainer;
    private myDBHelper dbHelper;
    private int studentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_tasks);

        dbHelper = new myDBHelper(this);
        taskContainer = findViewById(R.id.taskContainer);

        // Get username passed from login
        String username = getIntent().getStringExtra("username");

        if (username == null || username.isEmpty()) {
            Toast.makeText(this, "Username not passed from login!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Get student ID from DB using username
        studentID = dbHelper.getStudentIDByUsername(username);
        if (studentID == -1) {
            Toast.makeText(this, "Student not found in database!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        loadTasks();
    }

    private void loadTasks() {
        taskContainer.removeAllViews();

        List<Task> tasks = dbHelper.getTasksByStudentID(studentID);
        if (tasks == null || tasks.isEmpty()) {
            Toast.makeText(this, "No tasks found for this student", Toast.LENGTH_SHORT).show();
            return;
        }

        for (Task task : tasks) {
            addTaskItem(task);
        }
    }

    private void addTaskItem(Task task) {

        LinearLayout taskLayout = new LinearLayout(this);
        taskLayout.setOrientation(LinearLayout.VERTICAL);
        taskLayout.setPadding(30, 30, 30, 30);
        taskLayout.setBackgroundColor(0xFFF9F9F9);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, 20);
        taskLayout.setLayoutParams(layoutParams);

        TextView taskText = new TextView(this);
        String status = task.isCompleted() ? "[Completed]" : "[Pending]";
        taskText.setText(task.getTaskName() + " - Due: " + task.getDueDate() + " " + status);
        taskText.setTextSize(16);

        taskLayout.addView(taskText);

        taskLayout.setOnClickListener(v -> showTaskOptionsDialog(task));

        taskContainer.addView(taskLayout);
    }

    private void showTaskOptionsDialog(Task task) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(task.getTaskName());

        String[] options = {"Toggle Completion", "Delete Task", "Cancel"};

        builder.setItems(options, (dialog, which) -> {
            switch (which) {

                case 0:
                    toggleTaskCompletion(task);
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

    private void toggleTaskCompletion(Task task) {

        boolean newStatus = !task.isCompleted();

        boolean updated = dbHelper.updateTaskCompletion(task.getTaskID(), newStatus);

        if (updated) {
            Toast.makeText(this, "Task status updated.", Toast.LENGTH_SHORT).show();
            loadTasks();  // Refresh the UI
        } else {
            Toast.makeText(this, "Failed to update task.", Toast.LENGTH_SHORT).show();
        }

    }
    private void confirmDeleteTask(Task task) {

        new AlertDialog.Builder(this)

                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Yes", (dialog, which) -> {

                    boolean deleted = dbHelper.deleteTask(task.getTaskID());

                    if (deleted) {
                        Toast.makeText(this, "Task deleted.", Toast.LENGTH_SHORT).show();
                        loadTasks(); // Refresh the list
                    } else {
                        Toast.makeText(this, "Failed to delete task.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }



}
