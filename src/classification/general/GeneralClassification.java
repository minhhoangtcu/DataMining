package classification.general;

import java.util.Arrays;
import java.util.TreeMap;

import org.apache.commons.math3.ml.distance.ManhattanDistance;

import classification.Athlete;
import classification.ModifiedNormalization;


public class GeneralClassification {
	
	public static void main(String[] args) {
		GeneralClassification classification = new GeneralClassification();
		
		/*String classificationName = "20";
		double[] attributes = {8, 307.0, 130.0, 3504, 12.0};
		String[] comments = {"chevrolet chevelle malibu"};
		Item testItem = new Item(classificationName, attributes, comments);
		System.out.println(classification.findNearestMatch(testItem, GeneralLoad.MPGTRAINING));
		*/
		
		GeneralLoad load = new GeneralLoad();
		Item[] allItems = load.loadFile(GeneralLoad.MPGTEST);
		double[][] notNormalizedValues = load.setValues(allItems);
		
		int corrects = 0;
		int numberOfTrials = allItems.length;
		for (Item itemInArray: allItems) {
			// Normalize
			
			String result = classification.classify(itemInArray, GeneralLoad.MPGTRAINING);
			String expected = itemInArray.getClassification();
			if (result.equals(expected)) corrects++;
		}
		
		double correctness = 100*corrects/numberOfTrials;
		System.out.printf("The classification is %.2f correct%n", correctness);
	}
	
	private Item normalizeItem(Item itemToNormalize, double[][] values) {
		ModifiedNormalization normal = new ModifiedNormalization();
		double[] allAttributes = itemToNormalize.getAttributes();
		
		for (double attribute: allAttributes) {
			
		}
	}
	
	public String classify(Item itemToClassify, String fileDir) {
		Item closest = findNearestMatch(itemToClassify, fileDir);
		return closest.getClassification();
	}
	
	public Item findNearestMatch(Item itemToFind, String fileDir) {
		GeneralLoad load = new GeneralLoad();
		Item[] allItems = load.loadFile(fileDir);
		double[][] values = load.setValues(allItems);
		TreeMap<Double, Item> distancesOfItems = new TreeMap<>();
		values = setNormalizedValue(values); // Normalize values.
		
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
	 * @param take a 2d array of double
	 * @return normalize all the values in the input 
	 */
	public double[][] setNormalizedValue(double[][] input, double[] medians, double[] absoluteSD) {
		ModifiedNormalization normal = new ModifiedNormalization();
		int numberOfAttributes = input.length;
		int numberOfItems = input[0].length;
		// Normalize the values
		for (int i = 0; i < numberOfAttributes; i++) {
			for (int j = 0; j < numberOfItems; j++) {
				
			}
		}
		System.out.println("Normalized: " + Arrays.deepToString(input));
		return input;
	}
	
	public double getDistance(Item first, Item second) {
		ManhattanDistance distance = new ManhattanDistance();
		return distance.compute(first.getNormalizedAttributes(), second.getNormalizedAttributes());
	}
}
