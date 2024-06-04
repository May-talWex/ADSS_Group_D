package HR.Domain;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class BranchController {
    public static void UpdateEmployee(Branch branch) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the employee you want to update: ");
        int id = scanner.nextInt();
        Employee employee = branch.getWorkerById(id);
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }
        boolean updateDone = false;
        while (!updateDone) {
            System.out.println("Update Employee Information:");
            System.out.println("1. Update Name");
            System.out.println("2. Update Email");
            System.out.println("3. Update Bank Account");
            System.out.println("4. Update Salary");
            System.out.println("5. Done");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter new name: ");
                    String newName = scanner.next();
                    employee.setName(newName);
                    break;
                case 2:
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.next();
                    employee.setEmail(newEmail);
                    break;
                case 3:
                    System.out.println("Enter bankNumber: ");
                    int bankNumber = Integer.parseInt(System.console().readLine());
                    System.out.println("Enter branchNumber: ");
                    int branchNumber = Integer.parseInt(System.console().readLine());
                    System.out.println("Enter accountNumber: ");
                    int accountNumber = Integer.parseInt(System.console().readLine());
                    BankAccount bankAccount = new BankAccount(bankNumber, accountNumber, branchNumber);
                    employee.setBankAccount(bankAccount);
                    break;
                case 4:
                    System.out.println("Enter workerSalary: ");
                    float workerSalary = Float.parseFloat(System.console().readLine());
                    Salary salary = new Salary(workerSalary, LocalDate.now());
                    employee.setSalary(salary);
                    break;
                case 5:
                    updateDone = true;
                    System.out.println("Employee information update complete.");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public Branch createBranch() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter branch name: ");
        String branchName = scanner.nextLine();
        System.out.println("Enter branch address: ");
        String branchAddress = scanner.nextLine();
        Branch branch = new Branch(branchName, branchAddress);
        System.out.println("Branch " + branchName + " created successfully");
        return branch;
    }
    public static void removeEmployee(Branch branch) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the employee you want to remove: ");
        int id = scanner.nextInt();
        Employee employee = branch.getWorkerById(id);
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }
        branch.removeEmployee(employee);
    }

    public static void printEmployees(Branch branch) {
        List<Employee> workers = branch.getWorkers();
        for (Employee worker : workers) {
            System.out.println(worker.getEmployeeId() + " - " + worker.getName() + " - " + worker.printPossiblePositions());
        }
    }


}
