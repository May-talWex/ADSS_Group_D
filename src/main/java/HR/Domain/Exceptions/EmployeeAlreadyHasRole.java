package HR.Domain.Exceptions;

public class EmployeeAlreadyHasRole extends Exception {
    public EmployeeAlreadyHasRole(String type) {
        super("Employee already has role: " + type);
    }
}
