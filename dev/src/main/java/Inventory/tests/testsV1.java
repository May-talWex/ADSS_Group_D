package src.main.java.Inventory.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.Inventory.ServiceLayer.ServiceController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private static ItemController itemController = new ItemController();    //Will need to be changed to service controller


    @BeforeEach
    void setUp() {
        // Setup for Product tests
        System.out.println("Setting up test, clearing categories");
        serviceController = new ServiceController(false);
        serviceController.clearCategories();
        System.out.println("Categories after clearing: " + serviceController.getCategories().size());

        product = new Product( "P1", "Milk", "Tnuva", 4.0, 5.0, "C1", "SC1", 10);
        item = new Item(false, true, -1, -1, Integer.MIN_VALUE, Integer.MIN_VALUE, 3.5f, 4.5f, "Milk", "1", LocalDate.now().plusDays(10), "C1", "M1");

        // Setup for ServiceController tests
        serviceController = new ServiceController(false);
        serviceController.addCategory("Dairy", "C1");
        serviceController.addNewProduct( "P1", "Milk", "Tnuva", 4.0, 5.0, "C1", "SC1", 10);

        // Add the item to the ItemController
        itemController.addNewItem(item.defective, item.inWareHouse, item.floorBuilding, item.floorShelf, item.x, item.y, item.supplierCost, item.priceNoDiscount, item.name, item.id, item.expireDate, item.categoryID, item.productID);
    }




    // Test for Adding a New Product to Inventory
    @Test
    void testAddNewProduct() {
        List<Product> products = new ArrayList<>();
        products.add(product);
        assertTrue(products.contains(product));
    }

    // Test for Updating Product Quantity
    @Test
    void testUpdateQuantity() {
        int initialQuantity = product.getCurrentQuantity();
        product.addItem(new Item(false, true, -1, -1, Integer.MIN_VALUE, Integer.MIN_VALUE, 3.5f, 4.5f, "Milk", "2", LocalDate.now().plusDays(10), "C1", "P1"));
        assertEquals(initialQuantity + 1, product.getCurrentQuantity());
    }

    // Test for Low Stock Alert
    @Test
    void testLowStockAlert() {
        assertTrue(product.isLowStock());
        for (int i = 0; i < 10; i++) {
            product.addItem(new Item(false, true, -1, -1, Integer.MIN_VALUE, Integer.MIN_VALUE, 3.5f, 4.5f, "Milk", "I123", LocalDate.now().plusDays(10), "C1", "P1"));
        }
        assertFalse(product.isLowStock());
    }

    // Test for Generating Inventory Reports
    @Test
    void testGenerateStockReport() {
        serviceController.generateStockReport();
    }

    // Test for Tracking and Reporting Damaged Items
    @Test
    void testTrackAndReportDamagedItems() {
        itemController.reportDefectiveItem(true, item.id);
        Item retrievedItem = itemController.getItemByID(item.id);
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


    // Test for Generating Expire Report
//    @Test
//    void testGenerateExpireReport() {
//
//        serviceController.addItem(false,false,2,1,12,10,12,14,"Milk 3% Tnuva","123",LocalDate.parse("2024-07-01"), "C1", "P1");
//        List<Item> report = serviceController.generateExpireReport();
//        assertTrue(report.contains("Milk"));
//    }

    // Test for Removing Expired Items
    @Test
    void testRemoveExpiredItems() {
        itemController.addNewItem(false, true, -1, -1, Integer.MIN_VALUE, Integer.MIN_VALUE, 3.5f, 4.5f, "Milk", "2", LocalDate.now().minusDays(1), "C1", "P1");
        itemController.removeExpiredItems(new ProductController());
//        List<Item> expiredItems = itemController.reportExpiredItems();
//        assertTrue(expiredItems.isEmpty());
    }


    // Test for Removing Expired Items
//    @Test
//    void testRemoveExpiredItems() {
//        serviceController.addItemToProduct("M1", "123");
//        serviceController.removeExpire();
//        List<Item> expiredItems = serviceController.reportExpired();
//        assertTrue(expiredItems.isEmpty());
//    }
//
//    // Test for Adding Items to a Product
//    @Test
//    void testAddItemToProduct() {
//        assertTrue(serviceController.addItemToProduct("M1", "1"));
//        assertFalse(serviceController.addItemToProduct("M2", "2"));
//    }
//    // Test for Setting Product Discount
//    @Test
//    void testSetDiscount() {
//        product.applyDiscount(10);
//        assertEquals(4.5, product.getSellingPrice(), 0.001);
//    }
    // Test for Setting Supplier Cost
    @Test
    void testSetSupplierCost() {
        item.setSupplierCost(5.0f);
        assertEquals(5.0f, item.supplierCost);
    }
//
//    // Test for Moving Item to Store
//    @Test
//    void testMoveItemToStore() {
//        assertTrue(serviceController.moveItemToStore("1"));
//        assertFalse(serviceController.moveItemToStore("2")); // Item doesn't exist
//    }

    // Test for Setting Price Without Discount
    @Test
    void testSetPriceNoDiscount() {
        item.setPriceNoDiscount(6.0f);
        assertEquals(6.0f, item.priceNoDiscount);
    }

    // Test for Generating Report by Category
//    @Test
//    void testGenerateReportByCategory() {
//        serviceController.addNewProduct("M2", "P2", "Cheese", "Tnuva", 6.0, 8.0, "C1", "SC1", 5);
//        List<Product> categoryProducts = serviceController.generateReportByCategory("C1");
//        assertEquals(2, categoryProducts.size());
//        assertTrue(categoryProducts.stream().anyMatch(p -> p.getName().equals("Milk")));
//        assertTrue(categoryProducts.stream().anyMatch(p -> p.getName().equals("Cheese")));
//    }
}
