package HR.Domain;

import HR.Domain.Exceptions.EmployeeAlreadyExistsInBranch;
import HR.Domain.Exceptions.EmployeeDoesNotExistInBranch;
import HR.Domain.Exceptions.ShiftAlreadyExists;
import HR.Domain.Exceptions.ShiftLimitationDoesntExist;

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

    public void addWorker(Employee worker) throws Exception {
        for (Employee w : workers) {
            if (w.equals(worker)) {
                throw new EmployeeAlreadyExistsInBranch("Employee ID: " + worker.getEmployeeId() + " already exists in the branch with ID: " + branchId);
            }
        }
        System.out.println("Employee added to the branch.");
        workers.add(worker);
    }

    public void removeEmployee(Employee worker) throws Exception {
        for (Employee w : workers) {
            if (w.equals(worker)) {
                workers.remove(w);
                System.out.println("Worker removed from the branch.");
                return;
            }
        }
        throw new EmployeeDoesNotExistInBranch("Worker ID: " + worker.getEmployeeId() + " does not exist in the branch with ID: " + branchId);
    }

    public void addShiftLimitation(Employee worker, LocalDate date, boolean isMorningShift) throws Exception {
        ShiftLimitation shiftLimitation = new ShiftLimitation(worker, date, isMorningShift);
        for (ShiftLimitation sl : shiftLimitations) {
            if (sl.equals(shiftLimitation)) {
                throw new ShiftAlreadyExists("Shift limitation already exists for worker ID: " + worker.getEmployeeId() + " on date: " + date + " and isMorningShift: " + isMorningShift);
            }
        }
        System.out.println("Shift limitation added.");
        shiftLimitations.add(shiftLimitation);
    }

    public void addShiftLimitation(Employee worker, ShiftLimitation shiftLimitation) throws Exception {
        for (ShiftLimitation sl : shiftLimitations) {
            if (sl.equals(shiftLimitation)) {
                throw new ShiftAlreadyExists("Shift limitation already exists for worker ID: " + worker.getEmployeeId() + " on date: " + shiftLimitation.getDate() + " and isMorningShift: " + shiftLimitation.isMorningShift());
            }
        }
        System.out.println("Shift limitation added.");
        shiftLimitations.add(shiftLimitation);
    }

    public void removeShiftLimitation(Employee worker, LocalDate date, boolean isMorningShift) throws Exception {
        ShiftLimitation shiftLimitation = new ShiftLimitation(worker, date, isMorningShift);
        for (ShiftLimitation sl : shiftLimitations) {
            if (sl.equals(shiftLimitation)) {
                shiftLimitations.remove(sl);
                System.out.println("Shift limitation removed.");
                return;
            }
        }
        throw new ShiftLimitationDoesntExist("Shift limitation does not exist for worker ID: " + worker.getEmployeeId() + " on date: " + date + " and isMorningShift: " + isMorningShift);
    }

    public void removeShiftLimitation(Employee worker, ShiftLimitation shiftLimitation) throws Exception {
        for (ShiftLimitation sl : shiftLimitations) {
            if (sl.equals(shiftLimitation)) {
                shiftLimitations.remove(sl);
                System.out.println("Shift limitation removed.");
                return;
            }
        }
        throw new ShiftLimitationDoesntExist("Shift limitation does not exist for worker ID: " + worker.getEmployeeId() + " on date: " + shiftLimitation.getDate() + " and isMorningShift: " + shiftLimitation.isMorningShift());
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
