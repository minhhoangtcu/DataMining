package classification.general;

import java.util.TreeMap;
import org.apache.commons.math3.ml.distance.ManhattanDistance;
import classification.Normalization;


public class GeneralClassification {
	
	public static void main(String[] args) {
		
	}
	
	public double computeCorrectness(int mode, String trainingDir, String testingDir) {
		GeneralClassification classification = new GeneralClassification();
		GeneralLoad loadTest = new GeneralLoad(testingDir);
		GeneralLoad loadTraining = new GeneralLoad(trainingDir);
		Item[] testItems = loadTest.getItems();
		double[] medians = loadTraining.getMedian();
		double[] absoluteSD = loadTraining.getAbsoluteSD();
		double[] min = loadTraining.getMin();
		double[] max = loadTraining.getMax();
		
		int corrects = 0;
		int numberOfTrials = testItems.length;
		String nameOfMode = null;
		if (mode == 0) {
			nameOfMode = "NO normalization";
			for (Item itemInArray: testItems) {
				String result = classification.classify(itemInArray, loadTraining); // Using training to classify items
				String expected = itemInArray.getClassification();
				if (result.equals(expected)) corrects++;
			}
		}
		else if (mode == 1) {
			nameOfMode = "normalization with median and absolute SD";
			for (Item itemInArray: testItems) {
				itemInArray = classification.normalizeItem(itemInArray, medians, absoluteSD);
				String result = classification.classifyNormalize(itemInArray, loadTraining); // Using training to classify items
				String expected = itemInArray.getClassification();
				if (result.equals(expected)) corrects++;
			}
		}
		else if (mode == 2) {
			nameOfMode = "normalization with min and max";
			for (Item itemInArray: testItems) {
				itemInArray = classification.normalizeItem2(itemInArray, min, max);
				String result = classification.classifyNormalize2(itemInArray, loadTraining); // Using training to classify items
				String expected = itemInArray.getClassification();
				if (result.equals(expected)) corrects++;
			}
		}
		double correctness = 100*corrects/numberOfTrials;
		System.out.printf("Using %s, the classification is %.2f correct%n", nameOfMode, correctness);
		return correctness;
	}
	
	public String classifyNormalize(Item itemToClassify, GeneralLoad load) {
		Item closest = findNearestMatchNormalize(itemToClassify, load);
		return closest.getClassification();
	}
	
	public String classifyNormalize2(Item itemToClassify, GeneralLoad load) {
		Item closest = findNearestMatchNormalize2(itemToClassify, load);
		return closest.getClassification();
	}
	
	public String classify(Item itemToClassify, GeneralLoad load) {
		Item closest = findNearestMatch(itemToClassify, load);
		return closest.getClassification();
	}
	
	/*
	 * After normalization, find the nearest match in distance(Manhattan distance) to the item, using the data from a load file (getting all items, medians and absolute sd)
	 * @param item to find the nearest match.
	 * @param data to execute the method
	 * @return the nearest item to the input item
	 */
	public Item findNearestMatchNormalize(Item itemToFind, GeneralLoad load) {
		Item[] allItems = load.getItems();
		double[] medians = load.getMedian();
		double[] absoluteSD = load.getAbsoluteSD();
		TreeMap<Double, Item> distancesOfItems = new TreeMap<>();
		
		itemToFind = normalizeItem(itemToFind, medians, absoluteSD);
		for (Item itemInList: allItems) {
			itemInList = normalizeItem(itemInList, medians, absoluteSD);
			if (!itemInList.equals(itemToFind)) {
				double distance = getDistanceNormalize(itemInList, itemToFind);
				distancesOfItems.put(distance, itemInList);
				//System.out.printf("Distance between \t%s \t%s is \t%.2f%n", itemInList, itemToFind, distance);
			}
		}
		return distancesOfItems.firstEntry().getValue();
	}
	
	/*
	 * After normalization, find the nearest match in distance(Manhattan distance) to the item, using the data from a load file (getting all items, medians and absolute sd)
	 * @param item to find the nearest match.
	 * @param data to execute the method
	 * @return the nearest item to the input item
	 */
	public Item findNearestMatchNormalize2(Item itemToFind, GeneralLoad load) {
		Item[] allItems = load.getItems();
		double[] min = load.getMin();
		double[] max = load.getMax();
		TreeMap<Double, Item> distancesOfItems = new TreeMap<>();
		
		itemToFind = normalizeItem2(itemToFind, min, max);
		for (Item itemInList: allItems) {
			itemInList = normalizeItem2(itemInList, min, max);
			if (!itemInList.equals(itemToFind)) {
				double distance = getDistanceNormalize(itemInList, itemToFind);
				distancesOfItems.put(distance, itemInList);
				//System.out.printf("Distance between \t%s \t%s is \t%.2f%n", itemInList, itemToFind, distance);
			}
		}
		return distancesOfItems.firstEntry().getValue();
	}
	
	/*
	 * WITHOUT normalization, find the nearest match in distance(Manhattan distance) to the item, using the data from a load file
	 * @param item to find the nearest match.
	 * @param data to execute the method
	 * @return the nearest item to the input item
	 */
	public Item findNearestMatch(Item itemToFind, GeneralLoad load) {
		Item[] allItems = load.getItems();
		TreeMap<Double, Item> distancesOfItems = new TreeMap<>();
		
		for (Item itemInList: allItems) {
			if (!itemInList.equals(itemToFind)) {
				double distance = getDistance(itemInList, itemToFind);
				distancesOfItems.put(distance, itemInList);
				//System.out.printf("Distance between \t%s \t%s is \t%.2f%n", itemInList, itemToFind, distance);
			}
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
	private Item normalizeItem(Item itemToNormalize, double[] medians, double[] absoluteSD) {
		Normalization normal = new Normalization();
		double[] allAttributes = itemToNormalize.getAttributes();
		double[] normalized = new double[allAttributes.length];
		
		for (int i = 0; i < allAttributes.length; i++) {
			normalized[i] = normal.getModifiedNormalization(allAttributes[i], medians[i], absoluteSD[i]);
		}
		itemToNormalize.setNormalizedAttributes(normalized);
		return itemToNormalize;
	}
	

	/*
	 * Normalize all attributes of an item using min and max
	 * @param an item to normalize
	 * @param an array of min. Each index is the medians of all attributes.
	 * @param an array of max. 
	 * @return an item with all attributes normalized  
	 */
	private Item normalizeItem2(Item itemToNormalize, double[] min, double[] max) {
		Normalization normal = new Normalization();
		double[] allAttributes = itemToNormalize.getAttributes();
		double[] normalized = new double[allAttributes.length];
		
		for (int i = 0; i < allAttributes.length; i++) {
			normalized[i] = normal.getModifiedNormalization2(allAttributes[i], min[i], max[i]);
		}
		itemToNormalize.setNormalizedAttributes(normalized);
		return itemToNormalize;
	}
	
	public double getDistanceNormalize(Item first, Item second) {
		ManhattanDistance distance = new ManhattanDistance();
		return distance.compute(first.getNormalizedAttributes(), second.getNormalizedAttributes());
	}
	
	public double getDistance(Item first, Item second) {
		ManhattanDistance distance = new ManhattanDistance();
		return distance.compute(first.getAttributes(), second.getAttributes());
	}
}
