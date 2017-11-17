import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class BankRecordsSerializer implements Serializable
{

	private static final long serialVersionUID = 1L;

	public static void serialize(ArrayList<BankRecords> bankRecordsList, String path)
	{
		
		System.out.println("First 3 lines of object to be serialized:");
		
		for (	int i = 0;
				i < (bankRecordsList.size() >= 3 ? 3 : bankRecordsList.size());
				i++)
		{
			System.out.println(bankRecordsList.get(i).toString());
		}
		
		System.out.printf("Serializing object to this filepath: '%s'",path);
		
		
		
		ObjectOutputStream oos = null;
		try
		{
			oos = new ObjectOutputStream(new FileOutputStream(path));
			
			for(BankRecords person: bankRecordsList)
			{
				oos.writeObject(person);
			}
			
		}
		catch (IOException ioe)
		{
			System.err.printf("Error opening file at '%s'\n",path);
			ioe.printStackTrace();
		}
		catch (NoSuchElementException nsee)
		{
			System.err.printf("halp! \n");
			nsee.printStackTrace();
		}
		finally
		{
			if(oos != null)
			{
				try
				{
					oos.close();
				}
				catch (IOException e)
				{
					System.err.printf("Failed closing file at '%d'\n",path);
					e.printStackTrace();
				}
			}
		}
	}
	
	public static ArrayList<BankRecords> deserialize(String path)
	{
		ArrayList<BankRecords> ret = new ArrayList<>();
		
		ObjectInputStream ois = null;
		
		try
		{
			ois = new ObjectInputStream(new FileInputStream(path));
			
			while(true)
			{
				BankRecords br = (BankRecords) ois.readObject();
				ret.add(br);
			}
			
		}
		catch (EOFException eofe)
		{
			//end o' list
			return ret;
		}
		catch (IOException ioe)
		{
			System.out.printf("Error opening file '%s' for reading!",path);
			ioe.printStackTrace();
		}
		catch (ClassNotFoundException cnfe)
		{
			System.out.println("Object creation failed! Our list so far:");
			System.out.println(ret.toString());
			cnfe.printStackTrace();
		}

		finally
		{
			if(ois != null)
			{
				try
				{
					ois.close();
				}
				catch (IOException ioe2)
				{
					System.out.printf("Error closing file '%s'!",path);
					ioe2.printStackTrace();
				}
			}
		}
		return ret;
	}
}
