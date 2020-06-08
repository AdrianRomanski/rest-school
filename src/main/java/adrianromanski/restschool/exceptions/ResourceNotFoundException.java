package adrianromanski.restschool.exceptions;

public class ResourceNotFoundException extends RuntimeException {


    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Long id, Class cls) {
        super(cls.getSimpleName() + " with id: " + id + " not found");
    }


    public ResourceNotFoundException(String name, Class cls) {
        super(cls.getSimpleName() + " with name: " + name + " not found");
    }

    public ResourceNotFoundException(String firstName, String lastName, Class cls) {
        super(cls.getSimpleName() + " with name: " + firstName +  " " + lastName + " not found");
    }
}
