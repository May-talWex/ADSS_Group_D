package HR.Domain;

import java.time.LocalDate;
import java.util.*;

public class ScheduleController {

    public static void GenerateSchedule(Branch branch) {
        Schedule schedule = branch.getSchedule();
        List<ShiftLimitation> shiftLimitations = branch.getShiftLimitations();
        List<Employee> workers = branch.getWorkers();
        List<Shift> shifts = new ArrayList<>();
        // TO DO: Implement the logic to generate the schedule

    }

    public void addShift(LocalDate date, List<Shift> shifts, Schedule schedule) {
        try {
            schedule.addShift(date, shifts);
            System.out.println("Shift added successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeShift(LocalDate date, boolean isMorningShift , Schedule schedule) {
        try {
            schedule.removeShift(date, isMorningShift);
            System.out.println("Shift removed successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void printWeeklySchedule(LocalDate startDate , Schedule schedule) {
        schedule.printWeeklySchedule(startDate);
    }

    public void generateShift(Branch branch, LocalDate date, boolean isMorningShift , Schedule schedule) {
        try {
            schedule.generateShift(branch, date, isMorningShift);
            System.out.println("Shift generated successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void printShifts(LocalDate date , Schedule schedule) {
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

    public boolean doesShiftExist(LocalDate date, boolean isMorningShift , Schedule schedule) {
        return schedule.doesShiftExist(date, isMorningShift);
    }
}
