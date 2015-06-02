package classification.general;

import java.util.TreeMap;

import org.apache.commons.math3.ml.distance.ManhattanDistance;

import classification.Normalization;


public class GeneralClassification {
	
	public double computeCorrectness(int mode, String trainingDir, String testingDir) {
		GeneralLoad loadTest = new GeneralLoad(testingDir);
		GeneralLoad loadTraining = new GeneralLoad(trainingDir);
		Item[] testItems = loadTest.getItems();
		int numberOfItems = loadTraining.getItems().length;
		double[] medians = new double[numberOfItems];
		double[] absoluteSD = new double[numberOfItems];
		double[] min = new double[numberOfItems];
		double[] max = new double[numberOfItems];
		String nameOfMode = null;
		if (mode == 0) {
			nameOfMode = "NO normalization";
		}
		else if (mode == 1) {
			medians = loadTraining.getMedian();
			absoluteSD = loadTraining.getAbsoluteSD();
			nameOfMode = "normalization with median and absolute SD";
		}
		else if (mode == 2) {
			min = loadTraining.getMin();
			max = loadTraining.getMax();
			nameOfMode = "normalization with min and max";
		}
		
		
		
		int corrects = 0;
		int numberOfTrials = testItems.length;
		
		for (Item itemInArray: testItems) {
			if (mode == 1) {
				itemInArray = normalizeItem(itemInArray, medians, absoluteSD);
			}
			else if (mode == 2) {
				itemInArray = normalizeItem(itemInArray, min, max);
			}
			String result = classify(itemInArray, loadTraining, mode); // Using training to classify items
			String expected = itemInArray.getClassification();
			if (result.equals(expected)) corrects++;
		}
		double correctness = 100*corrects/numberOfTrials;
		System.out.printf("Using %s, the classification is %.2f correct%n", nameOfMode, correctness);
		return correctness;
	}
	
	public String classify(Item itemToClassify, GeneralLoad load, int mode) {
		Item closest = findNearestMatch(itemToClassify, load, mode);
		return closest.getClassification();
	}
	
	/*
	 * Find the nearest match in distance(Manhattan distance) to the item, using the data from a load file
	 * @param item to find the nearest match.
	 * @param data to execute the method
	 * @param the mode to normalize attributes of items
	 * @return the nearest item to the input item
	 */
	public Item findNearestMatch(Item itemToFind, GeneralLoad load, int mode) {
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
		return distancesOfItems.firstEntry().getValue();
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
