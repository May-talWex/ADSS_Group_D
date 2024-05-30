package HR.Domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.LocalDate;

public class ShiftLimitation {

    private Employee worker;
    private LocalDate date;
    private boolean isMorningShift;

    public ShiftLimitation(Employee worker, LocalDate date, boolean isMorningShift) {
        this.worker = worker;
        this.date = date;
        this.isMorningShift = isMorningShift;
    }

    @JsonCreator
    public ShiftLimitation JSONtoClass(


    public boolean equals(ShiftLimitation other) {
        return this.worker.equals(other.worker) && this.date.equals(other.date) && this.isMorningShift == other.isMorningShift;
    }

    public Employee getWorker() {
        return worker;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isMorningShift() {
        return isMorningShift;
    }

}
