package com.example.crudprogram;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;



public class myDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "student_db";
    private static final int DATABASE_VERSION = 15;

    private final Context context;

    public myDBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create Admins Table
        String CREATE_ADMINS_TABLE = "CREATE TABLE admins (" +
                "adminID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL);";


        // Create Students Table
        String CREATE_STUDENTS_TABLE = "CREATE TABLE students (" +
                "studentID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "studentName TEXT NOT NULL, " +
                "studentSurname TEXT NOT NULL, " +
                "dob TEXT NOT NULL, " +
                "username TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL);";

        // Create Instructors Table
        String CREATE_INSTRUCTORS_TABLE = "CREATE TABLE instructors (" +
                "instructorID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "instructorName TEXT NOT NULL, " +
                "username TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL);";


        // Create Modules Table
        String CREATE_MODULES_TABLE = "CREATE TABLE modules (" +
                "moduleID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "moduleName TEXT NOT NULL, " +
                "duration TEXT NOT NULL);";

        // Create Tasks Table with foreign keys
        String CREATE_TASKS_TABLE = "CREATE TABLE tasks (" +
                "taskID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "taskName TEXT NOT NULL, " +
                "dueDate TEXT NOT NULL, " +
                "moduleID INTEGER NOT NULL, " +
                "studentID INTEGER NOT NULL, " +
                "completed INTEGER DEFAULT 0, " +
                "FOREIGN KEY (moduleID) REFERENCES modules(moduleID), " +
                "FOREIGN KEY (studentID) REFERENCES students(studentID));" ;


        // Execute the SQL to create tables
        db.execSQL(CREATE_ADMINS_TABLE);
        db.execSQL(CREATE_STUDENTS_TABLE);
        db.execSQL(CREATE_INSTRUCTORS_TABLE);
        db.execSQL(CREATE_MODULES_TABLE);
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop child tables first to avoid foreign key constraint issues
        db.execSQL("DROP TABLE IF EXISTS tasks");
        db.execSQL("DROP TABLE IF EXISTS modules");
        db.execSQL("DROP TABLE IF EXISTS students");
        db.execSQL("DROP TABLE IF EXISTS instructors");
        db.execSQL("DROP TABLE IF EXISTS admins");
        onCreate(db);

    }

    /**
     * Insert a new student record.
     */
    public boolean insertStudent(String name, String surname, String dob, String username, String password) {

        SQLiteDatabase db = null;

        try {

            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();


            values.put("studentName", name);
            values.put("studentSurname", surname);
            values.put("dob", dob);
            values.put("username", username);
            values.put("password", password);

            long result = db.insert("students", null, values);

            if (result == -1) {

                Toast.makeText(context, "Failed to insert student record", Toast.LENGTH_SHORT).show();

                return false;

            } else {

                Toast.makeText(context, "Student record inserted successfully", Toast.LENGTH_SHORT).show();

                return true;

            }

        } catch (Exception e) {

            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();

            return false;

        } finally {

            if (db != null && db.isOpen()) {
                db.close();
            }

        }
    }

    /**
     * Get a list of all students.
     */
    public  List<Student> getAllStudentsDetailed() {

        List<Student> students = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT studentID, studentName, studentSurname, dob, username FROM students", null);

        if (cursor.moveToFirst()) {

            do {

                Student student = new Student(

                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)

                );

                students.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return students;

    }

    public boolean updateStudent(int id, String name, String surname, String dob, String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();


        values.put("studentName", name);
        values.put("studentSurname", surname);
        values.put("dob", dob);
        values.put("username", username);
        values.put("password", password);

        int rows = db.update("students", values, "studentID=?", new String[]{String.valueOf(id)});

        db.close();

        return rows > 0;
    }

    public boolean deleteStudent(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int rows = db.delete("students", "studentID=?", new String[]{String.valueOf(id)});

        db.close();

        return rows > 0;

    }

    public boolean insertModule(String moduleName, String duration) {

        SQLiteDatabase db = null;

        try {

            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put("moduleName", moduleName);
            values.put("duration", duration);

            long result = db.insert("modules", null, values);

            return result != -1;

        } catch (Exception e) {

            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return false;

        } finally {

            if (db != null && db.isOpen()) db.close();

        }
    }

    public List<String> getAllModules() {


        List<String> modules = new ArrayList<>();


        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT moduleName, duration FROM modules", null);

        if (cursor.moveToFirst()) {

            do {

                String module = cursor.getString(0) + " | Duration: " + cursor.getString(1);
                modules.add(module);

            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();


        return modules;
    }

    public boolean insertInstructor(String instructorName, String username, String password) {

        SQLiteDatabase db = null;

        try {

            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put("instructorName", instructorName);
            values.put("username", username);
            values.put("password", password);

            long result = db.insert("instructors", null, values);

            return result != -1;

        } catch (Exception e) {

            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();

            return false;

        } finally {

            if (db != null && db.isOpen()) {

                db.close();

            }
        }
    }

    public List<String> getAllInstructors() {


        List<String> instructors = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT instructorName, username FROM instructors", null);

        if (cursor.moveToFirst()) {

            do {

                String name = cursor.getString(0);
                String username = cursor.getString(1);
                instructors.add(name + " (" + username + ")");

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();


        return instructors;
    }


    /**
     * Insert a new task assigned by an instructor to a student.
     */
    public boolean insertTask(String taskName, String dueDate, int moduleID, int studentID) {

        SQLiteDatabase db = null;

        try {

            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put("taskName", taskName);
            values.put("dueDate", dueDate);
            values.put("moduleID", moduleID);
            values.put("studentID", studentID);

            long result = db.insert("tasks", null, values);

            return result != -1;

        } catch (Exception e) {

            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();

            return false;

        } finally {

            if (db != null && db.isOpen()) db.close();

        }

    }

    /**
     * Get all task names.
     */
    public List<String> getAllModulesNames() {

        List<String> moduleNames = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT moduleName FROM modules", null);

        if (cursor.moveToFirst()) {

            do {

                moduleNames.add(cursor.getString(0));


            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return moduleNames;
    }


    public List<String> getAllStudentsNames() {

        List<String> studentNames = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT studentName FROM students", null);

        if (cursor.moveToFirst()) {

            do {

                studentNames.add(cursor.getString(0));

            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();

        return studentNames;
    }

    /**
     * Get all module IDs.
     */
    public List<Integer> getAllModuleIDs() {

        List<Integer> moduleIDs = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT moduleID FROM modules", null);

        if (cursor.moveToFirst()) {

            do {

                moduleIDs.add(cursor.getInt(0));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return moduleIDs;

    }

    /**
     * Get all student IDs.
     */
    public List<Integer> getAllStudentIDs() {

        List<Integer> studentIDs = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT studentID FROM students", null);

        if (cursor.moveToFirst()) {

            do {

                studentIDs.add(cursor.getInt(0));

            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();


        return studentIDs;

    }

    public List<Task> getTasksByStudentID(int studentID) {

        List<Task> tasks = new ArrayList<>();


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT taskID, taskName, dueDate, completed FROM tasks WHERE studentID = ?",

                new String[]{String.valueOf(studentID)});

        if (cursor.moveToFirst()) {

            do {

                int taskID = cursor.getInt(0);
                String taskName = cursor.getString(1);
                String dueDate = cursor.getString(2);
                int completed = cursor.getInt(3);

                tasks.add(new Task(taskID, taskName, dueDate, completed == 1));

            } while (cursor.moveToNext());

        }


        cursor.close();
        db.close();


        return tasks;
    }

    public boolean updateTask(Task task) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("taskName", task.getTaskName());
        values.put("dueDate", task.getDueDate());
        values.put("completed", task.isCompleted() ? 1 : 0);

        int rows = db.update("tasks", values, "taskID=?", new String[]{String.valueOf(task.getTaskID())});
        db.close();
        return rows > 0;
    }


    public boolean updateTaskCompletion(int taskID, boolean completed) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("completed", completed ? 1 : 0);
        int rows = db.update("tasks", values, "taskID = ?", new String[]{String.valueOf(taskID)});
        db.close();

        return rows > 0;
    }


    public List<Task> getTasks() {

        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT taskID, taskName, dueDate, completed FROM tasks", null);

        if (cursor.moveToFirst()) {

            do {

                int taskID = cursor.getInt(0);
                String taskName = cursor.getString(1);
                String dueDate = cursor.getString(2);
                boolean completed = cursor.getInt(3) == 1;

                tasks.add(new Task(taskID, taskName, dueDate, completed));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tasks;
    }

    public boolean deleteTask(int taskID) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete("tasks", "taskID = ?", new String[]{String.valueOf(taskID)});
        db.close();
        return rows > 0;
    }




    public boolean deleteInstructor(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete("instructors", "instructorID = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected > 0;
    }

    public boolean updateInstructor(int id, String name, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("instructorName", name);
        values.put("username", username);
        values.put("password", password);
        int rowsAffected = db.update("instructors", values, "instructorID = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected > 0;
    }



    public List<Instructor> getAllInstructorsWithIDs() {
        List<Instructor> instructors = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT instructorID, instructorName, username, password FROM instructors", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String username = cursor.getString(2);
                String password = cursor.getString(3);

                instructors.add(new Instructor(id, name, username, password));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return instructors;
    }

    public boolean checkStudentCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query for student with matching username and password
        String query = "SELECT * FROM students WHERE username = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public boolean checkInstructorCredentials(String username, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        // Query for instructor with matching username and password
        String query = "SELECT * FROM instructors WHERE username = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public int getStudentIDByUsername(String username) {

        int studentID = -1;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT studentID FROM students WHERE username = ?", new String[]{username});

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                studentID = cursor.getInt(cursor.getColumnIndexOrThrow("studentID"));
            }
            cursor.close();

        }

        db.close();
        return studentID;
    }



}
