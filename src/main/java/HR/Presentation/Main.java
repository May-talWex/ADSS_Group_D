package HR.Presentation;

import HR.Domain.*;

import HR.Domain.EmployeeTypes.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        EmployeeController employeeController = new EmployeeController();
        BranchController branchController = new BranchController();
        Branch branch = branchController.createBranch();
        Scanner scanner = new Scanner(System.in);

        // Create Admin as HR Manager
        try {
            branch.addWorker(new Employee(0, "Admin", "admin@supermarket.com", new BankAccount(0, 0, 0), branch, new Salary(0)));
            branch.getWorkerById(0).addPossiblePosition(new HRManager());
            branch.addWorker(new Employee(1, "Admin", "admin@supermarket.com", new BankAccount(0, 0, 0), branch, new Salary(0)));
            branch.getWorkerById(1).addPossiblePosition(new ShiftManager());
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
                HRMenu(branch, id, employeeController, scanner);
            } else {
                NonManagerMenu(branch, id, employeeController, scanner);
            }
        }
    }

    public static void HRMenu(Branch branch, int id, EmployeeController employeeController, Scanner scanner) throws Exception {
        int HRchoice;
        do {
            PrintHRmenu(branch, id);
            HRchoice = scanner.nextInt();
            switch (HRchoice) {
                case 1:
                    // Add Employee logic
                    Employee temp = employeeController.createEmployee(branch);
                    try {
                        branch.addWorker(temp);
                    } catch (Exception e) {
                        System.out.println("Employee already exists in branch.");
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
                    // Create Schedule logic
                    System.out.println("Creating Schedule");
                    ScheduleController.GenerateSchedule(branch);
                    break;
                case 5:
                    // Set shifts requirements logic
                    System.out.println("Getting Shift Limitations...");
                    // Implement logic for displaying shift limitations (e.g., call a getShiftLimitations method)
                    break;
                case 6:
                    System.out.println("Logging out and returning to the main menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        } while (HRchoice != 6);
    }

    public static void NonManagerMenu(Branch branch, int id, EmployeeController employeeController, Scanner scanner) {
        int choice;
        do {
            PrintNonMenegerMenu(branch, id);
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


    public static void PrintHRmenu(Branch branch, int id) {
        System.out.println("HR Menu:");
        System.out.println("1. Add Employee");
        System.out.println("2. Remove Employee");
        System.out.println("3. Print all Employees");
        System.out.println("4. Create Schedule");
        System.out.println("5. Set Shift Requirements");
        System.out.println("6. Logout and return to main menu");
        System.out.print("Enter your choice: ");
    }


    public static void PrintNonMenegerMenu(Branch branch, int id) {
        System.out.println("Non-Manager Menu:");
        System.out.println("1. Submit Limitations");
        System.out.println("2. Remove Limitations");
        System.out.print("Enter your choice: ");
    }


}
