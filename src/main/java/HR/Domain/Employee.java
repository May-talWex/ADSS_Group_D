package HR.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;


public class Employee {
    @JsonProperty("workerId")
    private int workerId;
    @JsonProperty("workerName")
    private String name;
    @JsonProperty("workerEmail")
    private String email;
    @JsonProperty("bankAccount")
    private BankAccount bankAccount;
    @JsonProperty("branch")
    private Branch branch;
    @JsonProperty("possiblePositions")
    private List<EmployeeType> possiblePositions;
    @JsonProperty("salary")
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

    public Employee(String JSON){
        // TODO: Implement this method to create an Employee object from a JSON string
    }

    public Employee() {
        this.possiblePositions = new ArrayList<>();
    }
    public String toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            return null;
        }
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

    public String toString() {
        return name + " (" + workerId + ")";
    }

    public boolean hasRole(EmployeeType role) {
        return possiblePositions.contains(role);
    }

}
