package HR.Domain;
import HR.Domain.EmployeeTypes.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HRManager.class, name = "HRManager"),
        @JsonSubTypes.Type(value = Cashier.class, name = "Cashier"),
        @JsonSubTypes.Type(value = DeliveryPerson.class, name = "DeliveryPerson"),
        @JsonSubTypes.Type(value = ShiftManager.class, name = "ShiftManager"),
        @JsonSubTypes.Type(value = StorageEmployee.class, name = "StorageEmployee")
})
public interface EmployeeType {
    public String getType();

    public default boolean isAbleToManage() {
        return false;
    }


    public default boolean isAbleToDeliver() {
        return false;
    }


    public default boolean isAbleToBeCashier() {
        return false;
    }

    public default boolean isAbleToBeStorageWorker() {
        return false;
    }

    public default boolean isAbleToSetShifts() {
        return false;
    }


    public default boolean equals(EmployeeType other) {
        return this.getType().equals(other.getType());
    }
}
