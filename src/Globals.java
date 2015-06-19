import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;


public class Globals {

	public static final String root = "C:\\Users\\User\\Documents\\kaggle\\walmart\\";
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static Double readDouble(String str){
		if (str.equals("NA"))
			return null;
		if (str.equals("FALSE"))
			return 0.0;
		if (str.equals("TRUE"))
			return 1.0;
		return Double.valueOf(str);	
	}
	public static Date readDate(String str) throws ParseException{
		return sdf.parse(str);
	}

	public static double predict(OLSMultipleLinearRegression regression, double[] x){
		if(regression == null) {
			throw new IllegalArgumentException("Parameter regression must not be null");
		}
		double prediction = 0.0d;
		double[] beta = regression.estimateRegressionParameters();

		// intercept at beta[0]
		prediction = beta[0];
		for(int i = 1; i < beta.length; i++){
			prediction += beta[i] * x[i - 1];
		}

		return prediction;
	}
}

