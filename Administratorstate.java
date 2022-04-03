import java.util.*;
import java.text.*;
import java.io.*;
public class Administratorstate extends WarehouseState
{
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  private UserInterface ui;
  private static Administratorstate instance;
  private static final int LOGOUT = 0;
  private static final int ADD_PRODUCT = 1;
  private static final int ADD_SUPPLIER = 2;
  private static final int SHOW_SUPPLIERS = 3;
  private static final int SHOW_SUPPLIERS_PRODUCT = 4;
  private static final int SHOW_PRODUCTS_SUPPLIER = 5;
  private static final int UPDATE_PRODUCTS = 6;
  private static final int BECOME_SALESCLERK = 7;
  private static final int BECOME_CLIENT = 8;
  private static final int HELP = 9;
  private Administratorstate() 
  {
      super();
      warehouse = Warehouse.instance();
      ui = UserInterface.instance();
      //context = WarehouseContext.instance();
  }

  public static Administratorstate instance()
  {
    if (instance == null) {
      instance = new Administratorstate();
    }
    return instance;
  }

  public String getToken(String prompt)
  {
    do {
      try {
        System.out.println(prompt);
        String line = reader.readLine();
        StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
        if (tokenizer.hasMoreTokens())
		{
          return tokenizer.nextToken();
        }
      }
	  catch (IOException ioe)
	  {
        System.exit(0);
      }
    }
	while (true);
  }
  public int getNumber(String prompt)
  {
    do
	{
      try
	  {
        String item = getToken(prompt);
        Integer num = Integer.valueOf(item);
        return num.intValue();
      } catch (NumberFormatException nfe)
	  {
        System.out.println("Please input a number ");
      }
    }
	while (true);
  }
  public Calendar getDate(String prompt)
  {
    do
	{
      try
	  {
        Calendar date = new GregorianCalendar();
        String item = getToken(prompt);
        DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
        date.setTime(df.parse(item));
        return date;
      }
	  catch (Exception fe)
	  {
        System.out.println("Please input a date as mm/dd/yy");
      }
    }
	while (true);
  }
  public int getCommand()
  {
    do
	{
      try
	  {
        int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
        if (value >= LOGOUT && value <= HELP)
		{
          return value;
        }
      } catch (NumberFormatException nfe)
	  {
        System.out.println("Enter a number");
      }
    }
	while (true);
  }

  public void help()
  {
    System.out.println("Enter a number between 0 and 9 as explained below:");
    System.out.println(LOGOUT + " to Logout\n");
    System.out.println(ADD_PRODUCT + " to add a product");
    System.out.println(ADD_SUPPLIER + " to add a supplier");
    System.out.println(SHOW_SUPPLIERS + " to show list of suppliers");
    System.out.println(SHOW_SUPPLIERS_PRODUCT + " to show list of suppliers for a product, with purchase prices");
    System.out.println(SHOW_PRODUCTS_SUPPLIER + " to show list of products for a supplier, with purchase prices");
    System.out.println(UPDATE_PRODUCTS + " to update products and purchase prices for a particular supplier");
    System.out.println(BECOME_SALESCLERK + " to become a salesclerk");
    System.out.println(BECOME_CLIENT + " to become a client");
    System.out.println(HELP + " for help");
  }

  public void newProduct()
  {
     ui.addProduct();
  }

  public void addSupplier()
  {
      ui.addSupplier();
  }
 
  public void showSuppliers()
  {
     ui.showSuppliers();
  }
  
  public void showSuppliersProduct() {
      String productId = getToken("Enter product ID: ");
      warehouse.showSuppliers(productId);
  }

  public void showProductsSupplier()
  {
	  String id = getToken("Enter supplier ID: ");
	  warehouse.showProducts(id);
  }

  public void updateProducts()
  {
    String supplierId = getToken("Enter supplier ID: ");
    warehouse.showProducts(supplierId);
    String productId = getToken("Enter product id");
    Double price = ui.getTokenDouble("Enter price");
    warehouse.updateProducts(supplierId, productId, price);
  }

  public void becomeSalesclerk()
  {     
   (WarehouseContext.instance()).changeState(1);
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


 

  public void process()
  {
    int command;
    help();
    while ((command = getCommand()) != LOGOUT)
	{
      switch (command)
	  {
        case ADD_PRODUCT:
			newProduct();
			break;
        case ADD_SUPPLIER:
			addSupplier();
            break;
        case SHOW_SUPPLIERS:
			showSuppliers();
            break;
        case SHOW_SUPPLIERS_PRODUCT:
			showSuppliersProduct();
			break;
        case SHOW_PRODUCTS_SUPPLIER:
			showProductsSupplier();
            break;
        case UPDATE_PRODUCTS:
			updateProducts();
            break;
        case BECOME_SALESCLERK:
			becomeSalesclerk();
            break;
        case HELP:
			help();
            break;
      }
    }
    logout();
  }
  public void run()
  {
    process();
  }
}