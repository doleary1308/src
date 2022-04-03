import java.util.*;
import java.text.*;
import java.io.*;
public class Loginstate extends WarehouseState
{
  private static final int EXIT = 0;
  private static final int ADMINISTRATOR_LOGIN = 1;
  private static final int CLERK_LOGIN = 2;
  private static final int CLIENT_LOGIN = 3;
  
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));  
  private WarehouseContext context;
  private static Loginstate instance;
  private Loginstate()
  {
      super();
     // context = WarehouseContext.instance();
  }

  public static Loginstate instance()
  {
    if (instance == null)
	{
      instance = new Loginstate();
    }
    return instance;
  }

  public int getCommand()
  {
    do
    {
        try
        {
            int value = Integer.parseInt(getToken("Enter command:" ));
            if (value >= EXIT && value <= CLIENT_LOGIN)
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
    }
	while (true);
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

  private void administrator()
  {
    (WarehouseContext.instance()).setLogin(WarehouseContext.IsAdministrator);
    (WarehouseContext.instance()).changeState(0);
  }

  private void clerk()
  {
    (WarehouseContext.instance()).setLogin(WarehouseContext.IsClerk);
    (WarehouseContext.instance()).changeState(1);
  }

  private void client()
  {
    String clientID = getToken("Please input the client id: ");
    if (Warehouse.instance().searchClient(clientID) != null)
    {
      (WarehouseContext.instance()).setLogin(WarehouseContext.IsClient);
      (WarehouseContext.instance()).setUser(clientID);
      (WarehouseContext.instance()).changeState(2);
    }
    else 
      System.out.println("Invalid client id.");
  } 

  public void process()
  {
    int command;
    System.out.println("Login Menu:\n"+
                        "input 0 to exit the system\n"+ 
                        "input 1 to login as Administrator\n" +
                        "input 2 to login as Clerk\n" +
                        "input 3 to client\n");     
    while ((command = getCommand()) != EXIT)
	{

      switch (command)
	  {
        case ADMINISTRATOR_LOGIN:
			administrator();
            break;
        case CLERK_LOGIN:
			clerk();
            break;
        case CLIENT_LOGIN:
			client();
            break;
        default:
			System.out.println("Invalid choice");
                                
      }
      System.out.println("Please input 0 to login as Administrator\n"+ 
                        "input 1 to login as Clerk\n" +
                        "input 2 to login as Client\n" +
                        "input 3 to exit the system\n"); 
    }
    (WarehouseContext.instance()).changeState(3);
  }

  public void run()
  {
    process();
  }
}