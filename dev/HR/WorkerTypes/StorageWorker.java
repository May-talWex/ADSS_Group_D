package HR.WorkerTypes;

import HR.WorkerType;

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
