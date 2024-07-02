package HR.Domain;

import HR.Data.ShiftsDAO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShiftController {
    private static ShiftsDAO shiftDAO;

    public ShiftController() {
        this.shiftDAO = new ShiftsDAO();
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

    public static Shift createShift(Branch branch) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Creating a new shift for branch " + branch.getName());
        System.out.println("Enter the day of the shift (1-31):");
        int day = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the month of the shift (1-12):");
        int month = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the year of the shift:");
        int year = Integer.parseInt(scanner.nextLine());
        LocalDate date = LocalDate.of(year, month, day);
        System.out.println("Is this a morning shift? (true/false): ");
        boolean isMorningShift = Boolean.parseBoolean(scanner.nextLine());
        Shift shift = new Shift(isMorningShift, date);
        shiftDAO.addShift(shift, branch.getBranchId());
        return shift;
    }

    public List<Shift> getAllShifts() {
        return shiftDAO.getAllShifts();
    }

    public Shift getShift(LocalDate date, boolean isMorningShift, int branchId) {
        return shiftDAO.getShift(date, isMorningShift);
    }

    public void updateShift(Shift shift, int branchId) {
        shiftDAO.updateShift(shift, branchId);
    }

    public void deleteShift(LocalDate date, boolean isMorningShift, int branchId) {
        shiftDAO.deleteShift(date, isMorningShift, branchId);
    }

}
