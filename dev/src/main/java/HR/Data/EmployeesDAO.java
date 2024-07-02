package HR.Data;

import HR.Domain.*;
import HR.Domain.EmployeeTypes.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeesDAO {
    private Branch branch;

    public EmployeesDAO(Branch branch) {
        this.branch = branch;
    }

    public void addEmployee(Employee employee) throws SQLException {
        String sql = "INSERT INTO Employees (EmployeeID, Name, Email, BankAccountID, BranchID, SalaryID, DateOfEmployment) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = SQLiteConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, employee.getEmployeeId());
            pstmt.setString(2, employee.getName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setInt(4, employee.getBankAccount().getBankNumber()); // Assuming BankAccountID is set to BankNumber for simplicity
            pstmt.setInt(5, branch.getBranchId());
            pstmt.setFloat(6, employee.getCurrentSalary().getSalaryAmount()); // Assuming SalaryID is set to Salary amount for simplicity
            pstmt.setDate(7, Date.valueOf(employee.getDateOfEmployment()));
            pstmt.executeUpdate();

            addEmployeeRoles(employee);
        } catch (SQLException e) {
            throw new SQLException("Error adding employee: " + e.getMessage());
        }
    }

    private void addEmployeeRoles(Employee employee) throws SQLException {
        String sql = "INSERT INTO EmployeeRoles (EmployeeID, Role) VALUES (?, ?)";
        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (EmployeeType role : employee.getPossiblePositions()) {
                pstmt.setInt(1, employee.getEmployeeId());
                pstmt.setString(2, role.getType());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employees WHERE BranchID = ?";
        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, branch.getBranchId());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("EmployeeID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        getBankAccountById(rs.getInt("BankAccountID")),
                        branch,
                        getSalaryById(rs.getInt("SalaryID"))
                );
                employee.setDateOfEmployment(rs.getDate("DateOfEmployment").toLocalDate());
                List<EmployeeType> roles = getEmployeeRoles(rs.getInt("EmployeeID"));
                for (EmployeeType role : roles) {
                    employee.addPossiblePosition(role);
                }
                employees.add(employee);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return employees;
    }

    private BankAccount getBankAccountById(int bankAccountId) throws SQLException {
        String sql = "SELECT * FROM BankAccounts WHERE BankAccountID = ?";
        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bankAccountId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new BankAccount(
                        rs.getInt("BankNumber"),
                        rs.getInt("AccountNumber"),
                        rs.getInt("BranchNumber")
                );
            } else {
                throw new SQLException("BankAccount not found with ID: " + bankAccountId);
            }
        }
    }

    private Salary getSalaryById(int salaryId) throws SQLException {
        String sql = "SELECT * FROM Salaries WHERE SalaryID = ?";
        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, salaryId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                LocalDate endDate = rs.getDate("EndDate") != null ? rs.getDate("EndDate").toLocalDate() : null;
                return new Salary(
                        rs.getFloat("Amount"),
                        rs.getDate("StartDate").toLocalDate(),
                        endDate
                );
            } else {
                throw new SQLException("Salary not found with ID: " + salaryId);
            }
        }
    }

    private List<EmployeeType> getEmployeeRoles(int employeeId) throws SQLException {
        List<EmployeeType> roles = new ArrayList<>();
        String sql = "SELECT Role FROM EmployeeRoles WHERE EmployeeID = ?";
        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String role = rs.getString("Role");
                switch (role) {
                    case "Cashier":
                        roles.add(new Cashier());
                        break;
                    case "DeliveryPerson":
                        roles.add(new DeliveryPerson());
                        break;
                    case "HRManager":
                        roles.add(new HRManager());
                        break;
                    case "StorageEmployee":
                        roles.add(new StorageEmployee());
                        break;
                    case "ShiftManager":
                        roles.add(new ShiftManager());
                        break;
                }
            }
        }
        return roles;
    }
}
