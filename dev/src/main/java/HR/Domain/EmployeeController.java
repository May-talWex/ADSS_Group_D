package HR.Domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import HR.Domain.EmployeeTypes.*;

import java.time.LocalDate;
import java.util.Scanner;

public class EmployeeController {
    @JsonCreator
    public Employee JSONtoClass() {
        return null;
    }

    public EmployeeController() {

    }

    public Employee createEmployee(Branch branch) throws Exception {
        System.out.println("Creating Employee");
        System.out.println("Enter workerId: ");
        Scanner scanner = new Scanner(System.in);
        int workerId = scanner.nextInt();
        if (branch.getWorkerById(workerId) != null) {
            System.out.println("Employee with ID " + workerId + " already exists.");
            return null;
        }
        scanner.nextLine();
        try {
            System.out.println("Enter workerName: ");
            String workerName = scanner.nextLine();
            System.out.println("Enter workerEmail: ");
            String workerEmail = scanner.nextLine();
            System.out.println("Enter bankNumber: ");
            int bankNumber = scanner.nextInt();
            System.out.println("Enter branchNumber: ");
            int branchNumber = scanner.nextInt();
            System.out.println("Enter accountNumber: ");
            int accountNumber = scanner.nextInt();
            BankAccount bankAccount = new BankAccount(bankNumber, accountNumber, branchNumber);
            System.out.println("Enter workerSalary: ");
            float workerSalary = scanner.nextFloat();
            Salary salary = new Salary(workerSalary, LocalDate.now());
            Employee employee = new Employee(workerId, workerName, workerEmail, bankAccount, branch, salary);
            System.out.println("Employee " + workerName + " created successfully");
            return employee;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        // TODO: Add exception if employee already exists
    }

    public void addRoleToEmployee(Employee employee) {
        System.out.println("Choose a role to add to the employee: ");
        Scanner scanner = new Scanner(System.in);
        printRoleMenu();
        int role = scanner.nextInt();
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a role to remove from the employee: ");
        printRoleMenu();
        int role = scanner.nextInt();
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
