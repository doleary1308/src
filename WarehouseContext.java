import java.util.*;
import java.io.*;
public class WarehouseContext
{
  
  private int currentState;
  private static WarehouseContext context;
  private static Warehouse warehouse;
  private int currentUser;
  private String userID;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  public static final int IsAdministrator = 0;
  public static final int IsClerk = 1;
  public static final int IsClient = 2;
  private WarehouseState[] states;
  private int[][] nextState;

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
	  catch (IOException ioe) {
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

  public void setLogin(int code)
  {currentUser = code;}

  public void setUser(String uID)
  { userID = uID;}

  public int getLogin()
  { return currentUser;}

  public String getUser()
  { return userID;}
  
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

  private WarehouseContext()
  { 
	  if (yesOrNo("Look for saved data and  use it?")) {
	      retrieve();
	    } else {
	      Warehouse.instance();
	    }

    states = new WarehouseState[4]; 
    states[0] = Administratorstate.instance();
    states[1] = Clerkstate.instance();
    states[2] = Clientstate.instance(); 
    states[3]=  Loginstate.instance();
    nextState = new int[4][4];
    nextState[0][0] = 0;nextState[0][1] = 1;nextState[0][2] = 2;nextState[0][3] = 3;
    nextState[1][0] = 1;nextState[1][1] = 1;nextState[1][2] = 2;nextState[1][3] = 3;
    nextState[2][0] = 2;nextState[2][1] = 2;nextState[2][2] = 2;nextState[2][3] = 3;
    nextState[3][0] = 0;nextState[3][1] = 1;nextState[3][2] = 2;nextState[3][3] = -1;
    currentState = 3;
  }

  public void changeState(int transition)
  {
    System.out.println("["+currentState+"]"+"["+transition+"]"+"="+nextState[currentState][transition]);
    //if we need knowladge of the FSM at a previous stage then
    if(transition == 3 && currentState != currentUser && currentState != 3){
        if(currentState == 1 && currentUser == 0){
            currentState = 0;
        }
        else if(currentState == 2 && currentUser == 0){
            currentState = 1;
        }
        else if(currentState == 2 && currentUser == 1){
            currentState = 1;
        }
    }//else use FSM transition table
    else{
        currentState = nextState[currentState][transition];
    }
    
    if (currentState == -1) 
      terminate();
        
    states[currentState].run();
  }

  private void terminate()
  {
   if (yesOrNo("Save data?"))
   {
      if (Warehouse.save())
	  {
         System.out.println(" The warehouse has been successfully saved in the file WarehouseData \n" );
       }
	   else
	   {
         System.out.println(" There has been an error in saving \n" );
       }
     }
   System.out.println(" Goodbye \n "); System.exit(0);
  }

  public static WarehouseContext instance()
  {
    if (context == null)
	{
      context = new WarehouseContext();
    }
    return context;
  }

  public void process()
  {
    states[currentState].run();
  }
  
  public static void main (String[] args)
  {
    WarehouseContext.instance().process(); 
  }
}