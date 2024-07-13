package src.main.java.Inventory.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import src.main.java.Inventory.DataLayer.DataBaseConnection;
import src.main.java.Inventory.DomainLayer.*;
import src.main.java.Inventory.ServiceLayer.ServiceController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@TestMethodOrder(OrderAnnotation.class)
public class TestV2 {

    private CategoryController categoryController;
    private ProductController productController;
    private ItemController itemController;
    private static ServiceController serviceController;


    @BeforeAll
    static void initialize() {
        ServiceController serviceController = new ServiceController();
        serviceController.initialize();
    }

    @BeforeEach
    void setUp() {
        DataBaseConnection.getInstance();
        serviceController = new ServiceController();
        serviceController.initialize();

        categoryController = new CategoryController();
        productController = new ProductController();
        itemController = new ItemController();
    }

    @Test
    @Order(1)
    void testRemoveCategoryWithProducts() {
        categoryController.addCategory("C002", "Furniture");
        productController.addProduct("P002", "Chair", "Supplier2", 100, 200, "C002", "SC002", 5);
        itemController.addNewItem(false, false, 1, 1, 1, 1, "Chair", "I002", LocalDate.now().plusDays(30), "C002", "P002");
        serviceController.initialize();
        assertFalse(categoryController.removeCategory("C002"));
        assertNotNull(categoryController.getCategoryByID("C002"));
    }

    @Test
    @Order(2)
    void testRemoveProductWithItems() {
        assertFalse(productController.removeProduct("P002"));
        assertNotNull(productController.getProductById("P002"));
    }


    @Test
    @Order(3)
    void testUpdateItemLocation() {
        Item item = itemController.getItemByID("I002");
        assertNotNull(item);
        item.floor = 2;
        item.branchID = 5;
        assertTrue(itemController.updateItemLocation("I002", 2, 5, 1, 1, false));
        Item updatedItem = itemController.getItemByID("I002");
        assertEquals(2, updatedItem.floor);
        assertEquals(5, updatedItem.branchID);
    }

    @Test
    @Order(4)
    void testMoveToWarehouse() {
        Item item = itemController.getItemByID("I002");
        assertNotNull(item);
        assertTrue(itemController.updateItemLocation("I002", -1, -1, -1, -1, true));
        Item updatedItem = itemController.getItemByID("I002");
        assertTrue(updatedItem.inWareHouse);
        assertEquals(-1, updatedItem.floor);
        assertEquals(-1, updatedItem.branchID);
    }

    @Test
    @Order(5)
    void testMoveFromWarehouseToStore() {
        itemController.updateItemLocation("I002", -1, -1, Float.MIN_VALUE, Float.MIN_VALUE, true);
        assertTrue(itemController.updateItemLocation("I002", 2, 5, 1, 1, false));
        Item updatedItem = itemController.getItemByID("I002");
        assertFalse(updatedItem.inWareHouse);
        assertEquals(2, updatedItem.floor);
        assertEquals(5, updatedItem.branchID);
    }

    @Test
    @Order(6)
    void testMoveWithinStore() {
        assertTrue(itemController.updateItemLocation("I002", 3, 6, 2, 2, false));
        Item updatedItem = itemController.getItemByID("I002");
        assertFalse(updatedItem.inWareHouse);
        assertEquals(3, updatedItem.floor);
        assertEquals(6, updatedItem.branchID);
        assertEquals(2, updatedItem.aisle);
        assertEquals(2, updatedItem.shelf);
    }

    @Test
    @Order(7)
    void testGetProductsByCategory() {
        categoryController.addCategory("C003", "Books");
        productController.addProduct("P003", "Book", "Supplier3", 10, 20, "C003", "SC003", 5);
        assertFalse(productController.getProductsByCategoryId("C003").isEmpty());
    }

    @Test
    @Order(8)
    void testExpiredItemsReport() {
        itemController.addNewItem(false, false, 1, 1, 1, 1, "OldItem", "I004", LocalDate.now().minusDays(1), "C002", "P002");
        List<Map<String, String>> expiredItems = itemController.generateExpiredItemsReportData();
        assertFalse(expiredItems.isEmpty());
        assertTrue(expiredItems.stream().anyMatch(item -> item.get("ID").equals("I004")));
    }

    @Test
    @Order(9)
    void testDefectiveItemsReport() {
        itemController.addNewItem(true, false, 1, 1, 1, 1, "DefectiveItem", "I005", LocalDate.now().plusDays(30), "C002", "P002");
        List<Map<String, String>> defectiveItems = itemController.generateDefectiveItemsReportData();
        assertFalse(defectiveItems.isEmpty());
        assertTrue(defectiveItems.stream().anyMatch(item -> item.get("ID").equals("I005")));
    }

    @Test
    @Order(10)
    void testUpdateCategoryDiscount() {
        categoryController.addCategory("C004", "Appliances");
        productController.addProduct("P004", "Toaster", "Supplier4", 30, 60, "C004", "SC004", 5);
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(10);
        assertTrue(categoryController.updateCategory("C004", startDate, endDate, 20));
        Category category = categoryController.getCategoryByID("C004");
        assertNotNull(category);
        assertEquals(20, category.getDiscountPercentage());
        assertEquals(startDate, category.getDiscountStartDate());
        assertEquals(endDate, category.getDiscountEndDate());
    }


    @Test
    @Order(11)
    void testCleanup() {
        itemController.removeItem("I002");
        itemController.removeItem("I004");
        itemController.removeItem("I005");
        productController.removeProduct("P002");
        productController.removeProduct("P003");
        productController.removeProduct("P004");
        categoryController.removeCategory("C002");
        categoryController.removeCategory("C003");
        categoryController.removeCategory("C004");
        assertNull(itemController.getItemByID("I002"));
        assertNull(productController.getProductById("P002"));
        assertNull(categoryController.getCategoryByID("C002"));
    }
}
