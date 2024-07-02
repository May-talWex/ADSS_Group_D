package HR.Data;

import HR.Domain.ShiftLimitation;
import HR.Domain.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}