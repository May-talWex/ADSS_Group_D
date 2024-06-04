package HR.Domain;

import HR.Domain.Exceptions.EmployeeAlreadyHasRole;
import HR.Domain.Exceptions.EmployeeDoesNotHaveRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
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
    @JsonProperty("dateOfEmployment")
    private LocalDate dateOfEmployment;


    public Employee(int workerId, String name, String email, BankAccount bankAccount, Branch branch, Salary currentSalary) {
        this.workerId = workerId;
        this.name = name;
        this.email = email;
        this.bankAccount = bankAccount;
        this.branch = branch;
        this.possiblePositions = null;
        this.currentSalary = currentSalary;
        this.dateOfEmployment = LocalDate.now();
    }

    public Employee(String JSON) {
        // TODO: Implement this method to create an Employee object from a JSON string
    }


    public String toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            return null;
        }
    }

    public int getEmployeeId() {
        return workerId;
    }

    public boolean equals(Employee worker) {
        return workerId == worker.getEmployeeId();
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfEmployment() {
        return dateOfEmployment;
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

    public String printPossiblePositions() {
        String positions = "";
        if (possiblePositions != null) {
            for (EmployeeType employeeType : possiblePositions) {
                positions += employeeType.getType() + " ";
            }
        }
        return positions;
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

    public void addPossiblePosition(EmployeeType position) throws Exception {
        if (possiblePositions == null) {
            possiblePositions = new ArrayList<EmployeeType>();
        } else {
            for (EmployeeType employeeType : possiblePositions) {
                if (employeeType.equals(position)) {
                    throw new EmployeeAlreadyHasRole(position.getType());
                }
            }
        }
        System.out.println("Position " + position.getType() + " added to worker.");
        possiblePositions.add(position);
    }

    public void removePossiblePosition(EmployeeType position) throws Exception {
        if (possiblePositions != null) {
            for (EmployeeType employeeType : possiblePositions) {
                if (employeeType.equals(position)) {
                    possiblePositions.remove(employeeType);
                    System.out.println("Position removed from worker.");
                    return;
                }
            }
            throw new EmployeeDoesNotHaveRole(position.getType());
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
        if (this.possiblePositions == null) return false;
        return this.possiblePositions.contains(role);
    }

    public void setSalary(Salary salary) {
        this.currentSalary = salary;
    }
}
