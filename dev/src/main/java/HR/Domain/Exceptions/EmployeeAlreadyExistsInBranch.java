package HR.Domain.Exceptions;

public class EmployeeAlreadyExistsInBranch extends Exception {
    public EmployeeAlreadyExistsInBranch(String message) {
        super("Employee already exists in branch. Message: " + message);
    }
}
