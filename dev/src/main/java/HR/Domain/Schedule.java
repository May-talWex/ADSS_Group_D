package HR.Domain;

import HR.Domain.EmployeeTypes.*;
import HR.Domain.Exceptions.NotEnoughWorkers;
import HR.Domain.Exceptions.ShiftAlreadyExists;

import java.time.LocalDate;
import java.util.*;

/*
 * TODO: Finish the implementation of the Schedule class
 * */
public class Schedule {
    private final Map<LocalDate, List<Shift>> shifts;

    public Schedule() {
        this.shifts = new HashMap<>();
    }

    public void addShift(Shift shift) {
        if (shifts.containsKey(shift.getDate())) {
            shifts.get(shift.getDate()).add(shift);
        } else {
            List<Shift> shiftList = new ArrayList<>();
            shiftList.add(shift);
            shifts.put(shift.getDate(), shiftList);
        }


    }

    public List<Shift> getShifts(LocalDate date) {
        return shifts.get(date);
    }

    public List<Shift> getShifts() {
        List<Shift> allShifts = new ArrayList<>();
        for (List<Shift> shiftList : shifts.values()) {
            allShifts.addAll(shiftList);
        }
        return allShifts;
    }

    public Shift getShift(LocalDate date, boolean isMorningShift) {
        List<Shift> shiftList = shifts.get(date);
        if (shiftList == null) {
            return null;
        }
        for (Shift shift : shiftList) {
            if (shift.isMorningShift() == isMorningShift) {
                return shift;
            }
        }
        return null;
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

    public void generateShift(Branch branch, LocalDate date, boolean isMorningShift) throws Exception {
        if (branch.getSchedule().doesShiftExist(date, isMorningShift)) {
            System.out.println("Shift already exists.");
            throw new ShiftAlreadyExists(" date: " + date + " and isMorningShift: " + isMorningShift);
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
        List<Employee> branchWorkers = new ArrayList<>(List.copyOf(branch.getWorkers()));
        List<Employee> shiftWorkers = new ArrayList<>();
        List<Employee> shiftManagerList = new ArrayList<>();

        // Remove the workers which have a limitation for the shift
        List<ShiftLimitation> shiftLimitations = branch.getShiftLimitations();
        for (ShiftLimitation sl : shiftLimitations) {
            if (sl.getDate().equals(date) && sl.isMorningShift() == isMorningShift) {
                branchWorkers.remove(sl.getEmployee());
            }
        }

        if (branchWorkers.isEmpty() || branchWorkers.size() < numManagers) {
            System.out.println("Not enough workers for the shift.");
            throw new NotEnoughWorkers(" date: " + date + " and isMorningShift: " + isMorningShift);
        }
        for (int i = 0; i < numManagers; i++) {
            // Go through all the workers capable of being a manager
            // and add them to the list of workers that can be assigned to the shift
            // If the worker does not have a limitation for the shift, add them to the list
            // If the worker has a limitation for the shift, do not add them to the list
            // If the worker is already assigned to a shift on the same day, do not add them to the list
            // Stop when reaching the required number of managers
            for (Employee w : branchWorkers) {
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
            throw new NotEnoughWorkers(" date: " + date + " and isMorningShift: " + isMorningShift);
        }
        List<Employee> cashierList = new ArrayList<>();
        for (int i = 0; i < numCashiers; i++) {
            // Go through all the workers capable of being a cashier
            // and add them to the list of workers that can be assigned to the shift
            // If the worker does not have a limitation for the shift, add them to the list
            // If the worker has a limitation for the shift, do not add them to the list
            // If the worker is already assigned to a shift on the same day, do not add them to the list
            // Stop when reaching the required number of cashiers
            for (Employee w : branchWorkers) {
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
            throw new NotEnoughWorkers(" date: " + date + " and isMorningShift: " + isMorningShift);
        }
        List<Employee> storageWorkerList = new ArrayList<>();
        for (int i = 0; i < numStorageWorkers; i++) {
            // Go through all the workers capable of being a storage worker
            // and add them to the list of workers that can be assigned to the shift
            // If the worker does not have a limitation for the shift, add them to the list
            // If the worker has a limitation for the shift, do not add them to the list
            // If the worker is already assigned to a shift on the same day, do not add them to the list
            // Stop when reaching the required number of storage workers
            for (Employee w : branchWorkers) {
                if (w.hasRole(new StorageEmployee()) && !branch.hasShiftLimitation(w, date, isMorningShift) && !shiftWorkers.contains(w)) {
                    shiftWorkers.add(w);
                    storageWorkerList.add(w);
                    branchWorkers.remove(w);
                    break;
                }
            }
        }

        if (branchWorkers.isEmpty() || branchWorkers.size() < numDeliveriers) {
            System.out.println("Not enough workers for the shift.");
            throw new NotEnoughWorkers(" date: " + date + " and isMorningShift: " + isMorningShift);
        }

        List<Employee> deliverierList = new ArrayList<>();
        for (int i = 0; i < numDeliveriers; i++) {
            // Go through all the workers capable of being a deliveries
            // and add them to the list of workers that can be assigned to the shift
            // If the worker does not have a limitation for the shift, add them to the list
            // If the worker has a limitation for the shift, do not add them to the list
            // If the worker is already assigned to a shift on the same day, do not add them to the list
            // Stop when reaching the required number of deliveries
            for (Employee w : branchWorkers) {
                if (w.hasRole(new DeliveryPerson()) &&
                        !branch.hasShiftLimitation(w, date, isMorningShift) &&
                        !shiftWorkers.contains(w)) {
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

            throw new NotEnoughWorkers(" date: " + date + " and isMorningShift: " + isMorningShift);
        }
        this.shifts.computeIfAbsent(date, k -> new ArrayList<>()).add(new Shift(isMorningShift, date, shiftManagerList, cashierList, storageWorkerList, deliverierList));

    }

    public Shift generateShift(Branch branch, LocalDate date, boolean isMorningShift, int shiftManagers, int cashiers, int deliveriers, int storageWorkers) throws Exception {
        if (branch.getSchedule().doesShiftExist(date, isMorningShift)) { // TODO: Check this
            System.out.println("Shift already exists.");
            throw new ShiftAlreadyExists(" date: " + date + " and isMorningShift: " + isMorningShift);
        }
        List<Employee> branchWorkers = new ArrayList<>(new ArrayList<>(List.copyOf(branch.getWorkers())).stream().filter(w -> !branch.hasShiftLimitation(w, date, isMorningShift)).toList());


        List<Employee> shiftManagerList = new ArrayList<>();
        List<Employee> shiftCashierList = new ArrayList<>();
        List<Employee> shiftDeliverierList = new ArrayList<>();
        List<Employee> shiftStorageList = new ArrayList<>();


        if (branchWorkers.isEmpty() || branchWorkers.size() < (shiftManagers + cashiers + deliveriers + storageWorkers)) {
            System.out.println("Not enough workers for the shift.");
            throw new NotEnoughWorkers(" date: " + date + " and isMorningShift: " + isMorningShift);
        }
        ArrayList<Employee> possibleShiftManagers = new ArrayList<>(branchWorkers.stream().filter(w -> w.hasRole(new ShiftManager())).toList());
        for (int i = 0; i < shiftManagers; i++) {
            for (Employee w : possibleShiftManagers) {
                if (!branch.hasShiftLimitation(w, date, isMorningShift)) {
                    shiftManagerList.add(w);
                    branchWorkers.remove(w);
                    break;
                }

            }
        }

        if (shiftManagers > shiftManagerList.size()) {
            System.out.println("Not enough shift managers for the shift.");
            throw new NotEnoughWorkers(" date: " + date + " and isMorningShift: " + isMorningShift);
        }

        ArrayList<Employee> possibleCashiers = new ArrayList<>(branchWorkers.stream().filter(w -> w.hasRole(new Cashier())).toList());
        for (int i = 0; i < cashiers; i++) {
            for (Employee w : possibleCashiers) {
                if (!branch.hasShiftLimitation(w, date, isMorningShift)) {
                    shiftCashierList.add(w);
                    branchWorkers.remove(w);
                    break;
                }
            }
        }
        if (storageWorkers > shiftCashierList.size()) {
            System.out.println("Not enough cashiers for the shift.");
            throw new NotEnoughWorkers(" date: " + date + " and isMorningShift: " + isMorningShift);
        }

        ArrayList<Employee> possibleStorageWorkers = new ArrayList<>(branchWorkers.stream().filter(w -> w.hasRole(new StorageEmployee())).toList());

        for (int i = 0; i < storageWorkers; i++) {
            for (Employee w : possibleStorageWorkers) {
                if (!branch.hasShiftLimitation(w, date, isMorningShift)) {
                    shiftStorageList.add(w);
                    branchWorkers.remove(w);
                    break;
                }
            }
        }
        if (storageWorkers > shiftStorageList.size()) {
            System.out.println("Not enough storage workers for the shift.");
            throw new NotEnoughWorkers(" date: " + date + " and isMorningShift: " + isMorningShift);
        }

        ArrayList<Employee> possibleDeliveriers = new ArrayList<>(branchWorkers.stream().filter(w -> w.hasRole(new DeliveryPerson())).toList());
        for (int i = 0; i < deliveriers; i++) {
            for (Employee w : possibleDeliveriers) {
                if (!branch.hasShiftLimitation(w, date, isMorningShift)) {
                    shiftDeliverierList.add(w);
                    branchWorkers.remove(w);
                    break;
                }
            }
        }

        if (deliveriers > shiftDeliverierList.size()) {
            System.out.println("Not enough deliveriers for the shift.");
            throw new NotEnoughWorkers(" date: " + date + " and isMorningShift: " + isMorningShift);
        }


        if (branchWorkers.isEmpty() || branchWorkers.size() < cashiers) {
            System.out.println("Not enough workers for the shift.");
            throw new NotEnoughWorkers(" date: " + date + " and isMorningShift:" + isMorningShift);
        }
        try {
            return new Shift(isMorningShift, date, shiftManagerList, shiftCashierList, shiftStorageList, shiftDeliverierList);
        } catch (Exception e) {
            System.out.println("Shift could not be created.");
            throw new IllegalArgumentException();
        }
    }

    public void replaceWorker(Branch branch, Employee oldWorker, Shift shift) {
        EmployeeType type = shift.getEmployeeType(oldWorker);
        List<Employee> optionalWorkers = new ArrayList<>(branch.getWorkers().stream().filter(w ->
                        !shift.contains(w) &&
                                !branch.hasShiftLimitation(w, shift.getDate(), shift.isMorningShift()) &&
                                w.hasRole(type)
                )
                .toList());

        String shiftManager = new ShiftManager().getType();
        String cashier = new Cashier().getType();
        String storageEmployee = new StorageEmployee().getType();
        String deliveryPerson = new DeliveryPerson().getType();
        try {
            if (type.getType().equals(shiftManager)) {
                if (shift.getShiftManagers().contains(oldWorker)) {
                    shift.getShiftManagers().remove(oldWorker);
                    shift.getShiftManagers().add(optionalWorkers.getFirst());
                }
            } else if (type.getType().equals(cashier)) {
                if (shift.getCashiers().contains(oldWorker)) {
                    shift.getCashiers().remove(oldWorker);
                    shift.getCashiers().add(optionalWorkers.getFirst());
                }
            } else if (type.getType().equals(storageEmployee)) {
                if (shift.getStorageEmployees().contains(oldWorker)) {
                    shift.getStorageEmployees().remove(oldWorker);
                    shift.getStorageEmployees().add(optionalWorkers.getFirst());
                }
            } else if (type.getType().equals(deliveryPerson)) {
                if (shift.getDeliveriers().contains(oldWorker)) {
                    shift.getDeliveriers().remove(oldWorker);
                    shift.getDeliveriers().add(optionalWorkers.getFirst());
                }
            } else {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.out.println("Invalid type.");
            throw new IllegalArgumentException();
        }
    }

    public void updateShiftRequirement(LocalDate date, boolean isMorningShift, int choice,
                                       int NewRequirement, Branch branch) throws NotEnoughWorkers {
        List<Employee> workers = branch.getWorkers().stream().filter(w ->
                        !this.getShift(date, isMorningShift).getShiftManagers().contains(w) && // Not already shift manager
                                !this.getShift(date, isMorningShift).getCashiers().contains(w) && // Not already cashier
                                !this.getShift(date, isMorningShift).getStorageEmployees().contains(w) && // Not already storage worker
                                !this.getShift(date, isMorningShift).getDeliveriers().contains(w)) // Not already delivery person
                .toList(); // Get all the workers that are not already assigned to the shift
        Shift shift = getShift(date, isMorningShift);
        if (shift == null) {
            System.out.println("Shift not found.");
            throw new IllegalArgumentException();
        }
        switch (choice) {
            case 1:
                if (NewRequirement == shift.getShiftManagers().size()) {
                    System.out.println("Requirement is already set to " + NewRequirement);
                    return;
                } else if (NewRequirement < shift.getShiftManagers().size()) {
                    while (shift.getShiftManagers().size() > NewRequirement) {
                        shift.getShiftManagers().removeLast();
                    }
                } else {
                    for (Employee w : workers) {
                        if (w.hasRole(new ShiftManager()) &&
                                !shift.getShiftManagers().contains(w) &&
                                !branch.hasShiftLimitation(w, date, isMorningShift)) {
                            shift.getShiftManagers().add(w);
                            if (shift.getShiftManagers().size() == NewRequirement) {
                                break;
                            }
                        }
                    }
                    if (shift.getShiftManagers().size() < NewRequirement) {
                        System.out.println("Not enough workers for the shift.");
                        throw new NotEnoughWorkers(" date: " + date + " and isMorningShift: " + isMorningShift);
                    }
                }
                break;
            case 2:
                if (NewRequirement == shift.getCashiers().size()) {
                    System.out.println("Requirement is already set to " + NewRequirement);
                    return;
                } else if (NewRequirement < shift.getCashiers().size()) {
                    while (shift.getCashiers().size() > NewRequirement) {
                        shift.getCashiers().removeLast();
                    }
                } else {
                    for (Employee w : workers) {
                        if (w.hasRole(new Cashier()) && !shift.getCashiers().contains(w) && !branch.hasShiftLimitation(w, date, isMorningShift)) {
                            shift.getCashiers().add(w);
                            if (shift.getCashiers().size() == NewRequirement) {
                                break;
                            }
                        }
                    }
                    if (shift.getCashiers().size() < NewRequirement) {
                        System.out.println("Not enough workers for the shift.");
                        throw new NotEnoughWorkers(" date: " + date + " and isMorningShift: " + isMorningShift);
                    }
                }
                break;
            case 3:
                if (NewRequirement == shift.getStorageEmployees().size()) {
                    System.out.println("Requirement is already set to " + NewRequirement);
                    return;
                } else if (NewRequirement < shift.getStorageEmployees().size()) {
                    while (shift.getStorageEmployees().size() > NewRequirement) {
                        shift.getStorageEmployees().removeLast();
                    }
                } else {
                    for (Employee w : workers) {
                        if (w.hasRole(new StorageEmployee()) && !shift.getStorageEmployees().contains(w) && !branch.hasShiftLimitation(w, date, isMorningShift)) {
                            shift.getStorageEmployees().add(w);
                            if (shift.getStorageEmployees().size() == NewRequirement) {
                                break;
                            }
                        }
                    }
                    if (shift.getStorageEmployees().size() < NewRequirement) {
                        System.out.println("Not enough workers for the shift.");
                        throw new NotEnoughWorkers(" date: " + date + " and isMorningShift: " + isMorningShift);
                    }
                }
                break;
            case 4:
                if (NewRequirement == shift.getDeliveriers().size()) {
                    System.out.println("Requirement is already set to " + NewRequirement);
                    return;
                } else if (NewRequirement < shift.getDeliveriers().size()) {
                    while (shift.getDeliveriers().size() > NewRequirement) {
                        shift.getDeliveriers().removeLast();
                    }
                } else {
                    for (Employee w : workers) {
                        if (w.hasRole(new DeliveryPerson()) && !shift.getDeliveriers().contains(w) && !branch.hasShiftLimitation(w, date, isMorningShift)) {
                            shift.getDeliveriers().add(w);
                            if (shift.getDeliveriers().size() == NewRequirement) {
                                break;
                            }
                        }
                    }
                    if (shift.getDeliveriers().size() < NewRequirement) {
                        System.out.println("Not enough workers for the shift.");
                        throw new NotEnoughWorkers(" date: " + date + " and isMorningShift: " + isMorningShift);
                    }
                }
                break;
            default:
                System.out.println("Invalid choice.");
                throw new IllegalArgumentException();
        }
    }
}
