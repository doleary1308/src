import java.util.*;
import java.io.*;

public class Warehouse implements Serializable {
    
    private ClientList clientList;
    private ProductList productList;
    private SupplierList supplierList;
    
    private static Warehouse warehouse;
    
    //constructor
    private Warehouse(){
        clientList = ClientList.instance();
        productList = ProductList.instance();
        supplierList = SupplierList.instance();
    }
    
    //singleton pattern
    public static Warehouse instance(){
        if(warehouse == null){
            ClientIdServer.instance();
            return (warehouse = new Warehouse());
        } else{
            return warehouse;
        }
    }
    
    //functions
    public Client addClient(String name, String address, String phone){
        Client client = new Client(name, address, phone);
        if (clientList.insertClient(client)){
            return client;
        } else{
            return null;
        }
    }
    
    public Product addProduct(String id, String name, double price, String description, int quantity){
        if(productList.getProduct(id) == null){
            Product product = new Product(id, name, price, description, quantity);
            if (productList.insertProduct(product)){
                return product;
            } else{
                return null;
            }
        }
        else{
            System.out.println("Product with same id already exists!");
            return null;
        }
    }
    
    public Supplier addSupplier(String id, String name, String address, String phone){
        if(supplierList.getSupplier(id) == null){
            Supplier supplier = new Supplier(id, name, address, phone);
            if (supplierList.insertSupplier(supplier)){
                return supplier;
            } else{
                return null;
            }    
        }
        else{
            System.out.println("Supplier with same id already exists!");
            return null;
        }
    }
    
    public Supply addSupply(String productId, String supplierId, double price, int quantity){
        Product product = productList.getProduct(productId);
        Supplier supplier = supplierList.getSupplier(supplierId);
        
        if(product == null || supplier == null){
            System.out.println("invalid IDs");
            return null;
        }
        
        Supply supply = new Supply(product, supplier, price, quantity);
        product.addSupply(supply);
        return supply;
    }
    
    public Item addItem(String productId, String clientId, int quantity){
        Product product = productList.getProduct(productId);
        Client client = clientList.getClient(clientId);
        
        Item item = new Item(product, client, quantity);
        client.getShoppingCart().AddItem(item);
        return item;
    }
    
    public boolean printProductSupplies(String productId){
        Product product = productList.getProduct(productId);
        Iterator iterator = product.getSupplies();
        System.out.println("Currently in stock: " + product.getQuantity());
        if(iterator.hasNext()){
            int i = 0;
            while(iterator.hasNext()){
                Supply supply = (Supply) iterator.next();
                System.out.println(i + ": " + supply.toString());
                i++;
            }
            return true;
        }
        else{
            System.out.println("Currently there are no suppliers who are offering supplies for the selected product"
                    + "\nTry adding a supply first! (#4)");
            return false;
        }
    }
    
    public void showSuppliers(String productId){
        Product product = searchProduct(productId);
        Iterator iterator = product.getSupplies();
        while(iterator.hasNext()){
            Supply supply = (Supply) iterator.next();
            System.out.println(supply.toString());
        }
    }
    
    public void showProducts(String supplierId){
        Supplier supplier = searchSupplier(supplierId);
        Iterator iterator = supplier.getSupplies();
        while(iterator.hasNext()){
            Supply supply = (Supply) iterator.next();
            System.out.println(supply.toString());
        }
    }
    
    public void updateProducts(String supplierId, String productId, double price){
        Supplier supplier = searchSupplier(supplierId);
        Iterator iterator = supplier.getSupplies();
        while(iterator.hasNext()){
            Supply supply = (Supply) iterator.next();
            if(supply.getProduct().getId() == productId){
                supply.setPrice(price);
            }
        }
    }
    
    public Iterator receiveSupplies(String productId, int supplyNumber){
        Product product = productList.getProduct(productId);
        Iterator iterator = product.getSupplies();
        if(iterator.hasNext()){
            int i = 0;
            Supply supply = null;
            while(iterator.hasNext()){
                if(i == supplyNumber){
                    supply = (Supply) iterator.next();
                    break;
                }
                i++;
            }
            if(supply == null){
                System.out.println("The selected number is not associated with any supply\n");
                return null;
            }
            //This is what gets executed if we don't run into different issues
            product.setQuantity(product.getQuantity() + supply.getQuantity());
            System.out.println("Supply added to inventory\nProduct " + product.getName() + " now has " + product.getQuantity() + " in stock");
            Iterator waitlistedItems = product.getWaitListedItems();
            if(waitlistedItems.hasNext()){
                return waitlistedItems;
            }
            else{
                System.out.println("There are currently no waitlisted items associated to this product\n");
            }
            return null;
        }
        else{
            System.out.println("Currently there are no supplies for the selected product"
                    + "\nTry adding a supply first! (#4)\n");
            return null;
        }
    }
    
