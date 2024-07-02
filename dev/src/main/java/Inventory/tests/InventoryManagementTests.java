package src.main.java.Inventory.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.Inventory.ServiceLayer.ServiceController;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import src.main.java.Inventory.DomainLayer.Item;
import src.main.java.Inventory.DomainLayer.CategoryController;
import src.main.java.Inventory.DomainLayer.ItemController;
import src.main.java.Inventory.DomainLayer.ProductController;
import src.main.java.Inventory.DomainLayer.Product;

class InventoryManagementTests {

    private Product product;
    private Item item;
    private ServiceController serviceController;

    @BeforeEach
    void setUp() {
        // Setup for Product tests
        System.out.println("Setting up test, clearing categories");
        serviceController = new ServiceController();
        System.out.println("Categories after clearing: " + serviceController.categoryController.getCategories().size());

        product = new Product("P1", "Milk", "Tnuva", 4.0f, 5.0f, "C1", "SC1", 10);
        item = new Item(false, true, -1, -1, Float.MIN_VALUE, Float.MIN_VALUE,
                "Milk", "1", LocalDate.now().plusDays(10), "C1", "P1");

        // Setup for ServiceController tests
        serviceController.addCategory("Dairy", "C1");
        serviceController.addProduct("P1", "Milk", "Tnuva", 4.0f, 5.0f, "C1", "SC1", 10);

        // Add the item to the ItemController
        serviceController.itemController.addNewItem(item.defective, item.inWareHouse, item.floorBuilding, item.floorShelf,
                item.x, item.y, 0.0f, 0.0f, item.name, item.id, item.expireDate, item.categoryID, item.productID);
    }


    // Test for Updating Product Quantity
    @Test
    void testUpdateQuantity() {
        int initialQuantity = product.getCurrentQuantity();
        product.addItem(new Item(false, true, -1, -1, Float.MIN_VALUE, Float.MIN_VALUE,
                "Milk", "2", LocalDate.now().plusDays(10), "C1", "P1"));
        assertEquals(initialQuantity + 1, product.getCurrentQuantity());
    }

    // Test for Low Stock Alert
    @Test
    void testLowStockAlert() {
        assertTrue(product.isLowStock());
        for (int i = 0; i < 10; i++) {
            product.addItem(new Item(false, true, -1, -1, Float.MIN_VALUE, Float.MIN_VALUE,
                    "Milk", "I123", LocalDate.now().plusDays(10), "C1", "P1"));
        }
        assertFalse(product.isLowStock());
    }


    // Test for Tracking and Reporting Damaged Items
    @Test
    void testTrackAndReportDamagedItems() {
        serviceController.itemController.reportDefectiveItem(true, item.id);
        Item retrievedItem = serviceController.itemController.getItemByID(item.id);
        assertNotNull(retrievedItem);
        assertTrue(retrievedItem.defective);
    }

    // Test for Adding a Category
    @Test
    void testAddCategory() {
        assertTrue(serviceController.addCategory("Beverages", "C2"));
        assertFalse(serviceController.addCategory("Dairy", "C1")); // Duplicate ID
    }

    // Test for Removing a Category
    @Test
    void testRemoveCategory() {
        serviceController.addCategory("Dairy", "C45");
        assertTrue(serviceController.removeCategory("C45"));
        assertFalse(serviceController.removeCategory("C77")); // Non-existent ID
    }


    // Test for Removing Expired Items
    @Test
    void testRemoveExpiredItems() {
        assertTrue(serviceController.itemController.addNewItem(false, true, -1, -1, Float.MIN_VALUE, Float.MIN_VALUE,
                0.0f, 0.0f, // Placeholder values for supplierCost and priceNoDiscount
                "Milk", "2", LocalDate.now().minusDays(1), "C1", "P1"));

        serviceController.itemController.removeExpiredItems();

        List<Map<String, String>> expiredItems = serviceController.itemController.generateExpiredItemsReportData();
        assertTrue(expiredItems.isEmpty());
    }


    // Test for Adding Items to a Product
    @Test
    void testAddItemToProduct() {
        // Simulate adding an item with all required arguments
        assertTrue(serviceController.addItem(false, true, -1, -1, Float.MIN_VALUE, Float.MIN_VALUE,
                0.0f, 0.0f, "Milk", "1", LocalDate.now().plusDays(10), "C1", "P1"));
        // Attempt to add an item with a non-existent product ID
        assertFalse(serviceController.addItem(false, true, -1, -1, Float.MIN_VALUE, Float.MIN_VALUE,
                0.0f, 0.0f, "Milk", "2", LocalDate.now().plusDays(10), "C1", "P2"));
    }

    // Test for Setting Product Discount
    @Test
    void testSetDiscount() {
        product.applyDiscount(10);
        assertEquals(4.5f, product.getSellingPrice(), 0.001f);
    }

    // Test for Moving Item to Store
    @Test
    void testMoveItemToStore() {
        assertTrue(serviceController.itemController.moveItemToStore("1"));
        assertFalse(serviceController.itemController.moveItemToStore("2")); // Item doesn't exist
    }

