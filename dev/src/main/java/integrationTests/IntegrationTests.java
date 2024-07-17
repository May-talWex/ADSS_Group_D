package integrationTests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import HR.Data.BranchDAO;
import HR.Data.EmployeesDAO;
import HR.Data.CreateStubEmployeesData;
import HR.Domain.*;
import HR.Domain.EmployeeTypes.StorageEmployee;
import HR.Presentation.HRMainMenu;
import HR.Presentation.Main_Menu;
import Inventory.ServiceLayer.ServiceController;
import Inventory.DataLayer.ItemDAO;
import Inventory.DomainLayer.Item;

import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class IntegrationTests {
    private EmployeesDAO employeesDAO;
    private Employee employee;
    private ServiceController serviceController;
    private ItemDAO itemDAO;
    private Branch branch;

    @Before
    public void setUp() throws Exception {
        employeesDAO = new EmployeesDAO();
        serviceController = new ServiceController();
        itemDAO = new ItemDAO();
        BranchDAO branchDAO = new BranchDAO();
        BankAccount bankAccount = new BankAccount(0, 0, 0);
        Salary salary = new Salary(0);
        Branch branch = branchDAO.getBranchFromDatabase(1); // Default branch with ID 1
        employee = new Employee(1009, "John Doe", "john.doe@example.com", bankAccount, branch, salary);
        employeesDAO.addEmployeeToDatabase(employee);
    }

    @Test
    public void testFullWorkflow() throws Exception {
        // Step 1: Assign Storage Worker role
        try {
            EmployeeType storageEmployee = new StorageEmployee();
            employee.addPossiblePosition(storageEmployee);
            employeesDAO.addRolesToDatabase(employee);
            Employee updatedEmployee = EmployeesDAO.getEmployeeById(1009);
            assertTrue(updatedEmployee.hasRole(new StorageEmployee()));
            System.out.println("Assign Storage Worker test passed.");
        } catch (Exception e) {
            System.out.println("Assign Storage Worker test failed: " + e.getMessage());
            assertTrue(false);
            return;
        }

        // Simulate user input for login and adding item
        String input = "1009\n1\n1\n3\nMilk\n1001\nyes\nyes\n2025-01-01\nDP\nMLK1\n5\n7\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Step 2: Employee logs in and selects storage management menu
        try {
            HRMainMenu.DisplayHRMainMenu();
            System.out.println("Full workflow test passed.");
        } catch (Exception e) {
            System.out.println("Full workflow test failed: " + e.getMessage());
            assertTrue(false);
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
            assertTrue(false);
        }
    }
}
