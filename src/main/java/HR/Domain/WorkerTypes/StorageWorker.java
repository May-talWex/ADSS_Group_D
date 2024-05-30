package HR.Domain.WorkerTypes;

import HR.Domain.WorkerType;

public class StorageWorker implements WorkerType {
    @Override
    public String getType() {
        return "StorageWorker";
    }


    @Override
    public boolean isAbleToBeStorageWorker() {
        return true;
    }
}
