package slopeOne;

import java.util.Map;
import java.util.Map.Entry;

public class WeightedSlopeOne {
	/*
	 * the average deciation of an item i with respect to item j is:
	 * 
	 */
	public double computeDeviation(String firstProduct, String secondProduct, User<Integer>[] allUsers) {
		// Go through each user in the array and get his rating
		for (User<Integer> userInArray: allUsers) {
			Map<String, Integer> rating = userInArray.getRating();
			// Go through each rating of that user. Create 
			for (Entry<String, Integer> firstEntry: rating.entrySet()) {
				for (Entry<String, Integer> secondEentry: rating.entrySet()) {
					
				}
			}
		}
	}
	
}
