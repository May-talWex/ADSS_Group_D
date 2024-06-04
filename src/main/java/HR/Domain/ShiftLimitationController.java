package HR.Domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class ShiftLimitationController {

    public static void addShiftLimitation(Employee employee) {
        System.out.println("Enter the day of the limitation (1-31):");
        try {
            Integer.parseInt(System.console().readLine());
        } catch (Exception e) {
            System.out.println("Invalid input. Limitation not added.");
            return;
        }
        int day = Integer.parseInt(System.console().readLine());
        System.out.println("Enter the month of the limitation (1-12):");
        try {
            Integer.parseInt(System.console().readLine());
        } catch (Exception e) {
            System.out.println("Invalid input. Limitation not added.");
            return;
        }
        int month = Integer.parseInt(System.console().readLine());
        System.out.println("Enter the year of the limitation:");
        try {
            Integer.parseInt(System.console().readLine());
        } catch (Exception e) {
            System.out.println("Invalid input. Limitation not added.");
            return;
        }
        int year = Integer.parseInt(System.console().readLine());
        try {
            LocalDate date = LocalDate.of(year, month, day);
        } catch (Exception e) {
            System.out.println("Invalid date. Limitation not added.");
            return;
        }
        LocalDate date = LocalDate.of(year, month, day);
        System.out.println("Is this a morning shift? (true/false): ");
        try {
            Boolean.parseBoolean(System.console().readLine());
        } catch (Exception e) {
            System.out.println("Invalid input. Limitation not added.");
            return;
        }
        boolean isMorningShift = Boolean.parseBoolean(System.console().readLine());
        ShiftLimitation limitation = new ShiftLimitation(employee, date, isMorningShift);
        try {
            employee.getBranch().addShiftLimitation(employee, limitation);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeShiftLimitation(Employee employee) {
        System.out.println("Enter the day of the limitation (1-31):");
        try {
            Integer.parseInt(System.console().readLine());
        } catch (Exception e) {
            System.out.println("Invalid input. Limitation not removed.");
            return;
        }
        int day = Integer.parseInt(System.console().readLine());
        System.out.println("Enter the month of the limitation (1-12):");
        try {
            Integer.parseInt(System.console().readLine());
        } catch (Exception e) {
            System.out.println("Invalid input. Limitation not removed.");
            return;
        }
        int month = Integer.parseInt(System.console().readLine());
        System.out.println("Enter the year of the limitation:");
        try {
            Integer.parseInt(System.console().readLine());
        } catch (Exception e) {
            System.out.println("Invalid input. Limitation not removed.");
            return;
        }
        int year = Integer.parseInt(System.console().readLine());
        try {
            LocalDate date = LocalDate.of(year, month, day);
        } catch (Exception e) {
            System.out.println("Invalid date. Limitation not removed.");
            return;
        }
        LocalDate date = LocalDate.of(year, month, day);
        System.out.println("Is this a morning shift? (true/false): ");
        try {
            Boolean.parseBoolean(System.console().readLine());
        } catch (Exception e) {
            System.out.println("Invalid input. Limitation not removed.");
            return;
        }
        boolean isMorningShift = Boolean.parseBoolean(System.console().readLine());
        ShiftLimitation limitation = new ShiftLimitation(employee, date, isMorningShift);
        try {
            employee.getBranch().removeShiftLimitation(employee, limitation);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
