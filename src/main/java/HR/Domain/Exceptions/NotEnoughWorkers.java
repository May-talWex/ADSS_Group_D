package HR.Domain.Exceptions;

public class NotEnoughWorkers extends Exception {
    public NotEnoughWorkers(String message) {
        super("Not enough workers to fill the shift. Message: " + message);
    }
}
