public abstract class Person {
    private final String id;
    private String name;
    private int age;
    private String email;

    protected Person(String id, String name, int age, String email) {
        if (isBlank(id)) {
            throw new IllegalArgumentException("ID cannot be empty.");
        }

        this.id = id.trim().toUpperCase();
        setName(name);
        setAge(age);
        setEmail(email);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (isBlank(name)) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        this.name = name.trim();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 1 || age > 120) {
            throw new IllegalArgumentException("Age must be between 1 and 120.");
        }

        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (isBlank(email) || !email.contains("@")) {
            throw new IllegalArgumentException("Enter a valid email address.");
        }

        this.email = email.trim();
    }

    public abstract String getRole();

    protected static boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }
}