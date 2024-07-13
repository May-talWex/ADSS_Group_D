package HR.Data;

import HR.Domain.Branch;
import HR.Domain.Employee;
import HR.Domain.Shift;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import HR.Data.SQLiteConnection;

public class ShiftsDAO {

    public void addShift(Shift shift, int branchId) {
        String sql = "INSERT INTO Shifts(Date, IsMorningShift, BranchID, ShiftManagers, Cashiers, StorageEmployees, Deliveriers) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ObjectMapper objectMapper = new ObjectMapper();

            pstmt.setString(1, shift.getDate().toString());
            pstmt.setBoolean(2, shift.isMorningShift());
            pstmt.setInt(3, branchId);
            pstmt.setString(4, objectMapper.writeValueAsString(shift.getShiftManagersString()));
            pstmt.setString(5, objectMapper.writeValueAsString(shift.getCashiersString()));
            pstmt.setString(6, objectMapper.writeValueAsString(shift.getStorageEmployeesString()));
            pstmt.setString(7, objectMapper.writeValueAsString(shift.getDeliveriersString()));

            pstmt.executeUpdate();

        } catch (SQLException | JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Shift> getAllShifts(Branch branch) {
        String sql = "SELECT * FROM Shifts WHERE BranchID=?";
        List<Shift> shifts = new ArrayList<>();

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, branch.getBranchId());
            ResultSet rs = pstmt.executeQuery();

            ObjectMapper objectMapper = new ObjectMapper();

            while (rs.next()) {
                boolean isMorningShift = rs.getBoolean("IsMorningShift");
                LocalDate date = LocalDate.parse(rs.getString("Date"));

                String [] shiftManagersString = rs.getString("ShiftManagers").replaceAll("\\[", "")
                        .replaceAll("]", "").replaceAll("\"", "")
                        .split(",");
                String [] Cashiers = rs.getString("Cashiers").replaceAll("\\[", "")
                        .replaceAll("]", "").replaceAll("\"", "")
                        .split(",");
                String [] StorageEmployees = rs.getString("StorageEmployees").replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "")
                        .split(",");
                String [] Deliveriers = rs.getString("Deliveriers").replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "")
                        .split(",");
                List<Employee> shiftManagers = new ArrayList<>();
                List<Employee> cashiers = new ArrayList<>();
                List<Employee> storageEmployees = new ArrayList<>();
                List<Employee> deliveriers = new ArrayList<>();

                for (String id : shiftManagersString) {
                    shiftManagers.add(branch.getWorkerById(Integer.parseInt(id)));
                }
                for (String id : Cashiers) {
                    cashiers.add(branch.getWorkerById(Integer.parseInt(id)));
                }
                for (String id : StorageEmployees) {
                    storageEmployees.add(branch.getWorkerById(Integer.parseInt(id)));
                }
                for (String id : Deliveriers) {
                    deliveriers.add(branch.getWorkerById(Integer.parseInt(id)));
                }

                Shift shift = new Shift(isMorningShift, date);
                shift.setShiftManagers(shiftManagers);
                shift.setCashiers(cashiers);
                shift.setStorageEmployees(storageEmployees);
                shift.setDeliveriers(deliveriers);

                shifts.add(shift);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return shifts;
    }

    public Shift getShift(LocalDate date, boolean isMorningShift, Branch branch) {
        String sql = "SELECT * FROM Shifts WHERE Date=? AND IsMorningShift=? AND BranchID=?";
        Shift shift = null;

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, date.toString());
            pstmt.setBoolean(2, isMorningShift);
            pstmt.setInt(3, branch.getBranchId());
            ResultSet rs = pstmt.executeQuery();

            ObjectMapper objectMapper = new ObjectMapper();

            if (rs.next()) {
                List<Employee> shiftManagers = objectMapper.readValue(rs.getString("ShiftManagers"), new TypeReference<List<Employee>>() {});
                List<Employee> cashiers = objectMapper.readValue(rs.getString("Cashiers"), new TypeReference<List<Employee>>() {});
                List<Employee> storageEmployees = objectMapper.readValue(rs.getString("StorageEmployees"), new TypeReference<List<Employee>>() {});
                List<Employee> deliveriers = objectMapper.readValue(rs.getString("Deliveriers"), new TypeReference<List<Employee>>() {});

                shift = new Shift(isMorningShift, date);
                shift.setShiftManagers(shiftManagers);
                shift.setCashiers(cashiers);
                shift.setStorageEmployees(storageEmployees);
                shift.setDeliveriers(deliveriers);
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return shift;
    }

    public void updateShift(Shift shift, Branch branch) {
        String sql = "UPDATE Shifts SET ShiftManagers=?, Cashiers=?, StorageEmployees=?, Deliveriers=? WHERE Date=? AND IsMorningShift=? AND BranchID=?";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            ObjectMapper objectMapper = new ObjectMapper();

            pstmt.setString(1, objectMapper.writeValueAsString(shift.getShiftManagersString()));
            pstmt.setString(2, objectMapper.writeValueAsString(shift.getCashiersString()));
            pstmt.setString(3, objectMapper.writeValueAsString(shift.getStorageEmployeesString()));
            pstmt.setString(4, objectMapper.writeValueAsString(shift.getDeliveriersString()));
            pstmt.setString(5, shift.getDate().toString());
            pstmt.setBoolean(6, shift.isMorningShift());
            pstmt.setInt(7, branch.getBranchId());

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