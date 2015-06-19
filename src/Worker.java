import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.regression.GLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
//http://alvinalexander.com/java/jwarehouse/commons-math/src/test/java/org/apache/commons/math/stat/regression/OLSMultipleLinearRegressionTest.java.shtml
import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
public class Worker {
	
	public static void main(String[] args) throws IOException, ParseException {

		FeaturesCSV 	features = 	new FeaturesCSV(Globals.root + "features.csv");		// - Copy
		StoresCSV 		stores = 	new StoresCSV(Globals.root + "stores.csv");
		TrainCSV 		train_sr = 	new TrainCSV(Globals.root + "train.csv");		// - Copy
		TestCSV 		test = 		new TestCSV(Globals.root + "test.csv");		

		System.out.println(features.store.size());
		System.out.println(stores.store.size());
		System.out.println(train_sr.store.size());
//		System.out.println(test.store.size());

		Data data = new Data(test, stores, features);
		data.writeToFileForR(Globals.root + "outPrevsTest.txt");
		
//		Data data = new Data(train_sr, stores, features);
//		data.writeToFileForR(Globals.root + "outPrevs.txt");
        

		System.out.println(Data.numTypes);
		System.out.println(Data.numStores);
		System.out.println(Data.numDepts);

		System.exit(1);
		
        int nobs = train_sr.date.size();
        int nvars = 27;// + Data.numTypes + Data.numStores + Data.numDepts;// + Data.numStores + Data.numDepts;		//100		

		double [][] XOR_INPUT = data.inputDataForEncog(nvars, nobs); 
		double [][] XOR_IDEAL = data.idealDataForEncog(nobs);

		for (int r = 0; r < 10; r++){
			for (int c = 0; c < nvars; c++){
				System.out.print(XOR_INPUT[r][c] + " ");
			}
			System.out.println();
		}
		System.exit(1);

		// create a neural network, without using a factory
		BasicNetwork network = new BasicNetwork();
		network.addLayer(new BasicLayer(null,true,nvars));
		network.addLayer(new BasicLayer(new ActivationSigmoid(),true,nvars));
//		network.addLayer(new BasicLayer(new ActivationSigmoid(),true,nvars));
		network.addLayer(new BasicLayer(new ActivationSigmoid(),false,1));
		network.getStructure().finalizeStructure();
		network.reset();

		// create training data
		MLDataSet trainingSet = new BasicMLDataSet(XOR_INPUT, XOR_IDEAL);
		
		// train the neural network
		final ResilientPropagation train = new ResilientPropagation(network, trainingSet);

		int epoch = 1;

		do {
			train.iteration();
			System.out.println("Epoch #" + epoch + " Error:" + train.getError());
			epoch++;
		} while(train.getError() > 0.01);
		train.finishTraining();

		// test the neural network
		System.out.println("Neural Network Results:");
		for(MLDataPair pair: trainingSet ) {
			final MLData output = network.compute(pair.getInput());
			System.out.println(pair.getInput().getData(0) + "," + pair.getInput().getData(1)
					+ ", actual=" + output.getData(0) + ",ideal=" + pair.getIdeal().getData(0));
		}
		
		Encog.getInstance().shutdown();
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		System.exit(1);
		
		double [] design = data.flatten(nvars); 
		
		System.out.println(design.length);


        Data.writeLMVars(design, nobs, nvars, Globals.root + "design_out.csv");
		

        OLSMultipleLinearRegression  model = new OLSMultipleLinearRegression ();
//        model.setNoIntercept(true); 
        model.newSampleData(design, nobs, nvars);

		double errors [] = model.estimateRegressionParametersStandardErrors();
//		

		double[] beta = model.estimateRegressionParameters();       
		double[] residuals = model.estimateResiduals();   
//		RealMatrix hat = model.calculateHat();
//		double[][] parametersVariance = model.estimateRegressionParametersVariance();
//		double regressandVariance = model.estimateRegressandVariance();
//		double rSquared = model.calculateRSquared();
		double sigma = model.estimateRegressionStandardError();
		
		System.out.println("results:");

		System.out.format("%14s  %13s  %8s   %s %n", "beta", "residuals", "errors", "labels");
		for (int i = 0; i < beta.length; i++){
			System.out.format("%14.4f  %13.4f  %8.3e   %s %n", beta[i], residuals[i], errors[i], Labels.labels().get(i));
		}
		System.out.println("beta length: " + beta.length);
		System.out.println("residuals length: " + residuals.length);
		System.out.println("sigma: " + sigma);
		
//		for (int r = 1; r < train.store.size(); r++){
//			
//			double [] x = new double [nvars];
//			for (int j = 0; j < nvars; j++){
//				x[j] = design[r*(nvars+1) + j + 1];
//			}
//			
//			System.out.println(Globals.predict(model, x) + "   " + train.weekly_Sales.get(r));
//			
//		}

//		System.out.println(hat.getColumnDimension());
//		System.out.println(hat.getRowDimension());
        
//		System.out.println(Labels.labels());
        
		//todo -- update the data output method.  look at the rows.  see why it's singular with depts data in there
        
		
//		System.out.println(dataForLM);
//		for (int i = 0; i < dataForLM.length; i++){
//			System.out.println(dataForLM[i]);
//		}
		
		
//		for (Date d : features.date)
//			System.out.println(Globals.sdf.format(d));
		
//		for (Double doub : features.markDown3)
//			System.out.println(doub);
		
		
	}

}
