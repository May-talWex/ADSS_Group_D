package HR.Data;

import HR.Domain.Branch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BranchDAO {
    private Connection connection;

    // Constructor for production
    public BranchDAO() {
        this.connection = SQLiteConnection.getConnection();
    }

    // Constructor for testing
    public BranchDAO(Connection connection) {
        this.connection = connection;
    }

    public Branch getBranchFromDatabase(int branchId) {
        String sql = "SELECT BranchID, BranchName, BranchAddress FROM Branches WHERE BranchID = ?";
        Branch branch = null;
        //System.out.println("Trying getBranchFromDatabase");
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, branchId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                branch = new Branch(
                        rs.getInt("BranchID"),
                        rs.getString("BranchName"),
                        rs.getString("BranchAddress")
                );
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return branch;
    }

    public void addBranchToDatabase(Branch branch) {
        String sql = "INSERT INTO Branches (BranchName, BranchAddress) VALUES (?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            System.out.println("PreparedStatement created");
            pstmt.setString(1, branch.getName());
            System.out.println("BranchName set to " + branch.getName());
            pstmt.setString(2, branch.getAddress());
            System.out.println("BranchAddress set to " + branch.getAddress());
            pstmt.executeUpdate();
            System.out.println("Branch " + branch.getBranchId() + " added to database.");
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
}

