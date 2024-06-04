package HR.Domain.Exceptions;

public class EmployeeDoesNotHaveRole extends Exception {
    public EmployeeDoesNotHaveRole(String type) {
        super("Employee does not have role: " + type);
    }
}
