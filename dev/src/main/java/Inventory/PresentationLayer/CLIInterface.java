package src.main.java.Inventory.PresentationLayer;

import java.util.InputMismatchException;
import java.util.Scanner;
import src.main.java.Inventory.ServiceLayer.ServiceController;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;
import src.main.java.Inventory.DomainLayer.Category;
import src.main.java.Inventory.DomainLayer.Product;
import src.main.java.Inventory.DomainLayer.Item;

public class CLIInterface {
    private static ServiceController serviceController;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Do you want to upload the default data? answer yes or no");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                serviceController = new ServiceController(true);
                exit = true;
            } else if (response.equalsIgnoreCase("no")) {
                serviceController = new ServiceController(false);
                exit = true;
            } else {
                System.out.println("Please type yes or no");
            }
        }
        while (true) {
            try {
                System.out.print("");
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
                        getLowSupplyReport();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    private static void primeMenu() {
        System.out.println("What would you like to do: ");
        System.out.println("1. Add/remove products/item/categories");
        System.out.println("2. Display products/item/categories");
        System.out.println("3. Generate reports");
        System.out.println("4. Generate alarming report");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
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
                    removeExpired();
                    break;
                case 8:
                    removeDefective();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
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
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
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
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Consume the invalid input
        }
    }

    private static void addRemoveMenu() {
        System.out.println("Menu:");
        System.out.println("1. Add Category");
        System.out.println("2. Add Product");
        System.out.println("3. Add Item");
        System.out.println("4. Remove Category");
        System.out.println("5. Remove Product");
        System.out.println("6. Remove Item");
        System.out.println("7. Remove all expired Items");
        System.out.println("8. Remove all defective Items");
        System.out.println("9. Exit");
        System.out.print("Choose an option: ");
    }

    private static void displayHashMapsMenu() {
        System.out.println("Menu:");
        System.out.println("1. Display Categories");
        System.out.println("2. Display Products");
        System.out.println("3. Display Items");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }

    private static void reportMenu() {
        System.out.println("Menu:");
        System.out.println("1. Get Stock report");
        System.out.println("2. Get expire report");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    private static void getStockReport() {
        serviceController.generateStockReport();
    }

    private static void getExpireReport() {
        serviceController.reportExpired();
    }

    private static void addCategory() {
        System.out.print("Enter category name: ");
        String name = scanner.nextLine();
        System.out.print("Enter category ID: ");
        String id = scanner.nextLine();
        if (serviceController.addCategory(name, id)) {
            System.out.println("Category added successfully.");
        } else {
            System.out.println("Failed to add category. It might already exist.");
        }
    }

    private static void addProduct() {
        System.out.print("Enter product makat: ");
        String makat = scanner.nextLine();
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter supplier name: ");
        String supplier = scanner.nextLine();
        System.out.print("Enter product cost price: ");
        double costPrice = scanner.nextDouble();
        System.out.print("Enter product selling price: ");
        double sellingPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter product category ID: ");
        String categoryId = scanner.nextLine();
        System.out.print("Enter product sub category ID (if applicable): ");
        String subCategoryID = scanner.nextLine();
        System.out.print("Enter product minimum amount: ");
        int minimumAmount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (serviceController.addNewProduct(makat, name, supplier, costPrice, sellingPrice, categoryId, subCategoryID, minimumAmount)) {
            System.out.println("New product added successfully.");
        } else {
            System.out.println("Failed to add new product.");
        }
    }
    private static void addItem() {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter item ID (unique identifier): ");
        String itemId = scanner.nextLine();
        System.out.print("Is the item defective (yes/no)? ");
        boolean defective = scanner.nextLine().equalsIgnoreCase("yes");
        System.out.print("Is the item in the warehouse (yes/no)? ");
        boolean inWareHouse = scanner.nextLine().equalsIgnoreCase("yes");

        LocalDate expireDate;
        while (true) {
            System.out.print("Enter product expiry date (YYYY-MM-DD): ");
            String expireDateStr = scanner.nextLine();
            try {
                expireDate = LocalDate.parse(expireDateStr);
                break; // Break the loop if the date is valid
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        System.out.print("Enter category ID: ");
        String categoryID = scanner.nextLine();
        System.out.print("Enter product ID: ");
        String productID = scanner.nextLine();
        System.out.print("Enter amount of items to add to stock: ");
        int itemAmount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < itemAmount; i++) {
            String newItemId = itemId + i; // Generate a new item ID for each item
            serviceController.addItem(defective, inWareHouse, -1, -1, -1f, -1f, -1f, -1f, name, newItemId, expireDate, categoryID, productID);
            serviceController.addItemsToProduct(newItemId); // Add each item individually to the product
        }
    }


    private static void removeCategory() {
        System.out.print("Enter category ID: ");
        String categoryID = scanner.nextLine();
        serviceController.removeCategory(categoryID);
    }

    private static void removeProduct() {
        System.out.print("Enter product makat: ");
        String makat = scanner.nextLine();
        serviceController.removeProduct(makat);
    }

    private static void removeItem() {
        System.out.print("Enter item ID: ");
        String itemID = scanner.nextLine();
        serviceController.removeItemFromProduct(itemID);
        serviceController.removeItem(itemID);
    }

    private static void removeExpired() {
        serviceController.removeExpire();
    }

    private static void removeDefective() {
        serviceController.removeDefective();
    }

    // Methods to display the contents of hashmaps
    private static void displayCategories() {
        System.out.println("Categories:");
        for (Map.Entry<String, Category> entry : serviceController.getCategories().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().getName());
        }
    }

    private static void displayProducts() {
        System.out.println("Products:");
        for (Product product : serviceController.getAllProducts()) {
            System.out.println(product.getMakat() + ": " + product.getName());
        }
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
    }

    private static void getLowSupplyReport() {
        serviceController.generateLowSupplyCSVReport();
    }
}
