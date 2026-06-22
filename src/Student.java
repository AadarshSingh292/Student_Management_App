import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student extends Person {
    private String course;
    private int semester;
    private StudentStatus status;
    private final List<SubjectMark> marks;

    public Student(String id, String name, int age, String email, String course, int semester) {
        super(id, name, age, email);
        this.marks = new ArrayList<SubjectMark>();
        this.status = StudentStatus.ACTIVE;
        setCourse(course);
        setSemester(semester);
    }

    @Override
    public String getRole() {
        return "Student";
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        if (isBlank(course)) {
            throw new IllegalArgumentException("Course cannot be empty.");
        }

        this.course = course.trim();
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        if (semester < 1 || semester > 12) {
            throw new IllegalArgumentException("Semester must be between 1 and 12.");
        }

        this.semester = semester;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be empty.");
        }

        this.status = status;
    }

    public List<SubjectMark> getMarks() {
        return Collections.unmodifiableList(marks);
    }

    public void addOrUpdateMark(String subjectName, double score) {
        if (isBlank(subjectName)) {
            throw new IllegalArgumentException("Subject name cannot be empty.");
        }

        String normalizedSubjectName = subjectName.trim();
        for (SubjectMark mark : marks) {
            if (mark.getSubjectName().equalsIgnoreCase(normalizedSubjectName)) {
                mark.setScore(score);
                return;
            }
        }

        marks.add(new SubjectMark(normalizedSubjectName, score));
    }

    public double getAverageScore() {
        if (marks.isEmpty()) {
            return 0;
        }

        double total = 0;
        for (SubjectMark mark : marks) {
            total += mark.getScore();
        }

        return total / marks.size();
    }

    public String getGrade() {
        if (marks.isEmpty()) {
            return "N/A";
        }

        double average = getAverageScore();
        if (average >= 90) {
            return "A+";
        } else if (average >= 80) {
            return "A";
        } else if (average >= 70) {
            return "B";
        } else if (average >= 60) {
            return "C";
        } else if (average >= 50) {
            return "D";
        }

        return "F";
    }

    public String toTableRow() {
        return String.format(
                "%-10s %-20s %-4d %-24s %-16s %-8d %-10s %-8.2f %-5s",
                getId(),
                trimForTable(getName(), 20),
                getAge(),
                trimForTable(getEmail(), 24),
                trimForTable(course, 16),
                semester,
                status,
                getAverageScore(),
                getGrade());
    }

    public String getReportCard() {
        StringBuilder report = new StringBuilder();
        report.append("\nStudent Report Card\n");
        report.append("-------------------\n");
        report.append("ID       : ").append(getId()).append("\n");
        report.append("Name     : ").append(getName()).append("\n");
        report.append("Age      : ").append(getAge()).append("\n");
        report.append("Email    : ").append(getEmail()).append("\n");
        report.append("Course   : ").append(course).append("\n");
        report.append("Semester : ").append(semester).append("\n");
        report.append("Status   : ").append(status).append("\n");
        report.append("\nMarks\n");
        report.append("-----\n");

        if (marks.isEmpty()) {
            report.append("No marks added yet.\n");
        } else {
            report.append(String.format("%-24s %-8s%n", "Subject", "Score"));
            for (SubjectMark mark : marks) {
                report.append(String.format("%-24s %-8.2f%n", mark.getSubjectName(), mark.getScore()));
            }
        }

        report.append("\nAverage  : ").append(String.format("%.2f", getAverageScore())).append("\n");
        report.append("Grade    : ").append(getGrade()).append("\n");
        return report.toString();
    }

    private String trimForTable(String value, int width) {
        if (value.length() <= width) {
            return value;
        }

        return value.substring(0, width - 3) + "...";
    }
}