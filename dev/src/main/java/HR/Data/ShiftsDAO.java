package HR.Data;

import HR.Domain.Employee;
import HR.Domain.Shift;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import HR.Data.SQLiteConnection;

public class ShiftsDAO {

    public void addShift(Shift shift, int branchId) {
        String sql = "INSERT INTO Shifts(Date, IsMorningShift, BranchID, ShiftManagers, Cashiers, StorageEmployees, Deliveriers) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ObjectMapper objectMapper = new ObjectMapper();

            pstmt.setString(1, shift.getDate().toString());
            pstmt.setBoolean(2, shift.isMorningShift());
            pstmt.setInt(3, branchId); // Replace with actual BranchID
            pstmt.setString(4, objectMapper.writeValueAsString(shift.getShiftManagers()));
            pstmt.setString(5, objectMapper.writeValueAsString(shift.getCashiers()));
            pstmt.setString(6, objectMapper.writeValueAsString(shift.getStorageEmployees()));
            pstmt.setString(7, objectMapper.writeValueAsString(shift.getDeliveriers()));

            pstmt.executeUpdate();

        } catch (SQLException | JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Shift> getAllShifts() {
        String sql = "SELECT * FROM Shifts";
        List<Shift> shifts = new ArrayList<>();

        try (Connection conn = SQLiteConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ObjectMapper objectMapper = new ObjectMapper();

            while (rs.next()) {
                boolean isMorningShift = rs.getBoolean("IsMorningShift");
                LocalDate date = LocalDate.parse(rs.getString("Date"));

                List<Employee> shiftManagers = objectMapper.readValue(rs.getString("ShiftManagers"), new TypeReference<List<Employee>>(){});
                List<Employee> cashiers = objectMapper.readValue(rs.getString("Cashiers"), new TypeReference<List<Employee>>(){});
                List<Employee> storageEmployees = objectMapper.readValue(rs.getString("StorageEmployees"), new TypeReference<List<Employee>>(){});
                List<Employee> deliveriers = objectMapper.readValue(rs.getString("Deliveriers"), new TypeReference<List<Employee>>(){});

                Shift shift = new Shift(isMorningShift, date, shiftManagers, cashiers, storageEmployees, deliveriers);
                shifts.add(shift);
            }
        } catch (SQLException | JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return shifts;
    }

    public Shift getShift(LocalDate date, boolean isMorningShift) {
        String sql = "SELECT * FROM Shifts WHERE Date=? AND IsMorningShift=?";
        Shift shift = null;

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, date.toString());
            pstmt.setBoolean(2, isMorningShift);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                ObjectMapper objectMapper = new ObjectMapper();

                List<Employee> shiftManagers = objectMapper.readValue(rs.getString("ShiftManagers"), new TypeReference<List<Employee>>(){});
                List<Employee> cashiers = objectMapper.readValue(rs.getString("Cashiers"), new TypeReference<List<Employee>>(){});
                List<Employee> storageEmployees = objectMapper.readValue(rs.getString("StorageEmployees"), new TypeReference<List<Employee>>(){});
                List<Employee> deliveriers = objectMapper.readValue(rs.getString("Deliveriers"), new TypeReference<List<Employee>>(){});

                shift = new Shift(isMorningShift, date, shiftManagers, cashiers, storageEmployees, deliveriers);
            }
        } catch (SQLException | JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return shift;
    }

    public void updateShift(Shift shift, int branchId) {
        String sql = "UPDATE Shifts SET ShiftManagers=?, Cashiers=?, StorageEmployees=?, Deliveriers=? WHERE Date=? AND IsMorningShift=? AND BranchID=?";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ObjectMapper objectMapper = new ObjectMapper();

            pstmt.setString(1, objectMapper.writeValueAsString(shift.getShiftManagers()));
            pstmt.setString(2, objectMapper.writeValueAsString(shift.getCashiers()));
            pstmt.setString(3, objectMapper.writeValueAsString(shift.getStorageEmployees()));
            pstmt.setString(4, objectMapper.writeValueAsString(shift.getDeliveriers()));
            pstmt.setString(5, shift.getDate().toString());
            pstmt.setBoolean(6, shift.isMorningShift());
            pstmt.setInt(7, branchId);

            pstmt.executeUpdate();

        } catch (SQLException | JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteShift(LocalDate date, boolean isMorningShift, int branchId) {
        String sql = "DELETE FROM Shifts WHERE Date=? AND IsMorningShift=? AND BranchID=?";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, date.toString());
            pstmt.setBoolean(2, isMorningShift);
            pstmt.setInt(3, branchId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
