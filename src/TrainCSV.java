import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class TrainCSV {
	private final int STORE 		= 0;
	private final int DEPT 			= 1;
	private final int DATE			= 2;
	private final int WEEKLY_SALES	= 3;
	private final int ISHOLIDAY		= 4;
	
	ArrayList<String> 	store;
	ArrayList<String>	dept;
	ArrayList<Date>		date;
	ArrayList<Double>	weekly_Sales;
	ArrayList<Double>	isHoliday;
	
	public TrainCSV(String fileName) throws IOException, ParseException{
		store		 = new ArrayList<String>();
		dept		 = new ArrayList<String>();
		date 		 = new ArrayList<Date>();
		weekly_Sales = new ArrayList<Double>();
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
		weekly_Sales.add(	Globals.readDouble(	split[WEEKLY_SALES]));
		isHoliday.add(		Globals.readDouble(	split[ISHOLIDAY]));
	}
	
}
