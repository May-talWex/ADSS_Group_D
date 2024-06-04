package HR.Presentation;
import HR.Domain.*;
import HR.Data.*;
import HR.Domain.Exceptions.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EmployeeController employeeController = new EmployeeController();
        BranchController branchController = new BranchController();
        Branch branch = branchController.createBranch();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your ID: ");
        int id = scanner.nextInt();
        if(branch.getWorkerById(id).getPossiblePositions().contains("HRManager")){
            System.out.println("Welcome " + branch.getWorkerById(id).getName());
            System.out.println("Select an option:");
            System.out.println("1. Add Employee");
            System.out.println("2. Remove Employee");
            System.out.println("3. Get All Available Positions");
            System.out.println("4. Create Schedule");
            System.out.println("5. Set Shift Restrictions");
            System.out.println("6. Get Shift Limitations");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Employee temp = employeeController.createEmployee(branch);
                    try {
                        branch.addWorker(temp);
                    } catch (Exception e) {
                        System.out.println("Employee already exists in branch.");
                        break;
                    }
                case 2:
                    // Remove Employee logic
                    System.out.println("Removing Employee...");
                    // Implement logic for removing employee (e.g., prompt for employee ID, call a removeEmployee method)
                    break;
                case 3:
                    // Get All Available Positions logic
                    System.out.println("Getting Available Positions...");
                    // Implement logic for displaying available positions (e.g., call a getAvailablePositions method)
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
                    System.out.println("Exiting HR Manager...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

            scanner.close();
        }
        else{
            System.out.println("You do not have the required permissions to view the schedule.");
        }
    }
}
