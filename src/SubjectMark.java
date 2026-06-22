public class SubjectMark {
    private String subjectName;
    private double score;

    public SubjectMark(String subjectName, double score) {
        setSubjectName(subjectName);
        setScore(score);
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        if (subjectName == null || subjectName.trim().isEmpty()) {
            throw new IllegalArgumentException("Subject name cannot be empty.");
        }

        this.subjectName = subjectName.trim();
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100.");
        }

        this.score = score;
    }
}
