import java.util.*;
import java.io.*;

public class UserInterface {
    private static UserInterface userInterface;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;
    
    private static final int EXIT = 0;
    private static final int ADD_CLIENT = 1;
    private static final int ADD_PRODUCT = 2;
    private static final int ADD_SUPPLIER = 3;
    private static final int ADD_SUPPLY = 4;
    private static final int SHOW_CLIENTS = 5;
    private static final int SHOW_PRODUCTS = 6;
    private static final int SHOW_PRODUCT_SUPPLIES= 7;
    private static final int SHOW_WAITLISTED_ITEMS= 8;
    private static final int SHOW_SUPPLIERS = 9;
    private static final int ADD_ITEM_TO_CART = 10;
    private static final int VIEW_CART = 11;
    private static final int EDIT_ITEM_FROM_CART = 12;
    private static final int CLEAR_CART = 13;
    private static final int PLACE_ORDER = 14;
    private static final int PAY = 15;
    private static final int RECEIVE_SUPPLIES = 16;
    private static final int SHOW_TRANSACTIONS = 17;
    private static final int SAVE = 18;
    private static final int RETRIEVE = 19;
    private static final int HELP = 20;
    
    private UserInterface() {
        /*if (yesOrNo("Look for saved data and  use it?")) {
          retrieve();
        } else {
          warehouse = Warehouse.instance();
        }*/
    	warehouse = Warehouse.instance();
    }
    
    public static UserInterface instance() {
        if (userInterface == null) {
            return userInterface = new UserInterface();
        } else {
            return userInterface;
        }
    }
    
    public String getToken(String prompt) {
    do {
        try {
          System.out.println(prompt);
          String line = reader.readLine();
          StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
          if (tokenizer.hasMoreTokens()) {
            return tokenizer.nextToken();
          }
        } catch (IOException ioe) {
          System.exit(0);
        }
      } while (true);
    }
    
