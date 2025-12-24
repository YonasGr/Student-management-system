package com.studentmanagement;

import com.studentmanagement.model.Student;
import com.studentmanagement.service.StudentManager;
import com.studentmanagement.util.InputValidator;
import com.studentmanagement.util.DataStore;
import com.studentmanagement.util.SessionLogger;
import com.studentmanagement.util.ConsoleColors;
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
    private SessionLogger sessionLogger;

    public StudentManagementSystemApp() {
        this.studentManager = DataStore.load();
        this.scanner = new Scanner(System.in);
        this.sessionLogger = null;
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
            System.out.println(ConsoleColors.colorize("\n✓ Login successful! Welcome to Student Management System.\n", ConsoleColors.GREEN_BOLD));
            showMainMenu();
        } else {
            System.out.println(ConsoleColors.colorize("\n✗ Login failed. Maximum attempts reached. Exiting...", ConsoleColors.RED_BOLD));
        }
        try {
            DataStore.save(studentManager);
        } catch (Exception e) {
            System.out.println(ConsoleColors.colorize("✗ Error saving data: " + e.getMessage(), ConsoleColors.RED_BOLD));
        }
        if (sessionLogger != null) {
            sessionLogger.close();
        }
        scanner.close();
    }

    /**
     * Print welcome banner
     */
    private void printWelcomeBanner() {
        System.out.println(ConsoleColors.colorize("╔═══════════════════════════════════════════════════════════╗", ConsoleColors.BRIGHT_BLUE, ConsoleColors.BOLD));
        System.out.println(ConsoleColors.colorize("║        STUDENT MANAGEMENT SYSTEM - OOP PROJECT           ║", ConsoleColors.BRIGHT_WHITE_BOLD));
        System.out.println(ConsoleColors.colorize("║              Console-Based Application                    ║", ConsoleColors.BRIGHT_WHITE_BOLD));
        System.out.println(ConsoleColors.colorize("╚═══════════════════════════════════════════════════════════╝", ConsoleColors.BRIGHT_BLUE, ConsoleColors.BOLD));
    }

    /**
     * Perform admin login with validation
     */
    private boolean performLogin() {
        System.out.println(ConsoleColors.colorize("\n--- ADMIN LOGIN ---", ConsoleColors.BRIGHT_CYAN, ConsoleColors.BOLD));
        
        for (int attempt = 1; attempt <= MAX_LOGIN_ATTEMPTS; attempt++) {
            try {
                printPrompt("Username: ");
                String username = scanner.nextLine().trim();
                
                printPrompt("Password: ");
                String password = scanner.nextLine().trim();
                
                if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                    try {
                        this.sessionLogger = new SessionLogger(username);
                        sessionLogger.logAction("LOGIN_SUCCESS", "username=" + username);
                    } catch (Exception e) {
                        System.out.println(ConsoleColors.colorize("✗ Warning: Session logging disabled: " + e.getMessage(), ConsoleColors.YELLOW_BOLD));
                    }
                    return true;
                }
                
                System.out.println(ConsoleColors.colorize("✗ Invalid credentials. Attempt " + attempt + " of " + MAX_LOGIN_ATTEMPTS, ConsoleColors.RED_BOLD));
                
            } catch (Exception e) {
                System.out.println(ConsoleColors.colorize("✗ Error during login: " + e.getMessage(), ConsoleColors.RED_BOLD));
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
                        System.out.println(ConsoleColors.colorize("\nThank you for using Student Management System. Goodbye!", ConsoleColors.GREEN_BOLD));
                        running = false;
                        break;
                    default:
                        System.out.println(ConsoleColors.colorize("✗ Invalid choice. Please try again.", ConsoleColors.RED_BOLD));
                }
                
            } catch (Exception e) {
                System.out.println(ConsoleColors.colorize("✗ Error: " + e.getMessage(), ConsoleColors.RED_BOLD));
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
        System.out.println(ConsoleColors.colorize("\n╔═══════════════════════════════════════════════════════════╗", ConsoleColors.BRIGHT_BLUE, ConsoleColors.BOLD));
        System.out.println(ConsoleColors.colorize("║                     MAIN MENU                            ║", ConsoleColors.BRIGHT_WHITE_BOLD));
        System.out.println(ConsoleColors.colorize("╠═══════════════════════════════════════════════════════════╣", ConsoleColors.BRIGHT_BLUE, ConsoleColors.BOLD));
        System.out.println(ConsoleColors.colorize("║  1. Create New Student                                   ║", ConsoleColors.BRIGHT_GREEN));
        System.out.println(ConsoleColors.colorize("║  2. View All Students                                    ║", ConsoleColors.BRIGHT_GREEN));
        System.out.println(ConsoleColors.colorize("║  3. View Student Details                                 ║", ConsoleColors.BRIGHT_GREEN));
        System.out.println(ConsoleColors.colorize("║  4. Update Student Information                           ║", ConsoleColors.BRIGHT_GREEN));
        System.out.println(ConsoleColors.colorize("║  5. Delete Student                                       ║", ConsoleColors.BRIGHT_GREEN));
        System.out.println(ConsoleColors.colorize("║  6. Search Students                                      ║", ConsoleColors.BRIGHT_GREEN));
        System.out.println(ConsoleColors.colorize("║  7. Assign Course to Student                             ║", ConsoleColors.BRIGHT_GREEN));
        System.out.println(ConsoleColors.colorize("║  8. Remove Course from Student                           ║", ConsoleColors.BRIGHT_GREEN));
        System.out.println(ConsoleColors.colorize("║  9. View Statistics                                      ║", ConsoleColors.BRIGHT_GREEN));
        System.out.println(ConsoleColors.colorize("║  0. Exit                                                 ║", ConsoleColors.BRIGHT_GREEN));
        System.out.println(ConsoleColors.colorize("╚═══════════════════════════════════════════════════════════╝", ConsoleColors.BRIGHT_BLUE, ConsoleColors.BOLD));
    }

    /**
     * Create a new student with input validation
     */
    private void createStudent() {
        System.out.println(ConsoleColors.colorize("\n--- CREATE NEW STUDENT ---", ConsoleColors.BRIGHT_CYAN, ConsoleColors.BOLD));
        
        try {
            // Get first name
            String firstName;
            do {
                printPrompt("First Name: ");
                firstName = scanner.nextLine().trim();
                if (!InputValidator.isValidName(firstName)) {
                    System.out.println(ConsoleColors.colorize("✗ Invalid name. Use only letters and spaces.", ConsoleColors.RED_BOLD));
                }
            } while (!InputValidator.isValidName(firstName));
            
            // Get last name
            String lastName;
            do {
                printPrompt("Last Name: ");
                lastName = scanner.nextLine().trim();
                if (!InputValidator.isValidName(lastName)) {
                    System.out.println(ConsoleColors.colorize("✗ Invalid name. Use only letters and spaces.", ConsoleColors.RED_BOLD));
                }
            } while (!InputValidator.isValidName(lastName));
            
            // Get email
            String email;
            do {
                printPrompt("Email: ");
                email = scanner.nextLine().trim();
                if (!InputValidator.isValidEmail(email)) {
                    System.out.println(ConsoleColors.colorize("✗ Invalid email format. Example: user@example.com", ConsoleColors.RED_BOLD));
                }
            } while (!InputValidator.isValidEmail(email));
            
            // Get age
            int age;
            do {
                age = getIntInput("Age: ");
                if (!InputValidator.isValidAge(age)) {
                    System.out.println(ConsoleColors.colorize("✗ Invalid age. Must be between 1 and 149.", ConsoleColors.RED_BOLD));
                }
            } while (!InputValidator.isValidAge(age));
            
            // Create student
            String studentId = studentManager.createStudent(firstName, lastName, email, age);
            System.out.println(ConsoleColors.colorize("\n✓ Student created successfully!", ConsoleColors.GREEN_BOLD));
            System.out.println(ConsoleColors.colorize("  Student ID: " + studentId, ConsoleColors.BRIGHT_WHITE_BOLD));
            if (sessionLogger != null) {
                sessionLogger.logAction("CREATE_STUDENT", "id=" + studentId + ", email=" + email);
            }
            try { DataStore.save(studentManager); } catch (Exception ignored) {}
            
        } catch (Exception e) {
            System.out.println(ConsoleColors.colorize("✗ Error creating student: " + e.getMessage(), ConsoleColors.RED_BOLD));
        }
    }

    /**
     * View all students
     */
    private void viewAllStudents() {
        System.out.println(ConsoleColors.colorize("\n--- ALL STUDENTS ---", ConsoleColors.BRIGHT_CYAN, ConsoleColors.BOLD));
        
        try {
            List<Student> students = studentManager.getAllStudents();
            
            if (students.isEmpty()) {
                System.out.println(ConsoleColors.colorize("No students found in the system.", ConsoleColors.YELLOW_BOLD));
            } else {
                System.out.println(ConsoleColors.colorize("Total Students: " + students.size(), ConsoleColors.BRIGHT_WHITE_BOLD));
                System.out.println(ConsoleColors.colorize("\n" + "=".repeat(100), ConsoleColors.BRIGHT_BLUE));
                System.out.printf(ConsoleColors.colorize("%-12s %-20s %-30s %-5s %-8s%n", ConsoleColors.BRIGHT_WHITE_BOLD), 
                                "ID", "Name", "Email", "Age", "GPA");
                System.out.println(ConsoleColors.colorize("=".repeat(100), ConsoleColors.BRIGHT_BLUE));
                
                for (Student student : students) {
                    System.out.printf("%-12s %-20s %-30s %-5d %.2f%n",
                                    student.getStudentId(),
                                    student.getFirstName() + " " + student.getLastName(),
                                    student.getEmail(),
                                    student.getAge(),
                                    student.getGpa());
                }
                System.out.println(ConsoleColors.colorize("=".repeat(100), ConsoleColors.BRIGHT_BLUE));
            }
        } catch (Exception e) {
            System.out.println(ConsoleColors.colorize("✗ Error retrieving students: " + e.getMessage(), ConsoleColors.RED_BOLD));
        }
    }

    /**
     * View detailed information about a specific student
     */
    private void viewStudent() {
        System.out.println(ConsoleColors.colorize("\n--- VIEW STUDENT DETAILS ---", ConsoleColors.BRIGHT_CYAN, ConsoleColors.BOLD));
        
        try {
            printPrompt("Enter Student ID: ");
            String studentId = scanner.nextLine().trim().toUpperCase();
            
            Student student = studentManager.getStudent(studentId);
            System.out.println(ConsoleColors.colorize("\n" + "=".repeat(60), ConsoleColors.BRIGHT_BLUE));
            System.out.println(student);
            System.out.println(ConsoleColors.colorize("=".repeat(60), ConsoleColors.BRIGHT_BLUE));
            
        } catch (IllegalArgumentException e) {
            System.out.println(ConsoleColors.colorize("✗ " + e.getMessage(), ConsoleColors.RED_BOLD));
        } catch (Exception e) {
            System.out.println(ConsoleColors.colorize("✗ Error retrieving student: " + e.getMessage(), ConsoleColors.RED_BOLD));
        }
    }

    /**
     * Update student information (selective fields)
     */
    private void updateStudent() {
        System.out.println(ConsoleColors.colorize("\n--- UPDATE STUDENT INFORMATION ---", ConsoleColors.BRIGHT_CYAN, ConsoleColors.BOLD));
        
        try {
            printPrompt("Enter Student ID: ");
            String studentId = scanner.nextLine().trim().toUpperCase();
            
            // Verify student exists
            Student student = studentManager.getStudent(studentId);
            System.out.println("\nCurrent Information:");
            System.out.println(student);
            
            System.out.println(ConsoleColors.colorize("\nSelect field to update:", ConsoleColors.BRIGHT_WHITE_BOLD));
            System.out.println(ConsoleColors.colorize("1. First Name", ConsoleColors.BRIGHT_GREEN));
            System.out.println(ConsoleColors.colorize("2. Last Name", ConsoleColors.BRIGHT_GREEN));
            System.out.println(ConsoleColors.colorize("3. Email", ConsoleColors.BRIGHT_GREEN));
            System.out.println(ConsoleColors.colorize("4. Age", ConsoleColors.BRIGHT_GREEN));
            
            int choice = getIntInput("Enter choice: ");
            String field = "";
            String value = "";
            
            switch (choice) {
                case 1:
                    field = "firstname";
                    do {
                        printPrompt("New First Name: ");
                        value = scanner.nextLine().trim();
                        if (!InputValidator.isValidName(value)) {
                            System.out.println(ConsoleColors.colorize("✗ Invalid name. Use only letters and spaces.", ConsoleColors.RED_BOLD));
                        }
                    } while (!InputValidator.isValidName(value));
                    break;
                case 2:
                    field = "lastname";
                    do {
                        printPrompt("New Last Name: ");
                        value = scanner.nextLine().trim();
                        if (!InputValidator.isValidName(value)) {
                            System.out.println(ConsoleColors.colorize("✗ Invalid name. Use only letters and spaces.", ConsoleColors.RED_BOLD));
                        }
                    } while (!InputValidator.isValidName(value));
                    break;
                case 3:
                    field = "email";
                    do {
                        printPrompt("New Email: ");
                        value = scanner.nextLine().trim();
                        if (!InputValidator.isValidEmail(value)) {
                            System.out.println(ConsoleColors.colorize("✗ Invalid email format.", ConsoleColors.RED_BOLD));
                        }
                    } while (!InputValidator.isValidEmail(value));
                    break;
                case 4:
                    field = "age";
                    int age;
                    do {
                        age = getIntInput("New Age: ");
                        if (!InputValidator.isValidAge(age)) {
                            System.out.println(ConsoleColors.colorize("✗ Invalid age. Must be between 1 and 149.", ConsoleColors.RED_BOLD));
                        }
                    } while (!InputValidator.isValidAge(age));
                    value = String.valueOf(age);
                    break;
                default:
                    System.out.println(ConsoleColors.colorize("✗ Invalid choice.", ConsoleColors.RED_BOLD));
                    return;
            }
            
            studentManager.updateStudent(studentId, field, value);
            System.out.println(ConsoleColors.colorize("\n✓ Student information updated successfully!", ConsoleColors.GREEN_BOLD));
            if (sessionLogger != null) {
                sessionLogger.logAction("UPDATE_STUDENT", "id=" + studentId + ", field=" + field);
            }
            try { DataStore.save(studentManager); } catch (Exception ignored) {}
            
        } catch (IllegalArgumentException e) {
            System.out.println(ConsoleColors.colorize("✗ " + e.getMessage(), ConsoleColors.RED_BOLD));
        } catch (Exception e) {
            System.out.println(ConsoleColors.colorize("✗ Error updating student: " + e.getMessage(), ConsoleColors.RED_BOLD));
        }
    }

    /**
     * Delete a student
     */
    private void deleteStudent() {
        System.out.println(ConsoleColors.colorize("\n--- DELETE STUDENT ---", ConsoleColors.BRIGHT_CYAN, ConsoleColors.BOLD));
        
        try {
            printPrompt("Enter Student ID: ");
            String studentId = scanner.nextLine().trim().toUpperCase();
            
            // Show student details before deletion
            Student student = studentManager.getStudent(studentId);
            System.out.println("\nStudent to be deleted:");
            System.out.println(student);
            
            printPrompt("\nAre you sure you want to delete this student? (yes/no): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();
            
            if (confirmation.equals("yes") || confirmation.equals("y")) {
                if (studentManager.deleteStudent(studentId)) {
                    System.out.println(ConsoleColors.colorize("✓ Student deleted successfully!", ConsoleColors.GREEN_BOLD));
                    if (sessionLogger != null) {
                        sessionLogger.logAction("DELETE_STUDENT", "id=" + studentId);
                    }
                    try { DataStore.save(studentManager); } catch (Exception ignored) {}
                } else {
                    System.out.println(ConsoleColors.colorize("✗ Failed to delete student.", ConsoleColors.RED_BOLD));
                }
            } else {
                System.out.println(ConsoleColors.colorize("Deletion cancelled.", ConsoleColors.YELLOW_BOLD));
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println(ConsoleColors.colorize("✗ " + e.getMessage(), ConsoleColors.RED_BOLD));
        } catch (Exception e) {
            System.out.println(ConsoleColors.colorize("✗ Error deleting student: " + e.getMessage(), ConsoleColors.RED_BOLD));
        }
    }

    /**
     * Search for students
     */
    private void searchStudents() {
        System.out.println(ConsoleColors.colorize("\n--- SEARCH STUDENTS ---", ConsoleColors.BRIGHT_CYAN, ConsoleColors.BOLD));
        
        try {
            printPrompt("Enter search term (ID, name, or email): ");
            String searchTerm = scanner.nextLine().trim();
            
            if (!InputValidator.isNotEmpty(searchTerm)) {
                System.out.println(ConsoleColors.colorize("✗ Search term cannot be empty.", ConsoleColors.RED_BOLD));
                return;
            }
            
            List<Student> results = studentManager.searchStudents(searchTerm);
            
            if (results.isEmpty()) {
                System.out.println(ConsoleColors.colorize("No students found matching: " + searchTerm, ConsoleColors.YELLOW_BOLD));
            } else {
                System.out.println(ConsoleColors.colorize("\nFound " + results.size() + " student(s):", ConsoleColors.BRIGHT_WHITE_BOLD));
                System.out.println(ConsoleColors.colorize("\n" + "=".repeat(100), ConsoleColors.BRIGHT_BLUE));
                System.out.printf(ConsoleColors.colorize("%-12s %-20s %-30s %-5s %-8s%n", ConsoleColors.BRIGHT_WHITE_BOLD), 
                                "ID", "Name", "Email", "Age", "GPA");
                System.out.println(ConsoleColors.colorize("=".repeat(100), ConsoleColors.BRIGHT_BLUE));
                
                for (Student student : results) {
                    System.out.printf("%-12s %-20s %-30s %-5d %.2f%n",
                                    student.getStudentId(),
                                    student.getFirstName() + " " + student.getLastName(),
                                    student.getEmail(),
                                    student.getAge(),
                                    student.getGpa());
                }
                System.out.println(ConsoleColors.colorize("=".repeat(100), ConsoleColors.BRIGHT_BLUE));
            }
            
        } catch (Exception e) {
            System.out.println(ConsoleColors.colorize("✗ Error searching students: " + e.getMessage(), ConsoleColors.RED_BOLD));
        }
    }

    /**
     * Assign a course to a student
     */
    private void assignCourse() {
        System.out.println(ConsoleColors.colorize("\n--- ASSIGN COURSE TO STUDENT ---", ConsoleColors.BRIGHT_CYAN, ConsoleColors.BOLD));
        
        try {
            printPrompt("Enter Student ID: ");
            String studentId = scanner.nextLine().trim().toUpperCase();
            
            // Verify student exists
            Student student = studentManager.getStudent(studentId);
            System.out.println("\nStudent: " + student.getFirstName() + " " + student.getLastName());
            
            // Get course code
            String courseCode;
            do {
                printPrompt("Course Code (e.g., CS101, MATH201): ");
                courseCode = scanner.nextLine().trim().toUpperCase();
                if (!InputValidator.isValidCourseCode(courseCode)) {
                    System.out.println(ConsoleColors.colorize("✗ Invalid course code format. Use format like CS101 or MATH201.", ConsoleColors.RED_BOLD));
                }
            } while (!InputValidator.isValidCourseCode(courseCode));
            
            // Get course name
            String courseName;
            do {
                printPrompt("Course Name: ");
                courseName = scanner.nextLine().trim();
                if (!InputValidator.isNotEmpty(courseName)) {
                    System.out.println(ConsoleColors.colorize("✗ Course name cannot be empty.", ConsoleColors.RED_BOLD));
                }
            } while (!InputValidator.isNotEmpty(courseName));
            
            // Get credits
            int credits;
            do {
                credits = getIntInput("Credits (1-10): ");
                if (!InputValidator.isValidCredits(credits)) {
                    System.out.println(ConsoleColors.colorize("✗ Invalid credits. Must be between 1 and 10.", ConsoleColors.RED_BOLD));
                }
            } while (!InputValidator.isValidCredits(credits));
            
            // Get grade
            double grade;
            do {
                grade = getDoubleInput("Grade (0-100): ");
                if (!InputValidator.isValidGrade(grade)) {
                    System.out.println(ConsoleColors.colorize("✗ Invalid grade. Must be between 0 and 100.", ConsoleColors.RED_BOLD));
                }
            } while (!InputValidator.isValidGrade(grade));
            
            studentManager.assignCourse(studentId, courseCode, courseName, credits, grade);
            System.out.println(ConsoleColors.colorize("\n✓ Course assigned successfully!", ConsoleColors.GREEN_BOLD));
            System.out.println(ConsoleColors.colorize("  Updated GPA: " + String.format("%.2f", student.getGpa()), ConsoleColors.BRIGHT_WHITE_BOLD));
            if (sessionLogger != null) {
                sessionLogger.logAction("ASSIGN_COURSE", "id=" + studentId + ", course=" + courseCode);
            }
            try { DataStore.save(studentManager); } catch (Exception ignored) {}
            
        } catch (IllegalArgumentException e) {
            System.out.println(ConsoleColors.colorize("✗ " + e.getMessage(), ConsoleColors.RED_BOLD));
        } catch (Exception e) {
            System.out.println(ConsoleColors.colorize("✗ Error assigning course: " + e.getMessage(), ConsoleColors.RED_BOLD));
        }
    }

    /**
     * Remove a course from a student
     */
    private void removeCourse() {
        System.out.println(ConsoleColors.colorize("\n--- REMOVE COURSE FROM STUDENT ---", ConsoleColors.BRIGHT_CYAN, ConsoleColors.BOLD));
        
        try {
            printPrompt("Enter Student ID: ");
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
            
            printPrompt("\nEnter Course Code to remove: ");
            String courseCode = scanner.nextLine().trim().toUpperCase();
            
            studentManager.removeCourse(studentId, courseCode);
            // Retrieve updated student to ensure GPA is recalculated
            Student updatedStudent = studentManager.getStudent(studentId);
            System.out.println(ConsoleColors.colorize("\n✓ Course removed successfully!", ConsoleColors.GREEN_BOLD));
            System.out.println(ConsoleColors.colorize("  Updated GPA: " + String.format("%.2f", updatedStudent.getGpa()), ConsoleColors.BRIGHT_WHITE_BOLD));
            if (sessionLogger != null) {
                sessionLogger.logAction("REMOVE_COURSE", "id=" + studentId + ", course=" + courseCode);
            }
            try { DataStore.save(studentManager); } catch (Exception ignored) {}
            
        } catch (IllegalArgumentException e) {
            System.out.println(ConsoleColors.colorize("✗ " + e.getMessage(), ConsoleColors.RED_BOLD));
        } catch (Exception e) {
            System.out.println(ConsoleColors.colorize("✗ Error removing course: " + e.getMessage(), ConsoleColors.RED_BOLD));
        }
    }

    /**
     * View system statistics
     */
    private void viewStatistics() {
        System.out.println(ConsoleColors.colorize("\n--- SYSTEM STATISTICS ---", ConsoleColors.BRIGHT_CYAN, ConsoleColors.BOLD));
        
        try {
            List<Student> allStudents = studentManager.getAllStudents();
            
            if (allStudents.isEmpty()) {
                System.out.println(ConsoleColors.colorize("No students in the system.", ConsoleColors.YELLOW_BOLD));
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
            
            System.out.println(ConsoleColors.colorize("\n" + "=".repeat(60), ConsoleColors.BRIGHT_BLUE));
            System.out.println(ConsoleColors.colorize("Total Students: " + totalStudents, ConsoleColors.BRIGHT_WHITE_BOLD));
            System.out.println(ConsoleColors.colorize("Students with Courses: " + studentsWithCourses, ConsoleColors.BRIGHT_WHITE_BOLD));
            System.out.println(ConsoleColors.colorize("Average GPA: " + String.format("%.2f", averageGPA), ConsoleColors.BRIGHT_WHITE_BOLD));
            
            System.out.println(ConsoleColors.colorize("\nTop Students (GPA >= 3.0):", ConsoleColors.BRIGHT_WHITE_BOLD));
            List<Student> topStudents = studentManager.getStudentsByMinGPA(3.0);
            if (topStudents.isEmpty()) {
                System.out.println(ConsoleColors.colorize("  No students with GPA >= 3.0", ConsoleColors.YELLOW_BOLD));
            } else {
                for (Student student : topStudents) {
                    System.out.printf("  - %s %s (ID: %s, GPA: %.2f)%n",
                                    student.getFirstName(),
                                    student.getLastName(),
                                    student.getStudentId(),
                                    student.getGpa());
                }
            }
            System.out.println(ConsoleColors.colorize("=".repeat(60), ConsoleColors.BRIGHT_BLUE));
            
        } catch (Exception e) {
            System.out.println(ConsoleColors.colorize("✗ Error retrieving statistics: " + e.getMessage(), ConsoleColors.RED_BOLD));
        }
    }

    /**
     * Get integer input with error handling
     */
    private int getIntInput(String prompt) {
        while (true) {
            try {
                printPrompt(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.colorize("✗ Invalid input. Please enter a valid number.", ConsoleColors.RED_BOLD));
            }
        }
    }

    /**
     * Get double input with error handling
     */
    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                printPrompt(prompt);
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.colorize("✗ Invalid input. Please enter a valid number.", ConsoleColors.RED_BOLD));
            }
        }
    }

    /**
     * Wait for user to press Enter
     */
    private void pressEnterToContinue() {
        System.out.println(ConsoleColors.colorize("\nPress Enter to continue...", ConsoleColors.YELLOW_BOLD));
        scanner.nextLine();
    }

    private void printPrompt(String prompt) {
        System.out.print(ConsoleColors.colorize(prompt, ConsoleColors.CYAN_BOLD));
    }
}
