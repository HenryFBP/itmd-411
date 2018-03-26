
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class BankRecords implements Serializable
{

	public static final String filepath = "bank-Detail.csv";
	public static final String columns[] = { "ID", "Age", "Gender", "Region", "Income", "Married", "Children", "Car",
			"Savings", "Current", "Mortgage", "Pep" };
	public static final String genders[] = {"male","female","satan"};
	public static final String boolStrings[] = {"yes","true"};
	public static final int max_rows = 600;

	// setup static objects for IO processing

	// array of BankRecords objects
	static ArrayList<BankRecords> BRList = new ArrayList<BankRecords>();
	// arraylist to hold spreadsheet rows & columns
	static ArrayList<List<String>> array = new ArrayList<>();

	// instance fields
	public String id;
	public int age;
	public String gender;
	public String region;
	public double income;
	public String married;
	public int children;
	public String car;
	public String save_act;
	public String current_act;
	public String mortgage;
	public String pep;

	public static boolean parseBool(String s)
	{
		for(int i = 0; i < boolStrings.length; i++)
		{
			if(s.equalsIgnoreCase(boolStrings[i]))
			{
				return true;
			}
		}
		return false;
	}

	public static String print(String s)
	{
		System.out.print(s);
		return s;
	}
	
	public static String print(String s, int i)
	{
		return print(s) + println(i);
		
		
	}
	
	public static String print(String s, String end)
	{
		System.out.print(s);
		System.out.print(end);
		return (s + end);
	}
	
	public static ArrayList<String> print(ArrayList<String> s)
	{
		for (int i = 0; i < s.size(); i++)
		{
			print(s.get(i));
		}
		return s;
	}
	
	public static String println(String s)
	{
		print(s);
		println();
		return (s+"\n");
	}

	public static String println()
	{
		return(print("\n"));
	}
	
	public static String println(int lines)
	{
		String ret = "";
		for (int i = 0; i < lines; i++)
		{
			ret = ret + println();
		}
		return ret;
	}
	

	public static void readData()
	{
	
		BufferedReader br = null;
		String line = "";

		try
		{
			br = new BufferedReader(new FileReader(new File(filepath)));
			
			while ((line = br.readLine()) != null)
			{
				array.add(Arrays.asList(line.split(","))); // add split lines
			}
		
			br.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public static void processData()
	{
	
		int idx = 0;
	
		for (List<String> rowData : array)
		{
			BankRecords b = new BankRecords();
			BRList.add(b);
	
			BRList.get(idx).id = (rowData.get(0));
			BRList.get(idx).age = (Integer.parseInt(rowData.get(1)));
			BRList.get(idx).gender = (rowData.get(2));
			BRList.get(idx).region = (rowData.get(3));
			BRList.get(idx).income = (Double.parseDouble(rowData.get(4)));
			BRList.get(idx).married = (rowData.get(5));
			BRList.get(idx).children = (Integer.parseInt(rowData.get(6)));
			BRList.get(idx).car = (rowData.get(7));
			BRList.get(idx).save_act = (rowData.get(8));
			BRList.get(idx).current_act = (rowData.get(9));
			BRList.get(idx).mortgage = (rowData.get(10));
			BRList.get(idx).pep = (rowData.get(11));
	
			idx++;
		}
	}

	
	
	public ArrayList<String> printData()
	{
		return printData(BRList.size());
	}
	
	public static ArrayList<String> printData(ArrayList<BankRecords> brlist, int stop)
	{
		if(stop < 0)
		{
			return printData(BRList,(BRList.size() + stop),BRList.size()); //last 3 if it's -3...
		}
		else
			
		{
			return printData(BRList,0,stop);
		}
	}
	
	public static ArrayList<String> printData(int stop)
	{
		return printData(BRList,stop);
	}
	
	public static String getHeaders()
	{
		return String.format("%10s %4s %7s %10s %10s %9s %9s %9s %9s %9s %9s %9s",
				columns[0], columns[1], columns[2], columns[3], columns[4], columns[5],
				columns[6], columns[7], columns[8], columns[9], columns[10], columns[11]);
	}
	
	public static ArrayList<String> printData(ArrayList<BankRecords> brlist, int start, int stop)
	{
		ArrayList<String> ret = new ArrayList<>();
		
		ret.add(print(getHeaders()));
		
		
		
		for (int i = start; i < stop; i++)
		{
			
			if (BRList.get(i) != null)
			{
				ret.add(println(BRList.get(i).toString()));
			}
		}
		return ret;
	}


	

	
	public String toString()
	{
		return String.format("%10s %4d %7s %10s %10.02f %9s %9d %9s %9s %9s %9s %9s", 
				this.id,
				this.age,
				this.gender, this.region,
				this.income,
				this.married, 
				this.children,
				this.car,
				this.save_act, this.current_act, this.mortgage, this.pep);
	}

	public static ArrayList<BankRecords> getBRList()
	{
		return BRList;
	}

	public void setRobjs(ArrayList<BankRecords> robjs)
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

}
