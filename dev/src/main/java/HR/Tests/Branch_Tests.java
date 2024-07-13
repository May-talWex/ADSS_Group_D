package HR.Tests;

import HR.Domain.*;
import HR.Domain.Exceptions.*;
import HR.Data.CreateStubEmployeesData;

import java.time.LocalDate;

public class Branch_Tests {
    public static void main(String[] args) {
        Branch branch;
        try {
            branch = CreateStubEmployeesData.AddEmployeesBulk();
        } catch (Exception e) {
            System.out.println("AddEmployeesBulk test failed.");
            return;
        }

        Employee employee1 = new Employee(123123, "John Doe", "john.doe@example.com", new BankAccount(123, 456, 789), branch, new Salary(1000.0f, LocalDate.now(), null));
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


        int shiftManagers = 55;
        int cashiers = 1;
        int deliveriers = 1;
        int storageWorkers = 1;
        boolean isMorningShift = true;
        LocalDate date = LocalDate.now();
        try {
            Shift s = branch.getSchedule().generateShift(
                    branch,
                    date,
                    isMorningShift,
                    shiftManagers,
                    cashiers,
                    deliveriers,
                    storageWorkers);
            assert s != null;
            System.out.println("Generate shift test passed.");
        } catch (Exception e) {
            if (e instanceof NotEnoughWorkers) {
                System.out.println("Generate shift test passed - not enough workers expected.");
            } else {
                System.out.println("Generate shift test failed.");
            }
        }

        shiftManagers = 1;
        cashiers = 99;
        try {
            Shift s = branch.getSchedule().generateShift(
                    branch,
                    date,
                    isMorningShift,
                    shiftManagers,
                    cashiers,
                    deliveriers,
                    storageWorkers);
            assert s != null;
            System.out.println("Generate shift test passed.");
        } catch (AssertionError | Exception e) {
            if (e instanceof NotEnoughWorkers) {
                System.out.println("Generate shift test passed - not enough workers expected.");
            } else {
                System.out.println("Generate shift test failed.");
            }
        }


        try {
            branch.removeEmployee(employee1);
        } catch (Exception e) {
            System.out.println("Remove worker with shift limitation test failed.");
        }

        try {
            branch.addWorker(employee1);
            ShiftLimitation shiftLimitation2 = new ShiftLimitation(employee1, LocalDate.now().plusDays(1), false);
            branch.addShiftLimitation(employee1, shiftLimitation2);
            branch.removeEmployee(employee1);
            assert !branch.getWorkers().contains(employee1);
            assert !branch.getShiftLimitations().contains(shiftLimitation2);
            System.out.println("Remove worker with shift limitation test passed.");
        } catch (AssertionError | Exception e) {
            System.out.println("Remove worker with shift limitation test failed.");
        }

        try {
            Shift testShift = branch.getSchedule().generateShift(branch, LocalDate.now(), true, 1, 0, 0, 0);
            Employee previouslyAssignedEmployee = testShift.getShiftManagers().getFirst();
            branch.getSchedule().replaceWorker(branch, previouslyAssignedEmployee, testShift);
            assert !previouslyAssignedEmployee.equals(testShift.getShiftManagers().getFirst());
            System.out.println("Replace employee test passed.");

        } catch (Exception e) {
            System.out.println("Generate shift test failed.");
        }
    }

}
