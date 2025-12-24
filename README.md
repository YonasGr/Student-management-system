# Student Management System 🎓

A professional console-based Java Student Management System built with Object-Oriented Programming principles, featuring comprehensive CRUD operations, automatic grade calculation, and robust error handling.

![Java](https://img.shields.io/badge/Java-8%2B-orange)
![OOP](https://img.shields.io/badge/OOP-Principles-blue)
![License](https://img.shields.io/badge/License-MIT-green)
![Console](https://img.shields.io/badge/Interface-CLI-lightgrey)

## 📋 Table of Contents
- [Features](#-features)
- [System Requirements](#-system-requirements)
- [Project Structure](#-project-structure)
- [Quick Start](#-quick-start)
- [Usage Guide](#-usage-guide)
- [Documentation](#-documentation)
- [OOP Principles](#-oop-principles-implemented)
- [Technical Details](#-technical-details)
- [Contributing](#-contributing)
- [License](#-license)

## ✨ Features

### Core Functionality
- ✅ **Admin Authentication**: Secure login system with attempt limits
- ✅ **CRUD Operations**: Complete Create, Read, Update, Delete functionality
- ✅ **Unique ID Generation**: Automatic generation of student IDs (STU1001, STU1002, etc.)
- ✅ **Selective Updates**: Update specific student fields without affecting others
- ✅ **Advanced Search**: Search by ID, name, or email with partial matching
- ✅ **Course Management**: Assign and remove courses with grade tracking
- ✅ **Automatic GPA Calculation**: Real-time GPA computation on 4.0 scale
- ✅ **Statistical Reports**: View system statistics and top performers
- ✅ **Input Validation**: Comprehensive validation with try-catch error handling
- ✅ **User-Friendly CLI**: Clear menu system with formatted output

### Data Validation
- Email format validation (RFC-compliant)
- Age range validation (1-149)
- Name validation (alphabetic characters only)
- Course code format validation (e.g., CS101, MATH201)
- Grade validation (0-100 range)
- Credits validation (1-10 range)

### Error Handling
- Try-catch blocks throughout the application
- User-friendly error messages
- Input retry mechanisms
- Graceful exception handling

## 💻 System Requirements

### Prerequisites
- **Java Development Kit (JDK)**: Version 8 or higher
- **Operating System**: Windows, Linux, or macOS
- **Terminal/Console**: Command Prompt, PowerShell, or Unix Terminal

### Verify Java Installation
```bash
java -version
javac -version
```

If Java is not installed, download from:
- [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
- [OpenJDK](https://openjdk.org/)

## 📁 Project Structure

```
Student-management-system/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── studentmanagement/
│                   ├── StudentManagementSystemApp.java    # Main CLI application
│                   ├── model/
│                   │   ├── Student.java                   # Student entity (encapsulated)
│                   │   └── Course.java                    # Course entity
│                   ├── service/
│                   │   └── StudentManager.java            # CRUD operations service
│                   └── util/
│                       ├── InputValidator.java            # Input validation utility
│                       ├── DataStore.java                 # File-based persistence (save/load)
│                       └── SessionLogger.java             # Per-session action logging
├── docs/
│   ├── 01-Requirements-Specification.md                   # SRS document
│   ├── 02-System-Design-Document.md                       # Design specifications
│   ├── 03-Test-Plan.md                                    # Testing documentation
│   └── 04-User-Manual.md                                  # User guide
├── README.md                                              # This file
└── .gitignore                                             # Git ignore file
```

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/YonasGr/Student-management-system.git
cd Student-management-system
```

### 2. Compile the Source Code

**Windows:**
```cmd
cd src\main\java
javac com\studentmanagement\*.java com\studentmanagement\model\*.java com\studentmanagement\service\*.java com\studentmanagement\util\*.java
```

**Linux/Mac:**
```bash
cd src/main/java
javac com/studentmanagement/*.java com/studentmanagement/model/*.java com/studentmanagement/service/*.java com/studentmanagement/util/*.java
```

### 3. Run the Application

**Windows:**
```cmd
java com.studentmanagement.StudentManagementSystemApp
```

**Linux/Mac:**
```bash
java com.studentmanagement.StudentManagementSystemApp
```

### 4. Login
Use the default admin credentials:
- **Username**: `admin`
- **Password**: `admin123`

### 5. Start Managing Students!
Navigate through the menu using numbers (0-9) to access different features.

## 📖 Usage Guide

### Main Menu Options

```
╔═══════════════════════════════════════════════════════════╗
║                     MAIN MENU                            ║
╠═══════════════════════════════════════════════════════════╣
║  1. Create New Student                                   ║
║  2. View All Students                                    ║
║  3. View Student Details                                 ║
║  4. Update Student Information                           ║
║  5. Delete Student                                       ║
║  6. Search Students                                      ║
║  7. Assign Course to Student                             ║
║  8. Remove Course from Student                           ║
║  9. View Statistics                                      ║
║  0. Exit                                                 ║
╚═══════════════════════════════════════════════════════════╝
```

### Common Operations

#### Creating a Student
1. Select option `1`
2. Enter first name (e.g., "John")
3. Enter last name (e.g., "Doe")
4. Enter email (e.g., "john.doe@example.com")
5. Enter age (e.g., 20)
6. System generates unique ID automatically (e.g., STU1001)

#### Assigning a Course
1. Select option `7`
2. Enter student ID (e.g., STU1001)
3. Enter course code (e.g., CS101)
4. Enter course name (e.g., "Introduction to Programming")
5. Enter credits (e.g., 3)
6. Enter grade (e.g., 85)
7. GPA is calculated automatically!

#### Searching for Students
1. Select option `6`
2. Enter any search term (ID, name, or email)
3. System displays all matching results

### GPA Calculation

The system uses a 4.0 GPA scale:
- **A** (90-100): 4.0 grade points
- **B** (80-89): 3.0 grade points
- **C** (70-79): 2.0 grade points
- **D** (60-69): 1.0 grade points
- **F** (0-59): 0.0 grade points

**Formula:**
```
GPA = Sum of (Grade Points × Credits) / Total Credits
```

**Example:**
- CS101: 3 credits, 90% grade (4.0 points)
- MATH201: 4 credits, 85% grade (3.0 points)
- GPA = (4.0×3 + 3.0×4) / (3+4) = 24/7 = **3.43**

## 📚 Documentation

Comprehensive SDLC documentation is provided in the `docs/` folder:

### 1. Requirements Specification (`01-Requirements-Specification.md`)
- Functional requirements
- Non-functional requirements
- Use cases and acceptance criteria
- Input validation rules

### 2. System Design Document (`02-System-Design-Document.md`)
- System architecture
- Class diagrams and relationships
- Design patterns used
- Algorithm descriptions
- Error handling strategy

### 3. Test Plan (`03-Test-Plan.md`)
- 50+ comprehensive test cases
- Unit, integration, and system tests
- Test execution procedures
- Defect management guidelines

### 4. User Manual (`04-User-Manual.md`)
- Installation guide
- Feature walkthroughs with examples
- Troubleshooting section
- FAQ and best practices

## 🎯 OOP Principles Implemented

### 1. Encapsulation
- **Student Class**: All fields are private with public getters/setters
- **Course Class**: Protected attributes with controlled access
- Defensive copying for collections

```java
public class Student {
    private String studentId;      // Private field
    private String firstName;      // Private field
    
    public String getFirstName() { // Public getter
        return firstName;
    }
    
    public void setFirstName(String firstName) { // Public setter with validation
        this.firstName = firstName;
    }
}
```

### 2. Abstraction
- **StudentManager**: Abstracts complex CRUD logic
- **InputValidator**: Hides validation complexity
- Clean interfaces for client code

### 3. Modularity
- Separate packages for different concerns:
  - `model`: Data entities
  - `service`: Business logic
  - `util`: Utility functions

### 4. Single Responsibility Principle
- Each class has one clear purpose
- Student manages student data
- StudentManager handles CRUD operations
- InputValidator handles validation only

### 5. Data Hiding
- Private fields prevent direct access
- Controlled modification through methods
- Validation in setters ensures data integrity

## 🔧 Technical Details

### Classes and Responsibilities

#### `Student` (Model)
- Encapsulates student data
- Manages course enrollment
- Calculates GPA automatically
- **Key Methods**: `addCourse()`, `removeCourse()`, `calculateGPA()`

#### `Course` (Model)
- Represents a course with grade
- Converts grades to GPA scale
- **Key Methods**: `getGradePoint()`, `getLetterGrade()`

#### `StudentManager` (Service)
- Performs CRUD operations
- Generates unique IDs
- Manages student collection
- **Key Methods**: `createStudent()`, `getStudent()`, `updateStudent()`, `deleteStudent()`, `searchStudents()`

#### `InputValidator` (Utility)
- Validates all user inputs
- Regular expression patterns
- **Key Methods**: `isValidEmail()`, `isValidName()`, `isValidCourseCode()`

#### `StudentManagementSystemApp` (Main)
- Console interface controller
- Menu navigation
- Error handling and user feedback
- **Key Methods**: `performLogin()`, `showMainMenu()`, various operation methods

### Design Patterns

- **Factory Pattern**: Unique ID generation in StudentManager
- **Data Access Object (DAO)**: StudentManager abstracts data access
- **MVC Variant**: Separation of model, view, and controller concerns

### Data Structures

- **HashMap<String, Student>**: O(1) student lookup by ID
- **ArrayList<Course>**: Ordered course list per student
- **Streams API**: Efficient filtering and searching

### Error Handling Strategy

```java
try {
    // Operation code
} catch (NumberFormatException e) {
    System.out.println("✗ Invalid number format");
} catch (IllegalArgumentException e) {
    System.out.println("✗ " + e.getMessage());
} catch (Exception e) {
    System.out.println("✗ Error: " + e.getMessage());
}
```

## 🧪 Testing

The system includes comprehensive testing documentation:

- **Unit Tests**: Test individual class methods
- **Integration Tests**: Test class interactions
- **System Tests**: End-to-end scenarios
- **50+ Test Cases**: Covering all features and edge cases

Run manual tests following the test plan in `docs/03-Test-Plan.md`.

## 📝 Input Formats

### Valid Input Examples

| Field | Format | Example | Validation |
|-------|--------|---------|------------|
| Name | Letters & spaces | "John Doe" | `^[a-zA-Z\s]+$` |
| Email | RFC-compliant | "user@domain.com" | `^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$` |
| Age | 1-149 | 20 | Range check |
| Course Code | 2-4 letters + 3 digits | "CS101", "MATH201" | `^[A-Z]{2,4}\d{3}$` |
| Grade | 0-100 | 85.5 | Range check |
| Credits | 1-10 | 3 | Range check |

## 🚧 Known Limitations

- **File-based Persistence Implemented**: Data is now saved between sessions using Java serialization (`data/students.dat`).
- **Session Logs**: Each admin session is logged to `data/sessions/` with actions and timestamps.
- **Single User**: Designed for one administrator at a time
- **No Database**: Uses file storage instead of a RDBMS
- **Plain Text Password**: For educational purposes only

## 🔮 Future Enhancements

- [ ] Database integration (MySQL/PostgreSQL)
- [ ] Multi-user support with different roles
- [ ] Web-based interface
- [ ] Report generation (PDF/Excel)
- [ ] Advanced analytics and visualizations
- [ ] Attendance tracking
- [ ] Grade history and trends

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

**YonasGr**
- GitHub: [@YonasGr](https://github.com/YonasGr)

## 🙏 Acknowledgments

- Built as a demonstration of OOP principles in Java
- Follows professional SDLC documentation standards
- Designed for educational purposes

## 📞 Support

For issues, questions, or suggestions:
1. Check the [User Manual](docs/04-User-Manual.md)
2. Review the [FAQ section](docs/04-User-Manual.md#7-faq)
3. Open an issue on GitHub

## 🎓 Educational Value

This project demonstrates:
- ✅ Object-Oriented Programming (OOP) concepts
- ✅ Encapsulation, abstraction, and modularity
- ✅ CRUD operations implementation
- ✅ Input validation and error handling
- ✅ Console-based user interface design
- ✅ Professional documentation standards
- ✅ Software Development Life Cycle (SDLC) best practices

---

**⭐ If you find this project helpful, please give it a star!**

---

*Made with ❤️ for education and learning*