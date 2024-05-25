package HR;

public interface WorkerType {
    public String getType();

    public boolean isAbleToManage();

    public boolean isAbleToDeliver();

    public boolean isAbleToBeCashier();

    public boolean isAbleToBeStorageWorker();

    public boolean isAbleToSetShifts();

    public default boolean equals(WorkerType other) {
        return this.getType().equals(other.getType());
    }
}
