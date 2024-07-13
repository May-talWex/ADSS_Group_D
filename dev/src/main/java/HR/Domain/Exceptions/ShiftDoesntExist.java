package HR.Domain.Exceptions;

public class ShiftDoesntExist extends Exception {
    public ShiftDoesntExist(String message) {
        super("Shift does not exist. Message: " + message);
    }
}
