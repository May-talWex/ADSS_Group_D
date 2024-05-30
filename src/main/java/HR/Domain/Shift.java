package HR.Domain;

import HR.Domain.WorkerTypes.Cashier;
import HR.Domain.WorkerTypes.DeliveryPerson;
import HR.Domain.WorkerTypes.ShiftManager;
import HR.Domain.WorkerTypes.StorageWorker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Shift {
    private boolean isMorningShift;
    private LocalDate date;
    private List<Worker> shiftManagers;
    private List<Worker> cashiers;
    private List<Worker> storageWorkers;
    private List<Worker> deliveriers;

    public Shift(boolean isMorningShift, LocalDate date) {
        this.isMorningShift = isMorningShift;
        this.date = date;
        this.shiftManagers = new ArrayList<>();
        this.cashiers = new ArrayList<>();
        this.storageWorkers = new ArrayList<>();
        this.deliveriers = new ArrayList<>();
    }

    public Shift(boolean isMorningShift, LocalDate date, List<Worker> shiftManagers, List<Worker> cashiers, List<Worker> storageWorkers, List<Worker> deliveriers) {
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

    public List<Worker> getShiftManagers() {
        return shiftManagers;
    }

    public List<Worker> getCashiers() {
        return cashiers;
    }

    public List<Worker> getStorageWorkers() {
        return storageWorkers;
    }

    public List<Worker> getDeliveriers() {
        return deliveriers;
    }

    public void addShiftManager(Worker shiftManager) {
        if (!shiftManager.getPossiblePositions().contains(new ShiftManager())) {
            System.out.println("Worker is not able to be a shift manager.");
            return;
        }
        for (Worker w : shiftManagers) {
            if (w.equals(shiftManager)) {
                System.out.println("Worker is already a shift manager.");
                return;
            }
        }
        System.out.println("Worker added as shift manager.");
        shiftManagers.add(shiftManager);
    }

    public void addCashier(Worker cashier) {
        if (!cashier.getPossiblePositions().contains(new Cashier())) {
            System.out.println("Worker is not able to be a cashier.");
            return;
        }
        for (Worker w : cashiers) {
            if (w.equals(cashier)) {
                System.out.println("Worker is already a cashier.");
                return;
            }
        }
        System.out.println("Worker added as cashier.");
        cashiers.add(cashier);
    }

    public void addStorageWorker(Worker storageWorker) {
        if (!storageWorker.getPossiblePositions().contains(new StorageWorker())) {
            System.out.println("Worker is not able to be a storage worker.");
            return;
        }
        for (Worker w : storageWorkers) {
            if (w.equals(storageWorker)) {
                System.out.println("Worker is already a storage worker.");
                return;
            }
        }
        storageWorkers.add(storageWorker);
    }

    public void addDeliverier(Worker deliverier) {
        if (!deliverier.getPossiblePositions().contains(new DeliveryPerson())) {
            System.out.println("Worker is not able to be a deliverier.");
            return;
        }
        for (Worker w : deliveriers) {
            if (w.equals(deliverier)) {
                System.out.println("Worker is already a deliverier.");
                return;
            }
        }
        System.out.println("Worker added as deliverier.");
        deliveriers.add(deliverier);
    }

    public void removeShiftManager(Worker shiftManager) {
        for (Worker w : shiftManagers) {
            if (w.equals(shiftManager)) {
                shiftManagers.remove(w);
                System.out.println("Worker removed from shift managers.");
                return;
            }
        }
        System.out.println("Worker is not a shift manager.");
    }

    public void removeCashier(Worker cashier) {
        for (Worker w : cashiers) {
            if (w.equals(cashier)) {
                cashiers.remove(w);
                System.out.println("Worker removed from cashiers.");
                return;
            }
        }
        System.out.println("Worker is not a cashier.");
    }

    public void removeStorageWorker(Worker storageWorker) {
        for (Worker w : storageWorkers) {
            if (w.equals(storageWorker)) {
                storageWorkers.remove(w);
                System.out.println("Worker removed from storage workers.");
                return;
            }
        }
        System.out.println("Worker is not a storage worker.");
    }

    public void removeDeliverier(Worker deliverier) {
        for (Worker w : deliveriers) {
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
        for (Worker w : shiftManagers) {
            System.out.println("    " + ++i + ". " + w.getName());
        }
        i = 0;
        System.out.println("Cashiers:");
        for (Worker w : cashiers) {
            System.out.println("    " + ++i + ". " + w.getName());
        }
        i = 0;
        System.out.println("Storage workers:");
        for (Worker w : storageWorkers) {
            System.out.println("    " + ++i + ". " + w.getName());
        }
        i = 0;
        System.out.println("Deliveriers:");
        for (Worker w : deliveriers) {
            System.out.println("    " + ++i + ". " + w.getName());
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder("Shift date: " + date + "\nMorning shift: " + isMorningShift + "\nShift managers:\n");
        int i = 0;
        for (Worker w : shiftManagers) {
            result.append("    ").append(++i).append(". ").append(w.getName()).append("\n");
        }
        i = 0;
        result.append("Cashiers:\n");
        for (Worker w : cashiers) {
            result.append("    ").append(++i).append(". ").append(w.getName()).append("\n");
        }
        i = 0;
        result.append("Storage workers:\n");
        for (Worker w : storageWorkers) {
            result.append("    ").append(++i).append(". ").append(w.getName()).append("\n");
        }
        i = 0;
        result.append("Deliveriers:\n");
        for (Worker w : deliveriers) {
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
