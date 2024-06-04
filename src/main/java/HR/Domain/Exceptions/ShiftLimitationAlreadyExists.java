package HR.Domain.Exceptions;

public class ShiftLimitationAlreadyExists extends Exception {
    public ShiftLimitationAlreadyExists(String message) {
        super("Shift limitation already exists. Message: " + message);
    }
}
