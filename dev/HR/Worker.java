package HR;

import java.util.ArrayList;
import java.util.List;

public class Worker {
    private int workerId;
    private String name;
    private String email;
    private BankAccount bankAccount;
    private Branch branch;
    private List<WorkerType> possiblePositions;
    private Salary currentSalary;


    public Worker(int workerId, String name, String email, BankAccount bankAccount, Branch branch, Salary currentSalary) {
        this.workerId = workerId;
        this.name = name;
        this.email = email;
        this.bankAccount = bankAccount;
        this.branch = branch;
        this.possiblePositions = null;
        this.currentSalary = currentSalary;
    }

    public int getWorkerId() {
        return workerId;
    }

    public boolean equals(Worker worker) {
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

    public List<WorkerType> getPossiblePositions() {
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

    public void addPossiblePosition(WorkerType position) {
        if (possiblePositions == null) {
            possiblePositions = new ArrayList<WorkerType>();
        } else {
            for (WorkerType workerType : possiblePositions) {
                if (workerType.equals(position)) {
                    System.out.println("Worker already has this position.");
                    return;
                }
            }
        }
        System.out.println("Position added to worker.");
        possiblePositions.add(position);
    }

    public void removePossiblePosition(WorkerType position) {
        if (possiblePositions != null) {
            for (WorkerType workerType : possiblePositions) {
                if (workerType.equals(position)) {
                    possiblePositions.remove(workerType);
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
            for (WorkerType workerType : possiblePositions) {
                System.out.println(workerType.getType());
            }
        }
        System.out.println("Current salary: ");
        System.out.println(currentSalary.toString());
    }

    public boolean hasRole(WorkerType role) {
        return possiblePositions.contains(role);
    }

}
