package HR;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a branch of the company.
 * branchId: int
 * branchName: String
 * branchAddress: String
 * Workers: List<Worker>
 * Schedule: List<Shift>
 */
public class Branch {
    static int nextBranchId = 1;
    private int branchId;
    private String branchName;
    private String branchAddress;
    private final List<Worker> workers;
    private final Schedule schedule;

    public Branch(String name, String address) {
        this.branchId = nextBranchId++;
        this.branchName = name;
        this.branchAddress = address;
        this.workers = new ArrayList<>();
        this.schedule = new Schedule();
    }

    public String getName() {
        return branchName;
    }

    public String getAddress() {
        return branchAddress;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setName(String name) {
        this.branchName = name;
    }

    public void setAddress(String address) {
        this.branchAddress = address;
    }

    public void addWorker(Worker worker) {
        for (Worker w : workers) {
            if (w.equals(worker)) {
                System.out.println("Worker already exists in the branch.");
                return;
            }
        }
        System.out.println("Worker added to the branch.");
        workers.add(worker);
    }

    public void removeWorker(Worker worker) {
        for (Worker w : workers) {
            if (w.equals(worker)) {
                workers.remove(w);
                System.out.println("Worker removed from the branch.");
                return;
            }
        }
        System.out.println("Worker does not exist in the branch.");
    }


}
