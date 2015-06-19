import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;


public class Data {

	//start with TrainCSV
	//then add in data from Features and Stores

	String []		_store;
	String []		_type;
	String []		_dept;

	Date []			_date;
	double []		weekly_Sales;
	double []		isHoliday;

	double []		size;

	double []		temperature;
	double []		fuel_Price;
	double []		markDown1;
	double []		markDown2;
	double []		markDown3;
	double []		markDown4;
	double []		markDown5;

	double []		cpi;
	double []		unemployment;

	double []		markDown1_isNA;
	double []		markDown2_isNA;
	double []		markDown3_isNA;
	double []		markDown4_isNA;
	double []		markDown5_isNA;

	double []		econ_isNA;
	//stuarts                   
	double []		hotness;
	double []		coldness;
	double []		isSuperBowl;
	double []		isTGiving;
	double []		isLaborDay;
	double []		isChristmas;

	double []		isAfterHoliday;
	double []		isBeforeHoliday;
	double []		isBBeforeHoliday;

	/* degrees the temperature has moved TOWARDS 75*/
	double []		tempImprovement;
	double []		gasChange;
	double []		cpiChange;
	double []		unemploymentChange;

	static int numDepts = 0;
	static int numStores = 0;
	static int numTypes = 0;
	static ArrayList<Integer> deptsList;
	static ArrayList<Integer> storesList;
	static ArrayList<String> typesList;



	double [][]		isTypes;
	double [][]		isStores;
	double [][]		isDepts;

