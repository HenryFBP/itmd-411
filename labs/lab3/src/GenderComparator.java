import java.util.Comparator;

public class GenderComparator implements Comparator<BankRecords>
{

	@Override
	public int compare(BankRecords br1, BankRecords br2)
	{
		if(br1 != null && br2 != null)
		{
			return br1.gender.compareTo(br2.gender);
		}
		return 0;
	}

}
