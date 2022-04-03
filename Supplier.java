import java.util.*;
import java.io.*;
public class Supplier implements Serializable {
  
  private static final String SUPPLIER_STRING = "S";
  private String id;
  private String name;
  private String address;
  private String phone;
  
  private List soldSupplies = new LinkedList();
  private List supplies = new LinkedList();
  
  //constructor
  public  Supplier (String id, String name, String address, String phone) {
    this.id = SUPPLIER_STRING + id;
    this.name = name;
    this.address = address;
    this.phone = phone;
  }

  //getters and setter
  public String getName() {
    return name;
  }
  
  public String getPhone() {
    return phone;
  }
  
  public String getAddress() {
    return address;
  }
  
  public String getId() {
    return id;
  }
  
  public void setName(String newName) {
    name = newName;
  }
  
  public void setAddress(String newAddress) {
    address = newAddress;
  }
  
  public void setPhone(String newPhone) {
    phone = newPhone;
  }
  
  public Iterator getSuppliesSold(){
      return soldSupplies.iterator();
  }
  
  public Iterator getSupplies(){
      return supplies.iterator();
  }
  
  //functions
  @SuppressWarnings("unchecked")
  public boolean InsertSoldSupply(Supply supply){
      soldSupplies.add(supply);
      return true;
  }
  @SuppressWarnings("unchecked")
  public boolean InsertSupply(Supply supply){
      supplies.add(supply);
      return true;
  }
  
  public boolean equals(String id) {
    return this.id.equals(id);
  }
  
  public String toString() {
    String string = "Name: " + name + "\nAddress: " + address + "\nId: " + id + "\nPhone " + phone + "\n";
    return string;
  }
}