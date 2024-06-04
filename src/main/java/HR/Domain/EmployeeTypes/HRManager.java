package HR.Domain.EmployeeTypes;

import HR.Domain.EmployeeType;

public class HRManager implements EmployeeType {

    public String getType() {
        return "HR Manager";
    }


    public boolean isAbleToSetShifts() {
        return true;
    }
}
