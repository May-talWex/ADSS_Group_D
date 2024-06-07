package HR.Domain.Exceptions;

public class EmployeeDoesNotExistInBranch extends Exception {
    public EmployeeDoesNotExistInBranch(String message) {
        super("Worker does not exist in branch. Message: " + message);
    }
}
