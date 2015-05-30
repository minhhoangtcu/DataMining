package book.cosineSimilarity;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import book.LoadFile;
import book.pearsonCorrelationCoefficient.PearsonCorrelation;
import distance.Person;

public class CosineSimilarity {
	
	PearsonCorrelation correlation;
	
	public CosineSimilarity() {
		correlation = new PearsonCorrelation();
	}
	
	public static void main(String[] args) {
		CosineSimilarity sim = new CosineSimilarity();
		
		/* SMALL DATA
		Person clara = new Person("Clara");
		clara.putRating("Blues Traveler", 4.75);
		clara.putRating("Norah Jones", 4.5);
		clara.putRating("Phoenix", 5.0);
		clara.putRating("The Strokes", 4.25);
		clara.putRating("Weird AI", 4.0);
		Person robert = new Person("Robert");
		robert.putRating("Blues Traveler", 4.0);
		robert.putRating("Norah Jones", 3.0);
		robert.putRating("Phoenix", 5.0);
		robert.putRating("The Strokes", 2.0);
		robert.putRating("Weird AI", 1.0);
		System.out.println(sim.computeSimilarity(clara, robert));
		*/
		
		LoadFile load = new LoadFile("book");
		load.initUsers();
		load.initRating();
		for (Entry<Double, Person> entry: sim.getHigestKSimilarities(8, load.getPeople(), 5).entrySet()) {
			System.out.printf("Most similar person with ID: %d with Cosine Similarity: %.3f%n", entry.getValue().getID(), entry.getKey());
		}
	}
	
	public TreeMap<Double, Person> getHigestKSimilarities(int id, Map<Integer, Person> people, int k) {
		Person person = people.get(id);
		people.remove(id);
		TreeMap<Double, Person> similarities = new TreeMap<Double, Person>(Collections.reverseOrder()); 
		for (Person personInMap: people.values()) {
			double score = computeSimilarity(person, personInMap);
			if (score != 0.0) {
				similarities.put(score, personInMap);
				System.out.println(score);
			}
		}
		
		int counter = 0;
		TreeMap<Double, Person> output = new TreeMap<Double, Person>(Collections.reverseOrder());
		for (Entry<Double, Person> entry: similarities.entrySet()) {
			if (counter >= k) break;
			output.put(entry.getKey(), entry.getValue());
			counter++;
		}
		System.out.println("Finished");
		return output;
	}
	
	public double computeSimilarity(Person firstPerson, Person secondPerson) {
		if (correlation.getMatch(firstPerson, secondPerson) == 0) {
			return 0.0;
		}
		else {
			double dotProduct = 0.0;
			double sumOfXSquare = 0.0;
			double sumOfYSquare = 0.0;
			
			Map<String, Double> firstRating = firstPerson.getRating(); 
			Map<String, Double> secondRating = secondPerson.getRating();
			for (String product: firstRating.keySet()) {
				if (secondRating.containsKey(product)) {
					double rate1 = firstRating.get(product);
					double rate2 = secondRating.get(product);
					dotProduct += rate1* rate2;
					sumOfXSquare += Math.pow(rate1, 2);
					sumOfYSquare += Math.pow(rate2, 2);
				}
			}
			double output = dotProduct/(Math.sqrt(sumOfXSquare)*Math.sqrt(sumOfYSquare));
			//if (dotProduct != 0) System.out.println(dotProduct + " | " + (Math.sqrt(sumOfXSquare)*Math.sqrt(sumOfYSquare)));
			if (Double.isNaN(output)) {
				System.out.printf("Found NaN: %.3f | %.3f | %.3f%n", dotProduct, Math.sqrt(sumOfXSquare), Math.sqrt(sumOfYSquare));
				return 0.0;
			}
			else
				return output;
		}
	}

}
