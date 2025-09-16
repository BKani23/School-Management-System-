package com.example.crudprogram;

public class Student {

    public int id;

    public String name;
    public String surname;
    public String dob;

    public String username;


    public Student(int id, String name, String surname, String dob, String username) {

        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.username = username;

    }

    @Override
    public String toString() {
        return name + " " + surname + " (" + dob + ")";
    }



}

