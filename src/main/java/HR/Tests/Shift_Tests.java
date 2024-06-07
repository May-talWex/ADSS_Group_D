package HR.Tests;
import HR.Domain.*;
import HR.Domain.EmployeeTypes.*;
import HR.Domain.Exceptions.*;

import java.time.LocalDate;

public class Shift_Tests {
    public static void main(String[] args) throws Exception {
        LocalDate shiftDate = LocalDate.now();
        Shift shift = new Shift(true, shiftDate);

        Branch branch = new Branch("Main Branch", "123 Main St");
        Employee shiftManager = new Employee(1, "John Doe", "john.doe@example.com", new BankAccount(123, 456, 789), branch, new Salary(1000.0f, LocalDate.now(), null));
        Employee cashier = new Employee(2, "Jane Smith", "jane.smith@example.com", new BankAccount(321, 654, 987), branch, new Salary(1200.0f, LocalDate.now(), null));
        Employee storageEmployee = new Employee(3, "Jim Brown", "jim.brown@example.com", new BankAccount(111, 222, 333), branch, new Salary(1100.0f, LocalDate.now(), null));
        Employee deliverier = new Employee(4, "Jake White", "jake.white@example.com", new BankAccount(444, 555, 666), branch, new Salary(1150.0f, LocalDate.now(), null));

        shiftManager.addPossiblePosition(new ShiftManager());
        cashier.addPossiblePosition(new Cashier());
        storageEmployee.addPossiblePosition(new StorageEmployee());
        deliverier.addPossiblePosition(new DeliveryPerson());

        try {
            assert shift.isMorningShift();
            System.out.println("Is morning shift test passed.");
        } catch (AssertionError e) {
            System.out.println("Is morning shift test failed.");
        }

        try {
            assert shift.getDate().equals(shiftDate);
            System.out.println("Shift date test passed.");
        } catch (AssertionError e) {
            System.out.println("Shift date test failed.");
        }

        try {
            shift.addShiftManager(shiftManager);
            assert shift.getShiftManagers().contains(shiftManager);
            System.out.println("Add shift manager test passed.");
        } catch (AssertionError | Exception e) {
            System.out.println("Add shift manager test failed.");
        }
        try {
            shift.removeShiftManager(shiftManager);
            assert !shift.getShiftManagers().contains(shiftManager);
            System.out.println("Remove shift manager test passed.");
        } catch (AssertionError | Exception e) {
            System.out.println("Remove shift manager test failed.");
        }

        try {
            shift.removeShiftManager(shiftManager);
            System.out.println("Remove non-existent shift manager test failed.");
        } catch (ShiftDoesntExist e) {
            System.out.println("Remove non-existent shift manager test passed.");
        } catch (Exception e) {
            System.out.println("Unexpected exception in remove non-existent shift manager test.");
        }

        try {
            shift.addCashier(cashier);
            assert shift.getCashiers().contains(cashier);
            System.out.println("Add cashier test passed.");
        } catch (AssertionError | Exception e) {
            System.out.println("Add cashier test failed.");
        }

        try {
            shift.addCashier(cashier);
            System.out.println("Duplicate add cashier test failed.");
        } catch (ShiftAlreadyExists e) {
            System.out.println("Duplicate add cashier test passed.");
        } catch (Exception e) {
            System.out.println("Unexpected exception in duplicate add cashier test.");
        }
        try {
            shift.removeCashier(cashier);
            assert !shift.getCashiers().contains(cashier);
            System.out.println("Remove cashier test passed.");
        } catch (AssertionError | Exception e) {
            System.out.println("Remove cashier test failed.");
        }

        try {
            shift.removeCashier(cashier);
            System.out.println("Remove non-existent cashier test failed.");
        } catch (ShiftDoesntExist e) {
            System.out.println("Remove non-existent cashier test passed.");
        } catch (Exception e) {
            System.out.println("Unexpected exception in remove non-existent cashier test.");
        }

        try {
            shift.addStorageEmployee(storageEmployee);
            assert shift.getStorageEmployees().contains(storageEmployee);
            System.out.println("Add storage employee test passed.");
        } catch (AssertionError | Exception e) {
            System.out.println("Add storage employee test failed.");
        }

        try {
            shift.addStorageEmployee(storageEmployee);
            System.out.println("Duplicate add storage employee test failed.");
        } catch (ShiftAlreadyExists e) {
            System.out.println("Duplicate add storage employee test passed.");
        } catch (Exception e) {
            System.out.println("Unexpected exception in duplicate add storage employee test.");
        }

        try {
            shift.removeStorageEmployee(storageEmployee);
            assert !shift.getStorageEmployees().contains(storageEmployee);
            System.out.println("Remove storage employee test passed.");
        } catch (AssertionError | Exception e) {
            System.out.println("Remove storage employee test failed.");
        }

        try {
            shift.removeStorageEmployee(storageEmployee);
            System.out.println("Remove non-existent storage employee test failed.");
        } catch (ShiftDoesntExist e) {
            System.out.println("Remove non-existent storage employee test passed.");
        } catch (Exception e) {
            System.out.println("Unexpected exception in remove non-existent storage employee test.");
        }

        try {
            shift.addDeliverier(deliverier);
            assert shift.getDeliveriers().contains(deliverier);
            System.out.println("Add deliverier test passed.");
        } catch (AssertionError | Exception e) {
            System.out.println("Add deliverier test failed.");
        }

        try {
            shift.addDeliverier(deliverier);
            System.out.println("Duplicate add deliverier test failed.");
        } catch (ShiftAlreadyExists e) {
            System.out.println("Duplicate add deliverier test passed.");
        } catch (Exception e) {
            System.out.println("Unexpected exception in duplicate add deliverier test.");
        }

        try {
            shift.removeDeliverier(deliverier);
            assert !shift.getDeliveriers().contains(deliverier);
            System.out.println("Remove deliverier test passed.");
        } catch (AssertionError | Exception e) {
            System.out.println("Remove deliverier test failed.");
        }

        try {
            shift.removeDeliverier(deliverier);
            System.out.println("Remove non-existent deliverier test failed.");
        } catch (ShiftDoesntExist e) {
            System.out.println("Remove non-existent deliverier test passed.");
        } catch (Exception e) {
            System.out.println("Unexpected exception in remove non-existent deliverier test.");
        }

        try {
            shift.clear();
            assert shift.getShiftManagers().isEmpty() &&
                    shift.getCashiers().isEmpty() &&
                    shift.getStorageEmployees().isEmpty() &&
                    shift.getDeliveriers().isEmpty();
            System.out.println("Clear shift test passed.");
        } catch (AssertionError e) {
            System.out.println("Clear shift test failed.");
        }
    }
    }
