


import java.io.BufferedReader;

/**
 * @author henryfbp
 * @date 09/15/17
 * @class ITMD411
 * @lab 01
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;


public class AccountHolderTest {
static final String nlc = "\n > ";



    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        int testVal = 140;
        AccountHolder myTestAccount = new AccountHolder(testVal);
        
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your initial account balance:"+nlc);
        AccountHolder myAccount = new AccountHolder(scan.nextDouble());
        
        System.out.println("Enter amount to be deposited:"+nlc);
        myAccount.deposit(scan.nextDouble());
        
        System.out.println("Enter amount to be withdrawn:"+nlc);
        myAccount.withdrawal(scan.nextDouble());
        
        System.out.println("The following is what happens when one attempts to "
                + "make a withdrawal that would drop your account balance below"
                + " $100,\n in this case we have $"+testVal+" and want to take out $50:");
        myTestAccount.withdrawal(50);
        
        
        System.out.println("Monthly balances for one year at 0.04");
        myAccount.modifyMonthlyInterest(0.04);

        System.out.println("Balances:");
        System.out.println("Account Balance w. Interest");
        
        System.out.printf("Base %15s\n",(myAccount.toString()));
        
        for(int i = 1; i <= 12; i++)
        {
            myAccount.monthlyInterest(myAccount.balance);
            System.out.printf("Month %2d: %10s\n",i,myAccount.toString());
        }
        
        System.out.println("After setting interest rate to .05 and calculating monthly interest");
        System.out.println("Balances:");
        System.out.println("Account Balance w. Interest");
        
        
        myAccount.modifyMonthlyInterest(0.05);
        myAccount.monthlyInterest(myAccount.balance);
        
        System.out.println(myAccount.toString());
        
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println("Cur dt=" + timeStamp + "\nProgrammed by Henry Post\n");
        
    }
    
}
