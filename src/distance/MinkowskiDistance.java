package distance;

import java.util.Map;

public class MinkowskiDistance {
	
	public static void main(String[] args) {
		
	}

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
