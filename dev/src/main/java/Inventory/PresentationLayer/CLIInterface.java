package src.main.java.Inventory.PresentationLayer;

import java.util.InputMismatchException;
import java.util.Scanner;
import src.main.java.Inventory.ServiceLayer.ServiceController;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CLIInterface {
    private static ServiceController serviceController = new ServiceController();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initialize();
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
                        handleUpdateMenu();
                        break;
                    case 3:
                        handleReportMenu();
                        break;
                    case 4:
                        getLowSupplyReport();
                        break;
                    case 5:
                        generateLowSupplyDeltaCSVReport();
                        break;
                    case 6:
                        generateReorderCSVReport();
                        break;
                    case 7:
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

    private static void initialize() {
        serviceController.initialize();
    }

    private static void primeMenu() {
        System.out.println("What would you like to do: ");
        System.out.println("1. Add/remove products/item/categories");
        System.out.println("2. Update items/categories/products");
        System.out.println("3. Generate reports");
        System.out.println("4. Generate low stock report");
        System.out.println("5. Generate low stock report for supplier");
        System.out.println("6. Generate regular stock report for supplier");
        System.out.println("7. Exit");
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

    private static void handleReportMenu() {
        try {
            reportMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    generateStockReport();
                    break;
                case 2:
                    getExpireReport();
                    break;
                case 3:
                    getDefectiveReport();
                    break;
                case 4:
                    generateReportByCategoryID();
                    break;
                case 5:
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

    private static void handleUpdateMenu() {
        try {
            updateMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    updateProductDiscount();
                    break;
                case 2:
                    updateItemDefective();
                    break;
                case 3:
                    updateItemLocation();
                    break;
                case 4:
                    updateCategory();
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


    private static void reportMenu() {
        System.out.println("Menu:");
        System.out.println("1. Get Stock report");
        System.out.println("2. Get expire report");
        System.out.println("3. Get defective report");
        System.out.println("4. Generate report by category ID");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private static void updateMenu() {
        System.out.println("Update Menu:");
        System.out.println("1. Update Product Discount");
        System.out.println("2. Update Item Defective Status");
        System.out.println("3. Update Item Location");
        System.out.println("4. Update Category Discount");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private static void getExpireReport() {
        serviceController.generateExpiredCSVReport();
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

    private static void removeCategory() {
        System.out.print("Enter category ID: ");
        String categoryID = scanner.nextLine();
        if (serviceController.removeCategory(categoryID)) {
            System.out.println("Category removed successfully.");
        }
    }


    private static void updateCategory() {
        System.out.print("Enter category ID: ");
        String categoryID = scanner.nextLine();

        LocalDate startDate;
        while (true) {
            System.out.print("Enter new discount start date (YYYY-MM-DD): ");
            String startDateStr = scanner.nextLine();
            try {
                startDate = LocalDate.parse(startDateStr);
                break; // Break the loop if the date is valid
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        LocalDate endDate;
        while (true) {
            System.out.print("Enter new discount end date (YYYY-MM-DD): ");
            String endDateStr = scanner.nextLine();
            try {
                endDate = LocalDate.parse(endDateStr);
                break; // Break the loop if the date is valid
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        System.out.print("Enter new discount percentage: ");
        float discountPercentage = scanner.nextFloat();
        scanner.nextLine(); // Consume newline

        if (serviceController.updateCategory(categoryID, startDate, endDate, discountPercentage)) {
            System.out.println("Category updated successfully.");
        } else {
            System.out.println("Failed to update category.");
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
        float costPrice = scanner.nextFloat();
        System.out.print("Enter product selling price: ");
        float sellingPrice = scanner.nextFloat();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter product category ID: ");
        String categoryId = scanner.nextLine();
        System.out.print("Enter product sub category ID (if applicable): ");
        String subCategoryID = scanner.nextLine();
        System.out.print("Enter product minimum amount: ");
        int minimumAmount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (serviceController.addProduct(makat, name, supplier, costPrice, sellingPrice, categoryId, subCategoryID, minimumAmount)) {
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
        int itemAmount = 0;
        while (true) {
            System.out.print("Enter amount of items to add to stock: ");
            itemAmount = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (itemAmount > 0) {
                break; // Break the loop if the item amount is valid
            } else {
                System.out.println("Item amount must be greater than 0. Please try again.");
            }
        }

        for (int i = 0; i < itemAmount; i++) {
            String newItemId = itemId + i; // Generate a new item ID for each item
            serviceController.addItem(defective, inWareHouse, -1, -1, -1f, -1f, -1f, -1f, name, newItemId, expireDate, categoryID, productID);
        }
    }



    private static void removeProduct() {
        System.out.print("Enter product makat: ");
        String makat = scanner.nextLine();
        if (serviceController.removeProduct(makat)){
            System.out.println("Product removed successfully.");
        }
    }

    private static void removeItem() {
        System.out.print("Enter item ID: ");
        String itemID = scanner.nextLine();
        if (serviceController.removeItem(itemID)) {
            System.out.println("Item removed successfully.");
        } else {
            System.out.println("Failed to remove item.");
        }
    }

    private static void removeExpired() {
        serviceController.removeExpire();
    }

    private static void removeDefective() {
        serviceController.removeDefective();
    }

    private static void getLowSupplyReport() {
        serviceController.generateLowSupplyCSVReport();
    }

    private static void getDefectiveReport() {
        serviceController.generateDefectiveItemsCSVReport();
    }

    private static void updateProductDiscount() {
        System.out.print("Enter product makat: ");
        String makat = scanner.nextLine();
        System.out.print("Enter new discount percentage: ");
        int discount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        LocalDate startDate;
        while (true) {
            System.out.print("Enter discount start date (YYYY-MM-DD): ");
            String startDateStr = scanner.nextLine();
            try {
                startDate = LocalDate.parse(startDateStr);
                break; // Break the loop if the date is valid
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        LocalDate endDate;
        while (true) {
            System.out.print("Enter discount end date (YYYY-MM-DD): ");
            String endDateStr = scanner.nextLine();
            try {
                endDate = LocalDate.parse(endDateStr);
                break; // Break the loop if the date is valid
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        if (serviceController.updateProductDiscount(makat, discount, startDate, endDate)) {
            System.out.println("Product discount updated successfully.");
        } else {
            System.out.println("Failed to update product discount. Product might not exist.");
        }
    }

    private static void updateItemDefective() {
        System.out.print("Enter Item ID: ");
        String itemID = scanner.nextLine();
        System.out.print("Is the item defective (yes/no)? ");
        boolean defective = scanner.nextLine().equalsIgnoreCase("yes");
        if (serviceController.updateItemDefective(defective, itemID)) {
            System.out.println("Item defective status updated successfully.");
        } else {
            System.out.println("Failed to update item defective status.");
        }
    }

    private static void generateReportByCategoryID() {
        System.out.print("Enter category ID: ");
        String categoryID = scanner.nextLine();
        serviceController.generateCategoryProductReport(categoryID);
    }

    private static void generateStockReport() {
        serviceController.generateStockCSVReport();
        System.out.println("Stock report generated successfully.");
    }

    private static void generateLowSupplyDeltaCSVReport(){
        serviceController.generateLowSupplyDeltaCSVReport();
        System.out.println("Low supply report for supplier generated successfully.");
    }

    private static void updateItemLocation() {
        System.out.print("Enter item ID: ");
        String itemID = scanner.nextLine();

        if (!serviceController.doesItemExist(itemID)) {
            System.out.println("Item not found.");
            return;
        }

        if (serviceController.isItemInWarehouse(itemID)) {
            System.out.println("Item is currently in the warehouse.");
            System.out.print("Enter new building: ");
            int building = scanner.nextInt();
            System.out.print("Enter new floor: ");
            int floor = scanner.nextInt();
            System.out.print("Enter new aisle: ");
            float aisle = scanner.nextFloat();
            System.out.print("Enter new shelf: ");
            float shelf = scanner.nextFloat();
            scanner.nextLine(); // Consume newline

            if (serviceController.updateItemLocation(itemID, floor, building, aisle, shelf, false)) {
                System.out.println("Item moved to store and location updated successfully.");
            } else {
                System.out.println("Failed to update item location.");
            }
        } else {
            System.out.print("Do you want to move the item to the warehouse? (yes/no): ");
            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("yes")) {
                if (serviceController.updateItemLocation(itemID, -1, -1, Float.MIN_VALUE, Float.MIN_VALUE, true)) {
                    System.out.println("Item moved to warehouse successfully.");
                } else {
                    System.out.println("Failed to move item to warehouse.");
                }
            } else {
                System.out.print("Enter new building: ");
                int building = scanner.nextInt();
                System.out.print("Enter new floor: ");
                int floor = scanner.nextInt();
                System.out.print("Enter new aisle: ");
                float aisle = scanner.nextFloat();
                System.out.print("Enter new shelf: ");
                float shelf = scanner.nextFloat();
                scanner.nextLine(); // Consume newline

                if (serviceController.updateItemLocation(itemID, floor, building, aisle, shelf, false)) {
                    System.out.println("Item location updated successfully.");
                } else {
                    System.out.println("Failed to update item location.");
                }
            }
        }
    }

    private static void generateReorderCSVReport() {
        serviceController.generateReorderCSVReport();
        System.out.println("Reorder report generated successfully.");
    }

}
