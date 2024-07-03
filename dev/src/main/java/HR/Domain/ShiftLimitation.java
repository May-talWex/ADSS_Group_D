package HR.Domain;

import java.time.LocalDate;

public class ShiftLimitation {

    private final Employee employee;
    private final LocalDate date;
    private final boolean isMorningShift;

    public ShiftLimitation(Employee employee, LocalDate date, boolean isMorningShift) {
        this.employee = employee;
        this.date = date;
        this.isMorningShift = isMorningShift;
    }

    public boolean equals(ShiftLimitation other) {
        return this.employee.equals(other.employee) && this.date.equals(other.date) && this.isMorningShift == other.isMorningShift;
    }

    public int hashCode() {
        return employee.hashCode() + date.hashCode() * (isMorningShift ? 1 : -1);
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isMorningShift() {
        return isMorningShift;
    }

    public String toString() {
        return "Employee: " + employee + ", Date: " + date + ", Morning Shift: " + isMorningShift;
    }

}
