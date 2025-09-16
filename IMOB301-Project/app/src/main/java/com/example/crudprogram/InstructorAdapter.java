package com.example.crudprogram;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crudprogram.Instructor;
import com.example.crudprogram.myDBHelper;

import java.util.List;

public class InstructorAdapter extends ArrayAdapter<Instructor> {
    private Context context;
    private List<Instructor> instructors;
    private myDBHelper dbHelper;

    public InstructorAdapter(Context context, List<Instructor> instructors) {
        super(context, 0, instructors);
        this.context = context;
        this.instructors = instructors;
        this.dbHelper = new myDBHelper(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_instructor_item, parent, false);
        }

        Instructor instructor = instructors.get(position);

        TextView nameTextView = convertView.findViewById(R.id.instructorNameTextView);
        nameTextView.setText(instructor.getName());

        Button updateButton = convertView.findViewById(R.id.updateButton);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        updateButton.setOnClickListener(v -> {
            // Open UpdateInstructorActivity with instructor details
            Intent intent = new Intent(context, UpdateInstructor.class);
            intent.putExtra("id", instructor.getId());
            intent.putExtra("name", instructor.getName());
            intent.putExtra("username", instructor.getUsername());
            intent.putExtra("password", instructor.getPassword());
            context.startActivity(intent);
        });

        deleteButton.setOnClickListener(v -> {
            // Confirm deletion
            new AlertDialog.Builder(context)
                    .setTitle("Delete Instructor")
                    .setMessage("Are you sure you want to delete " + instructor.getName() + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        boolean deleted = dbHelper.deleteInstructor(instructor.getId());
                        if (deleted) {
                            instructors.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Instructor deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Failed to delete instructor", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        return convertView;
    }
}
