package pearsonCorrelationCoefficient;
import java.util.Map;

import distance.Person;

public class PearsonCorrelation {

	public double computeCorrelation(Person firstPerson, Person secondPerson) {
		double sumOfXY = getSumOfXY(firstPerson, secondPerson);
		double productOfSumXAndSumYOverN = getProductOfSumXAndSumYOverN(firstPerson, secondPerson);
		double squareOfSumOfX = Math.pow(getSumOfFirst(firstPerson, secondPerson, 1), 2);
		double squareOfSumOfY = Math.pow(getSumOfFirst(secondPerson, firstPerson, 1), 2);
		double sumOfXSquare = getSumOfFirst(firstPerson, secondPerson, 2);
		double sumOfYSquare = getSumOfFirst(secondPerson, firstPerson, 2);
		int matches = getMatch(firstPerson, secondPerson);
		return (sumOfXY - productOfSumXAndSumYOverN)/((Math.sqrt(sumOfXSquare - squareOfSumOfX/matches)*(Math.sqrt(sumOfYSquare - squareOfSumOfY/matches))));
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
