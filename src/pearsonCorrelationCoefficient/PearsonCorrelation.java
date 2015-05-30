package pearsonCorrelationCoefficient;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import book.LoadFile;
import distance.Person;

// TODO fix the getCorrelations

public class PearsonCorrelation {
	
	public static void main(String[] args) {
		LoadFile load = new LoadFile("book");
		PearsonCorrelation correlation = new PearsonCorrelation();
		load.initUsers();
		load.initRating();
		correlation.getHigestKCorrelations(1, load.getPeople(), 3);
	}
	
	/*
	 * Compute the correlation of the person with the given ID between all the people.
	 * @param the id of the person that we want to find correlation
	 * @param the map of people
	 * @param number of highest correlation 
	 */
	public TreeMap<Double, Person> getHigestKCorrelations(int id, Map<Integer, Person> people, int k) {
		Person person = people.get(id);
		TreeMap<Double, Person> correlations = new TreeMap<Double, Person>(); 
		for (Person personInMap: people.values()) {
			double correlation = computeCorrelation(person, personInMap);
			correlations.put(correlation, personInMap);
		}
		return getHigestKInAMap(correlations, k);
	}
	
	private TreeMap<Double, Person> getHigestKInAMap(TreeMap<Double, Person> source, int k) {
		int count = 0;
		TreeMap<Double, Person> output = new TreeMap<Double, Person>();
		for(Entry<Double, Person> entry: source.entrySet()) {
			if (count >= k) break;
			output.put(entry.getKey(), entry.getValue());
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
		return output;
	}

	public double computeCorrelation(Person firstPerson, Person secondPerson) {
		try {
			double sumOfXY = getSumOfXY(firstPerson, secondPerson);
			double productOfSumXAndSumYOverN = getProductOfSumXAndSumYOverN(
					firstPerson, secondPerson);
			double squareOfSumOfX = Math.pow(
					getSumOfFirst(firstPerson, secondPerson, 1), 2);
			double squareOfSumOfY = Math.pow(
					getSumOfFirst(secondPerson, firstPerson, 1), 2);
			double sumOfXSquare = getSumOfFirst(firstPerson, secondPerson, 2);
			double sumOfYSquare = getSumOfFirst(secondPerson, firstPerson, 2);
			int matches = getMatch(firstPerson, secondPerson);
			return (sumOfXY - productOfSumXAndSumYOverN)
					/ ((Math.sqrt(sumOfXSquare - squareOfSumOfX / matches) * (Math
							.sqrt(sumOfYSquare - squareOfSumOfY / matches))));
		} catch (IllegalArgumentException e) {
			return 0.0; // 0 correlation. No match
		}
	}
	
	public double getSumOfXY(Person firstPerson, Person secondPerson) {
		double output = 0.0;
		Map<String, Double> firstRating = firstPerson.getRating(); 
		Map<String, Double> secondRating = secondPerson.getRating();
		for (String product: firstRating.keySet()) {
			if (secondRating.containsKey(product)) {
				output += firstRating.get(product) * secondRating.get(product);
			}
		}
		return output;
	}	
	
	public double getProductOfSumXAndSumYOverN(Person firstPerson, Person secondPerson) throws IllegalArgumentException {
		double sumOfX = getSumOfFirst(firstPerson, secondPerson, 1);
		double sumOfY = getSumOfFirst(secondPerson, firstPerson, 1);
		int matches = getMatch(secondPerson, firstPerson);
		if (matches == 0)
			throw new IllegalArgumentException("no matches found");
		else
			return sumOfX*sumOfY/matches;
	}

	public double getSumOfFirst(Person firstPerson, Person secondPerson, int power) {
		double sumOfX = 0.0;
		Map<String, Double> firstRating = firstPerson.getRating(); 
		Map<String, Double> secondRating = secondPerson.getRating();
		for (String product: firstRating.keySet()) {
			if (secondRating.containsKey(product)) {
				sumOfX += Math.pow(firstRating.get(product), power);
			}
		}
		return sumOfX;
	}
	
	public int getMatch(Person firstPerson, Person secondPerson) {
		int matches = 0;
		Map<String, Double> firstRating = firstPerson.getRating(); 
		Map<String, Double> secondRating = secondPerson.getRating();
		for (String product: firstRating.keySet()) {
			if (secondRating.containsKey(product))
				matches++;
		}
		return matches;
	}
}
