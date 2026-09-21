package com.codealpha.sms;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Entry point of the Student Management System.
 *
 * Modes:
 *   default     -> interactive console menu
 *   "--demo"    -> non-interactive demo run (CI friendly, good for screenshots)
 */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        if (args.length > 0 && "--demo".equalsIgnoreCase(args[0])) {
            runDemo(manager);
        } else {
            runInteractive(manager);
        }
    }

    /* ------------------------------------------------------------------ */

    private static void runDemo(StudentManager m) {
        line();
        System.out.println(" STUDENT MANAGEMENT SYSTEM - DEMO MODE (CI friendly)");
        line();

        m.addStudent(new Student("S-001", "Ali Raza", "BS Computer Science", 85, 90, 78));
        m.addStudent(new Student("S-002", "Sara Khan", "BS Software Engineering", 92, 88, 95));
        m.addStudent(new Student("S-003", "Bilal Ahmed", "BS Data Science", 60, 55, 42));
        System.out.println("[ADD]    3 students added successfully.");

        System.out.println("\n[LIST]   All students:");
        for (Student s : m.listStudents()) {
            System.out.println("         " + s);
        }

        System.out.println("\n[SEARCH] Looking up S-002:");
        m.getStudent("S-002").ifPresent(s -> System.out.println("         found -> " + s));

        System.out.println("\n[DELETE] Removing S-003:");
        System.out.println("         removed? " + m.removeStudent("S-003"));

        System.out.println("\n[EXPORT] JSON export via Gson:");
        System.out.println(m.toJson());

        System.out.println("[DONE]   Total students now: " + m.totalStudents());
    }

    /* ------------------------------------------------------------------ */

    private static void runInteractive(StudentManager m) {
        Scanner sc = new Scanner(System.in);
        line();
        System.out.println(" STUDENT MANAGEMENT SYSTEM | CodeAlpha DevOps - Task 3");
        line();

        boolean running = true;
        while (running) {
            System.out.println("\n1) Add student   2) List all   3) Search by ID");
            System.out.println("4) Delete        5) Export JSON  6) Exit");
            System.out.print("Choose [1-6]: ");

            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("!! Invalid input - enter a number between 1 and 6.");
                continue;
            }

            switch (choice) {
                case 1:
                    addPrompt(m, sc);
                    break;
                case 2:
                    if (m.totalStudents() == 0) {
                        System.out.println("   (no students yet)");
                    }
                    for (Student s : m.listStudents()) {
                        System.out.println("   " + s);
                    }
                    break;
                case 3:
                    System.out.print("   Student ID: ");
                    String searchId = sc.nextLine().trim();
                    m.getStudent(searchId)
                            .ifPresentOrElse(
                                    s -> System.out.println("   found -> " + s),
                                    () -> System.out.println("   no student with ID " + searchId));
                    break;
                case 4:
                    System.out.print("   Student ID to delete: ");
                    String delId = sc.nextLine().trim();
                    System.out.println("   removed? " + m.removeStudent(delId));
                    break;
                case 5:
                    System.out.println(m.toJson());
                    break;
                case 6:
                    running = false;
                    System.out.println("Bye! Total students kept in memory: " + m.totalStudents());
                    break;
                default:
                    System.out.println("!! Please choose 1-6.");
            }
        }
        sc.close();
    }

    private static void addPrompt(StudentManager m, Scanner sc) {
        System.out.print("   ID (e.g. S-001): ");
        String id = sc.nextLine().trim();
        System.out.print("   Name: ");
        String name = sc.nextLine().trim();
        System.out.print("   Course: ");
        String course = sc.nextLine().trim();
        System.out.print("   Marks (space separated, e.g. 85 90 78): ");
        String marksLine = sc.nextLine().trim();

        int[] marks = parseMarks(marksLine);
        try {
            Student added = m.addStudent(new Student(id, name, course, marks));
            System.out.println("   added -> " + added);
        } catch (IllegalArgumentException e) {
            System.out.println("   !! " + e.getMessage());
        }
    }

    private static int[] parseMarks(String marksLine) {
        if (marksLine.isEmpty()) {
            return new int[0];
        }
        String[] parts = marksLine.split("\\s+");
        int[] marks = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            try {
                marks[i] = Integer.parseInt(parts[i]);
            } catch (NumberFormatException e) {
                marks[i] = 0;
            }
        }
        return marks;
    }

    private static void line() {
        System.out.println("==========================================================");
    }
}
