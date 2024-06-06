package Inventory.PresentationLayer;

import java.util.InputMismatchException;
import java.util.Scanner;
import Inventory.ServiceLayer.ServiceController;
import java.time.LocalDate;
import java.util.Map;
import Inventory.DomainLayer.Category;
import Inventory.DomainLayer.Product;
import Inventory.DomainLayer.Item;

public class CLIInterface {
    private static ServiceController serviceController = new ServiceController();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.print("");
                System.out.flush();
                primeMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1:
                        handleAddRemoveMenu();
                        break;
                    case 2:
                        handleDisplayHashMapsMenu();
                        break;
                    case 3:
                        handleReportMenu();
                        break;
                    case 4:
                        handleRemoveDefectiveAndExpiringMenu();
                        break;
                    case 5:
                        handleStockAlertsMenu();
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        System.out.flush();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        System.out.flush();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                System.out.flush();
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    private static void primeMenu() {
        System.out.println("What would you like to do: ");
        System.out.println("1. Add/remove products/item/categories");
        System.out.println("2. Display products/item/categories");
        System.out.println("3. Generate reports");
        System.out.println("4. Remove defective/expiring products");
        System.out.println("5. Generate stock alerts");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
        System.out.flush();
    }

    private static void handleAddRemoveMenu() {
        try {
            addRemoveMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addCategory();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    addItem();
                    break;
                case 4:
                    removeCategory();
                    break;
                case 5:
                    removeProduct();
                    break;
                case 6:
                    removeItem();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    System.out.flush();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    System.out.flush();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            System.out.flush();
            scanner.nextLine(); // Consume the invalid input
        }
    }

    private static void handleDisplayHashMapsMenu() {
        try {
            displayHashMapsMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    displayCategories();
                    break;
                case 2:
                    displayProducts();
                    break;
                case 3:
                    displayItems();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.out.flush();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    System.out.flush();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            System.out.flush();
            scanner.nextLine(); // Consume the invalid input
        }
    }

    private static void handleReportMenu() {
        try {
            reportMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    getStockReport();
                    break;
                case 2:
                    getExpireReport();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.out.flush();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    System.out.flush();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            System.out.flush();
            scanner.nextLine(); // Consume the invalid input
        }
    }

    private static void handleRemoveDefectiveAndExpiringMenu() {
        try {
            removeDefectiveAndExpiringMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    removeDefectiveItems();
                    break;
                case 2:
                    removeExpiringItems();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.out.flush();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    System.out.flush();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            System.out.flush();
            scanner.nextLine(); // Consume the invalid input
        }
    }

    private static void handleStockAlertsMenu() {
        serviceController.generateStockAlerts();
    }

    private static void addRemoveMenu() {
        System.out.println("Menu:");
        System.out.println("1. Add Category");
        System.out.println("2. Add Product");
        System.out.println("3. Add Item");
        System.out.println("4. Remove Category");
        System.out.println("5. Remove Product");
        System.out.println("6. Remove Item");
        System.out.println("7. Exit");
        System.out.print("Choose an option: ");
        System.out.flush();
    }

    private static void displayHashMapsMenu() {
        System.out.println("Menu:");
        System.out.println("1. Display Categories");
        System.out.println("2. Display Products");
        System.out.println("3. Display Items");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
        System.out.flush();
    }

    private static void reportMenu() {
        System.out.println("Menu:");
        System.out.println("1. Get Stock report");
        System.out.println("2. Get expire report");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        System.out.flush();
    }

    private static void removeDefectiveAndExpiringMenu() {
        System.out.println("Menu:");
        System.out.println("1. Remove defective items");
        System.out.println("2. Remove expiring items");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        System.out.flush();
    }

    private static void getStockReport() {
        serviceController.generateStockReport();
        System.out.flush();
    }

    private static void getExpireReport() {
        serviceController.reportExpired();
        System.out.flush();
    }

    private static void addCategory() {
        System.out.print("Enter category name: ");
        System.out.flush();
        String name = scanner.nextLine();
        System.out.print("Enter category ID: ");
        System.out.flush();
        String id = scanner.nextLine();
        if (serviceController.addCategory(name, id)) {
            System.out.println("Category added successfully.");
        } else {
            System.out.println("Failed to add category. It might already exist.");
        }
        System.out.flush();
    }

