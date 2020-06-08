package adrianromanski.restschool.exceptions;

public class UpdateBeforeInitializationException extends RuntimeException{

    public UpdateBeforeInitializationException() {
        super("Object have to be initialized before Updating");
    }
}
