

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Junit {

	
	
	private AccountHolder account;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
		account = new AccountHolder(0);
	}

	@Test
	public void testInitialInterest()
	{
		assert(account.annualInterestRate==0);
	}
	
	@Test
	public void testBalance()
	{
		account = new AccountHolder(1000);

		assert(account.balance == 1000);
		
//		if(account.balance != 1000)
//		{
//			fail("Account.balance should be equal to 1000!");
//		}
	}
	
	@Test
	public void testModifyInterest()
	{
		double rate = 0.05;
		double bal = 1000;
		account = new AccountHolder(bal);
		account.modifyMonthlyInterest(rate);
		
		for(int i = 0; i < 999; i++)
		{
			bal = bal + (bal * (rate / 12.0)); //compound interest
			//newBalance.
			bal = 1000 + (1000 * (0.05/12));
			account.monthlyInterest(account.balance);
			
			if(account.balance != bal)
			{
				fail("Acc.bal != bal aka '" + account.balance + "' != '" + bal + "'");
			}
		}
		
	}
	
	@Test
	public void testNullObject()
	{
		account = null;
		if(account != null)
		{
			fail("Assigning null value to account somehow failed!");
		}
	}
	
	@Test
	public void testWithdraw()
	{
		account = new AccountHolder(1000);
		
		account.withdrawal(50);
		
		assertTrue(account.balance == 950);
		
	}
	
	protected void tearDown() throws Exception
	{
		System.out.println("Running teardown.");
		account = null;
		assertNull(account);
	}
	

}