    // Test for Generating Report by Category
    @Test
    void testGenerateReportByCategory() {
        serviceController.addProduct("P2", "Cheese", "Tnuva", 6.0f, 8.0f, "C1", "SC1", 5);
        List<Product> categoryProducts = serviceController.categoryController.getCategoryByID("C1").products;
        assertEquals(2, categoryProducts.size());
        assertTrue(categoryProducts.stream().anyMatch(p -> p.getName().equals("Milk")));
        assertTrue(categoryProducts.stream().anyMatch(p -> p.getName().equals("Cheese")));
    }


    @Test
    void testGetCategoryByID() {
        assertNotNull(serviceController.categoryController.getCategoryByID("C1"));
        assertNull(serviceController.categoryController.getCategoryByID("C99")); // Non-existent ID
    }

    // Test for Checking if a Category Exists
    @Test
    void testDoesCategoryExist() {
        assertTrue(serviceController.categoryController.doesCategoryExist("C1"));
        assertFalse(serviceController.categoryController.doesCategoryExist("C99")); // Non-existent ID
    }

    // Test for Removing a Product from a Category
    @Test
    void testRemoveProductFromCategory() {
        assertTrue(serviceController.categoryController.removeProductFromCategory("C1", product));
        assertFalse(serviceController.categoryController.removeProductFromCategory("C1", new Product("P99", "NonExistent", "Brand", 1.0f, 1.0f, "C1", "SC1", 0))); // Non-existent product
    }

    // Test for Updating a Category
    @Test
    void testUpdateCategory() {
        LocalDate startDiscount = LocalDate.now().plusDays(1);
        LocalDate endDiscount = LocalDate.now().plusDays(10);
        float discountPercentage = 10.0f;

        assertTrue(serviceController.categoryController.updateCategory("C1", startDiscount, endDiscount, discountPercentage));
        assertFalse(serviceController.categoryController.updateCategory("C99", startDiscount, endDiscount, discountPercentage)); // Non-existent ID
    }

    @Test
    void testAddProduct() {
        assertTrue(serviceController.productController.addProduct("P2", "Cheese", "Tnuva", 6.0f, 8.0f, "C1", "SC1", 5));
        assertFalse(serviceController.productController.addProduct("P1", "Milk", "Tnuva", 4.0f, 5.0f, "C1", "SC1", 10)); // Duplicate ID
    }

    // Test for Removing a Product
    @Test
    void testRemoveProduct() {
        assertTrue(serviceController.productController.removeProduct("P1"));
        assertFalse(serviceController.productController.removeProduct("P99")); // Non-existent ID
    }

    // Test for Checking if a Product Exists
    @Test
    void testDoesProductExist() {
        assertTrue(serviceController.productController.doesProductExist("P1"));
        assertFalse(serviceController.productController.doesProductExist("P99")); // Non-existent ID
    }

    // Test for Generating Stock Report Data
    @Test
    void testGenerateStockReportData() {
        List<Map<String, String>> stockReport = serviceController.productController.generateStockReportData();
        assertFalse(stockReport.isEmpty());
    }

    // Test for Generating Category Product Report Data
    @Test
    void testGenerateCategoryProductReportData() {
        List<Map<String, String>> categoryProductReport = serviceController.productController.generateCategoryProductReportData("C1");
        assertFalse(categoryProductReport.isEmpty());
    }

    // Test for Generating Low Supply Report Data
    @Test
    void testGenerateLowSupplyReportData() {
        List<Map<String, String>> lowSupplyReport = serviceController.productController.generateLowSupplyReportData();
        assertFalse(lowSupplyReport.isEmpty());
    }

    // Test for Generating Low Supply Report Data with Delta
    @Test
    void testGenerateLowSupplyReportDataWithDelta() {
        List<Map<String, String>> lowSupplyReportWithDelta = serviceController.productController.generateLowSupplyReportDataWithDelta();
        assertFalse(lowSupplyReportWithDelta.isEmpty());
    }

    // Test for Updating Product Discount
    @Test
    void testUpdateProductDiscount() {
        LocalDate startDiscount = LocalDate.now().plusDays(1);
        LocalDate endDiscount = LocalDate.now().plusDays(10);
        int discountPercentage = 10;

        assertTrue(serviceController.productController.updateProductDiscount("P1", discountPercentage, startDiscount, endDiscount));
        assertFalse(serviceController.productController.updateProductDiscount("P99", discountPercentage, startDiscount, endDiscount)); // Non-existent ID
    }

    // Test for Checking if Product Contains Items
    @Test
    void testProductContainsItems() {
        assertTrue(serviceController.productController.productContainsItems("P1"));
        assertFalse(serviceController.productController.productContainsItems("P99")); // Non-existent product
    }

    // Test for Getting Products by Category ID
    @Test
    void testGetProductsByCategoryId() {
        List<Product> products = serviceController.productController.getProductsByCategoryId("C1");
        assertFalse(products.isEmpty());
    }
}
