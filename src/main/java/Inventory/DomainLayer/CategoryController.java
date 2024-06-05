package Inventory.DomainLayer;

import java.util.HashMap;


public class CategoryController {
    private HashMap<String, Category> categories;

    public void addDefaultCategory() {
        String defaultCategoryID = "MP493";
        String defaultCategoryName = "Milk Products";
        addCategory(defaultCategoryName, defaultCategoryID);
    }

    public CategoryController() {
        categories = new HashMap<>();
    }

    public HashMap<String, Category> getCategories() {
        return categories;
    }

    public boolean doesCategoryExist(String categoryID) {
        return categories.containsKey(categoryID);
    }
    public Category getCategoryByID(String categoryID) {
        if (categories.containsKey(categoryID)) {
            return categories.get(categoryID);
        } else {
            System.out.println("Category ID " + categoryID + " does not exist.");
            return null;
        }
    }

    public boolean addCategory(String name, String ID) {
        if (categories.containsKey(ID)) {
            System.out.println("Category ID " + ID + " already exists.");
            return false;
        }
        Category category = new Category(name, ID);
        categories.put(ID, category);
        System.out.println("Category " + ID + " added successfully.");
        return true;
    }

    public boolean removeCategory(String categoryID) {
        if (categories.containsKey(categoryID)) {
            if (!categories.get(categoryID).getProducts().isEmpty()) {
                System.out.println("Cannot remove category " + categoryID + " because it still contains products.");
                return false;
            }
            categories.remove(categoryID);
            System.out.println("Category " + categoryID + " removed successfully.");
            return true;
        }
        System.out.println("Category ID " + categoryID + " does not exist.");
        return false;
    }









    public boolean addProductToCategory(String categoryID, Product product) {
        if (categories.containsKey(categoryID)) {
            Category category = categories.get(categoryID);
            if (category.getProducts().contains(product)) {
                System.out.println("Product already exists in category " + categoryID + ".");
                return false;
            }
            category.addProduct(product);
            System.out.println("Product added to category " + categoryID + " successfully.");
            return true;
        }
        System.out.printf("Category ID " + categoryID + " does not exist.");
        return false;
    }

    public boolean removeProductFromCategory(String categoryID, Product product) {
        if (categories.containsKey(categoryID)) {
            Category category = categories.get(categoryID);
            if (category.getProducts().contains(product)) {
                category.removeProduct(product);
                System.out.println("Product removed from category " + categoryID + " successfully.");
                return true;
            } else {
                System.out.println("Product does not exist in category " + categoryID + ".");
                return false;
            }
        } else {
            System.out.println("Category ID " + categoryID + " does not exist.");
            return false;
        }
    }

    public  void clearCategories() {
        categories.clear();
    }



    public void printCategoriesDetails() {
        for (Category category : categories.values()) {
            System.out.println(category);
        }
    }

}