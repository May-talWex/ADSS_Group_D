package HR.Presentation;

import HR.Domain.*;

import HR.Domain.EmployeeTypes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        boolean testing = true;
        EmployeeController employeeController = new EmployeeController();
        BranchController branchController = new BranchController();
        Branch branch;
        if (testing) {
            branch = branchController.createBranch("Branch Name", "Branch Address");
        } else {
            branch = branchController.createBranch();
        }
        Scanner scanner = new Scanner(System.in);

        // Create Admin as HR Manager
        try {
            branch.addWorker(new Employee(0, "Admin", "admin@supermarket.com", new BankAccount(0, 0, 0), branch, new Salary(0)));
            branch.getWorkerById(0).addPossiblePosition(new HRManager());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        while (true) {
            System.out.println("Enter your ID (or -1 to exit): ");
            int id = scanner.nextInt();

            if (id == -1) {
                System.out.println("Exiting the system...");
                break;
            }

            Employee employee = branch.getWorkerById(id);

            if (employee == null) {
                System.out.println("Employee not found.");
                continue;  // Loop back to ask for ID again
            }

            if (employee.hasRole(new HRManager())) {
                HRMenu(branch, employeeController);
            } else {
                NonManagerMenu(branch, id);
            }
        }
    }

    public static void HRMenu(Branch branch, EmployeeController employeeController) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int HRChoice;
        do {
            PrintMainHRMenu();
            HRChoice = scanner.nextInt();
            switch (HRChoice) {
                case 1:
                    employeeMenu(branch, employeeController);
                    break;
                case 2:
                    scheduleMenu(branch);
                    break;
                case 3:
                    System.out.println("Logging out and returning to the main menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        } while (HRChoice != 3);
    }

    public static void employeeMenu(Branch branch, EmployeeController employeeController) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int employeeChoice;
        do {
            PrintEmployeeMenu();
            employeeChoice = scanner.nextInt();
            switch (employeeChoice) {
                case 1:
                    // Add Employee logic
                    Employee temp = employeeController.createEmployee(branch);
                    try {
                        branch.addWorker(temp);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    // Remove Employee logic
                    BranchController.removeEmployee(branch);
                    break;
                case 3:
                    System.out.println("Available Employees:");
                    BranchController.printEmployees(branch);
                    break;
                case 4:
                    // Update Employee Position logic
                    try {
                        BranchController.updateEmployeePosition(branch);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Returning to the main HR menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        } while (employeeChoice != 5);
    }

    public static void scheduleMenu(Branch branch) {
        Scanner scanner = new Scanner(System.in);
        int scheduleChoice;
        do {
            PrintScheduleMenu();
            scheduleChoice = scanner.nextInt();
            switch (scheduleChoice) {
                case 1:
                    // Create Schedule logic
                    System.out.println("Creating Schedule");
                    ScheduleController.GenerateSchedule(branch);
                    break;
                case 2:
                    // Set shifts requirements logic
                    System.out.println("Getting Shift Limitations...");
                    // Implement logic for displaying shift limitations (e.g., call a getShiftLimitations method)
                    break;
                case 3:
                    System.out.println("Returning to the main HR menu... ");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        } while (scheduleChoice != 3);
    }

    public static void NonManagerMenu(Branch branch, int id) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            PrintNonManagerMenu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Submit Limitations");
                    ShiftLimitationController.addShiftLimitation(branch.getWorkerById(id));
                case 2:
                    System.out.println("Remove Limitations");
                    ShiftLimitationController.removeShiftLimitation(branch.getWorkerById(id));
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        } while (choice != 2);
    }

    public static void PrintNonManagerMenu() {
        System.out.println("Non-Manager Menu:");
        System.out.println("1. Submit Limitations");
        System.out.println("2. Remove Limitations");
        System.out.print("Enter your choice: ");
    }

    public static void PrintMainHRMenu() {
        System.out.println("Main HR Menu:");
        System.out.println("1. Employee Functions");
        System.out.println("2. Schedule and Shift Functions");
        System.out.println("3. Logout");
        System.out.print("Enter your choice: ");
    }

    public static void PrintEmployeeMenu() {
        System.out.println("Employee Menu:");
        System.out.println("1. Add Employee");
        System.out.println("2. Remove Employee");
        System.out.println("3. View Available Employees");
        System.out.println("4. Update Employee Information");
        System.out.println("5. Return to Main HR Menu");
        System.out.print("Enter your choice: ");
    }

    public static void PrintScheduleMenu() {
        System.out.println("Schedule Menu:");
        System.out.println("1. Create Schedule");
        System.out.println("2. Set Shifts Requirements");
        System.out.println("3. Return to Main HR Menu");

        System.out.print("Enter your choice: ");
    }


}
