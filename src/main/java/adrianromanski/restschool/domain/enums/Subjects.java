package adrianromanski.restschool.domain.enums;

public enum Subjects {

    MATHEMATICS("Mathematics"), BIOLOGY("Biology"), PHYSICS("Physics"),
    CHEMISTRY("Chemistry"), COMPUTER_SCIENCE("Computer Science"),
    PHILOSOPHY("Philosophy"), ENGLISH("English"), HISTORY("History");

    private final String name;

    Subjects(String name) {
        this.name = name;
    }

    public String get() {
        return name;
    }
}