	public Data(TrainCSV train, StoresCSV stores, FeaturesCSV features) throws IOException, ParseException{
		//		store dept date weeklysales isholiday

		//store and dept.  IGNORE index 0!  start at 1 so index 1 matches store 1.  so make size 1 extra big.

		//45 stores, 100 departments

		int n = train.date.size();

		_store = new String[n];
		_type = new String[n];
		_dept = new String[n];
		_date = new Date [n];

		for (int r = 0; r < n; r++){
			_store[r] = train.store.get(r);
			_dept[r] = train.dept.get(r);
			_date[r] = train.date.get(r);
		}
		//
		//		isStores = new double [46][n];		//this is stupid.  i'm leaving first cell empty so index can equal store name
		//
		//		for (int s = 1; s <= 45; s++){
		//			for (int r = 0; r < n; r++){
		//				isStores[s][r] = Integer.valueOf(train.store.get(r)) == s ? 1.0 : 0.0;
		//			}
		//		}

		Set<Integer> storesSet = new TreeSet<Integer>();
		for (String store : train.store)
			storesSet.add(Integer.valueOf(store));
		storesList = Lists.newArrayList(storesSet);
		numStores = storesSet.size();
		isStores = new double [numStores][n];

		for (int s = 0; s < numStores; s++){
			Integer storeName = storesList.get(s);
			for (int r = 0; r < n; r++){
				isStores[s][r] = Integer.valueOf(train.store.get(r)) == storeName ? 1.0 : 0.0;
			}
		}




		Set<Integer> deptsSet = new TreeSet<Integer>();
		for (String dept : train.dept)
			deptsSet.add(Integer.valueOf(dept));
		deptsList = Lists.newArrayList(deptsSet);
		numDepts = deptsSet.size();
		isDepts = new double [numDepts][n];

		for (int d = 0; d < numDepts; d++){
			Integer deptName = deptsList.get(d);
			for (int r = 0; r < n; r++){
				isDepts[d][r] = Integer.valueOf(train.dept.get(r)) == deptName ? 1.0 : 0.0;
			}
		}

		//		//testing -- looking for singularities in isDepts
		//		for (int i = 0; i < numDepts; i++){
		//			double init = isDepts[i][0];
		////			boolean noChange = true;
		//			System.out.print("dept name: " + deptsList.get(i) + ". index: " + i);
		//			for (int j = 0; j < n; j++){
		//				if (isDepts[i][j] != init){
		////					noChange = false;
		//					System.out.print(". new value");
		//					break;
		//				}
		//			}
		//			System.out.print(".  no new change!  the singularity has been discovered!");
		//			System.out.println();
		//		}
		//		System.exit(1);

		weekly_Sales	= Doubles.toArray( train.weekly_Sales );
		isHoliday		= Doubles.toArray( train.isHoliday) ;



		Set<String> typesSet = new TreeSet<String>();
		for (int r = 0; r < n; r++){

			String store = train.store.get(r);
			int storescsvIndex = stores.store.indexOf(store);
			String type = stores.type.get(storescsvIndex);
			_type[r] = type;
			typesSet.add(type);
		}
		typesList = Lists.newArrayList(typesSet);
		numTypes = typesSet.size();
		isTypes = new double [numTypes][n];

		for (int t = 0; t < numTypes; t++){
			String type = typesList.get(t);
			//			System.out.println("t " + t + " type " + type);
			for (int r = 0; r < n; r++){

				String store = train.store.get(r);
				int storescsvIndex = stores.store.indexOf(store);
				String rowType = stores.type.get(storescsvIndex);

				isTypes[t][r] = rowType.equals(type) ? 1.0 : 0.0;

				//				if (type.equals("C")) System.out.println("C found");

			}
		}


		//		System.exit(1);
		//
		//		isTypes = new double [3][n];
		//
		//		for (int t = 0; t < 3; t++){
		//			for (int r = 0; r < n; r++){
		//
		//				String store = train.store.get(r);
		//				int storescsvIndex = stores.store.indexOf(store);
		//				String type = stores.type.get(storescsvIndex);
		//
		//				int typeInt;
		//				if (type.equals("A"))
		//					typeInt = 0;
		//				else if (type.equals("B"))
		//					typeInt = 1;
		//				else// (type.equals("C"))
		//					typeInt = 2;
		//
		//				isTypes[t][r] = typeInt == t ? 1.0 : 0.0;
		//			}
		//		}

		size = new double[n];
		// for each store in train.store, get store name.  then get store index in stores.store.  then get store size

		for (int r = 0; r < n; r++){
			String s = train.store.get(r);
			int storesIndex = stores.store.indexOf(s);
			size[r] = stores.size.get(storesIndex);			
		}

		//temp through unemployment from FeaturesCSV

		temperature		= new double[n];
		fuel_Price 		= new double[n]; 	
		markDown1 		= new double[n]; 	
		markDown2 		= new double[n]; 	
		markDown3 		= new double[n]; 	
		markDown4 		= new double[n]; 	
		markDown5 		= new double[n]; 	
		cpi 			= new double[n]; 			
		unemployment	= new double[n]; 	

		markDown1_isNA = new double[n];
		markDown2_isNA = new double[n];
		markDown3_isNA = new double[n];
		markDown4_isNA = new double[n];
		markDown5_isNA = new double[n];

		econ_isNA			= new double[n];

		for (int fr = 0; fr < features.date.size(); fr++){

			System.out.println("features row " + fr + " of " + features.date.size());

			String store	= features.store.get(fr);
			Date date		= features.date.get(fr);

			double _temperature 	= features.temperature.get(fr);
			double _fuel_Price 		= features.fuel_Price.get(fr);
			Double _markDown1 		= features.markDown1.get(fr);
			Double _markDown2 		= features.markDown2.get(fr);
			Double _markDown3 		= features.markDown3.get(fr);
			Double _markDown4 		= features.markDown4.get(fr);
			Double _markDown5 		= features.markDown5.get(fr);
			Double _cpi 			= features.cpi.get(fr);
			Double _unemployment 	= features.unemployment.get(fr);

			//could be much faster without going thorugh all 293842394 rows every time
			for (int r = 0; r < n; r++){
				if (train.store.get(r).equals(store) && train.date.get(r).equals(date)){
					temperature[r]	= _temperature;
					fuel_Price[r] 	= _fuel_Price;
					markDown1[r] 	= _markDown1 	== null ? -1 : _markDown1;
					markDown2[r] 	= _markDown2 	== null ? -1 : _markDown2;
					markDown3[r] 	= _markDown3 	== null ? -1 : _markDown3;
					markDown4[r] 	= _markDown4 	== null ? -1 : _markDown4;
					markDown5[r] 	= _markDown5 	== null ? -1 : _markDown5;
					cpi[r] 			= _cpi 		 	== null ? -1 : _cpi;               
					unemployment[r] = _unemployment == null ? -1 : _unemployment;
				}
			}
		}

		hotness				= new double[n];
		coldness			= new double[n];
		isSuperBowl			= new double[n];
		isLaborDay			= new double[n];
		isTGiving			= new double[n];
		isChristmas			= new double[n];

		isAfterHoliday		= new double[n];
		isBeforeHoliday		= new double[n];
		isBBeforeHoliday		= new double[n];

		tempImprovement		= new double[n];
		gasChange			= new double[n];
		cpiChange			= new double[n];
		unemploymentChange	= new double[n];


		Calendar cal = Calendar.getInstance();		

		for (int r = 0; r < n; r++){
			System.out.println("final data row " +r + " of " + n);
			hotness[r] 		= (temperature[r] - 75 > 0 ? temperature[r] - 75 : 0) ; //todo null pointer exception!
			coldness[r] 	= (75 - temperature[r] > 0 ? 75 - temperature[r] : 0);


			cal.setTime(_date[r]);
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);

			if ( ((month == Calendar.JANUARY && day > 15) || month == Calendar.FEBRUARY) && isHoliday[r] == 1)
				isSuperBowl[r] = 1;

			if ( (month == Calendar.AUGUST || month == Calendar.SEPTEMBER) && isHoliday[r] == 1)
				isLaborDay[r] = 1;

			if ( (month == Calendar.NOVEMBER || (month == Calendar.DECEMBER && day < 10) ) && isHoliday[r] == 1)
				isTGiving[r] = 1;

			if ( ((month == Calendar.DECEMBER && day > 12) || (month == Calendar.JANUARY  && day < 10)) && isHoliday[r] == 1)
				isChristmas[r] = 1;

			if (r > 0 && isHoliday[r-1] == 1)
				isAfterHoliday[r] = 1;
			if (r < n-1 && isHoliday[r+1] == 1)
				isBeforeHoliday[r] = 1;
			if (r < n-2 && isHoliday[r+2] == 1)
				isBBeforeHoliday[r] = 1;


			econ_isNA[r] 	= unemployment[r] 	== -1 ? 1 : 0;		//NA when CPI is na

			if (r > 0 && econ_isNA[r] != 1 && econ_isNA[r-1] != 1){
				double uncomfortableness_before = Math.abs(temperature[r-1] - 75);
				double uncomfortableness_now = Math.abs(temperature[r] - 75);
				tempImprovement[r] = uncomfortableness_before - uncomfortableness_now;

				gasChange[r] = 			fuel_Price[r] - 	fuel_Price[r-1];
				cpiChange[r] = 			cpi[r] - 			cpi[r-1];
				unemploymentChange[r] = unemployment[r] - 	unemployment[r-1];

			}

			markDown1_isNA[r] = markDown1[r] == -1 ? 1 : 0;
			markDown2_isNA[r] = markDown2[r] == -1 ? 1 : 0;
			markDown3_isNA[r] = markDown3[r] == -1 ? 1 : 0;
			markDown4_isNA[r] = markDown4[r] == -1 ? 1 : 0;
			markDown5_isNA[r] = markDown5[r] == -1 ? 1 : 0;


		}

	}

	public Data(TestCSV test, StoresCSV stores, FeaturesCSV features) throws IOException, ParseException{
		//		store dept date weeklysales isholiday

		//store and dept.  IGNORE index 0!  start at 1 so index 1 matches store 1.  so make size 1 extra big.

		//45 stores, 100 departments

		int n = test.date.size();

		_store = new String[n];
		_type = new String[n];
		_dept = new String[n];
		_date = new Date [n];

		for (int r = 0; r < n; r++){
			_store[r] = test.store.get(r);
			_dept[r] = test.dept.get(r);
			_date[r] = test.date.get(r);
		}
		//
		//		isStores = new double [46][n];		//this is stupid.  i'm leaving first cell empty so index can equal store name
		//
		//		for (int s = 1; s <= 45; s++){
		//			for (int r = 0; r < n; r++){
		//				isStores[s][r] = Integer.valueOf(train.store.get(r)) == s ? 1.0 : 0.0;
		//			}
		//		}

		Set<Integer> storesSet = new TreeSet<Integer>();
		for (String store : test.store)
			storesSet.add(Integer.valueOf(store));
		storesList = Lists.newArrayList(storesSet);
		numStores = storesSet.size();
		isStores = new double [numStores][n];

		for (int s = 0; s < numStores; s++){
			Integer storeName = storesList.get(s);
			for (int r = 0; r < n; r++){
				isStores[s][r] = Integer.valueOf(test.store.get(r)) == storeName ? 1.0 : 0.0;
			}
		}




		Set<Integer> deptsSet = new TreeSet<Integer>();
		for (String dept : test.dept)
			deptsSet.add(Integer.valueOf(dept));
		deptsList = Lists.newArrayList(deptsSet);
		numDepts = deptsSet.size();
		isDepts = new double [numDepts][n];

		for (int d = 0; d < numDepts; d++){
			Integer deptName = deptsList.get(d);
			for (int r = 0; r < n; r++){
				isDepts[d][r] = Integer.valueOf(test.dept.get(r)) == deptName ? 1.0 : 0.0;
			}
		}

		//		//testing -- looking for singularities in isDepts
		//		for (int i = 0; i < numDepts; i++){
		//			double init = isDepts[i][0];
		////			boolean noChange = true;
		//			System.out.print("dept name: " + deptsList.get(i) + ". index: " + i);
		//			for (int j = 0; j < n; j++){
		//				if (isDepts[i][j] != init){
		////					noChange = false;
		//					System.out.print(". new value");
		//					break;
		//				}
		//			}
		//			System.out.print(".  no new change!  the singularity has been discovered!");
		//			System.out.println();
		//		}
		//		System.exit(1);

		//		weekly_Sales	= Doubles.toArray( test.weekly_Sales );
		isHoliday		= Doubles.toArray( test.isHoliday) ;



		Set<String> typesSet = new TreeSet<String>();
		for (int r = 0; r < n; r++){

			String store = test.store.get(r);
			int storescsvIndex = stores.store.indexOf(store);
			String type = stores.type.get(storescsvIndex);
			_type[r] = type;
			typesSet.add(type);
		}
		typesList = Lists.newArrayList(typesSet);
		numTypes = typesSet.size();
		isTypes = new double [numTypes][n];

		for (int t = 0; t < numTypes; t++){
			String type = typesList.get(t);
			//			System.out.println("t " + t + " type " + type);
			for (int r = 0; r < n; r++){

				String store = test.store.get(r);
				int storescsvIndex = stores.store.indexOf(store);
				String rowType = stores.type.get(storescsvIndex);

				isTypes[t][r] = rowType.equals(type) ? 1.0 : 0.0;

				//				if (type.equals("C")) System.out.println("C found");

			}
		}


		//		System.exit(1);
		//
		//		isTypes = new double [3][n];
		//
		//		for (int t = 0; t < 3; t++){
		//			for (int r = 0; r < n; r++){
		//
		//				String store = train.store.get(r);
		//				int storescsvIndex = stores.store.indexOf(store);
		//				String type = stores.type.get(storescsvIndex);
		//
		//				int typeInt;
		//				if (type.equals("A"))
		//					typeInt = 0;
		//				else if (type.equals("B"))
		//					typeInt = 1;
		//				else// (type.equals("C"))
		//					typeInt = 2;
		//
		//				isTypes[t][r] = typeInt == t ? 1.0 : 0.0;
		//			}
		//		}

		size = new double[n];
		// for each store in train.store, get store name.  then get store index in stores.store.  then get store size

		for (int r = 0; r < n; r++){
			String s = test.store.get(r);
			int storesIndex = stores.store.indexOf(s);
			size[r] = stores.size.get(storesIndex);			
		}

		//temp through unemployment from FeaturesCSV

		temperature		= new double[n];
		fuel_Price 		= new double[n]; 	
		markDown1 		= new double[n]; 	
		markDown2 		= new double[n]; 	
		markDown3 		= new double[n]; 	
		markDown4 		= new double[n]; 	
		markDown5 		= new double[n]; 	
		cpi 			= new double[n]; 			
		unemployment	= new double[n]; 	

		markDown1_isNA = new double[n];
		markDown2_isNA = new double[n];
		markDown3_isNA = new double[n];
		markDown4_isNA = new double[n];
		markDown5_isNA = new double[n];

		econ_isNA			= new double[n];

		for (int fr = 0; fr < features.date.size(); fr++){

			System.out.println("features row " + fr + " of " + features.date.size());

			String store	= features.store.get(fr);
			Date date		= features.date.get(fr);

			double _temperature 	= features.temperature.get(fr);
			double _fuel_Price 		= features.fuel_Price.get(fr);
			Double _markDown1 		= features.markDown1.get(fr);
			Double _markDown2 		= features.markDown2.get(fr);
			Double _markDown3 		= features.markDown3.get(fr);
			Double _markDown4 		= features.markDown4.get(fr);
			Double _markDown5 		= features.markDown5.get(fr);
			Double _cpi 			= features.cpi.get(fr);
			Double _unemployment 	= features.unemployment.get(fr);

			//could be much faster without going thorugh all 293842394 rows every time
			for (int r = 0; r < n; r++){
				if (test.store.get(r).equals(store) && test.date.get(r).equals(date)){
					temperature[r]	= _temperature;
					fuel_Price[r] 	= _fuel_Price;
					markDown1[r] 	= _markDown1 	== null ? -1 : _markDown1;
					markDown2[r] 	= _markDown2 	== null ? -1 : _markDown2;
					markDown3[r] 	= _markDown3 	== null ? -1 : _markDown3;
					markDown4[r] 	= _markDown4 	== null ? -1 : _markDown4;
					markDown5[r] 	= _markDown5 	== null ? -1 : _markDown5;
					cpi[r] 			= _cpi 		 	== null ? -1 : _cpi;               
					unemployment[r] = _unemployment == null ? -1 : _unemployment;
				}
			}
		}

		hotness				= new double[n];
		coldness			= new double[n];
		isSuperBowl			= new double[n];
		isLaborDay			= new double[n];
		isTGiving			= new double[n];
		isChristmas			= new double[n];

		isAfterHoliday		= new double[n];
		isBeforeHoliday		= new double[n];
		isBBeforeHoliday		= new double[n];

		tempImprovement		= new double[n];
		gasChange			= new double[n];
		cpiChange			= new double[n];
		unemploymentChange	= new double[n];


		Calendar cal = Calendar.getInstance();		

		for (int r = 0; r < n; r++){
			System.out.println("final data row " +r + " of " + n);
			hotness[r] 		= (temperature[r] - 75 > 0 ? temperature[r] - 75 : 0) ; //todo null pointer exception!
			coldness[r] 	= (75 - temperature[r] > 0 ? 75 - temperature[r] : 0);


			cal.setTime(_date[r]);
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);

			if ( ((month == Calendar.JANUARY && day > 15) || month == Calendar.FEBRUARY) && isHoliday[r] == 1)
				isSuperBowl[r] = 1;

			if ( (month == Calendar.AUGUST || month == Calendar.SEPTEMBER) && isHoliday[r] == 1)
				isLaborDay[r] = 1;

			if ( (month == Calendar.NOVEMBER || (month == Calendar.DECEMBER && day < 10) ) && isHoliday[r] == 1)
				isTGiving[r] = 1;

			if ( ((month == Calendar.DECEMBER && day > 12) || (month == Calendar.JANUARY  && day < 10)) && isHoliday[r] == 1)
				isChristmas[r] = 1;

			if (r > 0 && isHoliday[r-1] == 1)
				isAfterHoliday[r] = 1;
			if (r < n-1 && isHoliday[r+1] == 1)
				isBeforeHoliday[r] = 1;
			if (r < n-2 && isHoliday[r+2] == 1)
				isBBeforeHoliday[r] = 1;


			econ_isNA[r] 	= unemployment[r] 	== -1 ? 1 : 0;		//NA when CPI is na

			if (r > 0 && econ_isNA[r] != 1 && econ_isNA[r-1] != 1){
				double uncomfortableness_before = Math.abs(temperature[r-1] - 75);
				double uncomfortableness_now = Math.abs(temperature[r] - 75);
				tempImprovement[r] = uncomfortableness_before - uncomfortableness_now;

				gasChange[r] = 			fuel_Price[r] - 	fuel_Price[r-1];
				cpiChange[r] = 			cpi[r] - 			cpi[r-1];
				unemploymentChange[r] = unemployment[r] - 	unemployment[r-1];

			}

			markDown1_isNA[r] = markDown1[r] == -1 ? 1 : 0;
			markDown2_isNA[r] = markDown2[r] == -1 ? 1 : 0;
			markDown3_isNA[r] = markDown3[r] == -1 ? 1 : 0;
			markDown4_isNA[r] = markDown4[r] == -1 ? 1 : 0;
			markDown5_isNA[r] = markDown5[r] == -1 ? 1 : 0;


		}

	}




	/**
	 * 
		"_store",
		"_dept",
		"_date",
	"weekly_Sales",
	"size",
	"temperature",
	"tempImprovement",
	"hotness",
	"coldness",
	"fuel_Price",
	"gasChange",
	"markDown1",
	"markDown2",
	"markDown3",
	"markDown4",
	"markDown5",
	"markDown1_isNA",
	"markDown2_isNA",
	"markDown3_isNA",
	"markDown4_isNA",
	"markDown5_isNA",
	"cpi",
	"cpiChange",
	"unemployment",
	"unemploymentChange",
	"econ_isNA",
	"isHoliday",
	"isSuperBowl",
	"isTGiving",
	"isLaborDay",
	"isChristmas",
	"isAfterHoliday",
	"isBeforeHoliday",
	"type_A",
	"type_B",
	"type_C",
	"store_1",
	 */
	public void writeToFileForR(String fileName) throws FileNotFoundException, UnsupportedEncodingException {

		SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
		String delim = " ";
		PrintWriter pw = new PrintWriter(fileName, "UTF-8");

		pw.println(
//				"weekly_Sales " +
						"_store " +
						"_type " +
						"_dept " +
						"date " +
						"month " +
						"size " +
						"temperature " +
						"tempImprovement " +
						"hotness " +
						"coldness " +
						"fuel_Price " +
						"gasChange " +
						"markDown1 " +
						"markDown2 " +
						"markDown3 " +
						"markDown4 " +
						"markDown5 " +
						"markDown1_isNA " +
						"markDown2_isNA " +
						"markDown3_isNA " +
						"markDown4_isNA " +
						"markDown5_isNA " +
						"markDown1p " +
						"markDown2p " +
						"markDown3p " +
						"markDown4p " +
						"markDown5p " +
						"markDown1p_isNA " +
						"markDown2p_isNA " +
						"markDown3p_isNA " +
						"markDown4p_isNA " +
						"markDown5p_isNA " +
						"markDown1pp " +
						"markDown2pp " +
						"markDown3pp " +
						"markDown4pp " +
						"markDown5pp " +
						"markDown1pp_isNA " +
						"markDown2pp_isNA " +
						"markDown3pp_isNA " +
						"markDown4pp_isNA " +
						"markDown5pp_isNA " +
						"markDown1ppp " +
						"markDown2ppp " +
						"markDown3ppp " +
						"markDown4ppp " +
						"markDown5ppp " +
						"markDown1ppp_isNA " +
						"markDown2ppp_isNA " +
						"markDown3ppp_isNA " +
						"markDown4ppp_isNA " +
						"markDown5ppp_isNA " +
						"cpi " +
						"cpiChange " +
						"unemployment " +
						"unemploymentChange " +
						"econ_isNA " +
						//						"isHoliday " +
						"isSuperBowl " +
						"isTGiving " +
						"isLaborDay " +
						"isChristmas " +
						"isAfterHoliday " +
						"isBeforeHoliday " +	
						"isBBeforeHoliday "				
				);


		int n = this._date.length;

		for (int r = 0; r < n - 5; r++){

//			pw.format("%.2f ", weekly_Sales[r+3]);
			pw.print("s" + 	_store[r+3] 						+ delim);
			pw.print(		_type[r+3]  						+ delim);
			pw.print("d" + _dept[r+3]  						+ delim);
			pw.print(Globals.sdf.format(_date[r+3])	+ delim);
			pw.print(monthFormat.format(_date[r+3]) 	+ delim);
			pw.format("%.0f ", size[r+3]);
			pw.format("%.2f ", temperature[r+3]);
			pw.format("%.2f ", tempImprovement[r+3]);
			pw.format("%.2f ", hotness[r+3]);
			pw.format("%.2f ", coldness[r+3]);
			pw.format("%.3f ", fuel_Price[r+3]);
			pw.format("%.3f ", gasChange[r+3]);
//			pw.format("%s ", markDown1[r+3] == -1 ? "NA" : String.format("%.2f", markDown1[r+3]));
//			pw.format("%s ", markDown2[r+3] == -1 ? "NA" : String.format("%.2f", markDown2[r+3]));
//			pw.format("%s ", markDown3[r+3] == -1 ? "NA" : String.format("%.2f", markDown3[r+3]));
//			pw.format("%s ", markDown4[r+3] == -1 ? "NA" : String.format("%.2f", markDown4[r+3]));
//			pw.format("%s ", markDown5[r+3] == -1 ? "NA" : String.format("%.2f", markDown5[r+3]));
			pw.format("%.2f ", markDown1[r+3]);
			pw.format("%.2f ", markDown2[r+3]);
			pw.format("%.2f ", markDown3[r+3]);
			pw.format("%.2f ", markDown4[r+3]);
			pw.format("%.2f ", markDown5[r+3]);
			pw.format("%.0f ", markDown1_isNA[r+3]);
			pw.format("%.0f ", markDown2_isNA[r+3]);
			pw.format("%.0f ", markDown3_isNA[r+3]);
			pw.format("%.0f ", markDown4_isNA[r+3]);
			pw.format("%.0f ", markDown5_isNA[r+3]);
			pw.format("%.2f ", markDown1[r+2]);
			pw.format("%.2f ", markDown2[r+2]);
			pw.format("%.2f ", markDown3[r+2]);
			pw.format("%.2f ", markDown4[r+2]);
			pw.format("%.2f ", markDown5[r+2]);
			pw.format("%.0f ", markDown1_isNA[r+2]);
			pw.format("%.0f ", markDown2_isNA[r+2]);
			pw.format("%.0f ", markDown3_isNA[r+2]);
			pw.format("%.0f ", markDown4_isNA[r+2]);
			pw.format("%.0f ", markDown5_isNA[r+2]);
			pw.format("%.2f ", markDown1[r+1]);
			pw.format("%.2f ", markDown2[r+1]);
			pw.format("%.2f ", markDown3[r+1]);
			pw.format("%.2f ", markDown4[r+1]);
			pw.format("%.2f ", markDown5[r+1]);
			pw.format("%.0f ", markDown1_isNA[r+1]);
			pw.format("%.0f ", markDown2_isNA[r+1]);
			pw.format("%.0f ", markDown3_isNA[r+1]);
			pw.format("%.0f ", markDown4_isNA[r+1]);
			pw.format("%.0f ", markDown5_isNA[r+1]);
			pw.format("%.2f ", markDown1[r+0]);
			pw.format("%.2f ", markDown2[r+0]);
			pw.format("%.2f ", markDown3[r+0]);
			pw.format("%.2f ", markDown4[r+0]);
			pw.format("%.2f ", markDown5[r+0]);
			pw.format("%.0f ", markDown1_isNA[r+0]);
			pw.format("%.0f ", markDown2_isNA[r+0]);
			pw.format("%.0f ", markDown3_isNA[r+0]);
			pw.format("%.0f ", markDown4_isNA[r+0]);
			pw.format("%.0f ", markDown5_isNA[r+0]);
			//			pw.format("%s ", cpi[r+3] 		== -1 ? "NA" : String.format("%.3f", cpi[r+3]));
			pw.format("%.3f ", cpi[r+3]);
			pw.format("%.3f ", cpiChange[r+3]);
			//			pw.format("%s ", unemployment[r+3] == -1 ? "NA" : String.format("%.3f", unemployment[r+3]));
			pw.format("%.3f ", unemployment[r+3]);
			pw.format("%.3f ", unemploymentChange[r+3]);
			pw.format("%.0f ", econ_isNA[r+3]);
			//			pw.format("%.0f ", isHoliday[r+3]);
			pw.format("%.0f ", isSuperBowl[r+3]);
			pw.format("%.0f ", isTGiving[r+3]);
			pw.format("%.0f ", isLaborDay[r+3]);
			pw.format("%.0f ", isChristmas[r+3]);
			pw.format("%.0f ", isAfterHoliday[r+3]);
			pw.format("%.0f ", isBeforeHoliday[r+3]);
			pw.format("%.0f ", isBBeforeHoliday[r+3]);


			pw.print("\n");
		}
		pw.close();

	}

	public static void writeLMVars(double[] design, int nobs, int nvars, String fileName) throws FileNotFoundException, UnsupportedEncodingException {
		String c = ",";
		//178 vars
		PrintWriter pw = new PrintWriter(fileName, "UTF-8");

		for (String s : Labels.labels())
			pw.print(s + ",");

		pw.println();

		//design, nobs, nvars (not including dependent var)
		for (int i = 0; i < design.length; i++){
			double d = design[i];
			if (d - (int) d == 0)
				pw.print((int) d + c);
			else
				pw.format("%.2f,", d);

			if ((i+1) % (nvars + 1) == 0)
				pw.print("\n");
		}



		pw.close();

	}

	public double[] flatten(int nvars) {
		int cols = nvars+1;
		double [] flat = new double[size.length * cols];
		for (int r = 0, j = 0; r < size.length - 1; r++){			//skip first row.  meaningless for change values
			flat[j++] 	= weekly_Sales[r+1];
			flat[j++] 	= size[r+1];
			flat[j++] 	= tempImprovement[r+1];
			flat[j++] 	= hotness[r+1];
			flat[j++] 	= coldness[r+1];
			flat[j++] 	= fuel_Price[r+1];
			flat[j++] 	= gasChange[r+1];
			//			flat[j++] 	= markDown1[r+1];
			//			flat[j++] 	= markDown2[r+1];
			//			flat[j++] 	= markDown3[r+1];
			//			flat[j++] 	= markDown4[r+1];
			//			flat[j++] 	= markDown5[r+1];
			//			flat[j++] 	= markDown1_isNA[r+1];
			//			flat[j++] 	= markDown2_isNA[r+1];
			//			flat[j++] 	= markDown3_isNA[r+1];
			//			flat[j++] 	= markDown4_isNA[r+1];
			//			flat[j++] 	= markDown5_isNA[r+1];
			flat[j++] 	= cpi[r+1];
			flat[j++] 	= cpiChange[r+1];
			flat[j++] 	= unemployment[r+1];
			flat[j++] 	= unemploymentChange[r+1];
			flat[j++] 	= isSuperBowl[r+1];
			flat[j++] 	= isTGiving[r+1];
			flat[j++] 	= isLaborDay[r+1];
			flat[j++] 	= isChristmas[r+1];
			flat[j++] 	= isAfterHoliday[r+1];
			flat[j++] 	= isBeforeHoliday[r+1];		//26 (dependents)

			for (int i = 0; i < numTypes; i++){
				flat[j++] = isTypes[i][r+1];		//3
			}	

			//			System.out.println(isTypes[2][r+1]);
			//

			for (int i = 0; i < numStores; i++){
				flat[j++] = isStores[i][r+1];
			}	
			//			for (int i = 0; i < numDepts; i++){	
			//				flat[j++] = isDepts[i][r+1];	
			//			}	

		}
		return flat;

	}

	public double[][] inputDataForEncog(int nvars, int nobs) {

		double [][] matrix = new double[nobs-1][nvars];

		for (int r = 0; r < nobs - 1; r ++){

			int j = 0;

			matrix[r][j++] 	= size[r+1];
			matrix[r][j++] 	= tempImprovement[r+1];
			matrix[r][j++] 	= hotness[r+1];
			matrix[r][j++] 	= coldness[r+1];
			matrix[r][j++] 	= fuel_Price[r+1];
			matrix[r][j++] 	= gasChange[r+1];
			matrix[r][j++] 	= markDown1[r+1];
			matrix[r][j++] 	= markDown2[r+1];
			matrix[r][j++] 	= markDown3[r+1];
			matrix[r][j++] 	= markDown4[r+1];
			matrix[r][j++] 	= markDown5[r+1];
			matrix[r][j++] 	= markDown1_isNA[r+1];
			matrix[r][j++] 	= markDown2_isNA[r+1];
			matrix[r][j++] 	= markDown3_isNA[r+1];
			matrix[r][j++] 	= markDown4_isNA[r+1];
			matrix[r][j++] 	= markDown5_isNA[r+1];
			matrix[r][j++] 	= cpi[r+1];
			matrix[r][j++] 	= cpiChange[r+1];
			matrix[r][j++] 	= unemployment[r+1];
			matrix[r][j++] 	= unemploymentChange[r+1];
			matrix[r][j++] 	= econ_isNA[r+1];
			matrix[r][j++] 	= isSuperBowl[r+1];
			matrix[r][j++] 	= isTGiving[r+1];
			matrix[r][j++] 	= isLaborDay[r+1];
			matrix[r][j++] 	= isChristmas[r+1];
			matrix[r][j++] 	= isAfterHoliday[r+1];
			matrix[r][j++] 	= isBeforeHoliday[r+1];		//27 (dependents)

			//			for (int i = 0; i < numTypes; i++){
			//				matrix[r][j++] = isTypes[i][r+1];		//3
			//			}	
			//
			//			//			System.out.println(isTypes[2][r+1]);
			//			//
			//
			//			for (int i = 0; i < numStores; i++){
			//				matrix[r][j++] = isStores[i][r+1];
			//			}	
			//			for (int i = 0; i < numDepts; i++){	
			//				matrix[r][j++] = isDepts[i][r+1];	
			//			}	
		}
		return matrix;
	}

	public double[][] idealDataForEncog(int nobs) {

		double [][] matrix = new double[nobs-1][1];

		for (int r = 0; r < nobs - 1; r ++){
			matrix[r][0] = weekly_Sales[r+1];
		}

		return matrix;
	}



}
