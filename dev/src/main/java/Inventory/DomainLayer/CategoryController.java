package src.main.java.Inventory.DomainLayer;

import src.main.java.Inventory.DataLayer.CategoryRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class CategoryController {
    private CategoryRepository categoryRepository;

    public CategoryController() {
        this.categoryRepository = new CategoryRepository();
    }

    public boolean addCategory(String id, String name) {
        Category category = new Category(name, id);
        return categoryRepository.addCategory(category);
    }

    public boolean removeCategory(String id) {
        return categoryRepository.deleteCategory(id);
    }

    public boolean updateCategory(String id, LocalDate startDiscount, LocalDate endDiscount, float discountPercentage) {
        return categoryRepository.updateCategory(id, startDiscount, endDiscount, discountPercentage);
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
        Category category = categoryRepository.getCategoryById(categoryID);
        if (category != null) {
            category.removeProduct(product);
            return categoryRepository.updateCategory(categoryID, category.getDiscountStartDate(), category.getDiscountEndDate(), category.getDiscountPercentage());
        }
        return false;
    }

}
