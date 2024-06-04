package HR.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Salary {
    @JsonProperty("amount")
    private float amount;
    //    @JsonProperty("worker")
//    private Employee worker;
    @JsonProperty("startDate")
    private LocalDate startDate;
    @JsonProperty("endDate")
    private LocalDate endDate;

    public Salary(float amount /*, Employee worker*/, LocalDate startDate, LocalDate endDate) {
        this.amount = amount;
//        this.worker = worker;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Salary() {
    }

    public float getSalaryAmount() {
        return amount;
    }

    /*public Employee getWorker() {
        return worker;
    }*/

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setSalaryAmount(float amount) {
        this.amount = amount;
    }

    /*public void setWorker(Employee worker) {
        this.worker = worker;
    }*/

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void printSalary() {
        System.out.println("Salary amount: " + amount);
        //System.out.println("Worker: " + worker.getName());
        System.out.println("Start date: " + startDate);
        System.out.println("End date: " + endDate);
    }

    public String toString() {
        return "Salary amount: " + amount + /*"\nWorker: " + worker.getName() + */"\nStart date: " + startDate + "\nEnd date: " + endDate;
    }


}
