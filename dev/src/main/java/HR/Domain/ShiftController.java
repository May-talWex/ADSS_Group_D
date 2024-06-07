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
import java.util.Scanner;

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


    public Shift createShift(Branch branch) {
        Scanner scanner = new Scanner(System.in);
            System.out.println("Creating a new shift for branch " + branch.getName());
            System.out.println("Enter the day of the shift (1-31):");
            int day = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter the month of the shift (1-12):");
            int month = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter the year of the shift:");
            int year = Integer.parseInt(scanner.nextLine());
            LocalDate date = LocalDate.of(year, month, day);
            System.out.println("Is this a morning shift? (true/false): ");
            boolean isMorningShift = Boolean.parseBoolean(scanner.nextLine());
            return new Shift(isMorningShift, date);
    }
}
