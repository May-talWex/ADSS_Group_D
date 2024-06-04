package HR.Domain;

import HR.Domain.EmployeeTypes.*;


public interface EmployeeType {
    public String getType();

    public default boolean isAbleToManage() {
        return false;
    }


    public default boolean isAbleToDeliver() {
        return false;
    }


    public default boolean isAbleToBeCashier() {
        return false;
    }

    public default boolean isAbleToBeStorageWorker() {
        return false;
    }

    public default boolean isAbleToSetShifts() {
        return false;
    }


    public default boolean equals(EmployeeType other) {
        System.out.println("EmployeeType: Checking if " + this.getType() + " equals " + other.getType());
        return this.getType().equals(other.getType());
    }

    public int hashCode();
}
