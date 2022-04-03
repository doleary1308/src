import java.util.*;
import java.text.*;
import java.io.*;
public class Clientstate extends WarehouseState
{
  private static Clientstate clientstate;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  private static UserInterface ui;
  private static final int LOGOUT = 0;
  private static final int SHOW_CLIENT_DETAILS = 1;
  private static final int SHOW_PRODUCTS = 2;
  private static final int SHOW_TRANSACTIONS = 3;
  private static final int MODIFY_CART = 4;
  private static final int DISPLAY_WAITLIST = 5;
  private static final int HELP = 6;
  
  private Clientstate()
  {
    warehouse = Warehouse.instance();
    ui = UserInterface.instance();
  }

  public static Clientstate instance()
  {
    if (clientstate == null)
	{
      return clientstate = new Clientstate();
    }
	else
	{
      return clientstate;
    }
  }
  
  public String getToken(String prompt)
  {
    do
	{
      try
	  {
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
    } while (true);
  }
  
  private boolean yesOrNo(String prompt)
  {
    String more = getToken(prompt + " (Y|y)[es] or anything else for no");
    if (more.charAt(0) != 'y' && more.charAt(0) != 'Y')
	{
      return false;
    }
    return true;
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
      }
	  catch (NumberFormatException nfe)
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
      }
	  catch (NumberFormatException nfe)
	  {
        System.out.println("Enter a number");
      }
    }
	while (true);
  }

  public void help()
  {
    System.out.println("Enter a number between 0 and 6 as explained below:");
    System.out.println(LOGOUT + " to Exit\n");
    System.out.println(SHOW_CLIENT_DETAILS + " to show client details");
    System.out.println(SHOW_PRODUCTS + " to show list of products with sale prices");
    System.out.println(SHOW_TRANSACTIONS + " to show client transactions");
    System.out.println(MODIFY_CART + " to modify client’s shopping cart");
    System.out.println(DISPLAY_WAITLIST + " to display client’s waitlist");
    System.out.println(HELP + " for help");
  }

  public void showClientDetails()
  {
    String id = getToken("Enter client id");
    System.out.println(warehouse.searchClient(id).toString());
  }

  public void showProducts()
  {
    String id = getToken("Enter product id");
    System.out.println(warehouse.searchProduct(id).toString());
  }

  public void showTransactions()
  {
     ui.showTransactions(WarehouseContext.instance().getUser());
  }

  public void modifyCart()
  {
    ui.editItemCart();
  }

  public void displayWaitlist()
  {
    String clientId = WarehouseContext.instance().getUser();
    warehouse.getClientWaitlist(clientId);
  }

  public void process()
  {
    int command;
    help();
    while ((command = getCommand()) != LOGOUT)
	{
      switch (command)
	  {

        case SHOW_CLIENT_DETAILS:
			showClientDetails();
            break;
        case SHOW_PRODUCTS:
			showProducts();
            break;
        case SHOW_TRANSACTIONS:
			showTransactions();
            break;
        case MODIFY_CART:
			modifyCart();
            break;
        case DISPLAY_WAITLIST:
			displayWaitlist();
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