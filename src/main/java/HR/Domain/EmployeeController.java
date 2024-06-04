package HR.Domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import HR.Domain.EmployeeTypes.*;

import java.time.LocalDate;

public class EmployeeController {
    @JsonCreator
    public Employee JSONtoClass() {
        return null;
    }

    public EmployeeController() {

    }

    public Employee createEmployee(Branch branch) {
        System.out.println("Creating Employee");
        System.out.println("Enter workerId: ");
        int workerId = Integer.parseInt(System.console().readLine());
        System.out.println("Enter workerName: ");
        String workerName = System.console().readLine();
        System.out.println("Enter workerEmail: ");
        String workerEmail = System.console().readLine();
        System.out.println("Enter bankNumber: ");
        int bankNumber = Integer.parseInt(System.console().readLine());
        System.out.println("Enter branchNumber: ");
        int branchNumber = Integer.parseInt(System.console().readLine());
        System.out.println("Enter accountNumber: ");
        int accountNumber = Integer.parseInt(System.console().readLine());
        BankAccount bankAccount = new BankAccount(bankNumber, accountNumber, branchNumber);
        System.out.println("Enter workerSalary: ");
        float workerSalary = Float.parseFloat(System.console().readLine());
        Salary salary = new Salary(workerSalary, LocalDate.now());
        Employee employee = new Employee(workerId, workerName, workerEmail, bankAccount, branch, salary);
        System.out.println("Employee " + workerName + " created successfully");
        return employee;

        // TODO: Add exception if employee already exists
    }

    public void addRoleToEmployee(Employee employee) {
        System.out.println("Choose a role to add to the employee: ");
        printRoleMenu();
        int role = Integer.parseInt(System.console().readLine());
        try {
            switch (role) {
                case 1:
                    employee.addPossiblePosition(new ShiftManager());
                    break;
                case 2:
                    employee.addPossiblePosition(new Cashier());
                    break;
                case 3:
                    employee.addPossiblePosition(new StorageEmployee());
                    break;
                case 4:
                    employee.addPossiblePosition(new DeliveryPerson());
                    break;
                case 5:
                    employee.addPossiblePosition(new HRManager());
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    public void removeRoleFromEmployee(Employee employee) {
        System.out.println("Choose a role to remove from the employee: ");
        printRoleMenu();
        int role = Integer.parseInt(System.console().readLine());
        try {
            switch (role) {
                case 1:
                    employee.removePossiblePosition(new ShiftManager());
                    break;
                case 2:
                    employee.removePossiblePosition(new Cashier());
                    break;
                case 3:
                    employee.removePossiblePosition(new StorageEmployee());
                    break;
                case 4:
                    employee.removePossiblePosition(new DeliveryPerson());
                    break;
                case 5:
                    employee.removePossiblePosition(new HRManager());
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printRoleMenu() {
        System.out.println("1. Shift Manager");
        System.out.println("2. Cashier");
        System.out.println("3. Storage Worker");
        System.out.println("4. Delivery Person");
        System.out.println("5. HR Manager");
        System.out.println("6. Exit");
    }


}
