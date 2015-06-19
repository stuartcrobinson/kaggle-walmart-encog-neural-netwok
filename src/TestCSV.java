import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class TestCSV {
	private final int STORE 		= 0;
	private final int DEPT 			= 1;
	private final int DATE			= 2;
	private final int ISHOLIDAY		= 3;
	
	ArrayList<String> 	store;
	ArrayList<String>	dept;
	ArrayList<Date>		date;
	ArrayList<Double>	isHoliday;
	
	public TestCSV(String fileName) throws IOException, ParseException{
		store		 = new ArrayList<String>();
		dept		 = new ArrayList<String>();
		date 		 = new ArrayList<Date>();
		isHoliday	 = new ArrayList<Double>();
		
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = br.readLine();
		while ((line = br.readLine()) != null) {
			this.inputLine(line);
		}
		br.close();
		
	}
	
	public void inputLine(String line) throws ParseException{
		String [] split = line.split(",");

		store.add(								split[STORE]);
		dept.add(								split[DEPT]);
		date.add(			Globals.readDate(	split[DATE]));
		isHoliday.add(		Globals.readDouble(	split[ISHOLIDAY]));
	}
	
}
