package HR.Data;

import HR.Domain.Branch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BranchDAO {
    public Branch getBranchFromDatabase(int branchId) {
        String sql = "SELECT BranchID, BranchName, BranchAddress FROM Branches WHERE BranchID = ?";
        Branch branch = null;
        System.out.println("Trying getBranchFromDatabase");
        try {
            Connection conn = SQLiteConnection.getConnection();
            if (conn == null || conn.isClosed()) {
                System.out.println("Connection is null or closed.");
                return null;
            }
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, branchId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                branch = new Branch(
                        rs.getInt("BranchID"),
                        rs.getString("BranchName"),
                        rs.getString("BranchAddress")
                );
                // System.out.println("Branch retrieved: " + branch);
            } else {
                System.out.println("No branch found with ID: " + branchId);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        return branch;
    }

    public void addBranchToDatabase(Branch branch) {
        String sql = "INSERT INTO Branches (BranchName, BranchAddress) VALUES (?, ?)";
        try {
            Connection conn = SQLiteConnection.getConnection();
            System.out.println("Connection obtained: " + conn);

            if (conn != null && !conn.isClosed()) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                System.out.println("PreparedStatement created: " + pstmt);

                pstmt.setString(1, branch.getName());
                System.out.println("BranchName set to " + branch.getName());

                pstmt.setString(2, branch.getAddress());
                System.out.println("BranchAddress set to " + branch.getAddress());

                pstmt.executeUpdate();
                System.out.println("Branch added to database.");
            } else {
                System.out.println("Connection is null or closed.");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
