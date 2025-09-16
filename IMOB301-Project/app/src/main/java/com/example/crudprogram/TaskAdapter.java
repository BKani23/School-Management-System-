package com.example.crudprogram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.CheckBox;

import android.widget.Toast;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    private Context context;
    private List<Task> tasks;
    private myDBHelper dbHelper;

    public TaskAdapter(Context context, List<Task> tasks, myDBHelper dbHelper) {
        super(context, 0, tasks);
        this.context = context;
        this.tasks = tasks;
        this.dbHelper = dbHelper;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)

            convertView = LayoutInflater.from(context).inflate(R.layout.activity_task_list_item, parent, false);

        Task task = tasks.get(position);

        TextView taskName = convertView.findViewById(R.id.taskName);
        TextView dueDate = convertView.findViewById(R.id.dueDate);
        CheckBox completedBox = convertView.findViewById(R.id.completedBox);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);
        Button updateButton = convertView.findViewById(R.id.updateButton);

        taskName.setText(task.getTaskName());
        dueDate.setText(task.getDueDate());
        completedBox.setChecked(task.isCompleted());

        // Toggle completion on checkbox change
        completedBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
            dbHelper.updateTaskCompletion(task.getTaskID(), isChecked);
        });

        // Delete button
        deleteButton.setOnClickListener(v -> {
            boolean deleted = dbHelper.deleteTask(task.getTaskID());
            if (deleted) {
                tasks.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Task deleted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to delete task.", Toast.LENGTH_SHORT).show();
            }
        });

        // Update button — you can open a dialog or another UI for updating task details
        updateButton.setOnClickListener(v -> {
            // Example: show update dialog (you'll implement this)
            showUpdateDialog(task);
        });

        return convertView;
    }

    private void showUpdateDialog(Task task) {
        // Your dialog code here to update the task (name, due date, etc.)
        // After update:
        // dbHelper.updateTask(...);
        // notifyDataSetChanged();
    }
}

