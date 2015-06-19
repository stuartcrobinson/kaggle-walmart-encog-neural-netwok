package connectomics;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Main {


	//	static final int LEFT = 0;
	//	static final int RIGHT = 1;
	static final int WEIGHT = 0;
	static final int N_COLIGHT = 1;
	static final int N_POSTLIGHT = 2;
	static final int DIST = 3;
	static final int DIST_SQ = 4;
	static final int nvars = 5;
	static int numFluorLines;
	static int numNeurons = 1000;
	static int numConnections = (numNeurons - 1)*numNeurons;


	static String outputFolder = "C:\\Users\\User\\Documents\\kaggle\\connectomics\\";

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String fileName = "C:\\Users\\User\\Documents\\kaggle\\connectomics\\valid\\valid\\valid\\fluorescence_valid.txt";
		//"C:\\Users\\User\\Documents\\kaggle\\connectomics\\small\\fluorescence_iNet1_Size100_CC01inh.txt";
		String networkPositionsFileName = "C:\\Users\\User\\Documents\\kaggle\\connectomics\\valid\\valid\\valid\\networkPositions_valid.txt";
		//"C:\\Users\\User\\Documents\\kaggle\\connectomics\\small\\networkPositions_iNet1_Size100_CC01inh.txt";
		String networkFileName = "C:\\Users\\User\\Documents\\kaggle\\connectomics\\small\\network_iNet1_Size100_CC01inh.txt";
		double [][] fluor = readData(fileName);

		double [][][] connections = new double [numNeurons][numNeurons][nvars];

		//now get distances between neurons
		//positions[neuron index][x or y]
		double [][] positions = new double[numNeurons][2];
		BufferedReader br = new BufferedReader( new FileReader(networkPositionsFileName));
		int k = 0;
		while((fileName = br.readLine()) != null) {
			String[] result = fileName.split(",");
			double x = Double.valueOf(result[0]);
			double y = Double.valueOf(result[0]);
			positions[k][0] = x;
			positions[k][1] = y;
			k++;
		}
		for (int i = 0; i < numNeurons; i++){
			for (int j = 0; j < numNeurons; j++){
				double distance_i_j = distance(positions[i][0], positions[i][1], positions[j][0], positions[j][1]);
				double distanceSquared = distance_i_j * distance_i_j;
				connections[i][j][DIST] = distance_i_j;
				connections[j][i][DIST] = distance_i_j;
				connections[i][j][DIST_SQ] = distanceSquared;
				connections[j][i][DIST_SQ] = distanceSquared;
			}
		}
		br.close();
		System.out.println("finished saving distances");


		//		//now get weights between neurons in network
		//		//positions[neuron index][x or y]
		//		br = new BufferedReader( new FileReader(networkFileName));
		//		k = 0;
		//		while((fileName = br.readLine()) != null) {
		//			String[] result = fileName.split(",");
		//			int n1 = Integer.valueOf(result[0]);
		//			int n2 = Integer.valueOf(result[1]);
		//			int w = Integer.valueOf(result[2]);
		//			int n1_i = n1 - 1;
		//			int n2_i = n2 - 1;
		//			connections[n1_i][n2_i][WEIGHT] = w;
		//			k++;
		//		}
		//		System.out.println("finished saving weights");


		//now check out which neurons are glowing
		List<ArrayList<Integer>> allGlowers = new ArrayList<ArrayList<Integer>>();
		for (int r = 0; r < numFluorLines; r ++){
			//get names of neurons that are glowing
			ArrayList<Integer> glowers_list = new ArrayList<Integer>();

			for (int i = 0; i < numNeurons; i++){
				int neuron = i + 1;
				if (fluor[r][i] > 0.5)
					glowers_list.add(neuron);			//neurons start at 1.  index starts at 0.
			}

			allGlowers.add(glowers_list);
		}
		//now tally connection votes.
		for (int r = 0; r < allGlowers.size() - 1; r ++){
			for (Integer left : allGlowers.get(r)){
				int left_i = left - 1;
				for (Integer right : allGlowers.get(r)){
					int right_i = right - 1;
					if (left != right){
						connections[left_i][right_i][N_COLIGHT] = connections[left_i][right_i][N_COLIGHT] + 1;
					}
				}
				for (Integer right : allGlowers.get(r+1)){
					int right_i = right - 1;
					if (left != right){
						connections[left_i][right_i][N_POSTLIGHT] = connections[left_i][right_i][N_POSTLIGHT] + 1;
					}
				}
			}
		}
		String outputFileName = outputFolder + "conOut.txt";
		System.out.println("printing to "+ outputFileName);


		PrintWriter out = new PrintWriter(new FileWriter(outputFileName));
		out.println("n1 n2 w colights postlights dist distSq");
		for (int i = 0; i < numNeurons; i++){
			for (int j = 0; j < numNeurons; j++){

				out.format("%d ", i+1);
				out.format("%d ", j+1);
				out.format("%.0f ",  connections[i][j][WEIGHT]);
				out.format("%.4f ",  connections[i][j][N_COLIGHT]/numFluorLines);
				out.format("%.4f ",  connections[i][j][N_POSTLIGHT]/numFluorLines);
				out.format("%.4f ",  connections[i][j][DIST]);
				out.format("%.4f ",  connections[i][j][DIST_SQ]);
				out.print("\n");
			}
		}
		out.close();

	}



	private static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt( (x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1) );
	}



	public static double mean(double [] ar){
		double sum = 0;
		double count = ar.length;
		for (int i = 0; i < count; i++)
			sum += ar[i];
		return sum/count;
	}


	public static double[][] readData(String fileName) throws IOException {
		numFluorLines = countLines(fileName);
		System.out.println(countLines(fileName));
		BufferedReader br = new BufferedReader( new FileReader(fileName));
		int lineNumber = 0;
		double [][] data = new double [numFluorLines][];
		while((fileName = br.readLine()) != null) {
			String[] _result = fileName.split(", ");

			double [] result = new double[_result.length];
			for (int i = 0; i < _result.length; i++){
				result[i] = Double.valueOf(_result[i]);
			}

			//
			//
			//			// Get a DescriptiveStatistics instance
			//			DescriptiveStatistics stats = new DescriptiveStatistics();
			//
			//			// Add the data from the array
			//			for( int i = 0; i < result.length; i++) {
			//				stats.addValue(result[i]);
			//			}
			//
			//
			//			System.out.println(lineNumber + ".  " + stats.getMean() + ", " + stats.getMax() + ", " +  stats.getPercentile(95) + ", " + result.length);
			//			System.out.println(lineNumber + " of " + numDataLines);
			data[lineNumber] = result;
			lineNumber++;
		}
		br.close();
		System.out.println("finished reading data");
		return data;


	}









	public static int countLines(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean endsWithoutNewLine = false;
			while ((readChars = is.read(c)) != -1) {
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n')
						++count;
				}
				endsWithoutNewLine = (c[readChars - 1] != '\n');
			}
			if(endsWithoutNewLine) {
				++count;
			} 
			return count;
		} finally {
			is.close();
		}
	}
}
