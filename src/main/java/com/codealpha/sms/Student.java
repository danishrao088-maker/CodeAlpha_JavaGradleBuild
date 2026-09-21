package com.codealpha.sms;

/**
 * Immutable student record with marks and grade calculation.
 */
public class Student {

    private final String id;
    private final String name;
    private final String course;
    private final int[] marks;

    public Student(String id, String name, String course, int... marks) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Student id must not be blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Student name must not be blank");
        }
        this.id = id.trim();
        this.name = name.trim();
        this.course = (course == null || course.isBlank()) ? "General" : course.trim();
        this.marks = (marks == null) ? new int[0] : marks.clone();
    }

    public String getId()      { return id; }
    public String getName()    { return name; }
    public String getCourse()  { return course; }
    public int[]  getMarks()   { return marks.clone(); }

    /** Average of all marks (0.0 when no marks recorded). */
    public double average() {
        if (marks.length == 0) {
            return 0.0;
        }
        int sum = 0;
        for (int mark : marks) {
            sum += mark;
        }
        return (double) sum / marks.length;
    }

    /** Grade derived from the average mark. */
    public String grade() {
        double avg = average();
        if (avg >= 90) return "A+";
        if (avg >= 80) return "A";
        if (avg >= 70) return "B";
        if (avg >= 60) return "C";
        if (avg >= 50) return "D";
        return "F";
    }

    @Override
    public String toString() {
        return String.format("%-8s | %-14s | %-24s | avg %5.1f | grade %s",
                id, name, course, average(), grade());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        return id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
