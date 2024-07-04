# Inventory Management System

This Inventory Management System is designed to help manage categories, products, and items in a store or warehouse. The system supports adding, removing, updating, and generating reports for various inventory elements.

## Features

- Add, remove, and update categories, products, and items.
- Generate various reports such as stock reports, low supply reports, defective items reports, and reorder reports.
- Update item locations and defective status.
- Maintain minimum stock levels and alert when items need to be reordered.

## Prerequisites

- Java Development Kit (JDK) 8 or later
- SQLite JDBC library

## Libraries Used

- SQLite JDBC: `org.xerial:sqlite-jdbc:3.32.3.2`
- JUnit 5: `org.junit.jupiter:junit-jupiter:5.7.0`

## Main Menu

1. Add/remove products/items/categories
2. Update items/categories/products
3. Generate reports
4. Generate low stock report
5. Generate low stock report for supplier
6. Generate regular stock report for supplier
7. Exit

## Add/Remove Menu

1. Add Category
2. Add Product
3. Add Item
4. Remove Category
5. Remove Product
6. Remove Item
7. Remove all expired Items
8. Remove all defective Items
9. Exit

## Update Menu

1. Update Product Discount
2. Update Item Defective Status
3. Update Item Location
4. Update Category Discount
5. Exit

## Report Menu

1. Get Stock report
2. Get expire report
3. Get defective report
4. Generate report by category ID
5. Exit

## Generating Reports

- `generateLowSupplyCSVReport()`: Generates a CSV report for low supply items.
- `generateLowSupplyDeltaCSVReport()`: Generates a CSV report for low supply items for suppliers.
- `generateReorderCSVReport()`: Generates a reorder report for products that need to be reordered.

## Example Commands

### Adding a Category
Enter category name: Electronics.  
Enter category ID: C001  

### Adding a Product
Enter product makat: P001  
Enter product name: Laptop  
Enter supplier name: Supplier1  
Enter product cost price: 500  
Enter product selling price: 1000  
Enter product category ID: C001  
Enter product sub category ID (if applicable): SC001  
Enter product minimum amount: 10  

### Adding an Item

'Enter item name: Laptop  
Enter item ID (unique identifier): I001  
Is the item defective (yes/no)? no  
Is the item in the warehouse (yes/no)? yes  
Enter product expiry date (YYYY-MM-DD): 2023-12-31  
Enter category ID: C001  
Enter product ID: P001  


### Removing a Category

Enter category ID: C001  


### Updating Product Discount

Enter product makat: P001  
Enter new discount percentage: 20  
Enter discount start date (YYYY-MM-DD): 2024-01-01  
Enter discount end date (YYYY-MM-DD): 2024-10-10  


