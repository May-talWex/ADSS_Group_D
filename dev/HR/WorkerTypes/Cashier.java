package HR.WorkerTypes;

import HR.WorkerType;

public class Cashier implements WorkerType {

    @Override
    public String getType() {
        return "Cashier";
    }

    @Override
    public boolean isAbleToBeCashier() {
        return true;
    }
}
