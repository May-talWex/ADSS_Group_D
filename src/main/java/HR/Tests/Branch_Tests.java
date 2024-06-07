package HR.Tests;
import HR.Domain.*;
import HR.Domain.Exceptions.*;

import java.time.LocalDate;


public class Branch_Tests {
        public static void main(String[] args) {
            Branch branch = new Branch("Main Branch", "123 Main St");

            Employee employee1 = new Employee(1, "John Doe", "john.doe@example.com", new BankAccount(123, 456, 789), branch, new Salary(1000.0f, LocalDate.now(), null));
            Employee employee2 = new Employee(2, "Jane Smith", "jane.smith@example.com", new BankAccount(321, 654, 987), branch, new Salary(1200.0f, LocalDate.now(), null));
            ShiftLimitation shiftLimitation = new ShiftLimitation(employee1, LocalDate.now(), true);

            try {
                assert branch.getName().equals("Main Branch");
                System.out.println("Branch name test passed.");
            } catch (AssertionError e) {
                System.out.println("Branch name test failed.");
            }

            try {
                assert branch.getAddress().equals("123 Main St");
                System.out.println("Branch address test passed.");
            } catch (AssertionError e) {
                System.out.println("Branch address test failed.");
            }

            try {
                branch.addWorker(employee1);
                assert branch.getWorkers().contains(employee1);
                System.out.println("Add worker test passed.");
            } catch (AssertionError | Exception e) {
                System.out.println("Add worker test failed.");
            }
            try {
                branch.addWorker(employee1);
                System.out.println("Duplicate add worker test failed.");
            } catch (EmployeeAlreadyExistsInBranch e) {
                System.out.println("Duplicate add worker test passed.");
            } catch (Exception e) {
                System.out.println("Unexpected exception in duplicate add worker test.");
            }

            try {
                branch.removeEmployee(employee1);
                assert !branch.getWorkers().contains(employee1);
                System.out.println("Remove worker test passed.");
            } catch (AssertionError | Exception e) {
                System.out.println("Remove worker test failed.");
            }

            try {
                branch.removeEmployee(employee1);
                System.out.println("Remove non-existent worker test failed.");
            } catch (EmployeeDoesNotExistInBranch e) {
                System.out.println("Remove non-existent worker test passed.");
            } catch (Exception e) {
                System.out.println("Unexpected exception in remove non-existent worker test.");
            }

            try {
                branch.addWorker(employee1);
                branch.addShiftLimitation(employee1, shiftLimitation);
                assert branch.getShiftLimitations().contains(shiftLimitation);
                System.out.println("Add shift limitation test passed.");
            } catch (AssertionError | Exception e) {
                System.out.println("Add shift limitation test failed.");
            }

            try {
                branch.addShiftLimitation(employee1, shiftLimitation);
                System.out.println("Duplicate add shift limitation test failed.");
            } catch (ShiftAlreadyExists e) {
                System.out.println("Duplicate add shift limitation test passed.");
            } catch (Exception e) {
                System.out.println("Unexpected exception in duplicate add shift limitation test.");
            }

            try {
                branch.removeShiftLimitation(employee1, shiftLimitation);
                assert !branch.getShiftLimitations().contains(shiftLimitation);
                System.out.println("Remove shift limitation test passed.");
            } catch (AssertionError | Exception e) {
                System.out.println("Remove shift limitation test failed.");
            }

            try {
                branch.removeShiftLimitation(employee1, shiftLimitation);
                System.out.println("Remove non-existent shift limitation test failed.");
            } catch (ShiftLimitationDoesntExist e) {
                System.out.println("Remove non-existent shift limitation test passed.");
            } catch (Exception e) {
                System.out.println("Unexpected exception in remove non-existent shift limitation test.");
            }

            try {
                boolean hasShiftLimitation = branch.hasShiftLimitation(employee1, LocalDate.now(), true);
                assert hasShiftLimitation;
                System.out.println("Has shift limitation test passed.");
            } catch (AssertionError e) {
                System.out.println("Has shift limitation test failed.");
            }

            try {
                Employee foundEmployee = branch.getWorkerById(1);
                assert foundEmployee != null && foundEmployee.getEmployeeId() == 1;
                System.out.println("Get worker by ID test passed.");
            } catch (AssertionError e) {
                System.out.println("Get worker by ID test failed.");
            }

            try {
                Employee foundEmployee = branch.getWorkerById(999);
                assert foundEmployee == null;
                System.out.println("Get non-existent worker by ID test passed.");
            } catch (AssertionError e) {
                System.out.println("Get non-existent worker by ID test failed.");
            }
        }

        }
