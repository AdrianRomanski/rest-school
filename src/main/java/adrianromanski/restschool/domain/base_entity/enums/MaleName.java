package adrianromanski.restschool.domain.base_entity.enums;

public enum MaleName {

    LIAM("Liam"), NOAH("Noah"), WILLIAM("William"),
    JAMES("James"), SEBASTIAN("Sebastian"), OLIVER("Oliver"),
    BENJAMIN("Benjamin"), GABRIEL("Gabriel"), LUCAS("Lucas"),
    MASON("Mason"), LOGAN("Logan"), ISAAC("Isaac"),
    ETHAN("Ethan"), JACOB("Jacob"), MICHAEL("Michael"),
    DANIEL("Daniel"), HENRY("Henry"), JACKSON("Jackson");

    private final String name;

    MaleName(String name) {
        this.name = name;
    }

    public String get() {
        return name;
    }
}

