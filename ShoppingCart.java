import java.io.*;
import java.util.*;
public class ShoppingCart implements Serializable {
    private List items = new LinkedList();
    //constructor
    public ShoppingCart() {
    }
    
    //getters and setters



    
    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }
    
    //functions
    @SuppressWarnings("unchecked")
    public boolean AddItem(Item newItem){
        try {
            items.add(newItem);
            return true;
        } catch(Exception e){
            System.out.println(e);
            return false;
        }
    }
    
    public boolean RemoveProduct(int index){
        double itemsPrice = ((Item) items.get(index)).getTotalPrice();
        try{
            items.remove(index);
            return true;
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
    }
    
    public boolean ClearCart(){
        try{
            items.clear();       
            return true;
        } catch(Exception e){
            System.out.println(e);
            return false;
        }
    }
    
   
    public double CalculatePrice(){
        double cost = 0;
        try{
            Iterator iterator = items.iterator();
            while(iterator.hasNext()){
                cost += ((Item)iterator.next()).getTotalPrice();
            }
            return cost;   
        } catch(Exception e){
            System.out.println(e);
            return 0;
        }
    }
    
    public boolean EditCart(String productId, int quantity){
        try{
            Iterator iterator = items.iterator();
            while(iterator.hasNext()){
                Item item = (Item) iterator.next();
                if(item.getProduct().getId() == productId){
                    if(quantity == 0){ //remove item
                        items.remove(item);
                    }
                    else{
                        item.setQuantity(quantity);
                    }
                    return true;
                }
            }
            return false;
        } catch(Exception e){
            System.out.println(e);
            return false;
        }
    }
    
    public double Submit(){
        
        if(items.size() < 0){
            System.out.println("Please add items to the Cart to submit an order");
            return 0;
        }
        else{        
            displayInvoice();
            double cost = CalculatePrice();
            this.ClearCart();
            return cost;
        }
    }

    @SuppressWarnings("unchecked")
    public Iterator getNonAvailableItems(){
        List nonAvailableItems = new LinkedList();
        Iterator iterator = items.iterator();
        while(iterator.hasNext()){
            Item item = (Item) iterator.next();
            if(!item.inStock()){
                nonAvailableItems.add(item);
            }
        }
        return nonAvailableItems.iterator();
    }    

    @SuppressWarnings("unchecked")
    public Iterator getAvailableItems(){
        List availableItems = new LinkedList();
        Iterator iterator = items.iterator();
        while(iterator.hasNext()){
            Item item = (Item) iterator.next();
            if(item.inStock()){
                availableItems.add(item);
            }
        }
        return availableItems.iterator();
    }
    
    public void displayInvoice(){
        Iterator availableItems = getAvailableItems();
        Iterator nonAvailableItems = getNonAvailableItems();
        System.out.println("Invoice: ");
        while(availableItems.hasNext()){
            Item item = (Item) availableItems.next();
            System.out.println(item.toString());
        }
        System.out.println("Waitlisted Items: ");
        while(nonAvailableItems.hasNext()){
            Item item = (Item) nonAvailableItems.next();
            System.out.println(item.toString());
        }
        System.out.println("Total Price: $" + CalculatePrice());
    }

    public String toString() {
        Iterator iterator = items.iterator();
        String string = "The Shopping cart has a cost of $" + CalculatePrice() + " and contians the following items: \n";
        if(!iterator.hasNext()){
            System.out.println("None");
        }
        while(iterator.hasNext()){
            string += ((Item) iterator.next()).toString();
            string += "\n";
        }
        return string;
    }
    
}

