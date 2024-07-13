package HR.Domain;

import java.time.LocalDate;
import java.util.Scanner;

public class ShiftController {

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

}
