package HR.Presentation;

import HR.Domain.*;

import HR.Domain.EmployeeTypes.*;
import HR.Domain.Exceptions.NotEnoughWorkers;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        boolean testing = true;
        ScheduleController scheduleController = new ScheduleController();
        EmployeeController employeeController = new EmployeeController();
        BranchController branchController = new BranchController();
        ShiftController shiftController = new ShiftController();
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
        AddEmployeesBulk(branch);

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
                HRMenu(branch, employeeController,scheduleController,shiftController);
            } else {
                NonManagerMenu(branch, id);
            }
        }
    }

    public static void HRMenu(Branch branch, EmployeeController employeeController,ScheduleController scheduleController,ShiftController shiftController) throws Exception {
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
                    scheduleMenu(branch,scheduleController,shiftController);
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

    public static void scheduleMenu(Branch branch, ScheduleController scheduleController, ShiftController shiftController) throws NotEnoughWorkers {
        Scanner scanner = new Scanner(System.in);
        int scheduleChoice;
        do {
            PrintScheduleMenu();
            scheduleChoice = scanner.nextInt();
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
        int choice;
        do {
            PrintNonManagerMenu();
            choice = scanner.nextInt();
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
                    break;
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
        System.out.println("2. update Shifts Requirements");
        System.out.println("3. Print Weekly Schedule");
        System.out.println("4. Return to Main HR Menu");
        System.out.print("Enter your choice: ");
    }

    public static void AddEmployeesBulk(Branch branch) throws Exception {
        branch.addWorker(new Employee(1, "John Doe", "john.doe@example.com", new BankAccount(12345, 67890, 54321), branch, new Salary(50000)));
        branch.addWorker(new Employee(2, "Jane Smith", "jane.smith@example.com", new BankAccount(54321, 98765, 12345), branch, new Salary(60000)));
        branch.addWorker(new Employee(3, "Alice Johnson", "alice.johnson@example.com", new BankAccount(98765, 13579, 24680), branch, new Salary(55000)));
        branch.addWorker(new Employee(4, "Bob Brown", "bob.brown@example.com", new BankAccount(24680, 24680, 13579), branch, new Salary(70000)));
        branch.addWorker(new Employee(5, "Emily Davis", "emily.davis@example.com", new BankAccount(13579, 54321, 98765), branch, new Salary(65000)));
        branch.addWorker(new Employee(6, "Michael Wilson", "michael.wilson@example.com", new BankAccount(99999, 88888, 77777), branch, new Salary(75000)));
        branch.addWorker(new Employee(7, "Jessica Martinez", "jessica.martinez@example.com", new BankAccount(77777, 66666, 55555), branch, new Salary(58000)));
        branch.addWorker(new Employee(8, "David Anderson", "david.anderson@example.com", new BankAccount(55555, 44444, 33333), branch, new Salary(62000)));
        branch.addWorker(new Employee(9, "Samantha Taylor", "samantha.taylor@example.com", new BankAccount(33333, 22222, 11111), branch, new Salary(69000)));
        branch.addWorker(new Employee(10, "Christopher Thomas", "christopher.thomas@example.com", new BankAccount(11111, 22222, 33333), branch, new Salary(58000)));
        branch.addWorker(new Employee(11, "Olivia Garcia", "olivia.garcia@example.com", new BankAccount(22222, 33333, 44444), branch, new Salary(72000)));
        branch.addWorker(new Employee(12, "Daniel Rodriguez", "daniel.rodriguez@example.com", new BankAccount(33333, 44444, 55555), branch, new Salary(64000)));
        branch.addWorker(new Employee(13, "Isabella Martinez", "isabella.martinez@example.com", new BankAccount(44444, 55555, 66666), branch, new Salary(68000)));
        branch.addWorker(new Employee(14, "Alexander Hernandez", "alexander.hernandez@example.com", new BankAccount(55555, 66666, 77777), branch, new Salary(70000)));
        branch.addWorker(new Employee(15, "Sophia Lopez", "sophia.lopez@example.com", new BankAccount(66666, 77777, 88888), branch, new Salary(59000)));
        branch.addWorker(new Employee(16, "William Gonzalez", "william.gonzalez@example.com", new BankAccount(77777, 88888, 99999), branch, new Salary(73000)));
        branch.addWorker(new Employee(17, "Mia Wilson", "mia.wilson@example.com", new BankAccount(88888, 99999, 11111), branch, new Salary(66000)));
        branch.addWorker(new Employee(18, "James Rodriguez", "james.rodriguez@example.com", new BankAccount(99999, 11111, 22222), branch, new Salary(70000)));
        branch.addWorker(new Employee(19, "Charlotte Hernandez", "charlotte.hernandez@example.com", new BankAccount(11111, 22222, 33333), branch, new Salary(71000)));
        branch.addWorker(new Employee(20, "Benjamin Smith", "benjamin.smith@example.com", new BankAccount(22222, 33333, 44444), branch, new Salary(60000)));
        branch.addWorker(new Employee(21, "Amelia Martinez", "amelia.martinez@example.com", new BankAccount(33333, 44444, 55555), branch, new Salary(74000)));
        branch.addWorker(new Employee(22, "Elijah Lopez", "elijah.lopez@example.com", new BankAccount(44444, 55555, 66666), branch, new Salary(67000)));
        branch.addWorker(new Employee(23, "Avery Gonzalez", "avery.gonzalez@example.com", new BankAccount(55555, 66666, 77777), branch, new Salary(71000)));
        branch.addWorker(new Employee(24, "Evelyn Wilson", "evelyn.wilson@example.com", new BankAccount(66666, 77777, 88888), branch, new Salary(72000)));
        branch.addWorker(new Employee(25, "Mason Rodriguez", "mason.rodriguez@example.com", new BankAccount(77777, 88888, 99999), branch, new Salary(61000)));
        branch.addWorker(new Employee(26, "Liam Hernandez", "liam.hernandez@example.com", new BankAccount(88888, 99999, 11111), branch, new Salary(75000)));
        branch.addWorker(new Employee(27, "Emma Martinez", "emma.martinez@example.com", new BankAccount(99999, 11111, 22222), branch, new Salary(68000)));
        branch.addWorker(new Employee(28, "Aiden Lopez", "aiden.lopez@example.com", new BankAccount(11111, 22222, 33333), branch, new Salary(72000)));
        branch.addWorker(new Employee(29, "Oliver Gonzalez", "oliver.gonzalez@example.com", new BankAccount(22222, 33333, 44444), branch, new Salary(73000)));
        branch.addWorker(new Employee(30, "Aria Wilson", "aria.wilson@example.com", new BankAccount(33333, 44444, 55555), branch, new Salary(62000)));
        branch.getWorkerById(1).addPossiblePosition(new Cashier());
        branch.getWorkerById(1).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(2).addPossiblePosition(new Cashier());
        branch.getWorkerById(3).addPossiblePosition(new Cashier());
        branch.getWorkerById(3).addPossiblePosition(new DeliveryPerson());
        branch.getWorkerById(4).addPossiblePosition(new Cashier());;
        branch.getWorkerById(5).addPossiblePosition(new Cashier());
        branch.getWorkerById(5).addPossiblePosition(new DeliveryPerson());
        branch.getWorkerById(6).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(6).addPossiblePosition(new StorageEmployee());
        branch.getWorkerById(7).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(8).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(9).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(10).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(11).addPossiblePosition(new Cashier());
        branch.getWorkerById(11).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(12).addPossiblePosition(new Cashier());
        branch.getWorkerById(13).addPossiblePosition(new Cashier());
        branch.getWorkerById(13).addPossiblePosition(new DeliveryPerson());
        branch.getWorkerById(14).addPossiblePosition(new Cashier());
        branch.getWorkerById(15).addPossiblePosition(new Cashier());
        branch.getWorkerById(15).addPossiblePosition(new DeliveryPerson());
        branch.getWorkerById(16).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(17).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(18).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(19).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(20).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(21).addPossiblePosition(new Cashier());
        branch.getWorkerById(21).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(22).addPossiblePosition(new StorageEmployee());
        branch.getWorkerById(23).addPossiblePosition(new Cashier());
        branch.getWorkerById(23).addPossiblePosition(new DeliveryPerson());
        branch.getWorkerById(24).addPossiblePosition(new StorageEmployee());
        branch.getWorkerById(25).addPossiblePosition(new Cashier());
        branch.getWorkerById(25).addPossiblePosition(new DeliveryPerson());
        branch.getWorkerById(26).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(27).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(28).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(29).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(30).addPossiblePosition(new ShiftManager());
    }

}
