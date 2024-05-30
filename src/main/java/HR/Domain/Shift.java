package HR.Domain;

import HR.Domain.EmployeeTypes.Cashier;
import HR.Domain.EmployeeTypes.DeliveryPerson;
import HR.Domain.EmployeeTypes.ShiftManager;
import HR.Domain.EmployeeTypes.StorageEmployee;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Shift {
    private boolean isMorningShift;
    private LocalDate date;
    private List<Employee> shiftManagers;
    private List<Employee> cashiers;
    private List<Employee> storageWorkers;
    private List<Employee> deliveriers;

    public Shift(boolean isMorningShift, LocalDate date) {
        this.isMorningShift = isMorningShift;
        this.date = date;
        this.shiftManagers = new ArrayList<>();
        this.cashiers = new ArrayList<>();
        this.storageWorkers = new ArrayList<>();
        this.deliveriers = new ArrayList<>();
    }
    @JsonCreator
    public Shift JSONtoClass(
            @JsonProperty("isMorningShift") boolean isMorningShift,
            @JsonProperty("date") LocalDate date,
            @JsonProperty("shiftManagers") List<Employee> shiftManagers,
            @JsonProperty("cashiers") List<Employee> cashiers,
            @JsonProperty("storageWorkers") List<Employee> storageWorkers,
            @JsonProperty("deliveries") List<Employee> deliveries) {

        if (shiftManagers == null) {
            shiftManagers = new ArrayList<>();
        }
        if (cashiers == null) {
            cashiers = new ArrayList<>();
        }
        if (storageWorkers == null) {
            storageWorkers = new ArrayList<>();
        }
        if (deliveries == null) {
            deliveries = new ArrayList<>();
        }
        return new Shift(isMorningShift, date, shiftManagers, cashiers, storageWorkers, deliveries);
    }



    public Shift(boolean isMorningShift, LocalDate date, List<Employee> shiftManagers, List<Employee> cashiers, List<Employee> storageWorkers, List<Employee> deliveriers) {
        this.isMorningShift = isMorningShift;
        this.date = date;
        this.shiftManagers = shiftManagers;
        this.cashiers = cashiers;
        this.storageWorkers = storageWorkers;
        this.deliveriers = deliveriers;
    }

    public boolean isMorningShift() {
        return isMorningShift;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Employee> getShiftManagers() {
        return shiftManagers;
    }

    public List<Employee> getCashiers() {
        return cashiers;
    }

    public List<Employee> getStorageWorkers() {
        return storageWorkers;
    }

    // Ido Haya Po

    public List<Employee> getDeliveriers() {
        return deliveriers;
    }

    public void addShiftManager(Employee shiftManager) {
        if (!shiftManager.getPossiblePositions().contains(new ShiftManager())) {
            System.out.println("Worker is not able to be a shift manager.");
            return;
        }
        for (Employee w : shiftManagers) {
            if (w.equals(shiftManager)) {
                System.out.println("Worker is already a shift manager.");
                return;
            }
        }
        System.out.println("Worker added as shift manager.");
        shiftManagers.add(shiftManager);
    }

    public void addCashier(Employee cashier) {
        if (!cashier.getPossiblePositions().contains(new Cashier())) {
            System.out.println("Worker is not able to be a cashier.");
            return;
        }
        for (Employee w : cashiers) {
            if (w.equals(cashier)) {
                System.out.println("Worker is already a cashier.");
                return;
            }
        }
        System.out.println("Worker added as cashier.");
        cashiers.add(cashier);
    }

    public void addStorageWorker(Employee storageWorker) {
        if (!storageWorker.getPossiblePositions().contains(new StorageEmployee())) {
            System.out.println("Worker is not able to be a storage worker.");
            return;
        }
        for (Employee w : storageWorkers) {
            if (w.equals(storageWorker)) {
                System.out.println("Worker is already a storage worker.");
                return;
            }
        }
        storageWorkers.add(storageWorker);
    }

    public void addDeliverier(Employee deliverier) {
        if (!deliverier.getPossiblePositions().contains(new DeliveryPerson())) {
            System.out.println("Worker is not able to be a deliverier.");
            return;
        }
        for (Employee w : deliveriers) {
            if (w.equals(deliverier)) {
                System.out.println("Worker is already a deliverier.");
                return;
            }
        }
        System.out.println("Worker added as deliverier.");
        deliveriers.add(deliverier);
    }

    public void removeShiftManager(Employee shiftManager) {
        for (Employee w : shiftManagers) {
            if (w.equals(shiftManager)) {
                shiftManagers.remove(w);
                System.out.println("Worker removed from shift managers.");
                return;
            }
        }
        System.out.println("Worker is not a shift manager.");
    }

    public void removeCashier(Employee cashier) {
        for (Employee w : cashiers) {
            if (w.equals(cashier)) {
                cashiers.remove(w);
                System.out.println("Worker removed from cashiers.");
                return;
            }
        }
        System.out.println("Worker is not a cashier.");
    }

    public void removeStorageWorker(Employee storageWorker) {
        for (Employee w : storageWorkers) {
            if (w.equals(storageWorker)) {
                storageWorkers.remove(w);
                System.out.println("Worker removed from storage workers.");
                return;
            }
        }
        System.out.println("Worker is not a storage worker.");
    }

    public void removeDeliverier(Employee deliverier) {
        for (Employee w : deliveriers) {
            if (w.equals(deliverier)) {
                deliveriers.remove(w);
                System.out.println("Worker removed from deliveriers.");
                return;
            }
        }
        System.out.println("Worker is not a deliverier.");
    }

    public void printShift() {
        System.out.println("Shift date: " + date);
        System.out.println((isMorningShift ? "Morning" : "Evening") + " shift");
        System.out.println("Shift managers:");
        int i = 0;
        for (Employee w : shiftManagers) {
            System.out.println("    " + ++i + ". " + w.getName());
        }
        i = 0;
        System.out.println("Cashiers:");
        for (Employee w : cashiers) {
            System.out.println("    " + ++i + ". " + w.getName());
        }
        i = 0;
        System.out.println("Storage workers:");
        for (Employee w : storageWorkers) {
            System.out.println("    " + ++i + ". " + w.getName());
        }
        i = 0;
        System.out.println("Deliveriers:");
        for (Employee w : deliveriers) {
            System.out.println("    " + ++i + ". " + w.getName());
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder("Shift date: " + date + "\nMorning shift: " + isMorningShift + "\nShift managers:\n");
        int i = 0;
        for (Employee w : shiftManagers) {
            result.append("    ").append(++i).append(". ").append(w.getName()).append("\n");
        }
        i = 0;
        result.append("Cashiers:\n");
        for (Employee w : cashiers) {
            result.append("    ").append(++i).append(". ").append(w.getName()).append("\n");
        }
        i = 0;
        result.append("Storage workers:\n");
        for (Employee w : storageWorkers) {
            result.append("    ").append(++i).append(". ").append(w.getName()).append("\n");
        }
        i = 0;
        result.append("Deliveriers:\n");
        for (Employee w : deliveriers) {
            result.append("    ").append(++i).append(". ").append(w.getName()).append("\n");
        }
        return result.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Shift)) {
            return false;
        }
        Shift shift = (Shift) obj;
        return isMorningShift == shift.isMorningShift && date.equals(shift.date);
    }

    public void clear() {
        shiftManagers.clear();
        cashiers.clear();
        storageWorkers.clear();
        deliveriers.clear();
    }

}
