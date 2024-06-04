package HR.Domain;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

public class BranchController {
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
