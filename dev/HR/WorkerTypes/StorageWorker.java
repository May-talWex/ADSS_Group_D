package HR.WorkerTypes;

import HR.WorkerType;

public class StorageWorker implements WorkerType {
    @Override
    public String getType() {
        return "StorageWorker";
    }

    @Override
    public boolean isAbleToManage() {
        return false;
    }

    @Override
    public boolean isAbleToDeliver() {
        return false;
    }

    @Override
    public boolean isAbleToBeCashier() {
        return false;
    }

    @Override
    public boolean isAbleToBeStorageWorker() {
        return true;
    }

    @Override
    public boolean isAbleToSetShifts() {
        return false;
    }
}
