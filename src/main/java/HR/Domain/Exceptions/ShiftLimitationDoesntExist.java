package HR.Domain.Exceptions;

public class ShiftLimitationDoesntExist extends Exception {
    public ShiftLimitationDoesntExist(String message) {
        super("Shift limitation doesn't exist. Message: " + message);
    }
}
