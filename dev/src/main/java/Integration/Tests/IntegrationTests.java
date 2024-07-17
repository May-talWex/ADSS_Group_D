package Integration.Tests;
import HR.Domain.*;
import HR.Domain.EmployeeTypes.StorageEmployee;
import Inventory.DomainLayer.*;
import Inventory.ServiceLayer.ServiceController;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTests {

    private Branch testBranch;
    private Employee employee;
    private CategoryController categoryController;
    private ProductController productController;
    private ItemController itemController;
    private static ServiceController serviceController;

    @BeforeAll
    static void initializeServiceController() {
        serviceController = new ServiceController();
        serviceController.initialize();
    }

    @BeforeEach
    void setUp() {
        // Set up HR domain objects
        testBranch = new Branch(1, "Test Branch", "123 Test St.");
        BankAccount bankAccount = new BankAccount(12345, 67890, 54321);
        Salary salary = new Salary(50000);
        employee = new Employee(1, "John Doe", "john.doe@example.com", bankAccount, testBranch, salary);

        // Set up Inventory domain objects
        categoryController = new CategoryController();
        productController = new ProductController();
        itemController = new ItemController();
    }

    @Test
    @Order(1)
    void testCreateEmployee() {
        assertEquals(1, employee.getEmployeeId());
        assertEquals("John Doe", employee.getName());
        assertEquals("john.doe@example.com", employee.getEmail());
        assertEquals(testBranch, employee.getBranch());
    }

    @Test
    @Order(2)
    void testAssignStorageEmployeeRole() throws Exception {
        EmployeeType storageEmployee = new StorageEmployee();
        employee.addPossiblePosition(storageEmployee);
        assertTrue(employee.getPossiblePositions().contains(storageEmployee));
    }

    @Test
    @Order(3)
    void testAddCategory() {
        assertTrue(categoryController.addCategory("C001", "Dairy"));
    }

    @Test
    @Order(4)
    void testAddProduct() {
        assertTrue(productController.addProduct("P001", "Milk", "Supplier1", 5, 6, "C001", "SC001", 10));
    }

    @Test
    @Order(5)
    void testAddItem() {
        LocalDate expiryDate = LocalDate.of(2026, 1, 1);
        assertTrue(itemController.addNewItem(false, true, -1, 1, -1, -1, "Milk 1%", "MLK11", expiryDate, "C001", "P001"));
    }

    @Test
    @Order(6)
    void testGetItem() {
        assertNotNull(itemController.getItemByID("MLK11"));
    }

    @AfterAll
    static void cleanUp() {
        // Clean up Inventory domain objects
        ItemController itemController = new ItemController();
        itemController.removeItem("MLK11");

        ProductController productController = new ProductController();
        productController.removeProduct("P001");

        CategoryController categoryController = new CategoryController();
        categoryController.removeCategory("C001");

    }
}
