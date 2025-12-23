package com.studentmanagement.service;

import com.studentmanagement.model.Course;
import com.studentmanagement.model.Student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * StudentManager class handling CRUD operations for students
 */
public class StudentManager {
    private Map<String, Student> students;
    private int nextId;

    /**
     * Constructor initializing the student storage
     */
    public StudentManager() {
        this.students = new HashMap<>();
        this.nextId = 1001; // Starting ID
    }

    /**
     * Generate a unique student ID
     */
    private String generateUniqueId() {
        String id;
        do {
            id = "STU" + nextId++;
        } while (students.containsKey(id));
        return id;
    }

    /**
     * Create a new student with unique ID
     */
    public String createStudent(String firstName, String lastName, String email, int age) {
        try {
            String studentId = generateUniqueId();
            Student student = new Student(studentId, firstName, lastName, email, age);
            students.put(studentId, student);
            return studentId;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed to create student: " + e.getMessage());
        }
    }

    /**
     * Read/retrieve a student by ID
     */
    public Student getStudent(String studentId) {
        Student student = students.get(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student not found with ID: " + studentId);
        }
        return student;
    }

    /**
     * Update student information (selective fields)
     */
    public void updateStudent(String studentId, String field, String value) {
        Student student = getStudent(studentId);
        
        try {
            switch (field.toLowerCase()) {
                case "firstname":
                    student.setFirstName(value);
                    break;
                case "lastname":
                    student.setLastName(value);
                    break;
                case "email":
                    student.setEmail(value);
                    break;
                case "age":
                    student.setAge(Integer.parseInt(value));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + field);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid value for " + field + ": " + value);
        }
    }

    /**
     * Delete a student by ID
     */
    public boolean deleteStudent(String studentId) {
        if (students.containsKey(studentId)) {
            students.remove(studentId);
            return true;
        }
        return false;
    }

    /**
     * Search for students by various criteria
     */
    public List<Student> searchStudents(String searchTerm) {
        String lowerSearchTerm = searchTerm.toLowerCase();
        return students.values().stream()
            .filter(student -> 
                student.getStudentId().toLowerCase().contains(lowerSearchTerm) ||
                student.getFirstName().toLowerCase().contains(lowerSearchTerm) ||
                student.getLastName().toLowerCase().contains(lowerSearchTerm) ||
                student.getEmail().toLowerCase().contains(lowerSearchTerm)
            )
            .collect(Collectors.toList());
    }

    /**
     * Assign a course to a student
     */
    public void assignCourse(String studentId, String courseCode, String courseName, 
                            int credits, double grade) {
        Student student = getStudent(studentId);
        Course course = new Course(courseCode, courseName, credits, grade);
        student.addCourse(course);
    }

    /**
     * Remove a course from a student
     */
    public void removeCourse(String studentId, String courseCode) {
        Student student = getStudent(studentId);
        student.removeCourse(courseCode);
    }

    /**
     * Get all students
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    /**
     * Get total number of students
     */
    public int getTotalStudents() {
        return students.size();
    }

    /**
     * Check if a student exists
     */
    public boolean studentExists(String studentId) {
        return students.containsKey(studentId);
    }

    /**
     * Get students with GPA above threshold
     */
    public List<Student> getStudentsByMinGPA(double minGPA) {
        return students.values().stream()
            .filter(student -> student.getGpa() >= minGPA)
            .collect(Collectors.toList());
    }
}
