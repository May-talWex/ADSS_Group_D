package src.main.java.Inventory.DomainLayer;

//Timor
import java.time.LocalDate;
import java.util.ArrayList;

public class Product {
    private String makat;
    private String name;
    private String supplier;
    private float costPrice;
    private float sellingPrice;
    private boolean isDamaged;
    private float discount;
    private int minimumAmount;
    private LocalDate startDiscount;
    private LocalDate endDiscount;
    private ArrayList<String> discounts;
    private String categoryID;
    private String subCategoryID;
    private ArrayList<Item> items;
    private float fullPrice;

    public Product(String makat, String name, String supplier, float costPrice, float sellingPrice, String categoryID, String subCategoryID, int minimumAmount) {
        this.makat = makat;
        this.name = name;
        this.supplier = supplier;
        this.sellingPrice = sellingPrice;
        this.fullPrice = sellingPrice;
        this.categoryID = categoryID;
        this.subCategoryID = subCategoryID;
        this.isDamaged = false;
        this.discount = 0;
        this.minimumAmount = minimumAmount;
        this.discounts = new ArrayList<>();
        this.costPrice = costPrice;
        this.items = new ArrayList<>();
    }

    // Getters and setters
    public String getMakat() { return makat; }
    public void setMakat(String makat) { this.makat = makat; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }

    public float getCostPrice() { return costPrice; }

    public int getItemAmount() {
        return this.items != null ? this.items.size() : 0;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public float getSellingPrice() {
        if(isDiscountActive()){
            return (float) (this.fullPrice - (this.fullPrice * (discount / 100.0)));
        }
        else{
            this.discount = 0;
            return (float) this.fullPrice;
        }
    }

    public String getCategoryID() { return categoryID; }
    public void setCategory(String categoryID) { this.categoryID = categoryID; }

    public String getSubCategoryID() { return subCategoryID; }

    public int getMinimumAmount() { return minimumAmount; }



    public boolean isDiscountActive() {
        if (startDiscount == null || endDiscount == null) {
            return false;
        }
        LocalDate now = LocalDate.now();
        return now.isAfter(startDiscount) && now.isBefore(endDiscount);
    }

    public float getDiscount() { return discount; }
    public void setDiscount(int discount, LocalDate startDate, LocalDate endDate) {
        this.fullPrice = this.sellingPrice;
        this.discount = discount;
        this.startDiscount = startDate;
        this.endDiscount = endDate;
        if(isDiscountActive()){
            this.sellingPrice = (float) (this.fullPrice - (this.fullPrice * (discount / 100.0)));
        }
        else{
            this.discount = 0;
            this.sellingPrice = (float) this.fullPrice;
        }
    }

    public float getFullPrice() { return fullPrice; }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public LocalDate getStartDiscount() {
        return this.startDiscount;
    }

    public LocalDate getEndDiscount() {
        return this.endDiscount;
    }


    public String toString() {
        return "Product{" +
                "makat='" + makat + '\'' +
                ", name='" + name + '\'' +
                ", supplier='" + supplier + '\'' +
                ", costPrice=" + costPrice +
                ", sellingPrice=" + sellingPrice +
                ", isDamaged=" + isDamaged +
                ", discount=" + discount +
                ", minimumAmount=" + minimumAmount +
                ", startDiscount=" + startDiscount +
                ", endDiscount=" + endDiscount +
                ", discounts=" + discounts +
                ", categoryID='" + categoryID + '\'' +
                ", subCategoryID='" + subCategoryID + '\'' +
                ", itemAmount=" + items.size() +
                '}';
    }
}