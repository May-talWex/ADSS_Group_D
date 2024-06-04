package HR.Domain;

import java.time.LocalDate;
import java.util.*;

public class ScheduleController {
    private Schedule schedule;

    public ScheduleController() {
        this.schedule = new Schedule();
    }

    public void addShift(LocalDate date, List<Shift> shifts) {
        try {
            schedule.addShift(date, shifts);
            System.out.println("Shift added successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeShift(LocalDate date, boolean isMorningShift) {
        try {
            schedule.removeShift(date, isMorningShift);
            System.out.println("Shift removed successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void printWeeklySchedule(LocalDate startDate) {
        schedule.printWeeklySchedule(startDate);
    }

    public void generateShift(Branch branch, LocalDate date, boolean isMorningShift) {
        try {
            schedule.generateShift(branch, date, isMorningShift);
            System.out.println("Shift generated successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void printShifts(LocalDate date) {
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

    public boolean doesShiftExist(LocalDate date, boolean isMorningShift) {
        return schedule.doesShiftExist(date, isMorningShift);
    }

    // Additional methods to handle other operations as needed
}
