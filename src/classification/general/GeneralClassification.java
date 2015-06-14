package classification.general;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.math3.ml.distance.ManhattanDistance;

import classification.Normalization;

public class GeneralClassification {
	
	static final int K = 5;
	
	/*
	 * Compute correctness of ten-fold classification on the given training and testing data
	 * @param computing mode. 0 to compute the distance between data entries with no normalization, 1 to compute with modified normalization and 2 to compute using min and max values among all entries
	 * @param classify mode. 1 to classify an item by its nearest neighbor. 2 to classify an item by its k nearest neighbor. The value k is a static value of the class.
	 * @param the directory of the training dataset
	 * @param the directory of the testing dataset
	 * @return the correctness using ten-fold classification. 
	 */
	public double computeCorrectness(int computeMode, int classifyMode, String trainingDir, String testingDir) {
		GeneralLoad loadTest = new GeneralLoad(testingDir);
		GeneralLoad loadTraining = new GeneralLoad(trainingDir);
		Item[] testItems = loadTest.getItems();
		int numberOfItems = loadTraining.getItems().length;
		double[] medians = new double[numberOfItems];
		double[] absoluteSD = new double[numberOfItems];
		double[] min = new double[numberOfItems];
		double[] max = new double[numberOfItems];
		String nameOfMode = null;
		if (computeMode == 0) {
			nameOfMode = "NO normalization";
		}
		else if (computeMode == 1) {
			medians = loadTraining.getMedian();
			absoluteSD = loadTraining.getAbsoluteSD();
			nameOfMode = "normalization with median and absolute SD";
		}
		else if (computeMode == 2) {
			min = loadTraining.getMin();
			max = loadTraining.getMax();
			nameOfMode = "normalization with min and max";
		}
		
		int corrects = 0;
		int numberOfTrials = testItems.length;
		
		for (Item itemInArray: testItems) {
			if (computeMode == 1) {
				itemInArray = normalizeItem(itemInArray, medians, absoluteSD);
			}
			else if (computeMode == 2) {
				itemInArray = normalizeItem(itemInArray, min, max);
			}
			String result = null;
			if (classifyMode == 1) result = classify(itemInArray, loadTraining, computeMode); // Using training to classify items
			if (classifyMode == 2) result = classifyBasedOnKNearest(itemInArray, loadTraining, computeMode, K); // Using training to classify items
			String expected = itemInArray.getClassification();
			if (result.equals(expected)) corrects++;
		}
		double correctness = 100*corrects/numberOfTrials;
		System.out.printf("Using %s, the classification is %.2f%% correct%n", nameOfMode, correctness);
		return correctness;
	}
	
	/*
	 * Classify item using its nearest neighbor
	 */
	public String classify(Item itemToClassify, GeneralLoad load, int mode) {
		Item closest = findNearestMatch(itemToClassify, load, mode);
		return closest.getClassification();
	}
	
	/*
	 * Classify item usings its k-nearest neighbors 
	 */
	public String classifyBasedOnKNearest(Item itemToClassify, GeneralLoad load, int mode, int k) {
		Item[] nearestMatches = findKNearestMatch(itemToClassify, load, mode, k);
		ArrayList<String> classifications = new ArrayList<>();
		ArrayList<Integer> numberOfOccurences = new ArrayList<>();
		
		// Go through all the matches and make a frequency.
		for (Item itemInArray: nearestMatches) {
			String classificationOfItem = itemInArray.getClassification();
			if (classifications.contains(classificationOfItem)) {
				int index = classifications.indexOf(classificationOfItem);
				int currentOccurence = numberOfOccurences.get(index);
				int newOccurence = currentOccurence + 1;
				numberOfOccurences.set(index, newOccurence);
			}
			else {
				classifications.add(classificationOfItem);
				numberOfOccurences.add(1);
			}
		}
		
		// Find the highest occurrences.
		int max = 0;
		int maxIndex = -1;
		int currentIndex = 0;
		for (int currentNumber: numberOfOccurences) {
			if (max < currentNumber) {
				max = currentNumber;
				maxIndex = currentIndex;
			}
			currentIndex++;
		}
		return classifications.get(maxIndex);
	}
	
