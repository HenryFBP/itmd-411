import java.util.Comparator;

public class AvgIncomeComparator implements Comparator<BankRecords>
{

	@Override
	public int compare(BankRecords br1, BankRecords br2)
	{		
		if(br1.income > br2.income)
		{
			return 1;
		}
		else if(br1.income == br2.income)
		{
			return 0;
		}
		
		return -1;
	}

}
