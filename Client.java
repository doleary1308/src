import java.util.*;
import java.io.*;

public class Client implements Serializable {
    private String name;
    private String address;
    private String phone;
    private double debit;
    private String id;
    private ShoppingCart shoppingCart;
    private static final String CLIENT_STRING = "C";
    private List transactions = new LinkedList();
    
    //constructor
    public Client(String name, String address, String phone){
        this.name = name;
        this.address = address;
        this.phone = phone;
        id = CLIENT_STRING + (ClientIdServer.instance()).getId();
        debit = 0;
        shoppingCart = new ShoppingCart();
    }
    
    //getters and setters
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
    
    public String getId(){
        return id;
    }

    public double getDebit() {
        return debit;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }
    
    //functions
    @SuppressWarnings("unchecked")
    public boolean Pay(double amount){
        if(amount > debit){
            System.out.println("The entered amount is greater than the debit ($" + debit + ")");
            return false;
        }
        else{
            debit -= amount;
            //record to transactions in next stage
            System.out.println("Payment sucessful. Debit remaining: $" + debit);
            transactions.add(new Transaction(amount, debit));
            return true;
        }
    }
    
    
    public boolean equals(String id){
        return this.id.equals(id);
    }
    
    public Iterator getTransactions(){
        return transactions.iterator();
    }
    
    public String toString() {
        String string = "Client name: " + name + "\nAddress: " + address + "\nId: " + id + "\nPhone: " + phone + "\nDebit: " + debit + "\n";
        return string;
    }
}
