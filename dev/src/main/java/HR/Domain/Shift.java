package HR.Domain;

import HR.Domain.EmployeeTypes.Cashier;
import HR.Domain.EmployeeTypes.DeliveryPerson;
import HR.Domain.EmployeeTypes.ShiftManager;
import HR.Domain.EmployeeTypes.StorageEmployee;
import HR.Domain.Exceptions.EmployeeDoesNotHaveRole;
import HR.Domain.Exceptions.ShiftAlreadyExists;
import HR.Domain.Exceptions.ShiftDoesntExist;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Shift {
    private boolean isMorningShift;
    private LocalDate date;
    private List<Employee> shiftManagers;
    private List<Employee> cashiers;
    private List<Employee> storageEmployees;
    private List<Employee> deliveriers;

    public Shift(boolean isMorningShift, LocalDate date) {
        this.isMorningShift = isMorningShift;
        this.date = date;
        this.shiftManagers = new ArrayList<>();
        this.cashiers = new ArrayList<>();
        this.storageEmployees = new ArrayList<>();
        this.deliveriers = new ArrayList<>();
    }


    public Shift(boolean isMorningShift, LocalDate date, List<Employee> shiftManagers, List<Employee> cashiers, List<Employee> storageEmployees, List<Employee> deliveriers) {
        this.isMorningShift = isMorningShift;
        this.date = date;
        this.shiftManagers = shiftManagers;
        this.cashiers = cashiers;
        this.storageEmployees = storageEmployees;
        this.deliveriers = deliveriers;
    }

    public String toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            return null;
        }
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

    public List<Employee> getStorageEmployees() {
        return storageEmployees;
    }

    public List<Employee> getDeliveriers() {
        return deliveriers;
    }

    public void addShiftManager(Employee shiftManager) throws Exception {
        if (!shiftManager.getPossiblePositions().contains(new ShiftManager())) {
            throw new EmployeeDoesNotHaveRole("Shift Manager");
        }
        if (this.getCashiers().contains(shiftManager) || this.getStorageEmployees().contains(shiftManager) || this.getDeliveriers().contains(shiftManager)) {
            throw new ShiftAlreadyExists("Employee is already assigned to the shift.");
        }
        for (Employee w : shiftManagers) {
            if (w.equals(shiftManager)) {
                throw new ShiftAlreadyExists("Employee is already assigned to the shift as a shift manager.");
            }
        }
        System.out.println("Employee added as shift manager.");
        shiftManagers.add(shiftManager);
    }

    public void addCashier(Employee cashier) throws Exception {
        if (!cashier.getPossiblePositions().contains(new Cashier())) {
            System.out.println("Employee is not able to be a cashier.");
            return;
        }
        if (this.getShiftManagers().contains(cashier) || this.getStorageEmployees().contains(cashier) || this.getDeliveriers().contains(cashier)) {
            throw new ShiftAlreadyExists("Employee is already assigned to the shift.");
        }
        for (Employee w : cashiers) {
            if (w.equals(cashier)) {
                throw new ShiftAlreadyExists("Employee is already assigned to the shift as a cashier.");
            }
        }
        System.out.println("Employee added as cashier.");
        cashiers.add(cashier);
    }

    public void addStorageEmployee(Employee storageEmployee) throws Exception {
        if (!storageEmployee.getPossiblePositions().contains(new StorageEmployee())) {
            throw new EmployeeDoesNotHaveRole("Storage Employee");
        }
        if (this.getShiftManagers().contains(storageEmployee) || this.getCashiers().contains(storageEmployee) || this.getDeliveriers().contains(storageEmployee)) {
            throw new ShiftAlreadyExists("Employee is already assigned to the shift.");
        }
        for (Employee w : storageEmployees) {
            if (w.equals(storageEmployee)) {
                throw new ShiftAlreadyExists("Employee is already assigned to the shift as a storage worker.");
            }
        }
        storageEmployees.add(storageEmployee);
    }

    public void addDeliverier(Employee deliverier) throws Exception {
        if (!deliverier.getPossiblePositions().contains(new DeliveryPerson())) {
            throw new EmployeeDoesNotHaveRole("Delivery Person");
        }
        if (this.getShiftManagers().contains(deliverier) || this.getCashiers().contains(deliverier) || this.getStorageEmployees().contains(deliverier)) {
            throw new ShiftAlreadyExists("Employee is already assigned to the shift.");
        }
        for (Employee w : deliveriers) {
            if (w.equals(deliverier)) {
                throw new ShiftAlreadyExists("Employee is already assigned to the shift as a deliverier.");
            }
        }
        System.out.println("Employee added as deliverier.");
        deliveriers.add(deliverier);
    }

    public void removeShiftManager(Employee shiftManager) throws Exception {
        for (Employee w : shiftManagers) {
            if (w.equals(shiftManager)) {
                shiftManagers.remove(w);
                System.out.println("Employee removed from shift managers.");
                return;
            }
        }
        throw new ShiftDoesntExist("Employee is not a shift manager in this shift.");
    }

    public void removeCashier(Employee cashier) throws Exception {
        for (Employee w : cashiers) {
            if (w.equals(cashier)) {
                cashiers.remove(w);
                System.out.println("Employee removed from cashiers.");
                return;
            }
        }
        throw new ShiftDoesntExist("Employee is not a cashier in this shift.");
    }

    public void removeStorageEmployee(Employee storageEmployee) throws Exception {
        for (Employee w : storageEmployees) {
            if (w.equals(storageEmployee)) {
                storageEmployees.remove(w);
                System.out.println("Employee removed from storage workers.");
                return;
            }
        }
        throw new ShiftDoesntExist("Employee is not a storage worker in this shift.");
    }

    public void removeDeliverier(Employee deliverier) throws Exception {
        for (Employee w : deliveriers) {
            if (w.equals(deliverier)) {
                deliveriers.remove(w);
                System.out.println("Employee removed from deliveriers.");
                return;
            }
        }
        throw new ShiftDoesntExist("Employee is not a deliverier in this shift.");
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
        for (Employee w : storageEmployees) {
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
        for (Employee w : storageEmployees) {
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

    public EmployeeType getEmployeeType(Employee employee) {
        if (shiftManagers.contains(employee)) {
            return new ShiftManager();
        } else if (cashiers.contains(employee)) {
            return new Cashier();
        } else if (storageEmployees.contains(employee)) {
            return new StorageEmployee();
        } else if (deliveriers.contains(employee)) {
            return new DeliveryPerson();
        }
        return null;
    }

    public boolean contains(Employee employee) {
        return shiftManagers.contains(employee) ||
                cashiers.contains(employee) ||
                storageEmployees.contains(employee) ||
                deliveriers.contains(employee);
    }

    public int hashCode() {
        int sign = isMorningShift ? 1 : -1;
        return date.hashCode() * sign;
    }

    public void clear() {
        shiftManagers.clear();
        cashiers.clear();
        storageEmployees.clear();
        deliveriers.clear();
    }
    public void setShiftManagers(List<Employee> shiftManagers) {
        this.shiftManagers = shiftManagers;
    }

    public void setCashiers(List<Employee> cashiers) {
        this.cashiers = cashiers;
    }

    public void setStorageEmployees(List<Employee> storageEmployees) {
        this.storageEmployees = storageEmployees;
    }

    public void setDeliveriers(List<Employee> deliveriers) {
        this.deliveriers = deliveriers;
    }

    public String getShiftManagersString() {
        StringBuilder result = new StringBuilder();
        for (Employee w : shiftManagers) {
            result.append(w.getEmployeeId()).append(",");
        }
        return result.toString();
    }

    public String getCashiersString() {
        StringBuilder result = new StringBuilder();
        for (Employee w : cashiers) {
            result.append(w.getEmployeeId()).append(",");
        }
        return result.toString();
    }

    public String getStorageEmployeesString() {
        StringBuilder result = new StringBuilder();
        for (Employee w : storageEmployees) {
            result.append(w.getEmployeeId()).append(",");
        }
        return result.toString();
    }

    public String getDeliveriersString() {
        StringBuilder result = new StringBuilder();
        for (Employee w : deliveriers) {
            result.append(w.getEmployeeId()).append(",");
        }
        return result.toString();
    }
}

