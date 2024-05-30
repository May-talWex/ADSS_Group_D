package HR.Domain.EmployeeTypes;

import HR.Domain.EmployeeType;

public class DeliveryPerson implements EmployeeType {
    @Override
    public String getType() {
        return "Delivery";
    }


    @Override
    public boolean isAbleToDeliver() {
        return true;
    }
}
