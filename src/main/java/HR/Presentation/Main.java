package HR.Presentation;

import HR.Domain.*;

import HR.Domain.EmployeeTypes.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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
            } else if (employee.hasRole(new ShiftManager())) {
                SMMenu(branch, id, employeeController, scanner);
            } else {
                NonManagerMenu(branch, id, employeeController, scanner);
            }
        }
    }

    public static void HRMenu(Branch branch, int id, EmployeeController employeeController, Scanner scanner) {
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
                    System.out.println("remove Employee:");
                    break;
                case 3:
                    System.out.println("Available Employees:");
                    break;
                case 4:
                    // Create Schedule logic
                    System.out.println("Creating Schedule...");
                    // Implement logic for creating schedule (e.g., prompt for details, call a createSchedule method)
                    break;
                case 5:
                    // Set Shift Restrictions logic
                    System.out.println("Setting Shift Restrictions...");
                    // Implement logic for setting shift restrictions (e.g., prompt for details, call a setShiftRestrictions method)
                    break;
                case 6:
                    // Get Shift Limitations logic
                    System.out.println("Getting Shift Limitations...");
                    // Implement logic for displaying shift limitations (e.g., call a getShiftLimitations method)
                    break;
                case 7:
                    System.out.println("Logging out and returning to the main menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        } while (HRchoice != 7);
    }

    public static void SMMenu(Branch branch, int id, EmployeeController employeeController, Scanner scanner) {
        int SMchoice;
        do {
            PrintSMmenu(branch, id);
            SMchoice = scanner.nextInt();
            switch (SMchoice) {
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
                    System.out.println("Available Employees:");
                    break;
                case 3:
                    // Create Schedule logic
                    System.out.println("Creating Schedule...");
                    // Implement logic for creating schedule (e.g., prompt for details, call a createSchedule method)
                    break;
                case 4:
                    // Set Shift Restrictions logic
                    System.out.println("Setting Shift Restrictions...");
                    // Implement logic for setting shift restrictions (e.g., prompt for details, call a setShiftRestrictions method)
                    break;
                case 5:
                    // Get Shift Limitations logic
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
        } while (SMchoice != 6);
    }

    public static void NonManagerMenu(Branch branch, int id, EmployeeController employeeController, Scanner scanner) {
        int choice;
        do {
            PrintNonMenegerMenu(branch, id);
            choice = scanner.nextInt();
            switch (choice) {
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
                    System.out.println("Available Employees:");
                    break;
                case 3:
                    // Create Schedule logic
                    System.out.println("Creating Schedule...");
                    // Implement logic for creating schedule (e.g., prompt for details, call a createSchedule method)
                    break;
                case 4:
                    // Set Shift Restrictions logic
                    System.out.println("Setting Shift Restrictions...");
                    // Implement logic for setting shift restrictions (e.g., prompt for details, call a setShiftRestrictions method)
                    break;
                case 5:
                    // Get Shift Limitations logic
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
        } while (choice != 6);
    }


    public static void PrintHRmenu(Branch branch, int id) {
        System.out.println("HR Manager Menu:");
        System.out.println("1. Add Employee");
        System.out.println("2. Remove Employee");
        System.out.println("3. Create Schedule");
        System.out.println("4. Set Shift Restrictions");
        System.out.println("5. Get Shift Limitations");
        System.out.println("6. Logout and return to main menu");
        System.out.print("Enter your choice: ");
    }

    public static void PrintSMmenu(Branch branch, int id) {
        System.out.println("Shift Manager Menu:");
        System.out.println("1. Add Employee");
        System.out.println("2. Remove Employee");
        System.out.println("3. Create Schedule");
        System.out.println("4. Set Shift Restrictions");
        System.out.println("5. Get Shift Limitations");
        System.out.println("6. Logout and return to main menu");
        System.out.print("Enter your choice: ");
    }

    public static void PrintNonMenegerMenu(Branch branch, int id) {
        System.out.println("Employee Menu:");
        System.out.println("1. Add Employee");
        System.out.println("2. Remove Employee");
        System.out.println("3. Create Schedule");
        System.out.println("4. Set Shift Restrictions");
        System.out.println("5. Get Shift Limitations");
        System.out.println("6. Logout and return to main menu");
        System.out.print("Enter your choice: ");
    }


}
