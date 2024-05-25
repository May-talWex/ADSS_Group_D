package HR.WorkerTypes;

import HR.WorkerType;

public class DeliveryPerson implements WorkerType {
    @Override
    public String getType() {
        return "Delivery";
    }

    @Override
    public boolean isAbleToManage() {
        return false;
    }

    @Override
    public boolean isAbleToDeliver() {
        return true;
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
