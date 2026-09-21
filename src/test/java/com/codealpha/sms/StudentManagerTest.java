package com.codealpha.sms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the Student Management System (JUnit 5, managed by Gradle).
 */
class StudentManagerTest {

    private StudentManager manager;

    @BeforeEach
    void setUp() {
        manager = new StudentManager();
    }

    @Test
    @DisplayName("addStudent stores students and listStudents returns them in order")
    void addsAndListsStudents() {
        manager.addStudent(new Student("S-1", "Ali", "BS CS", 80, 90));
        manager.addStudent(new Student("S-2", "Sara", "BS SE", 70, 60));

        assertEquals(2, manager.totalStudents());
        assertEquals("S-1", manager.listStudents().get(0).getId());
        assertEquals("S-2", manager.listStudents().get(1).getId());
    }

    @Test
    @DisplayName("duplicate student ID is rejected with a clear error")
    void duplicateIdIsRejected() {
        manager.addStudent(new Student("S-1", "Ali", "BS CS", 80));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> manager.addStudent(new Student("S-1", "Duplicate", "BS CS", 50)));
        assertTrue(ex.getMessage().contains("S-1"));
        assertEquals(1, manager.totalStudents());
    }

    @Test
    @DisplayName("getStudent finds existing IDs and returns empty for unknown IDs")
    void searchReturnsStudentOrEmpty() {
        manager.addStudent(new Student("S-9", "Bilal", "BS DS", 55, 65, 75));

        assertTrue(manager.getStudent("S-9").isPresent());
        assertEquals("Bilal", manager.getStudent("S-9").get().getName());
        assertTrue(manager.getStudent("NOPE").isEmpty());
    }

    @Test
    @DisplayName("removeStudent deletes once and reports false afterwards")
    void removeStudentWorks() {
        manager.addStudent(new Student("S-3", "Hina", "BS IT", 88, 92));

        assertTrue(manager.removeStudent("S-3"));
        assertFalse(manager.removeStudent("S-3"));
        assertEquals(0, manager.totalStudents());
    }

    @Test
    @DisplayName("average and grade calculation follow the grading scale")
    void averageAndGradeCalculation() {
        Student topper = new Student("S-4", "Topper", "BS CS", 90, 95, 85); // avg 90 -> A+
        assertEquals(90.0, topper.average(), 0.001);
        assertEquals("A+", topper.grade());

        Student middle = new Student("S-5", "Middle", "BS CS", 70, 75); // avg 72.5 -> B
        assertEquals("B", middle.grade());

        Student fail = new Student("S-6", "Low", "BS CS", 30, 40); // avg 35 -> F
        assertEquals(35.0, fail.average(), 0.001);
        assertEquals("F", fail.grade());
    }

    @Test
    @DisplayName("toJson exports every student as a JSON array (Gson)")
    void jsonExportContainsStudents() {
        manager.addStudent(new Student("S-7", "Zara", "BS SE", 77, 81));

        String json = manager.toJson();
        assertTrue(json.startsWith("["));
        assertTrue(json.contains("Zara"));
        assertTrue(json.contains("S-7"));
        assertTrue(json.contains("BS SE"));
    }
}
