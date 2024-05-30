package HR.Domain.EmployeeTypes;

import HR.Domain.EmployeeType;

public class ShiftManager implements EmployeeType {

    @Override
    public String getType() {
        return "Shift Manager";
    }

    @Override
    public boolean isAbleToManage() {
        return true;
    }

}
