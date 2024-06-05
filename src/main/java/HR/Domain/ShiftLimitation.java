package HR.Domain;

import java.time.LocalDate;

public class ShiftLimitation {

    private Employee employee;
    private LocalDate date;
    private boolean isMorningShift;

    public ShiftLimitation(Employee employee, LocalDate date, boolean isMorningShift) {
        this.employee = employee;
        this.date = date;
        this.isMorningShift = isMorningShift;
    }

    public boolean equals(ShiftLimitation other) {
        return this.employee.equals(other.employee) && this.date.equals(other.date) && this.isMorningShift == other.isMorningShift;
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

}
