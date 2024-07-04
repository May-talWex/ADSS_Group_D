package HR.Tests;

import HR.Data.BranchDAO;
import HR.Domain.Branch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

public class BranchDAOIntegrationTest {
    private Connection connection;
    private BranchDAO branchDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        branchDAO = new BranchDAO(connection);
        String createTableSQL = "CREATE TABLE Branches (" +
                "BranchID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "BranchName TEXT NOT NULL," +
                "BranchAddress TEXT NOT NULL)";
        connection.createStatement().execute(createTableSQL);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void testAddBranchToDatabase() {
        Branch branch = new Branch(1, "Integration Test Branch", "456 Test Ave");
        branchDAO.addBranchToDatabase(branch);

        Branch retrievedBranch = branchDAO.getBranchFromDatabase(1);
        assertNotNull(retrievedBranch);
        assertEquals("Integration Test Branch", retrievedBranch.getName());
        assertEquals("456 Test Ave", retrievedBranch.getAddress());
    }

    @Test
    public void testGetBranchFromDatabase() {
        Branch branch = new Branch(1, "Integration Test Branch", "456 Test Ave");
        branchDAO.addBranchToDatabase(branch);

        Branch retrievedBranch = branchDAO.getBranchFromDatabase(1);
        assertNotNull(retrievedBranch);
        assertEquals("Integration Test Branch", retrievedBranch.getName());
        assertEquals("456 Test Ave", retrievedBranch.getAddress());
    }
}
