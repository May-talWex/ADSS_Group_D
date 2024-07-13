package HR.Presentation;

import HR.Data.CreateStubEmployeesData;
import HR.Domain.*;
import HR.Domain.EmployeeTypes.*;
import HR.Domain.Exceptions.NotEnoughWorkers;
import Inventory.PresentationLayer.CLIInterface;

import java.util.Scanner;

public class Main_Menu {
    public static void main(String[] args) throws Exception {


        EmployeeController employeeController = new EmployeeController();
        ShiftController shiftController = new ShiftController();

        // Get stub data
        Branch branch = CreateStubEmployeesData.AddEmployeesBulk();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter your ID (or -1 to exit): ");
            int id;
            do {
                try {
                    id = scanner.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");

                }
            } while (true);

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
                HRMenu(branch, employeeController, shiftController);
            } else if(employee.hasRole(new StorageEmployee())) {
                StorageEmployeeMenu(branch, id);
            } else {
                NonManagerMenu(branch, id);
            }
        }
    }

    private static void PrintStorageEmployeeMenu() {
        System.out.println("Storage Employee Menu:");
        System.out.println("1. Storage Management");
        System.out.println("2. Employee Menu");
        System.out.println("3. Logout");
        System.out.print("Enter your choice: ");
    }
    private static void StorageEmployeeMenu(Branch branch, int id) throws Exception {
        PrintStorageEmployeeMenu();
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            do {
                try {
                    choice = scanner.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextInt();
                }
            } while (true);

            switch (choice) {
                case 1:
                    CLIInterface.main(branch);
                    break;
                case 2:
                    employeeMenu(branch, new EmployeeController());
                    break;
                case 3:
                    System.out.println("Logging out and returning to the main menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        } while (choice != 3);
    }

    public static void HRMenu(Branch branch, EmployeeController employeeController, ShiftController shiftController) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int HRChoice;
        do {
            PrintMainHRMenu();
            do {
                try {
                    HRChoice = scanner.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextInt();
                }
            } while (true);

            switch (HRChoice) {
                case 1:
                    employeeMenu(branch, employeeController);
                    break;
                case 2:
                    scheduleMenu(branch, shiftController);
                    break;
                case 3:
                    System.out.println("Inventory Management");
                    CLIInterface.main(branch);
                    break;
                case 4:
                    System.out.println("Logging out and returning to the main menu...");

                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        } while (HRChoice != 4);
    }

    public static void employeeMenu(Branch branch, EmployeeController employeeController) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int employeeChoice;
        do {
            PrintEmployeeMenu();
            do {
                try {
                    employeeChoice = scanner.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextInt();
                }
            } while (true);

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

    public static void scheduleMenu(Branch branch, ShiftController shiftController) throws NotEnoughWorkers {
        Scanner scanner = new Scanner(System.in);
        int scheduleChoice;
        do {
            PrintScheduleMenu();
            do {
                try {
                    scheduleChoice = scanner.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextInt();
                }
            } while (true);
            switch (scheduleChoice) {
                case 1:
                    // Create Shift Schedule logic
                    Shift shift = ShiftController.createShift(branch);
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
            do {
                try {
                    choice = scanner.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextInt();
                }
            } while (true);

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
        System.out.println("3. Inventory Managment");
        System.out.println("4. Logout");
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



}
