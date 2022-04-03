public abstract class WarehouseState {
  protected static WarehouseContext context;
  protected WarehouseState() {
    //context = WarehouseContext.instance();
  }
  public abstract void run();
  
  public void logout()
  {
    (WarehouseContext.instance()).changeState(3);
  }
  
}
