package HR;

import java.time.LocalDate;
import java.util.*;

/*
 * TODO: Finish the implementation of the Schedule class
 * */
public class Schedule {
    private Map<LocalDate, List<Shift>> shifts;

    public Schedule() {
        this.shifts = new HashMap<>();
    }

    public void addShift(LocalDate date, List<Shift> shift) {
        List<Shift> shiftList = shifts.computeIfAbsent(date, k -> new ArrayList<>());
        if (shiftList.isEmpty()) {
            shiftList.addAll(shift);
        } else {
            for (Shift s : shift) {
                if (!shiftList.contains(s)) {
                    shiftList.add(s);
                } else {
                    System.out.println("Shift already exists.");
                    throw new IllegalArgumentException();
                }
            }
        }

    }

    public List<Shift> getShifts(LocalDate date) {
        return shifts.get(date);
    }

    public boolean doesShiftExist(LocalDate date, boolean isMorningShift) {
        List<Shift> shiftList = shifts.get(date);
        if (shiftList == null) {
            return false;
        }
        return shiftList.stream().anyMatch(shift -> shift.isMorningShift() == isMorningShift && shift.getDate().equals(date));
    }

    public void removeShift(LocalDate date, boolean isMorningShift) {
        boolean result = shifts.get(date).removeIf(shift -> shift.isMorningShift() == isMorningShift);
        if (result) {
            System.out.println("Shift removed.");
        } else {
            System.out.println("Shift not found.");
            throw new IllegalArgumentException();
        }
    }

    public void printWeeklySchedule(LocalDate startDate) {
        System.out.println("Weekly Schedule for the dates: " + startDate + " - " + startDate.plusDays(6));
        for (int i = 0; i < 7; i++) {
            LocalDate date = startDate.plusDays(i);
            List<Shift> shiftList = shifts.get(date);
            System.out.println(date + " - " + date.getDayOfWeek() + ":");
            if (shiftList == null) {
                System.out.println(date + ": No shifts");
            } else {
                System.out.println(date + ":");
                // Sorting the list by isDayShift in descending order
                shiftList.sort(Comparator.comparing(Shift::isMorningShift).reversed());
                for (Shift shift : shiftList) {
                    System.out.println("  " + shift);
                }
            }
        }
    }

}
