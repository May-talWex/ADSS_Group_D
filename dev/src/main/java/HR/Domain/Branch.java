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
    private final List<ShiftLimitation> shiftLimitations;

    public Branch(String name, String address) {
        this.branchId = nextBranchId++;
        this.branchName = name;
        this.branchAddress = address;
        this.workers = new ArrayList<>();
        this.schedule = new Schedule();
        this.shiftLimitations = new ArrayList<>();
    }
    public Branch(int id, String name, String address) {
        this.branchId = id;
        this.branchName = name;
        this.branchAddress = address;
        this.workers = new ArrayList<>();
        this.schedule = new Schedule();
        this.shiftLimitations = new ArrayList<>();
    }

    public String getName() {
        return branchName;
    }

    public String getAddress() {
        return branchAddress;
    }

    public int getBranchId() {
        return branchId;
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
        System.out.println("Employee " + worker.getName() + ", id: " + worker.getEmployeeId() + " added to the branch.");
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
        // Remove any future shift limitations for the worker
        List<ShiftLimitation> limitations = new ArrayList<>(worker.getBranch().getShiftLimitations().stream().filter(
                sl ->
                        sl.getEmployee().equals(worker) &&
                                sl.getDate().isAfter(LocalDate.now())).toList());

        if (!limitations.isEmpty()) {
            // Remove all future shift limitations
            for (ShiftLimitation sl : limitations) {
                worker.getBranch().removeShiftLimitation(worker, sl);
            }
        }

        // Remove any future shifts for the worker
        // And update the shifts of the worker that are affected by the removal

        List<Shift> shifts = new ArrayList<>(worker.getBranch().getSchedule().getShifts().stream().filter(
                s ->
                        s.contains(worker)).toList());

        if (!shifts.isEmpty()) {
            // Remove worker from all future shifts
            // And update the shifts of the worker that are affected by the removal
            for (Shift s : shifts) {
                this.getSchedule().replaceWorker(this, worker, s);
                if(s.getShiftManagers().contains(worker)) {
                    s.removeShiftManager(worker);

                }
                if(s.getCashiers().contains(worker)) {
                    s.removeCashier(worker);
                }
                if(s.getStorageEmployees().contains(worker)) {
                    s.removeStorageEmployee(worker);
                }
                if(s.getDeliveriers().contains(worker)) {
                    s.removeDeliverier(worker);
                }

            }
        }

        throw new EmployeeDoesNotExistInBranch("Worker ID: " + worker.getEmployeeId() + " does not exist in the branch with ID: " + branchId);
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

    public Employee getWorkerById(int id) {
        if (workers == null || workers.isEmpty()) return null;

        for (Employee worker : workers) {

            if (worker.getEmployeeId() == id) {
                return worker;
            }
        }
        return null;
    }


    public List<ShiftLimitation> getShiftLimitations() {
        return shiftLimitations;
    }
    public Void SetShiftLimitations(List<ShiftLimitation> shiftLimitations) {
        this.shiftLimitations.clear();
        this.shiftLimitations.addAll(shiftLimitations);
        return null;
    }
}
