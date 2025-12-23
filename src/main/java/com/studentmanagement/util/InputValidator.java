package com.studentmanagement.util;

/**
 * Input validation utility class
 */
public class InputValidator {

    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    /**
     * Validate age range
     */
    public static boolean isValidAge(int age) {
        return age > 0 && age < 150;
    }

    /**
     * Validate name (only letters and spaces)
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return name.matches("^[a-zA-Z\\s]+$");
    }

    /**
     * Validate course code format
     */
    public static boolean isValidCourseCode(String courseCode) {
        if (courseCode == null || courseCode.trim().isEmpty()) {
            return false;
        }
        return courseCode.matches("^[A-Z]{2,4}\\d{3}$");
    }

    /**
     * Validate grade range (0-100)
     */
    public static boolean isValidGrade(double grade) {
        return grade >= 0 && grade <= 100;
    }

    /**
     * Validate credits (positive integer)
     */
    public static boolean isValidCredits(int credits) {
        return credits > 0 && credits <= 10;
    }

    /**
     * Validate string is not empty
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
