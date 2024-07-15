package Inventory.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import Inventory.DataLayer.DataBaseConnection;
import Inventory.DomainLayer.*;

import java.time.LocalDate;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestV1 {

    private CategoryController categoryController;
    private ProductController productController;
    private ItemController itemController;

    @BeforeEach
    void setUp() {
        DataBaseConnection.getInstance();
        categoryController = new CategoryController();
        productController = new ProductController();
        itemController = new ItemController();
    }

    @Test
    @Order(1)
    void testAddCategory() {
        assertTrue(categoryController.addCategory("C001", "Electronics"));
    }

    @Test
    @Order(2)
    void testGetCategory() {
        assertNotNull(categoryController.getCategoryByID("C001"));
    }

    @Test
    @Order(3)
    void testAddProduct() {
        assertTrue(productController.addProduct("P001", "Laptop", "Supplier1", 500, 1000, "C001", "SC001", 10));
    }

    @Test
    @Order(4)
    void testGetProduct() {
        assertNotNull(productController.getProductById("P001"));
    }

    @Test
    @Order(5)
    void testAddItem() {
        assertTrue(itemController.addNewItem(false, true, 0, 0, 0, 0, "Laptop", "I001", LocalDate.now().plusDays(30), "C001", "P001"));
    }

    @Test
    @Order(6)
    void testGetItem() {
        assertNotNull(itemController.getItemByID("I001"));
    }

    @Test
    @Order(7)
    void testUpdateProductDiscount() {
        assertTrue(productController.updateProductDiscount("P001", 1200, LocalDate.now().plusDays(30), LocalDate.now().plusDays(60)));
    }

    @Test
    @Order(8)
    void testUpdateItemDefective() {
        assertTrue(itemController.reportDefectiveItem(true, "I001"));
    }

    @Test
    @Order(9)
    void testDeleteItem() {
        assertTrue(itemController.removeItem("I001"));
        assertNull(itemController.getItemByID("I001"));
    }

    @Test
    @Order(10)
    void testDeleteProduct() {
        assertTrue(productController.removeProduct("P001"));
        assertNull(productController.getProductById("P001"));
    }

    @Test
    @Order(11)
    void testDeleteCategory() {
        assertTrue(categoryController.removeCategory("C001"));
        assertNull(categoryController.getCategoryByID("C001"));
    }
}