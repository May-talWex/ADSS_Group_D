package HR.Domain;

import HR.Domain.Exceptions.NotEnoughWorkers;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ScheduleController {

    public static void updateShiftRequirements(Branch branch) throws NotEnoughWorkers {
        int newRequirement;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Updating shift requirements for branch " + branch.getName());

        System.out.println("Enter the day of the shift (1-31):");
        int day = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the month of the shift (1-12):");
        int month = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the year of the shift:");
        int year = Integer.parseInt(scanner.nextLine());

        LocalDate date = LocalDate.of(year, month, day);

        System.out.println("Is this a morning shift? (true/false): ");
        boolean isMorningShift = Boolean.parseBoolean(scanner.nextLine());

        if (branch.getSchedule().doesShiftExist(date, isMorningShift)) {
            System.out.println("Shift exists. Select the role to update:");
            System.out.println("1. Shift Manager");
            System.out.println("2. Cashier");
            System.out.println("3. Storage");
            System.out.println("4. Delivery");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Enter the new requirement for Shift Manager:");
                    newRequirement = Integer.parseInt(scanner.nextLine());
                    if (newRequirement <= 0) {
                        System.out.println("A Shift Manager is required for the shift. Requirement must be greater than 0.");
                        return;
                    }
                    branch.getSchedule().updateShiftRequirement(date, isMorningShift, choice, newRequirement, branch);
                    System.out.println("Shift Manager requirement updated.");
                    break;
                case 2:
                    System.out.println("Enter the new requirement for Cashier:");
                    newRequirement = Integer.parseInt(scanner.nextLine());
                    if (newRequirement <= 0) {
                        System.out.println("A Cashier is required for the shift. Requirement must be greater than 0.");
                        return;
                    }
                    branch.getSchedule().updateShiftRequirement(date, isMorningShift, choice, newRequirement, branch);
                    System.out.println("Cashier requirement updated.");
                    break;
                case 3:
                    System.out.println("Enter the new requirement for Storage:");
                    newRequirement = Integer.parseInt(scanner.nextLine());
                    if (newRequirement <= 0) {
                        System.out.println("A Storage Employee is required for the shift. Requirement must be greater than 0.");
                        return;
                    }
                    branch.getSchedule().updateShiftRequirement(date, isMorningShift, choice, newRequirement, branch);
                    System.out.println("Storage requirement updated.");
                    break;
                case 4:
                    System.out.println("Enter the new requirement for Delivery:");
                    newRequirement = Integer.parseInt(scanner.nextLine());
                    if (newRequirement <= 0) {
                        System.out.println("A Delivery Person is required for the shift. Requirement must be greater than 0.");
                        return;
                    }
                    branch.getSchedule().updateShiftRequirement(date, isMorningShift, choice, newRequirement, branch);
                    System.out.println("Delivery requirement updated.");
                    break;
                default:
                    System.out.println("Invalid choice. No updates made.");
                    break;
            }
        } else {
            System.out.println("Shift does not exist for the specified date and time.");
        }
    }

    public void addShift(LocalDate date, List<Shift> shifts, Schedule schedule) {
        try {
            schedule.addShift(date, shifts);
            System.out.println("Shift added successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeShift(LocalDate date, boolean isMorningShift, Schedule schedule) {
        try {
            schedule.removeShift(date, isMorningShift);
            System.out.println("Shift removed successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void printWeeklySchedule(Branch branch) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Weekly schedule for branch " + branch.getName());
        System.out.println("Enter the month number: ");
        int month = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the year: ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the week number: ");
        int week = Integer.parseInt(scanner.nextLine());
        LocalDate date = LocalDate.of(year, month, 1 + 7 * (week - 1));
        for (int i = 0; i < 7; i++) {
            printShifts(date.plusDays(i), branch.getSchedule());
        }
    }

    public static void generateShift(Branch branch, Shift shift) {
        try {
            branch.getSchedule().generateShift(branch, shift.getDate(), shift.isMorningShift());
            System.out.println("Shift generated successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void printShifts(LocalDate date, Schedule schedule) {
        List<Shift> shifts = schedule.getShifts(date);
        if (shifts != null && !shifts.isEmpty()) {
            System.out.println("Shifts on " + date + ":");
            for (Shift shift : shifts) {
                System.out.println(shift);
            }
        } else {
            System.out.println("No shifts found on " + date);
        }
    }

    public boolean doesShiftExist(LocalDate date, boolean isMorningShift, Schedule schedule) {
        return schedule.doesShiftExist(date, isMorningShift);
    }
}
