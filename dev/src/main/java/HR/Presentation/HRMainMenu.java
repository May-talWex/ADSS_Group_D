package HR.Presentation;

import HR.Data.*;
import HR.Domain.*;
import HR.Domain.EmployeeTypes.*;
import HR.Domain.Exceptions.NotEnoughWorkers;
import Inventory.PresentationLayer.CLIInterface;

import java.util.List;
import java.util.Scanner;

public class HRMainMenu {
    public static void DisplayHRMainMenu() throws Exception {
        SQLiteConnection.initializeDatabase();

        BranchDAO branchDAO = new BranchDAO();
        EmployeesDAO employeesDAO = new EmployeesDAO();
        ShiftsDAO shiftDAO = new ShiftsDAO();
        ShiftLimitationDAO shiftLimitationDAO = new ShiftLimitationDAO();

        Branch branch = branchDAO.getBranchFromDatabase(1); // Default branch with ID 1

        if (branch == null) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Branch not found. Do you want to create a new branch? (yes/no)");
            if (scanner.nextLine().equalsIgnoreCase("yes")) {
                System.out.println("Enter the branch name: ");
                String branchName = scanner.nextLine();
                System.out.println("Enter the branch address: ");
                String branchAddress = scanner.nextLine();
                branch = new Branch(1, branchName, branchAddress); // ID 1 for default branch
                branchDAO.addBranchToDatabase(branch);

                // Add default admin employee
                BankAccount bankAccount = new BankAccount(0, 0, 0);
                Salary salary = new Salary(0);
                Employee admin = new Employee(0, "Admin", "admin@supermarket.com", bankAccount, branch, salary);
                admin.addPossiblePosition(new HRManager());
                employeesDAO.addEmployeeToDatabase(admin);
                System.out.println("Default admin account has been created.");
            }
        }


        assert branch != null;
        List<Employee> employees = employeesDAO.getAllEmployees(branch.getBranchId());
        for (Employee employee : employees) {
            branch.addWorker(employee);
        }

        List<Shift> shifts = new ShiftsDAO().getAllShifts(branch);
        for (Shift shift : shifts) {
            branch.getSchedule().addShift(shift);
        }