	/*
	 * Find the nearest match in distance(Manhattan distance) to the item, using the data from a load file
	 * @param item to find the nearest match.
	 * @param data to execute the method
	 * @param the mode to normalize attributes of items
	 * @return the nearest item to the input item
	 */
	public Item findNearestMatch(Item itemToFind, GeneralLoad load, int mode) {
		Item nearestMatch = findKNearestMatch(itemToFind, load, mode, 3)[0];
		return nearestMatch;
	}
	
	/*
	 * Find k nearest matches in distance to the item, using the data from a load file
	 * @param item to find the nearest match.
	 * @param data to execute the method
	 * @param the mode to normalize attributes of items
	 * @return the nearest item to the input item
	 */
	public Item[] findKNearestMatch(Item itemToFind, GeneralLoad load, int mode, int k) {
		Item[] allItems = load.getItems();
		TreeMap<Double, Item> distancesOfItems = new TreeMap<>();
		int numberOfItems = allItems.length;
		double[] medians = new double[numberOfItems];
		double[] absoluteSD = new double[numberOfItems];
		double[] min = new double[numberOfItems];
		double[] max = new double[numberOfItems];
		
		// Initialize required data for normalization
		if (mode == 1) {
			medians = load.getMedian();
			absoluteSD = load.getAbsoluteSD();
			itemToFind = normalizeItem(itemToFind, medians, absoluteSD);
		}
		else if (mode == 2) {
			min = load.getMin();
			max = load.getMax();
			itemToFind = normalizeItem(itemToFind, min, max);
		}
		
		// Go through all the element and get the distance.
		for (Item itemInList: allItems) {
			if (mode == 1) 
				itemInList = normalizeItem(itemInList, medians, absoluteSD);
			if (mode == 2)
				itemInList = normalizeItem(itemInList, min, max);
			
			double distance = getDistance(itemInList, itemToFind, mode);
			distancesOfItems.put(distance, itemInList);
			//System.out.printf("Distance between \t%s \t%s is \t%.2f%n", itemInList, itemToFind, distance);
		}
		
		// Return k nearest items
		Item[] output = new Item[k];
		int count = 0;
		for (Entry<Double, Item> entryInMap: distancesOfItems.entrySet()) {
			if (count >= k) break;
			output[count] = entryInMap.getValue();
			//double distance = entryInMap.getKey();
			//System.out.printf("%.2f between \t%s \t%s is \t%n", distance, output[count], itemToFind);
			count++;
		}
		//System.out.println("end");
		return output;
	}
	
	/*
	 * Normalize all attributes of an item using medians and absoluteSD
	 * @param an item to normalize
	 * @param an array of medians. Each index is the medians of all attributes.
	 * @param an array of absoluteSD. 
	 * @return an item with all attributes normalized  
	 */
	private Item normalizeItem(Item itemToNormalize, double[] arrayOfValues1, double[] arrayOfValues2) {
		Normalization normal = new Normalization();
		double[] allAttributes = itemToNormalize.getAttributes();
		double[] normalized = new double[allAttributes.length];
		
		for (int i = 0; i < allAttributes.length; i++) {
			normalized[i] = normal.getModifiedNormalization(allAttributes[i], arrayOfValues1[i], arrayOfValues2[i]);
		}
		itemToNormalize.setNormalizedAttributes(normalized);
		return itemToNormalize;
	}
	
	public double getDistance(Item first, Item second, int mode) {
		ManhattanDistance distance = new ManhattanDistance();
		if (mode == 0) return distance.compute(first.getAttributes(), second.getAttributes());
		else if (mode == 1 | mode == 2) return distance.compute(first.getNormalizedAttributes(), second.getNormalizedAttributes());
		else throw new IllegalArgumentException("Mode not avaiable");
	}
}
