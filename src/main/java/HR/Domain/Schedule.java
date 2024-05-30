package HR.Domain;

import HR.Domain.WorkerTypes.*;

import java.time.LocalDate;
import java.util.*;

/*
 * TODO: Finish the implementation of the Schedule class
 * */
public class Schedule {
    private Map<LocalDate, List<Shift>> shifts;

    public Schedule() {
        this.shifts = new HashMap<>();
    }

    public void addShift(LocalDate date, List<Shift> shift) {
        List<Shift> shiftList = shifts.computeIfAbsent(date, k -> new ArrayList<>());
        if (shiftList.isEmpty()) {
            shiftList.addAll(shift);
        } else {
            for (Shift s : shift) {
                if (!shiftList.contains(s)) {
                    shiftList.add(s);
                } else {
                    System.out.println("Shift already exists.");
                    throw new IllegalArgumentException();
                }
            }
        }

    }

    public List<Shift> getShifts(LocalDate date) {
        return shifts.get(date);
    }

    public boolean doesShiftExist(LocalDate date, boolean isMorningShift) {
        List<Shift> shiftList = shifts.get(date);
        if (shiftList == null) {
            return false;
        }
        return shiftList.stream().anyMatch(shift -> shift.isMorningShift() == isMorningShift && shift.getDate().equals(date));
    }

    public void removeShift(LocalDate date, boolean isMorningShift) {
        boolean result = shifts.get(date).removeIf(shift -> shift.isMorningShift() == isMorningShift);
        if (result) {
            System.out.println("Shift removed.");
        } else {
            System.out.println("Shift not found.");
            throw new IllegalArgumentException();
        }
    }

    public void printWeeklySchedule(LocalDate startDate) {
        System.out.println("Weekly Schedule for the dates: " + startDate + " - " + startDate.plusDays(6));
        for (int i = 0; i < 7; i++) {
            LocalDate date = startDate.plusDays(i);
            List<Shift> shiftList = shifts.get(date);
            System.out.println(date + " - " + date.getDayOfWeek() + ":");
            if (shiftList == null) {
                System.out.println(date + ": No shifts");
            } else {
                System.out.println(date + ":");
                // Sorting the list by isDayShift in descending order
                shiftList.sort(Comparator.comparing(Shift::isMorningShift).reversed());
                for (Shift shift : shiftList) {
                    System.out.println("  " + shift);
                }
            }
        }
    }

