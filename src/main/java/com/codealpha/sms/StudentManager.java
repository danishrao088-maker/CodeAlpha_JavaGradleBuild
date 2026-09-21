package com.codealpha.sms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory student registry: CRUD operations + JSON export.
 * Uses the Gson third-party dependency (managed by Gradle).
 */
public class StudentManager {

    private final Map<String, Student> students = new LinkedHashMap<>();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /** Adds a student; duplicate IDs are rejected. */
    public Student addStudent(Student student) {
        if (students.containsKey(student.getId())) {
            throw new IllegalArgumentException("Student ID already exists: " + student.getId());
        }
        students.put(student.getId(), student);
        return student;
    }

    /** Looks a student up by ID. */
    public Optional<Student> getStudent(String id) {
        return Optional.ofNullable(students.get(id));
    }

    /** Removes a student; returns true when it existed. */
    public boolean removeStudent(String id) {
        return students.remove(id) != null;
    }

    /** All students in insertion order (read-only view). */
    public List<Student> listStudents() {
        return Collections.unmodifiableList(new ArrayList<>(students.values()));
    }

    public int totalStudents() {
        return students.size();
    }

    /** JSON export of every student (Gson). */
    public String toJson() {
        return GSON.toJson(listStudents());
    }
}
