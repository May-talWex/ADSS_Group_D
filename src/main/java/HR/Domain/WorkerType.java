package HR.Domain;

public interface WorkerType {
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


    public default boolean equals(WorkerType other) {
        return this.getType().equals(other.getType());
    }
}
