import java.util.ArrayList;


public class Labels {

	//30
	/*
	flat[r*cols + 1 ] 	= size[r+1];
	flat[r*cols + 2 ] 	= temperature[r+1];
	flat[r*cols + 3 ] 	= tempImprovement[r+1];
	flat[r*cols + 4 ] 	= hotness[r+1];
	flat[r*cols + 5 ] 	= coldness[r+1];
	flat[r*cols + 6] 	= fuel_Price[r+1];
	flat[r*cols + 7 ] 	= gasChange[r+1];
	flat[r*cols + 8 ] 	= markDown1[r+1];
	flat[r*cols + 9 ] 	= markDown2[r+1];
	flat[r*cols + 10 ] 	= markDown3[r+1];
	flat[r*cols + 11] 	= markDown4[r+1];
	flat[r*cols + 12] 	= markDown5[r+1];
	flat[r*cols + 13] 	= markDown1_isNA[r+1];
	flat[r*cols + 14] 	= markDown2_isNA[r+1];
	flat[r*cols + 15] 	= markDown3_isNA[r+1];
	flat[r*cols + 16] 	= markDown4_isNA[r+1];
	flat[r*cols + 17] 	= markDown5_isNA[r+1];
	flat[r*cols + 18] 	= cpi[r+1];
	flat[r*cols + 19] 	= cpiChange[r+1];
	flat[r*cols + 20] 	= unemployment[r+1];
	flat[r*cols + 21] 	= unemploymentChange[r+1];
	flat[r*cols + 22] 	= econ_isNA[r+1];
	flat[r*cols + 23] 	= isSuperBowl[r+1];
	flat[r*cols + 24] 	= isTGiving[r+1];
	flat[r*cols + 25] 	= isLaborDay[r+1];
	flat[r*cols + 26] 	= isChristmas[r+1];
	flat[r*cols + 27] 	= isAfterHoliday[r+1];
	flat[r*cols + 28] 	= isBeforeHoliday[r+1];*/
	public static  ArrayList<String> labels() {

		ArrayList<String> labels = new ArrayList<String>();	

		//		labels.add(		"_store"					);
		//		labels.add(		"_dept"						);
		//		labels.add(		"_date"						);
		//		labels.add(		"weekly_Sales"				);
		labels.add(		"intcpt_or_wklySales"		);
		labels.add(		"size"						);
		labels.add(		"tempImprovement"			);
		labels.add(		"hotness"					);
		labels.add(		"coldness"					);
		labels.add(		"fuel_Price"				);
		labels.add(		"gasChange"					);
//		labels.add(		"markDown1"					);
//		labels.add(		"markDown2"					);
//		labels.add(		"markDown3"					);
//		labels.add(		"markDown4"					);
//		labels.add(		"markDown5"					);
//		labels.add(		"markDown1_isNA"			);
//		labels.add(		"markDown2_isNA"			);
//		labels.add(		"markDown3_isNA"			);
//		labels.add(		"markDown4_isNA"			);
//		labels.add(		"markDown5_isNA"			);
		labels.add(		"cpi"						);
		labels.add(		"cpiChange"					);
		labels.add(		"unemployment"				);
		labels.add(		"unemploymentChange"		);
		labels.add(		"isSuperBowl"				);
		labels.add(		"isTGiving"					);
		labels.add(		"isLaborDay"				);
		labels.add(		"isChristmas"				);
		labels.add(		"isAfterHoliday"			);
		labels.add(		"isBeforeHoliday"			);
//		labels.add(		"type_A"					);
//		labels.add(		"type_B"					);
//		labels.add(		"type_C"					);

		for (int i = 0; i < Data.numTypes; i++){
			labels.add("isTypes_" + Data.typesList.get(i));
		}

		for (int i = 0; i < Data.numStores; i++){
			labels.add("isStores_" + Data.storesList.get(i));
		}
//		for (int i = 0; i < Data.numDepts; i++){
//			labels.add("isDepts_" + Data.deptsList.get(i));
//		}
		
		return labels;

	}
}
