package adrianromanski.restschool.domain.enums;

public enum FemaleName {

    EMMA("Emma"), OLIVIA("Olivia"), AVA("Ava"),
    ISABELLA("Isabella"), SOPHIA("Sophia"), CHARLOTTE("Charlotte"),
    MIA("Mia"), AMELIA("Amelia"), HARPER("Harper"),
    EVELYN("Evelyn"), ABIGAIL("Abigail"), EMILY("Emily"),
    ELIZABETH("Elizabeth"), HANNAH("Hannah"), ELLA("Ella"),
    AVERY("Avery"), SOFIA("Sofia"), CAMILA("Camila"),
    ARIA("Aria"), SCARLETT("Scarlett");

    private final String name;

    FemaleName(String name) {
        this.name = name;
    }

    public String get() {
        return name;
    }
}
