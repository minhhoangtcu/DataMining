package classification.general;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TenFoldClassification {
	
	public static void main(String[] args) {
		TenFoldClassification classification = new TenFoldClassification();
		//String fileDir = "data\\mpgTrainingSet.txt";
		String prefixForBuckets = "mpgBucket";
		//int numberOfFiles = 10;
		//classification.splitToNFiles(fileDir, prefixForBuckets, numberOfFiles);
		String folderDir = "data";
		classification.combineFiles(folderDir, prefixForBuckets, 0, 1);
	}
	
	/*
	 * Combine the files with the provided prefix and index to index
	 * @param the folder directory of the files
	 * @param the prefix of the files
	 * @param the index of the starting file inclusive
	 * @param the index of the ending file inclusive
	 * @return the directory to the output file
	 */
	public String combineFiles(String folderDir, String prefixForBuckets, int startIndex, int endIndex) {
		String name = prefixForBuckets + "Combined" + startIndex + endIndex;
		String fileDir = folderDir + "\\" + name;
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter(fileDir));
			String firstLine = null;
			
			for (int i = startIndex; i <= endIndex; i++) {
				String smallFileDir = folderDir + "\\" + prefixForBuckets + i;
				BufferedReader reader = new BufferedReader(new FileReader(smallFileDir));
				// Read and write and first line
				if (firstLine == null) {
					firstLine = reader.readLine();
					writer.write(firstLine);
					writer.newLine();
				}
				else if (!firstLine.equals(reader.readLine())) {
					System.err.println("All files must be in similar format to combine");
					reader.close();
					throw new IOException();
				}
				// Read the rest of the lines and write
				String line = null;
				while ((line = reader.readLine()) != null) {
					writer.write(line);
					writer.newLine();
				}
				reader.close();
			}
		} catch (FileNotFoundException e) {
			System.err.println("Cannot find such file");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("File corrupted");
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) writer.close();
			} catch (IOException e) {
				System.err.println("Cannot close provided file");
				e.printStackTrace();
			}
		}
		return fileDir;
	}
	
	/*
	 * Read a file and split the file into n number of files.
	 * It will read the first line of the dataset and find out where the classification index is.
	 * Then, it will read the rest of the file line by line. Inspecting the classification of each line.
	 * If there is already a classification in the Map, add the line into the List.
	 * else, put a new element in the map with a new list.
	 * @param directory to the file we need to split. THE FIRST LINE OF THE FILE SOUND BE IN CORRECTED FORMAT
	 * @param prefix to split the file. For example, with prefixForBuckets = "auto". The output files will be "auto0", "auto1", "auto2"
	 * @param the number of output files to split
	 */
	public void splitToNFiles(String fileDir, String prefixForBuckets, int numberOfFiles) {
		BufferedReader reader = null;
		HashMap<String, ArrayList<String>> classToLines = new HashMap<String, ArrayList<String>>(); 
		
		try {
			// Go through the file, sort the lines by their classification
			reader = new BufferedReader(new FileReader(fileDir));
			String firstLine = reader.readLine();
			String[] types = firstLine.split("\t");
			int indexOfClassification = Arrays.asList(types).indexOf("class");
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split("\t");
				String thisLineClassification = fields[indexOfClassification];
				// If new classification
				if (!classToLines.containsKey(thisLineClassification)) {
					ArrayList<String> thisClassificationList = new ArrayList<>();
					thisClassificationList.add(line);
					classToLines.put(thisLineClassification, thisClassificationList);
				}
				// If already exist
				else {
					classToLines.get(thisLineClassification).add(line);
				}
			}
			
			// Get data from map and distribute equally into buckets. First, initialize the buckets. Second, distributes into the buckets.
			List<List<String>> buckets = new ArrayList<List<String>>();
			for (int i = 0; i < numberOfFiles; i++) {
				buckets.add(new ArrayList<String>());
			}
			for (ArrayList<String> listOfLinesInSameclassification: classToLines.values()) {
				int i = 0;
				for (String lineInSameclassification: listOfLinesInSameclassification) {
					buckets.get(i).add(lineInSameclassification);
					i = (i + 1) % numberOfFiles; // Loop through 0 to numberOfFiles exclusive
				}
			}
			
			// Write to files
			int indexOfBucket = 0;
			for (List<String> linesInBucket: buckets) {
				BufferedWriter writer = new BufferedWriter(new FileWriter("data\\" + prefixForBuckets + indexOfBucket));
				writer.write(firstLine); writer.newLine();
				for (String individualLine: linesInBucket) {
					writer.write(individualLine);
					writer.newLine();
				}
				try {
					writer.close();
				} catch (Exception e) {
					System.err.println("Cannot close bucket" + indexOfBucket);
					e.printStackTrace();
				}
				indexOfBucket++;
			}
			
			// Completion announcement
			System.out.println("Spliting completed. No error detected. Files are placed in folder data");
			
		} catch (FileNotFoundException e) {
			System.err.println("Cannot find the provided file");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("File corrupted");
		} finally {
			try {
				if (reader != null) reader.close();
			} catch (IOException e) {
				System.err.println("Cannot close provided file");
				e.printStackTrace();
			}
		}
	}

}
