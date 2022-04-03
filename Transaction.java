import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

public class Transaction {
    private double total;
    private double remainingBalance;
    private String date;

    public Transaction(double total, double remainingBalance) {
        this.total = total;
        this.remainingBalance = remainingBalance;
        this.date = getCurrentDate();
    }
    
    private String getCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        return dtf.format(now);  
    }
    
    public String toString() {
        String string = "Total transaction: " + total + "\nRemaining balance: " + remainingBalance;
        return string;
    }
    
}