    private boolean yesOrNo(String prompt) {
        String more = getToken(prompt + " (Y|y)[es] or anything else for no");
        if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
            return false;
        }
        return true;
    }
    
    public int getNumber(String prompt) {
      do {
        try {
          String item = getToken(prompt);
          Integer num = Integer.valueOf(item);
          return num.intValue();
        } catch (NumberFormatException nfe) {
          System.out.println("Please input a number ");
        }
      } while (true);
    }
    
    public int getCommand() {
        do {
          try {
            int value = Integer.parseInt(getToken("Enter command (" + HELP + " for help):"));
            if (value >= EXIT && value <= HELP) {
              System.out.println("");
	      return value;
            }
          } catch (NumberFormatException nfe) {
            System.out.println("Enter a number");
          }
        } while (true);
    }
    
    public void help() {
        System.out.println("Enter a number between 0 and 18 as explained below:");
        System.out.println(EXIT + " to Exit\n");
        System.out.println(ADD_CLIENT + " to add a client");
        System.out.println(ADD_PRODUCT + " to add a product");
        System.out.println(ADD_SUPPLIER + " to add a supplier");
        System.out.println(ADD_SUPPLY + " to add a supply");
        System.out.println(SHOW_CLIENTS + " to print the clients");
        System.out.println(SHOW_PRODUCTS + " to print the products");
        System.out.println(SHOW_PRODUCT_SUPPLIES + " to print a product' supplies");
        System.out.println(SHOW_WAITLISTED_ITEMS + " to print a product' waitlist");
        System.out.println(SHOW_SUPPLIERS + " to print the suppliers");
        System.out.println(ADD_ITEM_TO_CART + " to add an item to the cart");
        System.out.println(VIEW_CART + " to view all items in the cart");
        System.out.println(EDIT_ITEM_FROM_CART + " to edit an item in the cart");
        System.out.println(CLEAR_CART + " to remove all items in the cart");
        System.out.println(PLACE_ORDER + " to place an order (process shopping cart)");
        System.out.println(PAY + " to pay towards a Client's debit");
        System.out.println(RECEIVE_SUPPLIES + " to buy supplies from suppliers");
        System.out.println(SAVE + " to save data");
        System.out.println(RETRIEVE + " to retrieve");
        System.out.println(HELP + " for help");
    }
    
    public void addClient(){
        String name = getToken("Enter client Name: ");
        String address = getToken("Enter client Address: ");
        String phone = getToken("Enter client Phone: ");
        System.out.println("");

        Client result = warehouse.addClient(name, address, phone);
        if (result == null){
            System.out.println("Could not add client");
        } else{
            System.out.println(result);
        }
    }
    
    public void addProduct(){
        String id = getToken("Enter product Id:");
        String name = getToken("Enter product Name: ");
        double price = getTokenDouble("Enter product Price: ");
        String description = getToken("Enter product Description: ");
        int quantity = getTokenInt("Enter product Quantity: ");
        System.out.println("");

        Product result = warehouse.addProduct(id, name, price, description, quantity);
        if (result == null){
            System.out.println("Could not add product");
        } else{
            System.out.println(result);
        }
    }
    
    public void addSupplier(){
        String id = getToken("Enter supplier Id: ");
        String name = getToken("Enter supplier Name: ");
        String address = getToken("Enter supplier Address: ");
        String phone = getToken("Enter supplier Phone: ");
        System.out.println("");

        Supplier result = warehouse.addSupplier(id, name, address, phone);
        if (result == null){
            System.out.println("Could not add supplier");
        } else{
            System.out.println(result);
        }
    }
    
    public void addSupply(){
        try{
            String productId =  getToken("Enter product Id: ");
            String supplierId = getToken("Enter supplier Id: ");
            double price = getTokenDouble("Enter Price: ");
            int quantity = getTokenInt("Enter Quantity: ");
            System.out.println("");

            Supply result = warehouse.addSupply(productId, supplierId, price, quantity);
            if(result != null){
                System.out.println(result);
            }
        }
        catch(Exception e){
            System.out.println("Could not add supply");
        }
    }
    
    public void showClients(){
        Iterator clients = warehouse.getClients();
        while(clients.hasNext()){
            Client client = (Client)(clients.next());
            System.out.println(client.toString());
        }
    }
    
    public void showProducts(){
        Iterator products = warehouse.getProducts();
        while(products.hasNext()){
            Product product = (Product)(products.next());
            System.out.println(product.toString());
        }
    }
    
    public void showProductSupplies(){
        try{
            String productId =  getToken("Enter product Id: ");
            System.out.println("");
            warehouse.printProductSupplies(productId);
        }
        catch(Exception e){
            System.out.println("Could not show supplies");
        }
    }
    
    public void showWaitlistedItems(){
        try{
            String productId =  getToken("Enter product Id: ");
            System.out.println("");
            warehouse.printWaitlistedItems(productId);
        }
        catch(Exception e){
            System.out.println("Could not show items");
        }
    }
    
    public void showSuppliers(){
        Iterator suppliers = warehouse.getSuppliers();
        while(suppliers.hasNext()){
            Supplier supplier = (Supplier)(suppliers.next());
            System.out.println(supplier.toString());
        }
    }
    
    public void addItemCart(){
        try{
            String productId =  getToken("Enter product Id: ");
            String clientId = getToken("Enter client Id: ");
            int quantity = getTokenInt("Enter Quantity: ");
            System.out.println("");

            Item result = warehouse.addItem(productId, clientId, quantity);
            System.out.println(result);
        }
        catch(Exception e){
            System.out.println("Could not add item");
        }
    }
    
    public void viewCart(){
        try{
            String clientId = getToken("Enter client Id: ");
            System.out.println("");
            warehouse.printCart(clientId);
        }
        catch(Exception e){
            System.out.println("Could not see cart");
        }
    }
    
    public void editItemCart(){
        try{
            String productId =  getToken("Enter product Id: ");
            String clientId = getToken("Enter client Id: ");
            int quantity = getTokenInt("Enter Quantity: ");
            System.out.println("");

           if(warehouse.editCart(productId, clientId, quantity)){
               System.out.println("success");
           }
           else{
               System.out.println("Could not modify item");
           }
        }
        catch(Exception e){
            System.out.println("Could not modify item");
        }
    }
    
    public void clearCart(){
        try{
            String clientId = getToken("Enter client Id: ");
            System.out.println("");
            if(warehouse.clearCart(clientId)){
               System.out.println("success");
           }
           else{
               System.out.println("Could not clear cart");
           }
        }
        catch(Exception e){
            System.out.println("Could not clear cart");
        }
    }
    
    public void placeOrder(){
        try{
            String clientId = getToken("Enter client Id: ");
            System.out.println("");
            warehouse.submit(clientId);
            System.out.println("success");
        }
        catch(Exception e){
            System.out.println("Could not place order");
        }
    }
    
    public void pay(){
        try{
            String clientId = getToken("Enter client Id: ");
            double amount = getTokenDouble("Enter amount: ");
            warehouse.pay(clientId, amount);
            System.out.println("success");
        }
        catch(Exception e){
            System.out.println("Could not complete payment");
        }
    }
    
        public void receiveSupplies(){
        String productId = getToken("Enter product Id: ");
        System.out.println("");
        if(warehouse.printProductSupplies(productId)){
            int supplyNumber = getTokenInt("Enter number related to the supply you want to receive: ");
            System.out.println("");
            Iterator waitlistedItems = warehouse.receiveSupplies(productId, supplyNumber);
            if(waitlistedItems != null){
                while(waitlistedItems.hasNext()){
                    Item item = (Item) waitlistedItems.next();
                    System.out.println(item.toString());
                    boolean invalid = true;
                    while(invalid){
                        if (yesOrNo("Would you like to fulfill this waitlisted item? Y/N")){
                            warehouse.fulfillItem(item);
                            invalid = false;
                        }
                        else{
                            //do nothing
                            invalid = false;
                        }
                    }
                }
            }
        }
    }
        
    public void showTransactions()
    {
    String id = getToken("Enter client id");
    warehouse.searchClient(id);
    }
    
    //NEW FUNCTIONS FOR WarehouseUI
    public void showTransactions(String id)
    {
        warehouse.searchClient(id);
    }
    
    public void editItemCart(String id){
        String clientId = id;
        viewCart(id);
        try{
            String productId =  getToken("Enter product Id: ");
            int quantity = getTokenInt("Enter Quantity: ");
            System.out.println("");

           if(warehouse.editCart(productId, clientId, quantity)){
               System.out.println("success");
           }
           else{
               System.out.println("Could not modify item");
           }
        }
        catch(Exception e){
            System.out.println("Could not modify item");
        }
    }

    public void viewCart(String id){
        String clientId = id;
        try{
            System.out.println("");
            warehouse.printCart(clientId);
        }
        catch(Exception e){
            System.out.println("Could not see cart");
        }
    }
    
    private void save() {
        if (Warehouse.save()) {
            System.out.println(" The warehouse has been successfully saved in the file WarehouseData \n" );
        } else {
            System.out.println(" There has been an error in saving \n" );
        }
    }
    
    private void retrieve() {
        try {
            Warehouse tempWarehouse = Warehouse.retrieve();
            if (tempWarehouse != null) {
                System.out.println(" The warehouse has been successfully retrieved from the file WarehouseData \n" );
                warehouse = tempWarehouse;
            } else {
                System.out.println("File doesnt exist; creating new warehouse" );
                warehouse = Warehouse.instance();
            }
        } catch(Exception cnfe) {
            cnfe.printStackTrace();
        }
    }
    
    public void process(){
        int command;
        help();
        while((command = getCommand()) != EXIT){
            switch(command){
                case ADD_CLIENT:            addClient();
                                            break;
                case ADD_PRODUCT:           addProduct();
                                            break;
                case ADD_SUPPLIER:          addSupplier();
                                            break;
                case ADD_SUPPLY:            addSupply();
                                            break;
                case SHOW_CLIENTS:          showClients();
                                            break;
                case SHOW_PRODUCTS:         showProducts();
                                            break;
                case SHOW_PRODUCT_SUPPLIES: showProductSupplies();
                                            break;
                case SHOW_WAITLISTED_ITEMS: showWaitlistedItems();
                                            break;
                case SHOW_SUPPLIERS:        showSuppliers();
                                            break;
                case ADD_ITEM_TO_CART:      addItemCart();
                                            break;
                case VIEW_CART:             viewCart();
                                            break;
                case EDIT_ITEM_FROM_CART:   editItemCart();
                                            break;
                case CLEAR_CART:            clearCart();
                                            break;
                case PLACE_ORDER:           placeOrder();
                                            break;
                case PAY:                   pay();
                                            break;
                case RECEIVE_SUPPLIES:      receiveSupplies();
                                            break;
                case SHOW_TRANSACTIONS:     showTransactions();
                                            break;
                case SAVE:                  save();
                                            break;
                case RETRIEVE:              retrieve();
                                            break;
                case HELP:                  help();
                                            break;
                
            }
        }
    }
    
    public double getTokenDouble(String prompt){
        Double doubleInput;
        while (true) {
            String userInput = getToken(prompt);
            try {
                doubleInput = Double.parseDouble(userInput);
                break; // will only get to here if input was a double
            } catch (NumberFormatException ignore) {
                System.out.println("Invalid input. Enter a number (double)");
            }
        }
        return doubleInput;
    }
    
    public int getTokenInt(String prompt){
        int intInput;
        while (true) {
            String userInput = getToken(prompt);
            try {
                intInput = Integer.parseInt(userInput);
                break; // will only get to here if input was a double
            } catch (NumberFormatException ignore) {
                System.out.println("Invalid input. Enter a number (integer)");
            }
        }
        return intInput;
    }
    
    /*
    public static void main(String[] s) {
        UserInterface.instance().process();
    }
    */
}
