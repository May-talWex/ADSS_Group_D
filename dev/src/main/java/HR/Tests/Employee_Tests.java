package HR.Tests;
import HR.Domain.*;
import HR.Domain.EmployeeTypes.Cashier;

import java.time.LocalDate;

public class Employee_Tests {
    public static void main(String[] args) {
    Branch testBranch = new Branch("Test Branch", "123 Test St.");
    Employee employee = new Employee(1, "John Doe", "john.doe@example.com", new BankAccount(12345, 67890, 54321), testBranch, new Salary(50000));
        try {
            assert employee.getEmployeeId() == 1;
            System.out.println("Employee ID test passed.");
        } catch (AssertionError e) {
            System.out.println("Employee ID test failed.");
        }
        try {
            assert employee.getName().equals("John Doe");
            System.out.println("Employee name test passed.");
        } catch (AssertionError e) {
            System.out.println("Employee name test failed.");
        }
    try {
        assert employee.getEmail().equals("john.doe@example.com");
        System.out.println("Employee email test passed.");
    } catch (AssertionError e) {
        System.out.println("Employee email test failed.");
    }
        try {
            assert employee.getBranch() == testBranch;
            System.out.println("Employee branch test passed.");
        } catch (AssertionError e) {
            System.out.println("Employee branch test failed.");
        }

        try {
            assert employee.getDateOfEmployment().equals(LocalDate.now());
            System.out.println("Employee date of employment test passed.");
        } catch (AssertionError e) {
            System.out.println("Employee date of employment test failed.");
        }

        try {
            employee.setName("Jane Doe");
            assert employee.getName().equals("Jane Doe");
            System.out.println("Set employee name test passed.");
        } catch (AssertionError e) {
            System.out.println("Set employee name test failed.");
        }

        try {
            employee.setEmail("jane.doe@example.com");
            assert employee.getEmail().equals("jane.doe@example.com");
            System.out.println("Set employee email test passed.");
        } catch (AssertionError e) {
            System.out.println("Set employee email test failed.");
        }

        try {
            BankAccount newBankAccount = new BankAccount(321, 654, 987);
            employee.setBankAccount(newBankAccount);
            assert employee.getBankAccount() == newBankAccount;
            System.out.println("Set employee bank account test passed.");
        } catch (AssertionError e) {
            System.out.println("Set employee bank account test failed.");
        }

        try {
            Branch newBranch = new Branch("New Branch", "New Address");
            employee.setBranch(newBranch);
            assert employee.getBranch() == newBranch;
            System.out.println("Set employee branch test passed.");
        } catch (AssertionError e) {
            System.out.println("Set employee branch test failed.");
        }

        try {
            EmployeeType cashier = new Cashier();
            employee.addPossiblePosition(cashier);
            assert employee.getPossiblePositions().contains(cashier);
            System.out.println("Add possible position test passed.");
        } catch (AssertionError | Exception e) {
            System.out.println("Add possible position test failed.");
        }

        try {
            Salary newSalary = new Salary(2000.0f, LocalDate.now(), null);
            employee.setCurrentSalary(newSalary);
            assert employee.getCurrentSalary() == newSalary;
            System.out.println("Set current salary test passed.");
        } catch (AssertionError e) {
            System.out.println("Set current salary test failed.");
        }
    }}
