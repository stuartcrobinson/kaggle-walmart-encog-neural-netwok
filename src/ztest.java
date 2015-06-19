import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import com.google.common.collect.Lists;
import com.google.common.collect.ObjectArrays;


public class ztest {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {

System.out.format("%d\n",  4);
		int [] one = new int []{1, 2, 3, 4};
		int [] two = new int []{1, 2, 3, 4};
		int [] three = new int []{1, 2, 3, 4};
		
		int [][] pat = new int[3][];
		pat[0] = one;
		
		System.out.println(pat[0][2]);
		
		
		// Get a DescriptiveStatistics instance
		DescriptiveStatistics stats = new DescriptiveStatistics();

		// Add the data from the array
		for( int i = 0; i < three.length; i++) {
			stats.addValue(three[i]);
		}

		// Compute some statistics
		double mean = stats.getMean();
		double std = stats.getStandardDeviation();
		double median = stats.getPercentile(50);
		
		
		System.exit(1);
		
		Set<String> deptsSet = new TreeSet<String>();
		deptsSet.add("hi 7");
		deptsSet.add("hi 3");
		deptsSet.add("hi 7");
		deptsSet.add("hi 45");
		deptsSet.add("hi 4");
		deptsSet.add("hi 8");
		deptsSet.add("hi 2");
		deptsSet.add("hi 8");
		deptsSet.add("hi 5");
		deptsSet.add("hi 0");
		deptsSet.add("hi 8");
		deptsSet.add("hi 1");
		deptsSet.add("hi 5");
		ArrayList<String> deptsList = Lists.newArrayList(deptsSet);
		System.out.println(deptsList);



		SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
		
//		Date date = new Date();
//		System.out.println(month.format(date));

		//
		//		int j = 0;
		//		System.out.println(j++);
		//		System.out.println(j);
		//		
		//		double d = 1.00;
		//		
		//		System.out.println((int) d + " hi");
		//		
		//		Date date = Globals.sdf.parse("2012-02-23");
		//		
		//
		//		Calendar cal = Calendar.getInstance();		
		//
		//		cal.setTime(date);
		//		int month = cal.get(Calendar.MONTH);
		//		int day = cal.get(Calendar.DAY_OF_MONTH);
		//		
		//		System.out.println(day);
		//		

		//		String[] both = ObjectArrays.concat(first, second, type);

		//		int[] intArray = { 1, 2, 3, 4, 5 };
		//		int[] intArray2 = { 6, 7, 8, 9, 10 };
		//		// Apache Commons Lang library
		//		int[] combinedIntArray = ArrayUtils.addAll(intArray, intArray2);
		//		
		//		System.out.println(combinedIntArray);
		//		for (int i : combinedIntArray){
		//			System.out.println(i);
		//		}
		//		
		//		String [] ar1 = {"one", "two"};
		//		String [] ar2 = {"three", "four", "five"};
		//		String [] ar3 = {"3one", "3two", "3four", "3five"};
		//		String [] ar4 = {"4one", "4two"};
		//		
		//		
		//		Serializable[] both = ArrayUtils.addAll(ar1, ar2, ar3);
		//		
		//
		//		for (Serializable i : both){
		//			System.out.println(i);
		//		}

		//		OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
		//		double[] y = new double[]{11.0, 12.0, 13.0, 14.0, 15.0, 16};
		//		double[][] x = new double[6][];
		//		x[0] = new double[]{11,		10,		0,		0};
		//		x[1] = new double[]{11,		9,		0,		0};
		//		x[2] = new double[]{13,		80,		0,		0};
		//		x[3] = new double[]{13,		7,		4,		0};
		//		x[4] = new double[]{15,		16,		0,		5};
		//		x[5] = new double[]{16,		5,		0,		0};   
		//        regression.setNoIntercept(true);       
		//		regression.newSampleData(y, x);
		//
		//		double errors [] = regression.estimateRegressionParametersStandardErrors();
		//		
		//		//Get regression parameters and diagnostics:
		//		double[] beta = regression.estimateRegressionParameters();       
		//		double[] residuals = regression.estimateResiduals();
		//		double[][] parametersVariance = regression.estimateRegressionParametersVariance();
		//		double regressandVariance = regression.estimateRegressandVariance();
		//		double rSquared = regression.calculateRSquared();
		//		double sigma = regression.estimateRegressionStandardError();
		//		
		//		for (int i = 0; i < beta.length; i++){
		//			System.out.format("%6.2f  %10.5f  %6.3e%n", beta[i], residuals[i], errors[i]);
		//		}
		//		System.out.println(beta.length);
		//		System.out.println(residuals.length);

	}

}