    public void generateShift(Branch branch, LocalDate date, boolean isMorningShift) {
        if (branch.getSchedule().doesShiftExist(date, isMorningShift)) { // TODO: Check this
            System.out.println("Shift already exists.");
            throw new IllegalArgumentException();
        }
        System.out.println("Generating shift for the date: " + date + " and isMorningShift: " + isMorningShift);
        System.out.println("Enter the number of managers for the shift: ");
        int numManagers = new Scanner(System.in).nextInt();
        System.out.println("Enter the number of cashiers for the shift: ");
        int numCashiers = new Scanner(System.in).nextInt();
        System.out.println("Enter the number of storage workers for the shift: ");
        int numStorageWorkers = new Scanner(System.in).nextInt();
        System.out.println("Enter the number of deliveriers for the shift: ");
        int numDeliveriers = new Scanner(System.in).nextInt();
        List<Worker> branchWorkers = List.copyOf(branch.getWorkers());
        List<Worker> shiftWorkers = new ArrayList<>();
        List<Worker> shiftManagerList = new ArrayList<>();
        if (branchWorkers.isEmpty() || branchWorkers.size() < numManagers) {
            System.out.println("Not enough workers for the shift.");
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < numManagers; i++) {
            // Go through all of the workers capable of being a manager
            // and add them to the list of workers that can be assigned to the shift
            // If the worker does not have a limitation for the shift, add them to the list
            // If the worker has a limitation for the shift, do not add them to the list
            // If the worker is already assigned to a shift on the same day, do not add them to the list
            // Stop when reaching the required number of managers
            for (Worker w : branchWorkers) {
                if (w.hasRole(new ShiftManager()) && !branch.hasShiftLimitation(w, date, isMorningShift) && !shiftWorkers.contains(w)) {
                    shiftWorkers.add(w);
                    shiftManagerList.add(w);
                    branchWorkers.remove(w);
                    break;
                }
            }
        }
        if (branchWorkers.isEmpty() || branchWorkers.size() < numCashiers) {
            System.out.println("Not enough workers for the shift.");
            throw new IllegalArgumentException();
        }
        List<Worker> cashierList = new ArrayList<>();
        for (int i = 0; i < numCashiers; i++) {
            // Go through all of the workers capable of being a cashier
            // and add them to the list of workers that can be assigned to the shift
            // If the worker does not have a limitation for the shift, add them to the list
            // If the worker has a limitation for the shift, do not add them to the list
            // If the worker is already assigned to a shift on the same day, do not add them to the list
            // Stop when reaching the required number of cashiers
            for (Worker w : branchWorkers) {
                if (w.hasRole(new Cashier()) && !branch.hasShiftLimitation(w, date, isMorningShift) && !shiftWorkers.contains(w)) {
                    shiftWorkers.add(w);
                    cashierList.add(w);
                    branchWorkers.remove(w);
                    break;
                }
            }
        }
        if (branchWorkers.isEmpty() || branchWorkers.size() < numStorageWorkers) {
            System.out.println("Not enough workers for the shift.");
            throw new IllegalArgumentException();
        }
        List<Worker> storageWorkerList = new ArrayList<>();
        for (int i = 0; i < numStorageWorkers; i++) {
            // Go through all of the workers capable of being a storage worker
            // and add them to the list of workers that can be assigned to the shift
            // If the worker does not have a limitation for the shift, add them to the list
            // If the worker has a limitation for the shift, do not add them to the list
            // If the worker is already assigned to a shift on the same day, do not add them to the list
            // Stop when reaching the required number of storage workers
            for (Worker w : branchWorkers) {
                if (w.hasRole(new StorageWorker()) && !branch.hasShiftLimitation(w, date, isMorningShift) && !shiftWorkers.contains(w)) {
                    shiftWorkers.add(w);
                    storageWorkerList.add(w);
                    branchWorkers.remove(w);
                    break;
                }
            }
        }

        if (branchWorkers.isEmpty() || branchWorkers.size() < numDeliveriers) {
            System.out.println("Not enough workers for the shift.");
            throw new IllegalArgumentException();
        }

        List<Worker> deliverierList = new ArrayList<>();
        for (int i = 0; i < numDeliveriers; i++) {
            // Go through all of the workers capable of being a deliverier
            // and add them to the list of workers that can be assigned to the shift
            // If the worker does not have a limitation for the shift, add them to the list
            // If the worker has a limitation for the shift, do not add them to the list
            // If the worker is already assigned to a shift on the same day, do not add them to the list
            // Stop when reaching the required number of deliveriers
            for (Worker w : branchWorkers) {
                if (w.hasRole(new DeliveryPerson()) && !branch.hasShiftLimitation(w, date, isMorningShift) && !shiftWorkers.contains(w)) {
                    shiftWorkers.add(w);
                    deliverierList.add(w);
                    branchWorkers.remove(w);
                    break;
                }
            }
        }

        // Add the shift to the schedule
        if (shiftManagerList.isEmpty() && numManagers > 0
                || cashierList.isEmpty() && numCashiers > 0
                || storageWorkerList.isEmpty() && numStorageWorkers > 0
                || deliverierList.isEmpty() && numDeliveriers > 0) {
            System.out.println("Not enough workers for the shift.");
            throw new IllegalArgumentException();
        }
        this.shifts.computeIfAbsent(date, k -> new ArrayList<>()).add(new Shift(isMorningShift, date, shiftManagerList, cashierList, storageWorkerList, deliverierList));

    }

}
