package classification.general;

import java.util.TreeMap;

import org.apache.commons.math3.ml.distance.ManhattanDistance;

import classification.Athlete;
import classification.ModifiedNormalization;


public class GeneralClassification {
	
	public static void main(String[] args) {
		GeneralClassification classification = new GeneralClassification();
		String classificationName = "20";
		double[] attributes = {8, 307.0, 130.0, 3504, 12.0};
		String[] comments = {"chevrolet chevelle malibu"};
		Item testItem = new Item(classificationName, attributes, comments);
		System.out.println(classification.findNearestMatch(testItem, GeneralLoad.MPGTRAINING));
		
		GeneralLoad load = new GeneralLoad();
		Item[] allItems = load.loadFile(GeneralLoad.MPGTEST);
		
		int corrects = 0;
		int numberOfTrials = allItems.length;
		for (Item itemInArray: allItems) {
			itemInArray = classification.setNormalizedValue(itemInArray);
			String result = classification.classify(itemInArray, GeneralLoad.MPGTRAINING);
			String expected = itemInArray.getClassification();
			if (result.equals(expected)) corrects++;
		}
		
		double correctness = 100*corrects/numberOfTrials;
		System.out.printf("The classification is %.2f correct%n", correctness);
	}
	
	public String classify(Item itemToClassify, String fileDir) {
		Item closest = findNearestMatch(itemToClassify, fileDir);
		return closest.getClassification();
	}
	
	public Item findNearestMatch(Item itemToFind, String fileDir) {
		GeneralLoad load = new GeneralLoad();
		Item[] allItems = load.loadFile(fileDir);
		TreeMap<Double, Item> distancesOfItems = new TreeMap<>();
		itemToFind = setNormalizedValue(itemToFind);
		
		for (Item itemInList: allItems) {
			if (!itemInList.equals(itemToFind)) {
				itemInList = setNormalizedValue(itemInList);
				double distance = getDistance(itemInList, itemToFind);
				distancesOfItems.put(distance, itemInList);
				//System.out.printf("Distance between \t%s \t%s is \t%.2f%n", itemInList, itemToFind, distance);
			}
		}
		return distancesOfItems.firstEntry().getValue();
	}
	
	public Item setNormalizedValue(Item input) {
		ModifiedNormalization normal = new ModifiedNormalization();
		double[] normalizedAttributes = normal.getModifiedNormalization(input.getAttributes());
		input.setNormalizedAttributes(normalizedAttributes);
		return input;
	}
	
	public double getDistance(Item first, Item second) {
		ManhattanDistance distance = new ManhattanDistance();
		return distance.compute(first.getNormalizedAttributes(), second.getNormalizedAttributes());
	}
}
