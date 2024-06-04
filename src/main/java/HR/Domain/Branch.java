package HR.Domain;

import java.time.LocalDate;
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
    private final List<Employee> workers;
    private final Schedule schedule;
    private List<ShiftLimitation> shiftLimitations;

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

    public List<Employee> getWorkers() {
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

    public void addWorker(Employee worker) {
        for (Employee w : workers) {
            if (w.equals(worker)) {
                System.out.println("Worker already exists in the branch.");
                return;
            }
        }
        System.out.println("Worker added to the branch.");
        workers.add(worker);
    }

    public void removeWorker(Employee worker) {
        for (Employee w : workers) {
            if (w.equals(worker)) {
                workers.remove(w);
                System.out.println("Worker removed from the branch.");
                return;
            }
        }
        System.out.println("Worker does not exist in the branch.");
    }

    public void addShiftLimitation(Employee worker, LocalDate date, boolean isMorningShift) {
        ShiftLimitation shiftLimitation = new ShiftLimitation(worker, date, isMorningShift);
        for (ShiftLimitation sl : shiftLimitations) {
            if (sl.equals(shiftLimitation)) {
                System.out.println("Shift limitation already exists.");
                return;
            }
        }
        System.out.println("Shift limitation added.");
        shiftLimitations.add(shiftLimitation);
    }

    public void removeShiftLimitation(Employee worker, LocalDate date, boolean isMorningShift) {
        ShiftLimitation shiftLimitation = new ShiftLimitation(worker, date, isMorningShift);
        for (ShiftLimitation sl : shiftLimitations) {
            if (sl.equals(shiftLimitation)) {
                shiftLimitations.remove(sl);
                System.out.println("Shift limitation removed.");
                return;
            }
        }
        System.out.println("Shift limitation does not exist.");
    }

    public boolean hasShiftLimitation(Employee worker, LocalDate date, boolean isMorningShift) {
        ShiftLimitation shiftLimitation = new ShiftLimitation(worker, date, isMorningShift);
        for (ShiftLimitation sl : shiftLimitations) {
            if (sl.equals(shiftLimitation)) {
                return true;
            }
        }
        return false;
    }


}
