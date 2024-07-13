package HR.Tests;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import HR.Data.BranchDAO;
import HR.Domain.Branch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BranchDAOTest {
    private BranchDAO branchDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        branchDAO = new BranchDAO(mockConnection);
    }

    @Test
    public void testGetBranchFromDatabase() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("BranchID")).thenReturn(1);
        when(mockResultSet.getString("BranchName")).thenReturn("Test Branch");
        when(mockResultSet.getString("BranchAddress")).thenReturn("123 Test St");

        Branch branch = branchDAO.getBranchFromDatabase(1);
        assertNotNull(branch);
        assertEquals(1, branch.getBranchId());
        assertEquals("Test Branch", branch.getName());
        assertEquals("123 Test St", branch.getAddress());

        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).close();
        verify(mockPreparedStatement, times(1)).close();
    }

    @Test
    public void testAddBranchToDatabase() throws SQLException {
        Branch branch = new Branch(1, "Test Branch", "123 Test St");
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        branchDAO.addBranchToDatabase(branch);

        verify(mockPreparedStatement, times(1)).setString(1, "Test Branch");
        verify(mockPreparedStatement, times(1)).setString(2, "123 Test St");
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).close();
    }
}
