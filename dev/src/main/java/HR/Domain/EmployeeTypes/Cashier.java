package HR.Domain.EmployeeTypes;

import HR.Domain.EmployeeType;

public class Cashier implements EmployeeType {

    @Override
    public String getType() {
        return "Cashier";
    }

    @Override
    public boolean isAbleToBeCashier() {
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
        Cashier cashier = (Cashier) obj;
        return getType().equals(cashier.getType());
    }
}
