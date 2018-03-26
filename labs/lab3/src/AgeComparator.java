import java.util.Comparator;

public class AgeComparator implements Comparator<BankRecords>
{
	@Override
	public int compare(BankRecords br1, BankRecords br2)
 	{
		return (br1.age - br2.age);
	}

}
