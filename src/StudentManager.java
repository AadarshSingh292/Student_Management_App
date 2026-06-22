import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class StudentManager {
    private final List<Student> students;

    public StudentManager() {
        students = new ArrayList<Student>();
    }

    public boolean addStudent(Student student) {
        if (student == null || findById(student.getId()) != null) {
            return false;
        }

        students.add(student);
        return true;
    }

    public List<Student> getAllStudents() {
        return Collections.unmodifiableList(students);
    }

    public Student findById(String id) {
        if (Person.isBlank(id)) {
            return null;
        }

        String normalizedId = id.trim().toUpperCase();
        for (Student student : students) {
            if (student.getId().equals(normalizedId)) {
                return student;
            }
        }

        return null;
    }

    public List<Student> searchByName(String keyword) {
        List<Student> matches = new ArrayList<Student>();
        if (Person.isBlank(keyword)) {
            return matches;
        }

        String loweredKeyword = keyword.trim().toLowerCase();
        for (Student student : students) {
            if (student.getName().toLowerCase().contains(loweredKeyword)) {
                matches.add(student);
            }
        }

        return matches;
    }

    public boolean deleteById(String id) {
        if (Person.isBlank(id)) {
            return false;
        }

        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getId().equalsIgnoreCase(id.trim())) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    public boolean updateStudent(
            String id,
            String name,
            int age,
            String email,
            String course,
            int semester,
            StudentStatus status) {
        Student student = findById(id);
        if (student == null) {
            return false;
        }

        student.setName(name);
        student.setAge(age);
        student.setEmail(email);
        student.setCourse(course);
        student.setSemester(semester);
        student.setStatus(status);
        return true;
    }

    public boolean addOrUpdateMark(String studentId, String subjectName, double score) {
        Student student = findById(studentId);
        if (student == null) {
            return false;
        }

        student.addOrUpdateMark(subjectName, score);
        return true;
    }
}
