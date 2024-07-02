package HR.Presentation;

import HR.Data.*;
import HR.Domain.*;
import HR.Domain.EmployeeTypes.*;
import HR.Domain.Exceptions.*;
import java.util.List;
import java.util.Scanner;

public class Main_Menu_With_DB {
    public static void main(String[] args) throws Exception {
        // Initialize the database
        SQLiteConnection.initializeDatabase();
        EmployeeController employeeController = new EmployeeController();
        ShiftController shiftController = new ShiftController();

        Scanner scanner = new Scanner(System.in);
        BranchDAO branchDAO = new BranchDAO();
        Branch branch = null;

        while (branch == null) {
            System.out.println("Enter the branch ID (default is 1): ");
            int branchId;
            try {
                branchId = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Defaulting to branch ID 1.");
                scanner.next(); // clear the invalid input
                branchId = 1;
            }
            branch = branchDAO.getBranchFromDatabase(branchId);

            if (branch == null) {
                System.out.println("Branch not found. Do you want to create a new branch? (yes/no)");
                String response = scanner.next();
                if (response.equalsIgnoreCase("yes")) {
                    branch = createNewBranch();
                    branchDAO.addBranchToDatabase(branch);
                } else {
                    System.out.println("Please enter a valid branch ID.");
                }
            }
        }

        // Initialize EmployeesDAO with the retrieved branch
        EmployeesDAO employeesDAO = new EmployeesDAO(branch);

        // Load all employees into the branch
        List<Employee> employees = employeesDAO.getAllEmployees();
        for (Employee employee : employees) {
            branch.addWorker(employee);
        }

        while (true) {
            System.out.println("Enter your ID (or -1 to exit): ");
            int id;
            try {
                id = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

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
                HRMenu(branch, employeeController, shiftController, employeesDAO);
            } else {
                NonManagerMenu(branch, id);
            }
        }
    }

    public static Branch createNewBranch() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the branch name: ");
        String branchName = scanner.nextLine();
        System.out.println("Enter the branch address: ");
        String branchAddress = scanner.nextLine();
        return new Branch(branchName, branchAddress);
    }

    public static void HRMenu(Branch branch, EmployeeController employeeController, ShiftController shiftController, EmployeesDAO employeesDAO) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int HRChoice = -1; // Initialize HRChoice
        do {
            PrintMainHRMenu();
            try {
                HRChoice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            switch (HRChoice) {
                case 1:
                    employeeMenu(branch, employeeController, employeesDAO);
                    break;
                case 2:
                    scheduleMenu(branch, shiftController);
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

    public static void employeeMenu(Branch branch, EmployeeController employeeController, EmployeesDAO employeesDAO) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int employeeChoice = -1; // Initialize employeeChoice
        do {
            PrintEmployeeMenu();
            try {
                employeeChoice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            switch (employeeChoice) {
                case 1:
                    // Add Employee logic
                    Employee temp = employeeController.createEmployee(branch);
                    try {
                        branch.addWorker(temp);
                        employeesDAO.addEmployee(temp); // Add employee to the database
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

    public static void scheduleMenu(Branch branch, ShiftController shiftController) throws NotEnoughWorkers {
        Scanner scanner = new Scanner(System.in);
        int scheduleChoice = -1; // Initialize scheduleChoice
        do {
            PrintScheduleMenu();
            try {
                scheduleChoice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }
            switch (scheduleChoice) {
                case 1:
                    // Create Shift Schedule logic
                    Shift shift = shiftController.createShift(branch);
                    ScheduleController.generateShift(branch, shift);
                    break;
                case 2:
                    // Update Shift Requirements logic
                    ScheduleController.updateShiftRequirements(branch);
                    break;
                case 3:
                    // Print Weekly Schedule logic
                    ScheduleController.printWeeklySchedule(branch);
                    break;
                case 4:
                    System.out.println("Returning to the main HR menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        } while (scheduleChoice != 4);
    }

    public static void NonManagerMenu(Branch branch, int id) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1; // Initialize choice
        do {
            PrintNonManagerMenu();
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            switch (choice) {
                case 1:
                    ShiftLimitationController.addShiftLimitation(branch.getWorkerById(id));
                    break;
                case 2:
                    ShiftLimitationController.removeShiftLimitation(branch.getWorkerById(id));
                    break;
                case 3:
                    ShiftLimitationController.printShiftLimitations(branch.getWorkerById(id));
                    break;
                case 4:
                    System.out.println("Returning to the main menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }

    public static void PrintNonManagerMenu() {
        System.out.println("Non-Manager Menu:");
        System.out.println("1. Add Shift Limitation");
        System.out.println("2. Remove Shift Limitation");
        System.out.println("3. View Shift Limitations");
        System.out.println("4. Logout");
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
        System.out.println("1. Create Shift");
        System.out.println("2. Update Shifts Requirements");
        System.out.println("3. Print Weekly Schedule");
        System.out.println("4. Return to Main HR Menu");
        System.out.print("Enter your choice: ");
    }
}
