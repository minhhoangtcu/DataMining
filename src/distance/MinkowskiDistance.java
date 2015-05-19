package distance;

import java.util.*;
import java.util.Map.Entry;

import javax.swing.JTable.PrintMode;

import book.LoadFile;

public class MinkowskiDistance {
	
	
	
	public static void main(String[] args) {
		MinkowskiDistance distance = new MinkowskiDistance();
	}
	
	/* Wrong idea. Wrong implementaion.
	public void run() {
		LoadFile load = new LoadFile();
		load.loadUsers();
		load.loadRating();
		Person[] people = findNearestK(1, 3, 10, load.getPeople());
		for (Person person: people) {
			System.out.println(person.getID());
		}
	}
	*/

	/*
	 * Compute the distance between 2 people. The smaller the distance, they have more similarities.
	 * @param the mode to compute the distance. 1 to compute Manhattan Distance. 2 to compute Euclidean Distance.
	 * @param the first person to compare. This should be a HashMap with the key is the name of the product and the map is the rating.
	 * @param the second person.
	 * @return the distance between the 2 provided people. 
	 */
	public double computeMinkowski(int r, Map<String, Double> firstPerson, Map<String, Double> secondPerson) {	
		float distance = 0;
		for (String key: firstPerson.keySet()) {
			if (secondPerson.containsKey(key)) {
				distance += Math.pow(((Math.abs(firstPerson.get(key) - secondPerson.get(key)))), r);
			}
		}
		return Math.pow(distance, 1/r);
	}
	
	public Person findNearest(int r, Person person, List<Person> people) {
		Map<Double, Person> distances = new HashMap<>();
		for (Person personInList: people) {
			double distance = computeMinkowski(r, person.getRating(), personInList.getRating());
			distances.put(distance ,personInList);
		}
		TreeMap<Double, Person> sortedDistances = new TreeMap<>(distances);
		printMap(sortedDistances);
		return sortedDistances.firstEntry().getValue();
	}
	
	/* This piece of code is very wrong. Because we are not finding the closest k people according to distance. But, to correlation.
	 * However, we can still find k nearest based on distance. But, I do not have time to implement this.
	public Person[] findNearestK(int r, int k, int id, Map<Integer, Person> people) {
		Map<Double, Person> distances = new HashMap<>();
		Person person = people.get(id);
		people.remove(id);
		for (Person personInList: people.values()) {
			double distance = computeMinkowski(r, person.getRating(), personInList.getRating());
			if (distance != 0) System.out.println(id + " and " + personInList.getID() + " has " + distance);
			distances.put(distance, personInList);
		}
		TreeMap<Double, Person> sortedDistances = new TreeMap<>(distances);
		//printMap(sortedDistances);
		
		// Return the k nearest people
		if (k > sortedDistances.size()) {
			k = sortedDistances.size(); // If we cannot find 
		}
	    Set<Entry<Double, Person>> entrySet = sortedDistances.entrySet(); // Get a set of all the entries (key - value pairs) contained in the TreeMap
	    Iterator<Entry<Double, Person>> it = entrySet.iterator(); // Obtain an Iterator for the entries Set
		Person[] output = new Person[k];
		for (int i = 0; i < k; i++) {
			if (it.hasNext())
				output[i] = it.next().getValue();
		}
		people.put(id, person);
		return output;
	}
	*/
	
	public void printMap(Map<Double, Person> map) {
		System.out.println("--------------");
		for (Entry<Double, Person> entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() 
                                      + " Value : " + entry.getValue());
		}
	}
 
}


