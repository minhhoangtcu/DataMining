package distance;

import java.util.Map;

public class MinkowskiDistance {
	
	public static void main(String[] args) {
		
	}

	/*
	 * Compute the distance between 2 people. The smaller the distance, they have more similarities.
	 * @param the mode to compute the distance. 1 to compute Manhattan Distance. 2 to compute Euclidean Distance.
	 * @param the first person to compare. This should be a HashMap with the key is the name of the product and the map is the rating.
	 * @param the second person.
	 * @return the distance between the 2 provided people. 
	 */
	public double computerMinkowski(int r, Map<String, Double> firstPerson, Map<String, Double> secondPerson) {	
		float distance = 0;
		for (String key: firstPerson.keySet()) {
			if (secondPerson.containsKey(key)) {
				distance += Math.pow(((Math.abs(firstPerson.get(key) - secondPerson.get(key)))), r);
			}
		}
		return Math.pow(distance, 1/r);
	}
}
