import java.util.*;
import java.text.*;
import java.io.*;
public class Clerkstate extends WarehouseState {
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  private static UserInterface ui;
  private static Clerkstate instance;
  private static final int LOGOUT = 0;
  private static final int ADD_CLIENT = 1;
  private static final int SHOW_PRODUCTS = 2;
  private static final int SHOW_CLIENTS = 3;
  private static final int SHOW_CLIENTS_DEBT = 4;
  private static final int DISPLAY_WAITLIST = 5;
  private static final int RECEIVE_SHIPMENT = 6;
  private static final int BECOME_CLIENT = 7;
  private static final int HELP = 8;
  private Clerkstate() {
      super();
      warehouse = Warehouse.instance();
      ui = UserInterface.instance();
  }

  public static Clerkstate instance() {
    if (instance == null) {
      instance = new Clerkstate();
    }
    return instance;
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
  public Calendar getDate(String prompt) {
    do {
      try {
        Calendar date = new GregorianCalendar();
        String item = getToken(prompt);
        DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
        date.setTime(df.parse(item));
        return date;
      } catch (Exception fe) {
        System.out.println("Please input a date as mm/dd/yy");
      }
    } while (true);
  }
  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
        if (value >= LOGOUT && value <= HELP) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }

  public void help() {
    System.out.println("Enter a number between 0 and 8 as explained below:");
    System.out.println(LOGOUT + " to Logout\n");
    System.out.println(ADD_CLIENT + " to add a client");
    System.out.println(SHOW_PRODUCTS + " to show list of products with quantities and sale prices");
    System.out.println(SHOW_CLIENTS + " to show list of clients");
    System.out.println(SHOW_CLIENTS_DEBT + " to show list of clients with outstanding balance");
    System.out.println(DISPLAY_WAITLIST + " to display the waitlist for a product");
    System.out.println(RECEIVE_SHIPMENT + " to receive a shipment");
    System.out.println(BECOME_CLIENT + " to become a client");
    System.out.println(HELP + " for help");
  }

  public void newClient() {
    String name = getToken("Enter client name");
    String address = getToken("Enter address");
    String phone = getToken("Enter phone");
    Client result;
    result = warehouse.addClient(name, address, phone);
    if (result == null) {
      System.out.println("Could not add client");
    }
    System.out.println(result);
  }

  public void showProducts() {
    ui.showProducts();
  }
 
  public void showClients() {
    ui.showClients();
  }
  public void showClientsDebt() {
    String id = getToken("Enter client ID: ");
    warehouse.searchClient(id).getDebit();
  }

  public void displayWaitlist() {
    ui.showWaitlistedItems();
  }

  public void receiveShipment() {
    ui.receiveSupplies();

  }

  public void becomeClient()
  {
    String clientID = getToken("Please input the client id: ");
    if (Warehouse.instance().searchClient(clientID) != null){
      (WarehouseContext.instance()).setUser(clientID);      
      (WarehouseContext.instance()).changeState(2);
    }
    else 
      System.out.println("Invalid client id."); 
  }

 

  public void process() {
    int command;
    help();
    while ((command = getCommand()) != LOGOUT) {
      switch (command) {
        case ADD_CLIENT:        newClient();
                                break;
        case SHOW_PRODUCTS:         showProducts();
                                break;
        case SHOW_CLIENTS:      showClients();
                                break;
        case SHOW_CLIENTS_DEBT:      showClientsDebt();
                                break;
        case BECOME_CLIENT:      becomeClient();
                                break;
        case DISPLAY_WAITLIST:     displayWaitlist();
                                break;
        case RECEIVE_SHIPMENT:     receiveShipment();
                                break;
        case HELP:              help();
                                break;
      }
    }
    logout();
  }
  public void run() {
    process();
  }
}
