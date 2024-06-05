package HR.Domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Scanner;

public class ShiftLimitationController {

    public static void addShiftLimitation(Employee employee) {
        Scanner scanner = new Scanner(System.in);
        int day, month, year;

        try {
            System.out.println("Enter the day of the limitation (1-31):");
            day = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter the month of the limitation (1-12):");
            month = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter the year of the limitation:");
            year = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input: " + e.getMessage());
            return;
        }

        try {
            LocalDate date = LocalDate.of(year, month, day);

            System.out.println("Is this a morning shift? (true/false): ");
            boolean isMorningShift = Boolean.parseBoolean(scanner.nextLine());

            ShiftLimitation limitation = new ShiftLimitation(employee, date, isMorningShift);
            employee.getBranch().addShiftLimitation(employee, limitation);
        } catch (Exception e) {
            System.out.println("Error creating shift limitation: " + e.getMessage());
        }
    }

    public static void removeShiftLimitation(Employee employee) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Your current shift limitations are:");
        for (ShiftLimitation sl : employee.getBranch().getShiftLimitations()) {
            if (sl.getEmployee().equals(employee)) {
                System.out.println(sl);
            }
        }

        int day, month, year;
        try {
            System.out.println("Enter the day of the limitation (1-31):");
            day = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter the month of the limitation (1-12):");
            month = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter the year of the limitation:");
            year = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input. Limitation not removed.");
            return;
        }

        LocalDate date;
        try {
            date = LocalDate.of(year, month, day);
        } catch (Exception e) {
            System.out.println("Invalid date. Limitation not removed.");
            return;
        }

        boolean isMorningShift;
        try {
            System.out.println("Is this a morning shift? (true/false): ");
            isMorningShift = Boolean.parseBoolean(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input. Limitation not removed.");
            return;
        }

        ShiftLimitation limitation = new ShiftLimitation(employee, date, isMorningShift);
        try {
            employee.getBranch().removeShiftLimitation(employee, limitation);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
