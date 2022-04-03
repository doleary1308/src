import java.io.Serializable;

class Item implements Serializable {

    private Product product;
    private Client client;
    private int quantity;
    
    public Item(Product product, Client client, int quantity) {
        this.product = product;
        this.client = client;
        this.quantity = quantity;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    public Product getProduct(){
        return product;
    }
    
    public void setProduct(Product product){
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getTotalPrice(){
        return product.getPrice() * quantity;
    }
    
    public boolean inStock(){
        if(quantity <= product.getQuantity()){
            return true;
        }
        else{
            return false;
        }
    }

    public String toString() {
        String string = "Quantity: " + quantity + "\nProdcut: " + product.toString() + "\n";
        return string;
    }
}
