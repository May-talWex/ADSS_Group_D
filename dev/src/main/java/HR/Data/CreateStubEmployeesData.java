package HR.Data;

import HR.Domain.BankAccount;
import HR.Domain.Branch;
import HR.Domain.Employee;
import HR.Domain.EmployeeTypes.*;
import HR.Domain.Salary;

public class CreateStubEmployeesData {


    public static Branch AddEmployeesBulk() throws Exception {
        Branch branch = new Branch("Branch Name", "Branch Address");

        branch.addWorker(new Employee(0, "Admin", "admin@supermarket.com", new BankAccount(0, 0, 0), branch, new Salary(0)));
        branch.getWorkerById(0).addPossiblePosition(new HRManager());

        branch.addWorker(new Employee(1, "John Doe", "john.doe@example.com", new BankAccount(12345, 67890, 54321), branch, new Salary(50000)));
        branch.addWorker(new Employee(2, "Jane Smith", "jane.smith@example.com", new BankAccount(54321, 98765, 12345), branch, new Salary(60000)));
        branch.addWorker(new Employee(3, "Alice Johnson", "alice.johnson@example.com", new BankAccount(98765, 13579, 24680), branch, new Salary(55000)));
        branch.addWorker(new Employee(4, "Bob Brown", "bob.brown@example.com", new BankAccount(24680, 24680, 13579), branch, new Salary(70000)));
        branch.addWorker(new Employee(5, "Emily Davis", "emily.davis@example.com", new BankAccount(13579, 54321, 98765), branch, new Salary(65000)));
        branch.addWorker(new Employee(6, "Michael Wilson", "michael.wilson@example.com", new BankAccount(99999, 88888, 77777), branch, new Salary(75000)));
        branch.addWorker(new Employee(7, "Jessica Martinez", "jessica.martinez@example.com", new BankAccount(77777, 66666, 55555), branch, new Salary(58000)));
        branch.addWorker(new Employee(8, "David Anderson", "david.anderson@example.com", new BankAccount(55555, 44444, 33333), branch, new Salary(62000)));
        branch.addWorker(new Employee(9, "Samantha Taylor", "samantha.taylor@example.com", new BankAccount(33333, 22222, 11111), branch, new Salary(69000)));
        branch.addWorker(new Employee(10, "Christopher Thomas", "christopher.thomas@example.com", new BankAccount(11111, 22222, 33333), branch, new Salary(58000)));
        branch.addWorker(new Employee(11, "Olivia Garcia", "olivia.garcia@example.com", new BankAccount(22222, 33333, 44444), branch, new Salary(72000)));
        branch.addWorker(new Employee(12, "Daniel Rodriguez", "daniel.rodriguez@example.com", new BankAccount(33333, 44444, 55555), branch, new Salary(64000)));
        branch.addWorker(new Employee(13, "Isabella Martinez", "isabella.martinez@example.com", new BankAccount(44444, 55555, 66666), branch, new Salary(68000)));
        branch.addWorker(new Employee(14, "Alexander Hernandez", "alexander.hernandez@example.com", new BankAccount(55555, 66666, 77777), branch, new Salary(70000)));
        branch.addWorker(new Employee(15, "Sophia Lopez", "sophia.lopez@example.com", new BankAccount(66666, 77777, 88888), branch, new Salary(59000)));
        branch.addWorker(new Employee(16, "William Gonzalez", "william.gonzalez@example.com", new BankAccount(77777, 88888, 99999), branch, new Salary(73000)));
        branch.addWorker(new Employee(17, "Mia Wilson", "mia.wilson@example.com", new BankAccount(88888, 99999, 11111), branch, new Salary(66000)));
        branch.addWorker(new Employee(18, "James Rodriguez", "james.rodriguez@example.com", new BankAccount(99999, 11111, 22222), branch, new Salary(70000)));
        branch.addWorker(new Employee(19, "Charlotte Hernandez", "charlotte.hernandez@example.com", new BankAccount(11111, 22222, 33333), branch, new Salary(71000)));
        branch.addWorker(new Employee(20, "Benjamin Smith", "benjamin.smith@example.com", new BankAccount(22222, 33333, 44444), branch, new Salary(60000)));
        branch.addWorker(new Employee(21, "Amelia Martinez", "amelia.martinez@example.com", new BankAccount(33333, 44444, 55555), branch, new Salary(74000)));
        branch.addWorker(new Employee(22, "Elijah Lopez", "elijah.lopez@example.com", new BankAccount(44444, 55555, 66666), branch, new Salary(67000)));
        branch.addWorker(new Employee(23, "Avery Gonzalez", "avery.gonzalez@example.com", new BankAccount(55555, 66666, 77777), branch, new Salary(71000)));
        branch.addWorker(new Employee(24, "Evelyn Wilson", "evelyn.wilson@example.com", new BankAccount(66666, 77777, 88888), branch, new Salary(72000)));
        branch.addWorker(new Employee(25, "Mason Rodriguez", "mason.rodriguez@example.com", new BankAccount(77777, 88888, 99999), branch, new Salary(61000)));
        branch.addWorker(new Employee(26, "Liam Hernandez", "liam.hernandez@example.com", new BankAccount(88888, 99999, 11111), branch, new Salary(75000)));
        branch.addWorker(new Employee(27, "Emma Martinez", "emma.martinez@example.com", new BankAccount(99999, 11111, 22222), branch, new Salary(68000)));
        branch.addWorker(new Employee(28, "Aiden Lopez", "aiden.lopez@example.com", new BankAccount(11111, 22222, 33333), branch, new Salary(72000)));
        branch.addWorker(new Employee(29, "Oliver Gonzalez", "oliver.gonzalez@example.com", new BankAccount(22222, 33333, 44444), branch, new Salary(73000)));
        branch.addWorker(new Employee(30, "Aria Wilson", "aria.wilson@example.com", new BankAccount(33333, 44444, 55555), branch, new Salary(62000)));
        branch.getWorkerById(1).addPossiblePosition(new Cashier());
        branch.getWorkerById(1).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(2).addPossiblePosition(new Cashier());
        branch.getWorkerById(3).addPossiblePosition(new Cashier());
        branch.getWorkerById(3).addPossiblePosition(new DeliveryPerson());
        branch.getWorkerById(4).addPossiblePosition(new Cashier());
        branch.getWorkerById(5).addPossiblePosition(new Cashier());
        branch.getWorkerById(5).addPossiblePosition(new DeliveryPerson());
        branch.getWorkerById(6).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(6).addPossiblePosition(new StorageEmployee());
        branch.getWorkerById(7).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(8).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(9).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(10).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(11).addPossiblePosition(new Cashier());
        branch.getWorkerById(11).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(12).addPossiblePosition(new Cashier());
        branch.getWorkerById(13).addPossiblePosition(new Cashier());
        branch.getWorkerById(13).addPossiblePosition(new DeliveryPerson());
        branch.getWorkerById(14).addPossiblePosition(new Cashier());
        branch.getWorkerById(15).addPossiblePosition(new Cashier());
        branch.getWorkerById(15).addPossiblePosition(new DeliveryPerson());
        branch.getWorkerById(16).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(17).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(18).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(19).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(20).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(21).addPossiblePosition(new Cashier());
        branch.getWorkerById(21).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(22).addPossiblePosition(new StorageEmployee());
        branch.getWorkerById(23).addPossiblePosition(new Cashier());
        branch.getWorkerById(23).addPossiblePosition(new DeliveryPerson());
        branch.getWorkerById(24).addPossiblePosition(new StorageEmployee());
        branch.getWorkerById(25).addPossiblePosition(new Cashier());
        branch.getWorkerById(25).addPossiblePosition(new DeliveryPerson());
        branch.getWorkerById(26).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(27).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(28).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(29).addPossiblePosition(new ShiftManager());
        branch.getWorkerById(30).addPossiblePosition(new ShiftManager());
        System.out.println("Employees data added successfully.");
        System.out.println("________________");
        System.out.println("");

        return branch;
    }
}
