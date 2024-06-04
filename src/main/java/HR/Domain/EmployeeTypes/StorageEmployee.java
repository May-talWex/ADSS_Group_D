package HR.Domain.EmployeeTypes;

import HR.Domain.EmployeeType;

public class StorageEmployee implements EmployeeType {
    @Override
    public String getType() {
        return "StorageWorker";
    }


    @Override
    public boolean isAbleToBeStorageWorker() {
        return true;
    }
}
