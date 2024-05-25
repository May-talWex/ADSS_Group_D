package HR.WorkerTypes;

import HR.WorkerType;

public class ShiftManager implements WorkerType {

    @Override
    public String getType() {
        return "Shift Manager";
    }

    @Override
    public boolean isAbleToManage() {
        return true;
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
        return false;
    }

    @Override
    public boolean isAbleToSetShifts() {
        return false;
    }
}