    public boolean fulfillItem(Item item){
        Product product = item.getProduct();
        int itemQuantity = item.getQuantity();
        int productQuantity = product.getQuantity();
        if(itemQuantity <= productQuantity){
            //About client
            double cost = item.getTotalPrice();
            Client client = item.getClient();
            client.setDebit(client.getDebit() + cost);
            System.out.println("Client invoice:");
            System.out.println("An item was available and got shipped to you: ");
            System.out.println(item.toString());
            System.out.println("Client debit: " + client.getDebit());
            //About backend
            product.setQuantity(productQuantity - itemQuantity);
            System.out.println(product.getQuantity() + " of " + product.getName() + "left in stock");
            return true;
        }
        else{
            System.out.println("There are not enough products in stock to fulfill the waitlisted item");
            return false;
        }
    }
    
    public void printWaitlistedItems(String productId){
        Product product = productList.getProduct(productId);
        Iterator iterator = product.getWaitListedItems();
        while(iterator.hasNext()){
            Item item = (Item) iterator.next();
            System.out.println(item.toString());
        }
    }
    
    public void getClientWaitlist(String clientId){
        Iterator products = productList.getProducts();
        while(products.hasNext()){
            Product product = (Product) products.next();
            Iterator iterator = product.getWaitListedItems();
            while(iterator.hasNext()){
                Item item = (Item) iterator.next();
                if(item.getClient().getId() == clientId)
                    System.out.println(item.toString());
            }
        }
    }
    
    public void printTransactions(String clientId){
        Client client = clientList.getClient(clientId);
        Iterator iterator = client.getTransactions();
        while(iterator.hasNext()){
            Transaction transaction = (Transaction) iterator.next();
            System.out.println(transaction.toString());
        }
    }
    
    public boolean editCart(String clientId, String productId, int quantity){
        Client client = clientList.getClient(clientId);
        return client.getShoppingCart().EditCart(productId, quantity);
    }
    
    public void printCart(String clientId){
        Client client = clientList.getClient(clientId);
        System.out.println(client.getShoppingCart().toString());
    }
    
    public boolean clearCart(String clientId){
        Client client = clientList.getClient(clientId);
        return client.getShoppingCart().ClearCart();
    }
    
    
    public void submit(String clientId){
        Client client = clientList.getClient(clientId);
        ShoppingCart shoppingCart = client.getShoppingCart();
        Iterator availableItems = shoppingCart.getAvailableItems();
        Iterator nonAvailableItems = shoppingCart.getNonAvailableItems();
        double price = shoppingCart.Submit();
        client.setDebit(client.getDebit() + price);
        //reduce items in stock
        while(availableItems.hasNext()){
            Item item = (Item) availableItems.next();
            item.getProduct().setQuantity(item.getProduct().getQuantity() - item.getQuantity());
        }
        //add items to waitlist
        while(nonAvailableItems.hasNext()){
            Item item = (Item) nonAvailableItems.next();
            item.getProduct().addToWaitlist(item);
        }
    }
    
    public void pay(String clientId, double amount){
        Client client = clientList.getClient(clientId);
        client.Pay(amount);
    }
    
    public Iterator getClients(){
        return clientList.getClients();
    }
    
    public Client searchClient(String clientId) {
        return clientList.getClient(clientId);
    }
    
    public Iterator getProducts(){
        return productList.getProducts();
    }
    
    public Product searchProduct(String productId){
        return productList.getProduct(productId);
    }
    
    public Iterator getSuppliers(){
        return supplierList.getSuppliers();
    }
    
    public Supplier searchSupplier(String supplierId){
        return supplierList.getSupplier(supplierId);
    }
    
    public static Warehouse retrieve() {
    try {
      FileInputStream file = new FileInputStream("WarehouseData");
      ObjectInputStream input = new ObjectInputStream(file);
      input.readObject();
      ClientIdServer.retrieve(input);
      return warehouse;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return null;
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
      return null;
    }
  }
    
  public static  boolean save() {
    try {
      FileOutputStream file = new FileOutputStream("WarehouseData");
      ObjectOutputStream output = new ObjectOutputStream(file);
      output.writeObject(warehouse);
      output.writeObject(ClientIdServer.instance());
      return true;
    } catch(IOException ioe) {
      ioe.printStackTrace();
      return false;
    }
  }
  
  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(warehouse);
    } catch(IOException ioe) {
      System.out.println(ioe);
    }
  }
  
  private void readObject(java.io.ObjectInputStream input) {
    try {
		
      input.defaultReadObject();
      if (warehouse == null) {
        warehouse = (Warehouse) input.readObject();
      } else {
        input.readObject();
      }
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  public String toString() {
    return clientList + "\n" + productList + "\n" + supplierList;
  }
    
}
