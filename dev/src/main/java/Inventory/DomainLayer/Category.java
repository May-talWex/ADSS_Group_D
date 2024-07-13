package src.main.java.Inventory.DomainLayer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

//May-tal
// This class will represent a category, it will hold its ID, name, and description and
public class Category {
    private String name;
    private String ID;
    private LocalDate discountStartDate;
    private LocalDate discountEndDate;
    private float discountPercentage;
    private ArrayList<Product> products;

    //Category constructors
    public Category(String name, String ID) {
        this.name = name;
        this.ID = ID;
        this.products = new ArrayList<>();
    }

    public Category(String name, String ID, LocalDate discountStartDate, LocalDate discountEndDate, float discountPercentage) {
        this.name = name;
        this.ID = ID;
        this.discountStartDate = discountStartDate;
        this.discountEndDate = discountEndDate;
        this.discountPercentage = discountPercentage;
        this.products = new ArrayList<>();
    }

    public Category(String name, String ID, LocalDate discountStartDate, LocalDate discountEndDate, float discountPercentage, ArrayList<Product> products) {
        this.name = name;
        this.ID = ID;
        this.discountStartDate = discountStartDate;
        this.discountEndDate = discountEndDate;
        this.discountPercentage = discountPercentage;
        this.products = products;
    }

    //Getters
    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public LocalDate getDiscountStartDate() {
        return discountStartDate;
    }

    public LocalDate getDiscountEndDate() {
        return discountEndDate;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    //Setters
    public void setDiscountStartDate(LocalDate discountStartDate) {
        this.discountStartDate = discountStartDate;
    }

    public void setDiscountEndDate(LocalDate discountEndDate) {
        this.discountEndDate = discountEndDate;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", ID='" + ID + '\'' +
                ", discountStartDate=" + discountStartDate +
                ", discountEndDate=" + discountEndDate +
                ", discountPercentage=" + discountPercentage +
                ", products=" + products +
                '}';
    }
}
