package HR.Domain.WorkerTypes;

import HR.Domain.WorkerType;

public class HRManager implements WorkerType {

    public String getType() {
        return "HR Manager";
    }


    public boolean isAbleToSetShifts() {
        return true;
    }
}
