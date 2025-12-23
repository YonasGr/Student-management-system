package com.studentmanagement.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulated Student class representing a student entity
 * with private fields and public getters/setters
 */
public class Student {
    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private List<Course> courses;
    private double gpa;

    /**
     * Constructor for creating a new student
     */
    public Student(String studentId, String firstName, String lastName, String email, int age) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.courses = new ArrayList<>();
        this.gpa = 0.0;
    }

    // Getters
    public String getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courses); // Return copy to maintain encapsulation
    }

    public double getGpa() {
        return gpa;
    }

    // Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        if (age > 0 && age < 150) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("Age must be between 1 and 149");
        }
    }

    /**
     * Add a course to the student's course list
     */
    public void addCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
            calculateGPA();
        }
    }

    /**
     * Remove a course from the student's course list
     */
    public void removeCourse(String courseCode) {
        courses.removeIf(course -> course.getCourseCode().equals(courseCode));
        calculateGPA();
    }

    /**
     * Automatically calculate GPA based on enrolled courses
     */
    public void calculateGPA() {
        if (courses.isEmpty()) {
            this.gpa = 0.0;
            return;
        }

        double totalGradePoints = 0.0;
        int totalCredits = 0;

        for (Course course : courses) {
            totalGradePoints += course.getGradePoint() * course.getCredits();
            totalCredits += course.getCredits();
        }

        this.gpa = totalCredits > 0 ? totalGradePoints / totalCredits : 0.0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Student ID: %s%n", studentId));
        sb.append(String.format("Name: %s %s%n", firstName, lastName));
        sb.append(String.format("Email: %s%n", email));
        sb.append(String.format("Age: %d%n", age));
        sb.append(String.format("GPA: %.2f%n", gpa));
        sb.append("Enrolled Courses:\n");
        if (courses.isEmpty()) {
            sb.append("  No courses enrolled\n");
        } else {
            for (Course course : courses) {
                sb.append(String.format("  - %s%n", course));
            }
        }
        return sb.toString();
    }
}
