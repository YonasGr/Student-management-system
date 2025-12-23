package com.studentmanagement;

import com.studentmanagement.model.Student;
import com.studentmanagement.service.StudentManager;
import com.studentmanagement.util.InputValidator;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for Student Management System CLI
 * Provides console-based interface with admin login and menu system
 */
public class StudentManagementSystemApp {
    // Note: For production use, credentials should be stored securely (environment variables, 
    // configuration files, or external authentication service). Hardcoded for educational demonstration only.
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    
    private StudentManager studentManager;
    private Scanner scanner;

    public StudentManagementSystemApp() {
        this.studentManager = new StudentManager();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Main entry point
     */
    public static void main(String[] args) {
        StudentManagementSystemApp app = new StudentManagementSystemApp();
        app.run();
    }

    /**
     * Run the application
     */
    public void run() {
        printWelcomeBanner();
        
        if (performLogin()) {
            System.out.println("\n✓ Login successful! Welcome to Student Management System.\n");
            showMainMenu();
        } else {
            System.out.println("\n✗ Login failed. Maximum attempts reached. Exiting...");
        }
        
        scanner.close();
    }

    /**
     * Print welcome banner
     */
    private void printWelcomeBanner() {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║        STUDENT MANAGEMENT SYSTEM - OOP PROJECT           ║");
        System.out.println("║              Console-Based Application                    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }

    /**
     * Perform admin login with validation
     */
    private boolean performLogin() {
        System.out.println("\n--- ADMIN LOGIN ---");
        
        for (int attempt = 1; attempt <= MAX_LOGIN_ATTEMPTS; attempt++) {
            try {
                System.out.print("Username: ");
                String username = scanner.nextLine().trim();
                
                System.out.print("Password: ");
                String password = scanner.nextLine().trim();
                
                if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                    return true;
                }
                
                System.out.println("✗ Invalid credentials. Attempt " + attempt + " of " + MAX_LOGIN_ATTEMPTS);
                
            } catch (Exception e) {
                System.out.println("✗ Error during login: " + e.getMessage());
            }
        }
        
        return false;
    }

    /**
     * Display and handle main menu
     */
    private void showMainMenu() {
        boolean running = true;
        
        while (running) {
            try {
                printMainMenu();
                int choice = getIntInput("Enter your choice: ");
                
                switch (choice) {
                    case 1:
                        createStudent();
                        break;
                    case 2:
                        viewAllStudents();
                        break;
                    case 3:
                        viewStudent();
                        break;
                    case 4:
                        updateStudent();
                        break;
                    case 5:
                        deleteStudent();
                        break;
                    case 6:
                        searchStudents();
                        break;
                    case 7:
                        assignCourse();
                        break;
                    case 8:
                        removeCourse();
                        break;
                    case 9:
                        viewStatistics();
                        break;
                    case 0:
                        System.out.println("\nThank you for using Student Management System. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("✗ Invalid choice. Please try again.");
                }
                
            } catch (Exception e) {
                System.out.println("✗ Error: " + e.getMessage());
            }
            
            if (running) {
                pressEnterToContinue();
            }
        }
    }

    /**
     * Print main menu
     */
    private void printMainMenu() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                     MAIN MENU                            ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Create New Student                                   ║");
        System.out.println("║  2. View All Students                                    ║");
        System.out.println("║  3. View Student Details                                 ║");
        System.out.println("║  4. Update Student Information                           ║");
        System.out.println("║  5. Delete Student                                       ║");
        System.out.println("║  6. Search Students                                      ║");
        System.out.println("║  7. Assign Course to Student                             ║");
        System.out.println("║  8. Remove Course from Student                           ║");
        System.out.println("║  9. View Statistics                                      ║");
        System.out.println("║  0. Exit                                                 ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }

    /**
     * Create a new student with input validation
     */
    private void createStudent() {
        System.out.println("\n--- CREATE NEW STUDENT ---");
        
        try {
            // Get first name
            String firstName;
            do {
                System.out.print("First Name: ");
                firstName = scanner.nextLine().trim();
                if (!InputValidator.isValidName(firstName)) {
                    System.out.println("✗ Invalid name. Use only letters and spaces.");
                }
            } while (!InputValidator.isValidName(firstName));
            
            // Get last name
            String lastName;
            do {
                System.out.print("Last Name: ");
                lastName = scanner.nextLine().trim();
                if (!InputValidator.isValidName(lastName)) {
                    System.out.println("✗ Invalid name. Use only letters and spaces.");
                }
            } while (!InputValidator.isValidName(lastName));
            
            // Get email
            String email;
            do {
                System.out.print("Email: ");
                email = scanner.nextLine().trim();
                if (!InputValidator.isValidEmail(email)) {
                    System.out.println("✗ Invalid email format. Example: user@example.com");
                }
            } while (!InputValidator.isValidEmail(email));
            
            // Get age
            int age;
            do {
                age = getIntInput("Age: ");
                if (!InputValidator.isValidAge(age)) {
                    System.out.println("✗ Invalid age. Must be between 1 and 149.");
                }
            } while (!InputValidator.isValidAge(age));
            
            // Create student
            String studentId = studentManager.createStudent(firstName, lastName, email, age);
            System.out.println("\n✓ Student created successfully!");
            System.out.println("  Student ID: " + studentId);
            
        } catch (Exception e) {
            System.out.println("✗ Error creating student: " + e.getMessage());
        }
    }

    /**
     * View all students
     */
    private void viewAllStudents() {
        System.out.println("\n--- ALL STUDENTS ---");
        
        try {
            List<Student> students = studentManager.getAllStudents();
            
            if (students.isEmpty()) {
                System.out.println("No students found in the system.");
            } else {
                System.out.println("Total Students: " + students.size());
                System.out.println("\n" + "=".repeat(100));
                System.out.printf("%-12s %-20s %-30s %-5s %-8s%n", 
                                "ID", "Name", "Email", "Age", "GPA");
                System.out.println("=".repeat(100));
                
                for (Student student : students) {
                    System.out.printf("%-12s %-20s %-30s %-5d %.2f%n",
                                    student.getStudentId(),
                                    student.getFirstName() + " " + student.getLastName(),
                                    student.getEmail(),
                                    student.getAge(),
                                    student.getGpa());
                }
                System.out.println("=".repeat(100));
            }
        } catch (Exception e) {
            System.out.println("✗ Error retrieving students: " + e.getMessage());
        }
    }

    /**
     * View detailed information about a specific student
     */
    private void viewStudent() {
        System.out.println("\n--- VIEW STUDENT DETAILS ---");
        
        try {
            System.out.print("Enter Student ID: ");
            String studentId = scanner.nextLine().trim().toUpperCase();
            
            Student student = studentManager.getStudent(studentId);
            System.out.println("\n" + "=".repeat(60));
            System.out.println(student);
            System.out.println("=".repeat(60));
            
        } catch (IllegalArgumentException e) {
            System.out.println("✗ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error retrieving student: " + e.getMessage());
        }
    }

    /**
     * Update student information (selective fields)
     */
    private void updateStudent() {
        System.out.println("\n--- UPDATE STUDENT INFORMATION ---");
        
        try {
            System.out.print("Enter Student ID: ");
            String studentId = scanner.nextLine().trim().toUpperCase();
            
            // Verify student exists
            Student student = studentManager.getStudent(studentId);
            System.out.println("\nCurrent Information:");
            System.out.println(student);
            
            System.out.println("\nSelect field to update:");
            System.out.println("1. First Name");
            System.out.println("2. Last Name");
            System.out.println("3. Email");
            System.out.println("4. Age");
            
            int choice = getIntInput("Enter choice: ");
            String field = "";
            String value = "";
            
            switch (choice) {
                case 1:
                    field = "firstname";
                    do {
                        System.out.print("New First Name: ");
                        value = scanner.nextLine().trim();
                        if (!InputValidator.isValidName(value)) {
                            System.out.println("✗ Invalid name. Use only letters and spaces.");
                        }
                    } while (!InputValidator.isValidName(value));
                    break;
                case 2:
                    field = "lastname";
                    do {
                        System.out.print("New Last Name: ");
                        value = scanner.nextLine().trim();
                        if (!InputValidator.isValidName(value)) {
                            System.out.println("✗ Invalid name. Use only letters and spaces.");
                        }
                    } while (!InputValidator.isValidName(value));
                    break;
                case 3:
                    field = "email";
                    do {
                        System.out.print("New Email: ");
                        value = scanner.nextLine().trim();
                        if (!InputValidator.isValidEmail(value)) {
                            System.out.println("✗ Invalid email format.");
                        }
                    } while (!InputValidator.isValidEmail(value));
                    break;
                case 4:
                    field = "age";
                    int age;
                    do {
                        age = getIntInput("New Age: ");
                        if (!InputValidator.isValidAge(age)) {
                            System.out.println("✗ Invalid age. Must be between 1 and 149.");
                        }
                    } while (!InputValidator.isValidAge(age));
                    value = String.valueOf(age);
                    break;
                default:
                    System.out.println("✗ Invalid choice.");
                    return;
            }
            
            studentManager.updateStudent(studentId, field, value);
            System.out.println("\n✓ Student information updated successfully!");
            
        } catch (IllegalArgumentException e) {
            System.out.println("✗ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error updating student: " + e.getMessage());
        }
    }

    /**
     * Delete a student
     */
    private void deleteStudent() {
        System.out.println("\n--- DELETE STUDENT ---");
        
        try {
            System.out.print("Enter Student ID: ");
            String studentId = scanner.nextLine().trim().toUpperCase();
            
            // Show student details before deletion
            Student student = studentManager.getStudent(studentId);
            System.out.println("\nStudent to be deleted:");
            System.out.println(student);
            
            System.out.print("\nAre you sure you want to delete this student? (yes/no): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();
            
            if (confirmation.equals("yes") || confirmation.equals("y")) {
                if (studentManager.deleteStudent(studentId)) {
                    System.out.println("✓ Student deleted successfully!");
                } else {
                    System.out.println("✗ Failed to delete student.");
                }
            } else {
                System.out.println("Deletion cancelled.");
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println("✗ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error deleting student: " + e.getMessage());
        }
    }

    /**
     * Search for students
     */
    private void searchStudents() {
        System.out.println("\n--- SEARCH STUDENTS ---");
        
        try {
            System.out.print("Enter search term (ID, name, or email): ");
            String searchTerm = scanner.nextLine().trim();
            
            if (!InputValidator.isNotEmpty(searchTerm)) {
                System.out.println("✗ Search term cannot be empty.");
                return;
            }
            
            List<Student> results = studentManager.searchStudents(searchTerm);
            
            if (results.isEmpty()) {
                System.out.println("No students found matching: " + searchTerm);
            } else {
                System.out.println("\nFound " + results.size() + " student(s):");
                System.out.println("\n" + "=".repeat(100));
                System.out.printf("%-12s %-20s %-30s %-5s %-8s%n", 
                                "ID", "Name", "Email", "Age", "GPA");
                System.out.println("=".repeat(100));
                
                for (Student student : results) {
                    System.out.printf("%-12s %-20s %-30s %-5d %.2f%n",
                                    student.getStudentId(),
                                    student.getFirstName() + " " + student.getLastName(),
                                    student.getEmail(),
                                    student.getAge(),
                                    student.getGpa());
                }
                System.out.println("=".repeat(100));
            }
            
        } catch (Exception e) {
            System.out.println("✗ Error searching students: " + e.getMessage());
        }
    }

    /**
     * Assign a course to a student
     */
    private void assignCourse() {
        System.out.println("\n--- ASSIGN COURSE TO STUDENT ---");
        
        try {
            System.out.print("Enter Student ID: ");
            String studentId = scanner.nextLine().trim().toUpperCase();
            
            // Verify student exists
            Student student = studentManager.getStudent(studentId);
            System.out.println("\nStudent: " + student.getFirstName() + " " + student.getLastName());
            
            // Get course code
            String courseCode;
            do {
                System.out.print("Course Code (e.g., CS101, MATH201): ");
                courseCode = scanner.nextLine().trim().toUpperCase();
                if (!InputValidator.isValidCourseCode(courseCode)) {
                    System.out.println("✗ Invalid course code format. Use format like CS101 or MATH201.");
                }
            } while (!InputValidator.isValidCourseCode(courseCode));
            
            // Get course name
            String courseName;
            do {
                System.out.print("Course Name: ");
                courseName = scanner.nextLine().trim();
                if (!InputValidator.isNotEmpty(courseName)) {
                    System.out.println("✗ Course name cannot be empty.");
                }
            } while (!InputValidator.isNotEmpty(courseName));
            
            // Get credits
            int credits;
            do {
                credits = getIntInput("Credits (1-10): ");
                if (!InputValidator.isValidCredits(credits)) {
                    System.out.println("✗ Invalid credits. Must be between 1 and 10.");
                }
            } while (!InputValidator.isValidCredits(credits));
            
            // Get grade
            double grade;
            do {
                grade = getDoubleInput("Grade (0-100): ");
                if (!InputValidator.isValidGrade(grade)) {
                    System.out.println("✗ Invalid grade. Must be between 0 and 100.");
                }
            } while (!InputValidator.isValidGrade(grade));
            
            studentManager.assignCourse(studentId, courseCode, courseName, credits, grade);
            System.out.println("\n✓ Course assigned successfully!");
            System.out.println("  Updated GPA: " + String.format("%.2f", student.getGpa()));
            
        } catch (IllegalArgumentException e) {
            System.out.println("✗ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error assigning course: " + e.getMessage());
        }
    }

    /**
     * Remove a course from a student
     */
    private void removeCourse() {
        System.out.println("\n--- REMOVE COURSE FROM STUDENT ---");
        
        try {
            System.out.print("Enter Student ID: ");
            String studentId = scanner.nextLine().trim().toUpperCase();
            
            // Verify student exists and show courses
            Student student = studentManager.getStudent(studentId);
            System.out.println("\nStudent: " + student.getFirstName() + " " + student.getLastName());
            System.out.println("Enrolled Courses:");
            
            if (student.getCourses().isEmpty()) {
                System.out.println("  No courses enrolled.");
                return;
            }
            
            for (var course : student.getCourses()) {
                System.out.println("  - " + course.getCourseCode() + ": " + course.getCourseName());
            }
            
            System.out.print("\nEnter Course Code to remove: ");
            String courseCode = scanner.nextLine().trim().toUpperCase();
            
            studentManager.removeCourse(studentId, courseCode);
            // Retrieve updated student to ensure GPA is recalculated
            Student updatedStudent = studentManager.getStudent(studentId);
            System.out.println("\n✓ Course removed successfully!");
            System.out.println("  Updated GPA: " + String.format("%.2f", updatedStudent.getGpa()));
            
        } catch (IllegalArgumentException e) {
            System.out.println("✗ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error removing course: " + e.getMessage());
        }
    }

    /**
     * View system statistics
     */
    private void viewStatistics() {
        System.out.println("\n--- SYSTEM STATISTICS ---");
        
        try {
            List<Student> allStudents = studentManager.getAllStudents();
            
            if (allStudents.isEmpty()) {
                System.out.println("No students in the system.");
                return;
            }
            
            int totalStudents = allStudents.size();
            double averageGPA = allStudents.stream()
                                          .mapToDouble(Student::getGpa)
                                          .average()
                                          .orElse(0.0);
            
            long studentsWithCourses = allStudents.stream()
                                                  .filter(s -> !s.getCourses().isEmpty())
                                                  .count();
            
            System.out.println("\n" + "=".repeat(60));
            System.out.println("Total Students: " + totalStudents);
            System.out.println("Students with Courses: " + studentsWithCourses);
            System.out.println("Average GPA: " + String.format("%.2f", averageGPA));
            
            System.out.println("\nTop Students (GPA >= 3.0):");
            List<Student> topStudents = studentManager.getStudentsByMinGPA(3.0);
            if (topStudents.isEmpty()) {
                System.out.println("  No students with GPA >= 3.0");
            } else {
                for (Student student : topStudents) {
                    System.out.printf("  - %s %s (ID: %s, GPA: %.2f)%n",
                                    student.getFirstName(),
                                    student.getLastName(),
                                    student.getStudentId(),
                                    student.getGpa());
                }
            }
            System.out.println("=".repeat(60));
            
        } catch (Exception e) {
            System.out.println("✗ Error retrieving statistics: " + e.getMessage());
        }
    }

    /**
     * Get integer input with error handling
     */
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Get double input with error handling
     */
    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Wait for user to press Enter
     */
    private void pressEnterToContinue() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
