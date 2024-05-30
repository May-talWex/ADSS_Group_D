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
}
