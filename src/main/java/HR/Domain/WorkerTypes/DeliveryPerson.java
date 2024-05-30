package HR.Domain.WorkerTypes;

import HR.Domain.WorkerType;

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
