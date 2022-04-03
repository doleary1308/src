import java.io.Serializable;
import java.util.*;

public class Product implements Serializable {
    
    private String id;
    private String name;
    private double price;
    private String description;
    private int quantity;
    private List waitlist = new LinkedList();
    private List supplies = new LinkedList();
    
    public Product(String id, String name, double price, String description, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public Iterator getSupplies(){
        return supplies.iterator();
    }
    
    public Iterator getWaitListedItems(){
        return waitlist.iterator();
    }
    
    //function
    @SuppressWarnings("unchecked")
    public boolean addToWaitlist(Item item){
        try{
            waitlist.add(item);
        }catch(Exception e){
            System.out.println("Couldn't add item");
            return false;
        }
        return true;
    }
    @SuppressWarnings("unchecked")
    public boolean addSupply(Supply supply){
        try{
            supplies.add(supply);
        }catch(Exception e){
            System.out.println("Couldn't add item");
            return false;
        }
        return true;
    }

    public String toString() {
        String string = "Id: " + id + "\nName: " + name + "\nPrice: " + price + "\nDescription: " + description + "\n";
        return string;
    }
    
}
