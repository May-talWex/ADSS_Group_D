package HR.Data;

import HR.Domain.*;
import HR.Domain.EmployeeTypes.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeesDAO {

    public void addEmployeeToDatabase(Employee employee) {
        String sql = "INSERT INTO Employees (EmployeeID, Name, Email, BankAccountID, BranchID, SalaryID, DateOfEmployment) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String bankAccountSql = "INSERT INTO BankAccounts (BankAccountID, BankNumber, BranchNumber, AccountNumber) VALUES (?, ?, ?, ?)";
        String salarySql = "INSERT INTO Salaries (SalaryID, Amount, StartDate, EndDate) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = SQLiteConnection.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmtEmployee = conn.prepareStatement(sql);
            PreparedStatement pstmtBankAccount = conn.prepareStatement(bankAccountSql);
            PreparedStatement pstmtSalary = conn.prepareStatement(salarySql);

            // Insert BankAccount
            pstmtBankAccount.setInt(1, employee.getEmployeeId());
            pstmtBankAccount.setInt(2, employee.getBankAccount().getBankNumber());
            pstmtBankAccount.setInt(3, employee.getBankAccount().getBranchNumber());
            pstmtBankAccount.setInt(4, employee.getBankAccount().getAccountNumber());
            pstmtBankAccount.executeUpdate();

            // Insert Salary
            pstmtSalary.setInt(1, employee.getEmployeeId());
            pstmtSalary.setFloat(2, employee.getCurrentSalary().getSalaryAmount());
            pstmtSalary.setDate(3, Date.valueOf(employee.getCurrentSalary().getStartDate()));
            pstmtSalary.setDate(4, employee.getCurrentSalary().getEndDate() != null ? Date.valueOf(employee.getCurrentSalary().getEndDate()) : null);
            pstmtSalary.executeUpdate();

            // Insert Employee
            pstmtEmployee.setInt(1, employee.getEmployeeId());
            pstmtEmployee.setString(2, employee.getName());
            pstmtEmployee.setString(3, employee.getEmail());
            pstmtEmployee.setInt(4, employee.getEmployeeId()); // BankAccountID
            pstmtEmployee.setInt(5, employee.getBranch().getBranchId());
            pstmtEmployee.setInt(6, employee.getEmployeeId()); // SalaryID
            pstmtEmployee.setDate(7, Date.valueOf(employee.getDateOfEmployment()));
            pstmtEmployee.executeUpdate();

            // Insert possible positions
            addRolesToDatabase(employee);

            conn.commit();
            pstmtEmployee.close();
            pstmtBankAccount.close();
            pstmtSalary.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRolesToDatabase(Employee employee) throws SQLException {
        String roleSql = "INSERT INTO EmployeeRoles (EmployeeID, Role) VALUES (?, ?)";

        Connection conn = SQLiteConnection.getConnection();
        try (PreparedStatement pstmtRole = conn.prepareStatement(roleSql)) {
            for (EmployeeType role : employee.getPossiblePositions()) {
                pstmtRole.setInt(1, employee.getEmployeeId());
                pstmtRole.setString(2, role.getType());
                pstmtRole.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("SQLException in addRolesToDatabase: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void removeEmployeeFromDatabase(int employeeId) {
        String sql = "DELETE FROM Employees WHERE EmployeeID = ?";
        String bankAccountSql = "DELETE FROM BankAccounts WHERE BankAccountID = ?";
        String salarySql = "DELETE FROM Salaries WHERE SalaryID = ?";
        String roleSql = "DELETE FROM EmployeeRoles WHERE EmployeeID = ?";

        try {
            Connection conn = SQLiteConnection.getConnection();
            conn.setAutoCommit(false);

            try {
                // Delete roles
                PreparedStatement pstmtRole = conn.prepareStatement(roleSql);
                pstmtRole.setInt(1, employeeId);
                pstmtRole.executeUpdate();
                pstmtRole.close();

                // Delete Employee
                PreparedStatement pstmtEmployee = conn.prepareStatement(sql);
                pstmtEmployee.setInt(1, employeeId);
                pstmtEmployee.executeUpdate();
                pstmtEmployee.close();

                // Delete BankAccount
                PreparedStatement pstmtBankAccount = conn.prepareStatement(bankAccountSql);
                pstmtBankAccount.setInt(1, employeeId);
                pstmtBankAccount.executeUpdate();
                pstmtBankAccount.close();

                // Delete Salary
                PreparedStatement pstmtSalary = conn.prepareStatement(salarySql);
                pstmtSalary.setInt(1, employeeId);
                pstmtSalary.executeUpdate();
                pstmtSalary.close();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("SQLException: " + e.getMessage());
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Employee> getAllEmployees(int branchId) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employees WHERE BranchID = ?";
        String roleSql = "SELECT Role FROM EmployeeRoles WHERE EmployeeID = ?";
        String licenseSql = "SELECT LicenseType FROM DriverLicenses WHERE EmployeeID = ?";


        try {
            Connection conn = SQLiteConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, branchId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int employeeId = rs.getInt("EmployeeID");
                String name = rs.getString("Name");
                String email = rs.getString("Email");
                int bankAccountId = rs.getInt("BankAccountID");
                int salaryId = rs.getInt("SalaryID");
                LocalDate dateOfEmployment = rs.getDate("DateOfEmployment").toLocalDate();

                BankAccount bankAccount = getBankAccountById(bankAccountId);
                Salary salary = getSalaryById(salaryId);

                Employee employee = new Employee(employeeId, name, email, bankAccount, getBranchById(branchId), salary);
                employee.setDateOfEmployment(dateOfEmployment);

                PreparedStatement pstmtRole = conn.prepareStatement(roleSql);
                pstmtRole.setInt(1, employeeId);
                ResultSet rsRole = pstmtRole.executeQuery();
                while (rsRole.next()) {
                    String role = rsRole.getString("Role");
                    switch (role) {
                        case "ShiftManager":
                            employee.addPossiblePosition(new ShiftManager());
                            break;
                        case "Cashier":
                            employee.addPossiblePosition(new Cashier());
                            break;
                        case "StorageEmployee":
                            employee.addPossiblePosition(new StorageEmployee());
                            break;
                        case "DeliveryPerson":
                            employee.addPossiblePosition(new DeliveryPerson());
                            break;
                        case "HR Manager", "HRManager":
                            employee.addPossiblePosition(new HRManager());
                            break;
                        default:
                            break;
                    }
                }
                pstmtRole.close();
                PreparedStatement pstmtLicense = conn.prepareStatement(licenseSql);
                pstmtLicense.setInt(1, employeeId);
                ResultSet rsLicense = pstmtLicense.executeQuery();
                while (rsLicense.next()) {
                    String licenseType = rsLicense.getString("LicenseType");
                    employee.addDriverLicense(licenseType);
                }
                employees.add(employee);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }

    private BankAccount getBankAccountById(int bankAccountId) throws SQLException {
        String sql = "SELECT * FROM BankAccounts WHERE BankAccountID = ?";
        BankAccount bankAccount = null;

        Connection conn = SQLiteConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, bankAccountId);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            int bankNumber = rs.getInt("BankNumber");
            int branchNumber = rs.getInt("BranchNumber");
            int accountNumber = rs.getInt("AccountNumber");
            bankAccount = new BankAccount(bankNumber, accountNumber, branchNumber);
        } else {
            throw new SQLException("BankAccount not found with ID: " + bankAccountId);
        }
        pstmt.close();
        return bankAccount;
    }

    private Salary getSalaryById(int salaryId) throws SQLException {
        String sql = "SELECT * FROM Salaries WHERE SalaryID = ?";
        Salary salary = null;

        Connection conn = SQLiteConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, salaryId);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            float amount = rs.getFloat("Amount");
            LocalDate startDate = rs.getDate("StartDate").toLocalDate();
            LocalDate endDate = rs.getDate("EndDate") != null ? rs.getDate("EndDate").toLocalDate() : null;
            salary = new Salary(amount, startDate, endDate);
        } else {
            throw new SQLException("Salary not found with ID: " + salaryId);
        }
        pstmt.close();
        return salary;
    }

    private Branch getBranchById(int branchId) {
        BranchDAO branchDAO = new BranchDAO();
        return branchDAO.getBranchFromDatabase(branchId);
    }

    public void addRoleToEmployee(int employeeId, String role) {
        String sql = "INSERT INTO EmployeeRoles (EmployeeID, Role) VALUES (?, ?)";

        try {
            Connection conn = SQLiteConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, employeeId);
            pstmt.setString(2, role);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDriverLicenseToEmployee(int id, String license) {
        String licenseSql = "INSERT INTO DriverLicenses (EmployeeID, LicenseType) VALUES (?, ?)";
        try {
            Connection conn = SQLiteConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(licenseSql);
            pstmt.setInt(1, id);
            pstmt.setString(2, license);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void removeDriverLicenseFromEmployee(int id, String license) {
        String licenseSql = "DELETE FROM DriverLicenses WHERE EmployeeID = ? AND LicenseType = ?";
        try {
            Connection conn = SQLiteConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(licenseSql);
            pstmt.setInt(1, id);
            pstmt.setString(2, license);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
