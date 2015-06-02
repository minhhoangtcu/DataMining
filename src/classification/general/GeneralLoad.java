package classification.general;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import classification.ModifiedNormalization;

public class GeneralLoad {	
	static final String MPGTRAINING = "data\\mpgTrainingSet.txt";
	static final String MPGTEST = "data\\mpgTestSet.txt";
	static final String IRISTRAINING = "data\\irisTrainingSet.data";
	static final String IRISTEST = "data\\irisTestSet.data";
	static final String ATHLETETRAINING = "data\\athletesTrainingSet.txt";
	static final String ATHLETETEST = "data\\athletesTestSet.txt";
	
	private Item[] items;
	private double[][] values;
	private double[] medians;
	private double[] absoluteSD;
	
	//UNCOMMENT FOR TEST
	public static void main(String[] args) {
		GeneralLoad load = new GeneralLoad(MPGTRAINING);
		System.out.println("Medians: " + Arrays.toString(load.getMedian()));
		System.out.println("Absolute SD: " + Arrays.toString(load.getAbsoluteSD()));
	}
	
	public GeneralLoad(String fileDir) {
		Item[] allItems = loadFile(fileDir);
		double[][] values = setValues(allItems);
		setMedian(values);
		setAbsoluteSD(values);
	}

	/*
	 * Take a file directory and try to read through the file.
	 * The method is going to read the first line and set the values into the array.
	 * Then, it will read through the rest of the lines. On each line, check each index with the values of the first line. If it is an attribute("num") then put it into an arrayList. 
	 * @param a file directory
	 * @return an array with all items initialized.
	 */
	public Item[] loadFile(String fileDir) {
		BufferedReader reader = null;
		ArrayList<Item> tempList = new ArrayList<Item>();
		
		try {
			reader = new BufferedReader(new FileReader(fileDir));
			
			// Inspect the first line. We will see how many attributes there are to wrap the data into our items
			String firstLine = reader.readLine();
			String[] types = firstLine.split("\t");
			
			// Reader through the rest of the file and init items
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split("\t");
				String classification = null;
				ArrayList<Double> attributes = new ArrayList<Double>();
				ArrayList<String> ignores = new ArrayList<String>();
				
				for (int i = 0; i < fields.length; i++) {
					if (types[i].equals("class"))
						classification = fields[i];
					else if (types[i].equals("num")) {
						double tempAttribute = Double.parseDouble(fields[i]); 
						attributes.add(tempAttribute);
					}
					else if (types[i].equals("comment"))
						ignores.add(fields[i]);
				}
				tempList.add(new Item(classification,
									(convertToDouble(attributes)),
									ignores.toArray(new String[ignores.size()])));
			}
		}
		catch (FileNotFoundException e) {
			System.err.println("Cannot find traning file");
			e.printStackTrace();
		}
		catch (IOException e) {
			System.err.println("File corrupted");
			e.printStackTrace();
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				System.err.println("Cannot close file");
				e.printStackTrace();
			}
		}
		items = tempList.toArray(new Item[tempList.size()]);
		return items;
	}
	
	private double[][] setValues(Item[] input) {
		int numberOfAttributes = input[0].getAttributes().length; // Get the number of attributes by inspecting the first item in the array
		int numberOfItems = input.length;
		
		// Put all the attributes of all items into an multidimensional array for computational purpose. 
		values = new double[numberOfAttributes][numberOfItems];
		for (int i = 0; i < numberOfItems; i++) {
			for (int j = 0; j < numberOfAttributes; j++) {
				values[j][i] = input[i].getAttributes()[j];
			}
		}
		return values;
	}
	
	private void setMedian(double[][] values) {
		ModifiedNormalization normal = new ModifiedNormalization();
		int numberOfAttributes = values.length;
		medians = new double[numberOfAttributes]; 
		for (int i = 0; i < numberOfAttributes; i++) {
			medians[i] = normal.getMedian(values[i]);
		}
	}
	
	private void setAbsoluteSD(double[][] values) {
		ModifiedNormalization normal = new ModifiedNormalization();
		int numberOfAttributes = values.length;
		absoluteSD = new double[numberOfAttributes]; 
		for (int i = 0; i < numberOfAttributes; i++) {
			absoluteSD[i] = normal.getAbsoluteSD(values[i]);
		}
	}
	
	public double[] getMedian() {
		return medians;
	}
	
	public double[] getAbsoluteSD() {
		return absoluteSD;
	}
	
	public Item[] getItems() {
		return items;
	}
	
	public double[][] getValues() {
		return values;
	}
	
	public void printValues() {
		System.out.println(Arrays.deepToString(values));
	}
	
	
	private double[] convertToDouble(ArrayList<Double> input) {
		Double[] convertedArray = input.toArray(new Double[input.size()]);
		double[] unboxConvertedArray = Stream.of(convertedArray).mapToDouble(Double::doubleValue).toArray();
		return unboxConvertedArray;
	}
	
}
