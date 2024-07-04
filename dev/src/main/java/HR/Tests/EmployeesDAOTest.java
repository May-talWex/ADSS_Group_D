package HR.Tests;

import HR.Data.EmployeesDAO;
import HR.Data.SQLiteConnection;
import HR.Domain.BankAccount;
import HR.Domain.Branch;
import HR.Domain.Employee;
import HR.Domain.Salary;
import HR.Domain.EmployeeTypes.*;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeesDAOTest {
    private EmployeesDAO employeesDAO;
    private Connection connection;

    @BeforeEach
    public void setUp() {
        connection = SQLiteConnection.getConnection();
        employeesDAO = new EmployeesDAO(connection);

        // Clean up database before each test
        cleanDatabase();
    }

    private void cleanDatabase() {
        try {
            connection.prepareStatement("DELETE FROM EmployeeRoles").executeUpdate();
            connection.prepareStatement("DELETE FROM Employees").executeUpdate();
            connection.prepareStatement("DELETE FROM BankAccounts").executeUpdate();
            connection.prepareStatement("DELETE FROM Salaries").executeUpdate();
            connection.prepareStatement("DELETE FROM DriverLicenses").executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddAndGetEmployee() throws Exception {
        int employeeId = 1;
        Employee employee = createTestEmployee(employeeId);

        employeesDAO.addEmployeeToDatabase(employee);

        Employee retrievedEmployee = employeesDAO.getEmployeeById(employeeId);
        assertNotNull(retrievedEmployee);
        assertEquals(employee.getName(), retrievedEmployee.getName());
    }

    @Test
    public void testRemoveEmployee() throws Exception {
        int employeeId = 2;
        Employee employee = createTestEmployee(employeeId);

        employeesDAO.addEmployeeToDatabase(employee);

        employeesDAO.removeEmployeeFromDatabase(employeeId);

        if (employeesDAO.getEmployeeById(employeeId) != null) {
            fail("Employee was not removed from database");
        }
    }
    @Test
    public void testGetAllEmployees() throws Exception {

        int employeeId1 = 3;
        Employee employee1 = createTestEmployee(employeeId1);

        int employeeId2 = 4;
        Employee employee2 = createTestEmployee(employeeId2);
        List<Employee> EmployeesBeforeChange = employeesDAO.getAllEmployees(employee1.getBranch().getBranchId());
        employeesDAO.addEmployeeToDatabase(employee1);
        employeesDAO.addEmployeeToDatabase(employee2);

        List<Employee> employees = employeesDAO.getAllEmployees(employee1.getBranch().getBranchId());
        assertEquals(EmployeesBeforeChange.size() + 2, employees.size());
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        int employeeId = 5;
        Employee employee = createTestEmployee(employeeId);

        employeesDAO.addEmployeeToDatabase(employee);

        employee.setName("Updated Name");
        employeesDAO.updateEmployee(employee);

        Employee retrievedEmployee = employeesDAO.getEmployeeById(employeeId);
        assertEquals("Updated Name", retrievedEmployee.getName());
    }

    @Test
    public void testaddRolesToDatabase() throws Exception {
        int employeeId = 6;
        Employee employee = createTestEmployee(employeeId);
        employeesDAO.addEmployeeToDatabase(employee);
        employee.addPossiblePosition(new ShiftManager());
        employeesDAO.addRoleToEmployee(employeeId, "ShiftManager");
        Employee retrievedEmployee = EmployeesDAO.getEmployeeById(employeeId);
        assertEquals(2, retrievedEmployee.getPossiblePositions().size());
    }


    private Employee createTestEmployee(int employeeId) throws Exception {
        BankAccount bankAccount = new BankAccount(123, 456, 789);
        Salary salary = new Salary(5000.0f, LocalDate.now(), null);
        Branch branch = new Branch(1, "Main Branch", "123 Main St");

        Employee employee = new Employee(employeeId, "Test Employee", "test@example.com", bankAccount, branch, salary);
        employee.setDateOfEmployment(LocalDate.now());
        employee.addPossiblePosition(new Cashier());

        return employee;
    }
}
