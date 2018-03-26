import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class LoanProcessing extends BankRecords
{
	
//	public static final String prompt = " > ";
	public static final int TIME_DELAY_MS = 5000; //because idk how fast ur hard drive is ;)
	public static final String DEFAULT_QUERY = "SELECT * FROM "+Dao.TABLE_NAME;
	public static final String TEST_FILENAME = "test.ser";
	public static final String prompt = "";
	public static final String QUIT = "Q";
	public static final String[] OPTIONS = {
			"Print all records",
			"Create tables",
			"Reset and rebuild tables",
			"Delete all records",
			"Insert all records",
			"Serialize to a file (String path), relative or absolute.",
			"Deserialize from a file (String path), relative or absolute.",
			"See time diff between seri... and de-seri... our bankRecords!",
	};
	
	public static final String PROTIP = "PROTIP: I use scan.next(), so if you want to " +
									  	"delete all records and ALSO insert all records, you can do it in ONE LINE like so:\n" +
									  	"1 [SPACE] 2 [SPACE] [ENTER]\n" +
									  	"Cool, huh? Bye.";

	
	public static void main(String[] args) throws InterruptedException
	{
		Scanner s = new Scanner(System.in);
		String input = "";
		Dao dao = new Dao();
		int choice = Integer.MAX_VALUE;

		readData();
		processData();
		
		ArrayList<BankRecords> brfsf = new ArrayList<>();
		
		System.out.println(PROTIP);
		
		while(choice >= 0)
		{
			
			System.out.println("\n");
			for (int i = 0; i < OPTIONS.length; i++)
			{
				System.out.printf("%2d: %s\n",i,OPTIONS[i]); //print out options
			}
			
			choice = s.nextInt();
			
			switch(choice)
			{
			
				case 0:
					ResultSet rs = dao.executeQuery(DEFAULT_QUERY);
					dao.printResultSet(dao.formatResultSet(rs));
					break;
				case 1:
					dao.createTables();
					break;
					
				case 2:
					dao.resetTables();
					break;
					
				case 3:
					dao.deleteAllRecords();	
					break;
					
				case 4:
					dao.insertRecords(getBRList(),true);
					break;
				
				case 5:
					System.out.println("Path to file:");
					input = s.next();
					BankRecordsSerializer.serialize(getBRList(), input);
					break;
					
				case 6:
					System.out.println("Path to file:");
					input = s.next();
					brfsf.clear();
					brfsf.addAll(BankRecordsSerializer.deserialize(input));
					
					if(!brfsf.isEmpty())
					{
						System.out.println("From what two indices do you want to see your freshly deserialized file? (int, int)");
						int lower = s.nextInt();
						int higher = s.nextInt();
						
						System.out.println("      "+getHeaders());
						for(int i = lower; i <= higher; i++)
						{
							System.out.printf("%4d: %s\n",i,brfsf.get(i));
						}
					}
					else
					{
						System.out.println("Oops.");
					}
					break;
					
				case 7:
					
					Instant startSer = Instant.now();
					System.out.printf("Started at '%s'\n\n",startSer.toString());
					
					BankRecordsSerializer.serialize(getBRList(), TEST_FILENAME);
					
					Instant stopSer = Instant.now();
					System.out.printf("\n\nStopped at '%s'\n",startSer.toString());

					double diffSer = Math.abs(startSer.compareTo(stopSer));

					
					System.out.printf("Time to serialize '%s': %dns\n",TEST_FILENAME,startSer.getNano());
					
					System.out.printf("Sleeping for %dms aka %ds to not explode everything.\n",TIME_DELAY_MS,(TIME_DELAY_MS/1000));
					Thread.sleep(TIME_DELAY_MS);
					
					
					startSer = Instant.now();
					System.out.printf("Started at '%s'\n\n",startSer.toString());
					
					ArrayList<BankRecords> timeBRList = BankRecordsSerializer.deserialize(TEST_FILENAME);
					
					stopSer = Instant.now();
					System.out.printf("\n\nStopped at '%s'\n",startSer.toString());
					
					System.out.printf("Time to deserialize '%s': %dns\n",TEST_FILENAME,startSer.getNano());

					
					break;
					
				default:
					break;
			}
		}


		

		

		

		
		
		ResultSet rs = dao.executeQuery();
		ResultSetMetaData rsmd = null;
		try
		{
			rsmd = rs.getMetaData();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		ArrayList<String> formatted = dao.formatResultSet(rs);
		
		dao.printResultSet(formatted);
		s.close();

	}
	
	
	public static Boolean parseYes(String word)
	{
		if(word.charAt(0) == 'y' || word.charAt(0) == 'Y')
		{
			return true;
		}
		return false;
	}
	
	public static Boolean parseQuit(String word)
	{
		if(word.charAt(0) == 'q' || word.charAt(0) == 'Q')
		{
			return true;
		}
		return false;
	}
}
