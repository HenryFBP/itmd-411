

/**
 * @author henryfbp
 * @date 09/15/17
 * @class ITMD411
 * @lab 01
 * 
 * This program simulates a command-line banking application.
 */

public class AccountHolder
{
    static double annualInterestRate;
    double balance;
    
    /***
     * 
     * @param balance The initial balance for the account. Must be >= 0.
     */
    public AccountHolder(double balance)
    {
        this.balance = 0;
        
//        if(balance >= 0)
        if(true)
        {
            this.balance = balance;
        }
        else
        {
            System.out.println("Balance '" + balance +"' is negative and cannot be used! Using '0' instead.");
        }
    }
    
    
    /***
     * 
     * @param toDeposit A double representing how much will be deposited into the bank account.
     */
    public void deposit(double toDeposit)
    {
        this.balance += toDeposit;  //add toDeposit to current funds
    }
    

    /***
     * 
     * @param toWithdraw A double representing how much will be withdrawn from the bank account.
     */
    public void withdrawal(double toWithdraw)
    {
        double projectedBalance = this.balance - toWithdraw;
        

        if(projectedBalance < 500.00 && projectedBalance >= 100)
        {//charge for being under 500 dollars
            this.balance -= toWithdraw;
            this.balance -= 50; //transaction fee for under $500
            return;
        }
        else if(projectedBalance < 100)
        {//don't withdraw, tell user

            System.out.println("Attempted transation of '"+toWithdraw+"' not withdrawn, as it would cause balance to be '"+projectedBalance+"', which is under '$100'.");
            return;
        }
        else
        {//user is withdrawing money from an account that will have over $500 after it's done
        	this.balance -= toWithdraw;
        	return;
        }
        
    }
    
    /***
     * 
     * @param balance The balance to be used in the interest calculation.
     * This method updates this account's balance to be (balance + (balance * annualInterestRate / 12.0)).
     */
    public void monthlyInterest(double balance)
    {
        this.balance += (balance * (annualInterestRate / 12.0));
    }
    
    public static void modifyMonthlyInterest(double rateUpdate)
    {
        if(rateUpdate >= 0 && rateUpdate <= 1.0)
        {
            annualInterestRate = rateUpdate;
        }
    }

    @Override
    public String toString()
    {
        return String.format("$%.2f",this.balance); //returns a balance double formatted as "$2.35" even if it's 2.356324. Thanks String.format!
    }
    
    
}
