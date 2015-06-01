package classification.general;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GeneralLoad {	
	static final String MPGTRAINING = "data\\mpgTrainingSet.txt";
	static final String MPGTEST = "data\\mpgTestSet.txt";
	static final String IRISTRAINING = "data\\irisTrainingSet.data";
	
	//UNCOMMENT FOR TEST
	public static void main(String[] args) {
		GeneralLoad load = new GeneralLoad();
		for (Item currentA: load.loadFile(IRISTRAINING)) {
			System.out.println(currentA);
		}
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
			String firstLine = reader.readLine();
			String[] types = firstLine.split("\t");
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split("\t");
				
				String classification = null;
				ArrayList<Double> attributes = new ArrayList<Double>();
				ArrayList<String> ignores = new ArrayList<String>();
				for (int i = 0; i < fields.length; i++) {
					if (types[i].equals("class"))
						classification = fields[i];
					else if (types[i].equals("num"))
						attributes.add(Double.parseDouble(fields[i]));
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
		return tempList.toArray(new Item[tempList.size()]);
	}
	
	private double[] convertToDouble(ArrayList<Double> input) {
		Double[] convertedArray = input.toArray(new Double[input.size()]);
		double[] unboxConvertedArray = Stream.of(convertedArray).mapToDouble(Double::doubleValue).toArray();
		return unboxConvertedArray;
	}
	
}
