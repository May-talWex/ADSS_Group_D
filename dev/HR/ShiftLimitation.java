package HR;

import java.time.LocalDate;

public class ShiftLimitation {

    private Worker worker;
    private LocalDate date;
    private boolean isMorningShift;

    public ShiftLimitation(Worker worker, LocalDate date, boolean isMorningShift) {
        this.worker = worker;
        this.date = date;
        this.isMorningShift = isMorningShift;
    }

    public boolean equals(ShiftLimitation other) {
        return this.worker.equals(other.worker) && this.date.equals(other.date) && this.isMorningShift == other.isMorningShift;
    }

    public Worker getWorker() {
        return worker;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isMorningShift() {
        return isMorningShift;
    }

}
