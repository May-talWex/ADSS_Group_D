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

    @Override
    public int hashCode() {
        return getType().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StorageEmployee sm = (StorageEmployee) obj;
        return getType().equals(sm.getType());
    }
}
