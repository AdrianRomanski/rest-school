package adrianromanski.restschool.exceptions;

public class UpdateBeforeInitializationException extends RuntimeException{

    public UpdateBeforeInitializationException() {
    }

    public UpdateBeforeInitializationException(Class clss) {
        super(clss.getSimpleName() + " have to be initialized before updating");
    }
}
