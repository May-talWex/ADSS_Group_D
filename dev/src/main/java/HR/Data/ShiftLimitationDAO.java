package HR.Data;

import HR.Domain.Branch;
import HR.Domain.ShiftLimitation;
import HR.Domain.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShiftLimitationDAO {

    private final Connection connection;

    public ShiftLimitationDAO() {
        this.connection = SQLiteConnection.getConnection();
    }

    public void addShiftLimitation(ShiftLimitation limitation) throws SQLException {
        String sql = "INSERT INTO ShiftLimitations (EmployeeID, Date, IsMorningShift) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, limitation.getEmployee().getEmployeeId());
            pstmt.setString(2, limitation.getDate().toString());
            pstmt.setBoolean(3, limitation.isMorningShift());
            pstmt.executeUpdate();
        }
    }

    public void removeShiftLimitation(ShiftLimitation limitation) throws SQLException {
        String sql = "DELETE FROM ShiftLimitations WHERE EmployeeID = ? AND Date = ? AND IsMorningShift = ?";
        try  {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, limitation.getEmployee().getEmployeeId());
            pstmt.setString(2, limitation.getDate().toString());
            pstmt.setBoolean(3, limitation.isMorningShift());
            pstmt.executeUpdate();
            System.out.println("Added limitation to db");
        }
        catch (SQLException e) {
            System.out.println("Error removing shift limitation: " + e.getMessage());
        }
    }

    public List<ShiftLimitation> getShiftLimitationsByEmployee(Employee employee) throws SQLException {
        String sql = "SELECT * FROM ShiftLimitations WHERE EmployeeID = ?";
        List<ShiftLimitation> limitations = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employee.getEmployeeId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                LocalDate date = LocalDate.parse(rs.getString("Date"));
                boolean isMorningShift = rs.getBoolean("IsMorningShift");
                ShiftLimitation limitation = new ShiftLimitation(employee, date, isMorningShift);
                limitations.add(limitation);
            }
        }
        return limitations;
    }

    public List<ShiftLimitation> getAllShiftLimitations(Branch branch) {
        String sql = "SELECT * FROM ShiftLimitations WHERE EmployeeID IN (SELECT EmployeeID FROM Employees WHERE BranchID = ?)";
        List<ShiftLimitation> limitations = new ArrayList<>();

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, branch.getBranchId());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int employeeId = rs.getInt("EmployeeID");
                    LocalDate date = null;

                    // Try different methods to parse the date
                    try {
                        date = rs.getDate("Date").toLocalDate();
                    } catch (Exception e) {
                        try {
                            date = LocalDate.parse(rs.getString("Date"));
                        } catch (Exception e2) {
                            System.out.println("Error parsing date for employee " + employeeId + ": " + e2.getMessage());
                            continue; // Skip this record and move to the next one
                        }
                    }

                    boolean isMorningShift = rs.getBoolean("IsMorningShift");
                    Employee employee = branch.getWorkerById(employeeId);

                    if (employee != null && date != null) {
                        ShiftLimitation limitation = new ShiftLimitation(employee, date, isMorningShift);
                        limitations.add(limitation);
                    } else {
                        System.out.println("Skipping limitation for employee " + employeeId + " due to missing data.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting shift limitations: " + e.getMessage());
            e.printStackTrace();
        }

        return limitations;
    }
}