import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class Records extends BankRecords
{
	
	public static final String mypath = "src\\bankRecords_lab3_hpost.txt";
	public static final int dispRows = 10;

	public static void main(String[] args)
	{
		
		ArrayList<String> toWrite = new ArrayList<>();
		
		toWrite.add(print("We're in records, for lab3!!!",2));
		
		readData();
		processData(); 
		toWrite.add(println("First ten lines of unsorted datafile:"));
		toWrite.addAll(printData(10));
		
		toWrite.add(println(3));
		toWrite.addAll(print(avgIncomeComparator()));
		toWrite.add(println(3)) ;
		
		toWrite.addAll(print(genderMortgageSavings("female")));
		toWrite.add(println(3));
		
		toWrite.addAll(print(genderWithCarAndChildPerLocation("male",1)));
		toWrite.add(println(3));
		
		
		ArrayList<BankRecords> BRListIncome = BRList;
		Collections.sort(BRListIncome, new AvgIncomeComparator());
		toWrite.add(println(String.format("Sorted by income: (first %d and last %d)",dispRows,dispRows)));
		toWrite.addAll(printData(dispRows));
		toWrite.addAll(printData(-dispRows));
		toWrite.add(println(3));

		ArrayList<BankRecords> BRListGender = BRList;
		println(String.format("Sorted by gender (first %d and last %d)",dispRows,dispRows));
		Collections.sort(BRListGender, new GenderComparator());
		toWrite.addAll(printData(dispRows));
		toWrite.addAll(printData(-dispRows));
		toWrite.add(println(3));
		
		ArrayList<BankRecords> BRListAge = BRList;
		println(String.format("Sorted by age (first %d and last %d)",dispRows,dispRows));
		Collections.sort(BRListAge, new AgeComparator());
		toWrite.addAll(printData(dispRows));
		toWrite.addAll(printData(-dispRows));
		toWrite.add(println(3));
		
        
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        toWrite.add(print("Cur dt=" + timeStamp + "\nProgrammed by Henry Post.\nLab 3."));
		
		try
		{
			String filename = mypath;
			PrintWriter printWriter = new PrintWriter(filename);
			
			for (int i = 0; i < toWrite.size(); i++) 
			{
//				print("WRITING "+toWrite.get(i));
				printWriter.print(toWrite.get(i));
			}
			
			
			
			
			printWriter.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
	}
	





	private static ArrayList<String> genderWithCarAndChildPerLocation(String gender, int children)
	{
		ArrayList<String> ret = new ArrayList<>();
//		printData(0);
        HashMap<String, Integer> popPerLocation = new HashMap<String, Integer>();
        
        for (int i = 0; i < BRList.size(); i++)
        {
			if(BRList.get(i).gender.equalsIgnoreCase(gender) && //if gender and
			   (BRList.get(i).children == children) && 			//kids and
			   parseBool(BRList.get(i).car)) { 					//cars
				
//				print("person who fits our criteria:");
//				print(BRList[i].toString());
				
				popPerLocation.put(
						BRList.get(i).region, //insert into dict[region]
						(popPerLocation.get(BRList.get(i).region) == null?
								1 //a one if value is un-initialized
								: 
								popPerLocation.get(BRList.get(i).region)+1//or increment if we have a value
						)
				);
			}
		}
        
        
        ret.add((String.format("People of gender %s who have a car and %d children:",gender,children)));
        for (Entry<String, Integer> entry: popPerLocation.entrySet())
        {
        	ret.add(String.format("%15s : %3d",
        			entry.getKey(),
        			entry.getValue()));
        }
        
        return ret;
        

		
	}

	private static ArrayList<String> genderMortgageSavings(String gender)
	{
		ArrayList<String> ret = new ArrayList<>();
		
		int people = 0;
		int peopleMortgageSavings = 0;
				
		for (int i = 0; i < BRList.size(); i++)
		{
			if(BRList.get(i).gender.equalsIgnoreCase(gender))
			{
				people++; 
				if((parseBool(BRList.get(i).mortgage) == true) && 
					parseBool(BRList.get(i).save_act) == true)
					{
						peopleMortgageSavings++;
					}
			}
		}
		
		ret.add((String.format("Out of %d people of \'%s\' gender, %d of them have a mortgage and savings account.",
				people, gender, peopleMortgageSavings)));
		
		return ret;
	}

	public static ArrayList<String> avgIncomeComparator()
	{
		ArrayList<String> ret = new ArrayList<>();
		
		ret.add(("Average income for all genders:\n"));
		Collections.sort(BRList, new GenderComparator());
		
		ArrayList<Integer> genderCount = new ArrayList<>(genders.length);
		ArrayList<Double> genderMoney = new ArrayList<>(genders.length);
		
		for(int i = 0; i < genders.length; i++)
		{ //init for how long we want
			genderCount.add(0);
			genderMoney.add(0.0);
		}
		
		
		for (int i = 0; i < BRList.size(); i++) //go thru all records
		{
			for(int j = 0; j < genders.length; j++)//go thru gender list
			{
				if(BRList.get(i).gender.equalsIgnoreCase(genders[j]))
				{
					genderCount.set(j, (genderCount.get(j)+1)); 
					//tallied a person of gender X
					
//					genderMoney[j] += BRList[j].getIncome();
					genderMoney.set(j, (genderMoney.get(j) + BRList.get(j).income));
					//add their cash
				}
			}
		}

		ret.add((String.format("%8s | %6s |  %10s","gender","pop #","avg inc\n")));
		ret.add((String.format("---------------------------------------------\n")));
		
		for (int i = 0; i < genders.length; i++)
		{
			ret.add((String.format("%8s | %6d | $%10.02f\n",
					genders[i],
					genderCount.get(i),
					(genderMoney.get(i) / genderCount.get(i))))
				);
			
		}
		
		return ret;
		
		
		
	}
}
