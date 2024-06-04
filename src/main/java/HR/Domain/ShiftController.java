package HR.Domain;

import HR.Domain.EmployeeTypes.Cashier;
import HR.Domain.EmployeeTypes.DeliveryPerson;
import HR.Domain.EmployeeTypes.ShiftManager;
import HR.Domain.EmployeeTypes.StorageEmployee;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShiftController {
        @JsonCreator
        public Shift JSONtoClass(
                @JsonProperty("isMorningShift") boolean isMorningShift,
                @JsonProperty("date") LocalDate date,
                @JsonProperty("shiftManagers") List<Employee> shiftManagers,
                @JsonProperty("cashiers") List<Employee> cashiers,
                @JsonProperty("storageWorkers") List<Employee> storageWorkers,
                @JsonProperty("deliveries") List<Employee> deliveries) {

            if (shiftManagers == null) {
                shiftManagers = new ArrayList<>();
            }
            if (cashiers == null) {
                cashiers = new ArrayList<>();
            }
            if (storageWorkers == null) {
                storageWorkers = new ArrayList<>();
            }
            if (deliveries == null) {
                deliveries = new ArrayList<>();
            }
            return new Shift(isMorningShift, date, shiftManagers, cashiers, storageWorkers, deliveries);
        }

}
