# Student Management System

## Overview

Student Management System is a simple console-based Java project built using pure Java and Object-Oriented Programming concepts.  
The project is used to manage student records such as student details, course, semester, status, subject marks, average marks, and grade.

This project does not use any database or external framework. All data is stored temporarily in memory using Java collections.

## Features

- Add a new student
- View all students
- Search student by ID
- Search students by name
- Update student details
- Delete a student record
- Add or update subject marks
- View student report card
- Calculate average marks
- Generate grade based on average
- Manage student status

## Project Structure

| File | Description |
|---|---|
| `Person.java` | Abstract parent class that stores common details like ID, name, age, and email |
| `Student.java` | Child class of `Person`; stores course, semester, status, marks, average, and grade |
| `SubjectMark.java` | Stores subject name and score |
| `StudentStatus.java` | Enum that stores fixed status values: `ACTIVE`, `GRADUATED`, `INACTIVE` |
| `StudentManager.java` | Handles student operations like add, search, update, delete, and marks management |
| `StudentManagementApp.java` | Main class that displays menu, takes input, and runs the application |

## How The Project Works

1. The program starts from `StudentManagementApp.java`.
2. A menu is displayed to the user.
3. The user selects an option such as add, view, search, update, or delete.
4. `StudentManagementApp` takes input from the user.
5. `StudentManager` performs the required operation.
6. Student data is stored in an `ArrayList`.
7. Marks are added using the `SubjectMark` class.
8. Average marks and grade are calculated inside the `Student` class.

