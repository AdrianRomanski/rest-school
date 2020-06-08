package adrianromanski.restschool.exceptions;

public class DeleteBeforeInitializationException extends RuntimeException{

    public DeleteBeforeInitializationException() {
        super("Object have to be initialized before Deleting");
    }
}
