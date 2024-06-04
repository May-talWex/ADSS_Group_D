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
    }}
