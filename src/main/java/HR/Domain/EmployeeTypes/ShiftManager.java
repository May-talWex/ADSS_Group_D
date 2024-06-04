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

//    @Override
//    public int hashCode() {
//        return getType().hashCode();
//    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ShiftManager sm = (ShiftManager) obj;
        return getType().equals(sm.getType());
    }

}
