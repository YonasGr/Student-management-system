package com.studentmanagement.model;

/**
 * Course class representing a course with grade information
 */
public class Course {
    private String courseCode;
    private String courseName;
    private int credits;
    private double grade; // Grade in percentage (0-100)

    /**
     * Constructor for creating a course
     */
    public Course(String courseCode, String courseName, int credits, double grade) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        setGrade(grade);
    }

    // Getters
    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public double getGrade() {
        return grade;
    }

    // Setters
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCredits(int credits) {
        if (credits > 0) {
            this.credits = credits;
        } else {
            throw new IllegalArgumentException("Credits must be positive");
        }
    }

    public void setGrade(double grade) {
        if (grade >= 0 && grade <= 100) {
            this.grade = grade;
        } else {
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }
    }

    /**
     * Convert percentage grade to grade point (4.0 scale)
     */
    public double getGradePoint() {
        if (grade >= 90) return 4.0;
        else if (grade >= 80) return 3.0;
        else if (grade >= 70) return 2.0;
        else if (grade >= 60) return 1.0;
        else return 0.0;
    }

    /**
     * Get letter grade
     */
    public String getLetterGrade() {
        if (grade >= 90) return "A";
        else if (grade >= 80) return "B";
        else if (grade >= 70) return "C";
        else if (grade >= 60) return "D";
        else return "F";
    }

    @Override
    public String toString() {
        return String.format("%s - %s (Credits: %d, Grade: %.1f%%, Letter: %s)", 
                            courseCode, courseName, credits, grade, getLetterGrade());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return courseCode.equals(course.courseCode);
    }

    @Override
    public int hashCode() {
        return courseCode.hashCode();
    }
}
