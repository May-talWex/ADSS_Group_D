package HR.WorkerTypes;

import HR.WorkerType;

public class ShiftManager implements WorkerType {

    @Override
    public String getType() {
        return "Shift Manager";
    }

    @Override
    public boolean isAbleToManage() {
        return true;
    }

}