        List<ShiftLimitation> shiftLimitations = shiftLimitationDAO.getAllShiftLimitations(branch);
        branch.SetShiftLimitations(shiftLimitations);

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
                    scanner.next(); // Consume the invalid input
                }
            } while (true);

            if (id == -1) {
                System.out.println("Exiting the system...");
                break;
            }

            Employee employee = branch.getWorkerById(id);

            if (employee == null) {
                System.out.println("Employee not found.");
                continue;
            }

            if (employee.hasRole(new HRManager())) {
                System.out.println("Welcome, HR Manager " + employee.getName() + "!");
                HRMenu(branch, employeesDAO, shiftDAO);
            } else if(employee.hasRole(new StorageEmployee())) {
                System.out.println("Welcome, Storage Employee " + employee.getName() + "!");
                StorageEmployeeMenu(branch, id);
            } else {
                System.out.println("Welcome, " + employee.getName() + "!");
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
                    scanner.next(); // Consume the invalid input
                }
            } while (true);

            switch (choice) {
                case 1:
                    CLIInterface.main(branch);
                    break;
                case 2:
                    employeeMenu(branch, new EmployeesDAO());
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

    public static void HRMenu(Branch branch, EmployeesDAO employeesDAO, ShiftsDAO shiftDAO) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int HRChoice;
        do {
            PrintMainHRMenu();
            HRChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (HRChoice) {
                case 1:
                    employeeMenu(branch, employeesDAO);
                    break;
                case 2:
                    scheduleMenu(branch, shiftDAO);
                    break;
                case 3:
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

    public static void employeeMenu(Branch branch, EmployeesDAO employeesDAO) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int employeeChoice;
        do {
            PrintEmployeeMenu();
            employeeChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (employeeChoice) {
                case 1:
                    Employee temp = createEmployee(branch);
                    employeesDAO.addEmployeeToDatabase(temp);
                    branch.addWorker(temp);
                    break;
                case 2:
                    removeEmployee(branch, employeesDAO);
                    break;
                case 3:
                    printEmployees(branch);
                    break;
                case 4:
                    try {
                        updateEmployeePosition(branch, employeesDAO);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        updateDriverLicense(branch, employeesDAO);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Returning to the main HR menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        } while (employeeChoice != 6);
    }

    private static void updateDriverLicense(Branch branch, EmployeesDAO employeesDAO) {
        printLicenseMenu();
        Scanner scanner = new Scanner(System.in);
        int licenseChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        int id;
        Employee employee;
        String license;
        switch (licenseChoice) {
            case 1:
                System.out.println("Enter Employee ID: ");
                id = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                employee = branch.getWorkerById(id);
                if (employee == null) {
                    System.out.println("Employee not found.");
                    return;
                }
                System.out.print("Enter Driver License type: ");
                license = scanner.nextLine();

                if (employee.getDriverLicenses().contains(license)) {
                    System.out.println("Driver License already exists.");
                    break;
                }

                employee.addDriverLicense(license);
                employeesDAO.addDriverLicenseToEmployee(id, license);
                System.out.println("Driver License added successfully.");
                break;

            case 2:
                System.out.println("Enter Employee ID: ");
                id = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                employee = branch.getWorkerById(id);
                if (employee == null) {
                    System.out.println("Employee not found.");
                    return;
                }
                System.out.print("Enter Driver License type: ");
                license = scanner.nextLine();

                if (!employee.getDriverLicenses().contains(license)) {
                    System.out.println("Driver License not found.");
                    break;
                }
                employee.removeDriverLicense(license);
                employeesDAO.removeDriverLicenseFromEmployee(id, license);
                System.out.println("Driver License removed successfully.");
                break;

            case 3:
                System.out.println("Enter Employee ID: ");
                id = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                employee = branch.getWorkerById(id);
                if (employee == null) {
                    System.out.println("Employee not found.");
                    return;
                }
                List<String> licenses = employee.getDriverLicenses();
                if (licenses.isEmpty()) {
                    System.out.println("No driver licenses found.");
                } else {
                    System.out.println("Driver Licenses: ");
                    for (String driverLicense : licenses) {
                        System.out.print(driverLicense + ", ");
                    }
                    System.out.println(); // New line after listing licenses
                }
                break;

            case 4:
                System.out.println("Returning to the main HR menu...");
                break;

            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    private static void printLicenseMenu() {
        System.out.println("1. Add Driver License");
        System.out.println("2. Remove Driver License");
        System.out.println("3. View Driver Licenses");
        System.out.println("4. Return to Main HR Menu");
        System.out.print("Enter your choice: ");
    }

    public static Employee createEmployee(Branch branch) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter Employee Name: ");
        String name = scanner.nextLine();

        System.out.println("Enter Employee Email: ");
        String email = scanner.nextLine();

        System.out.println("Enter Bank Number: ");
        int bankNumber = scanner.nextInt();
        System.out.println("Enter Branch Number: ");
        int branchNumber = scanner.nextInt();
        System.out.println("Enter Account Number: ");
        int accountNumber = scanner.nextInt();
        BankAccount bankAccount = new BankAccount(bankNumber, accountNumber, branchNumber);

        System.out.println("Enter Salary: ");
        float salaryAmount = scanner.nextFloat();
        Salary salary = new Salary(salaryAmount);

        Employee employee = new Employee(id, name, email, bankAccount, branch, salary);

        System.out.println("Employee created successfully.");
        return employee;
    }

    public static void removeEmployee(Branch branch, EmployeesDAO employeesDAO) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Employee ID to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Employee employee = branch.getWorkerById(id);
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        try {
            branch.removeEmployee(employee);
            employeesDAO.removeEmployeeFromDatabase(id);
            System.out.println("Employee " + id + " removed successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void printEmployees(Branch branch) {
        List<Employee> employees = branch.getWorkers();
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    public static void updateEmployeePosition(Branch branch, EmployeesDAO employeesDAO) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the employee ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Employee employee = branch.getWorkerById(id);
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.println("Choose a role to add to the employee: ");
        printRoleMenu();
        int role = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            switch (role) {
                case 1:
                    employee.addPossiblePosition(new ShiftManager());
                    employeesDAO.addRoleToEmployee(id, "ShiftManager");
                    break;
                case 2:
                    employee.addPossiblePosition(new Cashier());
                    employeesDAO.addRoleToEmployee(id, "Cashier");
                    break;
                case 3:
                    employee.addPossiblePosition(new StorageEmployee());
                    employeesDAO.addRoleToEmployee(id, "StorageEmployee");
                    break;
                case 4:
                    employee.addPossiblePosition(new DeliveryPerson());
                    employeesDAO.addRoleToEmployee(id, "DeliveryPerson");
                    break;
                case 5:
                    employee.addPossiblePosition(new HRManager());
                    employeesDAO.addRoleToEmployee(id, "HRManager");
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printRoleMenu() {
        System.out.println("1. Shift Manager");
        System.out.println("2. Cashier");
        System.out.println("3. Storage Worker");
        System.out.println("4. Delivery Person");
        System.out.println("5. HR Manager");
    }

    public static void scheduleMenu(Branch branch, ShiftsDAO shiftDAO) throws NotEnoughWorkers {
        Scanner scanner = new Scanner(System.in);
        int scheduleChoice;
        do {
            PrintScheduleMenu();
            scheduleChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (scheduleChoice) {
                case 1:
                    Shift shift = ShiftController.createShift(branch);
                    ScheduleController.generateShift(branch, shift);
                    if (!branch.getSchedule().doesShiftExist(shift.getDate(), shift.isMorningShift())) {
                        shiftDAO.addShift(branch.getSchedule().getShift(shift.getDate(), shift.isMorningShift()), branch.getBranchId());
                    }
                    break;
                case 2:
                    ScheduleController.updateShiftRequirementsDAO(branch, shiftDAO);
                    break;
                case 3:
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
            scanner.nextLine(); // Consume newline

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
        System.out.println("3. Inventory Management");
        System.out.println("4. Logout");
        System.out.print("Enter your choice: ");
    }

    public static void PrintEmployeeMenu() {
        System.out.println("Employee Menu:");
        System.out.println("1. Add Employee");
        System.out.println("2. Remove Employee");
        System.out.println("3. View Available Employees");
        System.out.println("4. Update Employee Information");
        System.out.println("5. Update Driver License");
        System.out.println("6. Return to Main HR Menu");
        System.out.print("Enter your choice: ");
    }

    public static void PrintScheduleMenu() {
        System.out.println("Schedule Menu:");
        System.out.println("1. Create Shift");
        System.out.println("2. Update Shift Requirements");
        System.out.println("3. Print Weekly Schedule");
        System.out.println("4. Return to Main HR Menu");
        System.out.print("Enter your choice: ");
    }
}
