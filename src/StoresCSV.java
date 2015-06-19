import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;


public class StoresCSV {


	private final int STORE = 0;
	private final int TYPE 	= 1;
	private final int SIZE	= 2;
	
	
	ArrayList<String> 	store;
	ArrayList<String>	type;
	ArrayList<Double>	size;
	
	public StoresCSV(String fileName) throws IOException, ParseException{
		store		 = new ArrayList<String>();
		type		 = new ArrayList<String>();
		size		 = new ArrayList<Double>();
		
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = br.readLine();
		while ((line = br.readLine()) != null) {
			this.inputLine(line);
		}
		br.close();
	}
	

	public void inputLine(String line) throws ParseException{

		String [] split = line.split(",");

		store.add(						split[STORE]);
		type.add(						split[TYPE]);
		size.add(	Globals.readDouble(	split[SIZE]));

	}

	
}
