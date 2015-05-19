package slopeOne;

import java.util.Map.*;
import java.util.*;
import java.util.Map.Entry;

import distance.Person;
public class WeightedSlopeOne {
	
	Map<String, HashMap<String, Double>> deviations = new HashMap<String, HashMap<String,Double>>();
	Map<String, HashMap<String, Integer>> frequencies = new HashMap<String, HashMap<String,Integer>>();
	
	public static void main(String[] args) {
		Map<String, Double> amyRating = new HashMap<String, Double>();
		amyRating.put("Taylor Swift", 4.0);
		amyRating.put("PSY", 3.0);
		amyRating.put("Whitney Houston", 4.0);
		Person amy = new Person("Amy", amyRating);
		
		Map<String, Double> benRating = new HashMap<String, Double>();
		benRating.put("Taylor Swift", 5.0);
		benRating.put("PSY", 2.0);
		Person ben = new Person("Ben", benRating);
		
		Map<String, Double> claraRating = new HashMap<String, Double>();
		claraRating.put("PSY", 3.5);
		claraRating.put("Whitney Houston", 4.0);
		Person clara = new Person("Clara", claraRating);
		
		Map<String, Double> daisyRating = new HashMap<String, Double>();
		daisyRating.put("Taylor Swift", 5.0);
		daisyRating.put("Whitney Houston", 3.0);
		Person daisy = new Person("Daisy", daisyRating);
		
		Person[] people = {amy, ben, clara, daisy};
		
		
		WeightedSlopeOne slopeOne = new WeightedSlopeOne();
		slopeOne.computeDeviation(people);
		slopeOne.printDeviationsTable();
	}
	
	/*
	 * This method create a map of deviations from an array of users with their ratings
	 * @param an array of users
	 * 
	 */
	public void computeDeviation(Person[] allUsers) {
		deviations = new HashMap<String, HashMap<String,Double>>();
		frequencies = new HashMap<String, HashMap<String,Integer>>();
		
		// Go through each user in the array and get his rating
		for (Person userInArray: allUsers) {
			Map<String, Double> rating = userInArray.getRating();
			// Go through each rating of that user. Create 
			for (Entry<String, Double> firstEntry: rating.entrySet()) {
				// init the deviations matrix with firstEntry's name
				if (!deviations.containsKey(firstEntry.getKey())) {
					deviations.put(firstEntry.getKey(), new HashMap<String, Double>());
					frequencies.put(firstEntry.getKey(), new HashMap<String, Integer>());
				}
				for (Entry<String, Double> secondEntry: rating.entrySet()) {
					//if (firstEntry != secondEntry) {
						String firstName = firstEntry.getKey();
						String secondName = secondEntry.getKey();
						
						double oldDeviation = 0;
						if (deviations.get(firstName).containsKey(secondName))
							oldDeviation = deviations.get(firstName).get(secondName);
						double currentDeviation = firstEntry.getValue() - secondEntry.getValue();
						double newDeviation = oldDeviation + currentDeviation;
						deviations.get(firstName).put(secondName, newDeviation);
						//System.out.printf("Put into deviation %s %s %f \n", firstName, secondName, newDeviation);
						
						int oldFrequency = 0;
						if (frequencies.get(firstName).containsKey(secondName))
							oldFrequency = frequencies.get(firstName).get(secondName);
						frequencies.get(firstName).put(secondName, oldFrequency+1);
						//System.out.printf("Put into frequency %s %s %d \n", firstName, secondName, oldFrequency+1);
					//}
				}
			}
		}
		
		// devide each deviations by its frequency
		for (Entry<String, HashMap<String, Double>> outterEntry: deviations.entrySet()) {
			for (Entry<String, Double> innerEntry: outterEntry.getValue().entrySet()) {
				String outterName = outterEntry.getKey();
				String innerName = innerEntry.getKey();
				int frequency = frequencies.get(outterName).get(innerName);
				double oldDeviation = deviations.get(outterName).get(innerName);
				double newDeviation = oldDeviation/frequency;
				deviations.get(outterName).put(innerName, newDeviation);
			}
		}
	}

	public void printDeviationsTable() {
		Set<String> names = deviations.keySet();
		System.out.print("\t\t");
		for (String name: names) {
			System.out.print(name + "\t");
		}
		System.out.println();
		
		for (Entry<String, HashMap<String, Double>> outterEntry: deviations.entrySet()) {
			String outterName = outterEntry.getKey();
			System.out.print(outterName + "\t\t");
			for (Entry<String, Double> innerEntry: outterEntry.getValue().entrySet()) {
				System.out.print(innerEntry.getValue() + "\t\t");
			}
			System.out.println();
		}
	}
	
}
