package classification.general;

import java.util.TreeMap;
import org.apache.commons.math3.ml.distance.ManhattanDistance;
import classification.ModifiedNormalization;


public class GeneralClassification {
	
	public static void main(String[] args) {
		GeneralClassification classification = new GeneralClassification();
		GeneralLoad loadTest = new GeneralLoad(GeneralLoad.MPGTEST);
		GeneralLoad loadTraining = new GeneralLoad(GeneralLoad.MPGTRAINING);
		Item[] testItems = loadTest.getItems();
		double[] medians = loadTraining.getMedian();
		double[] absoluteSD = loadTraining.getAbsoluteSD();
		
		int corrects = 0;
		int numberOfTrials = testItems.length;
		for (Item itemInArray: testItems) {
			itemInArray = classification.normalizeItem(itemInArray, medians, absoluteSD);
			String result = classification.classifyNormalize(itemInArray, loadTraining); // Using training to classify items
			String expected = itemInArray.getClassification();
			if (result.equals(expected)) corrects++;
		}
		double correctness = 100*corrects/numberOfTrials;
		System.out.printf("Using normalization technique, the classification is %.2f correct%n", correctness);
		
		corrects = 0;
		for (Item itemInArray: testItems) {
			String result = classification.classify(itemInArray, loadTraining); // Using training to classify items
			String expected = itemInArray.getClassification();
			if (result.equals(expected)) corrects++;
		}
		correctness = 100*corrects/numberOfTrials;
		System.out.printf("the classification is %.2f correct%n", correctness);
	}
	
	
	
	public String classifyNormalize(Item itemToClassify, GeneralLoad load) {
		Item closest = findNearestMatchNormalize(itemToClassify, load);
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
	 * Normalize all attributes of an item 
	 * @param an item to normalize
	 * @param an array of medians. Each index is the medians of all attributes.
	 * @param an array of absoluteSD. 
	 * @return an item with all attributes normalized  
	 */
	private Item normalizeItem(Item itemToNormalize, double[] medians, double[] absoluteSD) {
		ModifiedNormalization normal = new ModifiedNormalization();
		double[] allAttributes = itemToNormalize.getAttributes();
		double[] normalized = new double[allAttributes.length];
		
		for (int i = 0; i < allAttributes.length; i++) {
			normalized[i] = normal.getModifiedNormalization(allAttributes[i], medians[i], absoluteSD[i]);
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
