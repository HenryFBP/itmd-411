import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;

public class Dao
{
	private static final String URL = "jdbc:mysql://www.papademas.net:3306/411labs?autoReconnect=true&useSSL=false";
	private static final String USERNAME = "db411";
	private static final String PASSWORD = "411";
	//	private static final String URL = "jdbc:mysql://127.0.0.1:3306/testdb";
//	private static final String[] USERNAME = {"root", "db411"};
//	private static final String[] PASSWORD = {"password","admin","root","toor","411"};
	public  static final String TABLE_NAME = "H_Post_tab";
	private static final int 	MAX_RESULTS = 600;
	
	private static final String columns[] = 
		   {"PID", "ID", "Age", "Gender",
			"Region", "Income", "Married","Children",
			"Car", "Savings", "Current", "Mortgage",
			"Pep" };
	
	private static final String columnTypes[] = 
		   {"INTEGER","VARCHAR(30)","INTEGER","VARCHAR(10)",
		    "VARCHAR(30)","DOUBLE(10,4)","VARCHAR(10)","INTEGER",
		    "VARCHAR(10)","VARCHAR(10)","VARCHAR(10)","VARCHAR(10)",
		    "VARCHAR(10)"};
	private static final String columnModifiers[] = 
		   {"NOT NULL UNIQUE AUTO_INCREMENT","","","",
		    "","","","",
		    "","","","",
		    ""};
	
	public static ArrayList<String> command_history = new ArrayList<>();
	
	private static ResultSet execute_print_query(String string, Statement s)
	{
		command_history.add(string);
		System.out.print(string.replace('\n', ' ')+"\n");
		Boolean wasDataManip = null;
		
		try
		{
			return s.executeQuery(string);
		}
		catch (SQLException e)
		{
			try
			{
				System.out.println("Instead of using executeQuery(), we are going to use execute().");
				wasDataManip = s.execute(string);
				System.out.println("Using execute succeeded! Ignore other errors.\n");
			}
			catch (Exception e1)
			{
				System.out.println("Darn.\nData manipulation query results in: "+ wasDataManip);
				e1.printStackTrace();
			}
			finally
			{
				if(wasDataManip == null)
				{
					System.out.println("Executing query \'" + string + "\' failed!");
					e.printStackTrace();			
				}
			}
		}
		return null;
		
	}
	
	private static final String NO_SAFE_UPDATES = "SET SQL_SAFE_UPDATES = 1";
	
	private static Connection conn = null;
	
