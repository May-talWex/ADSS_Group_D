package HR.Data;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteConnection {
    private static Connection connection = null;
    private static final String URL;

    static {
        String dbPath = "";
        try {
            // Determine the current directory where the application is running
            File currentDir = new File(System.getProperty("user.dir"));
            // If running from a JAR file, get the parent directory of the JAR file
            if (currentDir.getName().equals("release")) {
                dbPath = currentDir.getAbsolutePath() + File.separator + "HRDB.sqlite";
            } else {
                // If running from IntelliJ or other environment, assume the structure is different
                File releaseDir = new File(currentDir, "release");
                if (!releaseDir.exists() || !releaseDir.isDirectory()) {
                    throw new Exception("Release directory not found in " + releaseDir.getAbsolutePath());
                }
                dbPath = releaseDir.getAbsolutePath() + File.separator + "HRDB.sqlite";
            }

            File dbFile = new File(dbPath);
            if (!dbFile.exists()) {
                throw new Exception("Database file HRDB.sqlite not found in " + dbFile.getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("Error determining database path: " + e.getMessage());
        }
        URL = "jdbc:sqlite:" + dbPath;
    }

    public static Connection connect() {
        if (connection != null) {
            return connection;
        }
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println("SQLException in connect: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException in connect: " + e.getMessage());
        }
        return connection;
    }

    public static Connection getConnection() {
        if (connection == null) {
            return connect();
        }
        try {
            if (connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
            }
        } catch (SQLException e) {
            System.out.println("SQLException in getConnection: " + e.getMessage());
        }
        return connection;
    }

    public static void initializeDatabase() {
        String createBranchesTable = "CREATE TABLE IF NOT EXISTS Branches (\n"
                + "    BranchID INTEGER PRIMARY KEY,\n"
                + "    BranchName TEXT NOT NULL,\n"
                + "    BranchAddress TEXT NOT NULL\n"
                + ");";

        String createBankAccountsTable = "CREATE TABLE IF NOT EXISTS BankAccounts (\n"
                + "    BankAccountID INTEGER PRIMARY KEY,\n"
                + "    BankNumber INTEGER NOT NULL,\n"
                + "    BranchNumber INTEGER NOT NULL,\n"
                + "    AccountNumber INTEGER NOT NULL\n"
                + ");";

        String createSalariesTable = "CREATE TABLE IF NOT EXISTS Salaries (\n"
                + "    SalaryID INTEGER PRIMARY KEY,\n"
                + "    Amount REAL NOT NULL,\n"
                + "    StartDate DATE NOT NULL,\n"
                + "    EndDate DATE\n"
                + ");";

        String createEmployeesTable = "CREATE TABLE IF NOT EXISTS Employees (\n"
                + "    EmployeeID INTEGER PRIMARY KEY,\n"
                + "    Name TEXT NOT NULL,\n"
                + "    Email TEXT NOT NULL,\n"
                + "    BankAccountID INTEGER,\n"
                + "    BranchID INTEGER,\n"
                + "    SalaryID INTEGER,\n"
                + "    DateOfEmployment DATE,\n"
                + "    FOREIGN KEY (BankAccountID) REFERENCES BankAccounts(BankAccountID),\n"
                + "    FOREIGN KEY (BranchID) REFERENCES Branches(BranchID),\n"
                + "    FOREIGN KEY (SalaryID) REFERENCES Salaries(SalaryID)\n"
                + ");";

        String createShiftLimitationsTable = "CREATE TABLE IF NOT EXISTS ShiftLimitations (\n"
                + "    ShiftLimitationID INTEGER PRIMARY KEY,\n"
                + "    EmployeeID INTEGER NOT NULL,\n"
                + "    Date DATE NOT NULL,\n"
                + "    IsMorningShift BOOLEAN NOT NULL,\n"
                + "    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID)\n"
                + ");";

        String createShiftsTable = "CREATE TABLE IF NOT EXISTS Shifts (\n"
                + "    ShiftID INTEGER PRIMARY KEY,\n"
                + "    Date DATE NOT NULL,\n"
                + "    IsMorningShift BOOLEAN NOT NULL,\n"
                + "    BranchID INTEGER NOT NULL,\n"
                + "    ShiftManagers TEXT,\n"
                + "    Cashiers TEXT,\n"
                + "    StorageEmployees TEXT,\n"
                + "    Deliveriers TEXT,\n"
                + "    FOREIGN KEY (BranchID) REFERENCES Branches(BranchID)\n"
                + ");";

        String createEmployeeRolesTable = "CREATE TABLE IF NOT EXISTS EmployeeRoles (\n"
                + "    EmployeeID INTEGER NOT NULL,\n"
                + "    Role TEXT NOT NULL,\n"
                + "    PRIMARY KEY (EmployeeID, Role),\n"
                + "    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID)\n"
                + ");";

        String createDriverLicensesTable = "CREATE TABLE IF NOT EXISTS DriverLicenses (\n"
                + "    LicenseID INTEGER PRIMARY KEY,\n"
                + "    LicenseType TEXT NOT NULL,\n"
                + "    EmployeeID INTEGER NOT NULL,\n"
                + "    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID)\n"
                + ");";

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute(createBranchesTable);
            stmt.execute(createBankAccountsTable);
            stmt.execute(createSalariesTable);
            stmt.execute(createEmployeesTable);
            stmt.execute(createShiftLimitationsTable);
            stmt.execute(createShiftsTable);
            stmt.execute(createEmployeeRolesTable);
            stmt.execute(createDriverLicensesTable);
            stmt.close();
            System.out.println("Database has been initialized.");
        } catch (SQLException e) {
            System.out.println("SQLException in initializeDatabase: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        initializeDatabase();
    }
}