    private static void addProduct() {
        System.out.print("Enter product makat: ");
        System.out.flush();
        String makat = scanner.nextLine();
        System.out.print("Enter product name: ");
        System.out.flush();
        String name = scanner.nextLine();
        System.out.print("Enter supplier name: ");
        System.out.flush();
        String supplier = scanner.nextLine();
        System.out.print("Enter product cost price: ");
        System.out.flush();
        double costPrice = scanner.nextDouble();
        System.out.print("Enter product selling price: ");
        System.out.flush();
        double sellingPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter product category ID: ");
        System.out.flush();
        String categoryId = scanner.nextLine();
        System.out.print("Enter product sub category ID (if applicable): ");
        System.out.flush();
        String subCategoryID = scanner.nextLine();
        System.out.print("Enter product minimum amount: ");
        System.out.flush();
        int minimumAmount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (serviceController.addNewProduct(makat, name, supplier, costPrice, sellingPrice, categoryId, subCategoryID, minimumAmount)) {
            System.out.println("New product added successfully.");
        } else {
            System.out.println("Failed to add new product.");
        }
        System.out.flush();
    }

    private static void addItem() {
        System.out.print("Enter item name: ");
        System.out.flush();
        String name = scanner.nextLine();
        System.out.print("Enter item ID (unique identifier): ");
        System.out.flush();
        String itemId = scanner.nextLine();
        System.out.print("Is the item defective (yes/no)? ");
        System.out.flush();
        boolean defective = scanner.nextLine().equalsIgnoreCase("yes");
        System.out.print("Is the item in the warehouse (yes/no)? ");
        System.out.flush();
        boolean inWareHouse = scanner.nextLine().equalsIgnoreCase("yes");
        System.out.print("Enter product expiry date (YYYY-MM-DD): ");
        System.out.flush();
        String expireDateStr = scanner.nextLine();
        LocalDate expireDate = LocalDate.parse(expireDateStr);
        System.out.print("Enter category ID: ");
        System.out.flush();
        String categoryID = scanner.nextLine();
        System.out.print("Enter product ID: ");
        System.out.flush();
        String productID = scanner.nextLine();

        if (serviceController.addItem(defective, inWareHouse, -1, -1, -1f, -1f, -1f, -1f, name, itemId, expireDate, categoryID, productID)) {
            System.out.println("Item added successfully.");
        } else {
            System.out.println("Failed to add item.");
        }
        System.out.flush();
    }

    private static void removeCategory() {
        System.out.print("Enter category ID: ");
        System.out.flush();
        String categoryID = scanner.nextLine();
        if (serviceController.removeCategory(categoryID)) {
            System.out.println("Category was successfully removed.");
        } else {
            System.out.println("Failed to remove category.");
        }
        System.out.flush();
    }

    private static void removeProduct() {
        System.out.print("Enter product makat: ");
        System.out.flush();
        String makat = scanner.nextLine();
        if (serviceController.removeProduct(makat)) {
            System.out.println("Product removed successfully.");
        } else {
            System.out.println("Failed to remove product.");
        }
        System.out.flush();
    }

    private static void removeItem() {
        System.out.print("Enter item ID: ");
        System.out.flush();
        String itemID = scanner.nextLine();
        if (serviceController.removeItem(itemID)) {
            System.out.println("Item removed successfully.");
        } else {
            System.out.println("Failed to remove item.");
        }
        System.out.flush();
    }

    private static void removeDefectiveItems() {
        serviceController.removeDefectiveItems();
        System.out.println("Defective items removed successfully.");
        System.out.flush();
    }

    private static void removeExpiringItems() {
        serviceController.removeExpiredItems();
        System.out.println("Expiring items removed successfully.");
        System.out.flush();
    }

    // Methods to display the contents of hashmaps
    private static void displayCategories() {
        System.out.println("Categories:");
        for (Map.Entry<String, Category> entry : serviceController.getCategories().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().getName());
        }
        System.out.flush();
    }

    private static void displayProducts() {
        System.out.println("Products:");
        for (Product product : serviceController.getAllProducts()) {
            System.out.println(product.getMakat() + ": " + product.getName());
        }
        System.out.flush();
    }

    private static void displayItems() {
        System.out.println("Warehouse Items:");
        for (Item item : serviceController.getWarehouseItems()) {
            System.out.println(item.getID() + ": " + item.getName());
        }
        System.out.println("Store Items:");
        for (Item item : serviceController.getStoreItems()) {
            System.out.println(item.getID() + ": " + item.getName());
        }
        System.out.flush();
    }
}