	public Dao()
	{
		try
		{
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);		
		}
		catch (Exception e)
		{
			System.out.println("Exception happened for URL \'" + URL + "\'");
//			System.out.println("Exception is: "+e);
			e.printStackTrace();
		}
	}
	
	public static int getLargestColumn(ResultSet rs)
	{
		int largest = -1;
		ResultSetMetaData rsmd = null;
		try
		{
			rsmd = rs.getMetaData();
			
			
			for (int i = 1; i < rsmd.getColumnCount(); i++)
			{
				if(rsmd.getColumnDisplaySize(i) > largest)
				{
					largest = rsmd.getColumnDisplaySize(i);
				}
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return largest;

		
	}
	

	
	public static void printResultSet(ArrayList<String> ar)
	{
		for(int i = 0; i < ar.size(); i++)
		{
			System.out.println(ar.get(i));
		}
		return;
	}
	
	
	public static ArrayList<String> formatResultSet(ResultSet rs) 
	{
		int largestColumn = getLargestColumn(rs);
		String stringFormat = 	"%" + 13 + "s |";
		String stringFormat2 =  "%" + 15 + "s |";
		
		ArrayList<String> ret = new ArrayList<>();

		ResultSetMetaData rsmd;
		try
		{
			rsmd = rs.getMetaData();
			ret.add(String.format(stringFormat2, "Column name:"));
			ret.add(String.format(stringFormat2, "Column type:"));
			
			
//			System.out.println("metadata:");
			for(int i = 1; i < rsmd.getColumnCount(); i++)
			{
//				printf("i = %2d, current thing = %10s, %10s\n",
//						i,rsmd.getColumnName(i),rsmd.getColumnTypeName(i));
				ret.set(0,
						ret.get(0) + String.format(stringFormat,rsmd.getColumnName(i)) //tack on a name
						);
				
				ret.set(1,
						ret.get(1) + String.format(stringFormat,rsmd.getColumnTypeName(i)) //tack on a type
						);
			}
			
//			System.out.println("length of my lines should be " + ret.get(0).length());
			ret.add(stringLine("-",ret.get(0).length()));
			ret.add(0, stringLine("_",ret.get(0).length()));
			
			while(rs.next())
			{
				String oneColumn = String.format(stringFormat2,"");
				for(int i = 1; i < rsmd.getColumnCount(); i++)
				{
//					System.out.println("HI. ONECOLUMN = "+oneColumn);
					oneColumn += String.format(stringFormat, rs.getString(i));
				}
				
				ret.add(oneColumn);
			}

			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		
		
		return ret;
	}
	
	
	private static String stringLine(String duplicate, int length)
	{
		String ret = "";
		
		for (int i = 0; i < length ; i++)
		{
			ret += duplicate;
		}
		return ret;
	}

	public static void createTables()
	{
		Statement s = null;
		String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "\n(";
		
		
		try
		{
			s = conn.createStatement();
			
			System.out.print("Executing this:");
			execute_print_query(NO_SAFE_UPDATES,s);
			
			
			
			for (int i = 0; i < columns.length; i++)
			{
				query += columns[i] + " " + columnTypes[i] + " " + columnModifiers[i];

				if(i != columns.length-1)
				{ 
					query += ",\n"; //not the end, add a comma
				}
				else
				{
					query += ");";
				}
			}
			
			
			System.out.println("executing this:");
			execute_print_query(query,s);
			
			query = "DESCRIBE " + TABLE_NAME;
			
			ResultSet r = execute_print_query(query,s);
			ResultSetMetaData rmd = r.getMetaData();
			
			System.out.println("Table \'"+TABLE_NAME+"\' that was created: ");
			
		
			while(r.next())
			{
				String str = "";
				ArrayList<String> strA = new ArrayList<>();
				
				for(int	i = 1; i < rmd.getColumnCount(); i++)
				{
					strA.add(r.getString(i));
				}

				System.out.println(String.format("%-10s %-15s %-5s %-5s %-5s",
								strA.get(0),strA.get(1),strA.get(2),strA.get(3),strA.get(4)
								));
			}
				

		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return;
		
	}
	
	public static void resetTables()
	{
		String delmeTemp = "ALTER TABLE " + TABLE_NAME + " ADD delme INTEGER";
		
		Statement s = null;
		System.out.println("Executing this: \'" + delmeTemp + "\'");
		try
		{
			s = conn.createStatement();
			execute_print_query(delmeTemp,s);
			//this is added as we need a table to have at least one column.
		}
		catch (Exception e)
		{
			 System.out.println("Executing above statement failed!");
		}
		
		
		String dropQuery = "ALTER TABLE " + TABLE_NAME + " DROP COLUMN ";
		String addQuery  = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN ";
		String tempQuery = "";
		
		for (int i = 0; i < columns.length; i++)
		{
			tempQuery = dropQuery + columns[i];
			System.out.println("exec: ");
			execute_print_query(tempQuery,s);


			
			tempQuery = addQuery + columns[i] + " " + columnTypes[i] + " " + columnModifiers[i];
			System.out.println("exec: "+tempQuery);
			try
			{
				conn.createStatement().execute(tempQuery);
			}
			catch(SQLException e)
			{
				System.out.print("Couldn't execute adding query \'"+tempQuery+"\' because of: ");
				System.out.println(e.getMessage() + "\n");
			}
			
		}
		
		
		
		try
		{
			conn.createStatement().execute("ALTER TABLE " + TABLE_NAME + " DROP COLUMN delme");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void insertRecords(ArrayList<BankRecords> br, Boolean execute)
	{		
		Statement s = null;
		String query = "";
		try
		{
			s = conn.createStatement();
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		
		
		
		for (int i = 0; i < br.size(); i++)
		{
			BankRecords person = br.get(i);
			query = "INSERT INTO " + TABLE_NAME + " VALUES(";
			query += i + ", ";									//PID, 		int
			query += "'" + 	person.id + "', ";					//ID, 		String
			query += 		person.age + ", ";					//age, 		int
			query += "'" + 	person.gender + "', ";				//gender, 	String
			query += "'" + 	person.region + "', ";				//region,	String
			query += 		person.income + ", ";				//income,	double
			query += "'" +	person.married + "', ";				//married,	String
			query += 		person.children + ", ";				//children,	int
			query += "'" +	person.car + "', ";					//car,		String
			query += "'" +	person.save_act + "', ";			//savings,	String
			query += "'" +	person.current_act + "', ";			//current,	String
			query += "'" +	person.mortgage + "', ";			//mortgage,	String
			query += "'" +	person.pep + "'";					//pep,		String
			
			query += ")";
			
			System.out.println("\nExecuting INSERT query: "+query);
			if(execute)
			{
				execute_print_query(query,s);		//actually executes the thing
			}
		}

	}
	

	public static ResultSet executeQuery(String query)
	{
		Statement s = null;
		System.out.print("Retrieving records using this query: ");
		try
		{
			s = conn.createStatement();
			return execute_print_query(query, s);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;	
	}

	public static ResultSet executeQuery()
	{
		return executeQuery(MAX_RESULTS);
	}
	
	public static ResultSet executeQuery(int limit)
	{
		String query = "SELECT * FROM " + TABLE_NAME + " LIMIT "+limit;

		return executeQuery(query);
	}

	
	public static ArrayList<ArrayList<Object>> resultSetMetadataToList(ResultSet rs)
	{
		ResultSetMetaData rsmd = null;
		ArrayList<ArrayList<Object>> ret = new ArrayList<>();
		
		try
		{
			rsmd = rs.getMetaData();
			ArrayList<Object> tempRow = new ArrayList<>();
			ArrayList<Object> tempRow2 = new ArrayList<>();
			
			//add metadata for headers to ret array
			for (int i = 1; i < rsmd.getColumnCount(); i++)
			{
				tempRow.add(rsmd.getColumnName(i));
				tempRow2.add(rsmd.getColumnTypeName(i));
			}
			
			tempRow.add((Object)rsmd.getColumnName(rsmd.getColumnCount()));		// FIXME figure out why we need this...fixes last col not appearing...
			tempRow2.add((Object)rsmd.getColumnName(rsmd.getColumnCount()));		// FIXME figure out why we need this...fixes last col not appearing...
			ret.add(tempRow);
			ret.add(tempRow2);
			
			
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		
		
		return ret;
	}
	
	public static ArrayList<ArrayList<Object>> resultSetToList(ResultSet rs)
	{
		ResultSetMetaData rsmd = null;
		ArrayList<ArrayList<Object>> ret = new ArrayList<>();
		ArrayList<Object> tempRow;

		
		try
		{

			rsmd = rs.getMetaData();
			
			
			System.out.printf("Width of cols are %d\n",rsmd.getColumnCount());
			
			int col = 0;
			while(rs.next())
			{
//				System.out.printf("Col %d\n",col);
//				System.out.println("rs.getString(1) = "+rs.getString(1));
//				System.out.println("rs.getObject(1) = "+rs.getObject(1));

				tempRow = new ArrayList<>();
				
				if(rsmd.getColumnCount() > 1)
				{
					for (int i = 1; i < rsmd.getColumnCount(); i++)
					{
//						System.out.printf("about to add %s\n",rs.getObject(i));
						tempRow.add((Object)rs.getString(i));
					}
				}
				
				tempRow.add((Object)rs.getString(rsmd.getColumnCount())); // FIXME figure out why we need this...fixes last col not appearing...

				
				
//				rs.geto
				ret.add(tempRow);
//				System.out.println("added this row to ret array: "+tempRow.toString());
				col++;
			}
			
			
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
//		System.out.println("Returning this: "+ret.toString());
		
		
		return ret;
	}
	
	public static void deleteAllRecords()
	{
		Statement s = null;
		try
		{
			s = conn.createStatement();
			s.execute("DELETE FROM "+TABLE_NAME + ";");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
}
