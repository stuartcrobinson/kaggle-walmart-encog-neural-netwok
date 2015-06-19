import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;


public class FeaturesCSV {

	private final int STORE 		= 0;
	private final int DATE 			= 1;
	private final int TEMPERATURE	= 2;
	private final int FUEL_PRICE 	= 3;
	private final int MARKDOWN1 	= 4;
	private final int MARKDOWN2 	= 5;
	private final int MARKDOWN3 	= 6;
	private final int MARKDOWN4 	= 7;
	private final int MARKDOWN5 	= 8;
	private final int CPI 			= 9;
	private final int UNEMPLOYMENT 	= 10;
	private final int ISHOLIDAY 	= 11;	

	ArrayList<String> 		store		 = new ArrayList<String>();
	ArrayList<Date>			date		 = new ArrayList<Date>();  
	ArrayList<Double>		temperature	 = new ArrayList<Double>();
	ArrayList<Double>		fuel_Price	 = new ArrayList<Double>();
	ArrayList<Double>		markDown1	 = new ArrayList<Double>();
	ArrayList<Double>		markDown2	 = new ArrayList<Double>();
	ArrayList<Double>		markDown3	 = new ArrayList<Double>();
	ArrayList<Double>		markDown4	 = new ArrayList<Double>();
	ArrayList<Double>		markDown5	 = new ArrayList<Double>();
	ArrayList<Double>		cpi		 	 = new ArrayList<Double>();
	ArrayList<Double>		unemployment = new ArrayList<Double>();
	ArrayList<Double>		isHoliday	 = new ArrayList<Double>();


	public FeaturesCSV(String fileName) throws IOException, ParseException{
	
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
		date.add(			Globals.readDate(	split[DATE]));
		temperature.add(	Globals.readDouble(	split[TEMPERATURE]));
		fuel_Price.add(		Globals.readDouble(	split[FUEL_PRICE]));
		markDown1.add(		Globals.readDouble(	split[MARKDOWN1]));
		markDown2.add(		Globals.readDouble(	split[MARKDOWN2]));
		markDown3.add(		Globals.readDouble(	split[MARKDOWN3]));
		markDown4.add(		Globals.readDouble(	split[MARKDOWN4]));
		markDown5.add(		Globals.readDouble(	split[MARKDOWN5]));
		cpi.add(			Globals.readDouble(	split[CPI]));
		unemployment.add(	Globals.readDouble(	split[UNEMPLOYMENT]));
		isHoliday.add(		Globals.readDouble(	split[ISHOLIDAY]));

	}



}
