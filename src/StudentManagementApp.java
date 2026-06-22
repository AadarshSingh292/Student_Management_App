import java.util.List;
import java.util.Scanner;

public class StudentManagementApp {
    private final Scanner scanner;
    private final StudentManager manager;

    public StudentManagementApp() {
        scanner = new Scanner(System.in);
        manager = new StudentManager();
        addSampleData();
    }

    public static void main(String[] args) {
        new StudentManagementApp().run();
    }

    private void run() {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ", 0, 8);

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    searchStudentById();
                    break;
                case 4:
                    searchStudentsByName();
                    break;
                case 5:
                    updateStudent();
                    break;
                case 6:
                    deleteStudent();
                    break;
                case 7:
                    addOrUpdateMarks();
                    break;
                case 8:
                    viewReportCard();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using Student Management System.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n==============================");
        System.out.println(" Student Management System");
        System.out.println("==============================");
        System.out.println("1. Add student");
        System.out.println("2. View all students");
        System.out.println("3. Search student by ID");
        System.out.println("4. Search students by name");
        System.out.println("5. Update student");
        System.out.println("6. Delete student");
        System.out.println("7. Add or update marks");
        System.out.println("8. View report card");
        System.out.println("0. Exit");
    }

    private void addStudent() {
        System.out.println("\nAdd Student");
        String id = readRequiredText("Student ID: ");

        if (manager.findById(id) != null) {
            System.out.println("A student with this ID already exists.");
            return;
        }

        try {
            Student student = readStudentDetails(id, null);

            if (manager.addStudent(student)) {
                System.out.println("Student added successfully.");
            } else {
                System.out.println("Could not add student.");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    private void viewAllStudents() {
        printStudentTable(manager.getAllStudents());
    }

    private void searchStudentById() {
        String id = readRequiredText("\nEnter student ID: ");
        Student student = manager.findById(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        printStudentTableHeader();
        System.out.println(student.toTableRow());
    }

    private void searchStudentsByName() {
        String keyword = readRequiredText("\nEnter name keyword: ");
        printStudentTable(manager.searchByName(keyword));
    }

    private void updateStudent() {
        String id = readRequiredText("\nEnter student ID to update: ");
        Student existingStudent = manager.findById(id);

        if (existingStudent == null) {
            System.out.println("Student not found.");
            return;
        }

        try {
            Student updatedStudent = readStudentDetails(existingStudent.getId(), existingStudent);

            boolean updated = manager.updateStudent(
                    existingStudent.getId(),
                    updatedStudent.getName(),
                    updatedStudent.getAge(),
                    updatedStudent.getEmail(),
                    updatedStudent.getCourse(),
                    updatedStudent.getSemester(),
                    updatedStudent.getStatus()
            );

            if (updated) {
                System.out.println("Student updated successfully.");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    private void deleteStudent() {
        String id = readRequiredText("\nEnter student ID to delete: ");

        if (manager.deleteById(id)) {
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private void addOrUpdateMarks() {
        String id = readRequiredText("\nEnter student ID: ");

        if (manager.findById(id) == null) {
            System.out.println("Student not found.");
            return;
        }

        String subject = readRequiredText("Subject name: ");
        double score = readDouble("Score out of 100: ", 0, 100);

        if (manager.addOrUpdateMark(id, subject, score)) {
            System.out.println("Marks saved successfully.");
        }
    }

    private void viewReportCard() {
        String id = readRequiredText("\nEnter student ID: ");
        Student student = manager.findById(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println(student.getReportCard());
    }

    private Student readStudentDetails(String id, Student existingStudent) {
        String name = readTextWithDefault("Name", existingStudent == null ? null : existingStudent.getName());
        int age = readIntWithDefault("Age", existingStudent == null ? null : existingStudent.getAge(), 1, 120);
        String email = readTextWithDefault("Email", existingStudent == null ? null : existingStudent.getEmail());
        String course = readTextWithDefault("Course", existingStudent == null ? null : existingStudent.getCourse());
        int semester = readIntWithDefault("Semester", existingStudent == null ? null : existingStudent.getSemester(), 1, 12);

        Student student = new Student(id, name, age, email, course, semester);

        if (existingStudent != null) {
            student.setStatus(readStatus(existingStudent.getStatus()));
        }

        return student;
    }

    private StudentStatus readStatus(StudentStatus currentStatus) {
        StudentStatus[] statuses = StudentStatus.values();

        System.out.println("Status [" + currentStatus + "]:");

        for (int index = 0; index < statuses.length; index++) {
            System.out.println((index + 1) + ". " + statuses[index]);
        }

        while (true) {
            System.out.print("Choose status or press Enter to keep current: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return currentStatus;
            }

            try {
                int choice = Integer.parseInt(input);

                if (choice >= 1 && choice <= statuses.length) {
                    return statuses[choice - 1];
                }
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a number.");
            }

            System.out.println("Please choose a valid status.");
        }
    }

    private void printStudentTable(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        printStudentTableHeader();

        for (Student student : students) {
            System.out.println(student.toTableRow());
        }
    }

    private void printStudentTableHeader() {
        System.out.printf(
                "%-10s %-20s %-4s %-24s %-16s %-8s %-10s %-8s %-5s%n",
                "ID",
                "Name",
                "Age",
                "Email",
                "Course",
                "Sem",
                "Status",
                "Average",
                "Grade"
        );

        System.out.println("--------------------------------------------------------------------------------------------------------------");
    }

    private String readRequiredText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("This field is required.");
        }
    }

    private String readTextWithDefault(String label, String defaultValue) {
        if (defaultValue == null) {
            return readRequiredText(label + ": ");
        }

        System.out.print(label + " [" + defaultValue + "]: ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            return defaultValue;
        }

        return input;
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);

                if (value >= min && value <= max) {
                    return value;
                }

                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private int readIntWithDefault(String label, Integer defaultValue, int min, int max) {
        if (defaultValue == null) {
            return readInt(label + ": ", min, max);
        }

        while (true) {
            System.out.print(label + " [" + defaultValue + "]: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return defaultValue;
            }

            try {
                int value = Integer.parseInt(input);

                if (value >= min && value <= max) {
                    return value;
                }

                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                double value = Double.parseDouble(input);

                if (value >= min && value <= max) {
                    return value;
                }

                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private void addSampleData() {
        Student student = new Student("S001", "Aadarsh Singh", 23, "aadi@example.com", "Computer Science", 3);
        student.addOrUpdateMark("Java", 92);
        student.addOrUpdateMark("Database", 86);
        manager.addStudent(student);

        Student secondStudent = new Student("S002", "Atharva Sharma", 22, "athrv@example.com", "Information Tech", 4);
        secondStudent.addOrUpdateMark("Java", 78);
        secondStudent.addOrUpdateMark("Networking", 81);
        manager.addStudent(secondStudent);
    }
}
