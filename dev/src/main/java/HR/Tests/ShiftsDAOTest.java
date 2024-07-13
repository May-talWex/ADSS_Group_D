package HR.Tests;

import HR.Data.ShiftsDAO;
import HR.Domain.*;
import HR.Domain.EmployeeTypes.Cashier;
import HR.Domain.EmployeeTypes.DeliveryPerson;
import HR.Domain.EmployeeTypes.ShiftManager;
import HR.Domain.EmployeeTypes.StorageEmployee;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ShiftsDAOTest {

    private static ShiftsDAO shiftsDAO;
    private static Branch testBranch;
    private static Shift testShift;

    @BeforeAll
    public static void setup() throws Exception {
        shiftsDAO = new ShiftsDAO();
        testBranch = new Branch("test", "1234 Test St");
        testBranch.setBranchId(1); // Ensure branch ID is set
        testShift = new Shift(true, LocalDate.now());

        // Create and add some employees to the branch
        List<Employee> shiftManagers = new ArrayList<>();
        Employee shiftManager = new Employee(1, "John Doe", "john.doe@example.com", new BankAccount(123, 456, 789), testBranch, new Salary(1000.0f, LocalDate.now(), null));
        Employee cashier = new Employee(2, "Jane Smith", "jane.smith@example.com", new BankAccount(321, 654, 987), testBranch, new Salary(1200.0f, LocalDate.now(), null));
        Employee storageEmployee = new Employee(3, "Jim Brown", "jim.brown@example.com", new BankAccount(111, 222, 333), testBranch, new Salary(1100.0f, LocalDate.now(), null));
        Employee deliverer = new Employee(4, "Jake White", "jake.white@example.com", new BankAccount(444, 555, 666), testBranch, new Salary(1150.0f, LocalDate.now(), null));

        shiftManager.addPossiblePosition(new ShiftManager());
        cashier.addPossiblePosition(new Cashier());
        storageEmployee.addPossiblePosition(new StorageEmployee());
        deliverer.addPossiblePosition(new DeliveryPerson());

        shiftManagers.add(shiftManager); // Add the shift manager to the list
        testShift.setShiftManagers(shiftManagers);
        testBranch.getSchedule().addShift(testShift);
    }

    @BeforeEach
    public void initDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String createShiftsTable = "CREATE TABLE IF NOT EXISTS Shifts (" +
                    "Date TEXT," +
                    "IsMorningShift INTEGER," +
                    "BranchID INTEGER," +
                    "ShiftManagers TEXT," +
                    "Cashiers TEXT," +
                    "StorageEmployees TEXT," +
                    "Deliveriers TEXT," +
                    "PRIMARY KEY (Date, IsMorningShift, BranchID))";
            conn.createStatement().execute(createShiftsTable);
        }
    }

    @AfterEach
    public void cleanupDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            conn.createStatement().execute("DROP TABLE IF EXISTS Shifts");
        }
    }

    @Test
    public void testUpdateShift() throws Exception {
        shiftsDAO.addShift(testShift, testBranch.getBranchId());

        Employee shiftManager = new Employee(4, "John Doe", "john.doe@example.com", new BankAccount(123, 456, 789), testBranch, new Salary(1000.0f, LocalDate.now(), null));
        shiftManager.addPossiblePosition(new ShiftManager());
        testShift.addShiftManager(shiftManager);

        shiftsDAO.updateShift(testShift, testBranch);
        assertEquals(2, testShift.getShiftManagers().size());
    }

    @Test
    public void testDeleteShift() {
        shiftsDAO.addShift(testShift, testBranch.getBranchId());
        shiftsDAO.deleteShift(testShift.getDate(), testShift.isMorningShift(), testBranch.getBranchId());
        Shift deletedShift = shiftsDAO.getShift(testShift.getDate(), testShift.isMorningShift(), testBranch);
        assertNull(deletedShift);
    }
}
