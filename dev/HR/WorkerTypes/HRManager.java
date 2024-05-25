package HR.WorkerTypes;

import HR.WorkerType;

public class HRManager implements WorkerType {

    public String getType() {
        return "HR Manager";
    }

    public boolean isAbleToManage() {
        return false;
    }

    public boolean isAbleToDeliver() {
        return false;
    }

    public boolean isAbleToBeCashier() {
        return false;
    }

    public boolean isAbleToBeStorageWorker() {
        return false;
    }

    public boolean isAbleToSetShifts() {
        return true;
    }
}
