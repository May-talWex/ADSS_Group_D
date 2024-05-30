package HR.Domain;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {
    private int workerId;
    private String name;
    private String email;
    private BankAccount bankAccount;
    private Branch branch;
    private List<EmployeeType> possiblePositions;
    private Salary currentSalary;


    public Employee(int workerId, String name, String email, BankAccount bankAccount, Branch branch, Salary currentSalary) {
        this.workerId = workerId;
        this.name = name;
        this.email = email;
        this.bankAccount = bankAccount;
        this.branch = branch;
        this.possiblePositions = null;
        this.currentSalary = currentSalary;
    }
    @JsonCreator
    public Employee JSONtoClass(
            @JsonProperty("workerId") int workerId,
            @JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("bankAccount") BankAccount bankAccount,
            @JsonProperty("branch") Branch branch,
            @JsonProperty("possiblePositions") List<EmployeeType> possiblePositions,
            @JsonProperty("currentSalary") Salary currentSalary) {
        return new Employee(workerId, name, email, bankAccount, branch, currentSalary);
    }

    public int getWorkerId() {
        return workerId;
    }

    public boolean equals(Employee worker) {
        return workerId == worker.getWorkerId();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBankAccountString() {
        return bankAccount.toString();
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public Branch getBranch() {
        return branch;
    }

    public List<EmployeeType> getPossiblePositions() {
        return possiblePositions;
    }

    public Salary getCurrentSalary() {
        return currentSalary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void addPossiblePosition(EmployeeType position) {
        if (possiblePositions == null) {
            possiblePositions = new ArrayList<EmployeeType>();
        } else {
            for (EmployeeType employeeType : possiblePositions) {
                if (employeeType.equals(position)) {
                    System.out.println("Worker already has this position.");
                    return;
                }
            }
        }
        System.out.println("Position added to worker.");
        possiblePositions.add(position);
    }

    public void removePossiblePosition(EmployeeType position) {
        if (possiblePositions != null) {
            for (EmployeeType employeeType : possiblePositions) {
                if (employeeType.equals(position)) {
                    possiblePositions.remove(employeeType);
                    System.out.println("Position removed from worker.");
                    return;
                }
            }
            System.out.println("Worker does not have this position.");
        }
    }

    public void setCurrentSalary(Salary currentSalary) {
        this.currentSalary = currentSalary;
    }

    public void printWorker() {
        System.out.println("Worker ID: " + workerId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Bank Account: " + bankAccount.toString());
        System.out.println("Branch: " + branch.getName());
        System.out.println("Possible positions: ");
        if (possiblePositions != null) {
            for (EmployeeType employeeType : possiblePositions) {
                System.out.println(employeeType.getType());
            }
        }
        System.out.println("Current salary: ");
        System.out.println(currentSalary.toString());
    }

    public boolean hasRole(EmployeeType role) {
        return possiblePositions.contains(role);
    }

}
