import java.io.Serializable;

public class Supply implements Serializable {
    
    private Product product;
    private Supplier supplier;
    private double price;
    private int quantity;

    public Supply(Product product, Supplier supplier, double price, int quantity) {
        this.product = product;
        this.supplier = supplier;
        this.price = price;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    

    
    
    public String toString() {
        String string = "Prodcut: " + product.toString() + "Supplier Price: $" + price + "\n";
        return string;
    }
    
}
