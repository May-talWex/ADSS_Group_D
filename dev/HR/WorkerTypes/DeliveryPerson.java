package HR.WorkerTypes;

import HR.WorkerType;

public class DeliveryPerson implements WorkerType {
    @Override
    public String getType() {
        return "Delivery";
    }


    @Override
    public boolean isAbleToDeliver() {
        return true;
    }
}
