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
            System.out.println(conn);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            System.out.println(123);
            pstmt.setInt(1, branchId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                branch = new Branch(
                        rs.getInt("BranchID"),
                        rs.getString("BranchName"),
                        rs.getString("BranchAddress")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println(e.getMessage());
        }

        return branch;
    }

    public void addBranchToDatabase(Branch branch) {
        String sql = "INSERT INTO Branches (BranchName, BranchAddress) VALUES (?, ?)";
        try {
            Connection conn = SQLiteConnection.getConnection();
            System.out.println("got conn");
            System.out.println(conn);

            if (conn != null) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                System.out.println("41: PreparedStatement created");

                pstmt.setString(1, branch.getName());
                System.out.println("42: BranchName set to " + branch.getName());

                pstmt.setString(2, branch.getAddress());
                System.out.println("43: BranchAddress set to " + branch.getAddress());

                pstmt.executeUpdate();
                System.out.println("Branch " + branch.getBranchId() + " added to database.");
            } else {
                System.out.println("Connection is null.");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
