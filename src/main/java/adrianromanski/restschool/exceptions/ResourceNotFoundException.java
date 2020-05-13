package adrianromanski.restschool.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Class cls) {
        super(cls.getSimpleName() + " not found");
    }

    public ResourceNotFoundException(Long id, Class cls) {
        super(cls.getSimpleName() + " with id: " + id + " not found");
    }

    public ResourceNotFoundException(String firstName, String lastName, Class cls) {
        super(cls.getSimpleName() + " with name: " + firstName +  " " + lastName + " not found");
    }
}
