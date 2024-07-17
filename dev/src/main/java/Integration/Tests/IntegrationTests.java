package Integration.Tests;

import HR.Data.BranchDAO;
import HR.Data.EmployeesDAO;
import HR.Domain.*;
import HR.Domain.EmployeeTypes.StorageEmployee;
import HR.Presentation.HRMainMenu;
import Inventory.ServiceLayer.ServiceController;
import Inventory.DataLayer.ItemDAO;
import Inventory.DomainLayer.Item;

import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrationTests {
    private static EmployeesDAO employeesDAO;
    private static Employee employee;
    private static ServiceController serviceController;
    private static ItemDAO itemDAO;
    private static Branch branch;
    private static int employeeId = 1009;

    @BeforeAll
    public void setUp() {
        try {
            employeesDAO = new EmployeesDAO();
            serviceController = new ServiceController();
            itemDAO = new ItemDAO();
            BranchDAO branchDAO = new BranchDAO();
            branch = branchDAO.getBranchFromDatabase(9999); // Default branch with ID 9999
            if (branch == null) {
                branchDAO.addBranchToDatabase(new Branch(9999, "Integration Test Branch", "456 Test Ave"));
                branch = branchDAO.getBranchFromDatabase(9999); // Default branch with ID 9999
            }
            System.out.println(branch);
            assertNotNull(branch);
        } catch (Exception e) {
            System.out.println("Error setting up integration tests: " + e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @AfterAll
    public void tearDown() {
        try {
            BranchDAO branchDAO = new BranchDAO();
            // Cleanup code if needed
        } catch (Exception e) {
            System.out.println("Error tearing down integration tests: " + e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @BeforeEach
    public void resetEmployee() {
        try {
            BankAccount bankAccount = new BankAccount(0, 0, 0);
            Salary salary = new Salary(0);
            employee = employeesDAO.getEmployeeById(employeeId);
            if (employee != null) {
                employeesDAO.removeEmployeeFromDatabase(employeeId);
            }
            employee = new Employee(employeeId, "John Doe", "john.doe@example.com", bankAccount, branch, salary);
            employeesDAO.addEmployeeToDatabase(employee);
            employee = employeesDAO.getEmployeeById(employeeId);
            assertNotNull(employee);
            assertFalse(employee.hasRole(new StorageEmployee())); // Assert that the employee does not have the Storage Worker role
            System.out.println("Employee added to the DB.");
        } catch (Exception e) {
            System.out.println("Error resetting employee: " + e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testFullWorkflow() {
        // Step 1: Assign Storage Worker role
        try {
            assertNotNull(employee);
            employee.addPossiblePosition(new StorageEmployee());
            employeesDAO.addRoleToEmployee(employee.getEmployeeId(), "StorageEmployee");
            Employee updatedEmployee = employeesDAO.getEmployeeById(employeeId);
            System.out.println(updatedEmployee);
            assertTrue(updatedEmployee.hasRole(new StorageEmployee()));
            System.out.println("Assign Storage Worker test passed.");
        } catch (Exception e) {
            System.out.println("Assign Storage Worker test failed: " + e.getMessage());
            e.printStackTrace();
            fail();
            return;
        }

        // Simulate user input for login and adding item
        String input = "9999\n1009\n";//1\n1\n\n3";//\nMilk\n1001\nyes\nyes\n2025-01-01\nDP\nMLK1\n5\n7\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Step 2: Employee logs in and selects storage management menu
        try {
            HRMainMenu.DisplayHRMainMenu();
            System.out.println("Full workflow test passed.");
        } catch (Exception e) {
            System.out.println("Full workflow test failed: " + e.getMessage());
            e.printStackTrace();
            fail();
        }

        // Verify item was added
        try {
            Item item = itemDAO.getItemById("1001");
            assertNotNull(item);
            assertEquals("Milk", item.getName());
            assertEquals("1001", item.getID());
            System.out.println("Item added verification test passed.");
        } catch (Exception e) {
            System.out.println("Item added verification test failed: " + e.getMessage());
            e.printStackTrace();
            fail();
        }
    }
}
