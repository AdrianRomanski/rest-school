package adrianromanski.restschool.domain.enums;

public enum LastName {

    SMITH("Smith"), JOHNSON("Johnson"), WILLIAMS("William"),
    JONES("Jones"), GONZALES("Gonzales"), DAVIS("Davis"),
    MILLER("Miller"), WILSON("Willson"), PEREZ("Perez"),
    PARKER("Parker"), SIMMONS("Simmons"), CLARK("Clark"),
    RODRIGUEZ("Rodriguez"), COOPER("Cooper"), HENDERSON("Henderson");

    private final String lastName;

    LastName(String name) {
        this.lastName = name;
    }

    public String get() {
        return lastName;
    }
}
