package HR.WorkerTypes;

import HR.WorkerType;

public class Cashier implements WorkerType {

    @Override
    public String getType() {
        return "Cashier";
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
        return true;
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
