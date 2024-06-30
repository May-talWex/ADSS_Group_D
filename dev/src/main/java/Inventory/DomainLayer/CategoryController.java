package src.main.java.Inventory.DomainLayer;

import src.main.java.Inventory.DataLayer.CategoryRepository;

import java.time.LocalDate;
import java.util.List;

public class CategoryController {
    private CategoryRepository categoryRepository;

    public CategoryController() {
        categoryRepository = new CategoryRepository();
    }

    public boolean addCategory(String id, String name) {
        Category category = new Category(name, id);
        return categoryRepository.addCategory(category);
    }

    public boolean removeCategory(String id) {
        return categoryRepository.deleteCategory(id);
    }

    public Category getCategoryByID(String id) {
        return categoryRepository.getCategoryById(id);
    }

    public boolean doesCategoryExist(String categoryID) {
        return categoryRepository.getCategoryById(categoryID) != null;
    }

    public List<Category> getCategories() {
        return categoryRepository.getAllCategories();
    }

    public boolean removeProductFromCategory(String categoryID, Product product) {
        Category category = getCategoryByID(categoryID);
        if (category != null) {
            category.removeProduct(product);
            return categoryRepository.updateCategory(categoryID, category.getDiscountStartDate(), category.getDiscountEndDate(), category.getDiscountPercentage());
        }
        return false;
    }


    public boolean updateCategory(String categoryID, LocalDate startDiscount, LocalDate endDiscount, float discountPercentage) {
        Category category = categoryRepository.getCategoryById(categoryID);
        if (category != null) {
            category.setDiscountStartDate(startDiscount);
            category.setDiscountEndDate(endDiscount);
            category.setDiscountPercentage(discountPercentage);
            return categoryRepository.updateCategory(categoryID, startDiscount, endDiscount, discountPercentage);
        }
        return false;
    }

}
