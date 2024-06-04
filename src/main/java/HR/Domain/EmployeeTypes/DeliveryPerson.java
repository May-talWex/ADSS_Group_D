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

    @Override
    public int hashCode() {
        return getType().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DeliveryPerson deliveryPerson = (DeliveryPerson) obj;
        return getType().equals(deliveryPerson.getType());
    }
}
