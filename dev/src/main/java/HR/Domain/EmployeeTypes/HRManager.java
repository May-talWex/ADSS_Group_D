package HR.Domain.EmployeeTypes;

import HR.Domain.EmployeeType;

public class HRManager implements EmployeeType {

    public String getType() {
        return "HR Manager";
    }

    @Override
    public int hashCode() {
        return getType().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HRManager hRManager = (HRManager) obj;
        return getType().equals(hRManager.getType());
    }

    public boolean isAbleToSetShifts() {
        return true;
    }
}
