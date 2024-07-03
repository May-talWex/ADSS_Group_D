package HR.Domain;

import HR.Data.ShiftsDAO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShiftController {
    private static ShiftsDAO shiftDAO = new ShiftsDAO();

    public static Shift createShift(Branch branch) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Creating a new shift for branch " + branch.getName());

        LocalDate date = inputShiftDate(scanner);
        System.out.println("Is this a morning shift? (true/false): ");
        boolean isMorningShift = Boolean.parseBoolean(scanner.nextLine());

        return new Shift(isMorningShift, date);
    }

    private static LocalDate inputShiftDate(Scanner scanner) {
        System.out.println("Enter the day of the shift (1-31):");
        int day = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the month of the shift (1-12):");
        int month = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the year of the shift:");
        int year = Integer.parseInt(scanner.nextLine());
        return LocalDate.of(year, month, day);
    }

    public List<Shift> getAllShifts(Branch branch) {
        return shiftDAO.getAllShifts(branch);
    }

    public Shift getShift(LocalDate date, boolean isMorningShift, Branch branch) {
        return shiftDAO.getShift(date, isMorningShift, branch);
    }

    public void updateShift(Shift shift, Branch branch) {
        shiftDAO.updateShift(shift, branch);
    }

    public void deleteShift(LocalDate date, boolean isMorningShift, int branchId) {
        shiftDAO.deleteShift(date, isMorningShift, branchId);
    }
}
