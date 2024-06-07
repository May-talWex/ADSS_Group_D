package HR.Domain.Exceptions;

public class ShiftAlreadyExists extends Exception {
    public ShiftAlreadyExists(String message) {
        super("Shift already exists. Message: " + message);
    }
}
