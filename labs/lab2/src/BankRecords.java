
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class BankRecords
{

	public static final String filepath = "src/bank-Detail.csv";
	public static final String columns[] = { "ID", "Age", "Sex", "Region", "Income", "Married", "Children", "Car",
			"Savings", "Current", "Mortgage", "Pep" };
	public static final int COLS = 600;

	// setup static objects for IO processing

	// array of BankRecords objects
	static BankRecords BRList[] = new BankRecords[COLS];
	// arraylist to hold spreadsheet rows & columns
	static ArrayList<List<String>> array = new ArrayList<>();

	public static void main(String[] args)
	{
		try
		{
			readData();
			processData();
			printData();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// instance fields
	private String id;
	private int age;
	private String sex;
	private String region;
	private double income;
	private String married;
	private int children;
	private String car;
	private String save_act;
	private String current_act;
	private String mortgage;
	private String pep;

	public static void print(String s)
	{
		print(s, "\n");
	}

	public static void print(String s, String end)
	{
		System.out.print(s);
		System.out.print(end);
	}

	public static void readData() throws IOException
	{

		BufferedReader br;

		br = new BufferedReader(new FileReader(new File(filepath)));
		String line;

		while ((line = br.readLine()) != null)
		{
			array.add(Arrays.asList(line.split(","))); // add split lines
		}

		br.close();
	}

	public static void processData()
	{

		int i = 0;

//		for(int i = 0; i < array.size(); i++)
		for (List<String> singleRow : array)
		{
//			List<String> rowData = array.get(i);

			BRList[i] = new BankRecords();
			
//			System.out.printf("Made my %dth BankRecords object! Using this line: %s\n",
//					i,singleRow.toString().replaceAll("[\\r\\n]", ""));

			BRList[i].setId(singleRow.get(0));
			BRList[i].setAge(Integer.parseInt(singleRow.get(1)));
			BRList[i].setGender(singleRow.get(2));
			BRList[i].setRegion(singleRow.get(3));
			BRList[i].setIncome(Double.parseDouble(singleRow.get(4)));
			BRList[i].setMarried(singleRow.get(5));
			BRList[i].setChildren(Integer.parseInt(singleRow.get(6)));
			BRList[i].setCar(singleRow.get(7));
			BRList[i].setSave_act(singleRow.get(8));
			BRList[i].setCurrent_act(singleRow.get(9));
			BRList[i].setMortgage(singleRow.get(10));
			BRList[i].setPep(singleRow.get(11));

			i++;
		}
	}

	public static void printData()
	{
		print(String.format("%10s %4s %7s %10s %10s %9s %9s %9s %9s %9s %9s %9s", columns[0], columns[1], columns[2], columns[3],
			columns[4], columns[5], columns[6], columns[7], columns[8], columns[9], columns[10], columns[11]));

		for (int i = 0; i < BRList.length; i++)
		{
			
			if (BRList[i] != null)
			{
				print(BRList[i].toString());
			}
		}
	}

	public String toString()
	{
		return String.format("%10s %4d %7s %10s %10.02f %9s %9d %9s %9s %9s %9s %9s", this.getId(), this.getAge(), this.getSex(),
				this.getRegion(), this.getIncome(), this.getMarried(), this.getChildren(), this.getCar(),
				this.getSave_act(), this.getCurrent_act(), this.getMortgage(), this.getPep());
	}

	public static BankRecords[] getRobjs()
	{
		return BRList;
	}

	public static void setRobjs(BankRecords[] robjs)
	{
		BankRecords.BRList = robjs;
	}

	public static ArrayList<List<String>> getArray()
	{
		return array;
	}

	public static void setArray(ArrayList<List<String>> array)
	{
		BankRecords.array = array;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public String getSex()
	{
		return sex;
	}

	public void setGender(String sex)
	{
		this.sex = sex;
	}

	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}

	public double getIncome()
	{
		return income;
	}

	public void setIncome(double income)
	{
		this.income = income;
	}

	public String getMarried()
	{
		return married;
	}

	public void setMarried(String married)
	{
		this.married = married;
	}

	public int getChildren()
	{
		return children;
	}

	public void setChildren(int children)
	{
		this.children = children;
	}

	public String getCar()
	{
		return car;
	}

	public void setCar(String car)
	{
		this.car = car;
	}

	public String getSave_act()
	{
		return save_act;
	}

	public void setSave_act(String save_act)
	{
		this.save_act = save_act;
	}

	public String getCurrent_act()
	{
		return current_act;
	}

	public void setCurrent_act(String current_act)
	{
		this.current_act = current_act;
	}

	public String getMortgage()
	{
		return mortgage;
	}

	public void setMortgage(String mortgage)
	{
		this.mortgage = mortgage;
	}

	public String getPep()
	{
		return pep;
	}

	public void setPep(String pep)
	{
		this.pep = pep;
	}

}
